package com.teacoff.crazyplane.GameComponents;

import com.teacoff.crazyplane.GameView;
import com.teacoff.crazyplane.R;

import ge.xordinate.xengine.GameObject;
import ge.xordinate.xengine.Graphic;
import ge.xordinate.xengine.Room;
import ge.xordinate.xengine.SoundPlayer;

/**
 * Plane
 * <p>
 * This object is controlled by player and it's main object of the game.
 * </p>
 */
public class Plane extends GameObject{
    public static Graphic planeGraphic;     // Player object texture
    public boolean isPressed;               // Player controller - touch
    public boolean mode;
    public boolean gameplay_B;
    public double ACC = .7;         // Angle
    public int jumpingVelocity = 4; // Moving velocity
    private double vy;              // Velocity
    private boolean isRotated;      // Flag for keeping plane object angle changing status
    private SoundPlayer player;     // Player sound
    private long time;              // Time for checking passed time
    private int ding;

    /**
     * Initialize plane
     *
     * @param room
     */
    public Plane(Room room){
        super(room);
        planeGraphic = getView().getGraphicsHelper().addGraphic(R.drawable.arrow2);
        player = new SoundPlayer(getActivity().getApplicationContext()); // Initialize the sound player
        ding = player.newSound(R.raw.blow);

        setGraphic(GameView.Ufo, 1);
        giveCollisionBox(.25, .25, .75, .75);
    }

    public void set(){
        angle = 0;

        time = System.currentTimeMillis();
        mode = true;
        setGraphic(GameView.Ufo, 1);
        isRotated = true;
        isPressed = false;
        x = getRoom().getWidth() * 1 / 4;
        y = getRoom().getHeight() / 2;
        width = getRoom().getWidth() / 5;
        height = width / 2;
        vy = 0;
        layer = 3;
    }

    /**
     * Step
     * <p>
     * Update game object
     * </p>
     * @param dt Delta time
     */
    @Override
    public void step(double dt){
        // Check the x position of the plane and move it
        if(x>getRoom().getWidth() * 1 / 4){
            x = x - 2;
        }

        // Listen and check player controller then update plane properties
        if(getTouch().held() && gameplay_B){
            // Check bonus
            if(!isPressed){
                isPressed = true;
            }

            // Rotate plane object
            isRotated = true;
            if(isRotated){
                vy -= ACC;
                y += vy * 1 / 5;
            }
            else{
                vy -= ACC;
                y -= vy * 9 / 20;
            }

            // Move y coordinate
            if(y + jumpingVelocity<getRoom().getHeight()){
                vy = jumpingVelocity;
            }
            if(y + jumpingVelocity<0){
                vy = jumpingVelocity;
            }
        }

        // Check rotation
        if(isRotated){
            angle = vy * 3 / 4;
        }
        else{
            angle = 360 - vy * 3 / 4;
        }

        // Update frame rate and plane texture
        if(System.currentTimeMillis() - time >= 100){
            if(mode){
                mode = false;
                planeGraphic = getView().getGraphicsHelper().addGraphic(R.drawable.arrow2);
                setGraphic(planeGraphic, 1);
            }
            else{
                mode = true;
                setGraphic(GameView.Ufo, 1);
            }
            time = System.currentTimeMillis();
        }

        if(isPressed){
            if(isRotated){
                vy -= ACC;
                y += vy / 2;
            }
            else{
                vy -= ACC;
                y -= vy / 2;
            }
        }

        if(y<height / 2){
            player.play(ding);
            GameView.gameOver.set();
            getView().goToRoom(GameView.gameOver);
        }
        if(y>getRoom().getHeight()){
            player.play(ding);
            GameView.gameOver.set();
            getView().goToRoom(GameView.gameOver);
        }
    }

    /**
     * Check user input - touch
     */
    @Override
    public void newpress(int index){

        if(!gameplay_B){
            if(!isPressed){
                isPressed = true;
            }
            if(isRotated){
                isRotated = false;
            }
            else{
                isRotated = true;
            }

            if(y + jumpingVelocity<getRoom().getHeight()){
                vy = jumpingVelocity;
            }
            if(y + jumpingVelocity<0){
                vy = jumpingVelocity;
            }
        }
    }

    public void released(int index){
        super.released(index);
    }
}

