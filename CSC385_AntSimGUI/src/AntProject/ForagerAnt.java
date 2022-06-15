package AntProject;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;

public class ForagerAnt extends MoveAnt implements AntInterface {
    /***************
     * attributes
     ***************/
    // boolean flag used on Forager ant to decide if the ant should be in Forage mode or Return to nest mode
    private boolean hasFood;
    // boolean flag used on forager to help prevent getting stuck in infinite loop
    private boolean escapeLoop;
    // stack used to store the foragers movement inorder to return back to nest and feed the queen
    private Stack<Location> movementHistory;
    // Instance of the location obj used to set up the foragers movement logic, we need to know where the Forager ant has been
    private Location previousLocation;
    //used to remove magic numbers used for boundaries and other logic consisting of plus or minus 1 logic
    private final int ONE = 1;

    /**
     * Constructor
     *
     * @param antID is the unique identifier of the ants
     * @param location is the coordinates inside the [27][27] grid
     */
    public ForagerAnt(int antID, Location location) {
        super(antID, location);
        hasFood = false;
        escapeLoop = false;
        movementHistory = new Stack<Location>();
        previousLocation = null;
        // ant types (0) Queen, (1) Scout, (2) Forager, (3) Soldier, (4) Bala
        setAntType(2);
        setAge(0);
    }

    /***********
     * methods
     ***********/

    /**
     *
     * @return if the Forager ant has food or not
     */
    public boolean hasFood() {
        return hasFood;
    }

    /**
     * Overrides the Interface "AntInterface"
     * updateAction is used on Forager to update the location and mode the ant is in
     *
     * @param grid The colony grid [27][27]
     * @param ants The ants in the colony
     */
    @Override
    public void updateAction(ColonyNode[][] grid, LinkedList<AntBasics> ants) {
        if (hasFood) {
            //Bringing food back to nest and retracing steps
            returnToNest(grid);
        } else {
            //Looking for food
            forage(grid);
        }
    }


    /**
     * The ant will move one step back according to the movements stored in the stack movementHistory
     *
     * @param grid The colony grid
     */
    public void returnToNest(ColonyNode[][] grid) {
        //final int used to remove magic numbers
        int PHEROMONE_LIMIT = 1000;
        int TEN = 10;
        //Setting current node location
        ColonyNode currentNode = grid[getLocation().getX()][getLocation().getY()];
        //Checking current nodes Pheromones and making sure Forager is not in the nest
        if (currentNode.getPheromone() < PHEROMONE_LIMIT && !inNest(getLocation())) {
            //Getting ants current node & Adding 10 Pheromone to that node
            currentNode.depositPheromone(currentNode.getPheromone() + TEN);
            System.out.println("FORAGER: "+ getAntID() + " Location: "+ "["+ getLocation().getX()+"]"+"["+ getLocation().getY()+"] "+"Forager Is Dropping Pheromone");
        }
        //Tracking Forager movement
        previousLocation = getLocation();
        //Retracing movement via built in pop method on stack
        move(grid, movementHistory.pop());
        System.out.println("FORAGER: "+ getAntID() + " Location: "+ "["+ getLocation().getX()+"]"+"["+ getLocation().getY()+"] "+"Forager Has Food And Retracing Steps");
        // Add food to the nest once inside node [13][13]
        if (inNest(getLocation()) && movementHistory.isEmpty()) {
            grid[13][13].setFoodQuantity(grid[13][13].getFoodAmount() + ONE);
            System.out.println("FORAGER: "+ getAntID() + " Location: "+ "["+ getLocation().getX()+"]"+"["+ getLocation().getY()+"] "+"Forager Places Food At Nest");
           //Reset has food boolean
            hasFood = false;
            //Reset escape boolean
            escapeLoop = false;
        }
    }

    /**
     * The ant will move towards the node with the highest pheromone level until it finds food
     *
     * If the ant is stuck in a pheromone loop it will escape it by moving randomly until it finds food
     *
     * @param grid The colony grid [27][27]
     */
    private void forage(ColonyNode[][] grid) {
        if (!escapeLoop) {
            //Move toward highest Pheromone
            findHighestPheromone(grid);
            //Check to make sure we arent repeating the same steps
            checkForLoops();
        } else {
            //Tracking Forager movement
            previousLocation = getLocation();
            //Add nodes as ant moves to movementHistory/stack via the push method
            movementHistory.push(getLocation());
            //Move ants to new node
            move(grid, MovementLogic.getRestrictedMoves(grid, getLocation()));
        }
        System.out.println("FORAGER: "+ getAntID() + "  Location : "+ "["+ getLocation().getX()+"]"+"["+ getLocation().getY()+"] "+"Forager looking For Food");
       //Setting node location
        ColonyNode node = grid[getLocation().getX()][getLocation().getY()];
      //IF food in node is greater than zero and the Forager is not in the nest
        if (node.getFoodAmount() > 0 && !inNest(getLocation())) {
            //Pick up food from node and subtract from the food quantity in the node
            node.setFoodQuantity(node.getFoodAmount() - ONE);
            System.out.println("FORAGER: "+ getAntID() + "  Location : "+ "["+ getLocation().getX()+"]"+"["+ getLocation().getY()+"] "+"Forager Has Picked Up Food");
            //Set Boolean to true since Forager has food
            hasFood = true;
        }
    }

    /**
     * Moves the ant to the adjacent node with the most pheromone. If multiple nodes
     * have the same pheromone the ant will randomly choose a node to move
     *
     * @param grid The colony grid
     */
    private void findHighestPheromone(ColonyNode[][] grid) {
        ArrayList<Location> adjacentLocations = MovementLogic.getRestrictedMoves(grid, getLocation());
        ArrayList<Location> possibleLocations = new ArrayList<Location>();

        int strongestPheromone = 0;
        // Check each node for pheromone strength
        // Store the highest pheromone amounts & check multiple nodes with high amounts
        // Store in ArrayList, then choose a direction to move in
        for (Location loc : adjacentLocations) {
            ColonyNode node = grid[loc.getX()][loc.getY()];
            if (!lastLoc(loc) && !inNest(loc)) {

                if (node.getPheromone() == strongestPheromone) {
                    possibleLocations.add(loc);
                }

                if (node.getPheromone() > strongestPheromone) {
                    possibleLocations.clear();
                    strongestPheromone = node.getPheromone();
                    possibleLocations.add(loc);
                }
            }
        }

        previousLocation = getLocation();
        movementHistory.push(getLocation());

        if (possibleLocations.size() > 0) {
            move(grid, possibleLocations);
        } else {
            move(grid, movementHistory.peek());
        }
    }

    /**
     * Determines if a ant is stuck in a loop based off the stack of stored movements
     *
     * This is a helper method to prevent the Forager ants from getting stuck inside
     * Sudo random loops
     */
    private void checkForLoops() {
        if (movementHistory.size() > 15) {
            Stack<Location> historyClone = (Stack<Location>) movementHistory.clone();

            int locationCounter = 0;

            while (!historyClone.isEmpty()) {
                Location loc = historyClone.pop();

                if (loc.getX() == getLocation().getX() && loc.getY() == getLocation().getY())
                    locationCounter++;
            }

            if (locationCounter >= 5)
                escapeLoop = true;
        }
    }

    /**
     * Determines if node is the nest.
     *
     * @param loc The location to check
     * @return true if the location to check is the location of the nest else it is false
     */
    private boolean inNest(Location loc) {
        boolean isNest = false;
        if(!(loc.getX() == 13 && loc.getY() == 13)){

        }else if(loc.getX() == 13 && loc.getY() == 13){
            isNest = true;
        }else{
            //This should not be printing out now
            System.out.println("ISSUE: Issues with inNest method in ForagerAnt.java");
        }
        return isNest ;
    }

    /**
     * Determines if a location is equal to the previous location of the ant
     *
     * @param loc The location to check
     * @return true if the last location is equal to the location to check else it is false
     *
     */
    private boolean lastLoc(Location loc) {
        boolean lastLocation = false;
        if (previousLocation == null)
            return false;
        if(!(previousLocation.getX() == loc.getX() && previousLocation.getY() == loc.getY())){
            lastLocation = false;
        }else if(previousLocation.getX() == loc.getX() && previousLocation.getY() == loc.getY()){
            lastLocation = true;
        }else{
            //This should not be printing
            System.out.println("ISSUE: Issues with last location method in ForagerAnt.java");
        }

        return lastLocation;
    }

}
