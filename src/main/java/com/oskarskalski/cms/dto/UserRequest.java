package com.oskarskalski.cms.dto;

public class UserRequest extends UserDto{
    private String oldPassword;
    private String newPassword;

    public UserRequest(String firstName, String lastName, String email, String oldPassword, String newPassword) {
        super(firstName, lastName, email);
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    public UserRequest() {
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
