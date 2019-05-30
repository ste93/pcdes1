package es1;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Worker {
	PlanetManager planetManager;
	ExecutorService exec;

	public Worker(PlanetManager planetManager) {
		this.planetManager = planetManager;
	}
	
	public void startWorker() {
        exec = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
		while(true) {
			//semaphore waiting all uupdatePlanetPositions
			for(int i = 0; i < Constants.PLANET_NUMBER; i++) {
				for (int j = i; j < Constants.PLANET_NUMBER; j++) {
					exec.submit(new ComputeAccelerations(planetManager, i, j,"x"));
					exec.submit(new ComputeAccelerations(planetManager, i, j,"y"));
				}
				exec.submit(new ComputePositions(planetManager,i, "x"));
				exec.submit(new ComputePositions(planetManager,i, "y"));
			}
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

        public ComputePositions(PlanetManager planetManager, int planetNumber, String axis) {
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
