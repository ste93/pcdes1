
package es1;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService ;
        executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        es1.SynchronizationManager syncronizationManager = new es1.SynchronizationManager();
        es1.PlanetManager planetManager = new es1.PlanetManager(syncronizationManager);
        es1.PlanetsPanel panel = new es1.PlanetsPanel(planetManager, syncronizationManager, executorService);
        es1.GraphicSwing graphic = new es1.GraphicSwing(panel, syncronizationManager);
        graphic.startGUI();
        es1.Worker worker = new es1.Worker(planetManager, graphic, syncronizationManager, executorService);
        worker.startWorker();
    }
}
