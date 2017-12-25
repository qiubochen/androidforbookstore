package com.example.qiu.bookstore;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.qiu.bookstore.models.ResponseUserController;
import com.example.qiu.bookstore.models.User;
import com.example.qiu.bookstore.network.RRetrofit;
import com.example.qiu.bookstore.network.UserService;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by qiu on 2017/11/8.
 */

public class Register extends AppCompatActivity {
    Button buttonToRegister;
    EditText editTextForPassword;
    EditText editTextForUserName;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        buttonToRegister=(Button)findViewById(R.id.reButtonRegister);
        buttonToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextForPassword=(EditText) findViewById(R.id.reEditPassword);
                editTextForUserName=(EditText)findViewById(R.id.reEditUserName);
                String strForPassword=editTextForPassword.getText().toString();
                String strForUserName=editTextForUserName.getText().toString();
                if(strForPassword.equals("")||strForUserName.equals("")){

                    Toast.makeText(getApplicationContext(),"没有输入用户名或密码",Toast.LENGTH_SHORT).show();
                    return;
                }
                user=new User();
                user.setName(strForUserName);
                user.setPassword(strForPassword);
                UserService userService= RRetrofit.create(UserService.class);
                userService.postRegister(user)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<ResponseUserController>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(ResponseUserController responseUserController) {
                                System.out.println(responseUserController.getStatus());
                                if(responseUserController.getStatus().equals("10002")){
                                    Toast.makeText(getApplicationContext(),"你提交的名字已被占用",Toast.LENGTH_LONG).show();
                                }
                              if(responseUserController.getStatus().equals("200")){
                                  Intent intent=new Intent();
                                  intent.setClass(Register.this,MainActivity.class);
                                  startActivity(intent);
                              }
                            }
                        });
            }
        });

    }
}