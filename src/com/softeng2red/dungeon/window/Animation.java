package com.softeng2red.dungeon.window;

import java.awt.*;
import java.awt.image.BufferedImage;

//This class handles the animation of the player
public class Animation {

    private int speed;
    private int frames;
    private int index = 0;
    private int count = 0;

    private BufferedImage[] images;
    private BufferedImage currentImg;

    public Animation(int speed, BufferedImage... args){
         this.speed = speed;
         images = new BufferedImage[args.length];
         for (int i = 0; i<args.length; i++){
             images[i] = args[i];
         }
         frames = args.length;
    }
    //Animation runs dependant on the set speed
    //Each tick this is called and index is increased.
    public void runAnimation(){
        index++;
        if (index>speed){
            index = 0;
            nextFrame();
        }
    }
    //Sets the next frame to be used
    private void nextFrame(){
        for (int i = 0; i<frames; i++){
            if(count == i){
                currentImg = images[i];
            }
        }
        count++;
        if (count>frames){
            count = 0;
        }
    }
    //Draws the current frame
    public void drawAnimation(Graphics g, int x, int y){
        g.drawImage(currentImg, x,y,null);

    }
    //Draws the current frame allowing you to scale the image.
    public void drawAnimation(Graphics g, int x, int y, int scaleX, int scaleY){
        g.drawImage(currentImg, x,y,scaleX,scaleY, null);

    }

}
