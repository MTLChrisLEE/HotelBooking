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
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.DataModel.DataSource;
import sample.DataModel.rooms;

public class Controller {
    @FXML
    private TableView<rooms> roomsTableView;

    @FXML
    Button checkReservation;

    public void listRooms(){
        Task<ObservableList<rooms>> task = new GetAllRooms();
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

}


class GetAllRooms extends Task{
    @Override
    protected ObservableList<rooms> call() throws Exception {
        return FXCollections.observableArrayList
                (DataSource.getInstance().queryAllRooms());
    }

}



