package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import sample.Helpers.Messages;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Base extends Main {

    @FXML
    public StackPane stack_pane;

    @FXML
    public AnchorPane anchorPaneResize;



    @FXML
    public AnchorPane titlePane;

    @FXML
    public Label titlePaneHeading;

    @FXML
    public JFXButton home_btn;

    @FXML
    public JFXButton chart_btn;

    @FXML
    public JFXButton table_btn;



    List<Techniques> techniquesList = new ArrayList<>();
    List<String> tableColumn = new ArrayList<>();

    public Base() {

    }



    @FXML
    void openWindowAction(ActionEvent event) {

        String scene = "";

        if (event.getSource() == home_btn)
            scene = Locations.HOME;
        else if (event.getSource() == chart_btn)
            scene = Locations.GRAPH_SCENE;
        else if(event.getSource() == table_btn)
            scene = Locations.TABLE_SCENE;

        loadFrame(scene);
    }
    protected Label getSegoiLabel(String s) {
        Label label = new Label(s);
        label.setFont(new Font("Segoi Ui", 20));
        return label;
    }



    public void loadFrame(String frameLocation) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(frameLocation));
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void checkMaximize() {
        if (stage!=null){
            if (stage.isMaximized()) {
                anchorPaneResize.setPrefWidth(screenMaxWidth);
                anchorPaneResize.setPrefHeight(screenMaxHeight);
            }else {
                anchorPaneResize.setPrefWidth(WIDTH);
                anchorPaneResize.setPrefHeight(HEIGHT);

            }
        }

    }

    public void navigationTollTips() {
        Tooltip tooltip1 = new Tooltip();
        Tooltip tooltip2 = new Tooltip();
        Tooltip tooltip3 = new Tooltip();
        Tooltip tooltip4 = new Tooltip();
        Tooltip tooltip5 = new Tooltip();
        Tooltip tooltip6 = new Tooltip();
        Tooltip tooltip7 = new Tooltip();
        Tooltip tooltip8 = new Tooltip();

        tooltip1.setText(Messages.HOME);
        tooltip2.setText(Messages.GRAPH);
        tooltip3.setText(Messages.TABLE);

        home_btn.setTooltip(tooltip1);
        chart_btn.setTooltip(tooltip2);
        table_btn.setTooltip(tooltip3);

    }

}
