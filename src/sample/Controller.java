package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.DataModel.DataSource;
import sample.DataModel.rooms;

import java.io.IOException;
;

public class Controller {
    @FXML
    private TableView<rooms> roomsTableView;

    @FXML
    private BorderPane mainpane;

    @FXML
    Button checkReservation;

    public void listRooms(){
        Task<ObservableList<rooms>> task = new GetAllRooms();
        roomsTableView.itemsProperty().bind(task.valueProperty());

        new Thread(task).start();
    }

    public void listSearchedRooms(){
        Task<ObservableList<rooms>> task = new GetSearchedRooms();
        roomsTableView.itemsProperty().bind(task.valueProperty());

        new Thread(task).start();
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
    public void showReserve() {

        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ReserveDialogPane.fxml"));
            Parent root1 = (Parent)fxmlLoader.load();
            Stage stage = new Stage();
            stage.initStyle(StageStyle.DECORATED);
            stage.setTitle("Enter the guest's info");
            stage.setScene(new Scene(root1));
            stage.show();
        }catch(Exception e){
            System.out.println("Cannot open the dialog window: " + e.getMessage());
        }

    }

}


class GetAllRooms extends Task{
    @Override
    protected ObservableList<rooms> call() throws Exception {
        return FXCollections.observableArrayList
                (DataSource.getInstance().queryAllRooms());
    }

}


class GetSearchedRooms extends Task{
    @Override
    protected ObservableList<rooms> call() throws Exception {
        return FXCollections.observableArrayList
                (DataSource.getInstance().showSearchResult());
    }

}
