package puzzles.tipover.gui;


import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import puzzles.tipover.model.TipOverModel;
import util.Observer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * DESCRIPTION
 * @author Caitlin Patton
 * November 2021
 */
public class TipOverGUI extends Application
        implements Observer< TipOverModel, Object > {

    private TipOverModel model;


    private Label feedback;
    private String labelText = "New file loaded.";
    private Label[][] squares;
    private BorderPane borderPane;

    private static final Background WHITE = new Background(new BackgroundFill(Color.WHITE,null,null));
    private static final Background RED = new Background(new BackgroundFill(Color.RED,null,null));
    private static final Background PINK = new Background(new BackgroundFill(Color.PINK,null,null));


    private static final int SQUARES_FONT_SIZE = 40;
    private static final int BUTTONS_FONT_SIZE = 20;

    @Override
    public void init() throws Exception {
        System.out.println("init: Initialize and connect to model!");
        List<String> args = getParameters().getRaw();

        this.model = new TipOverModel(args.get(0));
        this.model.addObserver(this);
    }

    @Override
    public void start( Stage stage ) {
        this.borderPane = new BorderPane();

        this.feedback = new Label(this.labelText);
        this.borderPane.setTop(this.feedback);
        BorderPane.setAlignment(this.feedback, Pos.TOP_LEFT);

        makeBoard();

        VBox sideBar = new VBox();
        VBox sideButtons = new VBox();

        BorderPane controlButtons = new BorderPane();
        Button up = new Button("⇧");
        Button down = new Button("⇩");
        Button left = new Button("⇦");
        Button right = new Button("⇨");
        Button center = new Button(" ");
        center.setBackground(WHITE);
        up.setStyle("-fx-font: " + BUTTONS_FONT_SIZE + " arial;");
        down.setStyle("-fx-font: " + BUTTONS_FONT_SIZE + " arial;");
        left.setStyle("-fx-font: " + BUTTONS_FONT_SIZE + " arial;");
        right.setStyle("-fx-font: " + BUTTONS_FONT_SIZE + " arial;");
        center.setStyle("-fx-font: " + BUTTONS_FONT_SIZE + " arial;");
        controlButtons.setTop(up);
        controlButtons.setBottom(down);
        controlButtons.setLeft(left);
        controlButtons.setRight(right);
        controlButtons.setCenter(center);
        BorderPane.setAlignment(up, Pos.CENTER);
        BorderPane.setAlignment(down, Pos.CENTER);
        BorderPane.setAlignment(left,Pos.CENTER_LEFT);
        BorderPane.setAlignment(right,Pos.CENTER_RIGHT);

        up.setOnAction((event) -> {
            this.model.move("UP");
            update(this.model,null);
            //System.out.println("up");
            //System.out.println(this.model.getCurrentConfig().getTipRow() + " " + this.model.getCurrentConfig().getTipCol() + " " + this.model.getCurrentConfig().getBoard()[this.model.getCurrentConfig().getTipRow()][this.model.getCurrentConfig().getTipCol()]);
        });
        down.setOnAction((event) -> {
            this.model.move("DOWN");
            update(this.model,null);
            //System.out.println("down");
            //System.out.println(this.model.getCurrentConfig().getTipRow() + " " + this.model.getCurrentConfig().getTipCol() + " " + this.model.getCurrentConfig().getBoard()[this.model.getCurrentConfig().getTipRow()][this.model.getCurrentConfig().getTipCol()]);
        });
        left.setOnAction((event) -> {
            this.model.move("LEFT");
            update(this.model,null);
            //System.out.println("left");
            //System.out.println(this.model.getCurrentConfig().getTipRow() + " " + this.model.getCurrentConfig().getTipCol() + " " + this.model.getCurrentConfig().getBoard()[this.model.getCurrentConfig().getTipRow()][this.model.getCurrentConfig().getTipCol()]);
        });
        right.setOnAction((event) -> {
            this.model.move("RIGHT");
            update(this.model,null);
            //System.out.println("right");
            //System.out.println(this.model.getCurrentConfig().getTipRow() + " " + this.model.getCurrentConfig().getTipCol() + " " + this.model.getCurrentConfig().getBoard()[this.model.getCurrentConfig().getTipRow()][this.model.getCurrentConfig().getTipCol()]);
        });

        Button load = new Button("Load");
        Button reload = new Button("Reload");
        Button hint = new Button("Hint");

        load.setStyle("-fx-font: " + BUTTONS_FONT_SIZE + " arial;");
        reload.setStyle("-fx-font: " + BUTTONS_FONT_SIZE + " arial;");
        hint.setStyle("-fx-font: " + BUTTONS_FONT_SIZE + " arial;");

        sideButtons.getChildren().addAll(load,reload,hint);
        sideBar.getChildren().addAll(controlButtons,sideButtons);
        this.borderPane.setRight(sideBar);
        BorderPane.setAlignment(sideBar, Pos.CENTER_RIGHT);

        load.setOnAction((event) -> {
            //System.out.println("load pressed");
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Choose New Board");
            File file = fileChooser.showOpenDialog(stage);

            if (file != null) {

                this.model.load(file.getPath());

                makeBoard();
                update(this.model, null);
                this.feedback.setText("File Loaded");
            }

        });
        reload.setOnAction((event) -> {
            this.model.reload();
            makeBoard();
            update(this.model, null);
            this.feedback.setText("File Loaded");
            //System.out.println("reload pressed");
        });
        hint.setOnAction((event) -> {
            this.model.hint();
            update(this.model, null);
            //System.out.println("hint pressed");
        });


        Scene scene = new Scene( borderPane);
        stage.setScene( scene );
        stage.setTitle( "Tip Over" );

        //System.out.println("start()");
        stage.show();
    }

    public void makeBoard(){
        GridPane gridPane = new GridPane();
        squares = new Label[this.model.getCurrentConfig().getHeight()][this.model.getCurrentConfig().getWidth()];
        for (int i = 0; i < this.model.getCurrentConfig().getHeight(); i++) {
            for (int j = 0; j < this.model.getCurrentConfig().getWidth(); j++) {
                Label square = new Label(" " + this.model.getCurrentConfig().getBoard()[i][j] + " ");
                square.setStyle("-fx-font: " + SQUARES_FONT_SIZE + " arial;");
                if(i==this.model.getCurrentConfig().getGoalRow() & j==this.model.getCurrentConfig().getGoalCol()){
                    square.setBackground(RED);
                } else if (i==this.model.getCurrentConfig().getTipRow() & j==this.model.getCurrentConfig().getTipCol()){
                    square.setBackground(PINK);
                } else {
                    square.setBackground(WHITE);
                }
                this.squares[i][j] = square;
                gridPane.add(square,j,i);
            }
        }
        this.borderPane.setLeft(gridPane);
        BorderPane.setAlignment(gridPane,Pos.CENTER_LEFT);

    }

    public void updateBoard(int[][] numbers){
        for(int i=0 ; i < this.model.getCurrentConfig().getHeight(); i++){
            for(int j=0; j < this.model.getCurrentConfig().getWidth(); j++){
                Label tempL = squares[i][j];
                int tempN = numbers[i][j];

                if(tempN != Integer.parseInt(tempL.getText().trim())){
                    tempL.setText( " " + tempN + " ");
                }

                if(i==this.model.getCurrentConfig().getGoalRow() & j==this.model.getCurrentConfig().getGoalCol()){
                    tempL.setBackground(RED);
                } else if (i==this.model.getCurrentConfig().getTipRow() & j==this.model.getCurrentConfig().getTipCol()){
                    tempL.setBackground(PINK);
                } else {
                    tempL.setBackground(WHITE);
                }
            }

        }
    }

    @Override
    public void update( TipOverModel tipOverModel, Object o ) {
        //System.out.println( "My model has changed! (DELETE THIS LINE)");

        if(this.model.getHintWin()){
            this.feedback.setText("I WON!");
        } else if (this.model.isSolution(this.model.getCurrentConfig())){
            this.feedback.setText("YOU WON!");
        } else {
            feedback.setText(this.model.getFeedback());
        }

        updateBoard(this.model.getCurrentConfig().getBoard());
    }

    public static void main( String[] args ) {
        //System.err.println( "REPLACE THIS METHOD!" );

        Application.launch( args );
    }
}
