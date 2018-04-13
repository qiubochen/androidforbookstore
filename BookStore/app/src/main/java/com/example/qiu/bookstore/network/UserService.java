package com.example.qiu.bookstore.network;

import com.example.qiu.bookstore.models.ResponseUserController;
import com.example.qiu.bookstore.models.User;
import com.example.qiu.bookstore.models.UserAndResponse;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by qiu on 2017/12/23.
 */

public interface UserService {
    @POST("/web-ssm/user/login")
    Observable<UserAndResponse>postLogin(@Body User user);

    @POST("/web-ssm/user/register")
    Observable<ResponseUserController>postRegister(@Body User user);
}
