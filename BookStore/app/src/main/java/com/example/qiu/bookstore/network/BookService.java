package com.example.qiu.bookstore.network;

import com.example.qiu.bookstore.models.Book;
import com.example.qiu.bookstore.models.ListBooks;

import java.util.List;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by qiu on 2017/12/24.
 */

public interface BookService {
    @GET("/book/showbook")
    Observable<List<Book>>getAllBook();
}
