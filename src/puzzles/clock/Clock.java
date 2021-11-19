package puzzles.clock;

import solver.Solver;

import java.util.ArrayList;

/**
 * Main class for the "clock" puzzle.
 *
 * @author Maija Philip
 * @author Caitlin Patton
 */
public class Clock {


    /**
     * Run an instance of the clock puzzle.
     * @param args [0]: number of hours on the clock;
     *             [1]: starting time on the clock;
     *             [2]: goal time to which the clock should be set.
     */
    public static void main( String[] args ) {
        if ( args.length != 3 ) {
            System.out.println( "Usage: java Clock hours start end" );
        }
        else {
            int hours = Integer.parseInt(args[0]);
            int start = Integer.parseInt(args[1]);
            int end = Integer.parseInt(args[2]);

            //System.out.println( "java Clock " + hours + " " + start + " " + end);

            System.out.println( "Hours: " + hours + ", Start: " + start + ", End: " + end);

            ClockConfiguration c = new ClockConfiguration(hours, start, end);
            Solver<Integer> s = new Solver<>(c);
            ArrayList<Integer> path = s.solve(start);


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
