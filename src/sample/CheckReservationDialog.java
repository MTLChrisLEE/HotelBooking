package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import sample.DataModel.DataSource;
import sample.DataModel.Reservation;

import java.sql.SQLException;


/**
 * Created by Chris on 8/21/2017.
 */
public class CheckReservationDialog {
    @FXML
    private TableView<Reservation> reservationTableView;

    public void listReservation(){
        Task<ObservableList<Reservation>> task = new GetAllReservation();
        reservationTableView.itemsProperty().bind(task.valueProperty());
        new Thread(task).start();
    }

    public void DeleteTheReservation(){
        Reservation reservation = reservationTableView.getSelectionModel().getSelectedItem();
        try{
            DataSource.getInstance().deleteFomReservation(reservation.getGuestID(),reservation.getRoomNumber());
        }catch (SQLException e){
            System.out.println("Cannot cancel the reservation: " + e.getMessage());
        }
        listReservation();
    }


    public void DeleteKey(KeyEvent keyEvent){
        if(keyEvent.getCode().equals(KeyCode.DELETE)){
            DeleteTheReservation();
        }
    }
}


class GetAllReservation extends Task{
    @Override
    protected ObservableList<Reservation> call() throws Exception {
        return FXCollections.observableArrayList
                (DataSource.getInstance().queryAllReservation());
    }

}

















