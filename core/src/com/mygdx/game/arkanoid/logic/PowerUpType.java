package com.mygdx.game.arkanoid.logic;

import java.util.Random;

public enum PowerUpType {
	BOMB(0, 5), //5
	BALL(5, 15), //10
	PLATFORM(15, 35), //20
	NONE(35, 100); //65

	private int from;
	private int to;

	PowerUpType (int from, int to) {
		this.from = from;
		this.to = to;
	}

	public static PowerUpType getRandomType() {
		Random dice = new Random();
		int choice = dice.nextInt(100);
		for (PowerUpType type : PowerUpType.values()) {
			if (type.from <= choice && type.to > choice) {
				return type;
			}
		}
		return NONE;

	}
}
