package com.racing121.perfectsquares.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.racing121.perfectsquares.IActivityRequestHandler;
import com.racing121.perfectsquares.PerfectSquares;

public class DesktopLauncher implements IActivityRequestHandler {
	private static DesktopLauncher application;
	public static void main (String[] arg) {
		if (application == null) {
			application = new DesktopLauncher();
		}
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 480;
		config.height = 800;
		new LwjglApplication(new PerfectSquares(application), config);
	}

	@Override
	public void showAds(boolean show) {

	}

	@Override
	public void loginFacebook(boolean login) {

	}

	@Override
	public void loadVideo(boolean load) {

	}

}
