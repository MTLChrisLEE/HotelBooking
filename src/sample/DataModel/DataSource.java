package sample.DataModel;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chris on 8/16/2017.
 */
public class DataSource {

    public static final String DB_NAME = "hotelmtlchrislee";
    public static final String CONNECTION_STRING = "jdbc:mysql://localhost:3306/" + DB_NAME;
    public static final String TABLE_GUESTS = "Guests";
    public static final String TABLE_ROOMS = "rooms";
    public static final String TABLE_RESERVATION = "reservation";

    public static final String COLUMN_GUESTID = "GuestID";
    public static final String COLUMN_LASTNAME = "lastName";
    public static final String COLUMN_FIRSTNAME = "firstName";
    public static final String COLUMN_PHONE = "phoneNumber";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_TYPE = "Type";
    public static final String COLUMN_RATE = "rate";

    public static final String COLUMN_ROOM = "RoomNumber";
    public static final String COLUMN_CHECKIN = "checkinDate";
    public static final String COLUMN_CHECKOUT = "checkoutDate";

    public static final String QUERY_RESERVATION_BY_GUEST_ID_START =
            "SELECT " + TABLE_RESERVATION + "." + COLUMN_ROOM + ", " + TABLE_RESERVATION + "." + COLUMN_GUESTID + ", " +
                    TABLE_RESERVATION + "." + COLUMN_CHECKIN + ", " + TABLE_RESERVATION + "." + COLUMN_CHECKOUT + " FROM " + TABLE_RESERVATION +
                    " INNER JOIN " + TABLE_GUESTS + " ON " + TABLE_RESERVATION + "." + COLUMN_GUESTID + "=" + TABLE_GUESTS + "." + COLUMN_GUESTID +
                    " WHERE " + TABLE_GUESTS + "." + COLUMN_GUESTID + "=\'";

    public static final String QEURY_ROOMS = "SELECT * FROM " + TABLE_ROOMS;

    public static final String QUERY_RESERVATION = "SELECT * FROM " + TABLE_RESERVATION;


    public static final String QUERY_VACANCY = "SELECT r.* FROM " + TABLE_ROOMS + " r WHERE NOT EXISTS (SELECT 1 FROM " + TABLE_RESERVATION + " re WHERE " +
            "re." + COLUMN_ROOM + "=" + "r." + COLUMN_ROOM + " AND " + "((?>=re." + COLUMN_CHECKIN + " AND ?<re." + COLUMN_CHECKOUT
            + ") OR " + "(?<re." + COLUMN_CHECKOUT + " AND ?>=re." + COLUMN_CHECKIN + ")))";

    public static final String QUERY_INSERT_GUEST = "INSERT INTO "+  TABLE_GUESTS + " VALUES " + "(?,?,?,?,?)";
    public static final String QUERY_INSERT_RESERVATION = "INSERT INTO "+ TABLE_RESERVATION + " VALUES " + "(?,?,?,?)";


    private PreparedStatement queryVacancy;
    private PreparedStatement insertGuest;
    private PreparedStatement insertReservation;

    private Connection connection;

    private static DataSource instance = new DataSource();

    public static DataSource getInstance() {
        return instance;
    }

    public boolean open() {
        try {
            connection = DriverManager.getConnection(CONNECTION_STRING, "root", "");
            queryVacancy = connection.prepareStatement(QUERY_VACANCY);
            insertGuest = connection.prepareStatement(QUERY_INSERT_GUEST);
            insertReservation = connection.prepareStatement(QUERY_INSERT_RESERVATION);

            return true;
        } catch (SQLException e) {
            System.out.println("CANNOT Connect to the DB: " + e.getMessage());
            return false;
        }
    }

    public void close() {
        try {
            if(queryVacancy!=null){
                queryVacancy.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println("CANNOT Close the connection: " + e.getMessage());
        }
    }


    public List<Reservation> queryReservation(int sortOrder) {

        StringBuilder sb = new StringBuilder("SELECT * FROM ");
        sb.append(TABLE_RESERVATION);

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sb.toString())) {


            List<Reservation> reservations = new ArrayList<>();
            while (resultSet.next()) {
                Reservation reservation = new Reservation();
                reservation.setGuestID(resultSet.getString(COLUMN_GUESTID));
                reservation.setRoomNumber(resultSet.getInt(COLUMN_ROOM));
                reservation.setCheckinDate(resultSet.getDate(COLUMN_CHECKIN));
                reservation.setCheckoutDate((resultSet.getDate(COLUMN_CHECKOUT)));
                reservations.add(reservation);
            }

            return reservations;

        } catch (SQLException e) {
            System.out.println("QUERY FAILED: " + e.getMessage());
            return null;

        }

    }


    public List<Rooms> queryAllRooms() {
        StringBuilder sb = new StringBuilder(QEURY_ROOMS);
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sb.toString())) {
            List<Rooms> rooms = new ArrayList<>();
            while (resultSet.next()) {
                Rooms room = new Rooms();
                room.setRoomNumber(resultSet.getInt(COLUMN_ROOM));
                room.setType(resultSet.getString(COLUMN_TYPE));
                room.setRate(resultSet.getInt(COLUMN_RATE));
                rooms.add(room);
            }
            return rooms;
        } catch (SQLException e) {
            System.out.println("QUERY FAILED: " + e.getMessage());
            return null;
        }
    }


    public List<Reservation> queryAllReservation() {
        StringBuilder sb = new StringBuilder(QUERY_RESERVATION);
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sb.toString())) {
            List<Reservation> reservations = new ArrayList<>();
            while (resultSet.next()) {
                Reservation reservation = new Reservation();
                reservation.setGuestID(resultSet.getString(COLUMN_GUESTID));
                reservation.setRoomNumber(resultSet.getInt(COLUMN_ROOM));
                reservation.setCheckinDate(resultSet.getDate(COLUMN_CHECKIN));
                reservation.setCheckoutDate(resultSet.getDate(COLUMN_CHECKOUT));
                reservations.add(reservation);
            }
            return reservations;
        } catch (SQLException e) {
            System.out.println("QUERY FAILED: " + e.getMessage());
            return null;
        }
    }

    public List<Rooms> showSearchResult(Date date) {
        try {
            queryVacancy.setDate(1, date);
            queryVacancy.setDate(2, date);
            queryVacancy.setDate(3, date);
            queryVacancy.setDate(4, date);
            ResultSet resultSet = queryVacancy.executeQuery();

            List<Rooms> rooms = new ArrayList<>();
            while (resultSet.next()) {
                Rooms room = new Rooms();
                room.setRoomNumber(resultSet.getInt(COLUMN_ROOM));
                room.setType(resultSet.getString(COLUMN_TYPE));
                room.setRate(resultSet.getInt(COLUMN_RATE));

                rooms.add(room);
            }
            return rooms;
        } catch (SQLException e) {
            System.out.println("QUERY FAILED: " + e.getMessage());
            return null;
        }
    }


    public String insertGuest(String GuestID, String lastName, String firstName, String phoneNumber, String email) throws SQLException{


           insertGuest.setString(1, GuestID);
           insertGuest.setString(2, lastName);
           insertGuest.setString(3, firstName);
           insertGuest.setString(4, phoneNumber);
           insertGuest.setString(5, email);

           ResultSet resultSet = insertGuest.executeQuery();
           if (resultSet.next()) {
               return resultSet.getString(1);
           } else {
               insertGuest.setString(1, GuestID);
               insertGuest.setString(2, lastName);
               insertGuest.setString(3, firstName);
               insertGuest.setString(4, phoneNumber);
               insertGuest.setString(5, email);
               int updatedRows = insertGuest.executeUpdate();

               if (updatedRows != 1) {
                   throw new SQLException("Could Not add the guest");
               }

               ResultSet generatedkey = insertGuest.getGeneratedKeys();
               if (generatedkey.next()) {
                   return generatedkey.getString(1);
               } else {
                   throw new SQLException("Could Not get the GuestID");
               }

           }

    }


    ;

    public void insertReservation(String GuestID_, int RoomNumber, Date checkinDate, Date checkoutDate) throws SQLException{
        try {
            connection.setAutoCommit(false);

           
            insertReservation.setString(1, GuestID_);
            insertReservation.setInt(2, RoomNumber);
            insertReservation.setDate(3, checkinDate);
            insertReservation.setDate(4, checkoutDate);

            int updatedRows = insertGuest.executeUpdate();
            if (updatedRows == 1) {
                connection.commit();
            } else {
                throw new SQLException("Cannot insert the reservation");
            }
        }catch(SQLException e2) {
            System.out.println("Insert reservation exception: " + e2.getMessage());
            try {
                System.out.println("ROLLBACK");
                connection.rollback();
            } catch (SQLException e) {
                System.out.println("SOMETHING IS VERY VERY WRONG: " + e.getMessage());
            }
        }finally{
            try {
                System.out.println("Resetting drfault commit");
                connection.setAutoCommit(true);
            }catch(SQLException e){
                System.out.println("CANNOT RESET AUTOCOMMIT: " + e.getMessage());
            }
        }


    }

}






