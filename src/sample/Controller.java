package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import sample.DataModel.DataSource;
import sample.DataModel.rooms;

public class Controller {
    @FXML
    private TableView<rooms> roomsTableView;

    public void listRooms(){
        Task<ObservableList<rooms>> task = new GetAllRooms();
        roomsTableView.itemsProperty().bind(task.valueProperty());

        new Thread(task).start();
    }

}


class GetAllRooms extends Task{
    @Override
    protected ObservableList<rooms> call() throws Exception {
        return FXCollections.observableArrayList
                (DataSource.getInstance().queryAllRooms());
    }

}