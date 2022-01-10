package com.example.play.stadium.StadiumBooking;

public class SCurrentBookingListDataModel {

    String b_id,user_id,booking_date,booking_time,payment_type,gametype,courttype,bookusername,bookuserimage,bookuserphone;

    public SCurrentBookingListDataModel(String b_id, String user_id, String booking_date, String booking_time, String payment_type, String gametype, String courttype, String bookusername, String bookuserimage, String bookuserphone) {
        this.b_id = b_id;
        this.user_id = user_id;
        this.booking_date = booking_date;
        this.booking_time = booking_time;
        this.payment_type = payment_type;
        this.gametype = gametype;
        this.courttype = courttype;
        this.bookusername = bookusername;
        this.bookuserimage = bookuserimage;
        this.bookuserphone = bookuserphone;
    }

    public String getB_id() {
        return b_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getBooking_date() {
        return booking_date;
    }

    public String getBooking_time() {
        return booking_time;
    }

    public String getPayment_type() {
        return payment_type;
    }

    public String getGametype() {
        return gametype;
    }

    public String getCourttype() {
        return courttype;
    }

    public String getBookusername() {
        return bookusername;
    }

    public String getBookuserimage() {
        return bookuserimage;
    }

    public String getBookuserphone() {
        return bookuserphone;
    }
}
