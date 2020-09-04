package com.teacoff.crazyplane.GameRooms;

import android.content.SharedPreferences;

import com.teacoff.crazyplane.GameComponents.Background;
import com.teacoff.crazyplane.GameView;
import com.teacoff.crazyplane.OnGameOverListener;

import ge.xordinate.xengine.EngineView;
import ge.xordinate.xengine.GameObject;
import ge.xordinate.xengine.NumberDisplay;
import ge.xordinate.xengine.Room;

/**
 * Game Over Room
 * <p>
 * Game over menu for end of the game, player can see game scores - user interface
 */
public class GameOverRoom extends Room{
    // Variables
    public static final String score_save_name = "score_save";
    public static final String best_score_key = "score";

    // Objects
    public GameObject play;
    public EngineView xxcon;

    private Background bg1;
    private Background bg2;
    private NumberDisplay score;
    private GameObject gameOver;
    private GameObject goScore;
    private OnGameOverListener onGameOverListener;

    /**
     * GameOverRoom
     * <p>
     * Prepare game room, initialize objects
     *
     * @param container Engine View
     */
    public GameOverRoom(EngineView container){
        super(container);
        xxcon = container;
        play = new GameObject(this);
        bg1 = new Background(this);
        bg2 = new Background(this);
        score = new NumberDisplay(this);
        gameOver = new GameObject(this);
        goScore = new GameObject(this);

        int score = 0;
    }

    /**
     * Set and initialize game objects and variables
     */
    public void set(){
        play.x = getWidth() * 3 / 4;
        play.y = getHeight() / 4;
        play.width = getWidth() / 4;
        play.height = play.width / 2;
        play.setGraphic(GameView.play, 2);

        goScore.x = getWidth() * 1 / 4;
        goScore.y = getHeight() / 4;
        goScore.width = getWidth() / 4;
        goScore.height = play.width / 2;
        goScore.setGraphic(GameView.score, 2);

        bg1.set(getWidth() / 2, 4);
        bg2.set(getWidth() * 3 / 2, 4);

        score.setNumber(GameView.game.getScore());
        score.y = getHeight() / 2;
        score.width = getWidth() / 5;
        score.height = score.width;
        score.x = getWidth() / 2;
        score.setAlignment(1);
        play.frame = 0;
        goScore.frame = 0;
        manageScore();

        if(GameView.game.getScore() == 99){
            gameOver.x = getWidth() / 2;
            gameOver.y = getHeight() * 5 / 6;
            gameOver.width = getWidth() / 2;
            gameOver.height = gameOver.width;
            gameOver.setGraphic(GameView.victory, 1);
        }
        else{
            gameOver.x = getWidth() / 2;
            gameOver.y = getHeight() * 5 / 6;
            gameOver.width = getWidth() / 2;
            gameOver.height = gameOver.width;
            gameOver.setGraphic(GameView.over, 1);
//            onGameOverListener.onGameOver();
        }

    }

//    public GameOverRoom setOnGameOverListener(OnGameOverListener onGameOverListener) {
//        this.onGameOverListener = onGameOverListener;
//        return this;
//    }

    private void manageScore(){

        SharedPreferences saves = this.getActivity().getSharedPreferences(score_save_name, 0);
        int oldPoints = saves.getInt(best_score_key, 0);
        if(GameView.game.getScore()>oldPoints){
            SharedPreferences.Editor editor = saves.edit();
            editor.putInt(best_score_key, GameView.game.getScore());

            editor.commit();
        }

    }

    @Override
    public void step(double dt){
        if(getTouch().objectTouched(play)){
            play.frame = 1;
        }
        else{
            play.frame = 0;
        }

        if(getTouch().objectTouched(goScore)){
            goScore.frame = 1;
        }
        else{
            goScore.frame = 0;
        }
    }

    public void released(int index){
        super.released(index);

        if(getTouch().objectTouched(play)){        // Play button touched
            GameView.difficulty.set();                     // Set up the game room.
            getView().goToRoom(GameView.difficulty);       // Go to the game room.
            play.frame = 0;
        }

        if(getTouch().objectTouched(goScore)){
            GameView.scoreroom.set();
            getView().goToRoom(GameView.scoreroom);
            goScore.frame = 0;
        }
    }
}
