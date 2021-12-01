package puzzles.tipover.model;
import util.Observer;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * DESCRIPTION
 * @author YOUR NAME HERE
 * November 2021
 */
public class TipOverModel {

    private TipOverConfig currentConfig;

    private List<Observer<TipOverModel, Object>> observers;

    /*
     * Code here includes...
     * Additional data variables for anything needed beyond what is in
     *   the config object to describe the current state of the puzzle
     * Methods to support the controller part of the GUI, e.g., load, move
     * Methods and data to support the "subject" side of the Observer pattern
     *
     * WARNING: To support the hint command, you will likely have to do
     *   a cast of Config to TipOverConfig somewhere, since the solve
     *   method works with, and returns, objects of type Configuration.
     */

    public TipOverModel(String filename){
        this.observers = new LinkedList<>();
        try{
            currentConfig = new TipOverConfig(filename);
        } catch(FileNotFoundException e){
            System.out.println("File \"" + filename + "\" not found");
            return;
        }
        this.reload();
    }

    public void load(){
        System.out.println("Load...");
    }

    public void reload(){
        System.out.println("Reload...");
    }

    public void move(String direction){
        System.out.println("Move " + direction + "...");
    }

    public void hint(){
        System.out.println("Hint");
    }

    public void addObserver( Observer< TipOverModel, Object > obs ) {
        this.observers.add( obs );
    }

    public TipOverConfig getCurrentConfig(){
        return currentConfig;
    }

}
