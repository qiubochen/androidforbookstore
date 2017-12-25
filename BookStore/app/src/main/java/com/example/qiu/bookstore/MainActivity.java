package com.example.qiu.bookstore;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class MainActivity extends AppCompatActivity {
    Button buttonToLogin;
    Button buttonToRegister;
    String strForUserName;
    String strForPassword;
    EditText editTextForPassWord;
    EditText editTextForUserName;
    User user = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonToLogin=(Button)findViewById(R.id.buttonLogin);
        buttonToLogin.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {

                UserService userservice= RRetrofit.create(UserService.class);
                editTextForUserName=(EditText) findViewById(R.id.editUserName);
                editTextForPassWord=(EditText)findViewById(R.id.editPassword);
                strForPassword=editTextForPassWord.getText().toString();
                strForUserName=editTextForUserName.getText().toString();
                if(strForPassword.equals("")||strForUserName.equals("")){

                    Toast.makeText(getApplicationContext(),"没有输入用户名或密码",Toast.LENGTH_SHORT).show();
                    return;
                }
                System.out.println(strForPassword);
                System.out.println(strForUserName);
                user.setName(strForUserName);
                user.setPassword(strForPassword);
                userservice.postLogin(user)
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
                                if(responseUserController.getStatus().equals("200")){
                                    Intent intent=new Intent();
                                    intent.setClass(MainActivity.this,ShowBook.class);
                                    startActivity(intent);
                                }
                                if(responseUserController.getStatus().equals("10001")){
                                    Toast.makeText(getApplicationContext(),"登录失败",Toast.LENGTH_LONG).show();
                                }
//                                     Toast.makeText(getApplicationContext(),responseUserController.getStatus(),Toast.LENGTH_LONG).show();
//                                System.out.println(responseUserController.getStatus());
                            }
                        });




            }
        });
        buttonToRegister=(Button)findViewById(R.id.buttonRegister);
        buttonToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentToRegister=new Intent();
                intentToRegister.setClass(MainActivity.this,Register.class);
                startActivity(intentToRegister);
            }
        });
    }
}
