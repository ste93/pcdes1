package es1;


public class Main {
	//random between 0 and 1
	public static void main(String[] args) {
		PlanetManager planetManager = new PlanetManager();
		PlanetsPanel panel = new PlanetsPanel(planetManager.getPlanetsList());
		GraphicSwing graphic = new GraphicSwing(panel);
		graphic.startGUI();
		
		
		//graphic.drawCenteredCircle(g, x, y, r);
		
	}
}
