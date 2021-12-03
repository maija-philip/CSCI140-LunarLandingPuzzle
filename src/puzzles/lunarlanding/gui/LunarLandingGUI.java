package puzzles.lunarlanding.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import puzzles.lunarlanding.model.LunarLandingModel;
import util.Observer;

import java.io.File;
import java.util.List;

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

    private static final double WIDTH = 640;
    private static final double HEIGHT = 480;

    @Override
    public void init() throws Exception {
        System.out.println("init: Initialize and connect to model!");
        List<String> args = getParameters().getRaw();

        this.model = new LunarLandingModel(args.get(0));
        this.model.addObserver( this );
        //update( this.model, null );
    }

    @Override
    public void start( Stage stage ) {
        stage.setTitle( "Lunar Landing" );
        Image spaceship = new Image(
                LunarLandingGUI.class.getResourceAsStream(
                        "resources" + File.separator + "explorer.png"
                )
        );

        Label temp = new Label();
        temp.setGraphic( new ImageView( spaceship ) );
        Scene scene = new Scene( temp, WIDTH, HEIGHT );
        stage.setScene( scene );
        stage.show();
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
