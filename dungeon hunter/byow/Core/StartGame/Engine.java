package byow.Core.StartGame;
import byow.Core.Agent.Location;
import byow.Core.Agent.Menu;
import byow.Core.Agent.WorldGenerator;
import byow.TileEngine.TERenderer;
import byow.TileEngine.*;
import byow.Core.Entity.Room;
import java.util.ArrayList;
import edu.princeton.cs.introcs.StdDraw;
import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.Date;
import java.util.Random;


public class Engine {

    public static final int WIDTH = 75;
    public static final int HEIGHT = 45;
    private long SEED;
    private Random RANDOM;

    TERenderer ter = new TERenderer();
    private TETile[][] world;
    private Location player;
    private ArrayList<Location> keys;


    private static boolean GAMEOVER = false;
    private int HEALTH = 0;
    private int Keys;
    private int TIMELEFT;
    private String message = null;


    private String input = "";

    /**
     * Method used for exploring a fresh world. This method should handle all inputs,
     * including inputs from the main menu.
     */
    public void interactWithKeyboard() {

        Menu.initializeGUI();

        while (true) {
            if (!StdDraw.hasNextKeyTyped()) {
                continue;
            }
            switch (Character.toLowerCase(StdDraw.nextKeyTyped())) {
                // create new game
                case ('n'): {
                    playTheNewWorld();
                    break;
                }
                case ('l'): {
                    loadTheWorld();
                    break;
                }
                case ('q'): {
                    /* Quit Operations */
                    GAMEOVER = true;
                    System.exit(0);
                    break;
                }
            }
        }
    }


    public void initializeWorld() {
        ter.initialize(Engine.WIDTH, Engine.HEIGHT);

        GAMEOVER = false;
        HEALTH = 0;
        Keys = 0;
        TIMELEFT = 20;
        message = null;

        world = WorldGenerator.generateWorld(SEED);
        player = makePlayer();
        putHearts();
        putDoor();
        keys = makeKeys();
        displayKeys();
        ter.renderFrame(world);
    }


    public void playTheNewWorld(){
        Menu.enterSeedScreen();
        String seed = "";
        while(true) {
            if (!StdDraw.hasNextKeyTyped()) {
                continue;
            }
            char key = Character.toLowerCase(StdDraw.nextKeyTyped());
            if (key == 's') {
                setupSeed(seed);
                input = SEED + " ";
                initializeWorld();
                break;
            } else if (Character.isDigit(key)) {
                seed += key;
            } else {
                // Ignore the input
            }
            Menu.displayEnteredSeed(seed);
        }
        System.out.println("Let's play the game");
        playWorld(world);
    }

    public void loadTheWorld(){
        input = loadWorld();
        setupSeed(input.substring(0, input.indexOf(" ")));
        String move = input.substring(input.indexOf(" ") + 1);
        initializeWorld();
        replay(move);
        playWorld(world);
    }

    // set the seed
    public void setupSeed(String seed) {
        SEED = Long.parseLong(seed);
        RANDOM = new Random(SEED);
        System.out.println("## SEED: " + SEED);
    }

    //replay
    public void replay(String move) {
        for (int i = 0; i < move.length(); i++) {
            move(move.charAt(i));
            ter.renderFrame(world);
            StdDraw.pause(100);
        }
        HEALTH = 0;
    }

    public void putHearts() {
        ArrayList<Location> heartlocs = new ArrayList<>();
        for (Room room: Room.rooms) {
            int x = room.getCenterX();
            int y = room.getCenterY();
            if (world[x][y] == Tileset.FLOOR) {
                Location loc = new Location(x, y);
                heartlocs.add(loc);
            }
            for (Location l: heartlocs) {
                world[l.getX()][l.getY()] = Tileset.HEART;
            }
        }
    }

    public void putDoor() {

        while (true) {
            int xx = 0 + RANDOM.nextInt(68);
            int yy = 0 + RANDOM.nextInt(44);
            if (world[xx][yy] == Tileset.WALL && (world[xx + 1][yy] == Tileset.FLOOR
                    || world[xx - 1][yy] == Tileset.FLOOR
                    || world[xx][yy + 1] == Tileset.FLOOR
                    || world[xx][yy - 1] == Tileset.FLOOR)) {
                world[xx][yy] = Tileset.LOCKED_DOOR;
                break;
            }
        }

    }




    public Location makePlayer() {
        int index = RANDOM.nextInt(Room.getRooms().size());
        Room room = (Room) Room.getRooms().get(index);
        int x = room.getCenterX();
        int y = room.getCenterY();
        Location p = new Location(x, y);
        world[p.getX()][p.getY()] = Tileset.AVATAR;
        System.out.println("Set Avatar");
        return p;
    }


    public ArrayList<Location> makeKeys() {
        int index;
        int numRoom = Room.getRooms().size();

        if(numRoom <= 10){
            Keys = 1;
        } else {
            Keys = numRoom / 10;
        }
        ArrayList<Location> keys = new ArrayList<>();
        for(int i = 1; i <= Keys; i++) {
            index = RANDOM.nextInt(Room.getRooms().size());
            Room room = (Room) Room.getRooms().get(index);
            int x = room.getCenterX();
            int y = room.getCenterY();
            Location loc = new Location(x, y);

            if (world[x][y] != Tileset.AVATAR && world[x][y] != Tileset.KEY) {
                keys.add(loc);
            } else {
                i -= 1;
            }
        }
        return keys;
    }

    public void displayKeys() {
        for (Location loc: keys) {
            world[loc.getX()][loc.getY()] = Tileset.KEY;
        }
    }

    private void playWorld(TETile[][] w) {
        Thread worker = new Thread(() -> {
            while (TIMELEFT > 0) {
                StdDraw.enableDoubleBuffering();
                TIMELEFT--;
                if (TIMELEFT == 0) {
                    GAMEOVER = true;
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }}});

        worker.start();
        char key;
        String move = "";

        while (!GAMEOVER) {
            if (message != null) {
                StdDraw.text(WIDTH / 5, 1, "Zen Message:" +message);
                StdDraw.show();
            }
            mouseHover();
            if (!StdDraw.hasNextKeyTyped()) {
                continue;
            }
            key = Character.toLowerCase(StdDraw.nextKeyTyped());
            move += key;

            if (TIMELEFT == 0) {
                GAMEOVER = true;
                Menu.lostScreen();
            }

            if (HEALTH == 1) {
                TIMELEFT += 2;
                HEALTH = 0;
                Menu.collectedHeart();
            }

            for (int i = 0; i < move.length() - 1; i += 1) {
                if (move.charAt(i) == ':' && move.charAt(i + 1) == 'q') {
                    input += move.substring(0, i);
                    saveWorld(input);
                    GAMEOVER = true;
                }
            }

            move(key);
        }

        if(GAMEOVER) {
            Menu.gameOverScreen();
            while (true) {
                if (!StdDraw.hasNextKeyTyped()) {
                    continue;
                }
                char input = Character.toLowerCase(StdDraw.nextKeyTyped());
                if (input == 'y') {
                    worker.stop();
                    interactWithKeyboard();
                } else if(input == 'n') {
                    //Menu.lostScreen();
                    Menu.endGameScreen();
                } else {
                    // Nothing happens
                }
            }
        }
    }

    // get things or open the door
    private void touchEntity(Location loc) {
        if (world[loc.getX()][loc.getY()] == Tileset.HEART) {
            HEALTH += 1;
        }
        if (world[loc.getX()][loc.getY()] == Tileset.KEY) {
            Keys -= 1;
        }
        if (world[loc.getX()][loc.getY()] != Tileset.WALL && world[loc.getX()][loc.getY()] != Tileset.LOCKED_DOOR) {

            world[loc.getX()][loc.getY()] = Tileset.AVATAR;

            world[player.getX()][player.getY()] = Tileset.FLOOR;
            player = loc  ;
        }
        if (world[loc.getX() - 1][loc.getY()].equals(Tileset.LOCKED_DOOR)) {

            if (Keys == 0){
                world[player.getX() - 1][player.getY()] = Tileset.UNLOCKED_DOOR;
                Menu.winScreen();
                GAMEOVER = true;
            }

        }
        if (world[player.getX()][player.getY() + 1].equals(Tileset.LOCKED_DOOR)) {

            if (Keys == 0){
                world[player.getX()][player.getY() + 1] = Tileset.UNLOCKED_DOOR;
                Menu.winScreen();
                GAMEOVER = true;
            }
        }
        if (world[player.getX() + 1][player.getY()].equals(Tileset.LOCKED_DOOR)) {

            if (Keys == 0){
                world[player.getX() + 1][player.getY()] = Tileset.UNLOCKED_DOOR;
                Menu.winScreen();
                GAMEOVER = true;
            }
        }
        if (world[player.getX()][player.getY() - 1].equals(Tileset.LOCKED_DOOR)) {

            if (Keys == 0){
                world[player.getX()][player.getY() - 1] = Tileset.UNLOCKED_DOOR;
                Menu.winScreen();
                GAMEOVER = true;
            }

        }


    }

    private void move(char key) {
        Location newplayerlocation;
        switch (key) {
            case ('w'): {
                newplayerlocation = new Location(player.getX(), player.getY() + 1);
                break;
            }
            case ('s'): {
                newplayerlocation = new Location(player.getX(), player.getY() - 1);
                break;
            }
            case ('a'): {
                newplayerlocation = new Location(player.getX() - 1, player.getY());
                break;
            }
            case ('d'): {
                newplayerlocation = new Location(player.getX() + 1, player.getY());
                break;
            }
            default:
                return;
        }
        touchEntity(newplayerlocation);

    }

    private void mouseHover() {
        //System.out.println("Before coordinates");
        int mx = (int) StdDraw.mouseX();
        int my = (int) StdDraw.mouseY();
        //System.out.println("After coordinates");

        // check if loc in image!!!! important
        Location loc = new Location(mx, my);
        if ((loc.getX() >= 0 && loc.getX() < WIDTH) && (loc.getY() >= 0 && loc.getY() < HEIGHT)) {
            showDescriptions(loc);
        }

        Date date = new Date();

        if (world[player.getX() - 1][player.getY()].equals(Tileset.LOCKED_DOOR)) {
            StdDraw.textLeft(3,  1,
                    "You need to collect all the key first!");
        }
        if (world[player.getX()][player.getY() + 1].equals(Tileset.LOCKED_DOOR)) {
            StdDraw.textLeft(3, 1,
                    "You need to collect all the key first!");
        }
        if (world[player.getX() + 1][player.getY()].equals(Tileset.LOCKED_DOOR)) {

            StdDraw.textLeft(3, 1,
                    "You need to collect all the key first!");
        }
        if (world[player.getX()][player.getY() - 1].equals(Tileset.LOCKED_DOOR)) {

            StdDraw.textLeft(3, 1,
                    "You need to collect all the key first!");
        }
        StdDraw.textLeft(3, HEIGHT - 1,
                "Open the door and get out before the time runs out!");

        StdDraw.textLeft(WIDTH * 3.5 / 5, HEIGHT - 1,
                date.toString());
        StdDraw.text(WIDTH / 2, HEIGHT - 1,
                "Time Left: " + TIMELEFT);
        StdDraw.show();
    }

    private void showDescriptions(Location loc) {
        int mx = loc.getX();
        int my = loc.getY();

        if (world[mx][my].equals(Tileset.WALL)) {
            ter.renderFrame(world);
            StdDraw.enableDoubleBuffering();
            StdDraw.setPenColor(Color.white);
            StdDraw.text(WIDTH * 4 / 5, 1, "wall");
            StdDraw.text(WIDTH / 2, 1, "A wall! You cannot through it!");
        } else if (world[mx][my].equals(Tileset.AVATAR)) {
            ter.renderFrame(world);
            StdDraw.enableDoubleBuffering();
            StdDraw.setPenColor(Color.white);
            StdDraw.text(WIDTH * 4 / 5, 1, "you");
            StdDraw.text(WIDTH / 2, 1, "That's you! And you're running out of time!");

        } else if (world[mx][my].equals(Tileset.FLOOR)) {
            ter.renderFrame(world);
            StdDraw.enableDoubleBuffering();
            StdDraw.setPenColor(Color.white);
            StdDraw.text(WIDTH * 4 / 5, 1, "floor");
            StdDraw.text(WIDTH / 2, 1, "The floor! You are walking on it!");
        } else if (world[mx][my].equals(Tileset.HEART)) {
            ter.renderFrame(world);
            StdDraw.enableDoubleBuffering();
            StdDraw.setPenColor(Color.white);
            StdDraw.text(WIDTH * 4 / 5, 1, "heart");
            StdDraw.text(WIDTH / 2, 1, "A heart! Collect it to extend your time!");
        } else if (world[mx][my].equals(Tileset.KEY)) {
            ter.renderFrame(world);
            StdDraw.enableDoubleBuffering();
            StdDraw.setPenColor(Color.white);
            StdDraw.text(WIDTH * 4 / 5, 1, "key");
            StdDraw.text(WIDTH / 2, 1, "A key! Collect it so that you can open the door!");
        }else if (world[mx][my].equals(Tileset.LOCKED_DOOR)) {
                ter.renderFrame(world);
                StdDraw.enableDoubleBuffering();
                StdDraw.setPenColor(Color.white);
                StdDraw.text(WIDTH * 4 / 5, 1, "Locked_door");
                StdDraw.text(WIDTH / 2, 1, "Collect all the keys then you can open it!");

        } else {
            ter.renderFrame(world);
            StdDraw.enableDoubleBuffering();
            StdDraw.setPenColor(Color.white);
            StdDraw.text(WIDTH / 2, 1, "Empty World!");
        }
    }

    /**
     * Method used for autograding and testing your code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The engine should
     * behave exactly as if the user typed these characters into the engine using
     * interactWithKeyboard.
     * <p>
     * Recall that strings ending in ":q" should cause the game to quite save. For example,
     * if we do interactWithInputString("n123sss:q"), we expect the game to run the first
     * 7 commands (n123sss) and then quit and save. If we then do
     * interactWithInputString("l"), we should be back in the exact same state.
     * <p>
     * In other words, both of these calls:
     * - interactWithInputString("n123sss:q")
     * - interactWithInputString("lww")
     * <p>
     * should yield the exact same world state as:
     * - interactWithInputString("n123sssww")
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] interactWithInputString(String input) {

        input = input.toLowerCase();
        System.out.println(input);
        String move = "";
        int endSeed = 0;
        int save = input.indexOf(":q");
        char gameMode = input.charAt(0);

        if (gameMode == 'n') {
            endSeed = input.indexOf("s");
            SEED = Long.parseLong(input.substring(1, endSeed));
            RANDOM = new Random(SEED);
            world = WorldGenerator.generateWorld(SEED);
            player = makePlayer();
            keys = makeKeys();
            displayKeys();
        } else if (gameMode == 'l') {
            input = loadWorld();
        } else {
            System.out.println("Incorrect Game Mode");
            return null;
        }

        if (save != -1) {
            move = input.substring(endSeed + 1, save);
        } else {
            move = input.substring(endSeed + 1, input.length());
        }

        if (!move.isEmpty()) {
            for (int i = 0; i < move.length(); i++) {
                move(move.charAt(i));
            }
        }

        if (save != -1) {
            saveWorld(input);
        }
        return world;
    }

    private String loadWorld() {
        File file = new File("./save_data.txt");
        if (file.exists()) {
            try {
                FileInputStream fs = new FileInputStream(file);
                ObjectInputStream os = new ObjectInputStream(fs);
                return (String) os.readObject();
            } catch (FileNotFoundException e) {
                System.out.println("File Not Found");
                System.exit(0);
            } catch (IOException e) {
                System.out.println(e);
                System.exit(0);
            } catch (ClassNotFoundException e) {
                System.out.println("Class Not Found");
                System.exit(0);
            }
        }
        System.out.println("No World Saved Yet-- Returning Brand New World");
        playTheNewWorld();
        return null;
    }

    private void saveWorld(String data) {
        File file = new File("./save_data.txt");
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fs = new FileOutputStream(file);
            ObjectOutputStream os = new ObjectOutputStream(fs);
            os.writeObject(data);
            Menu.saveScreen();
        } catch (FileNotFoundException e) {
            System.out.println("File Not Found");
            System.exit(0);
        } catch (IOException e) {
            System.out.println(e);
            System.exit(0);
        }
    }
}

