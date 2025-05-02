package com.mynexmy.nex.Models;

public class product_images {
    String product_image_url;
    int product_id;

    public product_images(String product_image_url, int product_id) {
        this.product_image_url = product_image_url;
        this.product_id = product_id;
    }

    public String getProduct_image_url() {
        return product_image_url;
    }

    public void setProduct_image_url(String product_image_url) {
        this.product_image_url = product_image_url;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }
}
