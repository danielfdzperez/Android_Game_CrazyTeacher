package com.game.crazygame.crazyteacher;

/**
 * Created by Danielfdzperez on 20/01/15.
 */


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v4.app.FragmentManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import java.text.DecimalFormat;
import java.util.ArrayList;

/*TODO Cambiar las variables height y width para que funcione bien sin repetir tanto codigo*/
public class GameView extends SurfaceView {

    static final int ROWS = 15;
    static final int COLS = 12;
    static final int MAX_SHOES = 50;
    static final int MIN_SHOES = 10;

    private SurfaceHolder holder;
    private GameLoopThread gameLoopThread;
    private Tile[][] map;
    private int height;
    private int width;
    private boolean big_screen_Y;
    private boolean big_screen_X;
    private int edit_width;
    private int edit_height;
    private static int touch_pos;//Guarda la posicion de la pulsacion anterior
    private static boolean move_touch;//Esta haciendo un scroll
    private boolean touch;
    private boolean touching; //The gamer is touching the screen.
    private int dodged_shoe;
    private int shoes_to_next_level;

    //Player puntuation
    private float player_puntuation;

    //Game functions
    private Arrow[] arrows;
    private Level level;

    //Game entities
    private Player player;
    private Enemy enemy;
    private ArrayList<Shoe> shoe;


    public GameView(Context context) {


        super(context);
        gameLoopThread = new GameLoopThread(this);
        holder = getHolder();

        holder.addCallback(new Callback() {

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                Log.d("ESTADO?", " " + gameLoopThread.getState());
                boolean retry = true;
                gameLoopThread.setRunning(false);

                while (retry) {
                    try {
                        gameLoopThread.join();
                        retry = false;
                    } catch (InterruptedException e) {}
                }
                Log.d("ESTADO?", " " + gameLoopThread.getState());
            }

            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                height = (int)Math.round((getHeight()*1.0)/ROWS);
                width =  (int)Math.round((getWidth()*1.0)/COLS);

                //TODO Usar esto para conocer la densidad de pixel y evitar usar el edit_with y height
                //Ver http://infinut.com/2013/10/10/to-opengl-or-to-surfaceview/
                DisplayMetrics dm = getContext().getResources().getDisplayMetrics();
                edit_height = height;
                edit_width = width;
                initializeVariables();
                loadLevel();
                loadMap();
                loadPlayer();
                loadEnemy();
                loadArrows();


                //Log.d("ESTADO?", " " + gameLoopThread.getState());

                try {
                    gameLoopThread.setRunning(true);
                    gameLoopThread.start();
                } catch (IllegalThreadStateException e){
                    gameLoopThread = null;
                    gameLoopThread = new GameLoopThread(GameView.this);
                    gameLoopThread.setRunning(true);
                    gameLoopThread.start();
                }


                //Log.d("ESTADO?", " " + gameLoopThread.getState());
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

        });

    }

    private void initializeVariables(){
        this.map = new Tile[ROWS][COLS];
        this.move_touch = false;
        this.touch = false;
        this.touching = false;
        this.shoe = new ArrayList(MAX_SHOES);
        this.dodged_shoe = 0;
        this.shoes_to_next_level = MIN_SHOES;
        this.player_puntuation = 0;
    }

    private void loadLevel(){
        this.level = new Level();
    }
    private void loadMap(){
        Bitmap img_ground = BitmapFactory.decodeResource(getResources(), R.drawable.ground);
        Bitmap img_table  = BitmapFactory.decodeResource(getResources(), R.drawable.table);
        Tile ground = new Tile(img_ground, false);
        Tile table = new Tile(img_table, false);

        //TODO Cambiar esto
        if((img_ground.getWidth()) < width) {
            big_screen_X = true;
            edit_width -= 6;
        }
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
        Bitmap player_img = BitmapFactory.decodeResource(getResources(), R.drawable.student);

        //TODO Cambiar esto
        if((player_img.getHeight()/4) < height) {
            big_screen_Y = true;
            edit_height -= 6;
        }
        else
            big_screen_Y = false;

        int img_width = (player_img.getWidth()/3) < width ? width : (player_img.getWidth()/3);
        int img_height = (player_img.getHeight()/4) < height ? height :  (player_img.getHeight()/4);
        int player_row = (player_img.getHeight()/4) > height ? ((ROWS-2)*(height)) :  ((ROWS-2)*(height-6));
        int player_col = 10;
        player = new Player(player_col, player_row, img_width, img_height, player_img, (player_img.getWidth()/3), (player_img.getHeight()/4), 2, 3, 0, 0, (edit_height * 2),
                0, (getWidth() - edit_width), (edit_height * 2), (getHeight()-edit_height));
    }

    private void loadEnemy(){
        Bitmap player_img = null;
        int image_number = 0 + (int)(Math.random() * ((1 - 0) + 1));
        switch (image_number) {
            case 0: player_img = BitmapFactory.decodeResource(getResources(), R.drawable.teacher);
                break;
            case 1:
                player_img = BitmapFactory.decodeResource(getResources(), R.drawable.teacher2);
                break;
        };

        int img_width = (player_img.getWidth()/3) < width ? width : (player_img.getWidth()/3);
        int img_height = (player_img.getHeight()/4) < height ? height :  (player_img.getHeight()/4);
        int player_row = (player_img.getHeight()/4) > height ? ((0)*(height)) :  ((0)*(height-6));
        int player_col = width;
        enemy = new Enemy(player_col, player_row, img_width, img_height, player_img, (player_img.getWidth()/3), (player_img.getHeight()/4), 2, 3, 0, 0, (edit_height * 2),
                0, (getWidth() - edit_width), (edit_height * 2), (getHeight()-edit_height));
    }

    private void loadArrows(){
        Bitmap arrow_right_img = BitmapFactory.decodeResource(getResources(), R.drawable.arrow_right);
        Bitmap arrow_left_img  = BitmapFactory.decodeResource(getResources(), R.drawable.arrow_left);
        arrows = new Arrow[2];
        arrows[0] = new Arrow (0, getHeight()-(arrow_left_img.getHeight()*2), arrow_left_img);
        arrows[1] = new Arrow (getWidth() - (arrow_right_img.getWidth()), getHeight()-(arrow_right_img.getHeight()*2), arrow_right_img);
    }

    private void restart(){
        this.shoe.clear();
        this.player_puntuation = 0;
        this.dodged_shoe = 0;
        this.shoes_to_next_level = MIN_SHOES;
        this.level.restartLevel();
        this.player.restart();
        this.enemy.restart();
    }

    private void addShoe(){
        //TODO Cambiar imagen
        Bitmap player_img = BitmapFactory.decodeResource(getResources(), R.drawable.sprite_shoe);

        //TODO cambiar width / 8, height / 1
        int img_width = (player_img.getWidth()/8);// < width ? width : (player_img.getWidth()/8);
        int img_height = (player_img.getHeight());// < height ? height :  (player_img.getHeight());
        this.shoe.add(new Shoe(this.enemy.getPosition().getX(), this.enemy.getPosition().getY(), img_width, img_height, player_img, (player_img.getWidth()/8), (player_img.getHeight()), 7, 3, 0, 0, 16,
                0, (getWidth() - edit_width), (edit_height * 2), (getHeight()-edit_height)));
    }

    private void shoeUpdate(){
        if(this.enemy.has_to_shoot())
            addShoe();
        for(int i = 0; i< this.shoe.size(); i++){
            if(this.shoe.get(i).screenOut((this.edit_height * ROWS))) {
                this.shoe.remove(i);
                this.player_puntuation +=  ( 3/(this.player.getPosition().getY()/edit_height) ) *level.getCurrent_level();
                this.dodged_shoe ++;
                Log.d("Zapato Muerto", " ");
            }
            else
               if(this.shoe.get(i).collision(this.player)) {
                   //this.shoe.remove(i);
                   this.restart();
                   //Toast toast1 = Toast.makeText(this.getContext().getApplicationContext(),"Muerto", Toast.LENGTH_SHORT);
                   //toast1.show();
                  //Log.d("IMPACTO", "MUERTOOOOOOOOOOO");
               }
            else
                this.shoe.get(i).update();
        }
    }

    private void levelUp(){
        enemy.change_speed(this.level.getCurrent_level()+1);
        if(enemy.getTime_shoot() > 5)
            enemy.setTime_shoot(enemy.getTime_shoot() - 5);
        this.level.nextLevel();
        this.dodged_shoe = 0;
        if(this.shoes_to_next_level + 5 < MAX_SHOES)
           this.shoes_to_next_level += 5;

        Log.d("NIVEL", "DISPARO: " + this.enemy.getTime_shoot());
        Log.d("NIVEL", "LEVEL: " + this.level.getCurrent_level());
    }

    public void updateObjects(){
        player.update();
        enemy.update(player);
        if(!move_touch && touch) {
            player.stop();
            touch = false;
        }
        this.shoeUpdate();
        if(this.dodged_shoe >= this.shoes_to_next_level)
            levelUp();
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
                if( (((int)((player.getPosition().getY()+player.getSprite().getHeight())/edit_height)) == y) && ((int)((player.getPosition().getX()+player.getSprite().getWidth())/(edit_width)) == x)) {
                    //player.getSprite().nextAnimation();
                    player.onDraw(canvas);
                }
                if( (((int)((enemy.getPosition().getY()+enemy.getSprite().getHeight())/edit_height)) == y) && ((int)((enemy.getPosition().getX()+enemy.getSprite().getWidth())/(edit_width)) == x)) {
                    //enemy.getSprite().nextAnimation();
                    enemy.onDraw(canvas);
                }
            }
        for(int i = 0; i< this.shoe.size(); i++) {
            this.shoe.get(i).onDraw(canvas);
        }

        for(int x = 0; x<arrows.length; x++){
            arrows[x].onDraw(canvas);
        }

        //TODO Poner esto para que solo se cree una vez y editar level_string cuando se cambie de nivel
        //TODO Cambiar nombre variables
        String level_text = "lvl: " + this.level.getCurrent_level();
        DecimalFormat decimal_format = new DecimalFormat("#.##");
        String puntuation_text = "Points: " + decimal_format.format(this.player_puntuation);
        Paint paintText = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintText.setColor(Color.WHITE);
        if(big_screen_Y)
            paintText.setTextSize(40);
        else
           paintText.setTextSize(16);
        paintText.setStyle(Paint.Style.FILL);
        paintText.setShadowLayer(10f, 10f, 10f, Color.BLACK);
        Rect rectText = new Rect();
        paintText.getTextBounds(level_text, 0, level_text.length(), rectText);
        canvas.drawText(level_text,(this.getWidth() - rectText.width())-5, rectText.height()+2, paintText);
        paintText.getTextBounds(puntuation_text, 0, puntuation_text.length(), rectText);
        canvas.drawText(puntuation_text,(this.getWidth() - rectText.width())-5, (rectText.height()+2)*2, paintText);
    }

    //@Override
    public boolean onTouchEvent(MotionEvent event) {

        if (!touching) {

            if (event.getAction() == MotionEvent.ACTION_MOVE) {
                move_touch = true;
                touching = true;
            }

            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                Log.d("Pulsado", "pulsado");
                touch_pos = (int) event.getY();
                if (arrows[0].getDetector().contains((int) event.getX(), (int) event.getY())) {
                    if (player.canMove(Player.TDirection.WEST)) {
                        player.moveLeft();
                        touching = true;
                    }
                }
                if (arrows[1].getDetector().contains((int) event.getX(), (int) event.getY())) {
                    if (player.canMove(Player.TDirection.EAST)) {
                        player.moveRight();
                        touching = true;
                    }
                }
            }
        }
            if (event.getAction() == MotionEvent.ACTION_UP) {
                Log.d("Soltado", "soltado");
                if (move_touch) {
                    int distance = (touch_pos - event.getY()) < 0 ? (int) (touch_pos - event.getY()) * -1 : (int) (touch_pos - event.getY());
                    if (distance > 100) {
                        if (touch_pos > (int) event.getY()) {
                            if (player.canMove(Player.TDirection.NORTH))
                                player.moveUp();
                        } else {
                            if (player.canMove(Player.TDirection.SOUTH))
                                player.moveDown();
                        }
                    } else
                        player.stop();
                    move_touch = false;
                    touch = true;
                } else
                    player.stop();
                touching = false;
            }
        return true;
    }
}