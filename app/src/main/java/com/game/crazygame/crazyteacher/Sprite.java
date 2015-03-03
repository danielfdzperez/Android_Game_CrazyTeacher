package com.game.crazygame.crazyteacher;

import android.graphics.Bitmap;

/**
 * Created by danielfdzperez on 19/01/15.
 */
public class Sprite {
    private Bitmap bmp;
    private int animation;//Cols
    private int current_animation;//Current Cols
    private int direction;//Row
    private int width;
    private int height;

    public Sprite(Bitmap bmp, int animation, int direction, int image_width, int image_height){
        this.bmp = bmp;
        this.animation = animation;
        this.current_animation = 0;
        this.direction = 0;
        this.width = image_width;
        this.height = image_height;
        this.direction = direction;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Bitmap getBmp(){
        return this.bmp;
    }

    public int getDirection() {
        return direction;
    }

    public int getAnimation() {
        return animation;
    }

    public void setAnimation(int animation) {
        this.animation = animation;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public void nextAnimation(){
        this.current_animation++;
        //Posible mejora -> ++this.current_animation or this.current_animation++ in the if
        if(this.current_animation > this.animation)
            this.current_animation = 0;
    }

    public int getCurrent_animation() {
        return current_animation;
    }

    public void setCurrent_animation(int current_animation) {
        this.current_animation = current_animation;
    }
}
