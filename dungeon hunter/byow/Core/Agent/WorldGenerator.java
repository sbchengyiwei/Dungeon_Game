package byow.Core.Agent;


import byow.Core.StartGame.Engine;
import byow.Core.StartGame.RandomUtils;
import byow.Core.Entity.Room;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.ArrayList;
import java.util.Random;

public class WorldGenerator {

    private int WIDTH;
    private int HEIGHT;
    private static long SEED;
    private Random RANDOM;
    private Room room;

    private static TETile[][] world;
    public WorldGenerator(TETile[][] w, long s) {
        WIDTH = w.length;
        HEIGHT = w[0].length;
        SEED = s;
        RANDOM = new Random(SEED);
        room = new Room();
        world = w;

    }



    public static TETile[][] getWorld() {
        return world;
    }

    public static TETile[][] generateWorld(long seed) {
        world = new TETile[Engine.WIDTH][Engine.HEIGHT];
        WorldGenerator worldGenerator = new WorldGenerator(world, seed);
        worldGenerator.clearWorld();
        worldGenerator.randomizeWorld();
        System.out.println(world[0][0].description());
        return world;
    }


    public void clearWorld() {
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
    }

    public void randomizeRoom() {
        int index = 0;
        int count = 0;
        int w, h;
        int numRoom = RandomUtils.uniform(RANDOM, 25, 35);
        System.out.println("Room Number : " + numRoom);

        while (index < numRoom) {
            int x = RandomUtils.uniform(RANDOM, 0, WIDTH);
            int y = RandomUtils.uniform(RANDOM, 0, HEIGHT);
            if (numRoom <= 18) {
                w = RandomUtils.uniform(RANDOM, 10, 15);
                h = RandomUtils.uniform(RANDOM, 10, 15);
            }
            if (numRoom > 18 && numRoom <= 30) {
                w = RandomUtils.uniform(RANDOM, 6, 15);
                h = RandomUtils.uniform(RANDOM, 6, 15);
            } else {
                w = RandomUtils.uniform(RANDOM, 5, 10);
                h = RandomUtils.uniform(RANDOM, 5, 10);
            }
            Room newRoom = new Room(x, y, w, h);
            if (!outbound(newRoom) && !intersects(newRoom)) {
                room.putRoom(world, newRoom);
                index++;
            }
            count++;

            if(count >= 10000){
                index = numRoom;
            }
        }
    }

    public void horizontalRaycasting() {
        for (Room r : room.rooms) {
            boolean detect = false;
            int goalX = 0;
            for (int x = r.getX2() + 1; x < WIDTH; x++) {
                if (world[x][r.getCenterY()] == Tileset.WALL && world[x + 1][r.getCenterY()] != Tileset.NOTHING
                && world[x + 1][r.getCenterY()] != Tileset.WALL) {
                    detect = true;
                    goalX = x;
                    break;
                }
            }
            if (detect) {
                for (int x = r.getX2(); x <= goalX + 1; x++) {
                    if (world[x][r.getCenterY() - 1] == Tileset.NOTHING) {
                        world[x][r.getCenterY() - 1] = Tileset.WALL;
                    }
                    if (world[x][r.getCenterY() + 1] == Tileset.NOTHING) {
                        world[x][r.getCenterY() + 1] = Tileset.WALL;
                    }
                    world[x][r.getCenterY()] = Tileset.FLOOR;
                }

            }
        }
    }

    public void verticalRaycasting() {
        for (Room r : room.rooms) {
            boolean detect = false;
            int goalY = 0;
            for (int y = r.getY2() + 1; y < HEIGHT; y++) {
                if (world[r.getCenterX()][y] == Tileset.WALL && world[r.getCenterX()][y + 1] != Tileset.NOTHING
                && world[r.getCenterX()][y + 1] != Tileset.WALL) {
                    detect = true;
                    goalY = y;
                    break;
                }
            }


            if (detect) {
                for (int y = r.getY2(); y <= goalY; y++) {
                    if (world[r.getCenterX() - 1][y] == Tileset.NOTHING) {
                        world[r.getCenterX() - 1][y] = Tileset.WALL;
                    }
                    if (world[r.getCenterX() + 1][y] == Tileset.NOTHING) {
                        world[r.getCenterX() + 1][y] = Tileset.WALL;
                    }
                    world[r.getCenterX()][y] = Tileset.FLOOR;
                }
            }
        }
    }


    public void checkConnection(){
        ArrayList<Room> deleteRoom = new ArrayList<>();
        for (Room r : room.rooms) {
            Boolean connect = false;
            for (int x = r.getX1(); x <= r.getX2(); x++) {
                if (world[x][r.getY1()] != Tileset.WALL) {
                    connect = true;
                }
            }
            for (int x = r.getX1(); x <= r.getX2(); x++) {
                if (world[x][r.getY2()] != Tileset.WALL) {
                    connect = true;
                }
            }
            for (int y = r.getY1(); y <= r.getY2(); y++) {
                if (world[r.getX1()][y] != Tileset.WALL) {
                    connect = true;
                }
            }
            for (int y = r.getY1(); y <= r.getY2(); y++) {
                if (world[r.getX2()][y] != Tileset.WALL) {
                    connect = true;
                }
            }
            if(!connect) {
                deleteRoom.add(r);
                deleteRoom(r);
            }
        }
        for (Room r: deleteRoom){
            room.rooms.remove(r);
        }
    }

    public void deleteRoom(Room r){
        for (int x = r.getX1(); x <= r.getX2(); x++) {
            for(int y = r.getY1(); y <= r.getY2(); y++){
                world[x][y] = Tileset.NOTHING;
            }
        }
    }
    public void randomizeWorld() {
        randomizeRoom();
        horizontalRaycasting();
        verticalRaycasting();
        checkConnection();

    }

    private boolean outbound(Room r) {
        if (r.getX2() >= Engine.WIDTH - 2
                || r.getY2() >= Engine.HEIGHT - 2
                || r.getX1() <= 2 || r.getY1() <= 2) {
            return true;
        }
        return false;
    }

    private boolean intersects(Room r) {
        for (int i = r.getX1(); i <= r.getX2(); i++) {
            for (int j = r.getY1(); j <= r.getY2(); j++) {
                if (world[i][j] != Tileset.NOTHING) {
                    return true;
                }
            }
        }
        return false;
    }
}
