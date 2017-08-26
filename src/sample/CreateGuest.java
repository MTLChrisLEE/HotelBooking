package sample;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sample.DataModel.DataSource;

import java.sql.SQLException;


/**
 * Created by Chris on 8/25/2017.
 */
public class CreateGuest {

    @FXML
    private TextField GuestID;
    @FXML
    private TextField lastName;
    @FXML
    private TextField firstName;
    @FXML
    private TextField phoneNumber;
    @FXML
    private TextField email;
    @FXML
    private Button cancelButton;

    public void insertnewguest(){
        String newID = GuestID.getText();
        String newlastName = lastName.getText();
        String newfirstName = firstName.getText();
        String newphoneNumber = phoneNumber.getText();
        String newEmail = email.getText();

        try {
            DataSource.getInstance().insertGuest(newID, newlastName, newfirstName, newphoneNumber, newEmail);
        }catch (SQLException e){
            System.out.println("Cannot add the guest info into the DB: " + e.getMessage());
        }

        setCancelButton();
    }


    public void setCancelButton(){
        Stage stage=(Stage)cancelButton.getScene().getWindow();
        stage.close();
    }




}
