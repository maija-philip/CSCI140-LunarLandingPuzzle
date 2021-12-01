package puzzles.tipover.ptui;

import puzzles.tipover.model.TipOverModel;
import java.util.Scanner;
import java.util.ArrayList;
import util.Observer;

/**
 * DESCRIPTION
 * @author YOUR NAME HERE
 * November 2021
 */
public class TipOverPTUI implements Observer<TipOverModel, Object > {

    private TipOverModel model;
    private int tempNum = 5;
    private int[][] tempBoard = new int[5][4];

    public TipOverPTUI(){
        this.model = new TipOverModel();
        initializeView();
    }

    private void run(){
        Scanner in = new Scanner(System.in);
        for( ; ; ){
            System.out.println("game command: ");
            String line = in.nextLine();
            String[] words = line.split(("\\s+"));
            if(words.length > 0){
                if(words[0].startsWith("q")){
                    break;
                } else if (words[0].startsWith("l")){
                    this.model.load();
                } else if (words[0].startsWith("r")){
                    this.model.reload();
                } else if (words[0].startsWith("m")){
                    this.model.move(words[1]);
                } else if (words[0].startsWith("hi")){
                    this.model.hint();
                } else if (words[0].startsWith("s")){
                    displayBoard(tempNum, tempBoard);
                } else {
                    help();
                }
            }
        }
    }

    public void initializeView(){
        this.model.addObserver(this);
        update(this.model,null);
    }

    public void displayBoard(int numMoves, int[][] board){
        System.out.println("Behold, a board!");
    }

    public void help(){
        System.out.println("l(oad)   -- load new puzzle from file");
        System.out.println("r(eload) -- reload current puzzle");
        System.out.println("m(ove) d -- move tipper in d direction");
        System.out.println("hi(nt)   -- receive hint");
        System.out.println("s(how)   -- display the board");
        System.out.println("he(lp)   -- show all commands");
        System.out.println("q(uit)   -- quit the game");
    }

    public void update(TipOverModel o, Object arg){
        System.out.println("updating...");
    }

    public static void main( String[] args ) {
        TipOverPTUI ptui = new TipOverPTUI();
        ptui.run();
    }
}
