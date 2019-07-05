
package es1;


import java.math.BigDecimal;

public class Planet {
	//fields
	private double mass; // for simplicity is integer, not double, suppose that grams are not useful
	private int positionX; //position is intended as pixel, so is integer
	private int positionY;
	private BigDecimal speedX;
	private BigDecimal speedY;
	private Double massPerGravity;
	private BigDecimal accelerationX;
	private BigDecimal accelerationY;

	public Planet(int positionX, int positionY) {
		this.setMass(Math.random() * Constants.MAX_MASS + 120000000);
		this.setSpeedX(BigDecimal.valueOf(Math.random()*2 - 1));
		this.setSpeedY(BigDecimal.valueOf(Math.random()*2 - 1));
		this.initAccelerationX();
		this.initAccelerationY();
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
	
	public BigDecimal getSpeedX() {
		return speedX;
	}
	public synchronized void setSpeedX(BigDecimal speedX) {
		this.speedX = speedX;
	}
	public BigDecimal getSpeedY() {
		return speedY;
	}
	public synchronized void setSpeedY(BigDecimal speedY) {
		this.speedY = speedY;
	}
	public double getMassPerGravity() {
		return massPerGravity;
	}
	public void setMassPerGravity(double massPerGravity) {
		this.massPerGravity = massPerGravity;
	}
	
	public BigDecimal getAccelerationX() {
		return accelerationX;
	}
	
	public void invertAccelerationX() {
		this.accelerationX = this.accelerationX.negate();

	}
	
	public void invertAccelerationY() {
		this.accelerationY = this.accelerationY.negate();
	}

	public synchronized void updateAccelerationX(BigDecimal accelerationX) {
		this.accelerationX = this.accelerationX.add(accelerationX);
	}

	public BigDecimal getAccelerationY() {
		return accelerationY;
	}

	private void initAccelerationY() {
		this.accelerationY = BigDecimal.valueOf(0);
	}
	private void initAccelerationX() {
		this.accelerationX = BigDecimal.valueOf(0);
	}

	public synchronized void updateAccelerationY(BigDecimal accelerationY) {
		this.accelerationY = this.accelerationY.add(accelerationY);
	}
	


}
