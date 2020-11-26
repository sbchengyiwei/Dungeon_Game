package com.softeng2red.dungeon.objects;

import com.softeng2red.dungeon.framework.GameObject;
import com.softeng2red.dungeon.framework.ObjectId;
import com.softeng2red.dungeon.framework.Texture;
import com.softeng2red.dungeon.window.Game;
import com.softeng2red.dungeon.window.Handler;
import java.awt.*;
import java.util.LinkedList;

//This Class handles the Health of the player
public class Health extends GameObject {

    Texture tex = Game.getInstance();
    private Handler handler;

    public Health(float x, float y, Handler handler, ObjectId id) {
        super(x, y, id);
        this.handler = handler;
    }

    public void tick(LinkedList<GameObject> object){}
    public void render(Graphics g) {}
    public Rectangle getBounds() {
        return null;
    }

}
