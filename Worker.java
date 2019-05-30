package es1;

public class Worker {
	PlanetManager planetManager;

	public Worker(PlanetManager planetManager) {
		this.planetManager = planetManager;
	}
	
	public void startWorker() {
		for(int i = 0; i < Constants.PLANET_NUMBER; i++) {
			
		}
	}
	
	
    private static class ComputeAccelerations implements Runnable {
    	int planet1Number;
    	int planet2Number;
    	PlanetManager planetManager;
    	String axis;

        public ComputeAccelerations(PlanetManager planetManager, int planet1Number, int planet2Number, String axis) {
        	this.planet1Number = planet1Number;
        	this.planet2Number = planet2Number;
        	this.planetManager = planetManager;
            this.axis = axis;
        }

        @Override
        public void run() {
        	planetManager.updatePlanetsAcceleration(planet1Number, planet2Number, axis);
        }
    }
    
    private static class ComputePositions implements Runnable {
    	int planetNumber;
    	PlanetManager planetManager; 
    	String axis;

        public ComputePositions(int planetNumber, String axis, PlanetManager planetManager) {
            this.planetManager = planetManager;
            this.planetNumber = planetNumber;
            this.axis = axis;
        }
        
        @Override
        public void run() {
        	planetManager.updatePlanetPosition(planetNumber, axis);
        }
    }
}
