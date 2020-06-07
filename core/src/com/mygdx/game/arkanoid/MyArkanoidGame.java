package com.mygdx.game.arkanoid;

import java.util.HashSet;

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
		circle = new Circle(100, 100, 150, 150, 10);
		platform = new Platform(50, 10, (Gdx.graphics.getWidth() / 2) - 25, 30);
		goLeft = false;
		goRight = false;
		level = new HashSet<>();
		level.add(new Block(Color.GOLDENROD, 5, Gdx.graphics.getHeight() - 65, 30, 30));
		level.add(new Block(Color.GOLDENROD, 40, Gdx.graphics.getHeight() - 65, 30, 30));
		level.add(new Block(Color.GOLDENROD, 75, Gdx.graphics.getHeight() - 65, 30, 30));
		level.add(new Block(Color.GOLDENROD, 110, Gdx.graphics.getHeight() - 65, 30, 30));
		level.add(new Block(Color.GOLDENROD, 145, Gdx.graphics.getHeight() - 65, 30, 30));
		level.add(new Block(Color.GOLDENROD, 180, Gdx.graphics.getHeight() - 65, 30, 30));
	}

	@Override
	public void dispose() {
		batch.dispose();
		shapeRenderer.dispose();
		font.dispose();
	}


}
