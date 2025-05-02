package com.mynexmy.nex.Models;

public class Scratchcard_Model1 {


  int scratch_card_payment_id,scratch_card_id,customer_id,amount, bucket_id,level,is_scratched,
            bucket_product_id, bucket_product_status,added_by,ref_count_one,ref_count_two,
            ref_count_three,ref_count_four;

    String shipping_address,shipment_tracker,created_at,updated_at,bucket_product_title,
            bucket_product_description,bucket_product_image, awb,tracking_data;

    public Scratchcard_Model1(int scratch_card_payment_id, int scratch_card_id, int customer_id, int amount,
                             int bucket_id, int level, int is_scratched, int bucket_product_id,
                             int bucket_product_status, int added_by, int ref_count_one, int ref_count_two,
                             int ref_count_three, int ref_count_four,String shipping_address,
                             String shipment_tracker, String created_at, String updated_at,
                             String bucket_product_title, String bucket_product_description,
                             String bucket_product_image, String awb, String tracking_data) {
        this.scratch_card_payment_id = scratch_card_payment_id;
        this.scratch_card_id = scratch_card_id;
        this.customer_id = customer_id;
        this.amount = amount;
        this.bucket_id = bucket_id;
        this.level = level;
        this.is_scratched = is_scratched;
        this.bucket_product_id = bucket_product_id;
        this.bucket_product_status = bucket_product_status;
        this.added_by = added_by;
        this.ref_count_one = ref_count_one;
        this.ref_count_two = ref_count_two;
        this.ref_count_three = ref_count_three;
        this.ref_count_four = ref_count_four;
        this.shipping_address = shipping_address;
        this.shipment_tracker = shipment_tracker;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.bucket_product_title = bucket_product_title;
        this.bucket_product_description = bucket_product_description;
        this.bucket_product_image = bucket_product_image;
        this.awb = awb;
        this.tracking_data = tracking_data;
    }

    public String getAwb() {
        return awb;
    }

    public void setAwb(String awb) {
        this.awb = awb;
    }

    public String getTracking_data() {
        return tracking_data;
    }

    public void setTracking_data(String tracking_data) {
        this.tracking_data = tracking_data;
    }

    public int getRef_count_one() {
        return ref_count_one;
    }

    public void setRef_count_one(int ref_count_one) {
        this.ref_count_one = ref_count_one;
    }

    public int getRef_count_two() {
        return ref_count_two;
    }

    public void setRef_count_two(int ref_count_two) {
        this.ref_count_two = ref_count_two;
    }

    public int getRef_count_three() {
        return ref_count_three;
    }

    public void setRef_count_three(int ref_count_three) {
        this.ref_count_three = ref_count_three;
    }

    public int getRef_count_four() {
        return ref_count_four;
    }

    public void setRef_count_four(int ref_count_four) {
        this.ref_count_four = ref_count_four;
    }

    public int getScratch_card_payment_id() {
        return scratch_card_payment_id;
    }

    public void setScratch_card_payment_id(int scratch_card_payment_id) {
        this.scratch_card_payment_id = scratch_card_payment_id;
    }

    public int getScratch_card_id() {
        return scratch_card_id;
    }

    public void setScratch_card_id(int scratch_card_id) {
        this.scratch_card_id = scratch_card_id;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getBucket_id() {
        return bucket_id;
    }

    public void setBucket_id(int bucket_id) {
        this.bucket_id = bucket_id;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getIs_scratched() {
        return is_scratched;
    }

    public void setIs_scratched(int is_scratched) {
        this.is_scratched = is_scratched;
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

    public String getShipping_address() {
        return shipping_address;
    }

    public void setShipping_address(String shipping_address) {
        this.shipping_address = shipping_address;
    }

    public String getShipment_tracker() {
        return shipment_tracker;
    }

    public void setShipment_tracker(String shipment_tracker) {
        this.shipment_tracker = shipment_tracker;
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

    public String getBucket_product_title() {
        return bucket_product_title;
    }

    public void setBucket_product_title(String bucket_product_title) {
        this.bucket_product_title = bucket_product_title;
    }

    public String getBucket_product_description() {
        return bucket_product_description;
    }

    public void setBucket_product_description(String bucket_product_description) {
        this.bucket_product_description = bucket_product_description;
    }

    public String getBucket_product_image() {
        return bucket_product_image;
    }

    public void setBucket_product_image(String bucket_product_image) {
        this.bucket_product_image = bucket_product_image;
    }
}
