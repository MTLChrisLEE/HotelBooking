package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
//        DataSource dataSource = new DataSource();
//        if(!dataSource.open()){
//            System.out.println("CANNOT open datasource");
//            return;
//        }
//
//        List<guests> guests = dataSource.queryGuests();
//        if(guests==null){
//            System.out.println("NO GUESTS");
//            return;
//        }
//
//        for(guests Guest: guests){
//            System.out.println("ID: "+ Guest.getGuestID()+ ", First Name: " + Guest.getFirstName() +
//                                ", Last Name: " + Guest.getLastName() + ", Email: " + Guest.getEmail() +
//                                ", Phone Number: " + Guest.getPhoneNumber());
//        }
//
//        List<reservation> reservations = dataSource.queryReservation(3);
//        if(reservations==null){
//            System.out.println("NO RESERVATION!");
//            return;
//        }
//
//        for(reservation Reservation: reservations) {
//            System.out.println("GuestID: " + Reservation.getGuestID() + ", Room No: " + Reservation.getRoomNumber() +
//                    ", CheckIn Date: " + Reservation.getCheckinDate() + ", CheckOut Date: " + Reservation.getCheckoutDate());
//        }
//
//
//
//        List<reservation> reservationsguestid = dataSource.queryReservationbyGuestID("JDBC",1);
//
//        for(reservation reservation:reservationsguestid){
//            System.out.println("Room Number: "+reservation.getRoomNumber()+ "   GuestID: "+reservation.getGuestID()+"   CheckInDate: "+reservation.getCheckinDate()+"   CheckoutDate: "+reservation.getCheckoutDate());
//        }
//
//
//
//        dataSource.close();
        launch(args);
    }



}
