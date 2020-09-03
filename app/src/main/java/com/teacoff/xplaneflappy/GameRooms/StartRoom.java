package com.teacoff.xplaneflappy.GameRooms;

import android.content.SharedPreferences;

import com.teacoff.xplaneflappy.GameActivity;
import com.teacoff.xplaneflappy.GameComponents.Background;
import com.teacoff.xplaneflappy.GameView;

import ge.xordinate.xengine.EngineView;
import ge.xordinate.xengine.GameObject;
import ge.xordinate.xengine.Room;
import ge.xordinate.xengine.SoundPlayer;

/**
 * Start Room
 * <p>
 * Main menu user interface
 */
public class StartRoom extends Room{
    // Variables
    public static final String saveSoundName = "soundsave";
    public static final String soundStatus = "soundstatus";
    public int oldPoints;
    public SoundPlayer player;

    // Objects
    public GameObject soundMute;

    private GameObject title;
    private GameObject playButton;
    private GameObject scoreButton;
    private Background bg1;
    private Background bg2;

    /**
     * StartRoom
     * <p>
     * Main menu of the game - User interface
     *
     * @param container - EngineView
     */
    public StartRoom(EngineView container){
        super(container);
        title = new GameObject(this);
        playButton = new GameObject(this);
        scoreButton = new GameObject(this);
        soundMute = new GameObject(this);
        bg1 = new Background(this);
        bg2 = new Background(this);
    }

    public void set(){
        SharedPreferences saves = this.getActivity().getSharedPreferences(saveSoundName, 0);
        oldPoints = saves.getInt(soundStatus, 0);
        title.x = getWidth() / 2;
        title.y = getHeight() * 3 / 4;
        title.width = getWidth() * 3 / 4;
        title.height = title.width / 2;
        title.setGraphic(GameView.title, 1);
        playButton.x = getWidth() / 2;
        playButton.y = (getHeight() / 2);
        playButton.width = getWidth() / 3;
        playButton.height = playButton.width / 2;
        playButton.setGraphic(GameView.play, 2);

        scoreButton.x = getWidth() / 2;
        scoreButton.y = getHeight() * 1 / 4;
        scoreButton.width = getWidth() / 3;
        scoreButton.height = scoreButton.width / 2;
        scoreButton.setGraphic(GameView.score, 2);

        soundMute.x = getWidth() / 2;
        soundMute.y = 0 + soundMute.height;
        soundMute.width = getWidth() / 6;
        soundMute.height = soundMute.width / 2;
        soundMute.setGraphic(GameView.mute, 2);
        if(oldPoints == 0){
            soundMute.frame = 0;
            ((GameActivity)getView().getActivity()).isMute(1);
        }
        else{
            soundMute.frame = 1;
            ((GameActivity)getView().getActivity()).isMute(0);
        }

        bg1.set(getWidth() / 2, 4);
        bg2.set((int)bg1.x + getWidth(), 4);
    }

    /**
     * Step event happens every frame.
     *
     * @param dt
     */
    @Override
    public void step(double dt){
        if(getTouch().objectTouched(playButton)){
            playButton.frame = 1;
        }
        else{
            playButton.frame = 0;
        }

        if(getTouch().objectTouched(scoreButton)){
            scoreButton.frame = 1;
        }
        else{
            scoreButton.frame = 0;
        }
    }

    /**
     * Check button
     */
    public void released(int index){
        if(getTouch().objectTouched(playButton)){
            GameView.difficulty.set();
            getView().goToRoom(GameView.difficulty);
        }

        if(getTouch().objectTouched(scoreButton)){
            GameView.scoreroom.set();
            getView().goToRoom(GameView.scoreroom);
        }
        if(getTouch().objectTouched(soundMute)){
            manageScore();
        }
    }

    private void manageScore(){
        SharedPreferences saves = this.getActivity().getSharedPreferences(saveSoundName, 0);
        oldPoints = saves.getInt(soundStatus, 0);
        if(oldPoints == 0){
            SharedPreferences.Editor editor = saves.edit();
            editor.putInt(soundStatus, 1);
            soundMute.frame = 1;
            editor.commit();
            ((GameActivity)getView().getActivity()).isMute(0);
        }
        else{
            SharedPreferences.Editor editor = saves.edit();
            editor.putInt(soundStatus, 0);
            soundMute.frame = 0;
            editor.commit();
            ((GameActivity)getView().getActivity()).isMute(1);
        }
    }// end of the manage score method
}// end of the start room class
