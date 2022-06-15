package AntProject;

// Class helps handle movement of ants
public class Location {
    /**************
     * attributes
     *************/
    private int x;
    private int y;

    /**
     * Constructor
     *
     * @param x axis for the grid
     * @param y axis for the grid
     */
    public Location(int x, int y) {
        setX(x);
        setY(y);
    }
    //Get and Set methods for X & Y axis
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

}
