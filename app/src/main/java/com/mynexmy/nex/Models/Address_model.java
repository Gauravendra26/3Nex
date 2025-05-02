package com.mynexmy.nex.Models;

public class Address_model {


    String email,first_name,last_name,mobile,pincode,postal_code,created_at,updated_at,address_line,
            city,country,address_phone,state,referral_code ;

    int customer_id,is_verified,address_id,is_default;

    public Address_model(String email, String first_name, String last_name, String created_at, String updated_at,
                         String address_line, String city, String country, String state, String referral_code,
                         int customer_id, String mobile, String pincode, int is_verified, int address_id,
                         String postal_code,String address_phone,
                         int is_default)
    {
        this.email = email;
        this.first_name = first_name;
        this.last_name = last_name;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.address_line = address_line;
        this.city = city;
        this.country = country;
        this.state = state;
        this.referral_code = referral_code;
        this.customer_id = customer_id;
        this.mobile = mobile;
        this.pincode = pincode;
        this.is_verified = is_verified;
        this.address_id = address_id;
        this.postal_code = postal_code;
        this.is_default = is_default;
        this.address_phone = address_phone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getPostal_code() {
        return postal_code;
    }

    public void setPostal_code(String postal_code) {
        this.postal_code = postal_code;
    }

    public String getAddress_phone() {
        return address_phone;
    }

    public void setAddress_phone(String address_phone) {
        this.address_phone = address_phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getAddress_line() {
        return address_line;
    }

    public void setAddress_line(String address_line) {
        this.address_line = address_line;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getReferral_code() {
        return referral_code;
    }

    public void setReferral_code(String referral_code) {
        this.referral_code = referral_code;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }



    public int getIs_verified() {
        return is_verified;
    }

    public void setIs_verified(int is_verified) {
        this.is_verified = is_verified;
    }

    public int getAddress_id() {
        return address_id;
    }

    public void setAddress_id(int address_id) {
        this.address_id = address_id;
    }



    public int getIs_default() {
        return is_default;
    }

    public void setIs_default(int is_default) {
        this.is_default = is_default;
    }
}
