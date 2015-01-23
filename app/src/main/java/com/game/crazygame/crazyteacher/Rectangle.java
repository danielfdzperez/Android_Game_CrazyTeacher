package com.game.crazygame.crazyteacher;

/**
 * Created by danielfdzperez on 16/01/15.
 */

/**
 * Class responsible for managing collisions
 */
public class Rectangle {
    private Point position;
    private int width;
    private int height;

    //Constructor
    public Rectangle(float x, float y, int width, int height){
        this.position = new Point(x, y);
        this.width = width;
        this.height = height;
    }

    //Gets and Sets
    public void setHeight(int height) {
        this.height = height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setPosition(float x, float y) {
        this.position.setX(x);
        this.position.setY(y);
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    /**
     *
     * @param rectangle
     * @return boolean
     */
    public boolean Collision(Rectangle rectangle){
        return (this.position.getX() <  rectangle.position.getX() + rectangle.getWidth() &&
                this.position.getX() + this.width > rectangle.position.getX() &&
                this.position.getY() < rectangle.position.getY() + rectangle.getHeight()&&
                this.position.getY() + this.height > rectangle.getHeight());
    }
}
