package sample;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import sample.Helpers.Bus;
import sample.Helpers.TableHelper;

public class TableController extends Base {


    @FXML
    private JFXComboBox<String> tech_cmb;

    @FXML
    private TableView<ObservableList<String>> tableView;

    public void initialize() {
        checkMaximize();
        navigationTollTips();


        System.out.printf("Hello "+ Bus.getInstance().getTechniquesList().size());
        initTechComboBox();
    }
    protected void initTechComboBox() {

        for (Techniques techniques: Bus.getInstance().getTechniquesList()) {
            tech_cmb.getItems().add(techniques.getTechName());
        }
        tech_cmb.getSelectionModel().select(0);

    }
    @FXML
    void getLogData(ActionEvent event) {

        int index = tech_cmb.getSelectionModel().getSelectedIndex();
        Techniques techniques = Bus.getInstance().getTechniquesList().get(index);
        TableHelper.insertDataInColumn(tableView, techniques.getCloudHeaders());
        TableHelper.insertDataInRow(tableView, techniques.getCloudLogs());
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

}
