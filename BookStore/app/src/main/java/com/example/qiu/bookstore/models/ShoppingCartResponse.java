package com.example.qiu.bookstore.models;

import java.util.Date;

/**
 * Created by qiu on 2017/12/29.
 */

public class ShoppingCartResponse {
    private int cost;
    private String bookName;
    private long currentDate;

    public long getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(long currentDate) {
        this.currentDate = currentDate;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }
}
