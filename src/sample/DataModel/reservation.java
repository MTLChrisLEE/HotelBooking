package sample.DataModel;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.sql.Date;

/**
 * Created by Chris on 8/13/2017.
 */
public class reservation {
    private SimpleStringProperty GuestID = new SimpleStringProperty("");
    private SimpleIntegerProperty RoomNumber = new SimpleIntegerProperty();
    private Date checkinDate;
    private Date checkoutDate;

    public String getGuestID() {
        return GuestID.get();
    }

    public SimpleStringProperty guestIDProperty() {
        return GuestID;
    }

    public void setGuestID(String guestID) {
        this.GuestID.set(guestID);
    }

    public int getRoomNumber() {
        return RoomNumber.get();
    }

    public SimpleIntegerProperty roomNumberProperty() {
        return RoomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.RoomNumber.set(roomNumber);
    }

    public Date getCheckinDate() {
        return checkinDate;
    }

    public void setCheckinDate(Date checkinDate) {
        this.checkinDate = checkinDate;
    }

    public Date getCheckoutDate() {
        return checkoutDate;
    }

    public void setCheckoutDate(Date checkoutDate) {
        this.checkoutDate = checkoutDate;
    }
}
