package puzzles.tipover.model;

import solver.Configuration;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * DESCRIPTION
 * @author YOUR NAME HERE
 * November 2021
 */
public class TipOverConfig implements Configuration {

    private int width;
    private int height;
    private int board [][];

    private int tipRow;
    private int tipCol;

    private int goalRow;
    private int goalCol;



    public TipOverConfig(String filename) throws FileNotFoundException {
        try (Scanner in = new Scanner(new File(filename))) {
            this.height = in.nextInt();
            this.width = in.nextInt();
            this.tipRow = in.nextInt();
            this.tipCol = in.nextInt();
            this.goalRow = in.nextInt();
            this.goalCol = in.nextInt();

            board = new int[height][width];

            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    board[i][j] = in.nextInt();
                }
            }
        }
    }

    public TipOverConfig (TipOverConfig original){
        this.height = original.height;
        this.width = original.width;
        this.tipRow = original.tipRow;
        this.tipCol = original.tipCol;
        this.goalRow = original.goalRow;
        this.goalCol = original.goalCol;

        this.board = new int[width][height];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                this.board[i][j] = original.board[i][j];
            }
        }

    }

    @Override
    public boolean isSolution(Object o) {
        boolean holding = false;
        if(tipRow==goalRow && tipCol==goalCol){
            holding = true;
        }
        return holding;
    }

    @Override
    public ArrayList getNeighbors(Object o) {
        return null;
    }
}
