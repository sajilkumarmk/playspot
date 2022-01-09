package com.example.play.stadium.StadiumFacilities;

public class SFacilitiesListDataModel {

    String f_id,facility,opening_time,closing_time,description;

    public SFacilitiesListDataModel(String f_id, String facility, String opening_time, String closing_time, String description) {
        this.f_id = f_id;
        this.facility = facility;
        this.opening_time = opening_time;
        this.closing_time = closing_time;
        this.description = description;
    }

    public String getId() {
        return f_id;
    }

    public String getFacility() { return facility; }

    public String getOpening_time() {
        return opening_time;
    }

    public String getClosing_time() {
        return closing_time;
    }

    public String getDescription() {
        return description;
    }
}
