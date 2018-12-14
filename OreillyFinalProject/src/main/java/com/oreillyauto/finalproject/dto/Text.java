package com.oreillyauto.finalproject.dto;

public class Text {

    private String phoneNumber;
    private String textInformation;
    private boolean error;
    private String errorMessage;

    public Text() {
    
    }
    
    public Text(String phoneNumber, String textInformation) {
        this.phoneNumber = phoneNumber;
        this.textInformation = textInformation;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getTextInformation() {
        return textInformation;
    }

    public void setTextInformation(String textInformation) {
        this.textInformation = textInformation;
    }
    
    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
