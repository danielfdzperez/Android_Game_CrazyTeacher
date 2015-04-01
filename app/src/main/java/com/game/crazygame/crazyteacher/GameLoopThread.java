package com.game.crazygame.crazyteacher;

import android.graphics.Canvas;
import android.util.Log;

/**
 * Created by Danielfdzperez on 22/01/15.
 */
public class GameLoopThread extends Thread {

    static final long FPS = 10;
    static final long SECOND = 1000;

    private GameView view;

    private boolean running = false;



    public GameLoopThread(GameView view) {

        this.view = view;

    }



    public void setRunning(boolean run) {
        running = run;
    }
    public boolean isRunning() { return running; }



    @Override

    public void run() {

        long ticksPS = SECOND / FPS;

        long startTime;

        long sleepTime;

        while (running) {
//            if(isInterrupted()) {
//                Log.d("INTERUMPIDO", "");
//                running = false;
//                return;
//            }

            Canvas c = null;

            startTime = System.currentTimeMillis();

            try {

                c = view.getHolder().lockCanvas();

                synchronized (view.getHolder()) {
                    view.updateObjects();
                    view.Draw(c);

                }

            } finally {
                if (c != null) {
                    view.getHolder().unlockCanvasAndPost(c);
                }
            }

            sleepTime = ticksPS - (System.currentTimeMillis() - startTime);

            try {

                if (sleepTime > 0)

                    sleep(sleepTime);

                else

                    sleep(10);

            } catch (Exception e) {}

        }

    }

}