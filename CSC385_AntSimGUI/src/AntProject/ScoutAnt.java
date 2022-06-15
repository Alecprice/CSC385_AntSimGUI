/*******************************************************************************************************************************
 Scouts are responsible for enlarging the foraging area available to the foragers. The specific requirements for the scout ant are:
 Scouts should always randomly pick one of the eight possible directions of movement when it is their turn to do something.
 a. If the chosen square is open, the scout should simply move into that square.
 If the chosen square is closed, the scout should move into that square and the contents of that square should be
 revealed.
 b.
 1.
 Whenever a closed square is revealed, there is a chance of there being food in the square, according to the following
 frequency:
 a. There is a 25% chance that the square will contain a random amount of food between 500 and 1000 units.
 b. The other 75% of the time the square is empty.
 You can predetermine the contents of all the squares at the beginning of the simulation, or you can dynamically
 determine the contents of each square as it is opened
 *************************************************************************************************************************/
package AntProject;

import java.util.LinkedList;

public class ScoutAnt extends MoveAnt implements AntInterface {

    /**
     * Constructor
     *
     * @param antID is the unique identifier of the ants
     * @param location is the coordinates inside the [27][27] grid
     */
    public ScoutAnt(int antID, Location location) {
        super(antID, location);
        // ant types (0) Queen, (1) Scout, (2) Forager, (3) Soldier, (4) Bala
        setAntType(1);
        setAge(0);
    }

    /**
     * Overrides the Interface "AntInterface" method
     * updateAction is used on ScoutAnt to update the location and mode the ant is in
     *
     * @param grid The colony grid [27][27]
     * @param ants The ants in the colony
     */
    @Override
    public void updateAction(ColonyNode[][] grid, LinkedList<AntBasics> ants) {
        //movement logic that is not impeded by closed nodes & capable of moving in all directions
        move(grid, MovementLogic.getUnrestrictedMoves(getLocation()));

        ColonyNode node = grid[getLocation().getX()][getLocation().getY()];

        if (!node.isDiscovered()) {
            System.out.println("SCOUT: "+ getAntID() + "    Location : "+ "["+ getLocation().getX()+"]"+"["+ getLocation().getY()+"] "+"Scout Has Uncovered Hidden Node @ " + "["+getLocation().getX() + "]"+"["+ getLocation().getY()+"]");
            node.showNode(true);
            //Dynamically generate food as scout ants open closed nodes
            randomlyGenerateFood(node);
        }else{
            System.out.println("SCOUT: "+ getAntID() + "    Location : "+ "["+ getLocation().getX()+"]"+"["+ getLocation().getY()+"] " + "Scouts Current Node");
        }
    }

    /**
     *  Has a 25% chance to generate 500 to 1000 units of food in a newly discovered node
     *  (Dynamically generated food)
     *
     * @param node The colony node to generate food
     */
    private void randomlyGenerateFood(ColonyNode node) {
        if (random().nextInt(100) + 1 <= 25) {
            node.setFoodQuantity(random().nextInt(1000 - 500 + 1) + 500);
        }
    }

}
