package com.mygdx.game.arkanoid.logic;

public class Platform {

	int platformWidth = 50;
	int platformHeight = 20;
	int platformX;
	int platformY = 30;
	int speed = 300;

	public int getPlatformWidth() {
		return platformWidth;
	}

	public void setPlatformWidth(int platformWidth) {
		this.platformWidth = platformWidth;
	}

	public int getPlatformHeight() {
		return platformHeight;
	}

	public void setPlatformHeight(int platformHeight) {
		this.platformHeight = platformHeight;
	}

	public int getPlatformX() {
		return platformX;
	}

	public void setPlatformX(int platformX) {
		this.platformX = platformX;
	}

	public int getPlatformY() {
		return platformY;
	}

	public void setPlatformY(int platformY) {
		this.platformY = platformY;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}
	public int getPlatformUp() {
		return platformY + platformHeight;
	}
	public int getPlatformRight() {
		return platformX + platformWidth;
	}

	public Platform(int platformWidth, int platformHeight, int platformX, int platformY) {
		super();
		this.platformWidth = platformWidth;
		this.platformHeight = platformHeight;
		this.platformX = platformX;
		this.platformY = platformY;
	}
}
