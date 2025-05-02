package com.mynexmy.nex.Models;

public class Slide_Model_Movie {
    String image;
    String position;
int indicator;
    // Constructor to initialize image URL and position
    public Slide_Model_Movie(String image, String position,int indicator) {
        this.image = image;
        this.position = position;
        this.indicator = indicator;
    }

    public int getIndicator() {
        return indicator;
    }

    public void setIndicator(int indicator) {
        this.indicator = indicator;
    }

    // Getter and setter methods
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
