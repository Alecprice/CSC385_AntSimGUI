/*****
 * Each node contains one or more of the following
 * a. Zero or mor enemy ants;
 * b. Zero or more friendly ants
 * c. Zero or more units of food
 * d. Zero or more units of pheromone
 */
package AntProject;

public class ColonyNode {
    /**************
     * attributes
     **************/
    private String id;
    public boolean discovered;
    private boolean queenPresent;
    private int foragerCount;
    private int scoutCount;
    private int soldierCount;
    private int balaCount;
    private int foodAmount;
    private int pheromone;
    private boolean isHatching;

    private ColonyNodeView colonyNodeView;

    /**
     * Constructor
     *
     * @param id of node ex [x][y] values
     */
    public ColonyNode(String id) {
        colonyNodeView = new ColonyNodeView();
        setId(id);
        reset();
    }

    public String getId() {
        return id;
    }

    /**
     * Sets node value in the grid
     *
     * @param id of node
     */
    public void setId(String id) {
        this.id = id;
        colonyNodeView.setID(this.id);
    }
    //Is the node visible to ants
    public boolean isDiscovered() {
        return discovered;
    }

    /**
     *
     * @param discovered boolean value for if the node has been discovered by the scouts
     * @return  true if scout has already been to the node false if not & outside of the nest
     */
    public boolean showNode(boolean discovered) {
        this.discovered = discovered;

        if (this.discovered) {
            colonyNodeView.showNode();
        } else {
            colonyNodeView.hideNode();
        }
        return discovered;
    }

    public boolean isQueenPresent() {
        return queenPresent;
    }

    //Get & Set methods for ants and queen placement
    public void setQueenPresent(boolean queenPresent) {
        this.queenPresent = queenPresent;
        colonyNodeView.setQueen(this.queenPresent);
    }

    public int getForagerCount() {
        return foragerCount;
    }

    public void setForagerCount(int foragerCount) {
        this.foragerCount = foragerCount;
        colonyNodeView.setForagerCount(this.foragerCount);
    }

    public int getScoutCount() {
        return scoutCount;
    }

    public void setScoutCount(int scoutCount) {
        this.scoutCount = scoutCount;
        colonyNodeView.setScoutCount(this.scoutCount);
    }

    public int getSoldierCount() {
        return soldierCount;
    }

    public void setSoldierCount(int soldierCount) {
        this.soldierCount = soldierCount;
        colonyNodeView.setSoldierCount(this.soldierCount);
    }

    public int getBalaCount() {
        return balaCount;
    }

    public void setBalaCount(int balaCount) {
        this.balaCount = balaCount;
        colonyNodeView.setBalaCount(this.balaCount);
    }
    //Get & Set methods for amount of food in nodes
    public int getFoodAmount() {
        return foodAmount;
    }

    public void setFoodQuantity(int foodAmount) {
        this.foodAmount = foodAmount;
        colonyNodeView.setFoodAmount(this.foodAmount);
    }
    //Get & Set method for pheromone in nodes
    public int getPheromone() {
        return pheromone;
    }

    public void depositPheromone(int pheromoneLevel) {
        this.pheromone = pheromoneLevel;
        colonyNodeView.setPheromoneLevel(this.pheromone);
    }
    //Get method for colony node view
    public ColonyNodeView getColonyNodeView() {
        return colonyNodeView;
    }

    /**
     * Method adding ants to the colony and updating the Icons to reflect the new ants
     *
     * @param ant being added to the colony, all from Queen hatching besides Bala
     */
    public void addAnt(AntBasics ant) {
        if (ant instanceof QueenAnt) {
            setQueenPresent(true);
        } else if (ant instanceof ForagerAnt) {
            setForagerCount(foragerCount + 1);
        } else if (ant instanceof ScoutAnt) {
            setScoutCount(scoutCount + 1);
        } else if (ant instanceof SoldierAnt) {
            setSoldierCount(soldierCount + 1);
        } else if (ant instanceof BalaAnt) {
            setBalaCount(balaCount + 1);
        }

        updateIcons();
    }

    /**
     * Method removing ants from the colony and updating the Icons to reflect the removal
     *
     * @param ant being removed from the colony by death: starving, killed, or by old age
     */
    public void removeAnt(AntBasics ant) {
        if (ant instanceof ForagerAnt) {
            setForagerCount(foragerCount - 1);
        } else if (ant instanceof ScoutAnt) {
            setScoutCount(scoutCount - 1);
        } else if (ant instanceof SoldierAnt) {
            setSoldierCount(soldierCount - 1);
        } else if (ant instanceof BalaAnt) {
            setBalaCount(balaCount - 1);
        }

        updateIcons();
    }
    //Used to update the visible ants image inside the colony
    private void updateIcons() {
        if (queenPresent) {
            colonyNodeView.showQueenIcon();
        } else {
            colonyNodeView.hideQueenIcon();
        }

        if (foragerCount > 0) {
            colonyNodeView.showForagerIcon();
        } else {
            colonyNodeView.hideForagerIcon();
        }

        if (scoutCount > 0) {
            colonyNodeView.showScoutIcon();
        } else {
            colonyNodeView.hideScoutIcon();
        }

        if (soldierCount > 0) {
            colonyNodeView.showSoldierIcon();
        } else {
            colonyNodeView.hideSoldierIcon();
        }

        if (balaCount > 0) {
            colonyNodeView.showBalaIcon();
        } else {
            colonyNodeView.hideBalaIcon();
        }
    }
    //Clear all values present
    public void reset() {
        showNode(false);
        setQueenPresent(false);
        setForagerCount(0);
        setScoutCount(0);
        setSoldierCount(0);
        setBalaCount(0);
        setFoodQuantity(0);
        depositPheromone(0);
        updateIcons();
    }

}
