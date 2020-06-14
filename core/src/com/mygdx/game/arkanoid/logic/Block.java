package com.mygdx.game.arkanoid.logic;

import com.badlogic.gdx.graphics.Color;

public class Block {
	private Color color;
	private int blockX;
	private int blockY;
	private int blockWidth;
	private int blockHeight;
	private PowerUpType type;

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public int getBlockX() {
		return blockX;
	}

	public void setBlockX(int blockX) {
		this.blockX = blockX;
	}

	public int getBlockY() {
		return blockY;
	}

	public void setBlockY(int blockY) {
		this.blockY = blockY;
	}

	public int getBlockWidth() {
		return blockWidth;
	}

	public void setBlockWidth(int blockWidth) {
		this.blockWidth = blockWidth;
	}

	public int getBlockHeight() {
		return blockHeight;
	}

	public void setBlockHeight(int blockHeight) {
		this.blockHeight = blockHeight;
	}

	public PowerUpType getType() {
		return type;
	}

	public Block(Color color, int blockX, int blockY, int blockWidth, int blockHeight, PowerUpType type) {
		super();
		this.color = color;
		this.blockX = blockX;
		this.blockY = blockY;
		this.blockWidth = blockWidth;
		this.blockHeight = blockHeight;
		this.type = type;
	}

	public boolean checkHitUp(Circle circle) {
		if (circle.getCircleRight() >= blockX
				&& circle.getCircleLeft() <= getBlockRight()
				&& circle.getCircleDown() <= getBlockUp()
				&& circle.getCircleUp() >= getBlockUp()) {
			return true;
		}
		return false;
	}

	public boolean checkHitDown(Circle circle) {
		if (circle.getCircleRight() >= blockX
				&& circle.getCircleLeft() <= getBlockRight()
				&& circle.getCircleDown() <= blockY
				&& circle.getCircleUp() >= blockY) {
			return true;
		}
		return false;
	}

	public boolean checkHitLeft(Circle circle) {
		if (circle.getCircleRight() >= blockX
				&& circle.getCircleLeft() <= blockX
				&& circle.getCircleDown() <= getBlockUp()
				&& circle.getCircleUp() >= blockY) {
			return true;
		}
		return false;
	}

	public boolean checkHitRight(Circle circle) {
		if (circle.getCircleRight() >= getBlockRight()
				&& circle.getCircleLeft() <= getBlockRight()
				&& circle.getCircleDown() <= getBlockUp()
				&& circle.getCircleUp() >= blockY) {
			return true;
		}
		return false;
	}
	public boolean checkHit(Circle circle) {
		return checkHitDown(circle) || checkHitUp(circle) || checkHitLeft(circle) || checkHitRight(circle);
	}

	private int getBlockUp() {
		return blockY + blockHeight;
	}

	private int getBlockRight() {
		return blockX + blockWidth;
	}
}
