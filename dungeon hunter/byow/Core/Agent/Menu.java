package byow.Core.Agent;

import byow.Core.StartGame.Engine;
import byow.Core.Entity.Sound;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;

public class Menu {

    private static final int WIDTH = Engine.WIDTH;
    private static final int HEIGHT = Engine.HEIGHT;
    public static final int MENU_WIDTH = 50;
    public static final int MENU_HEIGHT = 60;

    public static void initializeGUI() {
        makeGUIBackground();
        makeGUI();
        StdDraw.show();
        StdDraw.enableDoubleBuffering();
        new Thread(()->{while(true) {
            Sound.play();}
        }).start();


    }
    public static void makeGUIBackground() {
        StdDraw.clear();
        StdDraw.enableDoubleBuffering();
        StdDraw.setCanvasSize(MENU_WIDTH * 25, MENU_HEIGHT * 13);
        Font font = new Font("Comic Sans Ms", Font.BOLD, 100);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, MENU_WIDTH);
        StdDraw.setYscale(0, MENU_HEIGHT);
        StdDraw.clear(Color.BLACK);
    }

    public static void makeGUI() {
        Font title = new Font("Monaco", Font.BOLD, 40);
        Font mainMenu = new Font("Comic Sans Ms", Font.PLAIN, 30);
        StdDraw.setFont(title);
        StdDraw.setPenColor(Color.white);
        StdDraw.text(MENU_WIDTH / 2, MENU_HEIGHT * 2 / 2.5, "DUNGEON HUNTER : NEW GAME");
        StdDraw.setFont(mainMenu);
        StdDraw.text(MENU_WIDTH / 2, MENU_HEIGHT * 5.5 / 10, "New World (n)");
        StdDraw.text(MENU_WIDTH / 2, MENU_HEIGHT * 4.5 / 10, "Load World (l)");
        StdDraw.text(MENU_WIDTH / 2, MENU_HEIGHT * 3.5 / 10, "Quit (q)");
    }

    public static void makeCustomMessageScreen(String string) {
        int x = MENU_WIDTH / 2;
        int y = MENU_HEIGHT * 2 / 3;
        StdDraw.clear();
        StdDraw.clear(Color.black);
        Font bigFont = new Font("Comic Sans Ms", Font.BOLD, 25);
        StdDraw.setFont(bigFont);
        StdDraw.setPenColor(Color.white);
        StdDraw.text(x, y, string);
        StdDraw.show();
    }

    public static void makeHUD() {
        StdDraw.filledRectangle(0.0, HEIGHT, 10, 10);
        StdDraw.show();
    }

    public static void enterSeedScreen() {
        StdDraw.text(MENU_WIDTH / 2, MENU_HEIGHT / 4 - 4,
                "Let's generate a new world! Input a seed, then press 's' to begin.");
        StdDraw.show();
    }

    public static void displayEnteredSeed(String seed) {
        StdDraw.clear(Color.BLACK);
        Menu.makeGUI();
        StdDraw.text(MENU_WIDTH / 2, MENU_HEIGHT / 4 -4, "Your seed is: " + seed);
        StdDraw.show();
    }

    public static void lostScreen() {
        Menu.makeGUIBackground();
        Menu.makeCustomMessageScreen("You lost! You failed to get out :(");
        StdDraw.pause(2000);
    }

    public static void winScreen() {
        Menu.makeGUIBackground();
        Menu.makeCustomMessageScreen("You did it! You open the door in time :)");
        StdDraw.pause(2000);
    }

    public static void collectedHeart(){
        StdDraw.textLeft(WIDTH / 2, HEIGHT - 1,
                "You've collected a heart and gained 5 seconds!");
        StdDraw.show();
    }

    public static void saveScreen(){
        Menu.makeGUIBackground();
        Menu.makeCustomMessageScreen("Your game has been saved!");
        StdDraw.pause(1500);
    }

    public static void gameOverScreen(){
        Menu.makeGUIBackground();
        Menu.makeCustomMessageScreen("Do you want to start over (y/n)?");
    }

    public static void endGameScreen(){
        Menu.makeGUIBackground();
        Menu.makeCustomMessageScreen("See you!");
        StdDraw.show();
        StdDraw.pause(800);
        System.exit(0);
    }
}
