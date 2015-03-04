package com.game.crazygame.crazyteacher;

import android.graphics.Bitmap;

/**
 * Created by danielfdzperez on 3/03/15.
 */
public class Shoe extends Player{
    public Shoe(float x, float y, int width, int height, Bitmap bmp, int image_width, int image_height, int animation, int direction, float speed_x, float speed_y, int y_movement,
                int x_min_margin, int x_max_margin, int y_min_margin, int y_max_margin) {
        super(x, y, width, height, bmp, image_width, image_height, animation, direction, speed_x, speed_y, y_movement, x_min_margin, x_max_margin, y_min_margin, y_max_margin);
        this.setX_movement(0);
        this.setY_movement(16);
        this.moveDown();
    }

    public boolean screenOut(int y){
        return (this.getPosition().getY()+this.getHeight() > y);
    }

    public boolean canMove(TDirection direction){
        return true;
    }
}