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
		System.out.println(myGame.circle.getCircleX());

		movePlattform();
		checkCollisionBorder();
		checkCollisionPlattform();

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

	private void movePlattform() {
		if (myGame.goLeft && myGame.platform.getPlatformX() > 0) {
			myGame.platform.setPlatformX(myGame.platform.getPlatformX() - myGame.platform.getSpeed());
		}
		if (myGame.goRight
				&& myGame.platform.getPlatformX() < (Gdx.graphics.getWidth() - myGame.platform.getPlatformWidth())) {
			myGame.platform.setPlatformX(myGame.platform.getPlatformX() + myGame.platform.getSpeed());
		}
	}

	private void checkCollisionPlattform() {
		if (myGame.circle.getCircleX() + myGame.circle.getCircleR() > myGame.platform.getPlatformX()
				&& myGame.circle.getCircleX() < myGame.platform.getPlatformX() + myGame.platform.getPlatformWidth()
						+ myGame.circle.getCircleR()
				&& myGame.circle.getCircleY() - myGame.circle.getCircleR() <= myGame.platform.getPlatformY()
						+ myGame.platform.getPlatformHeight()
				&& myGame.circle.getCircleY() + myGame.circle.getCircleR() >= myGame.platform.getPlatformY()
						+ myGame.platform.getPlatformHeight()) {
			myGame.circle.setSpeedY(Math.abs(myGame.circle.getSpeedY()));
		} else {

			if (myGame.circle.getCircleX() + myGame.circle.getCircleR() >= myGame.platform.getPlatformX()
					&& myGame.circle.getCircleX() - myGame.circle.getCircleR() <= myGame.platform.getPlatformX()
					&& myGame.circle.getCircleY() - myGame.circle.getCircleR() <= myGame.platform.getPlatformY()
							+ myGame.platform.getPlatformHeight()
					&& myGame.circle.getCircleY() + myGame.circle.getCircleR() >= myGame.platform.getPlatformY()) {
				myGame.circle.setSpeedX(-Math.abs(myGame.circle.getSpeedX()));
			}
			if (myGame.circle.getCircleX() - myGame.circle.getCircleR() <= myGame.platform.getPlatformX()
					+ myGame.platform.getPlatformWidth()
					&& myGame.circle.getCircleX() + myGame.circle.getCircleR() <= myGame.platform.getPlatformX()
							+ myGame.platform.getPlatformWidth()
					&& myGame.circle.getCircleY() - myGame.circle.getCircleR() <= myGame.platform.getPlatformY()
							+ myGame.platform.getPlatformHeight()
					&& myGame.circle.getCircleY() + myGame.circle.getCircleR() >= myGame.platform.getPlatformY()) {
				myGame.circle.setSpeedX(Math.abs(myGame.circle.getSpeedX()));
			}
		}
	}

	@Override
	public void hide() {
		Gdx.input.setInputProcessor(null);
	}
}
