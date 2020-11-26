package com.softeng2red.dungeon.objects;

import com.softeng2red.dungeon.framework.GameObject;
import com.softeng2red.dungeon.framework.ObjectId;
import com.softeng2red.dungeon.framework.Texture;
import com.softeng2red.dungeon.window.Game;
import com.softeng2red.dungeon.window.Handler;
import java.util.Random;
import java.awt.*;
import java.util.LinkedList;


//This Class handles the Villains
//Villains are either a fox or a rat
//They move backwards and forwards in predictable manner
public class Villain extends GameObject {

    private float width = 32, height = 32;
    private float gravity = 0.5f;
    private final float MAX_SPEED = 10;
    private int Villain_Type;
    private int count;
    private int delay;

    Texture tex = Game.getInstance();


    public Villain(float x, float y,int delay, ObjectId id) {
        super(x, y, id);
        this.delay = delay*40;
        setVelX(1);
        Random random = new Random();
        Villain_Type = random.nextInt(2);//Selects either a fox or rat randomly
    }

//Each tick the villain is moved
    public void tick(LinkedList<GameObject> object) {
        this.count +=1;
        if (count%this.delay==0){
            setVelX(velX*-1);
        }
        x += velX;
    }
    //This direction the villain is moving is the direction the image of the villain will face.
    public void render(Graphics g) {
        if (velX>0){
            if (Villain_Type == 1){
                g.drawImage(tex.villain[1],(int)x,(int)y,(int) width + 10,(int) height, null);
            }else{
                g.drawImage(tex.villain[3],(int)x,(int)y,(int) width,(int) height, null);
            }
        }else{
            if (Villain_Type == 1){
                g.drawImage(tex.villain[0],(int)x,(int)y,(int) width + 10,(int) height, null);
            }else{
                g.drawImage(tex.villain[2],(int)x,(int)y,(int) width,(int) height, null);
            }
        }



    }

    public Rectangle getBounds() {
        return new Rectangle((int)x, (int)y, 32, 32);
    }
    public Rectangle getBoundsRight() {
        return new Rectangle((int)((int)x+width-5), (int)y+5, (int) 5, (int)height-10);
    }
    public Rectangle getBoundsLeft() {
        return new Rectangle((int)x, (int)y+5, (int)5, (int)height-10);
    }
}
