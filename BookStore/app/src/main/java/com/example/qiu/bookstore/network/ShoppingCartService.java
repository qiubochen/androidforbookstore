package com.example.qiu.bookstore.network;

import com.example.qiu.bookstore.models.ResponseUserController;
import com.example.qiu.bookstore.models.ShoppingCart;
import com.example.qiu.bookstore.models.ShoppingCartResponse;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by qiu on 2017/12/29.
 */

public interface ShoppingCartService {
    @POST("/web-ssm/shoppingcart/add")
    Observable<ResponseUserController>addShoppingCart(@Body ShoppingCart shoppingCart);
    @GET("/web-ssm/shoppingcart/show")
    Observable<List<ShoppingCartResponse>>showShoppingCart(@Query("userId") int userId);
}
