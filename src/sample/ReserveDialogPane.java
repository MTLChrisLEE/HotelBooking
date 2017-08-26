package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.DataModel.DataSource;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 * Created by Chris on 8/21/2017.
 */
public class ReserveDialogPane {

        @FXML
        private TextField GuestID;
        @FXML
        private TextField roomnumber;
        @FXML
        private DatePicker checkindate;
        @FXML
        private DatePicker checkoutdate;
        @FXML
        private Button Guests;
        @FXML
        private Button cancelButton;


        public void setCancelButton(){
                Stage stage=(Stage)cancelButton.getScene().getWindow();
                stage.close();
        }

        public void insertnewreservation(){
                String newID = GuestID.getText();
                int RoomNumber = Integer.parseInt(roomnumber.getText());
                Date arrivaldate = Date.valueOf(checkindate.getValue());
                Date leavedate = Date.valueOf(checkoutdate.getValue());
                try {
                        DataSource.getInstance().insertReservation(newID, RoomNumber, arrivaldate, leavedate);
                }catch (SQLException e){
                        System.out.println("Cannot add the reservation into the DB: " + e.getMessage());
                }

                setCancelButton();
        }

        public void showAllguests(ActionEvent event){
                try{
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ListGuests.fxml"));
                        Parent root1 = (Parent)fxmlLoader.load();

                        ListGuests guestscontroller = fxmlLoader.getController();
                        guestscontroller.listGuests();

                        Stage stage = new Stage();
                        stage.initStyle(StageStyle.DECORATED);
                        stage.setTitle("Guest List");
                        stage.setScene(new Scene(root1));
                        stage.show();
                }catch(Exception e){
                        System.out.println("Cannot Open the new Window: "+e.getMessage());
                }
        }






}
