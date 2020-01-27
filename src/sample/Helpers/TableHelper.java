package sample.Helpers;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.List;

public class TableHelper {

    public static void insertDataInColumn(TableView<ObservableList<String>> tableView,List<String> columnNames) {
        tableView.getColumns().clear();
        for (int i = 0; i < columnNames.size(); i++) {
            final int finalIdx = i;
            TableColumn<ObservableList<String>, String> column = new TableColumn<>(
                    columnNames.get(i)
            );
            column.setCellValueFactory(param ->
                    new ReadOnlyObjectWrapper<>(param.getValue().get(finalIdx))
            );
            column.setPrefWidth(100);


            tableView.getColumns().add(column);
        }
    }

    public static void insertDataInRow(TableView<ObservableList<String>> tableView, List<List<String>> arrayLists) {

        for (int i = 0; i < arrayLists.size(); i++) {
            tableView.getItems().add(
                    FXCollections.observableArrayList(
                            arrayLists.get(i)
                    )
            );
        }
    }


}
