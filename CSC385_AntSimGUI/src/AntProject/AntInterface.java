package AntProject;

import java.util.LinkedList;


public interface AntInterface {

    /**
     *
     * @param grid The colony grid [27][27]
     * @param ants The ants in the colony
     */
    void updateAction(ColonyNode[][] grid, LinkedList<AntBasics> ants);


}
