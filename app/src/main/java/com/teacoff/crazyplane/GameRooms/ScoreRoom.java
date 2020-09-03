package com.teacoff.crazyplane.GameRooms;

import android.content.SharedPreferences;

import com.teacoff.crazyplane.GameComponents.Background;
import com.teacoff.crazyplane.GameView;

import ge.xordinate.xengine.EngineView;
import ge.xordinate.xengine.GameObject;
import ge.xordinate.xengine.NumberDisplay;
import ge.xordinate.xengine.Room;

/**
 * Score Room
 * <p>
 * High score menu user interface
 */
public class ScoreRoom extends Room{
    // Variables
    public static final String scoreSaveName = "score_save";
    public static final String scoreBestKey = "score";

    // Objects
    private GameObject title;
    private GameObject playButton;
    private Background bg1, bg2;
    private NumberDisplay score;

    public ScoreRoom(EngineView container){
        super(container);

        // Initialize objects
        title = new GameObject(this);
        playButton = new GameObject(this);
        bg1 = new Background(this);
        bg2 = new Background(this);
        score = new NumberDisplay(this);
    }

    public void set(){
        title.x = getWidth() / 2;
        title.y = getHeight() * 3 / 4;
        title.width = getWidth() * 3 / 4;
        title.height = title.width / 2;
        title.setGraphic(GameView.title, 1);
        playButton.x = getWidth() / 2;
        playButton.y = (getHeight() * 1 / 4);
        playButton.width = getWidth() / 3;
        playButton.height = playButton.width / 2;
        playButton.setGraphic(GameView.play, 2);
        playButton.frame = 0;
        SharedPreferences saves = this.getActivity().getSharedPreferences(scoreSaveName, 0);
        int oldPoints = saves.getInt(scoreBestKey, 0);
        score.setNumber(oldPoints);
        score.y = getHeight() / 2;
        score.width = getWidth() / 5;
        score.height = score.width;
        score.x = getWidth() / 2;
        score.setAlignment(1);

        bg1.set(getWidth() / 2, 4);
        bg2.set((int)bg1.x + getWidth(), 4);
    }

    @Override
    public void step(double dt){
        if(getTouch().objectTouched(playButton)){
            playButton.frame = 1;
        }
        else{
            playButton.frame = 0;
        }
    }

    /**
     * Check button
     */
    public void released(int index){
        if(getTouch().objectTouched(playButton)){
            GameView.difficulty.set();
            getView().goToRoom(GameView.difficulty);
            playButton.frame = 0;
        }
    }
}
