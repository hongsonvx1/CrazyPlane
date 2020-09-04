package com.teacoff.crazyplane;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import ge.xordinate.xengine.EngineActivity;

/**
 * GameActivity
 * <p>
 * Game Activity is main class of the game
 * first initializing this class from MainActivity class to startRoom game
 * GameActivity class manage game views and other game components
 * </p>
 */
public class GameActivity extends EngineActivity {
    public boolean started = false;
    private GameView gameView;
    private AudioManager audioManager;
    private AdView mAdView;
    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Window flags
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Initialize gameView
//        gameView = new GameView(this);
        setContentView(R.layout.game_activity);
//        setContentView(gameView);
        gameView = findViewById(R.id.game_view);
        // Set screen orientation as portrait
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // Initialize audioManager
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        // Get screen dimensions and check x, y ratio
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        double x = Math.pow(dm.widthPixels / dm.xdpi, 2);
        double y = Math.pow(dm.heightPixels / dm.ydpi, 2);
        double screenInches = Math.sqrt(x + y);
        if (x > y) {
            super.onDestroy();
            moveTaskToBack(true);
            android.os.Process.killProcess(android.os.Process.myPid());
        }

        createAds();
        loadRewardedAd();
        gameView.setOnGameOverListener(new OnGameOverListener() {
            @Override
            public void onGameOver() {
                runOnUiThread(new Runnable() {
                    @Override public void run() {
                        if (mInterstitialAd.isLoaded()) {
                            mInterstitialAd.show();
                        }
                    }
                });
            }
        });
        mInterstitialAd.setAdListener(new AdListener(){
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
                loadRewardedAd();
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when the ad is displayed.
                loadRewardedAd();
            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
                loadRewardedAd();
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the interstitial ad is closed.
                loadRewardedAd();
            }
        });
    }

    private void createAds() {
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    private void loadRewardedAd() {
        MobileAds.initialize(this,
                getResources().getString(R.string.admob_app_id));

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getResources().getString(R.string.interstitial_ad_unit_id));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
    }

    /**
     * Check and set sound configuration
     */
    public void isMute(int i) {
        if (i == 1) {
            audioManager.setStreamMute(AudioManager.STREAM_MUSIC, false);
        } else {
            audioManager.setStreamMute(AudioManager.STREAM_MUSIC, true);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (started) {
            gameView.game.player.pauseAll();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (started) {
            gameView.game.player.resumeAll();
        }
    }
}
