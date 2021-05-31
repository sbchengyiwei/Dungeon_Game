package byow.Core.Agent;


public class Location {
     public int x;
     public int y;

    public Location(int x, int y) {
        this.x = x;
        this.y = y;
    }


    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public void setLocation(Location loc) {
        this.x = loc.getX();
        this.y = loc.getY();
    }
}
