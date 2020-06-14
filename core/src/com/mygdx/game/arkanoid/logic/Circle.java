package com.mygdx.game.arkanoid.logic;

public class Circle {

	int circleX = 100;
	int circleY = 100;
	int speedX;
	int speedY = 100;
	int circleR = 10;
	boolean hittedWithForce = false;

	public int getCircleX() {
		return circleX;
	}

	public void setCircleX(int circleX) {
		this.circleX = circleX;
	}

	public int getCircleY() {
		return circleY;
	}

	public void setCircleY(int circleY) {
		this.circleY = circleY;
	}

	public int getSpeedX() {
		return speedX;
	}

	public void setSpeedX(int speedX) {
		this.speedX = speedX;
	}
	public void changeSpeedX(int delta) {
		speedX = speedX + delta;
	}

	public int getSpeedY() {
		return speedY;
	}

	public void setSpeedY(int speedY) {
		this.speedY = speedY;
	}
	public void changeSpeedY(int delta) {
		speedY = speedY + delta;
	}

	public int getCircleR() {
		return circleR;
	}

	public void setCircleR(int circleR) {
		this.circleR = circleR;
	}

	public boolean isHittedWithForce() {
		return hittedWithForce;
	}

	public void setHittedWithForce(boolean hittedWithForce) {
		this.hittedWithForce = hittedWithForce;
	}

	public Circle(int circleX, int circleY, int speedX, int speedY, int circleR) {
		super();
		this.circleX = circleX;
		this.circleY = circleY;
		this.speedX = speedX;
		this.speedY = speedY;
		this.circleR = circleR;
	}
	public int getCircleUp() {
		return circleY + circleR;
	}
	public int getCircleDown() {
		return circleY - circleR;
	}

	public int getCircleRight() {
		return circleX + circleR;
	}

	public int getCircleLeft() {
		return circleX - circleR;
	}
}
