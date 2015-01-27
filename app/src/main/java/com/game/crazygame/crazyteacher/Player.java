package com.game.crazygame.crazyteacher;

import android.graphics.Bitmap;
import android.util.Log;

/**
 * Created by pc02 on 21/01/15.
 */


public class Player extends GameObject{
    public enum TDirection{
        NORTH, SOUTH, WEST, EAST;
    }

    private boolean moving;
    private int movement;
    private TDirection direction;

    public Player(float x, float y, int width, int height, Bitmap bmp, int image_width, int image_height, int animation, int direction, float speedX, float speedY){
        super(x, y, width, height, bmp, image_width, image_height, animation, direction, speedX, speedY);
        this.moving = false;
        this.movement = 10;
        this.direction = Player.TDirection.NORTH;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public boolean isMoving() {
        return moving;
    }

    public void moveRight(){
        //Log.d("PARO", "Entrado en right");
        this.getSpeed().setX(movement);
        this.getSprite().setDirection(2);
        moving = true;
    }
    public void moveLeft(){
        Log.d("IZAq", "izq");
        this.getSpeed().setX(-movement);
        this.getSprite().setDirection(1);
        moving = true;
    }
    public void moveUp(int mv){
        this.getSpeed().setY((mv*-1));
        this.getSprite().setDirection(2);
        moving = true;
    }
    public void moveDown(int mv){
        this.getSpeed().setY(mv);
        this.getSprite().setDirection(0);
        moving = true;
    }
    public void stop(){
        this.getSpeed().setX(0);
        this.getSpeed().setY(0);
        this.getSprite().setDirection(3);
        this.getSprite().setCurrent_animation(0);
       // Log.d("PARO", "Entrado en stop");
        this.moving = false;
    }

    public boolean canMove(int measurement, int y_speed, TDirection direction){
        boolean move = false;
        switch (direction) {
            case NORTH:
                if (this.getPosition().getY() + y_speed < measurement)
                    move = true;
                break;
            case SOUTH:
                if((this.getPosition().getY() - y_speed) > measurement)
                    move = true;
                break;
            case EAST:
                if((this.getPosition().getX()+this.movement) < measurement)
                    move = true;
                break;
            case WEST:
                if((this.getPosition().getX()-this.movement) > measurement)
                    move = true;
                break;
        }
        return move;
    }

    public void update(){
        if(this.isMoving())
            this.getSprite().nextAnimation();
        super.update();
    }
}
