package AntProject;

import java.util.ArrayList;
import java.util.LinkedList;

public class SoldierAnt extends MoveAnt implements AntInterface {

    /**
     *
     * @param antID
     * @param location
     */
    public SoldierAnt(int antID, Location location) {
        super(antID, location);
        // ant types (0) Queen, (1) Scout, (2) Forager, (3) Soldier, (4) Bala
        setAntType(3);
        setAge(0);
    }

    /**
     * Overrides the Interface "AntInterface" method
     * updateAction is used on Soldier to update the location and mode the ant is in
     *
     * @param grid The colony grid [27][27]
     * @param ants The ants in the colony
     */
    @Override
    public void updateAction(ColonyNode[][] grid, LinkedList<AntBasics> ants) {
        ColonyNode node = grid[getLocation().getX()][getLocation().getY()];

        if (node.getBalaCount() > 0) {
            attackEnemy(grid, ants);
        } else {
            findBala(grid);
        }
    }

    /**
     * Attacks a BalaAnt that is in the same colony node. There is a chance that the
     * BalaAnt is already dead, if so the SoldierAnt will move towards the next
     * closest BalaAnt.
     *
     * @param grid The colony grid
     * @param ants The ants in the colony
     */
    private void attackEnemy(ColonyNode[][] grid, LinkedList<AntBasics> ants) {
        LinkedList<AntBasics> canAttack = new LinkedList<AntBasics>();

        for (AntBasics ant : ants) {
            if (attackableEnemy(ant) && ant.isAlive()) {
                canAttack.add(ant);
            }
        }

        if (canAttack.size() > 0) {
            System.out.println("SOLDIER: " + getAntID() + "  Location: " + "[" + getLocation().getX() + "]" + "[" + getLocation().getY() + "] " + " Soldier Attacks ");
            AntBasics antToAttack = canAttack.get(random().nextInt(canAttack.size()));
            int randKill = RandomHelper.randomNumbers(2);
            if (randKill == 1) {
                System.out.println("SOLDIER: " + getAntID() + "  Location: " + "[" + getLocation().getX() + "]" + "[" + getLocation().getY() + "] " + " Soldier Hits ");
                antToAttack.setAlive(false);

            }else{
                System.out.println("SOLDIER: " + getAntID() + "  Location: " + "[" + getLocation().getX() + "]" + "[" + getLocation().getY() + "] " + " Soldier Misses ");
            }

        } else {
            findBala(grid);
        }
    }

    /**
     * Moves towards any nodes that contain BalaAnts, if none are present the
     * SoldierAnt will move randomly.
     *
     * @param grid The colony grid
     */
    private void findBala(ColonyNode[][] grid) {
        ArrayList<Location> adjacentLocations = MovementLogic.getRestrictedMoves(grid, getLocation());
        ArrayList<Location> possibleLocations = new ArrayList<Location>();
        System.out.println("SOLDIER: "+ getAntID() + "  Location: "+ "["+ getLocation().getX()+"]"+"["+ getLocation().getY()+"] "+" Soldier Searching For Bala Ants ");
        for (Location loc : adjacentLocations) {
            ColonyNode node = grid[loc.getX()][loc.getY()];

            if (node.getBalaCount() > 0) {
                possibleLocations.add(loc);
            }
        }

        if (possibleLocations.size() > 0) {
            move(grid, possibleLocations);
        } else {
            move(grid, MovementLogic.getRestrictedMoves(grid, getLocation()));
        }

    }

    /**
     * Determines if a given ant is allowed to be attacked.
     *
     * @param ant The Ant to check if it can be attacked
     * @return true if the ant can be attacked (bala)
     */
    private boolean attackableEnemy(AntBasics ant) {
        boolean canAttack = false;
        if(ant instanceof BalaAnt){
            if(ant.getLocation().getX() == getLocation().getX() && ant.getLocation().getY() == getLocation().getY()){
                canAttack =true;
            }
        }else if(!(ant instanceof BalaAnt)){
            canAttack = false;
        }else{
            System.out.println("ISSUE: Issue with attackable enemy method in SoldierAnt.java");
        }
        return canAttack;
    }
}
