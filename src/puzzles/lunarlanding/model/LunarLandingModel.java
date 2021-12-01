package puzzles.lunarlanding.model;

import util.Observer;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * DESCRIPTION
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

        this.currentFilename = filename;
        this.reset();
    }

    /**
     * resets the board
     */
    public void reset() {
        System.out.println("reset");

    }

    public void load(String filename) {
        System.out.println("load");

        try {
            this.currentConfig = new LunarLandingConfig(filename);
        } catch (FileNotFoundException e) {
            System.out.println("File \"" + filename + "\" not found");
            return;
        }

        this.currentFilename = filename;
        this.reset();
    }

    public void reload() {
        System.out.println("reload");
        try {
            currentConfig = new LunarLandingConfig(currentFilename);
        } catch (FileNotFoundException e) {
            System.out.println("File \"" + currentFilename + "\" not found");
            return;
        }
        this.reset();
    }

    public void move(String move) {
        System.out.println("move: " + move);
    }

    public void hint() {
        System.out.println("hint");
    }

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
}
