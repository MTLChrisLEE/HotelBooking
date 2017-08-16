package sample.DataModel;

import javafx.beans.property.SimpleIntegerProperty;

/**
 * Created by Chris on 8/13/2017.
 */
public class rooms {
    private SimpleIntegerProperty RoomNumber = new SimpleIntegerProperty();
    private SimpleIntegerProperty rate = new SimpleIntegerProperty();

    public int getRoomNumber() {
        return RoomNumber.get();
    }

    public SimpleIntegerProperty roomNumberProperty() {
        return RoomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.RoomNumber.set(roomNumber);
    }

    public int getRate() {
        return rate.get();
    }

    public SimpleIntegerProperty rateProperty() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate.set(rate);
    }
}
