package com.softeng2red.dungeon.objects;

import com.softeng2red.dungeon.framework.GameObject;
import com.softeng2red.dungeon.framework.ObjectId;
import com.softeng2red.dungeon.framework.Texture;
import com.softeng2red.dungeon.window.Game;

import java.awt.*;
import java.util.LinkedList;

//This class handles the vertically moving blocks
public class Moving_Block extends GameObject {
    Texture tex = Game.getInstance();
    private int count;
    private int delay;

    public Moving_Block(float x, float y, int delay, ObjectId id){
        super(x, y, id);
        this.delay = delay*40;
        setVelY(1);
    }

    public void tick(LinkedList<GameObject> object) {
        this.count +=1;
        //Alternates the direction of movement dependant on the delay
        if (count%this.delay==0){
            setVelY(velY*-1);
        }
        //Sets if the block is moving up or down
        if (velY>0){
            m_Up = false;
            m_Down = true;
        }else{
            m_Up = true;
            m_Down = false;
        }
        y += velY;


    }
    //Draws the moving block
    public void render(Graphics g) {
        g.drawImage(tex.block[2],(int)x,(int)y,null);

    }
    public Rectangle getBounds() {
        return new Rectangle((int)x, (int)y, 32, 32);
    }
}
