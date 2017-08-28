package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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
        @FXML
        private Button Ok;

        public void setCancelButton(){
                Stage stage=(Stage)cancelButton.getScene().getWindow();
                stage.close();
        }

        public void insertnewreservation(){
                Alert emptyID=new Alert(Alert.AlertType.ERROR);
                emptyID.setTitle("Error");
                emptyID.setHeaderText("No empty guestID");

                Alert emptyRoomNumber = new Alert(Alert.AlertType.ERROR);
                emptyRoomNumber.setTitle("Error");
                emptyRoomNumber.setHeaderText("No empty room number");

                Alert checkinLaterThanchecout = new Alert(Alert.AlertType.ERROR);
                checkinLaterThanchecout.setTitle("ERROR");
                checkinLaterThanchecout.setHeaderText("CheckIn Date is Later than Checkout, Undo the reservation");

                if(GuestID.getText().isEmpty()){
                        Ok.setOnAction(event -> emptyID.show());
                        emptyID.showAndWait();
                        setCancelButton();
                        return;
                }

                if(roomnumber.getText().isEmpty()){
                        Ok.setOnAction(event -> emptyRoomNumber.show());
                        emptyRoomNumber.showAndWait();
                        setCancelButton();
                        return;
                }

                if(checkindate.getValue().isAfter(checkoutdate.getValue())){
                        Ok.setOnAction(event -> checkinLaterThanchecout.show());
                        checkinLaterThanchecout.showAndWait();
                        setCancelButton();
                        return;
                }

                String newID = GuestID.getText();
                int RoomNumber = Integer.parseInt(roomnumber.getText());
                Date arrivaldate = Date.valueOf(checkindate.getValue());
                Date leavedate = Date.valueOf(checkoutdate.getValue());

                Alert failtoinsertDB=new Alert(Alert.AlertType.ERROR);
                failtoinsertDB.setTitle("Error");
                failtoinsertDB.setHeaderText("There is no guest with ID: "+ newID + "\nOR\n" + "There is no room such as: "+RoomNumber);



                Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
                confirmation.setTitle("CONFIRMATION");
                confirmation.setHeaderText("Reservation Info:");
                confirmation.setContentText("GuestID: "+newID+"\n"+"RoomNumber: "+RoomNumber+"\n"+"Check-In Date: "+arrivaldate.toString()+"\n"+"Check-Out Date: "+leavedate.toString());

                try{
                        if(true){
                                Ok.setOnAction(event -> confirmation.show());
                                confirmation.showAndWait();
                        }
                        DataSource.getInstance().insertReservation(newID, RoomNumber, arrivaldate, leavedate);
                }catch (SQLException e){
                        if(true){
                                Ok.setOnAction(event -> failtoinsertDB.show());
                                failtoinsertDB.showAndWait();
                                setCancelButton();
                        }
                        System.out.println("Cannot add the reservation into the DB: " + e.getMessage());
                }catch (NumberFormatException e){
                        System.out.println("Cannot and the reservation into the DB: "+ e.getMessage());
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
