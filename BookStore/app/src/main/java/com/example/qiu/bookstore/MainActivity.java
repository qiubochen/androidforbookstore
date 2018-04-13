package com.example.qiu.bookstore;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ShareActionProvider;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.qiu.bookstore.models.ResponseUserController;
import com.example.qiu.bookstore.models.User;
import com.example.qiu.bookstore.models.UserAndResponse;
import com.example.qiu.bookstore.myutil.PasswordToggleEditText;
import com.example.qiu.bookstore.myutil.UserManage;
import com.example.qiu.bookstore.network.RRetrofit;
import com.example.qiu.bookstore.network.UserService;

import java.net.CookieStore;
import java.util.Observable;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    Button buttonToLogin;
    Button buttonToRegister;
    public static Boolean isOk=false;
   // EditText editTextForPassWord;
    PasswordToggleEditText editTextForPassWord;
    EditText editTextForUserName;
    User user = new User();

    private String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);
        boolean flag=getIntent().getBooleanExtra("flag",false);
        //这个是用来判断这个存储数据是否存在，如果存在就跳过
        if(UserManage.getInstance().hasUserInfo(this)){
            if(!flag)
            toIntent(UserManage.getInstance().getUserInfo(this));
        }
        editTextForPassWord = (PasswordToggleEditText) findViewById(R.id.editPassword);
        buttonToLogin = (Button) findViewById(R.id.buttonLogin);
        //editTextForPassWord.setTransformationMethod(PasswordTransformationMethod.getInstance());




        buttonToLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                editTextForUserName = (EditText) findViewById(R.id.editUserName);



                String strForPassword = editTextForPassWord.getText().toString();
                String strForUserName = editTextForUserName.getText().toString();

                contectSsm(strForUserName,strForPassword);
                System.out.println(TAG+"isok "+isOk);

                if(isOk) {

                }

            }
        });
        buttonToRegister = (Button) findViewById(R.id.buttonRegister);
        buttonToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentToRegister = new Intent();
                intentToRegister.setClass(MainActivity.this, Register.class);
                startActivity(intentToRegister);
            }
        });
    }

    private void contectSsm(final String strForUserName, final String strForPassword){
        UserService userservice = RRetrofit.create(UserService.class);
        System.out.println(TAG+"进来了");

        if (strForPassword.equals("") || strForUserName.equals("")) {

            Toast.makeText(getApplicationContext(), "没有输入用户名或密码", Toast.LENGTH_SHORT).show();
            return;
        }
        System.out.println(strForPassword);
        System.out.println(strForUserName);
        user.setName(strForUserName);
        user.setPassword(strForPassword);
        userservice.postLogin(user)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<UserAndResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                      Toast.makeText(getApplicationContext(),"找不到服务器",Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onNext(UserAndResponse userAndResponse) {
                        if (userAndResponse.getStatus().equals("200")) {
                            UserAndResponse userAndResponseTemp = new UserAndResponse();
                            userAndResponseTemp.setPassword(userAndResponse.getPassword());
                            userAndResponseTemp.setId(userAndResponse.getId());
                            userAndResponseTemp.setName(userAndResponse.getName());


                            System.out.println(TAG+" "+isOk);
                            System.out.println(TAG+"isok");
                            //获取用户输入密码，用户名信息
                            UserManage.getInstance().saveUserInfo(MainActivity.this,userAndResponse.getName(),userAndResponse.getPassword(),userAndResponse.getId());
                            toIntent(userAndResponseTemp);

                        }
                        if (userAndResponse.getStatus().equals("10001")) {
                            Toast.makeText(getApplicationContext(), "登录失败", Toast.LENGTH_LONG).show();
                        }
//                                     Toast.makeText(getApplicationContext(),responseUserController.getStatus(),Toast.LENGTH_LONG).show();
//                                System.out.println(responseUserController.getStatus());
                    }
                });

    }
    void toIntent(UserAndResponse userAndResponseTemp){
        System.out.println(TAG + userAndResponseTemp.getId());
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, ShowBook.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("userAndResponse", userAndResponseTemp);
        intent.putExtras(bundle);

        startActivity(intent);
    }

}
