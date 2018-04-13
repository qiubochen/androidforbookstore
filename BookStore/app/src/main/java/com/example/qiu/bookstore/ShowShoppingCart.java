package com.example.qiu.bookstore;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.qiu.bookstore.models.ResponseUserController;
import com.example.qiu.bookstore.models.ShoppingCart;
import com.example.qiu.bookstore.models.ShoppingCartResponse;
import com.example.qiu.bookstore.models.User;
import com.example.qiu.bookstore.network.RRetrofit;
import com.example.qiu.bookstore.network.ShoppingCartService;

import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ShowShoppingCart extends AppCompatActivity {
    private String TAG = "ShowShoppingCart";
    private RecyclerView recyclerView;
    private MyAdapter myAdapter;
    private List<ShoppingCartResponse> listOfShoppingCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        int userId = getIntent().getIntExtra("userId", 18);


        setContentView(R.layout.activity_show_shopping_cart);


        ShoppingCartService shoppingCartService = (ShoppingCartService) RRetrofit.create(ShoppingCartService.class);
        System.out.println(TAG + " " + userId);
        shoppingCartService.showShoppingCart(userId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<ShoppingCartResponse>>() {
                    @Override
                    public void onCompleted() {
                        System.out.println(TAG + " " + "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.getMessage();
                        System.out.println(e.getMessage());
                        System.out.println(TAG + " " + "onError");
                    }

                    @Override
                    public void onNext(List<ShoppingCartResponse> shoppingCartResponses) {
                        listOfShoppingCart = shoppingCartResponses;
                        System.out.println(TAG + " " + "1");


                        Iterator<ShoppingCartResponse> iterator = listOfShoppingCart.iterator();
                        while (iterator.hasNext()) {
                            ShoppingCartResponse shoppingCartResponse = iterator.next();
                            System.out.println(TAG + " " + shoppingCartResponse.getBookName());
                            System.out.println(TAG + " " + shoppingCartResponse.getCost());
                        }


                        recyclerView = (RecyclerView) findViewById(R.id.recycleviewforshowshoppingcart);
                        recyclerView.setLayoutManager(new LinearLayoutManager(ShowShoppingCart.this));
                        recyclerView.setAdapter(myAdapter = new MyAdapter());
                    }
                });

    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {


        @Override
        public MyViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
            final MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                    parent.getContext()).inflate(R.layout.show_shoppingcart_item, parent,
                    false));
            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            System.out.println(TAG + "!!! " + listOfShoppingCart.get(position).getBookName());
            System.out.println(TAG + "!!! " + listOfShoppingCart.get(position).getCost());
            ShoppingCartResponse shoppingCartResponseTemp = listOfShoppingCart.get(position);
            holder.textViewForPrice.setText(Integer.toString(listOfShoppingCart.get(position).getCost()));
            holder.textViewForBookName.setText(listOfShoppingCart.get(position).getBookName());
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            System.out.println(TAG+" "+format.format(listOfShoppingCart.get(position).getCurrentDate()).toString());
            holder.getTextViewForCurrentDate.setText(format.format(listOfShoppingCart.get(position).getCurrentDate()).toString());
        }

        @Override
        public int getItemCount() {
            System.out.println(TAG + " " + "listofshoppingcart" + listOfShoppingCart.size());
            return listOfShoppingCart.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            TextView textViewForPrice;
            TextView textViewForBookName;
            TextView getTextViewForCurrentDate;

            public MyViewHolder(View itemView) {
                super(itemView);
                textViewForBookName = (TextView) itemView.findViewById(R.id.textview_for_bookname);
                textViewForPrice = (TextView) itemView.findViewById(R.id.textview_for_price);
                getTextViewForCurrentDate = (TextView) itemView.findViewById(R.id.current_date);
            }
        }

    }
}
