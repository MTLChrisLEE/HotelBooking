package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class Main extends Application {

    private final String TABLE_GUESTS = "guests";
    private final String TABLE_ROOMS = "rooms";
    private final String TABLE_RESERVATION = "reservation";

    private final String COLUMN_GUESTID = "GuestID";
    private final String COLUMN_LASTNAME = "lastName";
    private final String COLUMN_FIRSTNAME = "firstName";
    private final String COLUMN_PHONE = "phoneNumber";
    private final String COLUMN_EMAIL = "email";

    private final String COLUMN_ROOM = "RoomNumber";
    private final String COLUMN_CHECKIN = "checkinDate";
    private final String COLUMN_CHECKOUT = "checkoutDate";



    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
        primaryStage.setTitle("Hotel MTLCHRISLEE");
        primaryStage.setScene(new Scene(root, 1000, 800));
        primaryStage.show();
    }


    @Override
    public void init() throws Exception {
        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotelmtlchrislee","root","");
            Statement statement = connection.createStatement();
            statement.execute("INSERT INTO guests VALUES ('HELLO','WORLD','JAVA','123456789','JDBC@gmail.com')");
        }catch(SQLException e){
            e.getMessage();
        }
    }

    @Override
    public void stop() throws Exception {
        super.stop();
    }



    public static void main(String[] args) {
        launch(args);
    }
}
