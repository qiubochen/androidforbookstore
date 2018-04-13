package com.example.qiu.bookstore.fragmentshoppingcart;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qiu.bookstore.AddShoppingCart;
import com.example.qiu.bookstore.R;
import com.example.qiu.bookstore.models.Book;
import com.example.qiu.bookstore.models.BookAndUserId;
import com.example.qiu.bookstore.models.ResponseUserController;
import com.example.qiu.bookstore.models.ShoppingCart;
import com.example.qiu.bookstore.myutil.BookAndUserIdManage;
import com.example.qiu.bookstore.network.RRetrofit;
import com.example.qiu.bookstore.network.ShoppingCartService;

import java.text.SimpleDateFormat;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by qiu on 2018/4/12.
 */

public class ShoppingCartFragment extends Fragment implements View.OnClickListener{
    private View mView;
    private String TAG="ShoppingCartFragment";

    TextView textViewForPrice;
    TextView textViewForName;
    TextView textViewForPublishinghouse;
    TextView textViewForPublishingDate;
    Button buttonForAddShoppingCart;
    ShoppingCart shoppingCart;
    public ShoppingCartFragment() {
    }
    void getData(){

    }
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //AddShoppingCart activity=(AddShoppingCart)getActivity();
        System.out.println(" qiuqiu");
        if(this.mView == null) {
            this.mView = inflater.inflate(R.layout.activity_shopping_cart, container, false);
        }
        textViewForName=(TextView)mView.findViewById(R.id.textforbookname);
        textViewForPrice=(TextView)mView.findViewById(R.id.textforprice);
        textViewForPublishinghouse=(TextView)mView.findViewById(R.id.textforpublishinghouse);
        textViewForPublishingDate=(TextView)mView.findViewById(R.id.textforpublishingdate);
        buttonForAddShoppingCart=(Button)mView.findViewById(R.id.buttonforaddshoppingcart);
        buttonForAddShoppingCart.setOnClickListener(this);
        setUi();
        return this.mView;
    }

    public void setUi(){

            BookAndUserId book= BookAndUserIdManage.getInstance().getBookAndUserIdInfo(getContext());
        shoppingCart=new ShoppingCart();
           shoppingCart.setUserId(book.getUserId());
           shoppingCart.setBookId(book.getBookId());
           shoppingCart.setCost(book.getPrice());
            System.out.println("qiu11"+" "+book.getUserId());
        System.out.println("qiu11"+book.getBookId());
            textViewForName.setText( book.getName());
            textViewForPrice.setText(Integer.toString(book.getPrice()));
            System.out.println(TAG + " " + "text");
            textViewForPublishinghouse.setText(book.getPublishHouse());

            textViewForPublishingDate.setText(book.getPublishDate());

    }

    public void onDestroyView() {
        super.onDestroyView();
        ((ViewGroup)this.mView.getParent()).removeView(this.mView);
    }
    public void addGoods(){

        ShoppingCartService shoppingCartService= RRetrofit.create(ShoppingCartService.class);
        shoppingCartService.addShoppingCart(shoppingCart)
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
                            Toast.makeText(getContext(),"加入购物车成功",Toast.LENGTH_LONG).show();
                        }
                        if(responseUserController.getStatus().equals("10003")){
                            Toast.makeText(getContext(),"你的书本id不正确",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonforaddshoppingcart:{
                addGoods();
            }
        }
    }
}
