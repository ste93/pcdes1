
package es1;


public class Main {
    public static void main(String[] args) throws InterruptedException {
        es1.SynchronizationManager syncronizationManager = new es1.SynchronizationManager();
        es1.PlanetManager planetManager = new es1.PlanetManager(syncronizationManager);
        es1.PlanetsPanel panel = new es1.PlanetsPanel(planetManager, syncronizationManager);
        es1.GraphicSwing graphic = new es1.GraphicSwing(panel, syncronizationManager);
        graphic.startGUI();
        es1.Worker worker = new es1.Worker(planetManager, graphic, syncronizationManager);
        worker.startWorker();
    }
}
