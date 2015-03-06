package com.game.crazygame.crazyteacher;

import android.graphics.Bitmap;

/**
 * Created by Danielfdzperez on 21/01/15.
 */


public class Player extends GameObject{
    public enum TDirection{
        NORTH, SOUTH, WEST, EAST;
    }

    private boolean moving;
    private int x_movement;
    private int y_movement;
    private TDirection direction;

    private Point initial_position;
    private TDirection initial_direction;
    private int initial_sprite_direction;

    private int x_min_margin;
    private int x_max_margin;
    private int y_min_margin;
    private int y_max_margin;

    public Player(float x, float y, int width, int height, Bitmap bmp, int image_width, int image_height, int animation, int direction, float speed_x, float speed_y, int y_movement,
                  int x_min_margin, int x_max_margin, int y_min_margin, int y_max_margin){
        super(x, y, width, height, bmp, image_width, image_height, animation, direction, speed_x, speed_y);
        this.initial_position = new Point(x,y);
        this.moving = false;
        this.x_movement = 10;
        this.y_movement = y_movement;
        this.initial_sprite_direction = direction;
        this.initial_direction = this.direction = Player.TDirection.NORTH;
        this.x_min_margin = x_min_margin;
        this.x_max_margin = x_max_margin;
        this.y_min_margin = y_min_margin;
        this.y_max_margin = y_max_margin;
    }

    public int getY_movement() {
        return y_movement;
    }

    public void setY_movement(int y_movement) {
        this.y_movement = y_movement;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public boolean isMoving() {
        return moving;
    }

    public int getX_movement() {
        return x_movement;
    }

    public void setX_movement(int x_movement) {
        this.x_movement = x_movement;
    }

    public void moveRight(){
        this.getSpeed().setX(x_movement);
        this.getSprite().setDirection(2);
        this.direction = TDirection.EAST;
        moving = true;
    }
    public void moveLeft(){
        this.getSpeed().setX(-x_movement);
        this.getSprite().setDirection(1);
        this.direction = TDirection.WEST;
        moving = true;
    }
    public void moveUp(){
        this.getSpeed().setY((-y_movement));
        this.getSprite().setDirection(3);
        this.direction = TDirection.NORTH;
        moving = true;
    }
    public void moveDown(){
        this.getSpeed().setY(y_movement);
        this.getSprite().setDirection(0);
        this.direction = TDirection.SOUTH;
        moving = true;
    }
    public void stop(){
        this.getSpeed().setX(0);
        this.getSpeed().setY(0);
        this.getSprite().setDirection(3);
        this.getSprite().setCurrent_animation(1);
        this.direction = TDirection.NORTH;
        this.moving = false;
    }

    public void restart(){
        this.getPosition().setX(this.initial_position.getX());
        this.getPosition().setY(this.initial_position.getY());
        this.direction = this.initial_direction;
        this.moving = false;
        this.getSprite().setDirection(this.initial_sprite_direction);
    }

    public void setDirection(TDirection direction) {
        this.direction = direction;
    }

    public TDirection getDirection() {
        return direction;
    }

    public boolean canMove(TDirection direction){
        boolean move = false;
        switch (direction) {
            case SOUTH:
                if (this.getPosition().getY() + this.y_movement < this.y_max_margin)
                    move = true;
                break;
            case NORTH:
                if((this.getPosition().getY() - this.y_movement) > this.y_min_margin)
                    move = true;
                break;
            case EAST:
                if((this.getPosition().getX()+this.x_movement) < this.x_max_margin)
                    move = true;
                break;
            case WEST:
                if((this.getPosition().getX()-this.x_movement) > this.x_min_margin)
                    move = true;
                break;
        }
        return move;
    }

    public void update(){
        if(this.isMoving())
            this.getSprite().nextAnimation();
        if(this.canMove(this.direction))
            super.update();
    }
}
