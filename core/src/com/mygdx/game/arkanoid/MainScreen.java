package com.mygdx.game.arkanoid;

import java.util.HashSet;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game.arkanoid.logic.Block;
import com.mygdx.game.arkanoid.logic.Circle;
import com.mygdx.game.arkanoid.logic.PowerUp;
import com.mygdx.game.arkanoid.logic.PowerUpType;

public class MainScreen extends ScreenAdapter {
	MyArkanoidGame myGame;
	int activeBombs = 0;
	int activePlatformTime = -1;
	boolean platformActivated = false;

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

		if (activePlatformTime % 500 == 0 && platformActivated) {
			myGame.platform.setPlatformWidth(myGame.platform.getPlatformWidth() - myGame.platformPowerUpPlus);
			myGame.platform.setPlatformX(myGame.platform.getPlatformX() + myGame.platformPowerUpPlus/2);
			activePlatformTime--;
			platformActivated = (activePlatformTime > 0);
		} else if (activePlatformTime > 0){
			activePlatformTime--;
		}

		movePlattform(delta);
		HashSet<Circle> fallen = new HashSet<>();
		for (Circle circle : myGame.balls) {
			circle.setCircleX((int) (circle.getCircleX() + circle.getSpeedX() * delta));
			circle.setCircleY((int) (circle.getCircleY() + circle.getSpeedY() * delta));
			if (!checkCollisionBorder(circle)) {
				fallen.add(circle);
			}

			if (!checkCollisionPlattform(circle)) {
				circle.setHittedWithForce(false);
			}
		}
		HashSet<PowerUp> collected = new HashSet<>();
		HashSet<PowerUp> lost = new HashSet<>();
		myGame.batch.begin();
		for (PowerUp powerUp : myGame.powerUps) {
			powerUp.setPowerUpY((int) (powerUp.getPowerUpY() + powerUp.getPowerUpSpeedY() * delta));
			myGame.batch.draw(myGame.ballsImage, powerUp.getPowerUpX(), powerUp.getPowerUpY(),
					powerUp.getType().ordinal() * 40, 2, 40, 40);
			if (checkCollisionPlattform(powerUp)) {
				collected.add(powerUp);
			} else if (powerUp.getPowerUpUp() <= 0) {
				lost.add(powerUp);
			}

		}
		myGame.batch.end();

		myGame.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		myGame.shapeRenderer.setColor(Color.WHITE);
		myGame.shapeRenderer.rect(myGame.platform.getPlatformX(), myGame.platform.getPlatformY(),
				myGame.platform.getPlatformWidth(), myGame.platform.getPlatformHeight());
		HashSet<Block> hitted = new HashSet<>();
		for (Block block : myGame.level) {
			myGame.shapeRenderer.setColor(block.getColor());
			myGame.shapeRenderer.rect(block.getBlockX(), block.getBlockY(), block.getBlockWidth(),
					block.getBlockHeight());
		}

		myGame.shapeRenderer.setColor(Color.WHITE);
		for (Circle circle : myGame.balls) {

			for (Block block : myGame.level) {
				if (block.checkHit(circle)) {
					if (activeBombs > 0) {
						activeBombs--;
						circle.setCircleR(50);
					}
				}
			}
			myGame.shapeRenderer.circle(circle.getCircleX(), circle.getCircleY(), circle.getCircleR());
			for (Block block : myGame.level) {

				if (block.checkHitUp(circle)) {
					circle.setSpeedY(Math.abs(circle.getSpeedY()));
					hitted.add(block);

				} else if (block.checkHitDown(circle)) {
					circle.setSpeedY(-Math.abs(circle.getSpeedY()));
					hitted.add(block);

				} else if (block.checkHitLeft(circle)) {
					circle.setSpeedX(-Math.abs(circle.getSpeedX()));
					hitted.add(block);

				} else if (block.checkHitRight(circle)) {
					circle.setSpeedX(Math.abs(circle.getSpeedX()));
					hitted.add(block);

				}
			}
			circle.setCircleR(10);
		}
		myGame.shapeRenderer.end();

		for (Block block : hitted) {
			if (block.getType() != PowerUpType.NONE) {
				myGame.powerUps.add(new PowerUp(block.getType(), block.getBlockX(), block.getBlockY(), -75));
			}
		}
		activatePowerUps(collected);
		myGame.level.removeAll(hitted);
		myGame.balls.removeAll(fallen);
		myGame.powerUps.removeAll(collected);
		myGame.powerUps.removeAll(lost);
		if (myGame.balls.size() == 0) {
			myGame.setScreen(new GameOverScreen(myGame));

		}
		if (myGame.level.size() == 0) {
			myGame.setScreen(new WinScreen(myGame));
		}
	}

	private void activatePowerUps(HashSet<PowerUp> powerUps) {
		Random dice = new Random();
		for (PowerUp powerUp : powerUps) {
			switch (powerUp.getType()) {
			case BALL:
				for (int i = 0; i <= dice.nextInt(3); i++) {

				myGame.balls.add(new Circle(powerUp.getPowerUpX(), powerUp.getPowerUpY(), dice.nextInt(200) - 100, 150, 10));
				}
				break;
			case BOMB:
				activeBombs++;
				break;
			case PLATFORM:
				platformActivated = true;
				activePlatformTime += (myGame.platformPowerUpTime );
				myGame.platform.setPlatformWidth(myGame.platform.getPlatformWidth() + myGame.platformPowerUpPlus);
				myGame.platform.setPlatformX(myGame.platform.getPlatformX() - myGame.platformPowerUpPlus/2);
				break;
			default:
				break;
			}
		}
	}

	private boolean checkCollisionBorder(Circle circle) {

		if (circle.getCircleRight() >= Gdx.graphics.getWidth()) {
			circle.setSpeedX(-Math.abs(circle.getSpeedX()));
		}
		if (circle.getCircleLeft() <= 0) {
			circle.setSpeedX(Math.abs(circle.getSpeedX()));
		}
		if (circle.getCircleUp() >= Gdx.graphics.getHeight()) {
			circle.setSpeedY(-Math.abs(circle.getSpeedY()));
		}
		if (circle.getCircleDown() <= 0) {
			return false;
		}
		return true;
	}

	private void movePlattform(float delta) {
		if (myGame.goLeft && myGame.platform.getPlatformX() > 0) {
			myGame.platform.setPlatformX(myGame.platform.getPlatformX() - (int) (myGame.platform.getSpeed() * delta));
		}
		if (myGame.goRight
				&& myGame.platform.getPlatformX() + myGame.platform.getPlatformWidth() < (Gdx.graphics.getWidth())) {
			myGame.platform.setPlatformX(myGame.platform.getPlatformX() + (int) (myGame.platform.getSpeed() * delta));
		}
	}

	private boolean checkCollisionPlattform(Circle circle) {
		if (circle.getCircleRight() > myGame.platform.getPlatformX()
				&& circle.getCircleLeft() < myGame.platform.getPlatformRight()
				&& circle.getCircleDown() <= myGame.platform.getPlatformUp()
				&& circle.getCircleUp() >= myGame.platform.getPlatformUp()) {
			circle.setSpeedY(Math.abs(circle.getSpeedY()));
			if (myGame.goLeft && !myGame.goRight && !circle.isHittedWithForce()) {
				circle.changeSpeedX(-(int) (myGame.platform.getSpeed() * myGame.platformToCircle));
				if (circle.getSpeedX() < -myGame.platform.getSpeed()) {
					circle.setSpeedX((int) (-myGame.platform.getSpeed() * 0.9));
				}
				circle.setHittedWithForce(true);
				return true;
			} else if (myGame.goRight && !myGame.goLeft && !circle.isHittedWithForce()) {
				circle.changeSpeedX((int) (myGame.platform.getSpeed() * myGame.platformToCircle));
				if (circle.getSpeedX() > myGame.platform.getSpeed()) {
					circle.setSpeedX((int) (myGame.platform.getSpeed() * 0.9));
				}
				circle.setHittedWithForce(true);
				return true;
			}
		} else {

			if (circle.getCircleRight() >= myGame.platform.getPlatformX()
					&& circle.getCircleLeft() <= myGame.platform.getPlatformX()
					&& circle.getCircleDown() <= myGame.platform.getPlatformUp()
					&& circle.getCircleUp() >= myGame.platform.getPlatformY()) {
				circle.setSpeedX((int) (-myGame.platform.getSpeed() * 0.9));
				return true;
			}
			if (circle.getCircleLeft() <= myGame.platform.getPlatformRight()
					&& circle.getCircleRight() >= myGame.platform.getPlatformRight()
					&& circle.getCircleDown() <= myGame.platform.getPlatformUp()
					&& circle.getCircleUp() >= myGame.platform.getPlatformY()) {
				circle.setSpeedX((int) (myGame.platform.getSpeed() * 0.9));
				return true;
			}
		}
		return false;
	}

	private boolean checkCollisionPlattform(PowerUp powerUp) {
		return (powerUp.getPowerUpRight() > myGame.platform.getPlatformX()
				&& powerUp.getPowerUpX() < myGame.platform.getPlatformRight()
				&& powerUp.getPowerUpY() <= myGame.platform.getPlatformUp()
				&& powerUp.getPowerUpUp() >= myGame.platform.getPlatformUp());
	}

	@Override
	public void hide() {
		Gdx.input.setInputProcessor(null);
	}
}
