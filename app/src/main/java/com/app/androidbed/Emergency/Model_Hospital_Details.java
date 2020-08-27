package com.app.androidbed.Emergency;

public class Model_Hospital_Details {
    private String key;
    private String hospital_name;
    private String hospital_number;
    private String address;

    public Model_Hospital_Details(String key, String hospital_name, String hospital_number, String address) {
        this.key = key;
        this.hospital_name = hospital_name;
        this.hospital_number = hospital_number;
        this.address = address;
    }

    public Model_Hospital_Details() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getHospital_name() {
        return hospital_name;
    }

    public void setHospital_name(String hospital_name) {
        this.hospital_name = hospital_name;
    }

    public String getHospital_number() {
        return hospital_number;
    }

    public void setHospital_number(String hospital_number) {
        this.hospital_number = hospital_number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
