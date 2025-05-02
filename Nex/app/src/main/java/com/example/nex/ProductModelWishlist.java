package com.example.nex;

public class ProductModelWishlist {

    String p_image;
    String proname;
    String saleprize;
    String actualprice;
    String rating;
    String id;


    public ProductModelWishlist(String p_image, String proname, String saleprize, String actualprice, String rating, String id) {
        this.p_image = p_image;
        this.proname = proname;
        this.saleprize = saleprize;
        this.actualprice = actualprice;
        this.id = id;
        this.rating = rating;


    }

    public String getP_image() {
        return p_image;
    }

    public void setP_image(String p_image) {
        this.p_image = p_image;
    }

    public String getProname() {
        return proname;
    }

    public void setProname(String proname) {
        this.proname = proname;
    }

    public String getSaleprize() {
        return saleprize;
    }

    public void setSaleprize(String saleprize) {
        this.saleprize = saleprize;
    }

    public String getActualprice() {
        return actualprice;
    }

    public void setActualprice(String actualprice) {
        this.actualprice = actualprice;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id=id;
}
}