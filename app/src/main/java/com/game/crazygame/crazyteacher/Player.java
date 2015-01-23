package com.game.crazygame.crazyteacher;

import android.graphics.Bitmap;

/**
 * Created by pc02 on 21/01/15.
 */
public class Player extends GameObject{

    private boolean moving;

    public Player(float x, float y, int width, int height, Bitmap bmp, int image_width, int image_height, int animation, int direction, float speedX, float speedY){
        super(x, y, width, height, bmp, image_width, image_height, animation, direction, speedX, speedY);
        this.moving = false;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public boolean isMoving() {
        return moving;
    }

    public void moveRight(){
        this.getSpeed().setX(10);
        this.getSprite().setDirection(2);
        //moving = true;
    }
    public void moveLeft(){
        this.getSpeed().setX(-10);
        this.getSprite().setDirection(1);
        //moving = true;
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
        this.getSprite().setAnimation(0);
        this.moving = false;
    }

    public void update(){
        if(this.isMoving())
            this.getSprite().nextAnimation();
        super.update();
    }
}
