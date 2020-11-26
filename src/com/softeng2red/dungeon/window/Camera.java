package com.softeng2red.dungeon.window;

import com.softeng2red.dungeon.framework.GameObject;

//This Class handles the camera
//This should follow the player around the level, keeping them in the centre
public class Camera  {
    private float x, y;
    public Camera(float x, float y){
        this.x = x;
        this.y = y;
    }
    //Sets the position so the screen is centred on the player
    public void tick(GameObject player){
        x = -player.getX() + Game.WIDTH/2;
        y = -player.getY() + Game.HEIGHT/2;
    }

    public void setX(float x){
        this.x= x;
    }
    public void setY(float y){
        this.y = y;
    }
    public float getX(){
        return x;
    }
    public float getY(){
        return y;
    }

}
