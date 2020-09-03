package com.xordinate.xplaneflappy.GameComponents;

import com.xordinate.xplaneflappy.GameView;

import ge.xordinate.xengine.GameObject;
import ge.xordinate.xengine.Room;

/**
 * Background
 * <p>
 * Game background object
 * keep background information like that background image,
 * background moving speed and position in pixels
 * </p>
 */
public class Background extends GameObject{

    private int speed;  // Background speed

    public Background(Room room){
        super(room);
        layer = 0;
        setGraphic(GameView.bg, 1);
    }

    public void set(int x, int speed){
        this.x = x;
        this.speed = 3;

        width = getRoom().getWidth() * 2;
        height = getRoom().getHeight();
        y = height / 2;
    }

    @Override
    public void step(double dt){
        x -= speed;

        if(x<=-width / 2){
            x = getRoom().getWidth() + width / 2 - (x + width / 2);
        }
    }
}
