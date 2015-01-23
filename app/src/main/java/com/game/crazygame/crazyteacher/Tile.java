package com.game.crazygame.crazyteacher;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Created by danielfdzperez on 19/01/15.
 */

public class Tile{

    private boolean walkable;
    private Bitmap image;

    public Tile(Bitmap bmp, boolean walkable){
        this.walkable = walkable;
        this.image = bmp;
    }

    public void onDraw(Canvas canvas, int x, int y, int width, int height){
        int img_width = this.image.getWidth() < width ? width : this.image.getWidth();
        int img_height = this.image.getHeight() < height ? height :  this.image.getHeight();

        x *= this.image.getWidth() < width ? (width-6) :  width;
        y *= this.image.getHeight() < height ? (height-6) :  height;

        Rect src = new Rect(0, 0, img_width, img_height);
        Rect destination = new Rect(x, y, (x+width), (y+height));
        canvas.drawBitmap(this.image, src, destination, null);
    }

    public boolean isWalkable() {
        return walkable;
    }

    public Bitmap getImage() {
        return image;
    }
}