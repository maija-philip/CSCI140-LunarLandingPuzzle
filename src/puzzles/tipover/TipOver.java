package puzzles.tipover;

import puzzles.lunarlanding.model.LunarLandingConfig;
import puzzles.tipover.model.TipOverConfig;
import solver.Solver;

import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * DESCRIPTION
 * @author YOUR NAME HERE
 * November 2021
 */
public class TipOver {

    /*
     * code to read the file name from the command line and
     * run the solver on the puzzle
     */

    public static void main( String[] args ) {
        TipOverConfig TipConfig;
        try{
            TipConfig = new TipOverConfig(args[0]);
        } catch (FileNotFoundException e){
            System.out.println("File \"" + args[0] + "\" not found");
            return;
        }
        Solver<TipOverConfig> TipSolver = new Solver<>(TipConfig);

        ArrayList<TipOverConfig> path = TipSolver.solve(TipConfig);



        System.out.println("Total configs: " + TipSolver.getConfigCounter());
        System.out.println("Unique configs: " + TipSolver.getUniqueConfigs());

        if (path == null) {
            System.out.println("No Solution");
        }
        else
        {
            for (int i = 0; i < path.size(); i++) {
                System.out.println("Step " + i + ": \n" + path.get(i));
            }

        }
    }

}
