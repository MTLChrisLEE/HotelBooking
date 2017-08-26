package sample.DataModel;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
    public static final String COLUMN_RATE = "rate";

    public static final String COLUMN_ROOM = "RoomNumber";
    public static final String COLUMN_CHECKIN = "checkinDate";
    public static final String COLUMN_CHECKOUT = "checkoutDate";

    public static final String QUERY_RESERVATION_BY_GUEST_ID_START =
            "SELECT " + TABLE_RESERVATION + "." + COLUMN_ROOM + ", " + TABLE_RESERVATION + "." + COLUMN_GUESTID + ", " +
                    TABLE_RESERVATION + "." + COLUMN_CHECKIN + ", " + TABLE_RESERVATION + "." + COLUMN_CHECKOUT + " FROM " + TABLE_RESERVATION +
                    " INNER JOIN " + TABLE_GUESTS + " ON " + TABLE_RESERVATION + "." + COLUMN_GUESTID + "=" + TABLE_GUESTS + "." + COLUMN_GUESTID +
                    " WHERE " + TABLE_GUESTS + "." + COLUMN_GUESTID + "=\'";


    //To print all rooms
    public static final String QEURY_ALLROOMS = "SELECT * FROM " + TABLE_ROOMS;

    //To print all reservation
    public static final String QUERY_RESERVATION = "SELECT * FROM " + TABLE_RESERVATION + " ORDER BY "+COLUMN_CHECKIN ;

    //To print all the guests
    public static final String QUERY_GUESTS = "SELECT * FROM " + TABLE_GUESTS;


    //To print all available rooms on the chosen date
    public static final String QUERY_VACANCY = "SELECT r.* FROM " + TABLE_ROOMS + " r WHERE NOT EXISTS (SELECT 1 FROM " + TABLE_RESERVATION + " re WHERE " +
            "re." + COLUMN_ROOM + "=" + "r." + COLUMN_ROOM + " AND " + "((?>=re." + COLUMN_CHECKIN + " AND ?<re." + COLUMN_CHECKOUT
            + ") OR " + "(?<re." + COLUMN_CHECKOUT + " AND ?>=re." + COLUMN_CHECKIN + ")))";


    //To insert a new guest's info
    public static final String QUERY_INSERT_GUEST = "INSERT INTO " + TABLE_GUESTS + "(" + COLUMN_GUESTID + ", " + COLUMN_LASTNAME + ", " + COLUMN_FIRSTNAME + ", " +
            COLUMN_PHONE + ", " + COLUMN_EMAIL + ") VALUES " + "(?,?,?,?,?)";


    //To reserve a room
    public static final String QUERY_INSERT_RESERVATION = "INSERT INTO " + TABLE_RESERVATION + " VALUES " + "(?,?,?,?)";


    //To save a new guest, it is mandatory to check if the GuestID is repeated
    public static final String QUERY_GUEST = "SELECT " + COLUMN_GUESTID + " FROM " + TABLE_GUESTS + " WHERE " + COLUMN_GUESTID + "=?";



    //To delete a reservation, it is mandatory to click a row in the reservation list
    public static final String QUERY_DELETE_RESERVATION = "DELETE FROM "+TABLE_RESERVATION+ " WHERE ("+COLUMN_GUESTID+"=? AND "+
                                                    COLUMN_ROOM+"=?)";



    private PreparedStatement queryVacancy;
    private PreparedStatement queryGuestID;
    private PreparedStatement queryReservation;
    private PreparedStatement insertINTOGuest;
    private PreparedStatement insertINTOReservation;
    private PreparedStatement deleteReservation;

    private Connection connection;

    private static DataSource instance = new DataSource();

    public static DataSource getInstance() {
        return instance;
    }

    public boolean open() {
        try {
            connection = DriverManager.getConnection(CONNECTION_STRING, "root", "");
            queryVacancy = connection.prepareStatement(QUERY_VACANCY);
            queryGuestID = connection.prepareStatement(QUERY_GUEST);
            queryReservation = connection.prepareStatement(QUERY_RESERVATION);
            insertINTOGuest = connection.prepareStatement(QUERY_INSERT_GUEST);
            insertINTOReservation = connection.prepareStatement(QUERY_INSERT_RESERVATION);
            deleteReservation = connection.prepareStatement(QUERY_DELETE_RESERVATION);

            return true;
        } catch (SQLException e) {
            System.out.println("CANNOT Connect to the DB: " + e.getMessage());
            return false;
        }
    }

    public void close() {
        try {
            if (queryVacancy != null) {
                queryVacancy.close();
            }

            if (insertINTOGuest != null) {
                insertINTOGuest.close();
            }

            if(insertINTOReservation!=null){
                insertINTOReservation.close();
            }

            if(deleteReservation!=null){
                deleteReservation.close();
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
        StringBuilder sb = new StringBuilder(QEURY_ALLROOMS);
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
            System.out.println("QUERY FAILED from queryAllRooms: " + e.getMessage());
            return null;
        }
    }

    public List<Guests> queryAllGuests() {
        StringBuilder sb = new StringBuilder(QUERY_GUESTS);
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sb.toString())) {
            List<Guests> Guests = new ArrayList<>();
            while (resultSet.next()) {
                Guests guest = new Guests();
                guest.setGuestID(resultSet.getString(COLUMN_GUESTID));
                guest.setLastName(resultSet.getString(COLUMN_LASTNAME));
                guest.setFirstName((resultSet.getString(COLUMN_FIRSTNAME)));
                guest.setPhoneNumber(resultSet.getString(COLUMN_PHONE));
                guest.setEmail(resultSet.getString(COLUMN_EMAIL));
                Guests.add(guest);
            }
            return Guests;
        } catch (SQLException e) {
            System.out.println("QUERY FAILED from queryAllGuests: " + e.getMessage());
            return null;
        }
    }


    public List<Reservation> queryAllReservation() {
        StringBuilder sb = new StringBuilder(QUERY_RESERVATION);
        System.out.println(sb.toString());
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
            System.out.println("QUERY FAILED from queryAllReservation: " + e.getMessage());
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
            System.out.println("QUERY FAILED from showSearchResult: " + e.getMessage());
            return null;
        }
    }

    public String insertGuest(String GuestID, String lastName, String firstName, String phoneNumber, String email) throws SQLException {

        queryGuestID.setString(1, GuestID);
        ResultSet resultSet = queryGuestID.executeQuery();

        if (resultSet.next()) {
            //if the GuestID already exists, return
            return resultSet.getString(1);
        } else {
            //insert the guest's info
            insertINTOGuest.setString(1, GuestID);
            insertINTOGuest.setString(2, lastName);
            insertINTOGuest.setString(3, firstName);
            insertINTOGuest.setString(4, phoneNumber);
            insertINTOGuest.setString(5, email);

            //How many rows are affected(updated)?
            int updatedRows = insertINTOGuest.executeUpdate();

            if (updatedRows != 1) {
                throw new SQLException("Could not add the guest");
            }

            return GuestID;
        }
    }


    public void insertReservation(String GuestID_, int RoomNumber, Date checkinDate, Date checkoutDate) throws SQLException{
            insertINTOReservation.setString(1, GuestID_);
            insertINTOReservation.setInt(2,RoomNumber);
            insertINTOReservation.setDate(3,checkinDate);
            insertINTOReservation.setDate(4,checkoutDate);

            int updatedRows = insertINTOReservation.executeUpdate();

            if(updatedRows!=1){
                throw new SQLException("Could not add the reserevation");
            }
    }

    public void deleteFomReservation(String GuestID, int RoomNumber) throws SQLException{
            deleteReservation.setString(1,GuestID);
            deleteReservation.setInt(2,RoomNumber);

            int updateRows=deleteReservation.executeUpdate();

            if(updateRows!=1){
                throw new SQLException("SOMETHING WENT WRONG!!!!!!What did you delete?");
            }
    }


}






