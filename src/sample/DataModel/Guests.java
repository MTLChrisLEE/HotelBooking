package sample.DataModel;

import javafx.beans.property.SimpleStringProperty;

/**
 * Created by Chris on 8/13/2017.
 */
public class Guests {
    private SimpleStringProperty GuestID = new SimpleStringProperty("");
    private SimpleStringProperty lastName = new SimpleStringProperty("");
    private SimpleStringProperty firstName = new SimpleStringProperty("");
    private SimpleStringProperty phoneNumber = new SimpleStringProperty("");
    private SimpleStringProperty email = new SimpleStringProperty("");

    public String getGuestID() {
        return GuestID.get();
    }

    public SimpleStringProperty guestIDProperty() {
        return GuestID;
    }

    public void setGuestID(String guestID) {
        this.GuestID.set(guestID);
    }

    public String getLastName() {
        return lastName.get();
    }

    public SimpleStringProperty lastNameProperty() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public String getFirstName() {
        return firstName.get();
    }

    public SimpleStringProperty firstNameProperty() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public String getPhoneNumber() {
        return phoneNumber.get();
    }

    public SimpleStringProperty phoneNumberProperty() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber.set(phoneNumber);
    }

    public String getEmail() {
        return email.get();
    }

    public SimpleStringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }
}
