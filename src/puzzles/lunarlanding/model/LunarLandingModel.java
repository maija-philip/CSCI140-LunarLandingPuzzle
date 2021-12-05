package puzzles.lunarlanding.model;

import puzzles.tipover.model.TipOverConfig;
import solver.Solver;
import util.Observer;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Makes things happen, connects the board to the user inputs
 * @author Maija Philip
 * November 2021
 */
public class LunarLandingModel {

    /**
     * Those objects that are watching this object's every move
     */
    private List<Observer< LunarLandingModel, Object >> observers;
    private String currentFilename;

    private LunarLandingConfig currentConfig;

    private String feedback;

    /*
     * Code here includes...
     * Additional data variables for anything needed beyond what is in
     *   the config object to describe the current state of the puzzle
     * Methods to support the controller part of the GUI, e.g., load, move
     * Methods and data to support the "subject" side of the Observer pattern
     *
     * WARNING: To support the hint command, you will likely have to do
     *   a cast of Config to LunarLandingConfig somewhere, since the solve
     *   method works with, and returns, objects of type Configuration.
     */

    /**
     * Construct a LunarLandingModel
     */
    public LunarLandingModel(String filename) {
        this.observers = new LinkedList<>();

        try {
            this.currentConfig = new LunarLandingConfig(filename);
        } catch (FileNotFoundException e) {
            System.out.println("File \"" + filename + "\" not found");
            return;
        }

        this.feedback = "";
        this.currentFilename = filename;
        this.reset();
    }

    /**
     * resets the board
     */
    public void reset() {
        //System.out.println("reset");

    }

    /**
     * loads the selected board
     * @param filename - the file path of the new board
     */
    public void load(String filename) {
        // System.out.println("load");

        try {
            this.currentConfig = new LunarLandingConfig(filename);
        } catch (FileNotFoundException e) {
            System.out.println("File \"" + filename + "\" not found");
            return;
        }

        this.currentFilename = filename;
        this.reset();
    }

    /**
     * reloads the board to restart the game
     */
    public void reload() {
        //System.out.println("reload");
        try {
            currentConfig = new LunarLandingConfig(currentFilename);
        } catch (FileNotFoundException e) {
            System.out.println("File \"" + currentFilename + "\" not found");
            return;
        }
        this.reset();
    }

    /**
     * moves the board in the way specified if it's a legal move
     * @param givenX - x coordinate of the robot to move
     * @param givenY - y coordinate of the robot to move
     * @param direction - direction it should move in
     */
    public void move(String givenX, String givenY, String direction) {
        //System.out.println("move robot at (" + givenX + ", " + givenY + ") " + direction);

        int x, y;
        try {
            x = Integer.parseInt(givenX);
            y = Integer.parseInt(givenY);
        } catch (NumberFormatException e) {
            // System.out.println(e.getMessage().substring(18) + " is not a coordinate, please enter a number");
            feedback = "Illegal Move";
            return;
        }

        currentConfig = currentConfig.playMove(x, y, direction, currentConfig);
        feedback = currentConfig.geFeedback();

    }

    /**
     * moves the board one step closer to the solution
     */
    public void hint(){

        Solver<LunarLandingConfig> TipSolver = new Solver<>(currentConfig);
        ArrayList<LunarLandingConfig> path = TipSolver.solve(currentConfig);

        if (path == null) {
            feedback = "Unsolvable board";
            return;
        }

        if (path.isEmpty() || path.size() < 2) {
            System.out.println("You already won!");
            return;
        }

        currentConfig = path.get(1);

    }

    /**
     * tells whether the current configuration is the solution
     * @return - whether the current configuration is the solution
     */
    public boolean isSolution() {
        return currentConfig.isSolution(currentConfig);
    }

    /**
     * gives the current configuration that the model has
     * @return - current configuration of the board
     */
    public LunarLandingConfig getCurrentConfig() {
        return currentConfig;
    }

    /**
     * Add a new observer to the list for this model
     * @param obs an object that wants an
     *            {@link Observer#update(Object, Object)}
     *            when something changes here
     */
    public void addObserver( Observer< LunarLandingModel, Object > obs ) {
        this.observers.add( obs );
    }

    /**
     * Announce to observers the model has changed;
     */
    private void announce( String arg ) {
        for ( var obs : this.observers ) {
            obs.update( this, arg );
        }
    }

    /**
     * gives the hashmap of robots
     * @return - hashmap of robots
     */
    public HashMap<String, int[]> getRobots() {return currentConfig.getRobots();}

    /**
     * gives the number of rows on the board
     * @return - gives the number of rows on the board
     */
    public int getRows() {return currentConfig.getHeight();}

    /**
     * gives the number of columns on the board
     * @return - gives the number of columns on the board
     */
    public int getColumns() {return currentConfig.getWidth();}

    /**
     * gives the goal coordinates as an int[] like x, y
     * @return - gives {x, y} of goal coordinates
     */
    public int[] getGoal() {return currentConfig.getGoal();}

    /**
     * gives the feedback the model needs to give the UIs after the move method
     * @return - gives the model's feedback
     */
    public String getFeedback() {return feedback;}

    /**
     * for testing, prints the board and if it's the solution or not
     */
    public void printBoard() {
        System.out.println(currentConfig);
        System.out.println("This is the solution: " + isSolution());
    }



}
