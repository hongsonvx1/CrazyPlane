package com.teacoff.xplaneflappy.GameComponents;

import com.teacoff.xplaneflappy.GameView;

import java.util.Random;

import ge.xordinate.xengine.GameObject;
import ge.xordinate.xengine.Room;

/**
 * Bird
 * <p>
 * Bird is a obstacle object in the game
 * This class keep object properties
 * </p>
 */
public class Bird extends GameObject{

    public boolean isActive = false;        // Flag for object status isActive or passive
    private long time;                      // Keep time of the obstacle
    private Random rand = new Random();     // Generate random number
    private boolean isPassed;               // Flag for screen x positon border
    private boolean border = false;         // Flag for screen y positon border
    private boolean mode = false;           // Set y position as mode

    public Bird(Room room){
        super(room);
        setGraphic(GameView.Bird1, 1);
        giveCollisionBox(0, 0, 1, 1);
    }

    /**
     * Set bird object
     */
    public void set(){
        time = System.currentTimeMillis(); // Get system current time

        // Set height and width of the bird object
        height = getRoom().getHeight() / 21;
        width = height;

        // Set x position of the bird
        x = getRoom().getWidth() * 5 / 2 + width * 2;
        if(mode){
            // Set y position of the bird
            y = getRoom().getHeight() / 4;
            mode = false;
        }
        else{
            y = getRoom().getHeight() - (getRoom().getHeight() / 4);
            mode = true;
        }
        isPassed = true;
        isActive = false;
        border = false;
        rand = new Random();
    }

    /**
     * Handle object position and other updates
     */
    @Override
    public void step(double dt){
        // Change bird texture to simulate flying
        if(isActive){
            if(System.currentTimeMillis() - time >= 100){
                if(mode){
                    mode = false;
                    setGraphic(GameView.Bird1, 1);
                }
                else{
                    mode = true;
                    setGraphic(GameView.Bird2, 1);
                }
                time = System.currentTimeMillis();
            }

            // Decrease bird x position
            x = x - 12;

            // Check object y position
            if(y<0){
                border = false;
            }
            if(y>getRoom().getHeight()){
                border = true;
            }

            // Move y position as border
            if(border){
                y = y - rand.nextInt(8);
            }
            else{
                y = y + rand.nextInt(8);
            }

            // Object angle increase or decrease
            if(angle % 2 == 0){
                angle = angle + 1;
            }
            else{
                angle = angle - 1;
            }

            // Check angle and set 0 if bird object angle bigger than 360
            if(angle >= 360){
                angle = 0;
            }

            // If bird passed away x border than set as false
            if(x<-100){
                isActive = false;
            }
        }
    }
}// end of the Bird class
