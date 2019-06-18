public class Planet {
	//fields
	private double mass; // for simplicity is integer, not double, suppose that grams are not useful
	private int positionX; //position is intended as pixel, so is integer
	private int positionY;
	private double speedX;
	private double speedY;
	private double massPerGravity;
	private double accelerationX;
	private double accelerationY;

	public Planet(int positionX, int positionY) {
		this.setMass(Math.random() * 100 + 5);
		this.setSpeedX(Math.random() - 0.5);
		this.setSpeedY(Math.random() - 0.5);
		this.positionX = positionX;
		this.positionY = positionY;
	}
	
	public double getMass() {
		return mass;
	}
	public void setMass(double mass) {
		this.mass = mass;
		this.setMassPerGravity(this.mass * Constants.GRAVITY);
	}
	public int getPositionY() {
		return positionY;
	}

	public int getPositionX() {
		return positionX;
	}
	
	public synchronized void setPositionY(int positionY) {
		this.positionY = positionY;
	}

	public synchronized void setPositionX(int positionX) {
		this.positionX = positionX;
	}
	
	public double getSpeedX() {
		return speedX;
	}
	public synchronized void setSpeedX(double speedX) {
		this.speedX = speedX;
	}
	public double getSpeedY() {
		return speedY;
	}
	public synchronized void setSpeedY(double speedY) {
		this.speedY = speedY;
	}
	public double getMassPerGravity() {
		return massPerGravity;
	}
	public void setMassPerGravity(double massPerGravity) {
		this.massPerGravity = massPerGravity;
	}
	
	public double getAccelerationX() {
		return accelerationX;
	}

	public synchronized void updateAccelerationX(double accelerationX) {
		this.accelerationX = this.accelerationX + accelerationX;
	}

	public double getAccelerationY() {
		return accelerationY;
	}

	public synchronized void updateAccelerationY(double accelerationY) {
		this.accelerationY = this.accelerationY + accelerationY;
	}
	


}
