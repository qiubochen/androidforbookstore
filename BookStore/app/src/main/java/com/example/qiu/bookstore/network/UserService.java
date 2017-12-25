package com.example.qiu.bookstore.network;

import com.example.qiu.bookstore.models.ResponseUserController;
import com.example.qiu.bookstore.models.User;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by qiu on 2017/12/23.
 */

public interface UserService {
    @POST("/user/login")
    Observable<ResponseUserController>postLogin(@Body User user);

    @POST("/user/register")
    Observable<ResponseUserController>postRegister(@Body User user);
}
