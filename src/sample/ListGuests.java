package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import sample.DataModel.DataSource;
import sample.DataModel.Guests;
import sample.DataModel.Rooms;

/**
 * Created by Chris on 8/25/2017.
 */
public class ListGuests {

    @FXML
    private TableView guestlists;


    public void listGuests(){
        Task<ObservableList<Rooms>> task = new GetAllGuests();
        guestlists.itemsProperty().bind(task.valueProperty());

        new Thread(task).start();
    }
}

class GetAllGuests extends Task{
    @Override
    protected ObservableList<Guests> call() throws Exception {
        return FXCollections.observableArrayList
                (DataSource.getInstance().queryAllGuests());
    }
}