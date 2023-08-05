package com.racing121.perfectsquares;

import android.content.Context;
import android.os.Bundle;

import android.os.Handler;
import android.os.Message;
import android.support.multidex.MultiDex;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.appodeal.ads.Appodeal;
import com.appodeal.ads.BannerCallbacks;
import com.appodeal.gdx.GdxAppodeal;
import com.badlogic.gdx.backends.android.AndroidApplication;

public class AndroidLauncher extends AndroidApplication implements IActivityRequestHandler {

	private final int SHOW_ADS = 1;
	private final int HIDE_ADS = 0;

	private final int LOGIN = 1;
	private final int LOGOUT = 0;

	private final int LOAD = 1;
	private final int SHOW = 0;

	protected void attachBaseContext(Context base) {
		super.attachBaseContext(base);
		MultiDex.install(this);
	}

	protected Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case SHOW_ADS: {
					Appodeal.setBannerCallbacks(new BannerCallbacks() {
						@Override
						public void onBannerLoaded(int i, boolean b) {
							Appodeal.show(AndroidLauncher.this, Appodeal.BANNER_BOTTOM);
						}

						@Override
						public void onBannerFailedToLoad() {

						}

						@Override
						public void onBannerShown() {

						}

						@Override
						public void onBannerClicked() {

						}
					});
					break;
				}
				case HIDE_ADS: {
					Appodeal.hide(AndroidLauncher.this, GdxAppodeal.BANNER);
					break;
				}
			}
			switch (msg.what){
				case LOAD:{

				}
				case SHOW:{

				}
			}
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View gameView = initializeForView(new PerfectSquares(this));
		String appKey = "6b9d5e3ff901936449d8351940ea7f91bae3a983842d8c72";
		Appodeal.disableLocationPermissionCheck();
		Appodeal.initialize(AndroidLauncher.this, appKey, Appodeal.BANNER | Appodeal.INTERSTITIAL);
		Appodeal.cache(AndroidLauncher.this, Appodeal.BANNER);


		Appodeal.setTesting(false);
		Appodeal.setLogging(true);


		Appodeal.setBannerCallbacks(new BannerCallbacks() {
			@Override
			public void onBannerLoaded(int i, boolean b) {
				Appodeal.show(AndroidLauncher.this, Appodeal.BANNER_BOTTOM);
			}

			@Override
			public void onBannerFailedToLoad() {

			}

			@Override
			public void onBannerShown() {

			}

			@Override
			public void onBannerClicked() {

			}
		});
		System.out.println("loaded: " + Appodeal.isLoaded(Appodeal.BANNER_BOTTOM));

		System.out.println("loaded: " + Appodeal.isLoaded(Appodeal.BANNER_BOTTOM));

		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT);
		// Create the layout
		LinearLayout layout = new LinearLayout(this);

		// Do the stuff that initialize() would do for you
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);

		// Add the libgdx view
		layout.addView(gameView);
		// Hook it all up
		setContentView(layout);
	}

	// This is the callback that posts a message for the handler
	@Override
	public void showAds(boolean show) {
		handler.sendEmptyMessage(show ? SHOW_ADS : HIDE_ADS);
	}

	@Override
	public void loginFacebook(boolean login) {
		handler.sendEmptyMessage(login ? LOGIN : LOGOUT);
	}

	@Override
	public void loadVideo(boolean load) {
		handler.sendEmptyMessage(load ? LOAD : SHOW);
	}

}
