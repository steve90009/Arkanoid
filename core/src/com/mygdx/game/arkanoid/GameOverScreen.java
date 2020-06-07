package com.mygdx.game.arkanoid;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;

public class GameOverScreen extends ScreenAdapter {
	MyArkanoidGame myGame;

	public GameOverScreen(MyArkanoidGame game) {
		myGame = game;
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		myGame.batch.begin();
		myGame.font.draw(myGame.batch, "Game Over", Gdx.graphics.getWidth() / 2 - 25, Gdx.graphics.getHeight() / 2);
		myGame.batch.end();
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(new InputAdapter() {
			@Override
			public boolean touchDown(int x, int y, int pointer, int button) {
				myGame.setScreen(new MainScreen(myGame));
				return true;
			}

			@Override
			public boolean keyDown(int keycode) {
				myGame.setScreen(new MainScreen(myGame));
				return true;
			}
		});
	}

	@Override
	public void hide() {
		Gdx.input.setInputProcessor(null);
	}

}
