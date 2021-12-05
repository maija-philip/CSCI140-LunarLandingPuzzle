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
        try{
            currentConfig = new TipOverConfig(filename);
        } catch(FileNotFoundException e){
            System.out.println("File \"" + filename + "\" not found");
            return;
        }
        this.reload();
    }

    public void load(String fileNum){
        System.out.println("load");

        String filename = "";
        switch(fileNum){
            case "0" -> filename = "data/tipover/tipover-0.txt";
            case "1" -> filename = "data/tipover/tipover-1.txt";
            case "2" -> filename = "data/tipover/tipover-2.txt";
            case "3" -> filename = "data/tipover/tipover-3.txt";
            case "4" -> filename = "data/tipover/tipover-4.txt";
            case "5" -> filename = "data/tipover/tipover-5.txt";
            case "6" -> filename = "data/tipover/tipover-6.txt";
            case "7" -> filename = "data/tipover/tipover-7.txt";
            case "8" -> filename = "data/tipover/tipover-8.txt";
            case "9" -> filename = "data/tipover/tipover-9.txt";
            case "a" -> filename = "data/tipover/tipover-a.txt";
        }

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
        System.out.println("Move " + direction + "...");
        int x = currentConfig.getTipRow();
        int y = currentConfig.getTipCol();
        switch (direction) {
            case "UP" -> x--;
            case "RIGHT" -> y++;
            case "DOWN" -> x++;
            case "LEFT" -> y--;
        }
        if(currentConfig.getBoard()[currentConfig.getTipRow()][currentConfig.getTipCol()] == 1){
            moveCrate(x, y);
        } else if (currentConfig.getBoard()[currentConfig.getTipRow()][currentConfig.getTipCol()] > 1){
            moveTower(x, y, direction);
        }
    }

    private void moveCrate(int row, int col){
        if(currentConfig.isCoordinateInGrid(row,col)){
            if(currentConfig.getBoard()[row][col] > 0){
                TipOverConfig copy = new TipOverConfig(currentConfig);
                copy.setTipRow(row);
                copy.setTipCol(col);
                currentConfig = copy;
            } else {
                System.out.println("Illegal Move (crate)");
            }
        } else {
            System.out.println("Illegal Move (off board - crate)");
        }
        System.out.println(currentConfig);
    }

    private void moveTower(int row, int col, String direction){
        if(currentConfig.isCoordinateInGrid(row,col)){
            if(currentConfig.getBoard()[row][col]==0){
                TipOverConfig hold = currentConfig.TippingAttempt(currentConfig,direction);
                if(hold!=null){
                    hold.setTipRow(row);
                    hold.setTipCol(col);
                    currentConfig = hold;
                } else {
                    System.out.println("Illegal Move (tower - idk)");
                }
            } else if(currentConfig.getBoard()[row][col] > 0){
                TipOverConfig copy = new TipOverConfig(currentConfig);
                copy.setTipRow(row);
                copy.setTipCol(col);
                currentConfig = copy;
            }
        } else {
            System.out.println("Illegal Move (off board - tower)");
        }
        System.out.println(currentConfig);
    }

    public void hint(){
        Solver<TipOverConfig> TipSolver = new Solver<>(currentConfig);
        ArrayList<TipOverConfig> path = TipSolver.solve(currentConfig);
        currentConfig = path.get(1);

    }

    public void addObserver( Observer< TipOverModel, Object > obs ) {
        this.observers.add( obs );
    }

    public TipOverConfig getCurrentConfig(){
        return currentConfig;
    }

    public boolean isSolution(){
        boolean holding = false;
        if(currentConfig.getTipRow()== currentConfig.getGoalRow() && currentConfig.getTipCol()== currentConfig.getGoalCol()){
            holding = true;
        }
        return holding;
    }

}
