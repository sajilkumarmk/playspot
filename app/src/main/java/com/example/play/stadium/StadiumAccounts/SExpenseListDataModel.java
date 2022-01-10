package com.example.play.stadium.StadiumAccounts;

public class SExpenseListDataModel {
    String e_id, type, date,amount, description, bill;

    public SExpenseListDataModel(String e_id, String type, String date, String amount, String description, String bill) {
        this.e_id = e_id;
        this.type = type;
        this.date = date;
        this.amount = amount;
        this.description = description;
        this.bill = bill;
    }

    public String getE_id() {
        return e_id;
    }

    public String getType() {
        return type;
    }

    public String getDate() {
        return date;
    }

    public String getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public String getBill() {
        return bill;
    }
}
