package AntProject;

import java.util.ArrayList;

// Class helps handle movement of ants
public class MoveAnt extends AntBasics {
    /**
     * Constructor
     *
     * @param antID is the unique identifier of the ants
     * @param location is the coordinates inside the [27][27] grid
     */
    public MoveAnt(int antID, Location location) {
        super(antID, location);
    }

    /**
     * Moves the ant to a new location in the colony and remove the ant from the old location
     *
     * @param grid The colony grid
     * @param newLocation The new location of the ant
     */
    public void move(ColonyNode[][] grid, Location newLocation) {
        //Removes ant from previous location
        grid[getLocation().getX()][getLocation().getY()].removeAnt(this);
        //Sets locations to move ant to
        setLocation(newLocation);
        //Adds and to the new location
        grid[getLocation().getX()][getLocation().getY()].addAnt(this);
    }

    /**
     * Randomly chooses a new location for the ant and then moves the ant to that location
     *
     * @param grid The colony grid
     * @param possibleLocations The list of possible locations to move to
     */
    public void move(ColonyNode[][] grid, ArrayList<Location> possibleLocations) {
        //Sets new location randomly from the stored arraylist of possible locations ants can move to
        Location newLocation = possibleLocations.get(random().nextInt(possibleLocations.size()));
        //Sets the new location
        move(grid, newLocation);
    }
}
