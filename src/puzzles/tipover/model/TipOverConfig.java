package puzzles.tipover.model;

import puzzles.lunarlanding.model.LunarLandingConfig;
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
public class TipOverConfig implements Configuration<TipOverConfig> {

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

        this.board = new int[height][width];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                this.board[i][j] = original.board[i][j];
            }
        }

    }

    @Override
    public boolean isSolution(TipOverConfig o) {
        boolean holding = false;
        if(o.tipRow==o.goalRow && o.tipCol==o.goalCol){
            holding = true;
        }
        return holding;
    }

    @Override
    public ArrayList<TipOverConfig> getNeighbors(TipOverConfig c) {

        ArrayList<TipOverConfig> neighbors = new ArrayList<>();

        ArrayList<int[]> coordinates = getDirections(c);
        int row = c.tipRow;
        int col = c.tipCol;

        if(c.board[row][col]==1){
            for(int[] position : coordinates){
                if(c.board[position[0]][position[1]] > 0){
                    TipOverConfig copy = new TipOverConfig(c);
                    copy.tipRow = position[0];
                    copy.tipCol = position[1];
                    neighbors.add(copy);
                }
            }
        }

        int[] up = getUp(c);
        int[] down = getDown(c);
        int[] left = getLeft(c);
        int[] right = getRight(c);

        if (c.board[c.tipRow][c.tipCol] > 1){
            if(isCoordinateInGrid(up[0],up[1])){
                if(c.board[up[0]][up[1]]==0){
                    TipOverConfig hold = TippingAttempt(c,"UP");
                    if(hold!=null){
                        hold.tipRow = up[0];
                        hold.tipCol = up[1];
                        neighbors.add(hold);
                    }
                } else if(c.board[up[0]][up[1]] > 0){
                    TipOverConfig copy = new TipOverConfig(c);
                    copy.tipRow = up[0];
                    copy.tipCol = up[1];
                    neighbors.add(copy);
                }
            }
            if(isCoordinateInGrid(down[0],down[1])){
                if(c.board[down[0]][down[1]]==0){
                    TipOverConfig hold = TippingAttempt(c,"DOWN");
                    if(hold!=null){
                        hold.tipRow = down[0];
                        hold.tipCol = down[1];
                        neighbors.add(hold);
                    }
                } else if(c.board[down[0]][down[1]] > 0){
                    TipOverConfig copy = new TipOverConfig(c);
                    copy.tipRow = down[0];
                    copy.tipCol = down[1];
                    neighbors.add(copy);
                }
            }
            if(isCoordinateInGrid(left[0],left[1])){
                if(c.board[left[0]][left[1]]==0){
                    TipOverConfig hold = TippingAttempt(c,"LEFT");
                    if(hold!=null){
                        hold.tipRow = left[0];
                        hold.tipCol = left[1];
                        neighbors.add(hold);
                    }
                } else if(c.board[left[0]][left[1]] > 0){
                    TipOverConfig copy = new TipOverConfig(c);
                    copy.tipRow = left[0];
                    copy.tipCol = left[1];
                    neighbors.add(copy);
                }
            }
            if(isCoordinateInGrid(right[0],right[1])){
                if(c.board[right[0]][right[1]]==0){
                    TipOverConfig hold = TippingAttempt(c,"RIGHT");
                    if(hold!=null){
                        hold.tipRow = right[0];
                        hold.tipCol = right[1];
                        neighbors.add(hold);
                    }
                } else if (c.board[right[0]][right[1]] > 0){
                    TipOverConfig copy = new TipOverConfig(c);
                    copy.tipRow = right[0];
                    copy.tipCol = right[1];
                    neighbors.add(copy);
                }
            }
        }


        return neighbors;
    }


    public TipOverConfig TippingAttempt(TipOverConfig c, String direction){
        int x = c.tipRow;
        int y = c.tipCol;

        TipOverConfig newTipOver = new TipOverConfig(c);

        switch (direction) {
            case "UP" -> x--;
            case "RIGHT" -> y++;
            case "DOWN" -> x++;
            case "LEFT" -> y--;
        }

        while(isCoordinateInGrid(x,y)){
            for(int i = 0 ; i < c.board[c.tipRow][c.tipCol]; i++){
                if(!isCoordinateInGrid(x,y)){
                    return null;
                }
                if (c.board[x][y]==0){
                    newTipOver.board[x][y] = 1;
                } else {
                    return null;
                }
                switch (direction) {
                    case "UP" -> x--;
                    case "RIGHT" -> y++;
                    case "DOWN" -> x++;
                    case "LEFT" -> y--;
                }
            }
            newTipOver.board[c.tipRow][c.tipCol] = 0;
            newTipOver.tipRow = x;
            newTipOver.tipCol = y;
            return newTipOver;
        }
        return null;
    }

    public boolean isCoordinateInGrid(int x, int y) {
        return x >= 0 && x < height && y >= 0 && y < width;
    }

    private ArrayList<int[]> getDirections(TipOverConfig c){

        ArrayList<int[]> cords = new ArrayList<>();

        int[] up = getUp(c);
        int[] down = getDown(c);
        int[] left = getLeft(c);
        int[] right = getRight(c);

        if(isCoordinateInGrid(up[0],up[1])){
            cords.add(up);
        }
        if(isCoordinateInGrid(down[0],down[1])){
            cords.add(down);
        }
        if(isCoordinateInGrid(left[0],left[1])){
            cords.add(left);
        }
        if(isCoordinateInGrid(right[0],right[1])){
            cords.add(right);
        }

        return cords;

    }


    private int[] getUp(TipOverConfig c){
        int[] hold = new int[2];
        if(c.tipRow-1>=0){
            hold[0] = c.tipRow-1;
            hold[1] = c.tipCol;
        } else {
            hold[0] = -1;
            hold[1] = -1;
        }
        return hold;
    }

    private int[] getDown(TipOverConfig c){
        int[] hold = new int[2];
        if(c.tipRow+1<=c.height-1){
            hold[0] = c.tipRow+1;
            hold[1] = c.tipCol;
        }  else {
            hold[0] = -1;
            hold[1] = -1;
        }
        return hold;
    }

    private int[] getLeft(TipOverConfig c){
        int[] hold = new int[2];
        if(c.tipCol-1>=0){
            hold[0] = c.tipRow;
            hold[1] = c.tipCol-1;
        } else {
            hold[0] = -1;
            hold[1] = -1;
        }
        return hold;
    }

    private int[] getRight(TipOverConfig c){
        int[] hold = new int[2];
        if(c.tipCol+1<=c.width-1){
            hold[0] = c.tipRow;
            hold[1] = c.tipCol+1;
        } else {
            hold[0] = -1;
            hold[1] = -1;
        }
        return hold;
    }

    @Override
    public String toString(){
        String top = "    ";
        String line = "    ";
        String text = "";
        for(int i = 0; i < width; i++){
            top += "  " + i;
            line += "___";
        }
        top += "\n";
        line += "\n";

        for(int i = 0; i < height; i++){
            text += " " + i + " |";
            for(int j = 0; j < width; j++){
                if (board[i][j] == 0){
                    text += "  _";
                } else if (j==goalCol && i==goalRow){
                    text += " !" + board[i][j];
                } else if (j==tipCol && i==tipRow){
                    text += " *" + board[i][j];
                } else {
                    text += "  " + board[i][j];
                }
            }
            text += "\n";
        }
        return top + line + text;
    }

    @Override
    public boolean equals(Object o) {
        boolean result = false;
        if (o instanceof TipOverConfig other) {
            result = true;
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    if (this.board[i][j] != other.board[i][j]) {
                        return false;
                    }
                }
            }
        }
        return result;
    }

    public int getTipRow(){
        return this.tipRow;
    }

    public void setTipRow(int x){
        this.tipRow = x;
    }

    public int getTipCol(){
        return this.tipCol;
    }

    public void setTipCol(int x){
        this.tipCol = x;
    }

    public int[][] getBoard(){
        return this.board;
    }

}
