package es1;

public class Worker {
	PlanetManager planetManager;

	public Worker(PlanetManager planetManager) {
		this.planetManager = planetManager;
	}
	
	public void startWorker() {
		
	}
	
	
    private static class ComputeAccelerations implements Runnable {
    	Planet planetA;
    	Planet planetB;
    	String axis;

        public ComputeAccelerations(Planet planetA, Planet planetB, String axis) {
            this.planetA = planetA;
            this.planetB = planetB;
            this.axis = axis;
        }

        @Override
        public void run() {
        	int delta;
        	if( axis == "x") {
        		delta = planetA.getPositionX() - planetB.getPositionX();
        	}else {
        		delta = planetA.getPositionY() - planetB.getPositionY();
        	}
            //double force = G * planetA.mass * planetB.mass / (Math.pow(distance, 2));
            double accelerationA = (planetB.getMassPerGravity()) / delta;
            double accelerationB = (-1 * planetA.getMassPerGravity()) / delta;

            if( axis == "x") {
        		planetA.updateAccelerationX(accelerationA);
        		planetB.updateAccelerationX(accelerationB);
        	}else {
        		planetA.updateAccelerationY(accelerationA);
        		planetB.updateAccelerationY(accelerationB);
        	}
            /*
            
            if(deltaY != 0) {
                double accelerationAY = accelerationA * Math.sqrt(1 / (1 + Math.pow(deltaX / deltaY, 2)));
                double accelerationAX = accelerationAY * Math.abs(deltaX / deltaY);
                double accelerationBY = accelerationB * Math.sqrt(1 / (1 + Math.pow(deltaX / deltaY, 2)));
                double accelerationBX = accelerationBY * Math.abs(deltaX / deltaY);
                accelerations[a][b][0] = accelerationAX * (deltaX > 0 ? -1 : 1);
                accelerations[a][b][1] = accelerationAY * (deltaY > 0 ? -1 : 1);
                accelerations[b][a][0] = accelerationBX  * (deltaX < 0 ? -1 : 1);
                accelerations[b][a][1] = accelerationBY  * (deltaY < 0 ? -1 : 1);
            } else {
                accelerations[a][b][0] = accelerationA  * (deltaX > 0 ? -1 : 1);
                accelerations[a][b][1] = 0;
                accelerations[b][a][0] = accelerationB  * (deltaX < 0 ? -1 : 1);
                accelerations[b][a][1] = 0;
            }*/
        }
    }
}
