package sample.DataModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chris on 8/16/2017.
 */
public class DataSource {

    private final String DB_NAME = "hotelmtlchrislee";
    private final String CONNECTION_STRING = "jdbc:mysql://localhost:3306/" + DB_NAME;
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

    private Connection connection;

    public boolean open(){
        try{
            connection = DriverManager.getConnection(CONNECTION_STRING,"root","");
            return  true;
        }catch(SQLException e){
            System.out.println("CANNOT Connect to the DB: "+ e.getMessage());
            return false;
        }
    }

    public void close(){
        try{
            if(connection !=null){
                connection.close();
            }
        }catch(SQLException e){
            System.out.println("CANNOT Close the connection: " + e.getMessage());
        }
    }

    public List<guests> queryGuests(){


        try(Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM " + TABLE_GUESTS)){


            List<guests> guests= new ArrayList<>();
            while(resultSet.next()){
                guests guest = new guests();
                guest.setGuestID(resultSet.getString(COLUMN_GUESTID));
                guest.setLastName(resultSet.getString(COLUMN_LASTNAME));
                guest.setFirstName(resultSet.getString(COLUMN_FIRSTNAME));
                guest.setPhoneNumber((resultSet.getString(COLUMN_PHONE)));
                guest.setEmail(resultSet.getString(COLUMN_EMAIL));
                guests.add(guest);
            }

            return guests;

        }catch(SQLException e){
            System.out.println("QUERY FAILED: " + e.getMessage());
            return null;

        }

    }
}
