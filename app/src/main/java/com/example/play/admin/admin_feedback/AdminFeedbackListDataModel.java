package com.example.play.admin.admin_feedback;

public class AdminFeedbackListDataModel {

    String id,userName,userImage,userMessage;

    public AdminFeedbackListDataModel(String id, String userMessage, String userName, String userImage) {
        this.id = id;
        this.userName = userName;
        this.userImage = userImage;
        this.userMessage = userMessage;
    }

    public String getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserImage() {
        return userImage;
    }

    public String getUserMessage() {
        return userMessage;
    }
}
