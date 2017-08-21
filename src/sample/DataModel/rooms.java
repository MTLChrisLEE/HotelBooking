package sample.DataModel;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Created by Chris on 8/13/2017.
 */
public class rooms {
    private SimpleIntegerProperty RoomNumber = new SimpleIntegerProperty();
    private SimpleStringProperty Type = new SimpleStringProperty();
    private SimpleIntegerProperty rate = new SimpleIntegerProperty();

    public int getRoomNumber() {
        return RoomNumber.get();
    }

    public void setRoomNumber(int roomNumber) {
        this.RoomNumber.set(roomNumber);
    }

    public String getType() {
        return Type.get();
    }

    public void setType(String Type) {
        this.Type.set(Type);
    }

    public int getRate() {
        return rate.get();
    }

    public void setRate(int rate) {
        this.rate.set(rate);
    }


//    public SimpleIntegerProperty roomNumberProperty() {
//        return RoomNumber;
//    }
//
//    public SimpleStringProperty TypeProperty() {
//        return Type;
//    }
//
//    public SimpleIntegerProperty rateProperty() {
//        return rate;
//    }


}
