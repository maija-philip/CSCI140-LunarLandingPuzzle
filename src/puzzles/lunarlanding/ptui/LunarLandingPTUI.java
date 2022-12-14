package puzzles.lunarlanding.ptui;

import puzzles.lunarlanding.model.LunarLandingConfig;
import puzzles.lunarlanding.model.LunarLandingModel;
import puzzles.tipover.model.TipOverModel;
import puzzles.tipover.ptui.TipOverPTUI;
import util.Observer;

import java.util.Scanner;

/**
 * runs the PTUI for the lunar landing game and delegates the actions that the user chooses
 * @author Maija Philip
 * November 2021
 */
public class LunarLandingPTUI implements Observer< LunarLandingModel, Object > {

    private LunarLandingModel model;
    private int movesNum;

    public LunarLandingPTUI(String filename){
        this.model = new LunarLandingModel(filename);
        initializeView();
    }

    /**
     * runs the game, takes what the user selected and delegates the according action
     */
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
                    this.model.load(words[1]);
                    displayBoard(movesNum);
                } else if (words[0].startsWith("r")){
                    this.model.reload();
                    movesNum = 0;
                    displayBoard(movesNum);
                } else if (words[0].startsWith("m")){
                    this.model.move(words[1], words[2], words[3]);
                    this.movesNum+=1;
                    System.out.println(this.model.getFeedback());
                } else if (words[0].startsWith("hi")){
                    this.model.hint();
                    displayBoard(movesNum);
                } else if (words[0].startsWith("s")){
                    displayBoard(movesNum);
                } else {
                    help();
                }
            }

            if(this.model.isSolution()){
                displayBoard(movesNum);
                System.out.println("YOU WIN!");
                break;
            }
        }
    }

    /**
     * sets up this as an observer to the model
     */
    public void initializeView(){
        this.model.addObserver(this);
        update(this.model,null);
    }

    /**
     * displays the current board
     * @param numMoves - number of moves that have been played
     */
    public void displayBoard(int numMoves){
        System.out.println("Move " + numMoves + ": ");
        System.out.println(this.model.getCurrentConfig());
    }

    /**
     * displays the list of commands
     */
    public void help(){
        System.out.println("l(oad) f     -- load new puzzle from file f");
        System.out.println("r(eload)     -- reload current puzzle");
        System.out.println("m(ove) x y d -- move robot at x,y in d direction");
        System.out.println("                UP, DOWN, LEFT, RIGHT");
        System.out.println("hi(nt)       -- receive hint");
        System.out.println("s(how)       -- display the board");
        System.out.println("he(lp)       -- show all commands");
        System.out.println("q(uit)       -- quit the game");
    }


    @Override
    public void update(LunarLandingModel lunarLandingModel, Object o) {
        displayBoard(this.movesNum);
    }

    public static void main(String[] args ) {
        LunarLandingPTUI ptui = new LunarLandingPTUI(args[0]);
        ptui.run();
    }
}
