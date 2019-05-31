package es1;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class SynchronizationManager {
	public ArrayList<Semaphore> accelerationSemaphore;
	public ArrayList<Semaphore> positionSemaphore;
	public Semaphore graphicSemaphore, buttonSemaphore, workerSemaphore; 
	
	public SynchronizationManager() {
		this.accelerationSemaphore = new ArrayList<Semaphore>();
		this.positionSemaphore = new ArrayList<Semaphore>();
		for (int i =0; i < Constants.PLANET_NUMBER;i++) {
			accelerationSemaphore.add(new Semaphore(Constants.PLANET_NUMBER-1, true));
			//need also the graphic lock
			positionSemaphore.add(new Semaphore(0, true));
		}
		this.graphicSemaphore = new Semaphore(0,true);
		this.buttonSemaphore = new Semaphore(1,true);	
		this.workerSemaphore = new Semaphore(0,true);
	}

	public void acquireGraphicLock() {
		//TODO
        System.out.println("acquireGraphicLock");
		try {
			this.graphicSemaphore.acquire(Constants.PLANET_NUMBER);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void releaseGraphicLock() {
		//TODO
        System.out.println("releaseGraphicLock");
		this.graphicSemaphore.release();
	}

	public void acquireButtonLock() {
		//TODO
        System.out.println("acquireButtonLock");
		try {
			this.buttonSemaphore.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void releaseButtonLock() {
		//TODO
        System.out.println("releaseButtonLock");
		this.buttonSemaphore.release();
	}
	
	public void releaseAcceleration() {
		//TODO
        System.out.println("releaseAcceleration");
		for(Semaphore s : this.accelerationSemaphore) {
			s.release(Constants.PLANET_NUMBER-1);	
		}
	}
	
	public void acquireAcceleration(int planetNumber) {
		//TODO
        System.out.println("acquireAcceleration " + planetNumber);
		try {
			this.accelerationSemaphore.get(planetNumber).acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public void releasePosition(int planetNumber) {
		//TODO
        System.out.println("releasePosition " + planetNumber);
		this.positionSemaphore.get(planetNumber).release();		
	}
	
	public void acquirePosition(int planetNumber) {
		//TODO
        System.out.println("acquirePosition " + planetNumber);
		try {
			this.positionSemaphore.get(planetNumber).acquire(Constants.PLANET_NUMBER-1);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public void releaseWorker() {
		//TODO
        System.out.println("releaseWorker");
		this.workerSemaphore.release();		
	}
	
	public void acquireWorker() {
		//TODO
        System.out.println("acquireWorker");
		try {
			this.workerSemaphore.acquire(Constants.PLANET_NUMBER);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
}
