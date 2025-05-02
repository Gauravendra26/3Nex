package com.mynexmy.nex.Models;

public class HomeCategory_Model {

    int category_id,category_parent,category_status,added_by;
    String category_title,category_slug,category_image,category_description
            ,created_at,updated_at;



    public HomeCategory_Model(int category_id, String category_title, String category_image) {
        this.category_id = category_id;
        this.category_parent = category_parent;
        this.category_status = category_status;
        this.added_by = added_by;
        this.category_title = category_title;
        this.category_slug = category_slug;
        this.category_image = category_image;
        this.category_description = category_description;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
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

    public int getAdded_by() {
        return added_by;
    }

    public void setAdded_by(int added_by) {
        this.added_by = added_by;
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

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }


}

