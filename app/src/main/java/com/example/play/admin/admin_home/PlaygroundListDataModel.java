package com.example.play.admin.admin_home;

public class PlaygroundListDataModel {
    String id,name,type,district,place,state,idProof,phone,email,image;

    public PlaygroundListDataModel(String id, String name, String type, String district, String place,
                                   String state, String idProof,String phone,String email,String image) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.district = district;
        this.place = place;
        this.state = state;
        this.idProof = idProof;
        this.phone = phone;
        this.email = email;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() { return type; }

    public String getDistrict() {
        return district;
    }

    public String getPlace() {
        return place;
    }

    public String getState() { return state; }

    public String getIdProof() { return idProof; }

    public String getPhone() { return phone; }

    public String getEmail() { return email; }

    public String getImage() { return image; }
}
