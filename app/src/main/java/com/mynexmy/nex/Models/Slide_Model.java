package com.mynexmy.nex.Models;

public class Slide_Model {

    private String Banner_url;

    // Constructor to initialize Slide_Model with the image URL
    public Slide_Model(String banner_url) {
        this.Banner_url = banner_url;
    }

    // Getter for Banner_url
    public String getBanner_url() {
        return Banner_url;
    }

    // Setter for Banner_url
    public void setBanner_url(String banner_url) {
        Banner_url = banner_url;
    }
}
