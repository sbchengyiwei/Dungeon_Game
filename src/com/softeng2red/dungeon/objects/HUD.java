package com.softeng2red.dungeon.objects;

import com.softeng2red.dungeon.framework.GameObject;
import com.softeng2red.dungeon.framework.ObjectId;
import com.softeng2red.dungeon.framework.Texture;
import com.softeng2red.dungeon.window.Game;
import com.softeng2red.dungeon.window.Handler;

import java.awt.*;


//This class handles the heads up display
//This shows the health and the time
public class HUD {

    Game_Timer timer;
    private Health healthObject;
    private Font font;
//    Finishing_Screen finishingScreen;
    private boolean running = true;
    Texture tex = Game.getInstance();

    public HUD (Health healthObject, Game_Timer timer) {
        try{

            this.healthObject = healthObject;
            this.timer = timer;
//            this.finishingScreen = finishingScreen;
            font = new Font("Arial", Font.PLAIN, 24);

        }
        catch (Exception e){
            System.out.println("hello");
            e.printStackTrace();
        }
    }


    public void draw(Graphics2D g){

        if(running && !Game.isStarting && !Game.isFinished) {

            // Varies the size of the spotlight dependant on how much beer has been collected
            if (healthObject.beerNum == 1) g.drawImage(tex.spotlight[3], 0,0, Game.WIDTH + 20,Game.HEIGHT + 20, null);
            else if (healthObject.beerNum == 2) g.drawImage(tex.spotlight[2], 0,0, Game.WIDTH + 20,Game.HEIGHT + 20, null);
            else if (healthObject.beerNum == 3) g.drawImage(tex.spotlight[1], 0,0, Game.WIDTH + 20,Game.HEIGHT + 20, null);
            else if (healthObject.beerNum >= 4) g.drawImage(tex.spotlight[0], 0,0, Game.WIDTH + 20,Game.HEIGHT + 20, null);

            // Different number of hearts shown
            for (int i = 0; i < healthObject.healthNum; i++) {
                g.drawImage(tex.health[0], 30 + i * 32, 50, null);
            }

            // Displays a timer
            g.setColor(Color.WHITE);
            g.setFont(font);
            g.drawString(String.format("Time: %03d", timer.getTime()), 37, 130); // Timer可以用
        }

    }

    public void init() {
        running = true;
        healthObject.healthNum = GameObject.init_health;
        healthObject.beerNum = GameObject.init_beer;
        timer.init();
    }


    // when running is false, HUD stops display health and timer
    public void clear(){
        running = false;
    }

    

}


