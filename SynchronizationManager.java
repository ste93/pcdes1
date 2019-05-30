package es1;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class SynchronizationManager {
	public ArrayList<Semaphore> accelerationSemaphore;
	public ArrayList<Semaphore> positionSemaphore;
	
	public SynchronizationManager() {
		for (int i =0; i < Constants.PLANET_NUMBER;i++) {
			accelerationSemaphore.add(new Semaphore(Constants.PLANET_NUMBER  -1, true));
			//need also the graphic lock
			positionSemaphore.add(new Semaphore(-(Constants.PLANET_NUMBER + 1), true));
		}
	}

	public void acquireGraphicLock() {
		
	}
	
	public void releaseGraphicLock() {
		
	}
	
	public void releaseAcceleration(int planetNumber) {
		this.accelerationSemaphore.get(planetNumber).release();		
	}
	
	public void acquireAcceleration(int planetNumber) {
		try {
			this.accelerationSemaphore.get(planetNumber).acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public void releasePosition(int planetNumber) {
		this.positionSemaphore.get(planetNumber).release();		
	}
	
	public void acquirePosition(int planetNumber) {
		try {
			this.positionSemaphore.get(planetNumber).acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
}
