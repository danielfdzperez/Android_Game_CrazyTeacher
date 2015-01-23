package com.game.crazygame.crazyteacher;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Created by pc02 on 22/01/15.
 */
public class Arrow {
    private Rect detector;
    private Bitmap image;

    public Arrow(int x, int y, Bitmap image){
        this.image = image;
        this.detector = new Rect(x, y, (x+this.image.getWidth()), (y+this.image.getHeight()));
    }

    public Rect getDetector() {
        return detector;
    }

    public void onDraw(Canvas canvas){
        Rect src = new Rect(0, 0, this.image.getWidth(), this.image.getHeight());
        Rect dst = this.detector;
        canvas.drawBitmap(this.image, src, dst, null);
    }
}
