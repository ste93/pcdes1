package es1;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Worker {
    es1.PlanetManager planetManager;
    ExecutorService exec;
    es1.GraphicSwing graphic;
    es1.SynchronizationManager synchronizationManager;


    public Worker(es1.PlanetManager planetManager, es1.GraphicSwing graphic,
                  es1.SynchronizationManager synchronizationManager,
                  ExecutorService executorService) {
        this.planetManager = planetManager;
        this.graphic = graphic;
        this.synchronizationManager = synchronizationManager;
        this.exec = executorService;
    }

    public void startWorker() throws InterruptedException {
        while (true) {
            for (int i = 0; i < es1.Constants.PLANET_NUMBER; i++) {
                for (int j = i + 1; j < es1.Constants.PLANET_NUMBER; j++) {
                    exec.submit(new ComputeAccelerations(planetManager, i, j));
                }
            }
            for (int i = 0; i < es1.Constants.PLANET_NUMBER; i++) {
                exec.submit(new ComputePositions(planetManager, i));
            }
            synchronizationManager.acquireButtonLock();
            synchronizationManager.acquireWorker();

            synchronizationManager.releaseButtonLock();
            graphic.updatePanel();
        }
    }


    private static class ComputeAccelerations implements Runnable {
        int planet1Number;
        int planet2Number;
        es1.PlanetManager planetManager;

        public ComputeAccelerations(es1.PlanetManager planetManager, int planet1Number, int planet2Number) {
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
        es1.PlanetManager planetManager;

        public ComputePositions(es1.PlanetManager planetManager, int planetNumber) {
            this.planetManager = planetManager;
            this.planetNumber = planetNumber;
        }

        @Override
        public void run() {
            planetManager.updatePlanetPositionAndSpeed(planetNumber);
        }
    }
}
