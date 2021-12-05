package puzzles.lunarlanding;

import puzzles.clock.ClockConfiguration;
import puzzles.lunarlanding.model.LunarLandingConfig;
import solver.Solver;

import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Runs the lunar landing game and gives the quickest solution
 * @author Maija Philip
 * November 2021
 */
public class LunarLanding {

    /*
     * code to read the file name from the command line and
     * run the solver on the puzzle
     */

    /**
     * the main method, runs the filename specified and prints the quickest solution
     * @param args - filename of the board that should load
     */
    public static void main( String[] args ) {

        LunarLandingConfig c;
        try {
            c = new LunarLandingConfig(args[0]);
        } catch (FileNotFoundException e) {
            System.out.println("File \"" + args[0] + "\" not found");
            return;
        }
        Solver<LunarLandingConfig> s = new Solver<>(c);

        //System.out.println(c);
        ArrayList<LunarLandingConfig> path = s.solve(c);



        System.out.println("Total configs: " + s.getConfigCounter());
        System.out.println("Unique configs: " + s.getUniqueConfigs());

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
