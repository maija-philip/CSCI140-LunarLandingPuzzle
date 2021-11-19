package solver;

import java.util.ArrayList;

/**
 * Configuration abstraction for the solver algorithm
 *
 * @author Maija Philip
 */
public interface Configuration<E> {

    /*
     * List here the methods that the configurations of all the
     * puzzles must implement.
     * The project writeup explains that there are other acceptable designs,
     * so use of this interface is not required. However, for full design
     * credit, use of a shared solver that requires the implementation of
     * a certain abstraction from all puzzles is required.
     */

    /**
     * Gives if the configuration it was called on is the solution or not
     * @return - true or false if found the solution or not
     */
    boolean isSolution(E e);

    /**
     * Gives the neighbors of the configuration object it was called on
     * @return - the neighbors of the configuration passed in
     */
    ArrayList<E> getNeighbors(E e);

    boolean equals(Object other);
    int hashCode();

}
