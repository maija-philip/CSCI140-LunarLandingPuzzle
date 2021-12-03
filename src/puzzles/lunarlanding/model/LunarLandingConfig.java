package puzzles.lunarlanding.model;

import puzzles.lunarlanding.LunarLanding;
import solver.Configuration;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * DESCRIPTION
 * @author Maija Philip
 * November 2021
 */
public class LunarLandingConfig implements Configuration<LunarLandingConfig>{

    /* board details */
    private int width;
    private int height;
    private String board[][];

    /* important coordinates */
    private int goalRow;
    private int goalCol;

    private int explorerRow;
    private int explorerCol;

    /* Robots */
    private HashMap<String, int[]> robots;



    /**
     * Construct the initial configuration from an input file whose contents
     * are, for example:<br>
     * <tt><br>
     * 3 3          # rows columns<br>
     * 1 . #        # row 1, .=empty, 1-9=numbered island, #=island, &#64;=sea<br>
     * &#64; . 3    # row 2<br>
     * 1 . .        # row 3<br>
     * </tt><br>
     *
     * @param filename the name of the file to read from
     * @throws FileNotFoundException if the file is not found
     */
    public LunarLandingConfig(String filename) throws FileNotFoundException {
        try (Scanner in = new Scanner(new File(filename))) {

            this.height = in.nextInt();
            this.width = in.nextInt();
            board = new String[height][width];

            // fill board with underscores
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    board[i][j] = "_";
                }
            }


            // set up and place the goal
            this.goalRow = in.nextInt();
            this.goalCol = in.nextInt();
            board[goalRow][goalCol] = "!";


            // set up and place the robots
            robots = new HashMap<>();

            String s = in.nextLine();
            s = in.nextLine();
            String tempLetter = "_";

            //System.out.println(s);

            while (!s.isEmpty()) {
                //System.out.println(s);
                s = s.strip();
                String details[] = s.split("\\s+");

                tempLetter = details[0];

                int row = Integer.parseInt(details[1]);
                int col = Integer.parseInt(details[2]);

                if (tempLetter.equals("E")){
                    explorerCol = row;
                    explorerRow = col;
                }


                if (board[row][col].equals("!")) {
                    board[row][col] = "!" + tempLetter;

                } else {
                    board[row][col] = tempLetter;

                }

                robots.put(tempLetter, new int[] {row, col});

                s = in.nextLine();
            }


        } // try-with-resources, the file is closed automatically

        System.out.println(this);
    }

    private LunarLandingConfig(LunarLandingConfig copy) {

        /* board details */
        this.width = copy.width;
        this.height = copy.height;
        this.board = new String[height][width];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                this.board[i][j] = copy.board[i][j];
            }
        }


        /* important coordinates */
        this.goalRow = copy.goalRow;
        this.goalCol = copy.goalCol;


        this.explorerRow = copy.explorerRow;
        this.explorerCol = copy.explorerCol;

        /* Robots */
        this.robots = new HashMap<>();

        for (Map.Entry<String, int[]> robot: copy.robots.entrySet()) {

            String letter = robot.getKey();
            int robotX = robot.getValue()[0];
            int robotY = robot.getValue()[1];

            this.robots.put(letter, new int[] {robotX, robotY});
        }

    }

    @Override
    public boolean isSolution(LunarLandingConfig lunarLandingConfig) {
        return lunarLandingConfig.board[goalRow][goalCol].equals("!E");
    }

    int countttt = 0;

    @Override
    public ArrayList<LunarLandingConfig> getNeighbors(LunarLandingConfig lunarLandingConfig) {
        //System.out.println("getNeighbors");
        // countttt++;
        if (countttt > 10) {
            return new ArrayList<>();
        }

        ArrayList<LunarLandingConfig> neighbors = new ArrayList<>();


        for (Map.Entry<String, int[]> robot: lunarLandingConfig.robots.entrySet()) {

            String letter = robot.getKey();
            int robotX = robot.getValue()[0];
            int robotY = robot.getValue()[1];

            //System.out.println(robot + ", letter: " + letter + ", [ " + robotX + ", " + robotY + " ]");

            String[] directions = {"UP", "DOWN", "LEFT", "RIGHT"};

            for (String direction : directions) {

                // check for legal moves in direction
                int[] coordinates = lunarLandingConfig.getRobotCoordinates(robotX, robotY, direction);

                // if it is legal move, adds a possible next move with the move
                if (coordinates[0] != -1) {

                    int x = coordinates[0];
                    int y = coordinates[1];

                    // creates new move
                    LunarLandingConfig nextMove = new LunarLandingConfig(lunarLandingConfig);

                    if (robotX == goalRow && robotY == goalCol) {
                        nextMove.board[robotX][robotY] = "!";
                    } else {
                        nextMove.board[robotX][robotY] = "_";

                    }

                    if (x == goalRow && y == goalCol) {
                        nextMove.board[x][y] = "!" + letter;
                    } else {
                        nextMove.board[x][y] = letter;
                    }

                    nextMove.robots.replace(letter, new int[]{x, y});

                    //System.out.println(nextMove);
                    neighbors.add(nextMove);
                }
            }

        }
        //System.out.println("how many neighbors " + neighbors.size());
        return neighbors;
    }


    // Find the first robot we encounter in moving in this direction if any
    private int[] getRobotCoordinates(int x, int y, String direction) {
        //System.out.println("getRobotCoordinates");
        int startX = x, startY = y;
        int lastX = x, lastY = y;
        int[] noMove = new int[]{-1, -1};

        // move one
        switch (direction) {
            case "UP" -> x--;
            case "RIGHT" -> y++;
            case "DOWN" -> x++;
            case "LEFT" -> y--;
        }

        while (isCoordinateInGrid(x,y)) {

            // We found a robot, in a normal space
            if  (!board[x][y].equals("_") && !board[x][y].equals("!")) {
                // If we haven't actually moved, return noMove
                if (lastX == startX && lastY == startY) {
                    return noMove;
                }
                return new int[]{lastX, lastY};
            }

            lastX = x;
            lastY = y;
            // No robot yet, keep going.
            switch (direction) {
                case "UP" -> x--;
                case "RIGHT" -> y++;
                case "DOWN" -> x++;
                case "LEFT" -> y--;
            }
        }

        // We hit the end of the grid with no robots
        return noMove;
    }

    /**
     * checks to see if the coordinates are in the board
     * @param x - x coordinate of position being checked
     * @param y - y coordinate of position being checked
     * @return - if coordinate is a valid position in board
     */
    private boolean isCoordinateInGrid(int x, int y) {
        return x >= 0 && x < height && y >= 0 && y < width;
    }



    /**
     * Returns the string representation of the puzzle, e.g.: <br>
     * <tt><br>
     * 1 . #<br>
     * &#64; . 3<br>
     * 1 . .<br>
     * </tt><br>
     */
    @Override
    public String toString() {
        String result = "      ";
        String tempTabLine = "    ";

        // write the numbers along the top and the dashed line
        for (int w = 0; w < width; w++) {
            result += w + "  ";
            tempTabLine += "___";
        }

        // take the last space off and start new line
        result = result.substring(0, result.length()-1) + "\n";
        result += tempTabLine + "\n";


        for (int i = 0; i < height; i++) {

            result += " " + i + " |  ";

            for (int j = 0; j < width; j++) {
                String thisSpot = board[i][j];
                if (thisSpot.charAt(0) == '!') {
                    result = result.substring(0, result.length()-1) + board[i][j] + "  ";

                } else {
                    result += board[i][j] + "  ";

                }

            }
            result = result.substring(0, result.length()-2) + "\n";

        }
        return result;
    }


    @Override
    public boolean equals(Object o) {
        boolean result = false;
        if (o instanceof LunarLandingConfig other) {

            result = true;
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    if (this.board[i][j] != other.board[i][j]) {
                        //System.out.println(i + ", " + j);
                        return false;
                    }
                }
            }

            //result =  this.board == other.board;
        }
        return result;
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(width, height, goalRow, goalCol, explorerRow, explorerCol, robots);
        result = 31 * result + Arrays.hashCode(board);
        return result;
    }


    /**
     * gives the height of the board
     * @return - height of the board
     */
    public int getHeight() {return height;}
    /**
     * gives the width of the board
     * @return - width of the board
     */
    public int getWidth() {return width;}

    public LunarLandingConfig playMove(int x, int y, String direction, LunarLandingConfig current) {

        if (!isCoordinateInGrid(x, y)) {
            System.out.println("The coordinates (" + x + ", " + y + ") are not in the grid");
            return current;
        }

        if (!direction.equals("UP") && !direction.equals("RIGHT") && !direction.equals("DOWN") && !direction.equals("LEFT")) {
            System.out.println(direction + " is not a valid direction");
            System.out.println("valid directions are UP, DOWN, LEFT, RIGHT");
            return current;
        }

        if (board[x][y].equals("_") || board[x][y].equals("!")) {
            System.out.println("There is no robot or explorer at (" + x + ", " + y + ")");
            return current;

        }

        return createMove(x, y, direction, current);
    }

    private LunarLandingConfig createMove(int x, int y, String direction, LunarLandingConfig current) {
        // check for legal moves in direction
        int[] coordinates = current.getRobotCoordinates(x, y, direction);

        // if move is illegal, returns error message
        if (coordinates[0] == -1) {
            System.out.println("That is not a legal move");
            return current;
        }

        String letter = this.board[x][y];
        if (letter.charAt(0) == '!') {
            letter = letter.substring(1);
        }

        int newX = coordinates[0];
        int newY = coordinates[1];

        // creates new move
        LunarLandingConfig nextMove = new LunarLandingConfig(current);

        if (x == goalRow && y == goalCol) {
            nextMove.board[x][y] = "!";
        } else {
            nextMove.board[x][y] = "_";

        }

        if (newX == goalRow && newY == goalCol) {
            nextMove.board[newX][newY] = "!" + letter;
        } else {
            nextMove.board[newX][newY] = letter;
        }

        nextMove.robots.replace(letter, new int[]{newX, newY});

        return nextMove;


    }
}
