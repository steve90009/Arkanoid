package com.mygdx.game.arkanoid;

import java.util.HashSet;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game.arkanoid.logic.Block;

public class MainScreen extends ScreenAdapter {
	MyArkanoidGame myGame;

	public MainScreen(MyArkanoidGame game) {
		myGame = game;
	}

	@Override
	public void show() {
		myGame.init();
		Gdx.input.setInputProcessor(new InputAdapter() {
			@Override
			public boolean keyDown(int keycode) {
				if (keycode == Input.Keys.LEFT) {
					myGame.goLeft = true;
				} else if (keycode == Input.Keys.RIGHT) {
					myGame.goRight = true;
				}
				return true;
			}

			@Override
			public boolean keyUp(int keycode) {
				if (keycode == Input.Keys.LEFT) {
					myGame.goLeft = false;
				} else if (keycode == Input.Keys.RIGHT) {
					myGame.goRight = false;
				}
				return true;
			}

		});
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		myGame.circle.setCircleX((int) (myGame.circle.getCircleX() + myGame.circle.getSpeedX() * delta));
		myGame.circle.setCircleY((int) (myGame.circle.getCircleY() + (myGame.circle.getSpeedY() * delta)));

		movePlattform(delta);
		checkCollisionBorder();
//		checkCollisionPlattform();
		if (!checkCollisionPlattform()) {
			myGame.hittedWithForce = false;
		}

		myGame.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		myGame.shapeRenderer.setColor(Color.WHITE);
		myGame.shapeRenderer.circle(myGame.circle.getCircleX(), myGame.circle.getCircleY(), myGame.circle.getCircleR());
		myGame.shapeRenderer.rect(myGame.platform.getPlatformX(), myGame.platform.getPlatformY(),
				myGame.platform.getPlatformWidth(), myGame.platform.getPlatformHeight());
		HashSet<Block> hitted = new HashSet<>();
		for (Block block : myGame.level) {
			myGame.shapeRenderer.setColor(block.getColor());
			myGame.shapeRenderer.rect(block.getBlockX(), block.getBlockY(),
					block.getBlockWidth(), block.getBlockHeight());
			if (block.checkHitUp(myGame.circle)) {
				myGame.circle.setSpeedY(Math.abs(myGame.circle.getSpeedY()));
				hitted.add(block);

			} else if (block.checkHitDown(myGame.circle)) {
				myGame.circle.setSpeedY(-Math.abs(myGame.circle.getSpeedY()));
				hitted.add(block);

			} else if (block.checkHitLeft(myGame.circle)) {
				myGame.circle.setSpeedX(-Math.abs(myGame.circle.getSpeedX()));
				hitted.add(block);

			} else if (block.checkHitRight(myGame.circle)) {
				myGame.circle.setSpeedX(Math.abs(myGame.circle.getSpeedX()));
				hitted.add(block);
			}
		}
		myGame.shapeRenderer.end();
		myGame.level.removeAll(hitted);
	}

	private void checkCollisionBorder() {
		if (myGame.circle.getCircleX() + myGame.circle.getCircleR() >= Gdx.graphics.getWidth()) {
			myGame.circle.setSpeedX(-Math.abs(myGame.circle.getSpeedX()));
		}
		if (myGame.circle.getCircleX() - myGame.circle.getCircleR() <= 0) {
			myGame.circle.setSpeedX(Math.abs(myGame.circle.getSpeedX()));
		}
		if (myGame.circle.getCircleY() + myGame.circle.getCircleR() >= Gdx.graphics.getHeight()) {
			myGame.circle.setSpeedY(-Math.abs(myGame.circle.getSpeedY()));
		}
		if (myGame.circle.getCircleY() - myGame.circle.getCircleR() <= 0) {
			System.out.println("game over");
			myGame.setScreen(new GameOverScreen(myGame));
		}
	}

	private void movePlattform(float delta) {
		if (myGame.goLeft && myGame.platform.getPlatformX() > 0) {
			myGame.platform.setPlatformX((int)(myGame.platform.getPlatformX() - myGame.platform.getSpeed() * delta));
		}
		if (myGame.goRight
				&& myGame.platform.getPlatformX() < (Gdx.graphics.getWidth() - myGame.platform.getPlatformWidth())) {
			myGame.platform.setPlatformX((int)(myGame.platform.getPlatformX() + myGame.platform.getSpeed() * delta));
		}
	}

	private boolean checkCollisionPlattform() {
		if (myGame.circle.getCircleX() + myGame.circle.getCircleR() > myGame.platform.getPlatformX()
				&& myGame.circle.getCircleX() < myGame.platform.getPlatformX() + myGame.platform.getPlatformWidth()
						+ myGame.circle.getCircleR()
				&& myGame.circle.getCircleY() - myGame.circle.getCircleR() <= myGame.platform.getPlatformY()
						+ myGame.platform.getPlatformHeight()
				&& myGame.circle.getCircleY() + myGame.circle.getCircleR() >= myGame.platform.getPlatformY()
						+ myGame.platform.getPlatformHeight()) {
			myGame.circle.setSpeedY(Math.abs(myGame.circle.getSpeedY()));

			if (myGame.goLeft && !myGame.hittedWithForce) {
				myGame.circle.changeSpeedX(-(int) (myGame.platform.getSpeed() * myGame.platformToCircle));
				if (myGame.circle.getSpeedX() < -myGame.platform.getSpeed()) {
					myGame.circle.setSpeedX((int) (-myGame.platform.getSpeed() * 0.9));
					System.out.println("speed gleich");
				}
				myGame.hittedWithForce = true;
				System.out.println("X: " + myGame.circle.getSpeedX());
				System.out.println("Y: " + myGame.circle.getSpeedY());
				return true;
			} else if (myGame.goRight && !myGame.hittedWithForce) {
				myGame.circle.changeSpeedX((int) (myGame.platform.getSpeed() * myGame.platformToCircle));
				if (myGame.circle.getSpeedX() > myGame.platform.getSpeed()) {
					myGame.circle.setSpeedX((int) (myGame.platform.getSpeed() * 0.9));
					System.out.println("speed gleich");
				}
				myGame.hittedWithForce = true;
				System.out.println("X: " + myGame.circle.getSpeedX());
				System.out.println("Y: " + myGame.circle.getSpeedY());
				return true;
			}
		} else {

			if (myGame.circle.getCircleRight() >= myGame.platform.getPlatformX()
					&& myGame.circle.getCircleLeft() <= myGame.platform.getPlatformX()
					&& myGame.circle.getCircleLeft() <= myGame.platform.getPlatformY()
							+ myGame.platform.getPlatformHeight()
					&& myGame.circle.getCircleRight() >= myGame.platform.getPlatformY()) {
				myGame.circle.setSpeedX(myGame.platform.getSpeed());
				return true;
			}
			if (myGame.circle.getCircleLeft() <= myGame.platform.getPlatformX()
					+ myGame.platform.getPlatformWidth()
					&& myGame.circle.getCircleRight() <= myGame.platform.getPlatformX()
							+ myGame.platform.getPlatformWidth()
					&& myGame.circle.getCircleRight() <= myGame.platform.getPlatformY()
							+ myGame.platform.getPlatformHeight()
					&& myGame.circle.getCircleLeft() >= myGame.platform.getPlatformY()) {
				myGame.circle.setSpeedX(-myGame.platform.getSpeed());
				return true;
			}
		}
		return false;
	}

	@Override
	public void hide() {
		Gdx.input.setInputProcessor(null);
	}
}
