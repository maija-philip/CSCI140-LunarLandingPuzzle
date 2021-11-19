package puzzles.water;
import solver.Solver;

import java.util.ArrayList;

/**
 * Main class for the water buckets puzzle.
 *
 * @author Maija Philip
 * @author Caitlin Patton
 */
public class Water {

    /**
     * Run an instance of the water buckets puzzle.
     * @param args [0]: desired amount of water to be collected;
     *             [1..N]: the capacities of the N available buckets.
     */
    public static void main( String[] args ) {
        if ( args.length < 2 ) {
            System.out.println(
                    ( "Usage: java Water amount bucket1 bucket2 ..." )
            );
        }
        else {
            int amount = Integer.parseInt(args[0]);
            ArrayList<Integer> buckets = new ArrayList<>();

            for (int i = 1; i < args.length; i++) {
                buckets.add(Integer.parseInt(args[i]));
            }
            //System.out.println( "java Clock " + amount + " " + start + " " + end);

            System.out.println( "Amount: " + amount + ", Buckets: " + buckets);


            WaterConfiguration w = new WaterConfiguration(amount, buckets);
            Solver<ArrayList<Integer>> s = new Solver<>(w);

            ArrayList<Integer> start = new ArrayList<>();
            for (int i = 0; i < buckets.size(); i++) {
                start.add(0);
            }
            ArrayList<ArrayList<Integer>> path = s.solve(start);


            System.out.println("Total configs: " + s.getConfigCounter());
            System.out.println("Unique configs: " + s.getUniqueConfigs());

            if (path == null) {
                System.out.println("No Solution");
            }
            else
            {
                for (int i = 0; i < path.size(); i++) {
                    System.out.println("Step " + i + ": " + path.get(i));
                }

            }



        }
    }
}
