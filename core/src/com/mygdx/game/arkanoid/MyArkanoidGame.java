package com.mygdx.game.arkanoid;

import java.util.HashSet;
import java.util.Random;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game.arkanoid.logic.Block;
import com.mygdx.game.arkanoid.logic.Circle;
import com.mygdx.game.arkanoid.logic.Platform;

public class MyArkanoidGame extends Game {
	SpriteBatch batch;
	ShapeRenderer shapeRenderer;
	boolean goLeft = false;
	boolean goRight = false;
	boolean hittedWithForce = false;
	float platformToCircle = 0.5f;
	Circle circle;
	Platform platform;
	BitmapFont font;
	HashSet<Block> level;

	@Override
	public void create() {
		batch = new SpriteBatch();
		font = new BitmapFont();
		shapeRenderer = new ShapeRenderer();
		setScreen(new MainScreen(this));
	}

	public void init() {
		Random dice = new Random();
		circle = new Circle(Gdx.graphics.getWidth() / 2, 100, 1, 150, 10);
		platform = new Platform(50, 10, (Gdx.graphics.getWidth() / 2) - 25, 30);
		goLeft = false;
		goRight = false;
		level = new HashSet<>();
		int g = 40;
		int h = 5;
		for (int j = 1; j <= 5; j++) {

			Color color = new Color(dice.nextFloat(),dice.nextFloat(),dice.nextFloat(),1);
				for (int i = 0; i < 18; i++) {

				level.add(new Block(color, (30 * i) +(i+1)* h, Gdx.graphics.getHeight() - ((30 * j) + g), 30, 30));
			}
			g += 5;
		}
	}

	@Override
	public void dispose() {
		batch.dispose();
		shapeRenderer.dispose();
		font.dispose();
	}

}
