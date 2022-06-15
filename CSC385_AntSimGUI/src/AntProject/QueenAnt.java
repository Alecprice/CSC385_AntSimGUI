/**********************************************************************************************************
 * The queen ant is responsible for hatching new ants. The specific requirements for the queen ant are:
 1. The queen never moves from her square (i.e., she remains in the same square for the entire simulation).
 2. The queen's maximum lifespan is 20 years.
 3. The queen hatches new ants at a constant rate of 1 ant/day (i.e., 1 ant every 10 turns).
 4. New ants should always be hatched on the first turn of each day.
 The type of ant that is hatched should be determined randomly according to the initial frequencies listed below. You may
 change these frequencies as you see fit — these are simply suggestions for a starting point.
 5.
 a. Forager - 50%
 b. Scout - 25%
 c. Soldier - 25%
 6. The queen should consume 1 unit of the food in her chamber on each turn, including the turn in which she hatches a new ant.
 7. If the food level in the queen's square is zero when the queen tries to eat, the queen dies of starvation.
 8. If the queen dies, either by starvation or by a Bala attack, the simulation should end immediately.
 ************************************************************************************************************/
package AntProject;

public class QueenAnt extends AntBasics {

    /**
     * Constructor
     *
     * @param antID is the unique identifier of the ants
     * @param location is the coordinates inside the [27][27] grid
     */
    public QueenAnt(int antID, Location location) {
        // Queen antID is 0 & Location doesnt change for the Queen
        super(antID, location);
        // ant types (0) Queen, (1) Scout, (2) Forager, (3) Soldier, (4) Bala
        setAntType(0);
        setAge(0);
    }

    /**
     * Randomly hatches a ant based on given percentages at a rate of 1 ant per day
     * 	50% chance of ForagerAnt
     * 	25% chance of ScoutAnt
     * 	25% chance of SoldierAnt
     *
     * @param antCount The total ant count used as a unique ID for ant
     * @return The newly hatched ant
     */
    public AntBasics hatchAnt(int antCount) {
        boolean isHatching;

        int randomHatch = RandomHelper.randomNumbers(4);

        // 25% chance
        if(randomHatch == 0) {
            return new ScoutAnt(antCount, new Location(13, 13));
        }
        // 25% chance
        else if(randomHatch == 1) {
            return new SoldierAnt(antCount, new Location(13, 13));
        }
        // 50% chance
        else {
            return new ForagerAnt(antCount, new Location(13, 13));

        }

    }

    public boolean isHatching(boolean isHatching){

        if(isHatching = false){

        }
        return isHatching;
    }

    /**
     * The queen ant eats 1 unit of food per turn.
     *
     * @param colonyNode The nest node
     * @return true if the nest node has food, otherwise false
     */
    public boolean eating(ColonyNode colonyNode) {
        boolean eat = false;
        if(colonyNode.getFoodAmount() == 0){
            eat = false;
        }else if(colonyNode.getFoodAmount() > 0) {

            int foodAmountAfterEating = colonyNode.getFoodAmount() - 1;
            colonyNode.setFoodQuantity(foodAmountAfterEating);
            eat = true;
        }else{
            //This should not print now
            System.out.println("ISSUE: Issues with queen eating method in QueenAnt.java");
        }
        return eat;
    }

}
