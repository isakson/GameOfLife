package GameOfLife;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GameOfLife extends Application {

    /**
     * @param primaryStage The stage to initialize the program with.
     * Starts the program.
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("GameOfLife.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("Game of Life");

        Controller controller = loader.getController();
        root.setOnMouseClicked(controller);
        primaryStage.setScene(new Scene(root, 1000, 800));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
