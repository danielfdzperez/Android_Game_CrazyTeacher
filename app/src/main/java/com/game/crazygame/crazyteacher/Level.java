package com.game.crazygame.crazyteacher;

/**
 * Created by danielfdzperez on 5/03/15.
 */
public class Level {
    private int current_level;

    public Level(Integer... current_level) {
        this.current_level = current_level.length > 0 ? ((Integer)current_level[0]) : 1;
    }

    public int getCurrent_level() {
        return current_level;
    }

    public void setCurrent_level(int current_level) {
        this.current_level = current_level;
    }

    public void nextLevel(){
        this.current_level ++;
    }

    public void restartLevel(){
        this.current_level = 0;
    }
}
