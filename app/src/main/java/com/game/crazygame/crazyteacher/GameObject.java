package com.game.crazygame.crazyteacher;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Created by danielfdzperez on 19/01/15.
 */

public class GameObject {
    private Point position;
    private Rect collision_detector;
    private Sprite sprite;
    private Point speed;

    private int width;
    private int height;

    public GameObject(float x, float y, int width, int height, Bitmap bmp, int image_width, int image_height, int animation, int direction, float speedX, float speedY) {
        this.position = new Point(x,y);
        this.collision_detector = new Rect((int)x, (int)y, (int)(x+width), (int)(y+height));
        this.sprite = new Sprite(bmp, animation, direction, image_width, image_height);
        this.width = width;
        this.height = height;
        this.speed = new Point(speedX, speedY);
    }

    public Sprite getSprite() {
        return this.sprite;
    }

    public Point getPosition() {
        return position;
    }

    public Point getSpeed() {
        return speed;
    }

    //Methods
    public void onDraw(Canvas canvas) {
        int src_x = this.sprite.getCurrent_animation() * this.sprite.getWidth();
        int src_y = this.sprite.getDirection() * this.sprite.getHeight();

        Rect src = new Rect(src_x, src_y, (src_x + this.sprite.getWidth()), (src_y + this.sprite.getHeight()));
        Rect dst = this.collision_detector;
        canvas.drawBitmap(this.sprite.getBmp(), src, dst, null);
    }

    public void update(){
        this.position.setX(this.position.getX() + this.speed.getX());
        this.position.setY(this.position.getY() + this.speed.getY());

        this.collision_detector.set((int)this.position.getX(), (int)this.position.getY(), (int)(this.position.getX()+this.width), (int)(this.position.getY()+this.height));
    }
}