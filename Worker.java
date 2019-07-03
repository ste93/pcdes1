package es1;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Worker {
	PlanetManager planetManager;
	ExecutorService exec;
	GraphicSwing graphic;
	SynchronizationManager synchronizationManager;

	public Worker(PlanetManager planetManager, GraphicSwing graphic, SynchronizationManager synchronizationManager) {
		this.planetManager = planetManager;
		this.graphic = graphic;
		this.synchronizationManager = synchronizationManager;
	}
	
	public void startWorker() {
        exec = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
		while(true) {
			for(int i = 0; i < Constants.PLANET_NUMBER; i++) {
				for (int j = i+1; j < Constants.PLANET_NUMBER; j++) {
					exec.submit(new ComputeAccelerations(planetManager, i, j));
				}
				exec.submit(new ComputePositions(planetManager,i));
			}
			synchronizationManager.acquireButtonLock();
			synchronizationManager.acquireWorker();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			synchronizationManager.releaseButtonLock();
			graphic.updatePanel();
		}
	}
	
	
    private static class ComputeAccelerations implements Runnable {
    	int planet1Number;
    	int planet2Number;
    	PlanetManager planetManager;

        public ComputeAccelerations(PlanetManager planetManager, int planet1Number, int planet2Number) {
        	this.planet1Number = planet1Number;
        	this.planet2Number = planet2Number;
        	this.planetManager = planetManager;
        }

        @Override
        public void run() {
        	planetManager.updatePlanetsAcceleration(planet1Number, planet2Number);
        }
    }
    
    private static class ComputePositions implements Runnable {
    	int planetNumber;
    	PlanetManager planetManager; 

        public ComputePositions(PlanetManager planetManager, int planetNumber) {
            this.planetManager = planetManager;
            this.planetNumber = planetNumber;
        }
        
        @Override
        public void run() {
        	planetManager.updatePlanetPosition(planetNumber);
        }
    }
}
