/*************************COLONY*************************************************
 * The Environment represented using a 27 x 27 square grid
 * Each square in the grid represents a discrete location in the environment
 * Eight directions of movement are possible
 ********************************************************************************/
package AntProject;

import javax.swing.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Stack;


public class Colony {
    /****************
     * attributes
     ****************/
    private LinkedList<AntBasics> ants;
    private int antCounter;
    private QueenAnt queenAnt;
    private ScoutAnt scoutAnt;
    private ForagerAnt foragerAnt;
    private SoldierAnt soldierAnt;
    private ColonyNode[][] colonyGrid;
    private ColonyView colonyView;
    //boolean flag used to test the queens functionality
    private boolean isHatching;
    private int undoRedoPointer = -1;
    private Stack<AntBasics> commandStack = new Stack<>();
    // age of Queen ant
    private static final int QUEEN_MAX_AGE = 73000;
    //age of all ants besides the Queen
    private static final int ANT_MAX_AGE = 3650;
    private int antType;

    /****************
     * Constructor
     ****************/
    public Colony() {
        //Initialize counter to 0
        antCounter = 0;
        //Linked list used to store ants
        ants = new LinkedList<AntBasics>();
        //Initialize queen position in the colony to null
        queenAnt = null;
        //Size of the colony [27][27]
        colonyGrid = new ColonyNode[27][27];
        //Used to display the view colony
        colonyView = new ColonyView(27, 27);

        //Nested for loop to set the x,y values of the colony and display the colony
        for (int x = 0; x < colonyGrid.length; x++) {
            for (int y = 0; y < colonyGrid[x].length; y++) {
                String id = x + ", " + y;
                colonyGrid[x][y] = new ColonyNode(id);
                colonyView.addColonyNodeView(colonyGrid[x][y].getColonyNodeView(), x, y);
            }
        }
    }

    /**
     * Gets the queen ant
     *
     * @return The queen ant
     */
    public QueenAnt getQueenAnt() {
        return queenAnt;
    }

    /**
     * Gets the colony view
     *
     * @return The colony view
     */
    public ColonyView getColonyView() {
        return colonyView;
    }

    /**
     * This is the main setup for the simulation to run
     *
     * Sets up the colony under normal conditions
     *
     * Center Node [13][13]
     * 1. Queen Ant
     * 2. 10 soldier ants
     * 3. 50 Forager Ants
     * 4. 4 Scout Ants
     * 5. 1000 units of food
     *
     */
    public void normalSetupButton() {
		  /*                              The nested for loop displays the grid nodes around the queen
            goes over the nodes [12][12], [12][13], [12][14], [13,12], [13][13], [13][14], [14][12], [14][13], [14][14]
            Sets nodes to visible, over rides if there is food present and then sets node [13][13] food quantity to 1000
     */
        for (int x = 12; x < 15; x++) {
            for (int y = 12; y < 15; y++) {
                colonyGrid[x][y].showNode(true);
                colonyGrid[13][13].setFoodQuantity(0);
            }
        }

        // Center node must start with 1000 units of food
        colonyGrid[13][13].setFoodQuantity(1000);

        // Center node must contain the queen
        queenAnt = new QueenAnt(antCounter, new Location(13, 13));
        addAnts(queenAnt);



        // Center node must start with 50 forager ants
        for (int forager = 0; forager < 50; forager++) {
            addAnts(new ForagerAnt(antCounter, new Location(13, 13)));
        }



        // Center node must start with 4 scout ants
        for (int scout = 0; scout < 4; scout++) {
            addAnts( new ScoutAnt(antCounter, new Location(13,13)));
        }


        // Center node must start with 10 soldier ants
        for (int soldier = 0; soldier < 10; soldier++) {
            addAnts(new SoldierAnt(antCounter, new Location(13, 13)));
        }

    }
    /**
     * This setup is used to setup the colony inorder to test the Queens functionality
     */
    public void queenTestButton() {
		  /*                              The nested for loop displays the grid nodes around the queen
            goes over the nodes [12][12], [12][13], [12][14], [13,12], [13][13], [13][14], [14][12], [14][13], [14][14]
            Sets nodes to visible, over rides if there is food present and then sets node [13][13] food quantity to 1000
     */
        for (int x = 12; x < 15; x++) {
            for (int y = 12; y < 15; y++) {
                colonyGrid[x][y].showNode(true);
                colonyGrid[13][13].setFoodQuantity(0);
            }
        }

        // Center node must start with 1000 units of food
        colonyGrid[13][13].setFoodQuantity(1000);

        // Center node must contain the queen
        queenAnt = new QueenAnt(antCounter, new Location(13, 13));
        addAnts(queenAnt);

        // Test code for checking to make sure the queen is working properly
        //isHatching = false;
    }
    /**
     * This setup is used to set up the colony inorder to test the functionality of the Scout ant
     */
    public void scoutTestButton() {
		 /*                              The nested for loop displays the grid nodes around the queen
            goes over the nodes [12][12], [12][13], [12][14], [13,12], [13][13], [13][14], [14][12], [14][13], [14][14]
            Sets nodes to visible, over rides if there is food present and then sets node [13][13] food quantity to 1000
     */
        for (int x = 12; x < 15; x++) {
            for (int y = 12; y < 15; y++) {
                colonyGrid[x][y].showNode(true);
                colonyGrid[13][13].setFoodQuantity(0);
            }
        }

        // Center node must start with 1000 units of food
        colonyGrid[13][13].setFoodQuantity(1000);

        // Center node must contain the queen
        queenAnt = new QueenAnt(antCounter, new Location(13, 13));
        addAnts(queenAnt);

        // Center node must start with 4 scout ants
        for (int scout = 0; scout < 4; scout++) {
            addAnts(new ScoutAnt(antCounter, new Location(13, 13)));
        }
        // boolean flag to test with, keeps the queen from hatching more ants while testing functionality of other ants
        isHatching = false;


    }

//    /**
//     * This setup is used to set up the colony inorder to test the functionality of the Forager ant
//     *
//     */
//    public void foragerTestButton() {
//		 /*                              The nested for loop displays the grid nodes around the queen
//            goes over the nodes [12][12], [12][13], [12][14], [13,12], [13][13], [13][14], [14][12], [14][13], [14][14]
//            Sets nodes to visible, over rides if there is food present and then sets node [13][13] food quantity to 1000
//     */
//        for (int x = 12; x < 15; x++) {
//            for (int y = 12; y < 15; y++) {
//                colonyGrid[x][y].showNode(true);
//                //colonyGrid[13][13].setFoodQuantity(0);
//            }
//        }
//
//        // Center node must start with 1000 units of food
//        colonyGrid[13][13].setFoodQuantity(1000);
//
//        // Center node must contain the queen
//        queenAnt = new QueenAnt(antCount, new Location(13, 13));
//        addAnts(queenAnt);
//
//        // Center node starts with 2 forager ants
//        for (int forager = 0; forager < 2; forager++) {
//            addAnts(new ForagerAnt(antCount, new Location(13, 13)));
//        }
//        for(int a=0; a<27; a++){
//            for(int b=0; b<27; b++){
//                colonyGrid[a][b].setFoodQuantity(0);
//
//            }
//        }
//        // display grids to test forager without scout ants present
//        colonyGrid[13][13].showNode(true);
//        colonyGrid[13][13].setFoodQuantity(1000);
//        colonyGrid[14][13].showNode(true);
//        colonyGrid[15][13].showNode(true);
//
//        colonyGrid[16][13].showNode(true);
//
//        colonyGrid[17][13].showNode(true);
//
//        colonyGrid[18][13].showNode(true);
//
//        colonyGrid[19][13].showNode(true);
//        colonyGrid[20][13].showNode(true);
//
//        colonyGrid[21][14].showNode(true);
//        colonyGrid[20][15].showNode(true);
//        colonyGrid[20][16].showNode(true);
//        colonyGrid[20][17].showNode(true);
//        colonyGrid[20][18].showNode(true);
//
//        colonyGrid[19][18].showNode(true);
//        colonyGrid[18][18].showNode(true);
//        colonyGrid[17][18].showNode(true);
//        colonyGrid[16][18].showNode(true);
//        colonyGrid[15][18].showNode(true);
//        colonyGrid[15][18].setFoodQuantity(2000);
//
//
//    }

    /**
     * This setup is used to set up the colony inorder to test the functionality of the Forager ant
     */
    public void foragerTestButton() {
		 /*                              The nested for loop displays the grid nodes around the queen
            goes over the nodes [12][12], [12][13], [12][14], [13,12], [13][13], [13][14], [14][12], [14][13], [14][14]
            Sets nodes to visible, over rides if there is food present and then sets node [13][13] food quantity to 1000
     */
        for (int x = 12; x < 15; x++) {
            for (int y = 12; y < 15; y++) {
                colonyGrid[x][y].showNode(true);
                //colonyGrid[13][13].setFoodQuantity(0);
            }
        }

        // Center node must start with 1000 units of food
        colonyGrid[13][13].setFoodQuantity(1000);

        // Center node must contain the queen
        queenAnt = new QueenAnt(antCounter, new Location(13, 13));
        addAnts(queenAnt);

        // Center node starts with 2 forager ants
        for (int forager = 0; forager < 2; forager++) {
            addAnts(new ForagerAnt(antCounter, new Location(13, 13)));
        }
        for(int a=0; a<27; a++){
            for(int b=0; b<27; b++){
                colonyGrid[a][b].setFoodQuantity(0);

            }
        }
        // display grids to test forager without scout ants present
        colonyGrid[13][13].showNode(true);
        colonyGrid[13][13].setFoodQuantity(1000);
        colonyGrid[14][13].showNode(true);
        colonyGrid[15][13].showNode(true);

        colonyGrid[16][13].showNode(true);

        colonyGrid[17][13].showNode(true);

        colonyGrid[18][13].showNode(true);

        colonyGrid[19][13].showNode(true);
        colonyGrid[20][13].showNode(true);

        colonyGrid[20][14].showNode(true);
        colonyGrid[20][15].showNode(true);
        colonyGrid[20][16].showNode(true);
        colonyGrid[20][14].depositPheromone(123456);

        colonyGrid[20][12].showNode(true);
        colonyGrid[20][11].showNode(true);
        colonyGrid[20][10].showNode(true);

        colonyGrid[20][12].depositPheromone(999999999);

        //colonyGrid[21][14].showNode(true);
        //colonyGrid[20][15].showNode(true);
        //colonyGrid[20][16].showNode(true);
        //colonyGrid[20][17].showNode(true);
        //colonyGrid[20][18].showNode(true);

       // colonyGrid[19][18].showNode(true);
        //colonyGrid[18][18].showNode(true);
        colonyGrid[17][18].showNode(true);
        colonyGrid[16][18].showNode(true);
        colonyGrid[15][18].showNode(true);
        colonyGrid[15][18].setFoodQuantity(2000);

        isHatching = false;
    }

    /**
     This setup is used to set up the colony inorder to test the functionality of the Soldier ant
     */
    public void soldierTestButton(){

        queenAnt = new QueenAnt(antCounter, new Location(13, 13));
        addAnts(queenAnt);
        // opens nodes for soldiers to traverse through since  I didnt add scouts to open the nodes
        for (int x=3; x<=22; x++){
            for(int y=3; y<=22; y++){
                colonyGrid[x][y].showNode(true);
                colonyGrid[x][y].setFoodQuantity(0);
            }
        }
        // Center node must start with 10 soldier ants
        for (int soldier = 0; soldier < 10; soldier++) {
            addAnts(new SoldierAnt(antCounter, new Location(13, 13)));
        }
        colonyGrid[13][13].setFoodQuantity(1000);

        isHatching = false;


    }

    /**
     * Sets up the colony showing all nodes for testing.
     *
     * Balas dont spawn in this test
     */
    public void showNodesButton(){


        // Center node must start with 1000 units of food
        colonyGrid[13][13].setFoodQuantity(1000);

        // Center node must contain the queen
        queenAnt = new QueenAnt(antCounter, new Location(13, 13));
        addAnts(queenAnt);

        // Center node must start with 50 forager ants
        for (int forager = 0; forager < 50; forager++) {
            addAnts(new ForagerAnt(antCounter, new Location(13, 13)));
        }
        // Center node must start with 4 scout ants
        for (int scout = 0; scout < 4; scout++) {
            addAnts(new ScoutAnt(antCounter, new Location(13, 13)));
        }
        // Center node must start with 10 soldier ants
        for (int soldier = 0; soldier < 10; soldier++) {
            addAnts(new SoldierAnt(antCounter, new Location(13, 13)));
        }

    /*                              The nested for loop displays the grid nodes around the queen
            goes over the nodes [12][12], [12][13], [12][14], [13,12], [13][13], [13][14], [14][12], [14][13], [14][14]
            Sets nodes to visible, over rides if there is food present and then sets node [13][13] food quantity to 1000
     */
        for(int a=12; a<15; a++){
            for(int b=12; b<15; b++){
                //colonyGrid[a][b].showNode(true);
                colonyGrid[a][b].setFoodQuantity(0);
            }
        }
        // starting amount of food is 1000
        colonyGrid[13][13].setFoodQuantity(1000);

        for(int a=0; a<27; a++){
            for(int b=0; b<27; b++){
                colonyGrid[a][b].showNode(true);

            }
        }

    }

    /**
     * This adds ants to the colony and updates the total ant counter
     *
     * @param ant The ant to add to the colony
     */
    private void addAnts(AntBasics ant) {
        colonyGrid[ant.getLocation().getX()][ant.getLocation().getY()].addAnt(ant);
        ants.add(ant);
        antCounter++;
    }


    /**
     * This resets the entire colony back to Default settings
     *
     * Brings the colony back to original conditions, deletes all the ants,
     * restarts the ant count, resets the food count in queen node and places the starter ants back inside the original open nodes (NEST)
     */
    public void clear() {
        for (int x = 0; x < colonyGrid.length; x++) {
            for (int y = 0; y < colonyGrid[x].length; y++)
                colonyGrid[x][y].reset();
        }

        ants.clear();
        queenAnt = null;
        antCounter = 0;
    }

    /**
     *
     * This will decrease any colony node with pheromone > 0 by half each day
     */
    private void decreasePheromone() {
        // final int used to remove imaginary reference number '2'
        final int CUT_IN_HALF = 2;
        for (int x = 0; x < colonyGrid.length; x++) {
            for (int y = 0; y < colonyGrid[x].length; y++) {
                if (colonyGrid[x][y].getPheromone() > 0) {
                    int decreasePheromone = colonyGrid[x][y].getPheromone() / CUT_IN_HALF;
                    colonyGrid[x][y].depositPheromone(decreasePheromone);
                }
            }
        }
    }

    /**
     * This is used for the next day logic
     * @param turns The turn count
     *
     */
    public void newDay(int turns){
        int newDay  = turns / 10;

        if (turns % 10 == 0) {
            AntBasics hatchedAnt = queenAnt.hatchAnt(antCounter);
            addAnts(hatchedAnt);

                System.out.println("      NEW DAY: "+ (newDay + 1));

            System.out.println("ANT ID:" + hatchedAnt.getAntID() + "     ANT TYPE:" + hatchedAnt.getAntType());
            decreasePheromone();
        }
    }

    /**
     * Updates and performs the actions of each ant.
     *
     * @param turns The turn count
     *
     */
    public void update(int turns) {
        queenAnt.setAge(turns);

        // Check if queen died of old age
        if (queenAnt.getAge() >= QUEEN_MAX_AGE) {
            queenAnt.setAlive(false);
            System.out.println("Queen died of old age.");
            return;
        }

        // Check if its a new day
        newDay(turns);

        // Check if queen has ate, if she can not eat from lack of food she will die of starvation
        if (!queenAnt.eating(colonyGrid[13][13])) {
            queenAnt.setAlive(false);
            System.out.println("Queen died of starvation.");
            return;
        }else{
            queenAnt.setAlive(true);
            System.out.println("Queen eats food.");

        }
        // Chance between (0-99) inclusive
        int chanceOfBala = RandomHelper.randomNumbers(100);
        // 3% chance of BalaAnt spawning at one of three corners [0][0] , [26][26], [0][26]
        if (chanceOfBala < 3) {
            if(chanceOfBala == 0) {
                addAnts(new BalaAnt(antCounter, new Location(0, 0)));
            }else if(chanceOfBala ==1){
                addAnts(new BalaAnt(antCounter, new Location(26, 26)));
            }else{
                addAnts(new BalaAnt(antCounter, new Location(0, 26)));
            }
        }

        // Iterate through each and every ant and perform their actions accordingly
        Iterator<AntBasics> basicsIterator = ants.iterator();
        // Starter ant is always queen
        basicsIterator.next();
        // Goes through the ants while there is still ants left including new ants hatched by the queen and the bala
        while (basicsIterator.hasNext()) {
            AntBasics ant = basicsIterator.next();
            // if ant has reached max age, ant must then die 3650 turn || 365 days
            if (ant.getAge() > ANT_MAX_AGE) {
                ant.setAlive(false);
                System.out.println("Queen died of old age.");
            }
            // If the ant is dead iterate through the ants and remove them from the node they died in
            if (!ant.isAlive()) {
                colonyGrid[ant.getLocation().getX()][ant.getLocation().getY()].removeAnt(ant);
                basicsIterator.remove();
                System.out.println("ANT: " + ant.getAntID() + " has died \n");
                System.out.println("DATA --> " +" ANT: " + ant.getAntID()  + "  ANT TYPE: "+ ant.getAntType()+"   ANT AGE IN DAYS: " + (ant.getAge() / 10.0)+ "   ANT AGE IN TURNS: " + ant.getAge()+ "     LOCATION: " + "["+ant.getLocation().getX() + "]"+ "[" + ant.getLocation().getY() + "]");
                continue;
            }

            ((AntInterface) ant).updateAction(colonyGrid, ants);
            // If the ant is still alive increment current age
            ant.setAge(ant.getAge() + 1);

        }
    }

    public void endSimulationDialog() {

        int input = JOptionPane.showOptionDialog(null, "The Queen Has Died", "GAME OVER", JOptionPane.WARNING_MESSAGE, JOptionPane.INFORMATION_MESSAGE, null, null, null);
//    //    JOptionPane pane = new JOptionPane("Thank you for playing, Hit to exit");
////        JDialog dialog = pane.createDialog(null, "GAME OVER");
////        dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
////        dialog.setVisible(true);
//
        if (input == JOptionPane.OK_OPTION) {
            System.exit(0);
        } else if (input == JOptionPane.OK_CANCEL_OPTION) {

        }
    }
}
