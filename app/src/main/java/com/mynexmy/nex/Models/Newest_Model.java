package com.mynexmy.nex.Models;

public class Newest_Model {
    public int product_id;
    int added_by;
    int product_status;
    int product_featured;
    int category_parent;
    int category_status;
    int product_reg_price;
    int category_id;
    int product_sell_price;
    double product_rating;
    int product_rating_total;
    int product_tax_percent;

    String product_title;
    String product_url;
    String product_code;
    String product_description;

    String product_image;

    String created_at;
    String updated_at;

    String category_title;
    String category_slug;

    String category_image;
    String category_description;
    String product_images;
    String product_image_url;



    public Newest_Model(int product_id, double product_rating, String product_title,
                          String product_description, int product_rating_total,
                          String product_image, int product_sell_price, int product_reg_price,int product_tax_percent,
                          int added_by)
    {
        this.product_id = product_id;
        this.added_by = added_by;
        this.product_status = product_status;
        this.product_featured = product_featured;
        this.category_parent = category_parent;
        this.category_status = category_status;
        this.product_reg_price = product_reg_price;
        this.category_id = category_id;
        this.product_sell_price = product_sell_price;
        this.product_rating = product_rating;
        this.product_rating_total = product_rating_total;
        this.product_title = product_title;
        this.product_url = product_url;
        this.product_code = product_code;
        this.product_description = product_description;
        this.product_image = product_image;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.category_title = category_title;
        this.category_slug = category_slug;
        this.category_image = category_image;
        this.category_description = category_description;
        this.product_images = product_images;
        this.product_image_url = product_image_url;
        this.product_tax_percent = product_tax_percent;
    }

    public int getProduct_tax_percent() {
        return product_tax_percent;
    }

    public void setProduct_tax_percent(int product_tax_percent) {
        this.product_tax_percent = product_tax_percent;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getAdded_by() {
        return added_by;
    }

    public void setAdded_by(int added_by) {
        this.added_by = added_by;
    }

    public int getProduct_status() {
        return product_status;
    }

    public void setProduct_status(int product_status) {
        this.product_status = product_status;
    }

    public int getProduct_featured() {
        return product_featured;
    }

    public void setProduct_featured(int product_featured) {
        this.product_featured = product_featured;
    }

    public int getCategory_parent() {
        return category_parent;
    }

    public void setCategory_parent(int category_parent) {
        this.category_parent = category_parent;
    }

    public int getCategory_status() {
        return category_status;
    }

    public void setCategory_status(int category_status) {
        this.category_status = category_status;
    }

    public int getProduct_reg_price() {
        return product_reg_price;
    }

    public void setProduct_reg_price(int product_reg_price) {
        this.product_reg_price = product_reg_price;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public int getProduct_sell_price() {
        return product_sell_price;
    }

    public void setProduct_sell_price(int product_sell_price) {
        this.product_sell_price = product_sell_price;
    }

    public double getProduct_rating() {
        return product_rating;
    }

    public void setProduct_rating(double product_rating) {
        this.product_rating = product_rating;
    }

    public int getProduct_rating_total() {
        return product_rating_total;
    }

    public void setProduct_rating_total(int product_rating_total) {
        this.product_rating_total = product_rating_total;
    }

    public String getProduct_title() {
        return product_title;
    }

    public void setProduct_title(String product_title) {
        this.product_title = product_title;
    }

    public String getProduct_url() {
        return product_url;
    }

    public void setProduct_url(String product_url) {
        this.product_url = product_url;
    }

    public String getProduct_code() {
        return product_code;
    }

    public void setProduct_code(String product_code) {
        this.product_code = product_code;
    }

    public String getProduct_description() {
        return product_description;
    }

    public void setProduct_description(String product_description) {
        this.product_description = product_description;
    }

    public String getProduct_image() {
        return product_image;
    }

    public void setProduct_image(String product_image) {
        this.product_image = product_image;
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

    public String getCategory_title() {
        return category_title;
    }

    public void setCategory_title(String category_title) {
        this.category_title = category_title;
    }

    public String getCategory_slug() {
        return category_slug;
    }

    public void setCategory_slug(String category_slug) {
        this.category_slug = category_slug;
    }

    public String getCategory_image() {
        return category_image;
    }

    public void setCategory_image(String category_image) {
        this.category_image = category_image;
    }

    public String getCategory_description() {
        return category_description;
    }

    public void setCategory_description(String category_description) {
        this.category_description = category_description;
    }

    public String getProduct_images() {
        return product_images;
    }

    public void setProduct_images(String product_images) {
        this.product_images = product_images;
    }

    public String getProduct_image_url() {
        return product_image_url;
    }

    public void setProduct_image_url(String product_image_url) {
        this.product_image_url = product_image_url;
    }
}

