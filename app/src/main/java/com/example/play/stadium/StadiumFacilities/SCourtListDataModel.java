package com.example.play.stadium.StadiumFacilities;

public class SCourtListDataModel {

    String cId, gameType, courtType, courtLength, courtWidth, CourtAmount;

    public SCourtListDataModel(String cId, String gameType, String courtType, String courtLength, String courtWidth, String courtAmount) {
        this.cId = cId;
        this.gameType = gameType;
        this.courtType = courtType;
        this.courtLength = courtLength;
        this.courtWidth = courtWidth;
        CourtAmount = courtAmount;
    }

    public String getcId() {
        return cId;
    }

    public String getGameType() {
        return gameType;
    }

    public String getCourtType() {
        return courtType;
    }

    public String getCourtLength() {
        return courtLength;
    }

    public String getCourtWidth() {
        return courtWidth;
    }

    public String getCourtAmount() {
        return CourtAmount;
    }
}
