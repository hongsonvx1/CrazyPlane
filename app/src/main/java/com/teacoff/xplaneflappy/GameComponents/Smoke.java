package com.teacoff.xplaneflappy.GameComponents;

import com.teacoff.xplaneflappy.GameView;

import ge.xordinate.xengine.GameObject;
import ge.xordinate.xengine.Room;

/**
 * Smoke
 * <p>
 * Smoke object keeps particles of the plane smoke properties
 * </p>
 */
public class Smoke extends GameObject{

    private boolean isActive = false;

    public Smoke(Room room){
        super(room);
        setGraphic(GameView.smoke, 1);
        isActive = false;
    }

    public void set(){
        isActive = false;
        height = getRoom().getHeight() / 40;
        width = height;
    }

    @Override
    public void step(double dt){
    }
}
