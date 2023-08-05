package com.racing121.perfectsquares;

import com.appodeal.gdx.GdxAppodeal;
import com.appodeal.gdx.callbacks.InterstitialCallback;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.racing121.perfectsquares.screens.LevelUpScreen;
import com.racing121.perfectsquares.screens.MainMenuScreen;
import com.racing121.perfectsquares.screens.PlayScreen;
import com.racing121.perfectsquares.states.State;
import com.racing121.perfectsquares.states.Status;

public class PerfectSquares extends Game{
	public SpriteBatch batch;
	public static int LevelNumber = 1;
	public static int subLevelNumber = LevelNumber;
	FileHandle handle;
	FileHandle highscoreHandle;
	String saveLevel;
	String saveHighscore;
	Texture intro;
	float timer = 0;
	boolean showLevelUpScreen = false;
	int number = 0;

	public static int SCORE;
	public static int HIGHSCORE;
	public static int FAILS;
    public static int AdFails;

	private IActivityRequestHandler myRequestHandler;

	public PerfectSquares(IActivityRequestHandler handler) {
		myRequestHandler = handler;
	}

	@Override
	public void create() {
		handle = Gdx.files.local("data/level.ps");
		highscoreHandle = Gdx.files.local("data/highscore.ps");
		if (handle.exists()) {
			saveLevel = handle.readString();
		} else {
			handle.writeString("1", false);
			saveLevel = handle.readString();
		}

		if (highscoreHandle.exists()) {
			saveHighscore = highscoreHandle.readString();
		} else {
			highscoreHandle.writeString("0", false);
			saveHighscore = highscoreHandle.readString();
		}

		int saveLevelNumber = Integer.parseInt(saveLevel);
		int saveHighscoreNumber = Integer.parseInt(saveHighscore);
		LevelNumber = saveLevelNumber;
		HIGHSCORE = saveHighscoreNumber;
		batch = new SpriteBatch();
		State.setSTATE(Status.ITRNO);
		intro = new Texture("Intro.png");

		//create sound

		SCORE = 0;
		FAILS = 3;
        AdFails = 0;
	}

	@Override
	public void render() {
		super.render();
        if(AdFails >= 6){
            //show ads if they are ready
			GdxAppodeal.cache(GdxAppodeal.INTERSTITIAL);
			GdxAppodeal.setInterstitialCallbacks(new InterstitialCallback() {
				@Override
				public void onInterstitialLoaded(boolean isPrecache) {
					System.out.println("loaded");
					GdxAppodeal.show(GdxAppodeal.INTERSTITIAL);
				}

				@Override
				public void onInterstitialFailedToLoad() {
					System.out.println("failed");
				}

				@Override
				public void onInterstitialShown() {
					System.out.println("shown");
					AdFails = 0;
				}

				@Override
				public void onInterstitialClicked() {
					System.out.println("clicked");
				}

				@Override
				public void onInterstitialClosed() {
					System.out.println("closed");
					AdFails = 0;
				}
			});
            //rest fails

        }
		if (State.getSTATE() == Status.ITRNO) {
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			batch.begin();
			batch.draw(intro, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			batch.end();
			timer += 1 * Gdx.graphics.getRawDeltaTime();
			if (timer >= 4) //intro timing
				State.setSTATE(Status.MAIN_MENU_TRANSITION);
		}
		if (State.getSTATE() == Status.MAIN_MENU_TRANSITION) {
			setScreen(new MainMenuScreen(this));
			State.setSTATE(Status.MAIN_MENU);
		}
		if (State.getSTATE() == Status.MAIN_MENU) {
			myRequestHandler.showAds(true);
		}
		if (State.getSTATE() == Status.PLAY) {
			restart(false);
			State.setSTATE(Status.PLAYING);
		}
		if (State.getSTATE() == Status.PLAYING) {
			myRequestHandler.showAds(false);
		}

		if (State.getSTATE() == Status.LEVELING) {
			setScreen(new LevelUpScreen(this, LevelNumber));
			State.setSTATE(Status.LEVELUP);
		}
		if (State.getSTATE() == Status.LEVELUP) {
			myRequestHandler.showAds(true);
		}

	}


	public void restart(boolean NextLevel) {
		if (NextLevel == true)
			subLevelNumber -= 1;
		if (subLevelNumber <= 0) {
			LevelNumber += 1;
			String levelString = Integer.toString(LevelNumber);
			String highscoreString = Integer.toString(HIGHSCORE);
			handle.writeString(levelString, false);
			highscoreHandle.writeString(highscoreString, false);
			subLevelNumber = LevelNumber;
			showLevelUpScreen = true;
		}
		if (NextLevel == false) {
			//if user fails level
			subLevelNumber = LevelNumber;
			AdFails += 1;
			SCORE = 0;
		}
		if (!showLevelUpScreen)
			setScreen(new PlayScreen(this, LevelNumber));
		if (showLevelUpScreen) {
			State.setSTATE(Status.LEVELING);
			showLevelUpScreen = false;
		}
	}
}

