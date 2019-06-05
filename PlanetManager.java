package es1;

import java.util.ArrayList;

public class PlanetManager {
	SynchronizationManager synchronizationManager;
	private ArrayList<Planet> planetsList;
	
	public PlanetManager(SynchronizationManager synchronizationManager) {
		planetsList = new ArrayList<Planet>();
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
	
//	private double calculateDistance(int deltaX, int deltaY) {
//		return Math.sqrt(deltaY^2 + deltaX^2);
//	}

	//lock done
	public void updatePlanetsAcceleration(int planet1Number, int planet2Number) {
		this.synchronizationManager.acquireAcceleration(planet1Number);
		this.synchronizationManager.acquireAcceleration(planet2Number);
    	int deltaX, deltaY;
    	Planet planetA = this.planetsList.get(planet1Number);
    	Planet planetB = this.planetsList.get(planet2Number);
   		
    	deltaX = planetA.getPositionX() - planetB.getPositionX();
   		deltaY = planetA.getPositionY() - planetB.getPositionY();
    	
   		double deltaDoubleX = deltaX * deltaX;
    	double deltaDoubleY = deltaY * deltaY;
    	
        double accelerationAX = (planetB.getMassPerGravity()) / (deltaDoubleX);
        double accelerationBX = (-1 * planetA.getMassPerGravity()) /(deltaDoubleX);
        double accelerationAY = (planetB.getMassPerGravity()) / (deltaDoubleY);
        double accelerationBY = (-1 * planetA.getMassPerGravity()) /(deltaDoubleY);

        planetA.updateAccelerationX(accelerationAX);
		planetB.updateAccelerationX(accelerationBX);
		planetA.updateAccelerationY(accelerationAY);
		planetB.updateAccelerationY(accelerationBY);
		//TODO
        System.out.println("updatePlanetsAcceleration " + planet1Number + " " + planet2Number);
		this.synchronizationManager.releasePosition(planet1Number);
		this.synchronizationManager.releasePosition(planet2Number);
	}
	
    private int calculateNewPosition(double initialPosition, double acceleration, double initialSpeed) {
    	int newPosition = (int) (initialPosition + 
    			0.5*acceleration * Constants.DELTA_TIME * Constants.DELTA_TIME +
    			initialSpeed * Constants.DELTA_TIME
    			);
    	return newPosition;
    }
    
    private double calculateNewSpeed(double acceleration, double initialSpeed) {
    	return acceleration * Constants.DELTA_TIME + initialSpeed;    	
    }
    
    public void updatePlanetPosition(int planetNumber) {
    	this.synchronizationManager.acquirePosition(planetNumber);//???
    	Planet planet = this.planetsList.get(planetNumber);
    	boolean updatePositions = true;
    	for (int i = 0; i < planetNumber;i++) {
    		//TODO acquire position updated
    		this.synchronizationManager.acquireUpdatedPosition(i);
    	}
    		int newPositionX = calculateNewPosition(planet.getPositionX(),
				planet.getAccelerationX(),
				planet.getSpeedX());
    		int newPositionY = calculateNewPosition(planet.getPositionY(),
					planet.getAccelerationY(),
					planet.getSpeedY());
        	for (int i = 0; i < planetNumber;i++) {
        		double distance = this.calculateDistance(newPositionX, newPositionY, i);
        		if (distance < Constants.MIN_DISTANCE_BETWEEN_PLANETS && distance > - Constants.MIN_DISTANCE_BETWEEN_PLANETS) {
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
    		//TODO
            //System.out.println("updatePlanetPosition " + planetNumber);
            this.synchronizationManager.releaseUpdatedPosition(planetNumber, 
            		Constants.PLANET_NUMBER - planetNumber - 1);
    	this.synchronizationManager.releaseWorker();
    	this.synchronizationManager.releaseGraphicLock();
    }

	private double calculateDistance(int newPositionX, int newPositionY, int planetNumber) {
		return Math.sqrt((this.planetsList.get(planetNumber).getPositionX() - newPositionX)^2 +
				(this.planetsList.get(planetNumber).getPositionY() - newPositionY)^2);
				
	}
}

