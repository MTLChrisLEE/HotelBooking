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


}
