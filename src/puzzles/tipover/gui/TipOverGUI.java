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

    private int rows;
    private int cols;

    private Label feedback;
    private ArrayList<Label> squares;

    //private static final Background WHITE = new Background(new BackgroundFill(Color.WHITE,null,null));

    @Override
    public void init() throws Exception {
        System.out.println("init: Initialize and connect to model!");
        List<String> args = getParameters().getRaw();

        this.model = new TipOverModel(args.get(0));
        this.model.addObserver(this);
    }

    @Override
    public void start( Stage stage ) {
        BorderPane borderPane = new BorderPane();

        this.feedback = new Label("It's a label!");
        borderPane.setTop(this.feedback);
        BorderPane.setAlignment(this.feedback, Pos.TOP_LEFT);

        GridPane gridPane = new GridPane();
        for (int i = 0; i < this.model.getCurrentConfig().getHeight(); i++) {
            for (int j = 0; j < this.model.getCurrentConfig().getWidth(); j++) {
                Label label = new Label(" " + this.model.getCurrentConfig().getBoard()[i][j] + " ");
                gridPane.add(label,i,j);
            }
        }
        borderPane.setLeft(gridPane);
        BorderPane.setAlignment(gridPane,Pos.CENTER_LEFT);

        VBox sideBar = new VBox();
        VBox sideButtons = new VBox();

        BorderPane controlButtons = new BorderPane();
        Button up = new Button("^");
        Button down = new Button("\\/");
        Button left = new Button("<");
        Button right = new Button(">");
        controlButtons.setTop(up);
        controlButtons.setBottom(down);
        controlButtons.setLeft(left);
        controlButtons.setRight(right);
        BorderPane.setAlignment(up, Pos.CENTER);
        BorderPane.setAlignment(down, Pos.CENTER);

        Button load = new Button("Load");
        Button reload = new Button("Reload");
        Button hint = new Button("Hint");

        sideButtons.getChildren().addAll(load,reload,hint);
        sideBar.getChildren().addAll(controlButtons,sideButtons);
        borderPane.setRight(sideBar);
        BorderPane.setAlignment(sideBar, Pos.CENTER_RIGHT);

        load.setOnAction((event) -> {System.out.println("load pressed");});
        reload.setOnAction((event) -> {System.out.println("reload pressed");});
        hint.setOnAction((event) -> {System.out.println("hint pressed");});

        Scene scene = new Scene( borderPane, 640, 480 );
        stage.setScene( scene );
        stage.setTitle( "Tip Over" );

        System.out.println("start()");
        stage.show();
    }

    @Override
    public void update( TipOverModel tipOverModel, Object o ) {
        System.out.println( "My model has changed! (DELETE THIS LINE)");
    }

    public static void main( String[] args ) {
        //System.err.println( "REPLACE THIS METHOD!" );

        Application.launch( args );
    }
}
