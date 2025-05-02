package com.mynexmy.nex.Models;


public class Scratchcard_items_Model {

    int bucket_product_map_id,bucket_id,bucket_product_id,bucket_product_status,
            added_by,ordering;
    String bucket_product_title;

    String bucket_product_image;
    String created_at;

    String updated_at;

    public Scratchcard_items_Model(int bucket_product_map_id, int bucket_id, int bucket_product_id,int ordering,
                                   int bucket_product_status,  int added_by,
                                   String bucket_product_title,
                                   String bucket_product_image, String created_at, String updated_at) {
        this.bucket_product_map_id = bucket_product_map_id;
        this.bucket_id = bucket_id;
        this.bucket_product_id = bucket_product_id;
        this.bucket_product_status = bucket_product_status;
        this.ordering = ordering;

        this.added_by = added_by;
        this.bucket_product_title = bucket_product_title;

        this.bucket_product_image = bucket_product_image;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public int getOrdering() {
        return ordering;
    }

    public void setOrdering(int ordering) {
        this.ordering = ordering;
    }

    public int getBucket_product_map_id() {
        return bucket_product_map_id;
    }

    public void setBucket_product_map_id(int bucket_product_map_id) {
        this.bucket_product_map_id = bucket_product_map_id;
    }

    public int getBucket_id() {
        return bucket_id;
    }

    public void setBucket_id(int bucket_id) {
        this.bucket_id = bucket_id;
    }

    public int getBucket_product_id() {
        return bucket_product_id;
    }

    public void setBucket_product_id(int bucket_product_id) {
        this.bucket_product_id = bucket_product_id;
    }

    public int getBucket_product_status() {
        return bucket_product_status;
    }

    public void setBucket_product_status(int bucket_product_status) {
        this.bucket_product_status = bucket_product_status;
    }


    public int getAdded_by() {
        return added_by;
    }

    public void setAdded_by(int added_by) {
        this.added_by = added_by;
    }

    public String getBucket_product_title() {
        return bucket_product_title;
    }

    public void setBucket_product_title(String bucket_product_title) {
        this.bucket_product_title = bucket_product_title;
    }



    public String getBucket_product_image() {
        return bucket_product_image;
    }

    public void setBucket_product_image(String bucket_product_image) {
        this.bucket_product_image = bucket_product_image;
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
}