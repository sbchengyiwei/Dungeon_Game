package com.softeng2red.dungeon.objects;

import com.softeng2red.dungeon.framework.GameObject;
import com.softeng2red.dungeon.framework.ObjectId;
import com.softeng2red.dungeon.framework.Texture;
import com.softeng2red.dungeon.window.Game;

import java.awt.*;
import java.util.LinkedList;

// This class creates disappearing blocks on map, which appear and disappear repeatedly for seconds
public class Disappearing_Block extends GameObject {
    Texture tex = Game.getInstance();


    public Disappearing_Block(float x, float y, ObjectId id){
        super(x, y, id);
    }

    public void tick(LinkedList<GameObject> object) {
    }

    public void render(Graphics g) {
        if(Game.isAppear) {
            g.drawImage(tex.block[3], (int) x, (int) y, null);
        }
        else {
//            g.setColor(Color.BLACK);
//            g.drawRect((int) x, (int) y, 32, 32);
        }
    }

    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, 32, 32);
    }

}
