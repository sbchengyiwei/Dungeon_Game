package com.softeng2red.dungeon.objects;

import com.softeng2red.dungeon.framework.GameObject;
import com.softeng2red.dungeon.framework.ObjectId;
import com.softeng2red.dungeon.framework.Texture;
import com.softeng2red.dungeon.window.Game;

import java.awt.*;
import java.util.LinkedList;

public class Start_Screen extends GameObject {

    Texture tex = Game.getInstance();

    public Start_Screen(float x, float y, ObjectId id) {
        super(x, y, id);
    }

    public void tick(LinkedList<GameObject> object) {

    }

    public void render(Graphics g) {
        g.drawImage(tex.start_menu[0],(int)x,(int)y,null);
    }

    public Rectangle getBounds() {
        return null;
    }
}
