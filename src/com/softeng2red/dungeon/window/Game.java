package com.softeng2red.dungeon.window;

import com.softeng2red.dungeon.framework.GameObject;
import com.softeng2red.dungeon.framework.KeyInput;
import com.softeng2red.dungeon.framework.ObjectId;
import com.softeng2red.dungeon.framework.Texture;
import com.softeng2red.dungeon.objects.*;
import com.softeng2red.dungeon.window.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.BufferStrategy;
import java.util.Random;

//This class Handles the main game logic
public class Game extends Canvas implements Runnable {

    // Object
    public Handler handler;
    Camera cam;
    public static Game_Timer game_timer;
    static Texture tex;
    private HUD hud;

    public static boolean isAppear = true;
    public static boolean isStarting = false;
    public static boolean isFinished = false;
    private boolean running = false;
    private Thread thread;
    public static int WIDTH, HEIGHT;
    public BufferedImage city = null;
    public static int count;
    public static int delay;
    public static int LEVEL = 1;


    public void init() {
        WIDTH = getWidth();
        HEIGHT = getHeight();
        tex = new Texture();

        BufferedImageLoader loader = new BufferedImageLoader();
        // loading the level
//        level = loader.loadImage("/level1.png");//Loads the level image
        city = loader.loadImage("/Overground_City_Scene_Big_improved.png");//Loads the background city image
        cam = new Camera(0,0);//Initializes Camera
        handler = new Handler(cam, game_timer);//Initializes Handler
//        handler.LoadImageLevel(level0);
        GameStarting();
//        handler.addObject(new Health(650 ,20, handler,ObjectId.Health));//Initializes health
        game_timer = new Game_Timer(0,0, ObjectId.Game_Timer);//Initializes game timer

        // initialize HUD object
        GameObject healthObject = null;
        for(int i = 0; i<handler.object.size(); i++) {
            GameObject tempObject = handler.object.get(i);
            if(tempObject.getId() == ObjectId.Health) {
                healthObject = tempObject;
            }
        }
        hud = new HUD((Health) healthObject, game_timer);
        this.addKeyListener(new KeyInput(handler, this, hud));//Adds key Listener
        game_timer.init();

    }

    public synchronized void start() {
        if (running)
            return;
        running = true;
        thread = new Thread(this);
        thread.start();

    }

    // Function which runs the FPS
    public void run() {

        init();
        this.requestFocus();
        long lastTime = System.nanoTime();
        // decrease from 60 to 40 due to health object, need to improve later
        double amountOfTicks = 40.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int updates = 0;
        int frames = 0;
        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                tick();
                updates++;
                delta--;
            }
            render();
            frames++;

            if ((System.currentTimeMillis() - timer) > 1000) {
                timer += 1000;
                System.out.println("FPS: " + frames + "  TICKS: " + updates);
                frames = 0;
                updates = 0;
            }
        }
    }

    //Function which carries out the functions at each tick
    private void tick() {
        handler.tick();

        // the code block bellow controls the appearance of disappearing blocks
        count ++;
        delay = 80;
        if(count%delay==0) {
            isAppear = !isAppear;
        }

        // the code below controls the appearance of game over screen
        for (int i = 0; i<handler.object.size(); i++){
            GameObject tempObject = handler.object.get(i);
            if(tempObject.getId() == ObjectId.Player){
                cam.tick(tempObject);
                GameObject healthObject = handler.object.get(0);
                if (healthObject.healthNum == healthObject.minHealth) {
                    GameOver();//Checking if the player has died
                }
            }
        }

        if (game_timer.getTime() <= 0)
            // Checking if the time has run up
            GameOver();
    }

    //Function which is called when game begins
    public void GameStarting() {
        isStarting = true;
        isFinished = false;
        handler.clearLevel();
        handler.addObject(new Start_Screen(0,0,ObjectId.Start_Screen));
    }

    public static void GameFinish() {
        isFinished = true;
        game_timer.stop();
    }

    //Function which is called when player dies
    public void GameOver() {
        game_timer.stop();
        handler.clearLevel();
        hud.clear();
        handler.addObject(new Game_Over(0,0, ObjectId.Game_Over));

    }

    private void render() {
        //This is the Buffer strategy
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        Graphics2D g2d = (Graphics2D) g;

        /******************/
        g.setColor(Color.black);
        g.fillRect(0, 0, getWidth(), getHeight());

        g2d.translate(cam.getX(),cam.getY());

        g.drawImage(city,0,-180, 5000, 350, this);//Draws the background scene
        handler.render(g);//Draws all the objects

        g2d.translate(-cam.getX(),-cam.getY());//Adjusts camera so is aligned with player
        hud.draw(g2d);//Draws the heads up display

        /******************/
        g.dispose();
        bs.show();

    }

    public static Texture getInstance(){
        return tex;
    }

    public static void main(String args[]) {
        startGame();
    }

    // Creates the new Window and game object
    public static void startGame() {
        new Window(960, 800, "A Dungeon Game",  new Game());
    }
}

