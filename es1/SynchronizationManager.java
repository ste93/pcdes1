package es1;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SynchronizationManager {
    private ArrayList<Semaphore> accelerationSemaphore, positionSemaphore;//, updatedPositionSemaphore;
    private ArrayList<Integer> updated;
    private Semaphore graphicSemaphore, buttonSemaphore, workerSemaphore;
    private boolean buttonLock = false;
    private Lock lock;

    SynchronizationManager() {
        this.accelerationSemaphore = new ArrayList<>();
        this.positionSemaphore = new ArrayList<>();
        this.updated = new ArrayList<>();
        //this.updatedPositionSemaphore = new ArrayList<Semaphore>();
        for (int i = 0; i < es1.Constants.PLANET_NUMBER; i++) {
            accelerationSemaphore.add(new Semaphore(es1.Constants.PLANET_NUMBER - 1, true));
            positionSemaphore.add(new Semaphore(0, true));
            //updatedPositionSemaphore.add(new Semaphore(0, true));
        }
        this.graphicSemaphore = new Semaphore(0, true);
        this.buttonSemaphore = new Semaphore(1, true);
        this.workerSemaphore = new Semaphore(0, true);
        lock = new ReentrantLock();
    }


    /////////////////////////////////////////////////////////
    //GRAPHIC
    public void acquireGraphicLock() {
        try {
            this.graphicSemaphore.acquire(es1.Constants.PLANET_NUMBER);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void releaseGraphicLock() {
        this.graphicSemaphore.release();
    }


    /////////////////////////////////////////////////////////////////////
    //BUTTON
    public void acquireButtonLock() {
        try {
            this.buttonSemaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void releaseButtonLock() {
        this.buttonSemaphore.release();
    }

    public synchronized void buttonClick() {
        if (this.buttonLock) {
            this.releaseButtonLock();
        } else {
            this.acquireButtonLock();
        }
        this.buttonLock = !this.buttonLock;
    }

    ///////////////////////////////////////////////////////////////////////////////////
    //ACCELERATION
    private synchronized Semaphore getAccelerationSemaphore(int planetNumber) {
        return this.accelerationSemaphore.get(planetNumber);
    }

    public void releaseAcceleration() {
        for (Semaphore s : this.accelerationSemaphore) {
            s.release(es1.Constants.PLANET_NUMBER - 1);
        }
    }

    public void acquireAcceleration(int planetNumber) {
        try {
            getAccelerationSemaphore(planetNumber).acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    /////////////////////////////////////////////////////////////////////////////////
    //POSITION
    private synchronized Semaphore getPositionSemaphore(int planetNumber) {
        return this.positionSemaphore.get(planetNumber);
    }

    public void releasePosition(int planetNumber) {
        this.getPositionSemaphore(planetNumber).release();
    }

    public void acquirePosition(int planetNumber) {
        try {
            this.getPositionSemaphore(planetNumber).acquire(es1.Constants.PLANET_NUMBER - 1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /////////////////////////////////////////////////////////////////////////////////
    //UPDATED POSITION
	/*
	private synchronized Semaphore getUpdatedPositionSemaphore(int planetNumber) {
		return this.positionSemaphore.get(planetNumber);
	}
	
	public void releaseUpdatedPosition(int planetNumber, int numberPermits) {
		this.getUpdatedPositionSemaphore(planetNumber).release(numberPermits);		
	}
	
	public void acquireUpdatedPosition(int planetNumber) {
		try {
			this.getUpdatedPositionSemaphore(planetNumber).acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}	
	}
	*/
    //////////////////////////////////////////////////////////
    //WORKER
    public void releaseWorker() {
        this.workerSemaphore.release();
    }

    public void acquireWorker() {
        try {
            this.workerSemaphore.acquire(es1.Constants.PLANET_NUMBER);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void addPlanetToUpdatedList(int planetNumber) {
        this.updated.add(planetNumber);
    }

    public ArrayList<Integer> getUpdatedPlanetList() {
        return this.updated;
    }

    public void resetUpdatedPlanetList() {
        this.updated.clear();
    }

    public void getLock() {
        lock.lock();
    }

    public void releaseLock() {
        this.lock.unlock();
    }

}
