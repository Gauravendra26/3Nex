package com.mynexmy.nex.Models;


public class ProductModel {
    String p_image;
    String p_name;
    String p_description;
    String p_saleprice;
    String p_actualprice;
    String p_quantity;
    String p_id;

    public ProductModel(String p_image, String p_name, String p_description, String p_saleprice, String p_actualprice, String p_quantity, String p_id) {
        this.p_image = p_image;
        this.p_name = p_name;
        this.p_description = p_description;
        this.p_saleprice = p_saleprice;
        this.p_actualprice = p_actualprice;
        this.p_quantity = p_quantity;
        this.p_id = p_id;

    }

    public String getP_image() {
        return p_image;
    }

    public void setP_image(String p_image) {
        this.p_image = p_image;
    }

    public String getP_name() {
        return p_name;
    }

    public void setP_name(String p_name) {
        this.p_name = p_name;
    }

    public String getP_description() {
        return p_description;
    }

    public void setP_description(String p_description) {
        this.p_description = p_description;
    }

    public String getP_saleprice() {
        return p_saleprice;
    }

    public void setP_saleprice(String p_saleprice) {
        this.p_saleprice = p_saleprice;
    }

    public String getP_actualprice() {
        return p_actualprice;
    }

    public void setP_actualprice(String p_actualprice) {
        this.p_actualprice = p_actualprice;
    }

    public String getP_quantity() {
        return p_quantity;
    }

    public void setP_quantity(String p_quantity) {
        this.p_quantity = p_quantity;
    }

    public String getP_id() {
        return p_id;
    }

    public void setP_id(String p_id) {
        this.p_id = p_id;
    }
}