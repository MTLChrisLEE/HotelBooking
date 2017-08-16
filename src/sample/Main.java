package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.DataModel.DataSource;
import sample.DataModel.guests;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
        primaryStage.setTitle("Hotel MTLCHRISLEE");
        primaryStage.setScene(new Scene(root, 1000, 800));
        primaryStage.show();
    }


    @Override
    public void init() throws Exception {
//        try{
//            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotelmtlchrislee","root","");
//            Statement statement = connection.createStatement();
//            statement.execute("INSERT INTO guests VALUES ('HELLO','WORLD','JAVA','123456789','JDBC@gmail.com')");
//        }catch(SQLException e){
//            e.getMessage();
//        }
    }

    @Override
    public void stop() throws Exception {
        super.stop();
    }



    public static void main(String[] args) {
        DataSource dataSource = new DataSource();
        if(!dataSource.open()){
            System.out.println("CANNOT open datasource");
            return;
        }

        List<guests> guests = dataSource.queryGuests();
        if(guests==null){
            System.out.println("NO GUESTS");
            return;
        }

        for(guests Guest: guests){
            System.out.println("ID: "+ Guest.getGuestID()+ ", First Name: " + Guest.getFirstName() +
                                ", Last Name: " + Guest.getLastName() + ", Email: " + Guest.getEmail() +
                                ", Phone Number: " + Guest.getPhoneNumber());
        }

        dataSource.close();
        launch(args);
    }
}
