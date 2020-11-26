package com.softeng2red.dungeon.window;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;

//This Class Allows images to be loaded.
public class BufferedImageLoader {

    private BufferedImage image;
    public BufferedImage loadImage(String path){
        try {
            image = ImageIO.read(getClass().getResource(path));

        }catch(IOException e){
            e.printStackTrace();
        }
        return image;
    }

}
