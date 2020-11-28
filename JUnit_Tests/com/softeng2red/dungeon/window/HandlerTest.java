package com.softeng2red.dungeon.window;

import com.softeng2red.dungeon.framework.ObjectId;
import com.softeng2red.dungeon.objects.*;
import org.junit.Test;

import java.awt.image.BufferedImage;

import static org.junit.Assert.*;

public class HandlerTest {

    @Test
    public void loadImageLevelTest1() {
        // test whether an object is successfully created
        // if the test passes, the object's Class is complete and the new object was successfully created on the level map
        boolean isExist = false;
        BufferedImageLoader loader = new BufferedImageLoader();
        Game_Timer game_timer = new Game_Timer(0,0,ObjectId.Game_Timer);
        Camera cam = new Camera(0,0);
        Handler handler = new Handler(cam, game_timer);
        BufferedImage level = loader.loadImage("/level.png");
        handler.LoadImageLevel(level);
        for(int i=0; i<handler.object.size(); i++) {
            if(handler.object.get(i).getId() == ObjectId.Disappearing_Block) {  // change this ObjectId. to the object to be tested
                isExist = true;
            }
        }
        assertTrue(isExist);
    }


    @Test
    public void loadImageLevelTest2() {
        // test whether an object is successfully created by the right GRB
        // if the test passes, the object was created successfully by the right GRB

        // enter the object to be tested here
        String objectTested = "Player";

        boolean isExist = false;
        BufferedImageLoader loader = new BufferedImageLoader();
        Game_Timer game_timer = new Game_Timer(0,0,ObjectId.Game_Timer);
        Camera cam = new Camera(0,0);
        Handler handler = new Handler(cam, game_timer);
        BufferedImage level = loader.loadImage("/level.png");
        int w = level.getWidth();
        int h = level.getHeight();

        for (int xx = 0; xx < w; xx++){
            for (int yy = 0; yy < h; yy++){
                int pixel = level.getRGB(xx,yy);
                int red = (pixel >> 16) & 0xff;
                int green = (pixel >> 8) & 0xff;
                int blue = (pixel) & 0xff;
                // every time test one object, comment out other object creating statements

                switch(objectTested) {
                    case ("Player"):
                        //Blue on paint S (0,0,255), PLayer
                        if(red == 0 && blue == 255 & green == 0) {
                            handler.addObject((new Player(xx*32,yy*32, handler, cam, ObjectId.Player)));
                            isExist = true;
                        }
                    case ("Standard_Block"):
                        //White on paint S, (255,255,255), Standard block
                        if(red == 255 && blue == 255 & green == 255) {
                            handler.addObject((new Block(xx*32,yy*32, 0, ObjectId.Block)));
                            isExist = true;
                        }
                    case ("Disappearing_Block"):
                        // Paint S (144,144,144), Disappearing Blocks
                        if (red ==  144 && blue == 144 & green == 144) {
                            handler.addObject((new Disappearing_Block(xx*32,yy*32, ObjectId.Disappearing_Block)));
                            isExist = true;
                        }
                    case ("Grass_Block"):
                        //Green on paint S (0,255,0), Grass Block
                        if(red == 35 && blue == 6 & green == 255) {
                            handler.addObject((new Block(xx*32,yy*32, 1, ObjectId.Block)));
                            isExist = true;
                        }
                    case ("Moving_Block"):
                        //Pink on Paint S (255,0,255), Moving block
                        if (red == 251 && blue == 255 & green == 0) {
                            handler.addObject((new Moving_Block(xx*32,yy*32, 1, ObjectId.Moving_Block)));
                            isExist = true;
                        }
                    case ("Villain"):
                        //Red on Paint S (251,0,7), Villain
                        if (red == 251 && blue == 7 & green == 0) {
                            handler.addObject((new Villain(xx*32,yy*32, 2, ObjectId.Villain)));
                            isExist = true;
                        }
                    case ("Beer"):
                        // Yellow on Paint S (229,229,92), Beer
                        if (red == 229 && blue == 92 & green == 229) {
                            handler.addObject((new Beer(xx*32,yy*32, ObjectId.Beer)));
                            isExist = true;
                        }
                    case ("Barrel"):
                        // Brown on Paint S (102,0,0), Barrel
                        if (red == 102 && blue == 0 & green == 0) {
                            handler.addObject((new Obstacle(xx*32,yy*32, ObjectId.Obstacle)));
                            isExist = true;
                        }
                    case ("Game_Over"):
                        // Grey on Paint S (141,141,141), Game Over
                        if (red == 141 && blue == 141 && green == 141) {
                            handler.addObject((new Block(xx*32, yy*32, 2, ObjectId.Block)));
                            isExist = true;
                        }
                    case ("Game_Timer"):
                        // Purple on Paint s (160,0,160), Game timer trigger
                        if (red == 146 && blue == 104 && green == 104) {
                            handler.addObject((new Game_Timer(xx*32, yy*32, ObjectId.Game_Timer)));
                            isExist = true;
                        }
                    case ("Finishing_Screen"):
                        // Paint S (200, 200, 200), Finishing Screen
                        if (red ==  188 && blue == 188 & green == 188) {
                            handler.addObject((new Finishing_Screen(xx*32,yy*32, ObjectId.Finishing_Screen)));
                            isExist = true;
                        }
                }
            }
        }
        assertTrue(isExist);
    }
}