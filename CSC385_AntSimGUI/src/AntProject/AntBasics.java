/*************************Instructions******************************************************************
 1.Each ant should be identified by a unique integer The queen ant should have an ID value of 0
    Other ants should be numbered in ascending order as they are hatched.
 2. All ant types (except for the queen) have a maximum life span of 1 year (10 Turns per day * 365days) or 3650 turns.
 3. Dead ants should be removed from the simulation.
 4. All ants are limited to one action per turn, with some exceptions that will be discussed later.
 5. All ants except Bala ants may only move in squares that have been revealed by scout ants;
    Bala ants may also move into squares that have not been revealed by scout ants.
 6. When moving, all ant types should move no more than 1 square per turn.
 *********************************************************************************************************/
package AntProject;

import java.util.Random;

public abstract class AntBasics {

        /***************
         * attributes
         ***************/
        //1.    each ant should have a unique Integer ID
        private int antID;
        //2.    used for the age of each ant, all ants live for a max of one year besides the queen lives for a max of 20 years
        private int age;
        private boolean isAlive;
        private Location location;
        private static Random random = new Random();
        private int antType;

        /**
         * Constructor
         *  @param antID is the unique identifier of the ants
         * @param location is the coordinates inside the [27][27] grid
         */
        public AntBasics(int antID, Location location) {
            setAntID(antID);
            setAge(0);
            setAlive(true);
            setLocation(location);
            setAntType(antType);

        }


    //Get and Set Methods for AntID attribute
        public int getAntID() {
            return antID;
        }
        public void setAntID(int antID) {
            this.antID = antID;
        }
        //Get and Set Methods for Age attribute
        public int getAge() {
            return age;
        }
        public void setAge(int age) {
            this.age = age;
        }
        //Method check whether Queen Ant is still Alive and method to set if shes alive or not
        public boolean isAlive() {
            return isAlive;
        }
        public void setAlive(boolean alive) {
            this.isAlive = alive;
        }
        //Get and Set Methods for Location
        public Location getLocation() {
            return location;
        }
        public void setLocation(Location location) {
            this.location = location;
        }
        //Helper used to generate the sudo random actions needed by the inheriting classes
        public static Random random() {
            return random;
        }
    //Get and Set Method for AntType with test code for easy debugging
    public int getAntType() {

                switch (antType) {
                    case 0 -> {
                        antType = 0;
                        if(isAlive) {
                            // This will not occur and can be removed
                            System.out.println("Queen Hatched");
                        }else { //Check if the queen has died
                            System.out.println("Queen Has Died");
                        }
                            break;
                    }
                    case 1 -> {
                        antType = 1;
                        if(isAlive) {
                            System.out.println("Scout Hatched");
                        }else{
                            System.out.println("Scout Has Died");
                        }
                        break;
                    }
                    case 2 -> {
                        antType = 2;
                        if(isAlive) {
                            System.out.println("Forager Hatched");
                        }else{
                            System.out.println("Forager Has Died");
                        }
                        break;
                    }
                    case 3 -> {
                        antType = 3;
                        if(isAlive) {
                            System.out.println("Soldier Hatched");
                        }else{
                            System.out.println("Soldier Has Died");
                        }
                        break;
                    }
                    case 4 -> {
                        antType = 4;
                        if(isAlive) {
                            System.out.println("Bala Hatched");// This should not happen, testing
                        }else{
                            System.out.println("Bala Has Died");
                        }
                        break;
                    }
                }


            return antType;
        }

    public void setAntType(int antType) { this.antType = antType; }

}
