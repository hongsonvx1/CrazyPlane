package com.teacoff.crazyplane;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import ge.xordinate.xengine.EngineActivity;

/**
 * GameActivity
 * <p>
 * Game Activity is main class of the game
 * first initializing this class from MainActivity class to startRoom game
 * GameActivity class manage game views and other game components
 * </p>
 */
public class GameActivity extends EngineActivity{
    public boolean started = false;
    private GameView gameView;
    private AudioManager audioManager;

    @Override
    protected void onCreate(Bundle savedInstanceState){
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
        audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);

        // Get screen dimensions and check x, y ratio
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        double x = Math.pow(dm.widthPixels / dm.xdpi, 2);
        double y = Math.pow(dm.heightPixels / dm.ydpi, 2);
        double screenInches = Math.sqrt(x + y);
        if(x>y){
            super.onDestroy();
            moveTaskToBack(true);
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }

    /**
     * Check and set sound configuration
     */
    public void isMute(int i){
        if(i == 1){
            audioManager.setStreamMute(AudioManager.STREAM_MUSIC, false);
        }
        else{
            audioManager.setStreamMute(AudioManager.STREAM_MUSIC, true);
        }
    }

    @Override
    public void onPause(){
        super.onPause();
        if(started){
            gameView.game.player.pauseAll();
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        if(started){
            gameView.game.player.resumeAll();
        }
    }
}
