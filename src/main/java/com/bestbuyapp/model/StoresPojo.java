package com.bestbuyapp.model;

import java.util.HashMap;


public class StoresPojo {

    private String name;
    private String type;
    private String address;
    private String address2;
    private String city;
    private String state;
    private String zip;
    private int lat;
    private int lng;
    private String hours;
    private HashMap<String,Object> services;
    //Inside services, there is hashmap + hashmap. Do we need to consider this here?


    public String getName() {
        return name;
    }

    public void setName(String Name) {
        this.name = Name;
    }

    public String getType() {
        return type;
    }

    public void setType(String Type) {
        this.type = Type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String Address) {
        this.address = Address;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String Address2) {
        this.address2 = Address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String City) {
        this.city = City;
    }

    public String getState() {
        return state;
    }

    public void setState(String State) {
        this.state = State;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String Zip) {
        this.zip = Zip;
    }

    public int getLat() {
        return lat;
    }

    public void setLat(int Lat) {
        this.lat = Lat;
    }

    public int getLng() {
        return lng;
    }

    public void setLng(int Lng) {
        this.lng = Lng;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String Hours) {
        this.hours = Hours;
    }

    public HashMap<String, Object> getServices() {
        return services;
    }

    public void setServices(HashMap<String,Object>  Services) {
        this.services = Services;
    }
}
