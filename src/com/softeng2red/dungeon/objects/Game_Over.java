package com.softeng2red.dungeon.objects;

import com.softeng2red.dungeon.framework.GameObject;
import com.softeng2red.dungeon.framework.ObjectId;
import com.softeng2red.dungeon.framework.Texture;
import com.softeng2red.dungeon.window.Game;

import java.awt.*;
import java.util.LinkedList;

//This Class Handles the GameOver object
public class Game_Over extends GameObject {

    Texture tex = new Texture();

    public Game_Over(float x, float y, ObjectId id) {
        super(x, y, id);
    }
    public void tick(LinkedList<GameObject> object) {}
    public void render(Graphics g) {
        g.drawImage(tex.gameover[0],(int)x,(int)y,null);
    }
    public Rectangle getBounds() {
        return null;
    }
}
