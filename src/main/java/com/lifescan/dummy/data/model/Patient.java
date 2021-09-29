package com.lifescan.dummy.data.model;

public class Patient {

    private String gender;
    private String password;
    private String firstName;
    private String emailAddress;
    private String dateOfBirth;
    private String diabetesType;
    private String lastName;

    public Patient(String gender, String password, String firstName, String emailAddress, String dateOfBirth, String diabetesType, String lastName) {
        this.gender = gender;
        this.password = password;
        this.firstName = firstName;
        this.emailAddress = emailAddress;
        this.dateOfBirth = dateOfBirth;
        this.diabetesType = diabetesType;
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getDiabetesType() {
        return diabetesType;
    }

    public void setDiabetesType(String diabetesType) {
        this.diabetesType = diabetesType;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
