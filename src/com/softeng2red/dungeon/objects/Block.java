package com.softeng2red.dungeon.objects;

import com.softeng2red.dungeon.framework.GameObject;
import com.softeng2red.dungeon.framework.ObjectId;
import com.softeng2red.dungeon.framework.Texture;
import com.softeng2red.dungeon.window.Game;
import java.awt.*;
import java.util.LinkedList;

//This class Handles the standard non moving blocks
public class Block extends GameObject {
    Texture tex = Game.getInstance();
    private int type;
    public Block(float x, float y, int type, ObjectId id) {
        super(x, y, id);
        this.type = type;
    }

    public void tick(LinkedList<GameObject> object) { }

    //Draws the block of corresponding type.
    public void render(Graphics g) {
        if (type == 0){
            g.drawImage(tex.block[0],(int)x,(int)y,null);
        }
        if (type == 1){
            g.drawImage(tex.block[1],(int)x,(int)y,null);
        }
    }

    public Rectangle getBounds() {
        return new Rectangle((int)x, (int)y, 32, 32);
    }

}
