package AntProject;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Simulation implements SimulationEventListener, ActionListener {

    private int turnCounter;
    private Colony simColony;
    private AntSimGUI antSimGUI;
    private boolean initializedColony;
    private Timer newTimer;
    private boolean isHatching;

    public Simulation() {
        turnCounter = 0;
        simColony = new Colony();
        antSimGUI = new AntSimGUI();
        initializedColony = false;
        newTimer = new Timer(2, this);
        antSimGUI.addSimulationEventListener(this);
        antSimGUI.initGUI(simColony.getColonyView());
    }

    /**
     * Start method for timer
     */
    public void startTime(){
        // start timer
        newTimer.start();
    }

    public String sTime(){

        String updatedTime = "Day: " + turnCounter / 10 + ", Turn: " + turnCounter % 10 + " (10 Turns equals 1 Day)";
        return updatedTime;
    }

    /**
     * Reverse action method for debugging purposes
     */
    public void reverse(){
        turnCounter--;
        //update time label
        antSimGUI.setTime(sTime());

        //simColony.reverseUpdateColonyAndAnts(turnCounter);


    }

    /**
     * Updates the colony and increments the step. Each step each ant performs their
     * appropriate action.
     */
    public void step() {
        turnCounter++;
        simColony.update(turnCounter);
        antSimGUI.setTime(sTime());
        System.out.println("UPDATE:  Turn Count: " + turnCounter);
    }

    /**
     * Handles events depending on which button is clicked.
     */
    @Override
    public void simulationEventOccurred(SimulationEvent simEvent) {
        if (simEvent.getEventType() == SimulationEvent.NORMAL_SETUP_EVENT) {
            if (newTimer.isRunning()) {
                newTimer.stop();
            }

            if (initializedColony) {
                simColony.clear();
                turnCounter = 0;
            }

            simColony.normalSetupButton();
            initializedColony = true;
            antSimGUI.setTime(sTime());
        } else if(simEvent.getEventType() == simEvent.QUEEN_TEST_EVENT){
            if (newTimer.isRunning()) {
                newTimer.stop();
            }

            if (initializedColony) {
                simColony.clear();
                turnCounter = 0;
            }

            simColony.queenTestButton();
            initializedColony = true;
            antSimGUI.setTime(sTime());
        } else if(simEvent.getEventType() == simEvent.SCOUT_TEST_EVENT){
            if(newTimer.isRunning()){
                newTimer.stop();
            }

            if (initializedColony) {
                simColony.clear();
                turnCounter = 0;
            }
            simColony.scoutTestButton();
            initializedColony = true;
            antSimGUI.setTime(sTime());
        }  else if(simEvent.getEventType() == simEvent.FORAGER_TEST_EVENT){
            if(newTimer.isRunning()){
                newTimer.stop();
            }

            if (initializedColony) {
                simColony.clear();
                turnCounter = 0;
            }

            simColony.foragerTestButton();
            initializedColony = true;
            antSimGUI.setTime(sTime());
        } else if(simEvent.getEventType() == simEvent.SOLDIER_TEST_EVENT){
            if(newTimer.isRunning()){
                newTimer.stop();
            }

            if (initializedColony) {
                simColony.clear();
                turnCounter = 0;
            }

            simColony.soldierTestButton();
            initializedColony = true;
            antSimGUI.setTime(sTime());
        }else if (simEvent.getEventType() == SimulationEvent.RUN_EVENT) {
            if (initializedColony) {
                if (newTimer.isRunning()) {
                    newTimer.stop();
                } else {
                    newTimer.start();
                }
            }
        } else if (simEvent.getEventType() == SimulationEvent.SHOW_EVENT) {
            if(newTimer.isRunning()){
                newTimer.stop();
            }

            if (initializedColony) {
                simColony.clear();
                turnCounter = 0;
            }

            simColony.showNodesButton();
            initializedColony = true;
            antSimGUI.setTime(sTime());
        }else if (simEvent.getEventType() == SimulationEvent.STEP_EVENT) {
            if (initializedColony && !newTimer.isRunning()) {
                if (simColony.getQueenAnt().isAlive()) {
                    step();
                } else {
                    System.out.println("The Queen has died, the simulation is now over\n Thank you, \n Alec Price\"");
                }
            }
        }

    }



    /**
     * Executes the step method on each iteration of the timer class.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // get the button that was pressed

        if (simColony.getQueenAnt().isAlive()) {
            step();
        }
        else{
            newTimer.stop();
            System.out.println(" The Queen Ant has died and the simulation is now over\n Thank you, \n Alec Price");
        }

    }

}
