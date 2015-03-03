package com.game.crazygame.crazyteacher;

import android.graphics.Bitmap;

/**
 * Created by Danielfdzperez on 26/02/15.
 */
public class Enemy extends Player{


    private int time_shoot;
    private int current_time_shoot;
    private int max_speed;

    public Enemy(float x, float y, int width, int height, Bitmap bmp, int image_width, int image_height, int animation, int direction, float speed_x, float speed_y, int y_movement,
                 int x_min_margin, int x_max_margin, int y_min_margin, int y_max_margin){
        super(x, y, width, height, bmp, image_width, image_height, animation, direction, speed_x, speed_y, y_movement, x_min_margin, x_max_margin, y_min_margin, y_max_margin);

        this.time_shoot = 15;
        this.current_time_shoot = 1;
        this.max_speed = 30;
        this.setX_movement(2);
    }


    public void shoot(){

    }

    @Override
    public void stop(){
        this.getSpeed().setX(0);
        this.getSpeed().setY(0);
        this.getSprite().setDirection(0);
        this.getSprite().setCurrent_animation(1);
        this.setDirection(TDirection.SOUTH);
        this.setMoving(false);
    }

    public void move(Player player){
        if(this.getPosition().getX()+this.getX_movement() <= player.getPosition().getX())
            this.moveRight();
        else
            if(this.getPosition().getX()-this.getX_movement() >= player.getPosition().getX())
                this.moveLeft();
        else
                this.stop();
    }

    private boolean has_to_shoot(){
        boolean shoot = false;
        if(current_time_shoot % time_shoot == 0){
            current_time_shoot = 1;
            shoot = true;
        }
        else
            current_time_shoot ++;
        return shoot;
    }

    public void change_speed(int speed){
        if(speed <= this.max_speed)
            this.setX_movement(speed);
    }

    public void update(Player player){
        this.move(player);
        if(this.has_to_shoot())
            this.shoot();
        super.update();
    }
}


