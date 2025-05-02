package com.mynexmy.nex.Models;

public class scratch1_model {

    int scratch_card_id,status,scratch_card_price,level_count,scratch_count;
    int homeScratchImages;
    String created_at,updated_at,scratch_card_title;

    public scratch1_model(int scratch_card_id, String scratch_card_title, int status,
                          int scratch_card_price, String created_at, String updated_at,
                          int homeScratchImages ,int level_count, int scratch_count) {
        this.scratch_card_id = scratch_card_id;
        this.scratch_card_title = scratch_card_title;
        this.homeScratchImages = homeScratchImages;

        this.status = status;
        this.scratch_card_price = scratch_card_price;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.level_count = level_count;
        this.scratch_count = scratch_count;
    }

    public int getScratch_count() {
        return scratch_count;
    }

    public void setScratch_count(int scratch_count) {
        this.scratch_count = scratch_count;
    }

    public int getLevel_count() {
        return level_count;
    }

    public void setLevel_count(int level_count) {
        this.level_count = level_count;
    }

    public int getHomeScratchImages() {
        return homeScratchImages;
    }

    public void setHomeScratchImages(int homeScratchImages) {
        this.homeScratchImages = homeScratchImages;
    }

    public int getScratch_card_id() {
        return scratch_card_id;
    }

    public void setScratch_card_id(int scratch_card_id) {
        this.scratch_card_id = scratch_card_id;
    }

    public String getScratch_card_title() {
        return scratch_card_title;
    }

    public void setScratch_card_title(String scratch_card_title) {
        this.scratch_card_title = scratch_card_title;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getScratch_card_price() {
        return scratch_card_price;
    }

    public void setScratch_card_price(int scratch_card_price) {
        this.scratch_card_price = scratch_card_price;
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
