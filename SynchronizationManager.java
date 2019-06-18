package es1;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class SynchronizationManager {
	public ArrayList<Semaphore> accelerationSemaphore, positionSemaphore, updatedPositionSemaphore;
	//public ArrayList<Semaphore> positionSemaphore;
	public Semaphore graphicSemaphore, buttonSemaphore, workerSemaphore; 
	public boolean buttonLock = false;
	public SynchronizationManager() {
		this.accelerationSemaphore = new ArrayList<Semaphore>();
		this.positionSemaphore = new ArrayList<Semaphore>();
		this.updatedPositionSemaphore = new ArrayList<Semaphore>();
		for (int i =0; i < Constants.PLANET_NUMBER;i++) {
			accelerationSemaphore.add(new Semaphore(Constants.PLANET_NUMBER-1, true));
			//need also the graphic lock
			positionSemaphore.add(new Semaphore(0, true));
			updatedPositionSemaphore.add(new Semaphore(0, true));
		}
		this.graphicSemaphore = new Semaphore(0,true);
		this.buttonSemaphore = new Semaphore(1,true);	
		this.workerSemaphore = new Semaphore(0,true);
	}

	
	/////////////////////////////////////////////////////////
	public void acquireGraphicLock() {
		//TODO
        //System.out.println("acquireGraphicLock");
		try {
			this.graphicSemaphore.acquire(Constants.PLANET_NUMBER);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void releaseGraphicLock() {
		//TODO
        //System.out.println("releaseGraphicLock");
		this.graphicSemaphore.release();
	}

	
	/////////////////////////////////////////////////////////////////////
	public void acquireButtonLock() {
		//TODO
        //System.out.println("acquireButtonLock");
		try {
			this.buttonSemaphore.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void releaseButtonLock() {
		//TODO
        //System.out.println("releaseButtonLock");
		this.buttonSemaphore.release();
	}
	
	public synchronized void buttonClick() {
		if (this.buttonLock) {
			this.releaseButtonLock();
		}
		else {
			this.acquireButtonLock();
		}
		this.buttonLock = !this.buttonLock;
	}
	
	
	///////////////////////////////////////////////////////
	private synchronized Semaphore getAccelerationSemaphore(int planetNumber) {
		return this.accelerationSemaphore.get(planetNumber);
	}
	
	public void releaseAcceleration() {
		//TODO
      //  System.out.println("releaseAcceleration");
		for(Semaphore s : this.accelerationSemaphore) {
			s.release(Constants.PLANET_NUMBER-1);	
		}
	}
	
	public void acquireAcceleration(int planetNumber) {
		//TODO
    //    System.out.println("acquireAcceleration " + planetNumber);
		try {
			getAccelerationSemaphore(planetNumber).acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	
	/////////////////////////////////////////////////////////////////////////////////
	private synchronized Semaphore getPositionSemaphore(int planetNumber) {
		return this.positionSemaphore.get(planetNumber);
	}
	
	public void releasePosition(int planetNumber) {
		//TODO
  //      System.out.println("releasePosition " + planetNumber);
		this.getPositionSemaphore(planetNumber).release();		
	}
	
	public void acquirePosition(int planetNumber) {
		//TODO
//        System.out.println("acquirePosition " + planetNumber);
		try {
			this.getPositionSemaphore(planetNumber).acquire(Constants.PLANET_NUMBER-1);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

	/////////////////////////////////////////////////////////////////////////////////
	private synchronized Semaphore getUpdatedPositionSemaphore(int planetNumber) {
		return this.positionSemaphore.get(planetNumber);
	}
	
	public void releaseUpdatedPosition(int planetNumber, int numberPermits) {
		//TODO
        System.out.println("releaseUpdatedPosition " + planetNumber);
		this.getUpdatedPositionSemaphore(planetNumber).release(numberPermits);		
	}
	
	public void acquireUpdatedPosition(int planetNumber) {
		//TODO
        //System.out.println("acquireUpdatedPosition " + planetNumber);
		try {
			this.getUpdatedPositionSemaphore(planetNumber).acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	//////////////////////////////////////////////////////////
	public void releaseWorker() {
		//TODO
        System.out.println("releaseWorker");
		this.workerSemaphore.release();		
	}
	
	public void acquireWorker() {
		//TODO
        //System.out.println("acquireWorker");
		try {
			this.workerSemaphore.acquire(Constants.PLANET_NUMBER);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
}
