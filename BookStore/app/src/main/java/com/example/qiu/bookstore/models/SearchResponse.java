package com.example.qiu.bookstore.models;

import java.util.List;

/**
 * Created by qiu on 2017/12/30.
 */

public class SearchResponse {
    List<Book>searchByBookName;
    List<Book>searchByPublishName;

    public List<Book> getSearchByBookName() {
        return searchByBookName;
    }

    public void setSearchByBookName(List<Book> searchByBookName) {
        this.searchByBookName = searchByBookName;
    }

    public List<Book> getSearchByPublishName() {
        return searchByPublishName;
    }

    public void setSearchByPublishName(List<Book> searchByPublishName) {
        this.searchByPublishName = searchByPublishName;
    }
}
