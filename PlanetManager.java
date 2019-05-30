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

	public synchronized void updatePlanetsAcceleration(int planet1Number, int planet2Number, String axis) {
    	int delta;
    	Planet planetA = this.planetsList.get(planet1Number);
    	Planet planetB = this.planetsList.get(planet2Number);
    	if( axis == "x") {
    		delta = planetA.getPositionX() - planetB.getPositionX();
    	}else {
    		delta = planetA.getPositionY() - planetB.getPositionY();
    	} 
    	double deltaDouble = delta * delta;
        double accelerationA = (planetB.getMassPerGravity()) / (deltaDouble);
        double accelerationB = (-1 * planetA.getMassPerGravity()) /(deltaDouble);

        if( axis == "x") {
    		planetA.updateAccelerationX(accelerationA);
    		planetB.updateAccelerationX(accelerationB);
    	}else {
    		planetA.updateAccelerationY(accelerationA);
    		planetB.updateAccelerationY(accelerationB);
    	}
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
    
    public synchronized void updatePlanetPosition(int planetNumber, String axis) {
    	Planet planet = this.planetsList.get(planetNumber);
    	if( axis == "x") {
    		planet.setPositionX(
    				calculateNewPosition(planet.getPositionX(),
    						planet.getAccelerationX(),
    						planet.getSpeedX()));
    		planet.setSpeedX(calculateNewSpeed(planet.getSpeedX(), planet.getAccelerationX()));
    	}else {
    		planet.setPositionY(
    				calculateNewPosition(planet.getPositionY(),
    						planet.getAccelerationY(),
    						planet.getSpeedY())); 
    		planet.setSpeedY(calculateNewSpeed(planet.getSpeedY(), planet.getAccelerationY()));

    	}     	
    }
}

