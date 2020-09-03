package com.teacoff.xplaneflappy.GameRooms;

import com.teacoff.xplaneflappy.GameComponents.Background;
import com.teacoff.xplaneflappy.GameView;

import ge.xordinate.xengine.EngineView;
import ge.xordinate.xengine.GameObject;
import ge.xordinate.xengine.Room;

/**
 * Difficulty Room
 *
 * Game difficulty choosing menu - User interface
 *
 */
public class DifficultyRoom extends Room{

    // Objects
    private GameObject Easy;
    private GameObject title;
    private GameObject Hard;
    private Background bg1;
    private Background bg2;

    /**
     * Difficulty Room
     *
     * Initialize difficulty room variables
     *
     * @param container Engine view
     */
    public DifficultyRoom(EngineView container){
        super(container);
        title = new GameObject(this);
        Easy = new GameObject(this);
        Hard = new GameObject(this);
        bg1 = new Background(this);
        bg2 = new Background(this);
    }

    public void set(){
        title.x = getWidth() / 2;
        title.y = getHeight() * 3 / 4;
        title.width = getWidth() * 3 / 4;
        title.height = title.width / 2;
        title.setGraphic(GameView.title, 1);

        Easy.x = getWidth() / 2;
        Easy.y = (getHeight() / 2);
        Easy.width = getWidth() / 3;
        Easy.height = Easy.width / 2;
        Easy.setGraphic(GameView.easy, 2);

        Hard.x = getWidth() / 2;
        Hard.y = getHeight() * 1 / 4;
        Hard.width = getWidth() / 3;
        Hard.height = Hard.width / 2;
        Hard.setGraphic(GameView.hard, 2);

        bg1.set(getWidth() / 2, 4);
        bg2.set((int)bg1.x + getWidth(), 4);
    }

    /**
     * Step event happens every frame
     *
     * @param dt
     */
    @Override
    public void step(double dt){
        if(getTouch().objectTouched(Hard)){
            Hard.frame = 1;
        }
        else{
            Hard.frame = 0;
        }

        if(getTouch().objectTouched(Easy)){
            Easy.frame = 1;
        }
        else{
            Easy.frame = 0;
        }
    }

    public void released(int index){
        if(getTouch().objectTouched(Hard)){
            GameView.game.diff = true;
            GameView.game.set();
            GameView.game.sethard();
            getView().goToRoom(GameView.game);
        }

        if(getTouch().objectTouched(Easy)){
            GameView.game.diff = false;
            GameView.game.set();
            GameView.game.seteasy();
            getView().goToRoom(GameView.game);
        }
    }
}// end of the class
