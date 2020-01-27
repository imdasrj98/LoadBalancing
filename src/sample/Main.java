package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import sample.Helpers.Bus;
import sample.Helpers.TechDetails;

public class Main extends Application {

    public static Stage stage;

    public Screen screen = Screen.getPrimary();

    Rectangle2D bounds = screen.getVisualBounds();

    public double screenXmin = bounds.getMinX();

    public double screenYmin = bounds.getMinY();

    public double screenMaxWidth = bounds.getWidth();

    public double screenMaxHeight = bounds.getHeight();

    public final int WIDTH = 1200;
    public final int HEIGHT = 650;
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));

        Bus.setInstance(new TechDetails());
        stage = primaryStage;
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, WIDTH, HEIGHT));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
