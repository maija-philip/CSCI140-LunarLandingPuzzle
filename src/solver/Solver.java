package solver;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class contains a universal algorithm to find a path from a starting
 * configuration to a solution, if one exists
 *
 * @author Maija Philip
 */
public class Solver<E> {
    private int configCounter;
    private ArrayList<E> queue;
    private HashMap<E, E> predecessorMap;
    private Configuration c;

    public Solver(Configuration c) {
        this.configCounter = 0;
        this.queue = new ArrayList<>();
        this.predecessorMap = new HashMap<>();
        this.c = c;
    }

    /**
     * uses BFS to solve the puzzle
     * @param start - the point in the graph where the search starts
     * @return - the shortest path from start to finish
     */
    public ArrayList<E> solve(E start) {

        queue.add(start);
        predecessorMap.put(start, null);
        configCounter++;

        E current = queue.remove(0);
        while (!c.isSolution(current)) {
            ArrayList<E> neighbors = c.getNeighbors(current);
            for (E n : neighbors) {
                configCounter++;

                if (!predecessorMap.containsKey(n)) {
                    predecessorMap.put(n, current);
                    queue.add(n);
                }
            }

            if (queue.size() == 0) {
                return null;
            }
            current = queue.remove(0);
        }

        return getPath(current);
    }

    /**
     * goes through the predecessor map and finds the path from start to end
     * @param end - the goal of the BFS
     * @return - the lsit of the path from start to end
     */
    private ArrayList<E> getPath(E end) {
        ArrayList<E> path = new ArrayList<>();

        path.add(end);
        E next = predecessorMap.get(end);
        while (next != null) {
            path.add(0, next);
            next = predecessorMap.get(next);
        }
        return path;
    }

    /**
     * gives the number of configurations cycled through
     * @return - the number of configurations cycled through
     */
    public int getConfigCounter() {return configCounter;}

    /**
     * gives number of unique configurations cycled through
     * @return - number of unique configurations cycled through;
     */
    public int getUniqueConfigs() {return predecessorMap.size();}
}
