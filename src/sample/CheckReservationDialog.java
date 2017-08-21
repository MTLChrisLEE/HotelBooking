package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import sample.DataModel.DataSource;
import sample.DataModel.reservation;
import sample.DataModel.rooms;



/**
 * Created by Chris on 8/21/2017.
 */
public class CheckReservationDialog {

    @FXML
    private TableView<reservation> reservationTableView;

    public void listReservation(){
        Task<ObservableList<reservation>> task = new GetAllReservation();
        reservationTableView.itemsProperty().bind(task.valueProperty());

        new Thread(task).start();
    }

}


class GetAllReservation extends Task{
    @Override
    protected ObservableList<reservation> call() throws Exception {
        return FXCollections.observableArrayList
                (DataSource.getInstance().queryAllReservation());
    }

}

















