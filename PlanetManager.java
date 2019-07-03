package es1;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;

public class PlanetManager {
	SynchronizationManager synchronizationManager;
	private ArrayList<Planet> planetsList;
	
	public PlanetManager(SynchronizationManager synchronizationManager) {
		planetsList = new ArrayList<>();
		for(int i = 0; i < Constants.PLANET_NUMBER; i++) {
			planetsList.add(
					new Planet(
							(int)(Math.random() * Constants.DRAWING_PANEL_SIZE_X),
							(int)(Math.random() * Constants.DRAWING_PANEL_SIZE_Y)));
		}
		this.synchronizationManager = synchronizationManager;
	}

	public ArrayList<Planet> getPlanetsList() {
		return this.planetsList;
	}

	
	
	public void updatePlanetsAcceleration(int planet1Number, int planet2Number) {
		this.synchronizationManager.acquireAcceleration(planet1Number);
		this.synchronizationManager.acquireAcceleration(planet2Number);
    	double deltaX, deltaY;
    	Planet planetA = this.planetsList.get(planet1Number);
    	Planet planetB = this.planetsList.get(planet2Number);

/*
		double deltaX = planetA.x - planetB.x;
		double deltaY = planetA.y - planetB.y;
		double distance = Math.sqrt(Math.pow(deltaY, 2) + Math.pow(deltaX, 2));
		double force = G * planetA.mass * planetB.mass / (Math.pow(distance, 2));

		double accelerationA = force / planetA.mass;
		double accelerationB = force / planetB.mass;
		if(deltaY != 0) {
			double accelerationAY = accelerationA * Math.sqrt(1 / (1 + Math.pow(deltaX / deltaY, 2)));
			double accelerationAX = accelerationAY * Math.abs(deltaX / deltaY);
			double accelerationBY = accelerationB * Math.sqrt(1 / (1 + Math.pow(deltaX / deltaY, 2)));
			double accelerationBX = accelerationBY * Math.abs(deltaX / deltaY);



 */
    	deltaX = planetA.getPositionX() - planetB.getPositionX();
   		deltaY = planetA.getPositionY() - planetB.getPositionY();
/*		double distance = Math.sqrt(Math.pow(deltaY, 2) + Math.pow(deltaX, 2));
		double force = Constants.GRAVITY * planetA.getMass() * planetB.getMass() / (Math.pow(distance, 2));

		double accelerationA = force / planetA.getMass();
		double accelerationB = force / planetB.getMass();
		if(deltaY != 0) {
			double accelerationAY = accelerationA * Math.sqrt(1 / (1 + Math.pow(deltaX / deltaY, 2)));
			double accelerationAX = accelerationAY * Math.abs(deltaX / deltaY);
			double accelerationBY = accelerationB * Math.sqrt(1 / (1 + Math.pow(deltaX / deltaY, 2)));
			double accelerationBX = accelerationBY * Math.abs(deltaX / deltaY);



 */

   		double deltaDoubleX = deltaX * deltaX;
    	double deltaDoubleY = deltaY * deltaY;
		BigDecimal accelerationAX = BigDecimal.valueOf(planetB.getMassPerGravity())
				.divide(BigDecimal.valueOf(deltaDoubleX), MathContext.DECIMAL32);
		BigDecimal accelerationBX = BigDecimal.valueOf(-1 * planetA.getMassPerGravity())
				.divide(BigDecimal.valueOf(deltaDoubleX), MathContext.DECIMAL32);
		BigDecimal accelerationAY = BigDecimal.valueOf(planetB.getMassPerGravity())
				.divide(BigDecimal.valueOf(deltaDoubleY), MathContext.DECIMAL32);
		BigDecimal accelerationBY = BigDecimal.valueOf(-1 * planetA.getMassPerGravity())
				.divide(BigDecimal.valueOf(deltaDoubleY), MathContext.DECIMAL32);

        planetA.updateAccelerationX(accelerationAX);
		planetB.updateAccelerationX(accelerationBX);
		planetA.updateAccelerationY(accelerationAY);
		planetB.updateAccelerationY(accelerationBY);
		this.synchronizationManager.releasePosition(planet1Number);
		this.synchronizationManager.releasePosition(planet2Number);
	}
	
	
	
    private int calculateNewPosition(double initialPosition, BigDecimal acceleration, BigDecimal initialSpeed) {
		//TODO delete
		System.out.println("calculate new position"  + initialPosition + " " + acceleration + " " + initialSpeed);
		BigDecimal newPosition =  BigDecimal.valueOf(initialPosition).add(
				BigDecimal.valueOf(0.5).multiply(acceleration.multiply(
						BigDecimal.valueOf(Constants.DELTA_TIME).pow(2), MathContext.DECIMAL32), MathContext.DECIMAL32).add(
    			initialSpeed.multiply(BigDecimal.valueOf(Constants.DELTA_TIME), MathContext.DECIMAL32)));
		return newPosition.intValue();
    }
    
    private BigDecimal calculateNewSpeed(BigDecimal acceleration, BigDecimal initialSpeed) {
    	return acceleration.multiply(BigDecimal.valueOf(Constants.DELTA_TIME), MathContext.DECIMAL32)
				.add(initialSpeed);
    }
    
    public void updatePlanetPosition(int planetNumber) {
    	this.synchronizationManager.acquirePosition(planetNumber);
    	Planet planet = this.planetsList.get(planetNumber);

    	//newPositionX
		int newPositionX = calculateNewPosition(planet.getPositionX(),
			planet.getAccelerationX(),
			planet.getSpeedX());
		if(newPositionX > Constants.MAX_POSITION_X) {
			if (planet.getAccelerationX().compareTo(BigDecimal.ZERO) > 0)
				planet.invertAccelerationX();
			if (planet.getSpeedX().compareTo(BigDecimal.ZERO) > 0)
				planet.setSpeedX(planet.getSpeedX().negate());
			newPositionX = calculateNewPosition(planet.getPositionX(),
					planet.getAccelerationX(),
					planet.getSpeedX());
		}
		else if (newPositionX < 1 ){
			if (planet.getAccelerationX().compareTo(BigDecimal.ZERO) < 0)
				planet.invertAccelerationX();
			if (planet.getSpeedX().compareTo(BigDecimal.ZERO) < 0)
				planet.setSpeedX(planet.getSpeedX().negate());
			newPositionX = calculateNewPosition(planet.getPositionX(),
					planet.getAccelerationX(),
					planet.getSpeedX());
		}
		
    	//newPositionY
		int newPositionY = calculateNewPosition(planet.getPositionY(),
				planet.getAccelerationY(),
				planet.getSpeedY());
		if(newPositionY > Constants.MAX_POSITION_Y) {
			if (planet.getAccelerationY().compareTo(BigDecimal.ZERO) > 0)
				planet.invertAccelerationY();
			if (planet.getSpeedY().compareTo(BigDecimal.ZERO) > 0)
				planet.setSpeedY(planet.getSpeedY().negate());
			newPositionY = calculateNewPosition(planet.getPositionY(),
					planet.getAccelerationY(),
					planet.getSpeedY());

		} else if (newPositionY < 1 ) {
			if (planet.getAccelerationY().compareTo(BigDecimal.ZERO) < 0)
				planet.invertAccelerationY();
			if (planet.getSpeedY().compareTo(BigDecimal.ZERO) < 0)
				planet.setSpeedY(planet.getSpeedY().negate());
			newPositionY = calculateNewPosition(planet.getPositionY(),
					planet.getAccelerationY(),
					planet.getSpeedY());
		}
		
		//TODO check from here
		/*
    	for (int i = 0; i < planetNumber;i++) {
    		this.synchronizationManager.acquireUpdatedPosition(i);
    	}
    	*/
		this.checkDistances(newPositionX, newPositionY, planetNumber, planet);
    	//TODO check to here
        //this.synchronizationManager.releaseUpdatedPosition(planetNumber, 
        //		Constants.PLANET_NUMBER - planetNumber - 1);
    	this.synchronizationManager.releaseWorker();
    	this.synchronizationManager.releaseGraphicLock();
    }
    
    
    
    
    
    private void checkDistances(int newPositionX,
    										 int newPositionY,
    										 int planetNumber,
    										 Planet planet) {
		this.synchronizationManager.getLock();
			boolean updatePositions = true;
			ArrayList<Integer> updatedPlanets = this.synchronizationManager.getUpdatedPlanetList();
			//updatedPlanets.forEach((x) -> {System.out.println(x);});
			for (Integer i : updatedPlanets) {
				double distance = this.calculateDistance(newPositionX, newPositionY, i);
				if (distance < Constants.MIN_DISTANCE_BETWEEN_PLANETS) {
					updatePositions = false;
					break;
				}
			}
			if (updatePositions) {
				planet.setPositionX(newPositionX);
				planet.setSpeedX(calculateNewSpeed(planet.getSpeedX(), planet.getAccelerationX()));
				planet.setPositionY(newPositionY);
				planet.setSpeedY(calculateNewSpeed(planet.getSpeedY(), planet.getAccelerationY()));
			}
			this.synchronizationManager.addPlanetToUpdatedList(planetNumber);
			if (updatedPlanets.size() == Constants.PLANET_NUMBER) {
				this.synchronizationManager.resetUpdatedPlanetList();
			}
		this.synchronizationManager.releaseLock();
	}

    
    
    
	private double calculateDistance(int newPositionX, int newPositionY, int planetNumber) {
		double distance = Math.sqrt(
				Math.pow(this.planetsList.get(planetNumber).getPositionX() - newPositionX,2) +
						Math.pow(this.planetsList.get(planetNumber).getPositionY() - newPositionY,2)
		);
		return distance;

	}
}

