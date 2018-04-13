package com.example.qiu.bookstore.network;

import com.example.qiu.bookstore.models.Book;
import com.example.qiu.bookstore.models.ListBooks;
import com.example.qiu.bookstore.models.ResponseUserController;
import com.example.qiu.bookstore.models.SearchResponse;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by qiu on 2017/12/24.
 */

public interface BookService {
    @GET("/web-ssm/book/show")
    Observable<List<Book>>getAllBook();
    @GET("/web-ssm/book/search")
    Observable<SearchResponse>searchBookByPublishingHouseOrBookName(@Query("strOfBookNameOrPublishingHouse") String strOfBookNameOrPublishingHouse);
    @POST("/web-ssm/book/addclick")
    Observable<ResponseUserController> addclick(@Body Book book     );
}
