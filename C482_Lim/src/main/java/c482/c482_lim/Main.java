package c482.c482_lim;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * This class creates the Main Screen controller.
 * @author Alexandria Lim
 */
public class Main extends Application {

    /**
     * This method sets up the main screen.
     * @param firstStage a stage for the main screen
     * @throws IOException
     */
    @Override
    public void start(Stage firstStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/c482/Views/Main.fxml"));
        //Create Scene
        Scene scene = new Scene(fxmlLoader.load(), 1200, 400);
        firstStage.setTitle("First Screen");
        firstStage.setScene(scene);
        firstStage.show();
    }

    /**
     * This method launches the program.
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //Launch Program
        launch();
    }

}