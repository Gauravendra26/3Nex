package com.mynexmy.nex.Models;

public class Orderdetail_Model {

    int order_details_id,order_id,product_id,price,qty,tax,product_status,added_by,product_reg_price,product_sell_price,product_rating,
            product_rating_total,product_featured,product_tax_percent;
    String product_name,product_title,product_url,product_code,product_description,category_id,product_image,created_at,updated_at;

    public Orderdetail_Model(int order_details_id, int order_id, int product_id, int price, int qty, int tax, int product_status,
                             int added_by, int product_reg_price, int product_sell_price, int product_rating, int product_rating_total,
                             int product_featured, int product_tax_percent, String product_name, String product_title, String product_url,
                             String product_code, String product_description, String category_id, String product_image, String created_at,
                             String updated_at) {
        this.order_details_id = order_details_id;
        this.order_id = order_id;
        this.product_id = product_id;
        this.price = price;
        this.qty = qty;
        this.tax = tax;
        this.product_status = product_status;
        this.added_by = added_by;
        this.product_reg_price = product_reg_price;
        this.product_sell_price = product_sell_price;
        this.product_rating = product_rating;
        this.product_rating_total = product_rating_total;
        this.product_featured = product_featured;
        this.product_tax_percent = product_tax_percent;
        this.product_name = product_name;
        this.product_title = product_title;
        this.product_url = product_url;
        this.product_code = product_code;
        this.product_description = product_description;
        this.category_id = category_id;
        this.product_image = product_image;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public int getOrder_details_id() {
        return order_details_id;
    }

    public void setOrder_details_id(int order_details_id) {
        this.order_details_id = order_details_id;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public int getTax() {
        return tax;
    }

    public void setTax(int tax) {
        this.tax = tax;
    }

    public int getProduct_status() {
        return product_status;
    }

    public void setProduct_status(int product_status) {
        this.product_status = product_status;
    }

    public int getAdded_by() {
        return added_by;
    }

    public void setAdded_by(int added_by) {
        this.added_by = added_by;
    }

    public int getProduct_reg_price() {
        return product_reg_price;
    }

    public void setProduct_reg_price(int product_reg_price) {
        this.product_reg_price = product_reg_price;
    }

    public int getProduct_sell_price() {
        return product_sell_price;
    }

    public void setProduct_sell_price(int product_sell_price) {
        this.product_sell_price = product_sell_price;
    }

    public int getProduct_rating() {
        return product_rating;
    }

    public void setProduct_rating(int product_rating) {
        this.product_rating = product_rating;
    }

    public int getProduct_rating_total() {
        return product_rating_total;
    }

    public void setProduct_rating_total(int product_rating_total) {
        this.product_rating_total = product_rating_total;
    }

    public int getProduct_featured() {
        return product_featured;
    }

    public void setProduct_featured(int product_featured) {
        this.product_featured = product_featured;
    }

    public int getProduct_tax_percent() {
        return product_tax_percent;
    }

    public void setProduct_tax_percent(int product_tax_percent) {
        this.product_tax_percent = product_tax_percent;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
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

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
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
}
