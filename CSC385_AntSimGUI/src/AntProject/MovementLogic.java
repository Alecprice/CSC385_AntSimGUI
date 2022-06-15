package AntProject;

import java.util.ArrayList;

public class MovementLogic {

    // [0] starting upper right most node
    private static final int LOWER_BOUND = 0;
    // [26] bottom left most node
    private static final int UPPER_BOUND = 26;

    /**
     * Gets and returns a list of all the adjacent locations.
     *
     * @param location The starting location
     * @return List of adjacent locations
     */
    public static ArrayList<Location> getUnrestrictedMoves(Location location) {
        //Stores specific movement type (Unrestricted movement) for ants to use depending on the type of ant
        ArrayList<Location> moves = new ArrayList<Location>();
        //Checks to make sure the ant is inside the colony and moment is in bounds of the colony accordingly
        for (int xAxis = -1; xAxis <= 1; xAxis++) {
            for (int yAxis = -1; yAxis <= 1; yAxis++) {
                int x = location.getX() + xAxis;
                int y = location.getY() + yAxis;
                //Checking to make sure ant is in bounds of the colony and is not repeating movement
                if (inBoundary(x, y) && acceptableXY(xAxis, yAxis)) {
                    //Store location in moves for Bala and Scouts to move around unrestricted
                    moves.add(new Location(x, y));
                }
            }
        }
        //Return array list of unrestricted movement for Bala and Scouts
        return moves;
    }

    /**
     * Gets and returns a list of all the adjacent locations in which the ColonyNode
     * has been discovered.
     *
     * @param grid     The 2D array that makes up the colony [27][27]
     * @param location The starting location of the ant that turn
     * @return List of discovered locations
     */
    public static ArrayList<Location> getRestrictedMoves(ColonyNode[][] grid, Location location) {
        //Stores specific movement type (Restricted movement) for ants to use depending on the type of ant
        ArrayList<Location> moves = new ArrayList<Location>();
        //Checks to make sure the ant is inside the colony
        for (int xAxis = -1; xAxis <= 1; xAxis++) {
            for (int yAxis = -1; yAxis <= 1; yAxis++) {
                int x = location.getX() + xAxis;
                int y = location.getY() + yAxis;
                //Checking to make sure ant is in bounds of the colony and is not repeating movement
                if (inBoundary(x, y) && acceptableXY(xAxis, yAxis)) {
                    ColonyNode node = grid[x][y];
                    //If the node is visible store the location in the array list moves
                    if (node.isDiscovered()) {
                        moves.add(new Location(x, y));
                    }
                }
            }
        }
        //Return array of restricted moves for Soldier and Forager
        return moves;
    }

    /**
     * Determines if a difference in the movement is acceptable (Java 2D game logic)
     * Helps prevent ants from not moving or repeating movement
     *
     * @param x The x Coordinate
     * @param y The y Coordinate
     * @return true if both x and y are not 0 if 0 than return false
     */
    private static boolean acceptableXY(int x, int y) {
        boolean isAcceptableXY = false;
        if(x == 0 && y == 0){
            isAcceptableXY = false;
        }else if (!(x == 0 && y == 0)) {
            isAcceptableXY = true;
        }else{
            //This should never print
            System.out.println("ISSUE: Acceptable X & Y Coordinates Issues In MovementLogic.java  ");
        }
        return isAcceptableXY;
    }

    /**
     * Determines if both x and y Coordinates are within the set boundary
     *
     * @param x The x Coordinate
     * @param y The y Coordinate
     * @return true if within boundary of the colony [0][0] -> [26][26]
     */
    private static boolean inBoundary(int x, int y) {
        boolean inBoundary = false;
        if(!(x >= LOWER_BOUND && x <= UPPER_BOUND && y >= LOWER_BOUND && y <= UPPER_BOUND)){
            inBoundary = false;
        }else if(x >= LOWER_BOUND && x <= UPPER_BOUND && y >= LOWER_BOUND && y <= UPPER_BOUND){
            inBoundary = true;
        }else{
            System.out.println("ISSUE: Upper and Lower Bound Issue IN MovementLogic.java ");
        }
        return inBoundary;
    }

}
