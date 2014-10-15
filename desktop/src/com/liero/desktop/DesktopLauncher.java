package com.liero.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.liero.Liero;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.useGL30 = true;
		config.title = "Liero Game";
		//config.vSyncEnabled = true;
		config.width = 1280;
		config.height = 720;
		new LwjglApplication(new Liero(), config);
	}
}
