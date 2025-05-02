package com.mynexmy.nex.Models;

public class Selectaddress_Model {

    int C_mobile,C_pin;

    String C_name,C_email,C_address,C_locality,C_city,C_state;

    public Selectaddress_Model(int c_mobile, int c_pin, String c_name,
                               String c_email, String c_address, String c_locality,
                               String c_city, String c_state) {
        C_mobile = c_mobile;
        C_pin = c_pin;
        C_name = c_name;
        C_email = c_email;
        C_address = c_address;
        C_locality = c_locality;
        C_city = c_city;
        C_state = c_state;
    }

    public int getC_mobile() {
        return C_mobile;
    }

    public void setC_mobile(int c_mobile) {
        C_mobile = c_mobile;
    }

    public int getC_pin() {
        return C_pin;
    }

    public void setC_pin(int c_pin) {
        C_pin = c_pin;
    }

    public String getC_name() {
        return C_name;
    }

    public void setC_name(String c_name) {
        C_name = c_name;
    }

    public String getC_email() {
        return C_email;
    }

    public void setC_email(String c_email) {
        C_email = c_email;
    }

    public String getC_address() {
        return C_address;
    }

    public void setC_address(String c_address) {
        C_address = c_address;
    }

    public String getC_locality() {
        return C_locality;
    }

    public void setC_locality(String c_locality) {
        C_locality = c_locality;
    }

    public String getC_city() {
        return C_city;
    }

    public void setC_city(String c_city) {
        C_city = c_city;
    }

    public String getC_state() {
        return C_state;
    }

    public void setC_state(String c_state) {
        C_state = c_state;
    }
}
