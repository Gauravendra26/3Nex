package com.mynexmy.nex.Models;

public class Slide_Model_Nextube {
    String image;

    String position;

    public Slide_Model_Nextube(String image, String position) {
        this.image = image;
        this.position = position;
    }

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

