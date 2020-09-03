package com.teacoff.xplaneflappy.GameComponents;

import com.teacoff.xplaneflappy.GameView;
import com.teacoff.xplaneflappy.R;

import java.util.Random;

import ge.xordinate.xengine.GameObject;
import ge.xordinate.xengine.Room;
import ge.xordinate.xengine.SoundPlayer;

/**
 * Bonus
 * <p>
 * Bonus effects player object's properties and acts
 * This class keep bonus object properties like that
 * bonus type, object position etc.
 * </p>
 */
public class Bonus extends GameObject{
    // Variables
    public boolean isActive = false;        // Flag for object status isActive or passive;

    private Random rand = new Random();     // Initialize random number
    private boolean isPassed;               // Flag for screen x positon border
    private int bonusType = 0;              // Keep type of bonus

    private SoundPlayer player;             // Sound player
    private int ding;                       // Bonus activity sound

    /**
     * Bonus Constructor
     *
     * @param room       game room
     * @param _bonusType type of the bonus
     */
    public Bonus(Room room, int _bonusType){
        super(room);
        bonusType = _bonusType;

        // Set sound player
        player = new SoundPlayer(getActivity().getApplicationContext());
        ding = player.newSound(R.raw.ding);

        // Set object texture as bonus type
        if(_bonusType == 0){
            setGraphic(GameView.BonusIcon, 1);
        }
        else{
            if(_bonusType == 1){
                setGraphic(GameView.Xspeedlow, 1);
            }
            else{
                if(_bonusType == 2){
                    setGraphic(GameView.BonusIcon2, 1);
                }
            }
        }

        // Set collision box
        giveCollisionBox(0, 0, 1, 1);
    }

    /**
     * Set game object
     */
    public void set(){
        height = getRoom().getHeight() / 20;
        width = height;
        x = getRoom().getWidth() * 10 / 4;
        y = getRoom().getHeight() / 2;
        isPassed = true;
        isActive = false;
    }

    /**
     * Update game object
     */
    @Override
    public void step(double dt){
        if(isActive){
            // Decrease x position
            x = x - 8;

            // Set angle of the object
            angle = angle + 1;
            if(angle >= 360){
                angle = 0;
            }

            // If object passed away x border than set isActive as false
            if(x<-100){
                isActive = false;
            }

            if(x<getRoom().getWidth() / 2 && !isPassed && angle == 0){
                isPassed = true;
            }
        }
    }// end of the step
}// end of the bonus class
