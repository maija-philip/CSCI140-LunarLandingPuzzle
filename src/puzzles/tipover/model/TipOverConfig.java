package puzzles.tipover.model;

import solver.Configuration;

import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * DESCRIPTION
 * @author YOUR NAME HERE
 * November 2021
 */
public class TipOverConfig implements Configuration {

    public TipOverConfig(String filename) throws FileNotFoundException {

    }

    @Override
    public boolean isSolution(Object o) {
        return false;
    }

    @Override
    public ArrayList getNeighbors(Object o) {
        return null;
    }
}
