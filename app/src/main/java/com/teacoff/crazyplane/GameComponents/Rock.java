package com.teacoff.crazyplane.GameComponents;

import com.teacoff.crazyplane.GameRooms.GameRoom;
import com.teacoff.crazyplane.GameView;
import com.teacoff.crazyplane.R;

import ge.xordinate.xengine.GameObject;
import ge.xordinate.xengine.Room;
import ge.xordinate.xengine.SoundPlayer;

/**
 * Rock
 * <p>
 * Game obstacle
 * </p>
 */
public class Rock extends GameObject{
    // Variables
    private boolean passed;
    private SoundPlayer player;
    private int ding;

    public Rock(Room room){
        super(room);

        // Set sound
        player = new SoundPlayer(getActivity().getApplicationContext());
        ding = player.newSound(R.raw.ding);

        // Set object graphic texture
        setGraphic(GameView.flower, 1);

        // Set object collision box
        giveCollisionBox(0, 0, 1, 1);
    }

    public void set(boolean isTop){
        height = getRoom().getHeight() / 3;
        width = height / 2;
        x = getRoom().getWidth() * 2;

        passed = true;

        if(isTop){
            y = getRoom().getHeight() - height / 2;
            angle = 180;
        }
        else{
            y = 0 - height / 2;
            angle = 0;
        }
    }

    @Override
    public void step(double dt){
        if(((GameRoom)getRoom()).plane.isPressed){
            x -= ((GameRoom)getRoom()).SPEED;
        }

        // Check player's plane and increase score
        if(x<getRoom().getWidth() * 1 / 4 && !passed && angle == 0){
            ((GameRoom)getRoom()).incrementScore();
            passed = true;      // if obstacle is passed then set as passed
            player.play(ding);  // Play the sound effect
        }

        // Back on the right, can be passed again.
        if(x>getRoom().getWidth()){
            passed = false;
        }
    }
}
