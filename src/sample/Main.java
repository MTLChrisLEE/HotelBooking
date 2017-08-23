package sample;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import sample.DataModel.DataSource;
import sample.DataModel.guests;
import sample.DataModel.reservation;

import javax.print.DocFlavor;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Scanner;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));
        Parent root = loader.load();
        Controller controller = loader.getController();
        controller.listRooms();


        primaryStage.setTitle("Hotel MTLCHRISLEE");
        primaryStage.setScene(new Scene(root, 1000, 800));
        primaryStage.show();
    }


    @Override
    public void init() throws Exception {
        DataSource.getInstance().open();
    }

    @Override
    public void stop() throws Exception {
        DataSource.getInstance().close();
    }




    public static void main(String[] args) {

        launch(args);
    }



}
