package com.game.crazygame.crazyteacher;

/**
 * Created by pc02 on 20/01/15.
 */


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

/*TODO Cambiar las variables height y width para que funcione bien sin repetir tanto codigo*/
public class GameView extends SurfaceView {

    static final int ROWS = 15;
    static final int COLS = 12;

    private SurfaceHolder holder;
    private GameLoopThread gameLoopThread;
    private Tile[][] map = new Tile[ROWS][COLS];//TODO Inicializar en su sitio
    private int height;
    private int width;
    private boolean big_screen_Y;
    private boolean big_screen_X;
    private static int touch_pos;//Guarda la posicion y de la pulsacion anterior
    private static boolean move_touch;//Esta haciendo un scroll
    private boolean touch = false;//TODO inicializar en su sitio
    private Arrow[] arrows;

    private Player player;

    private Canvas c;



    public GameView(Context context) {

        super(context);

       gameLoopThread = new GameLoopThread(this);

        holder = getHolder();

        holder.addCallback(new Callback() {



            @Override

            public void surfaceDestroyed(SurfaceHolder holder) {
                boolean retry = true;

                gameLoopThread.setRunning(false);

                while (retry) {
                    try {
                        gameLoopThread.join();
                        retry = false;
                    } catch (InterruptedException e) {}

                }
            }

            @Override

            public void surfaceCreated(SurfaceHolder holder) {
                height = getHeight()/ROWS;
                width =  getWidth()/COLS;
                loadMap();
                loadPlayer();
                loadArrows();
                gameLoopThread.setRunning(true);
                gameLoopThread.start();
            }



            @Override

            public void surfaceChanged(SurfaceHolder holder, int format,

                                       int width, int height) {

            }

        });

    }


    private void loadMap(){
        Bitmap img_ground = BitmapFactory.decodeResource(getResources(), R.drawable.ground);
        Bitmap img_table  = BitmapFactory.decodeResource(getResources(), R.drawable.table);
        Tile ground = new Tile(img_ground, false);
        Tile table = new Tile(img_table, false);
        if((img_ground.getWidth()) < width)
            big_screen_X = true;
        else
            big_screen_X = false;

        for(int y=0; y<map.length; y++)
            for(int x=0; x<map[0].length; x++) {
                this.map[y][x] = ground;
                if(y!=0 && y%2 ==0)
                    this.map[y][x] = table;
            }
    }

    private void loadPlayer(){
        Bitmap player_img = BitmapFactory.decodeResource(getResources(), R.drawable.bad1);


        //TODO Cambiar esto
        if((player_img.getHeight()/4) < height)
            big_screen_Y = true;
        else
            big_screen_Y = false;



        int img_width = (player_img.getWidth()/3) < width ? width : (player_img.getWidth()/3);
        int img_height = (player_img.getHeight()/4) < height ? height :  (player_img.getHeight()/4);
        int player_row = (player_img.getHeight()/4) > height ? ((ROWS-2)*(height)) :  ((ROWS-2)*(height-6));
        int player_col = 10;
        player = new Player(player_col, player_row, img_width, img_height, player_img, (player_img.getWidth()/3), (player_img.getHeight()/4), 2, 3, 0, 0);
    }

    public void loadArrows(){
        Bitmap arrow_right_img = BitmapFactory.decodeResource(getResources(), R.drawable.arrow_right);
        Bitmap arrow_left_img  = BitmapFactory.decodeResource(getResources(), R.drawable.arrow_left);
        arrows = new Arrow[2];
        arrows[0] = new Arrow (0, getHeight()-(arrow_left_img.getHeight()*2), arrow_left_img);
        arrows[1] = new Arrow (getWidth() - (arrow_right_img.getWidth()), getHeight()-(arrow_right_img.getHeight()*2), arrow_right_img);
    }

    public void updateObjects(){
        player.update();
        if(!move_touch && touch) {
            player.stop();
            touch = false;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {

        canvas.drawColor(Color.BLACK);
        for(int y=0; y<ROWS; y++)
            for(int x=0; x<COLS; x++) {
                this.map[0][0].onDraw(canvas, x, y, width, height);
            }
        for(int y=0; y<ROWS; y++)
            for(int x=0; x<COLS; x++) {
                this.map[y][x].onDraw(canvas, x, y, width, height);

                //Cambiar
                int edit_height = height;
                if(big_screen_Y) {
                    edit_height -= 6;
                }
                int edit_width = width;
                if(big_screen_X)
                    edit_width -= 6;

                if( (((int)((player.getPosition().getY()+player.getSprite().getHeight())/edit_height)) == y) && ((int)((player.getPosition().getX()+player.getSprite().getWidth())/(edit_width)) == x)) {
                    player.getSprite().nextAnimation();
                    player.onDraw(canvas);
                }
            }
        for(int x = 0; x<arrows.length; x++){
            arrows[x].onDraw(canvas);
        }
        //player.onDraw(canvas);
    }

//    private boolean bigScreenY(Bitmap bmp){
//    }
//    private boolean bigScreenX(Bitmap bmp){
//    }

    //@Override
    public boolean onTouchEvent(MotionEvent event) {

//        if (System.currentTimeMillis() - lastClick > 500) {
//
//            lastClick = System.currentTimeMillis();
//
//            synchronized (getHolder()) {
//
//                for (int i = sprites.size() - 1; i >= 0; i--) {
//
//                    Sprite sprite = sprites.get(i);
//
//                    if (sprite.isCollition(event.getX(), event.getY())) {
//
//                        sprites.remove(sprite);
//
//                        break;
//
//                    }
//
//                }
//
//            }
//
//        }


        if(event.getAction() == MotionEvent.ACTION_MOVE)
            move_touch = true;

        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            touch_pos = (int) event.getY();
            if (arrows[0].getDetector().contains((int)event.getX(), (int)event.getY())) {
                player.moveLeft();
            }
            if (arrows[1].getDetector().contains((int)event.getX(), (int)event.getY()))
                player.moveRight();
        }
        if(event.getAction() == MotionEvent.ACTION_UP) {
            if (move_touch) {
                int distance = (touch_pos-event.getY()) < 0 ? (int)(touch_pos-event.getY())*-1 : (int)(touch_pos-event.getY());
                if(distance > 100) {
                    int edit_height = height;
                    if(big_screen_Y) {
                        edit_height -= 6;
                    }
                    if (touch_pos > (int) event.getY()) {
                        player.moveUp((edit_height * 2));
                    }
                    else
                        player.moveDown((edit_height* 2));
                }
                else
                    player.stop();
                move_touch = false;
                touch = true;
            }
            else
               player.stop();
        }
        return true;
    }

}