package es1;


public class Planet {
	//fields
	private double mass; // for simplicity is integer, not double, suppose that grams are not useful
	private int positionX; //position is intended as pixel, so is integer
	private int positionY;
	private double speedX;
	private double speedY;
	private double massPerGravity;

	public Planet(int positionX, int positionY) {
		this.setMass(Math.random() * 200 + 100);
		this.setSpeedX(Math.random() * 90 + 10);
		this.setSpeedY(Math.random() * 90 + 10);
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
	
	public void setPositionY(int positionY) {
		this.positionY = positionY;
	}

	public void setPositionX(int positionX) {
		this.positionX = positionX;
	}
	
	public double getSpeedX() {
		return speedX;
	}
	public void setSpeedX(double speedX) {
		this.speedX = speedX;
	}
	public double getSpeedY() {
		return speedY;
	}
	public void setSpeedY(double speedY) {
		this.speedY = speedY;
	}
	public double getMassPerGravity() {
		return massPerGravity;
	}
	public void setMassPerGravity(double massPerGravity) {
		this.massPerGravity = massPerGravity;
	}
	
	public synchronized void updateSpeedY(double partialSpeedY) {
		this.speedY = this.speedY + partialSpeedY;
	}
	
	public synchronized void updateSpeedX(double partialSpeedX) {
		this.speedX = this.speedX + partialSpeedX;
	}
	


}
