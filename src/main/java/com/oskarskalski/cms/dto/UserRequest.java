package com.oskarskalski.cms.dto;

public class UserRequest extends UserDto{
    private String oldPassword;
    private String newPassword;
    private String repeatNewPassword;

    public UserRequest(String oldPassword, String newPassword, String repeatNewPassword) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
        this.repeatNewPassword = repeatNewPassword;
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

    public String getRepeatNewPassword() {
        return repeatNewPassword;
    }

    public void setRepeatNewPassword(String repeatNewPassword) {
        this.repeatNewPassword = repeatNewPassword;
    }
}
