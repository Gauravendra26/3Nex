package com.mynexmy.nex.Models;

public class Slide_Model_Footwear {
    String image;
    String position;
    int Indicator;

    // Constructor to initialize image URL and position
    public Slide_Model_Footwear(String image, String position ,int Indicator) {
        this.image = image;
        this.position = position;
        this.Indicator = Indicator;
    }

    public int getIndicator() {
        return Indicator;
    }

    public void setIndicator(int indicator) {
        Indicator = indicator;
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
