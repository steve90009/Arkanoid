package com.mygdx.game.arkanoid.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.arkanoid.MyArkanoidGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new MyArkanoidGame(), config);
		config.resizable = false;
		config.backgroundFPS = -1;
		config.width = 635;
	}
}
