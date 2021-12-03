package puzzles.lunarlanding.gui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import puzzles.lunarlanding.model.LunarLandingModel;
import util.Observer;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * DESCRIPTION
 * @author Maija Philip
 * November 2021
 */
public class LunarLandingGUI extends Application
        implements Observer< LunarLandingModel, Object > {

    /**
     * The model for the view and controller.
     */
    private LunarLandingModel model;

    private static final double WIDTH = 550;
    private static final double HEIGHT = 480;

    private int ROWS;
    private int COLS;

    /** GUI Elements to keep track of */
    private Label feedback;
    private Button[][] gridSpots;

    /** Images */
    private Image explorer;
    private Image[] robots;

    /** Currently Clicked button */
    private int[] clickedCoords;



    @Override
    public void init() throws Exception {
        System.out.println("init: Initialize and connect to model!");
        List<String> args = getParameters().getRaw();

        this.model = new LunarLandingModel(args.get(0));
        this.model.addObserver( this );

        System.out.println(args.get(0));
        //update( this.model, null );
    }

    @Override
    public void start( Stage stage ) {
        BorderPane borderPane = new BorderPane();

        // get the row and column values
        this.ROWS = this.model.getRows();
        this.COLS = this.model.getColumns();

        // set up the current clicked value
        clickedCoords = new int[] {-1, -1};

        // make the feedback label
        this.feedback = new Label("File loaded");
        borderPane.setTop(this.feedback);
        BorderPane.setAlignment(this.feedback, Pos.TOP_LEFT);

        // load images
        loadImages();

        // make board grid
        GridPane gridPane = new GridPane();
        makeBoard(gridPane);
        updateGrid(this.model.getRobots());

        borderPane.setLeft(gridPane);
        BorderPane.setAlignment(gridPane, Pos.TOP_LEFT);


        // make buttons area
        VBox vBox = new VBox();
        BorderPane arrowContainer = new BorderPane();

        // create arrow buttons and place them
        Button up = new Button("\u2191");
        Button right = new Button("\u2192");
        Button down = new Button("\u2193");
        Button left = new Button("\u2190");

        arrowContainer.setTop(up);
        arrowContainer.setRight(right);
        arrowContainer.setBottom(down);
        arrowContainer.setLeft(left);

        BorderPane.setAlignment(up, Pos.TOP_CENTER);
        BorderPane.setAlignment(right, Pos.CENTER_RIGHT);
        BorderPane.setAlignment(down, Pos.BOTTOM_CENTER);
        BorderPane.setAlignment(left, Pos.CENTER_LEFT);

        // add center space between buttons
        Label spacer = new Label("          ");
        spacer.setMaxWidth(Double.MAX_VALUE);
        arrowContainer.setCenter(spacer);


        // create load/reload/hint buttons and place them
        Button load = new Button("load");
        Button reload = new Button("reload");
        Button hint = new Button("hint");

        vBox.getChildren().addAll(arrowContainer, load, reload, hint);
        borderPane.setRight(vBox);
        BorderPane.setAlignment(vBox, Pos.TOP_RIGHT);


        // button click events

        // up button action
        up.setOnAction((event) -> {
            System.out.println("Up Pressed");
        });

        // right button action
        right.setOnAction((event) -> {
            System.out.println("Right Pressed");
        });

        // down button action
        down.setOnAction((event) -> {
            System.out.println("Down Pressed");
        });

        // left button action
        left.setOnAction((event) -> {
            System.out.println("Left Pressed");
        });

        // load button action
        load.setOnAction((event) -> {
            System.out.println("Load Pressed");
        });

        // reload button action
        reload.setOnAction((event) -> {
            System.out.println("Reload Pressed");
        });

        // hint button action
        hint.setOnAction((event) -> {
            System.out.println("Hint Pressed");
        });


        // prepare the stage to show
        Scene scene = new Scene( borderPane, WIDTH, HEIGHT );
        stage.setScene( scene );
        stage.setTitle( "Lunar Landing" );
        stage.show();
    }

    private void loadImages() {
        // load images
        this.explorer = new Image(getClass().getResourceAsStream("resources/explorer.png"));
        this.robots = new Image[8];

        this.robots[0] = new Image(getClass().getResourceAsStream("resources/robot-blue.png"));
        this.robots[1] = new Image(getClass().getResourceAsStream("resources/robot-green.png"));
        this.robots[2] = new Image(getClass().getResourceAsStream("resources/robot-lightblue.png"));
        this.robots[3] = new Image(getClass().getResourceAsStream("resources/robot-orange.png"));
        this.robots[4] = new Image(getClass().getResourceAsStream("resources/robot-pink.png"));
        this.robots[5] = new Image(getClass().getResourceAsStream("resources/robot-purple.png"));
        this.robots[6] = new Image(getClass().getResourceAsStream("resources/robot-white.png"));
        this.robots[7] = new Image(getClass().getResourceAsStream("resources/robot-yellow.png"));
    }

    private void makeBoard(GridPane gridPane) {

        double temp = ((HEIGHT-15)/ROWS)*COLS + 1;
        gridPane.setPrefWidth(temp);
        gridPane.setMinWidth(temp);
        gridPane.setMaxWidth(temp);


        this.gridSpots = new Button[ROWS][COLS];
        int spotCount = 0;

        // for each space in the grid
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                int roww = row;
                int coll = col;

                gridPane.getColumnConstraints().add(new ColumnConstraints((HEIGHT-15)/ROWS));

                // Button space = new Button(spotCount + "");
                Button space = new Button();
                space.setStyle("-fx-background-color: gray; -fx-background-radius: 0");


                space.setMaxWidth(Double.MAX_VALUE);
                space.setMaxHeight(Double.MAX_VALUE);
                space.setPadding(new Insets(0, 0, 0, 0));

                space.setOnAction((event) -> {
                    // System.out.println("Grid Button Pressed");
                    clickedCoords = new int[] {roww, coll};
                    feedback.setText("(" + clickedCoords[0] + ", " + clickedCoords[1] + ") Selected");
                });

                gridSpots[row][col] = space;
                gridPane.add(space, col, row);
                spotCount++;
            }
            gridPane.getRowConstraints().add(new RowConstraints((HEIGHT-15)/ROWS));

        }

        // make the goal square pink
        int goalX = this.model.getGoal()[0];
        int goalY = this.model.getGoal()[1];

        this.gridSpots[goalX][goalY].setStyle("-fx-background-color: pink;");

    }

    private void updateGrid(HashMap<String, int[]> robots) {

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                gridSpots[i][j].setGraphic(null);
            }
        }


        for (Map.Entry<String, int[]> robot: robots.entrySet()) {

            String letter = robot.getKey();
            int robotX = robot.getValue()[0];
            int robotY = robot.getValue()[1];

            Button tempSpace = gridSpots[robotX][robotY];
            assignImage(tempSpace, letter);

        }
    }

    private void assignImage(Button space, String letter) {

        int imageNumber = 6;

        // assign image based on letter
        switch (letter) {
            case "B" -> imageNumber = 0;
            case "G" -> imageNumber = 1;
            case "L" -> imageNumber = 2;
            case "O" -> imageNumber = 3;
            case "P" -> imageNumber = 4;
            case "R" -> imageNumber = 5;
            case "W" -> imageNumber = 6;
            case "Y" -> imageNumber = 7;
        }

        space.setGraphic(new ImageView(robots[imageNumber]));

    }


    @Override
    public void update( LunarLandingModel lunarLandingModel, Object o ) {
        System.out.println( "My model has changed! (DELETE THIS LINE)");
    }

    public static void main( String[] args ) {
        // System.err.println( "REPLACE THIS METHOD!" );
        Application.launch( args );
    }
}
