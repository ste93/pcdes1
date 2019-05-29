package es1;

import java.util.ArrayList;

public class PlanetManager {

	private ArrayList<Planet> planetsList;
	
	public PlanetManager() {
		planetsList = new ArrayList<Planet>();
		for(int i = 0; i < Constants.PLANET_NUMBER; i++) {
			planetsList.add(new Planet((int)(Math.random() * Constants.DRAWING_PANEL_SIZE_X),(int)(Math.random() * Constants.DRAWING_PANEL_SIZE_Y)));
		}
	}
	
	public ArrayList<Planet> getPlanetsList() {
		return this.planetsList;
	}

	public void updatePlanet(int planetNumber) {
		//planetsList[planetNumber].
	}
	
	public synchronized int getPairToCalculate() {
		return 0;
	}
}
