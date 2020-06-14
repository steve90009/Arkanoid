package com.mygdx.game.arkanoid.logic;

public class PowerUp {
	public PowerUp(PowerUpType type, int powerUpX, int powerUpY, int powerUpSpeedY) {
		super();
		this.type = type;
		this.powerUpX = powerUpX;
		this.powerUpY = powerUpY;
		this.powerUpSpeedY = powerUpSpeedY;
	}
	PowerUpType type;
	int powerUpX;
	int powerUpY;
	int powerUpSpeedY;
	public PowerUpType getType() {
		return type;
	}
	public void setType(PowerUpType type) {
		this.type = type;
	}
	public int getPowerUpX() {
		return powerUpX;
	}
	public void setPowerUpX(int powerUpX) {
		this.powerUpX = powerUpX;
	}
	public int getPowerUpY() {
		return powerUpY;
	}
	public void setPowerUpY(int powerUpY) {
		this.powerUpY = powerUpY;
	}
	public int getPowerUpSpeedY() {
		return powerUpSpeedY;
	}
	public void setPowerUpSpeedY(int powerUpSpeedY) {
		this.powerUpSpeedY = powerUpSpeedY;
	}
	public int getPowerUpRight() {
		return powerUpX + 40;
	}
	public int getPowerUpUp() {
		return powerUpY + 40;
	}

}
