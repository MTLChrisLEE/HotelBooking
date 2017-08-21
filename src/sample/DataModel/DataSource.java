package sample.DataModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chris on 8/16/2017.
 */
public class DataSource {

    public static final String DB_NAME = "hotelmtlchrislee";
    public static final String CONNECTION_STRING = "jdbc:mysql://localhost:3306/" + DB_NAME;
    public static final String TABLE_GUESTS = "guests";
    public static final String TABLE_ROOMS = "rooms";
    public static final String TABLE_RESERVATION = "reservation";

    public static final String COLUMN_GUESTID = "GuestID";
    public static final String COLUMN_LASTNAME = "lastName";
    public static final String COLUMN_FIRSTNAME = "firstName";
    public static final String COLUMN_PHONE = "phoneNumber";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_TYPE = "Type";
    public static final String COLUMN_RATE ="rate";

    public static final String COLUMN_ROOM = "RoomNumber";
    public static final String COLUMN_CHECKIN = "checkinDate";
    public static final String COLUMN_CHECKOUT = "checkoutDate";

    public static final String QUERY_RESERVATION_BY_GUEST_ID_START =
            "SELECT " + TABLE_RESERVATION + "." +COLUMN_ROOM+", "+ TABLE_RESERVATION + "." +COLUMN_GUESTID+", "+
                    TABLE_RESERVATION+"."+COLUMN_CHECKIN+", "+TABLE_RESERVATION+"."+COLUMN_CHECKOUT+ " FROM "+TABLE_RESERVATION+
                    " INNER JOIN "+ TABLE_GUESTS +" ON " + TABLE_RESERVATION + "."+COLUMN_GUESTID+"="+TABLE_GUESTS+"."+COLUMN_GUESTID+
                    " WHERE "+ TABLE_GUESTS+"."+COLUMN_GUESTID+"=\'";

    public static final String QEURY_ROOMS = "SELECT * FROM " + TABLE_ROOMS;



    public static final String QUERY_GUEST_BY_LASTNAME =
            "SELECT " + TABLE_GUESTS + " WHERE " + COLUMN_LASTNAME+"=\'";


    public static final int ORDER_BY_NONE = 1;
    public static final int ORDER_BY_ASC =2;
    public static final int ORDER_BY_DESC=3;

    private Connection connection;

    private static DataSource instance = new DataSource();

    public static DataSource getInstance(){
        return instance;
    }

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

    public List<reservation> queryReservation(int sortOrder){

        StringBuilder sb = new StringBuilder("SELECT * FROM ");
        sb.append(TABLE_RESERVATION);
        if(sortOrder!=ORDER_BY_NONE){
            sb.append(" ORDER BY ");
            sb.append(COLUMN_ROOM);
            if(sortOrder==ORDER_BY_DESC){
                sb.append(" DESC");
            }else{
                sb.append(" ASC");
            }
        }


        try(Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sb.toString())){


            List<reservation> reservations= new ArrayList<>();
            while(resultSet.next()){
                reservation reservation = new reservation();
                reservation.setGuestID(resultSet.getString(COLUMN_GUESTID));
                reservation.setRoomNumber(resultSet.getInt(COLUMN_ROOM));
                reservation.setCheckinDate(resultSet.getDate(COLUMN_CHECKIN));
                reservation.setCheckoutDate((resultSet.getDate(COLUMN_CHECKOUT)));
                reservations.add(reservation);
            }

            return reservations;

        }catch(SQLException e){
            System.out.println("QUERY FAILED: " + e.getMessage());
            return null;

        }

    }


    public List<rooms> queryAllRooms(){
        StringBuilder sb = new StringBuilder(QEURY_ROOMS);
        try(Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sb.toString())){


            List<rooms> rooms= new ArrayList<>();
            while(resultSet.next()){
                rooms room = new rooms();
                room.setRoomNumber(resultSet.getInt(COLUMN_ROOM));
                room.setType(resultSet.getString(COLUMN_TYPE));
                room.setRate(resultSet.getInt(COLUMN_RATE));

                rooms.add(room);
            }

            return rooms;

        }catch(SQLException e){
            System.out.println("QUERY FAILED: " + e.getMessage());
            return null;

        }


    }

    //SELECT reservation.RoomNumber, reservation.checkinDate, reservation.checkoutDate FROM reservation INNER JOIN guests ON reservation.GuestID=guests.GuestID WHERE guests.GuestID='MTLChrisLEE'
    public List<reservation> queryReservationbyGuestID(String guestID, int sortOrder){
        StringBuilder sb = new StringBuilder(QUERY_RESERVATION_BY_GUEST_ID_START);
        sb.append(guestID);
        sb.append("\'");

        if(sortOrder!=ORDER_BY_NONE){
            sb.append(" ORDER BY ");
            sb.append(COLUMN_CHECKIN);
            if(sortOrder==ORDER_BY_DESC){
                sb.append(" DESC");
            }else{
                sb.append(" ASC");
            }
        }

        //System.out.println(sb.toString());

        try(Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sb.toString())){


            List<reservation> reservations= new ArrayList<>();
            while(resultSet.next()){
                reservation reservation = new reservation();
                reservation.setGuestID(resultSet.getString(COLUMN_GUESTID));
                reservation.setRoomNumber(resultSet.getInt(COLUMN_ROOM));
                reservation.setCheckinDate(resultSet.getDate(COLUMN_CHECKIN));
                reservation.setCheckoutDate((resultSet.getDate(COLUMN_CHECKOUT)));
                reservations.add(reservation);
            }

            return reservations;

        }catch(SQLException e){
            System.out.println("QUERY FAILED: " + e.getMessage());
            return null;

        }
    }




}
