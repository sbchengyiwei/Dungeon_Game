package com.softeng2red.dungeon.objects;

import com.softeng2red.dungeon.framework.GameObject;
import com.softeng2red.dungeon.framework.ObjectId;
import com.softeng2red.dungeon.framework.Texture;
import com.softeng2red.dungeon.window.Game;

import java.awt.*;
import java.util.LinkedList;

public class Bouncer extends GameObject {


    Texture tex = Game.getInstance();
    public Bouncer(float x, float y,ObjectId id) {
        super(x, y, id);

    }

    public void tick(LinkedList<GameObject> object) {

    }


    //Draws the block of corresponding type.
    public void render(Graphics g) {

        if (img_type==0){
            g.drawImage(tex.bouncer[0],(int)x,(int)y, null);
        }else{
            g.drawImage(tex.bouncer[1],(int)x,(int)y, null);
        }
    }


    public Rectangle getBounds() {
        return new Rectangle((int)x, (int)y, 32, 32);
    }
}
