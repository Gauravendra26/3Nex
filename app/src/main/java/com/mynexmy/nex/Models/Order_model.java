package com.mynexmy.nex.Models;

public class Order_model {

    int order_id,customer_id,status;
    double total_amount;
   String order_number, shipping_address, payment_method,transaction_details,order_tracking_id, created_at, updated_at ;

    public Order_model(int order_id, int customer_id, double total_amount, int status, String order_number,
                       String shipping_address, String payment_method,String transaction_details,String order_tracking_id,
                       String created_at, String updated_at ) {
        this.order_id = order_id;
        this.customer_id = customer_id;
        this.total_amount = total_amount;
        this.status = status;
        this.order_number = order_number;
        this.shipping_address = shipping_address;
        this.payment_method = payment_method;
        this.order_tracking_id = order_tracking_id;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.transaction_details = transaction_details;

    }

    public String getTransaction_details() {
        return transaction_details;
    }

    public void setTransaction_details(String transaction_details) {
        this.transaction_details = transaction_details;
    }

    public String getOrder_tracking_id() {
        return order_tracking_id;
    }

    public void setOrder_tracking_id(String order_tracking_id) {
        this.order_tracking_id = order_tracking_id;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public double getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(double total_amount) {
        this.total_amount = total_amount;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getOrder_number() {
        return order_number;
    }

    public void setOrder_number(String order_number) {
        this.order_number = order_number;
    }

    public String getShipping_address() {
        return shipping_address;
    }

    public void setShipping_address(String shipping_address) {
        this.shipping_address = shipping_address;
    }

    public String getPayment_method() {
        return payment_method;
    }

    public void setPayment_method(String payment_method) {
        this.payment_method = payment_method;
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

