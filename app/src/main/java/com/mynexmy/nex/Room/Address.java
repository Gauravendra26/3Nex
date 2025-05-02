package com.mynexmy.nex.Room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Address {

    @PrimaryKey(autoGenerate = true)
    public int cid;

    @ColumnInfo(name = "c_mobile")
    public int c_mobile;


    @ColumnInfo(name = "c_pin")
    public int c_pin;


    @ColumnInfo(name = "c_name")
    public String c_name;

    @ColumnInfo(name = "c_email")
    public String c_email;

    @ColumnInfo(name = "c_address")
    public String c_address;

    @ColumnInfo(name = "c_locality")
    public String c_locality;

    @ColumnInfo(name = "c_city")
    public String c_city;

    @ColumnInfo(name = "c_state")
    public String c_state;

    public Address(int cid, int c_mobile, int c_pin, String c_name, String c_email, String c_address,
                   String c_locality, String c_city, String c_state) {
        this.cid = cid;
        this.setC_mobile(c_mobile);
        this.setC_pin(c_pin);
        this.c_name = c_name;
        this.c_email = c_email;
        this.c_address = c_address;
        this.c_locality = c_locality;
        this.c_city = c_city;
        this.c_state = c_state;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public int getC_mobile() {
        return c_mobile;
    }

    public int getC_pin() {
        return c_pin;
    }

    public String getC_name() {
        return c_name;
    }

    public void setC_name(String c_name) {
        this.c_name = c_name;
    }

    public String getC_email() {
        return c_email;
    }

    public void setC_email(String c_email) {
        this.c_email = c_email;
    }

    public String getC_address() {
        return c_address;
    }

    public void setC_address(String c_address) {
        this.c_address = c_address;
    }

    public String getC_locality() {
        return c_locality;
    }

    public void setC_locality(String c_locality) {
        this.c_locality = c_locality;
    }

    public String getC_city() {
        return c_city;
    }

    public void setC_city(String c_city) {
        this.c_city = c_city;
    }

    public String getC_state() {
        return c_state;
    }

    public void setC_state(String c_state) {
        this.c_state = c_state;
    }

    public void setC_mobile(int c_mobile) {
        this.c_mobile = c_mobile;
    }

    public void setC_pin(int c_pin) {
        this.c_pin = c_pin;
    }
}
