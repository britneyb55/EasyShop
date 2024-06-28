package org.yearup.models;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Order {

    int order_id;
    int user_id;
    LocalDate date;

    String address;
    String city;
    String state;
    String zip;
    double shipping_amount;

    public Order()
    {

    }

    public Order(int order_id, int user_id, LocalDate date, String address, String city, String state, String zip, double shipping_amount) {
        // Parameterized constructor
        this.order_id = order_id;
        this.user_id = user_id;
        this.date = date;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.shipping_amount = shipping_amount;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_d(int order_id) {
        this.order_id = order_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public double getShipping_amount() {
        return shipping_amount;
    }

    public void setShipping_amount(double shipping_amount) {
        this.shipping_amount = shipping_amount;
    }

    // OrderDoa // submitOrder(
    //MySqlOrderDao


    //OrderController RestController(/orders)
    //PostMapping()
    // public void submitOrder() {
    // you would be calling the method(s) from your MySqlOrderDao
//}





}
