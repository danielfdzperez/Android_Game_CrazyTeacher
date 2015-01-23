package com.game.crazygame.crazyteacher;

/**
 * Created by danielfdzperez on 16/01/15.
 */
public class Point {
    private float x;
    private float y;

    /**
     *
     * @param x
     * @param y
     */
    public Point(float x, float y){
        this.x = x;
        this.y = y;
    }

    //Gets and sets
    public float getX(){
        return this.x;
    }
    public float getY(){
        return this.y;
    }
    public void setX(float x) {
        this.x = x;
    }
    public void setY(float y) {
        this.y = y;
    }
}
