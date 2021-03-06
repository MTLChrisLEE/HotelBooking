package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.DataModel.DataSource;
import sample.DataModel.Guests;
import sample.DataModel.Rooms;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class Controller {
    @FXML
    private TableView<Rooms> roomsTableView;

    @FXML
    private DatePicker checkindatepicker;

    @FXML
    Button checkReservation;

    @FXML
    private TableView<Guests> guestlists;

    @FXML
    private BorderPane mainpane;


    public void listRooms(){
        Task<ObservableList<Rooms>> task = new GetAllRooms();
        roomsTableView.itemsProperty().bind(task.valueProperty());

        new Thread(task).start();
    }

    public void listSearchedRooms(){
        try {
            Task<ObservableList<Rooms>> task =
                    new GetSearchedRooms(Date.valueOf(checkindatepicker.getValue()));
            roomsTableView.itemsProperty().bind(task.valueProperty());
            new Thread(task).start();
        }catch(NullPointerException e){
            System.out.println("");
        }
    }


    @FXML
    public void showReservation(ActionEvent event){
       try{
           FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CheckReservationDialog.fxml"));
           Parent root1 = (Parent)fxmlLoader.load();
           CheckReservationDialog reservationcontroller = fxmlLoader.getController();
           reservationcontroller.listReservation();
           Stage stage = new Stage();
           stage.initStyle(StageStyle.DECORATED);
           stage.setTitle("Reservation List");
           stage.setScene(new Scene(root1));
           stage.show();
       }catch(Exception e){
           System.out.println("Cannot Open the new Window: "+e.getMessage());
       }
    }


    @FXML
    public void addReservation() {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ReserveDialogPane.fxml"));
            Parent root1 = (Parent)fxmlLoader.load();
            Stage stage = new Stage();
            stage.initStyle(StageStyle.DECORATED);
            stage.setTitle("Reserve a room");
            stage.setScene(new Scene(root1));
            stage.show();
        }catch(Exception e){
            System.out.println("Cannot open the dialog window: " + e.getMessage());
        }
    }


    @FXML
    public void addGuest(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CreateGuest.fxml"));
            Parent root1 = (Parent)fxmlLoader.load();
            Stage stage = new Stage();
            stage.initStyle(StageStyle.DECORATED);
            stage.setTitle("Create a guest profile");
            stage.setScene(new Scene(root1));
            stage.show();
            guestlists.getItems();
            listGuests();
        }catch(Exception e){
            System.out.println("Cannot open the dialog window: " + e.getMessage());
        }
    }

    @FXML
    public void refreshtable(KeyEvent keyEvent){
        if(keyEvent.getCode().equals(KeyCode.F5)){
            listGuests();
        }
    }


    public void listGuests(){
        Task<ObservableList<Guests>> task = new GetAllGuests();
        guestlists.itemsProperty().bind(task.valueProperty());
        new Thread(task).start();
    }


    public void PressEnterSearch(KeyEvent keyEvent){
        if(keyEvent.getCode().equals(KeyCode.ENTER)){
            listSearchedRooms();
        }
    }
}


class GetAllRooms extends Task{
    @Override
    protected ObservableList<Rooms> call() throws Exception {
        return FXCollections.observableArrayList
                (DataSource.getInstance().queryAllRooms());
    }
}


class GetSearchedRooms extends Task{

    private final Date date ;

    public GetSearchedRooms(Date date) throws NullPointerException{
        this.date = date;
    }

    @Override
    protected ObservableList<Rooms> call() throws Exception {
        return FXCollections.observableArrayList
                (DataSource.getInstance().showSearchResult(date));
    }

}
class GetAllGuests extends Task{
    @Override
    protected ObservableList<Guests> call() throws Exception {
        return FXCollections.observableArrayList
                (DataSource.getInstance().queryAllGuests());
    }
}



