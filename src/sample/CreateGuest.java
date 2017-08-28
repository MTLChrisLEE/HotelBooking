package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
    @FXML
    private Button Ok;

    public void insertnewguest(){
        String newID = GuestID.getText();
        String newlastName = lastName.getText();
        String newfirstName = firstName.getText();
        String newphoneNumber = phoneNumber.getText();
        String newEmail = email.getText();

        Alert failtoinsertDB=new Alert(Alert.AlertType.ERROR);
        failtoinsertDB.setTitle("Error");
        failtoinsertDB.setHeaderText("The following ID already exists: "+ newID);

        try {
            DataSource.getInstance().insertGuest(newID, newlastName, newfirstName, newphoneNumber, newEmail);
        }catch (SQLException e){
            if(true){
                Ok.setOnAction(event -> failtoinsertDB.show());
                failtoinsertDB.showAndWait();
                setCancelButton();
            }
            System.out.println("Cannot add the guest info into the DB: " + e.getMessage());
        }
        setCancelButton();
    }


    public void setCancelButton(){
        Stage stage=(Stage)cancelButton.getScene().getWindow();
        stage.close();
    }

    public void PressEnterInsertGuest(KeyEvent keyEvent){
        if(keyEvent.getCode().equals(KeyCode.ENTER)){
            insertnewguest();
        }
    }


}
