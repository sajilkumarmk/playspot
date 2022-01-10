package com.example.play.stadium.StadumAccounts;

public class SPaymentListDataModel {
    String p_id, type, amount, username, userimage;

    public SPaymentListDataModel(String p_id, String type, String amount, String username, String userimage) {
        this.p_id = p_id;
        this.type = type;
        this.amount = amount;
        this.username = username;
        this.userimage = userimage;
    }

    public String getP_id() {
        return p_id;
    }

    public String getType() {
        return type;
    }

    public String getAmount() {
        return amount;
    }

    public String getUsername() {
        return username;
    }

    public String getUserimage() {
        return userimage;
    }
}
