package com.teacoff.xplaneflappy.GameRooms;

import com.teacoff.xplaneflappy.GameActivity;
import com.teacoff.xplaneflappy.GameComponents.Background;
import com.teacoff.xplaneflappy.GameComponents.Bird;
import com.teacoff.xplaneflappy.GameComponents.Bonus;
import com.teacoff.xplaneflappy.GameComponents.Plane;
import com.teacoff.xplaneflappy.GameComponents.Rock;
import com.teacoff.xplaneflappy.GameComponents.Smoke;
import com.teacoff.xplaneflappy.GameView;
import com.teacoff.xplaneflappy.R;

import java.util.Random;

import ge.xordinate.xengine.GameObject;
import ge.xordinate.xengine.NumberDisplay;
import ge.xordinate.xengine.Room;
import ge.xordinate.xengine.SoundPlayer;

/**
 * GameRoom
 * <p>
 * Game play frame, all game passing in this room
 * This class also manage all game flow and acts as a game manager
 */
public class GameRoom extends Room{
    public static boolean chng;
    private final int tot_smok = 20;
    private final int NUM_ROCK = 3;
    public Plane plane;
    public SoundPlayer player;
    public int SPEED;
    public boolean diff; // true : hard -- false: easy
    private int SPACE;
    private int smokevalue;
    private int distance;
    private int score;
    private int lastscore, lastscoreb;
    private GameObject helper;
    private Rock[] topRocks;
    private Rock[] bottomRocks;
    private Background bg1;
    private long bonus1, bonus2, bonus3;
    private Bird birdx;
    private Bonus speedlowbonus, bonussize, bonusplay;
    private Boolean speedlowactivebonus, sizebonus_B, bonusplayactive_B, birdactive = false;
    private Background bg2;
    private NumberDisplay scoreDis;
    private Smoke[] smokesx;
    private int ding, ambiance, bonusound, enginesnd, birdsound, victory;
    private Random rand;
    private Boolean bonusesactive = false;

    public GameRoom(GameView container){
        super(container);
        ((GameActivity)getView().getContext()).started = true;
        helper = new GameObject(this);
        player = new SoundPlayer(getActivity().getApplicationContext());
        ding = player.newSound(R.raw.blow);
        victory = player.newSound(R.raw.victory);
        birdsound = player.newSound(R.raw.bird);
        enginesnd = player.newSound(R.raw.planengine);
        bonusound = player.newSound(R.raw.soundbonus);
        ambiance = player.newSound(R.raw.ambiance);
        rand = new Random();
        plane = new Plane(this);
        bg1 = new Background(this);
        bg2 = new Background(this);
        scoreDis = new NumberDisplay(this);
        topRocks = new Rock[NUM_ROCK];
        bottomRocks = new Rock[NUM_ROCK];
        birdx = new Bird(this);
        bonussize = new Bonus(this, 0);
        speedlowbonus = new Bonus(this, 1);
        bonusplay = new Bonus(this, 2);
        smokevalue = 0;
        smokesx = new Smoke[tot_smok];

        for(int i = 0; i<20; i++){
            smokesx[i] = new Smoke(this);

        }
        for(int i = 0; i<NUM_ROCK; i++){
            topRocks[i] = new Rock(this);
            bottomRocks[i] = new Rock(this);
        }
    }

    public void set(){
        player.loop(ambiance);
        player.loop(enginesnd);
        distance = 0;
        score = 0;
        bonus1 = 0;
        bonus2 = 0;
        bonus3 = 0;
        lastscore = 0;
        lastscoreb = 0;
        plane.set();
        bg1.set(getWidth() / 2, 4);
        bg2.set(getWidth() * 3 / 2, 4);

        plane.x = getWidth() / 2;

        if(diff){
            sethard();
        }
        else{

            seteasy();
        }
        smokevalue = 0;
        helper.visible = true;
        bonusesactive = false;
        sizebonus_B = false;
        bonusplayactive_B = false;
        speedlowactivebonus = false;
        birdactive = false;

        bonussize.set();
        bonussize.isActive = false;


        speedlowbonus.set();
        speedlowbonus.isActive = false;

        bonusplay.set();
        bonusplay.isActive = false;

        birdx.set();
        birdx.isActive = false;

        scoreDis.width = getWidth() / 12;
        scoreDis.height = scoreDis.width;
        scoreDis.x = getWidth() / 2;
        scoreDis.y = getHeight() - scoreDis.height;
        scoreDis.setNumber(score);
        scoreDis.setAlignment(1);

        helper.x = getWidth() / 2;
        helper.y = getHeight() / 6;
        helper.width = getWidth() / 2;
        helper.height = helper.width / 2;
        helper.setGraphic(GameView.Help, 1);

        chng = true;

        for(int i = 0; i<NUM_ROCK; i++){
            topRocks[i].set(true);
            bottomRocks[i].set(false);
        }

        for(int i = 0; i<20; i++){
            smokesx[i].x = 0 - getWidth() * 2;
            smokesx[i].set();
        }
        for(int i = 0; i<NUM_ROCK; i++){
            if(i>0){
                topRocks[i].x = topRocks[i - 1].x + getWidth();
                bottomRocks[i].x = bottomRocks[i - 1].x + getWidth();
            }


            // Uzunluk
            int offset = rand.nextInt(4);
            offset = offset + 5;
            offset = getHeight() / offset;
            if(offset % 2 == 0){

                topRocks[i].height = getHeight() / 3 + offset;
                topRocks[i].y = getHeight() - topRocks[i].height / 2;

                bottomRocks[i].height = getHeight() / 3;
                bottomRocks[i].y = 0 + bottomRocks[i].height / 2;


                if(topRocks[i].height / 2>getHeight() / 6){
                    topRocks[i].width = topRocks[i].height / 2;
                    bottomRocks[i].width = topRocks[i].height / 2;
                    ;
                }
                else{
                    topRocks[i].width = topRocks[i].height / 6;
                    bottomRocks[i].width = topRocks[i].height / 6;
                }

            }
            else{

                topRocks[i].height = getHeight() / 3;
                topRocks[i].y = getHeight() - topRocks[i].height / 2;


                bottomRocks[i].height = getHeight() / 3 + offset;
                bottomRocks[i].y = 0 + bottomRocks[i].height / 2;

                if(bottomRocks[i].height / 2>getHeight() / 6){
                    topRocks[i].width = bottomRocks[i].height / 2;
                    bottomRocks[i].width = bottomRocks[i].height / 2;
                }
                else{
                    topRocks[i].width = bottomRocks[i].height / 6;
                    bottomRocks[i].width = bottomRocks[i].height / 6;
                }
            }  // Uzunluk
        }
    }

    public void seteasy(){
        SPEED = 12;
        SPACE = 100;
        plane.jumpingVelocity = 7;
        plane.ACC = .4;
        diff = false;
        plane.gameplay_B = true;
    }

    public void sethard(){
        SPEED = 12;
        SPACE = 100;
        plane.jumpingVelocity = 7;
        plane.ACC = .4;
        diff = true;
        plane.gameplay_B = false;
    }

    @Override
    public void step(double dt){

        if(plane.isPressed){

            // smoke
            for(int i = 0; i<tot_smok; i++){

                smokesx[smokevalue].x = plane.x - plane.x / 4;
                smokesx[smokevalue].y = plane.y;
                smokesx[smokevalue].angle = plane.angle;

                smokesx[smokevalue].height = rand.nextInt(getHeight() / 20);
                smokesx[smokevalue].height = smokesx[smokevalue].height + getHeight() / 20;
                smokesx[smokevalue].width = smokesx[smokevalue].height;
                smokesx[i].x = smokesx[i].x - 21;
            }
            smokevalue++;
            if(smokevalue == tot_smok){

                smokevalue = 0;
            }

            helper.visible = false;
            distance++;
            scoreDis.setNumber(score);

            for(int i = 0; i<NUM_ROCK; i++){
                if(topRocks[i].x<0 - topRocks[i].width || bottomRocks[i].x<0 - bottomRocks[i].width){
                    if(i == 0){
                        topRocks[i].x = topRocks[NUM_ROCK - 1].x + getWidth();
                        bottomRocks[i].x = bottomRocks[NUM_ROCK - 1].x + getWidth();

                    }
                    else{
                        topRocks[i].x = topRocks[i - 1].x + getWidth();
                        bottomRocks[i].x = bottomRocks[i - 1].x + getWidth();
                    }
                    // Uzunluk
                    int offset = rand.nextInt(4);
                    offset = offset + 5;
                    offset = getHeight() / offset;
                    if(offset % 2 == 0){

                        topRocks[i].height = getHeight() / 3 + offset;
                        topRocks[i].y = getHeight() - topRocks[i].height / 2;

                        bottomRocks[i].height = getHeight() / 3;
                        bottomRocks[i].y = 0 + bottomRocks[i].height / 2;


                        if(topRocks[i].height / 2>getHeight() / 6){
                            topRocks[i].width = topRocks[i].height / 2;
                            bottomRocks[i].width = topRocks[i].height / 2;
                            ;
                        }
                        else{
                            topRocks[i].width = topRocks[i].height / 6;
                            bottomRocks[i].width = topRocks[i].height / 6;
                        }

                    }
                    else{

                        topRocks[i].height = getHeight() / 3;
                        topRocks[i].y = getHeight() - topRocks[i].height / 2;


                        bottomRocks[i].height = getHeight() / 3 + offset;
                        bottomRocks[i].y = 0 + bottomRocks[i].height / 2;

                        if(bottomRocks[i].height / 2>getHeight() / 6){
                            topRocks[i].width = bottomRocks[i].height / 2;
                            bottomRocks[i].width = bottomRocks[i].height / 2;
                        }
                        else{
                            topRocks[i].width = bottomRocks[i].height / 6;
                            bottomRocks[i].width = bottomRocks[i].height / 6;
                        }
                    }  // Uzunluk

                }// previous if


            } // for


            //Bird

            if(checkCollision(plane, birdx)){
                birdactive = true;
                player.play(birdsound);
                bonusesactive = true;
                GameView.gameOver.set();
                getView().goToRoom(GameView.gameOver);

            }


            // Bonus

            if(checkCollision(plane, bonusplay)){
                bonusplayactive_B = true;
                player.play(bonusound);

                if(diff){
                    plane.gameplay_B = true;
                }
                else{
                    plane.gameplay_B = false;
                }
                score++;
                lastscore = score;

                bonusesactive = true;

            }

            if(checkCollision(plane, bonussize)){
                sizebonus_B = true;
                player.play(bonusound);


                plane.width = getWidth() / 10;
                plane.height = plane.width / 2;
                score++;
                lastscoreb = score;

                bonusesactive = true;

            }

            if(checkCollision(plane, speedlowbonus)){
                speedlowactivebonus = true;
                player.play(bonusound);

                SPEED = 6;
                SPACE = 180;
                plane.jumpingVelocity = 3;
                plane.ACC = .5;
                score++;
                lastscore = score;

                bonusesactive = true;

            }
            // Bonus

            // Bonus

            if(score>4 && score % 4 == 0 && birdx.isActive == false){
                if(birdx.x<0 || birdx.x>getWidth()){
                    birdx.set();
                    birdx.isActive = true;

                }

            }

            if(System.currentTimeMillis() - bonus3 >= 15000 && rand.nextInt(450) == 4 && bonusplay.isActive == false){
                if(bonusplay.x<0 || bonusplay.x>getWidth()){
                    bonus3 = System.currentTimeMillis();
                    bonusplay.set();
                    bonusplay.isActive = true;

                }

            }

            if(System.currentTimeMillis() - bonus2 >= 15000 && rand.nextInt(450) == 5 && bonussize.isActive == false || !bonusesactive){
                if(speedlowbonus.x<0 || bonussize.x>getWidth()){
                    bonussize.set();
                    bonus2 = System.currentTimeMillis();
                    bonussize.isActive = true;

                }

            }


            if(System.currentTimeMillis() - bonus1 >= 15000 && rand.nextInt(450) == 1 && speedlowbonus.isActive == false){
                if(speedlowbonus.x<0 || speedlowbonus.x>getWidth()){
                    bonus1 = System.currentTimeMillis();
                    speedlowbonus.set();
                    speedlowbonus.isActive = true;

                }

            }

            if(birdactive == true && birdx.x<0 - birdx.width){

                birdx.isActive = false;
                birdactive = false;
                bonusesactive = false;

            }

            if(bonusplayactive_B == true){
                bonusplay.set();
                bonusplay.isActive = true;
                if(score - lastscore >= 4){
                    bonusplayactive_B = false;
                    if(diff){
                        sethard();
                    }
                    else{
                        seteasy();
                    }

                    bonusesactive = false;
                }
            }

            if(speedlowactivebonus == true){
                speedlowbonus.set();
                speedlowbonus.isActive = true;
                if(score - lastscore >= 4){
                    speedlowactivebonus = false;
                    if(diff){
                        sethard();
                    }
                    else{
                        seteasy();
                    }

                    bonusesactive = false;
                }
            }

            if(sizebonus_B == true){
                bonussize.set();
                bonussize.isActive = true;
                if(score - lastscoreb >= 4){
                    sizebonus_B = false;
                    plane.width = getWidth() / 5;
                    plane.height = plane.width / 2;

                    bonusesactive = false;

                }
            }
        }

        if(score == 99){

            player.play(victory);

            GameView.gameOver.set();
            getView().goToRoom(GameView.gameOver);
        }
        for(int i = 0; i<NUM_ROCK; i++){

            if(checkCollision(plane, topRocks[i]) || checkCollision(plane, bottomRocks[i])){
                player.play(ding);

                GameView.gameOver.set();
                getView().goToRoom(GameView.gameOver);
            }
        }
    }


    public void incrementScore(){
        score++;
    }

    public int getScore(){
        return score;
    }
}

