package com.example.qiu.bookstore.models;


import java.io.Serializable;
import java.util.Date;

/**
 * Created by qiu on 2017/12/23.
 */

public class Book implements Serializable{
    private int id;
    private String name;
    private long publishingDate;
    private String publishingHouse;
    private int price;
    private int clickCount;
    private int isDelete;
    private int isBuy;
    private String bookCategory;
    private int isActivity;

    public int getIsActivity() {
        return isActivity;
    }

    public void setIsActivity(int isActivity) {
        this.isActivity = isActivity;
    }

    //    private Date orderCurrentDate;
//
//    public Date getOrderCurrentDate() {
//        return orderCurrentDate;
//    }
//
//    public void setOrderCurrentDate(Date orderCurrentDate) {
//        this.orderCurrentDate = orderCurrentDate;
//    }

    public String getBookCategory() {
        return bookCategory;
    }

    public void setBookCategory(String bookCategory) {
        this.bookCategory = bookCategory;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPublishingDate() {
        return publishingDate;
    }

    public void setPublishingDate(long publishingDate) {
        this.publishingDate = publishingDate;
    }

    public String getPublishingHouse() {
        return publishingHouse;
    }

    public void setPublishingHouse(String publishingHouse) {
        this.publishingHouse = publishingHouse;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }


    public int getClickCount() {
        return clickCount;
    }

    public void setClickCount(int clickCount) {
        this.clickCount = clickCount;
    }

    public int getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(int isDelete) {
        this.isDelete = isDelete;
    }

    public int getIsBuy() {
        return isBuy;
    }

    public void setIsBuy(int isBuy) {
        this.isBuy = isBuy;
    }
}
