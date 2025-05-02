package com.mynexmy.nex.Activitys;

public class order_details {

    int order_details_id,order_id,product_id,price,qty,tax;
    String product_name;

    public order_details(int order_details_id, int order_id,
                         int product_id, int price, int qty, int tax, String product_name) {
        this.order_details_id = order_details_id;
        this.order_id = order_id;
        this.product_id = product_id;
        this.price = price;
        this.qty = qty;
        this.tax = tax;
        this.product_name = product_name;
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

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }
}
