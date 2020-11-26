package com.softeng2red.dungeon.objects;

import com.softeng2red.dungeon.framework.GameObject;
import com.softeng2red.dungeon.framework.ObjectId;
import com.softeng2red.dungeon.framework.Texture;
import com.softeng2red.dungeon.window.Game;
import com.softeng2red.dungeon.window.Handler;
import java.awt.*;
import java.util.LinkedList;

//This Class handles beer objects
public class Beer extends GameObject {

    Texture tex = Game.getInstance();

    public Beer(float x, float y, ObjectId id) {
        super(x, y, id);
    }
    public void tick(LinkedList<GameObject> object) { }
    public void render(Graphics g) {
        g.drawImage(tex.beer[0],(int)x,(int)y,32,32,null);
    }
    public Rectangle getBounds() {
        return new Rectangle((int)x, (int)y, 32, 32);
    }
}
