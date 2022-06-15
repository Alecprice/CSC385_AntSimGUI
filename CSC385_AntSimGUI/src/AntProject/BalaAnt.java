/**********************************BALA ANTS*****************************************************
 Bala ants are enemies of the colony. They should enter only at the periphery of the colony
 (i.e., they should not simply pop up in
 the middle of the colony). Once in the colony they may move around freely. Assume they never leave
 the colony once they enter it.
 The specific requirements for the Bala ant are:
 1.Each turn there is a 3% chance one Bala ant will appear in one of the squares at the boundary of the colony. You may choose
 to have Bala ants always enter at the same square (e.g., upper left corner), or you may have them enter randomly at any of
 the 106 squares on the edge of the colony.
 2. Once a Bala appears, it should remain in the environment until it is killed, or dies of old age.
 3. Bala ants should always move randomly.
 4. Bala ants may move into squares that have not yet been revealed by scout ants.
 5.If a Bala ant is in a square containing one or more friendly ants (scout, forager, soldier, queen),
 the Bala should attack one of
 those ants. The ant that is attacked can be selected at random
 During an attack, there is a 50% chance a Bala kills the ant it attacks; otherwise, the Bala misses and the ant
 that is attacked
 survives.
 ***********************************************************************************************************/
package AntProject;

import java.util.LinkedList;


public class BalaAnt extends MoveAnt implements AntInterface {
    /****************
     * attributes
     ****************/
    public final int ONE = 1;


    /**
     * Constructor
     *
     * @param antID is the unique identifier of the ants
     * @param location is the coordinates inside the [27][27] grid
     */
    public BalaAnt(int antID, Location location) {

        super(antID, location);
        // ant types (0) Queen, (1) Scout, (2) Forager, (3) Soldier, (4) Bala
        setAntType(4);
        setAge(0);
    }

    /***********
     * methods
     ***********/

    /**
     * Overrides the Interface "AntInterface"
     * updateAction is used on Bala to update the location and mode the ant is in
     *
     * @param grid The colony grid [27][27]
     * @param ants The ants in the colony
     */
    @Override
    public void updateAction(ColonyNode[][] grid, LinkedList<AntBasics> ants) {
        ColonyNode node = grid[getLocation().getX()][getLocation().getY()];

        if (antsInRange(node)) {
            attackEnemy(grid, ants);
        } else {
            System.out.println("BALA: "+ getAntID() + " Location: "+ "["+ getLocation().getX()+"]"+"["+ getLocation().getY()+"] "+" Bala Moves ");
            move(grid, MovementLogic.getUnrestrictedMoves(getLocation()));
        }

    }

    /**
     * The Bala ant will attack an enemy ant inside the same node
     * If more than one enemy ant inside the node the ball will randomly choose
     * only one ant to attack
     *
     * @param grid The colony grid [27][27]
     * @param ants The ants in the colony
     */
    private void attackEnemy(ColonyNode[][] grid, LinkedList<AntBasics> ants) {
        //Used to store the list of ants that the bala can choose from to attack
        LinkedList<AntBasics> antsToAttack = new LinkedList<AntBasics>();
        // ForEach loop goes through all ants in the array list and picks out all the ants that are alive and can be attacked by the bala
        for (AntBasics ant : ants) {
            if (canAttack(ant) && ant.isAlive())
                // adds to the list of ants the bala can attack
                antsToAttack.add(ant);
        }

        // Pick a random ant to attack if there is more than one enemy inside of the same node
        if (antsToAttack.size() > 0) {
            AntBasics antToAttack = antsToAttack.get(random().nextInt(antsToAttack.size()));
            System.out.println("BALA: "+ getAntID() + " Location: "+ "["+ getLocation().getX()+"]"+"["+ getLocation().getY()+"] "+"Bala Attacks");
            int randomKill = RandomHelper.randomNumbers(2);
            // While attacking there is a 50% chance to kill enemy.
            if (randomKill==1) {
                System.out.println("BALA: "+ getAntID() + " Location: "+ "["+ getLocation().getX()+"]"+"["+ getLocation().getY()+"] "+"Bala KILLS");
                antToAttack.setAlive(false);

                // ForagerAnt will drop the food it has in the node it's in if killed by the bala while carrying food
                if (antToAttack instanceof ForagerAnt) {
                    if (((ForagerAnt) antToAttack).hasFood()) {
                        System.out.println("Forager Drops Food");
                        ColonyNode node = grid[antToAttack.getLocation().getX()][antToAttack.getLocation().getY()];
                        node.setFoodQuantity(node.getFoodAmount() + ONE);
                    }
                }
            }else{
                System.out.println("BALA: "+ getAntID() + " Location: "+ "["+ getLocation().getX()+"]"+"["+ getLocation().getY()+"] "+"Bala NoT kIlL");
            }
        } else {
            move(grid, MovementLogic.getUnrestrictedMoves(getLocation()));
        }
    }

    /**
     * Search for enemy ants within the same node as the bala
     *
     * @param node The ColonyNode to search in for enemies
     * @return True if enemies are in the node & false if no enemies present in the searching node
     */
    private boolean antsInRange(ColonyNode node) {
        return (node.getScoutCount() > 0 || node.getSoldierCount() > 0 || node.getForagerCount() > 0 || node.isQueenPresent());
    }

    //This is a helper method to remove repetitive logic
    /**
     * 	Method used to find out if the ant should be able to be attacked or not
     *
     * @param ant The ant that needs to be checked to see if it can be attacked
     * @return True if the ant can be attacked & false if not
     */
    private boolean canAttack(AntBasics ant) {
        return ant.getLocation().getX() == getLocation().getX() && ant.getLocation().getY() == getLocation().getY() && !(ant instanceof BalaAnt);
    }
}
