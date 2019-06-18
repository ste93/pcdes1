public class Main {
	//random between 0 and 1
	public static void main(String[] args) {
		SynchronizationManager syncronizationManager = new SynchronizationManager();
		PlanetManager planetManager = new PlanetManager(syncronizationManager);
		PlanetsPanel panel = new PlanetsPanel(planetManager, syncronizationManager);
		GraphicSwing graphic = new GraphicSwing(panel, syncronizationManager);
		graphic.startGUI();
		Worker worker = new Worker(planetManager, graphic, syncronizationManager);
		worker.startWorker();		
	}
}