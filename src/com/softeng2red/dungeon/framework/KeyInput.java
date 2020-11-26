package com.softeng2red.dungeon.framework;

import com.softeng2red.dungeon.objects.Game_Timer;
import com.softeng2red.dungeon.objects.Health;
import com.softeng2red.dungeon.window.Handler;
import com.softeng2red.dungeon.objects.HUD;
import com.softeng2red.dungeon.window.Game;


import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

//This class handles key input.
public class KeyInput extends KeyAdapter {
    Handler handler;
    Game game;
    HUD hud;
    int key;
    protected ObjectId id;

    public KeyInput(Handler handler, Game game, HUD hud) {
        this.handler = handler;
        this.game = game;
        this.hud = hud;
    }

    //If a useful key is pressed a corresponding action occurs
    //Often a change in the velocity of the player
    public void keyPressed(KeyEvent e) {
        key = e.getKeyCode();
        for (int i = 0; i < handler.object.size(); i++) {
            GameObject tempObject = handler.object.get(i);
            if (tempObject.getId() == ObjectId.Player) {
                if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) {
                    tempObject.m_Left = false;
                    tempObject.m_Right = true;
                    tempObject.setVelX(4);
                }
                if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) {
                    tempObject.m_Right = false;
                    tempObject.m_Left = true;
                    tempObject.setVelX(-4);
                }
                if (!tempObject.isJumping() && (key == KeyEvent.VK_UP || key == KeyEvent.VK_W)) {
                    tempObject.setJumping(true);
                    tempObject.setVelY(-10);
                }
            }

            if (tempObject.getId() == ObjectId.Game_Over) {
                if (key == KeyEvent.VK_SPACE) {
                    Game.LEVEL = -1;
                    handler.switchLevel();
                    handler.removeObject(tempObject);
                    hud.init();
                }
            }

            if (tempObject.getId() == ObjectId.Finishing_Screen) {
                if (key == KeyEvent.VK_SPACE) {
                    Game.LEVEL = -1;
                    handler.switchLevel();
                    handler.removeObject(tempObject);
                    hud.init();
                }
            }

            if (tempObject.getId() == ObjectId.Start_Screen) {
                if (key == KeyEvent.VK_1) {
                    Game.LEVEL = 0;
                    Game.isStarting = false;
                    handler.switchLevel();
                    hud.init();
                }
                if (key == KeyEvent.VK_2) {
                    Game.LEVEL = 1;
                    Game.isStarting = false;
                    handler.switchLevel();
                    hud.init();
                }
                if (key == KeyEvent.VK_3) {
                    Game.LEVEL = 2;
                    Game.isStarting = false;
                    handler.switchLevel();
                    hud.init();
                }

            }
            if (key == KeyEvent.VK_ESCAPE) {
                System.exit(1);
            }
        }
    }
    //Handles functionality for when a key is released.
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        for (int i = 0; i < handler.object.size(); i++) {
            GameObject tempObject = handler.object.get(i);

            if (tempObject.getId() == ObjectId.Player) {
                if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D)
                    tempObject.setVelX(0);
                if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A)
                    tempObject.setVelX(0);
            }
        }
    }

    public void setKeyInput(int keycode) {
        key = keycode;
    }
}
