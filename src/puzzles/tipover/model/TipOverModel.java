package puzzles.tipover.model;
import puzzles.lunarlanding.model.LunarLandingConfig;
import solver.Solver;
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
    private String currentFilename;
    private String feedback;

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
        this.currentFilename = filename;
        this.feedback = "";
        try{
            currentConfig = new TipOverConfig(filename);
        } catch(FileNotFoundException e){
            System.out.println("File \"" + filename + "\" not found");
            return;
        }
        this.reload();
    }

    public void load(String filename){
        System.out.println("load");

        try {
            this.currentConfig = new TipOverConfig(filename);
        } catch (FileNotFoundException e) {
            System.out.println("File \"" + filename + "\" not found");
            return;
        }

        this.currentFilename = filename;
        //this.reset();
    }

    public void reload(){
        System.out.println("reload");
        try {
            currentConfig = new TipOverConfig(currentFilename);
        } catch (FileNotFoundException e) {
            System.out.println("File \"" + currentFilename + "\" not found");
            return;
        }
        //this.reset();
    }

    public void move(String direction){
        if(!isSolution(currentConfig)) {
            System.out.println("Move " + direction + "...");
            int x = currentConfig.getTipRow();
            int y = currentConfig.getTipCol();
            switch (direction) {
                case "UP" -> x--;
                case "RIGHT" -> y++;
                case "DOWN" -> x++;
                case "LEFT" -> y--;
            }
            if (currentConfig.getBoard()[currentConfig.getTipRow()][currentConfig.getTipCol()] == 1) {
                moveCrate(x, y);
            } else if (currentConfig.getBoard()[currentConfig.getTipRow()][currentConfig.getTipCol()] > 1) {
                moveTower(x, y, direction);
            }
        }
    }

    private void moveCrate(int row, int col){
        if(currentConfig.isCoordinateInGrid(row,col)){
            if(currentConfig.getBoard()[row][col] > 0){
                TipOverConfig copy = new TipOverConfig(currentConfig);
                copy.setTipRow(row);
                copy.setTipCol(col);
                currentConfig = copy;
                this.feedback = "";
            } else {
                this.feedback = "Illegal Move (crate)";
            }
        } else {
            this.feedback = "Illegal Move (off board - crate)";
        }
        //System.out.println(currentConfig);
    }

    private void moveTower(int row, int col, String direction){
        if(currentConfig.isCoordinateInGrid(row,col)){
            if(currentConfig.getBoard()[row][col]==0){
                TipOverConfig hold = currentConfig.TippingAttempt(currentConfig,direction);
                if(hold!=null){
                    hold.setTipRow(row);
                    hold.setTipCol(col);
                    currentConfig = hold;
                    this.feedback = "A tower has been tipped over";
                } else {
                    this.feedback = "Illegal Move (tower - idk)";
                }
            } else if(currentConfig.getBoard()[row][col] > 0){
                TipOverConfig copy = new TipOverConfig(currentConfig);
                copy.setTipRow(row);
                copy.setTipCol(col);
                currentConfig = copy;
                this.feedback = "";
            }
        } else {
            this.feedback = "Illegal Move (off board - tower)";
        }
        //System.out.println(currentConfig);
    }

    public void hint(){
        Solver<TipOverConfig> TipSolver = new Solver<>(currentConfig);
        ArrayList<TipOverConfig> path = TipSolver.solve(currentConfig);

        if (path == null) {
            feedback = "Unsolvable board";
            return;
        }

        if (path.isEmpty() || path.size() < 2) {
            System.out.println("You already won!");
            return;
        }

        if(isSolution(path.get(1))){
            feedback = "I WON!";
        }

        currentConfig = path.get(1);

    }

    public void addObserver( Observer< TipOverModel, Object > obs ) {
        this.observers.add( obs );
    }

    public TipOverConfig getCurrentConfig(){
        return currentConfig;
    }

    public boolean isSolution(TipOverConfig config){
        boolean holding = false;
        if(config.getTipRow()== config.getGoalRow() && config.getTipCol()== config.getGoalCol()){
            holding = true;
        }
        return holding;
    }

    public String getFeedback() {return feedback;}


}
