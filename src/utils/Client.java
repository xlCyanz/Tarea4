package utils;

import org.bson.Document;

import javafx.beans.property.SimpleStringProperty;

public class Client {
    private SimpleStringProperty firstName;
    private SimpleStringProperty lastName;
    private SimpleStringProperty userName;
    private SimpleStringProperty email;
    private SimpleStringProperty phoneNumber;

    public Client(String firstname, String lastname, String username, String email, String phone_number) {
        this.firstName = new SimpleStringProperty(firstname);
        this.lastName = new SimpleStringProperty(lastname);
        this.userName = new SimpleStringProperty(username);
        this.email = new SimpleStringProperty(email);
        this.phoneNumber = new SimpleStringProperty(phone_number);
    }

    public Client(Document user) {
        
    }

    public String getFirstName() {
        return firstName.get();
    }

    public void setFirstName(String nstr) {
        firstName.set(nstr);
    }


    public String getLastName() {
        return lastName.get();
    }

    public void setLastName(String nstr) {
        lastName.set(nstr);
    }


    public String getUserName() {
        return userName.get();
    }

    public void setUserName(String nstr) {
        userName.set(nstr);
    }


    public String getEmail() {
        return email.get();
    }

    public void setEmail(String nstr) {
        email.set(nstr);
    }


    public String getPhoneNumber() {
        return phoneNumber.get();
    }

    public void setPhoneNumber(String nstr) {
        phoneNumber.set(nstr);
    }

}
