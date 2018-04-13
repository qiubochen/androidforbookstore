package com.example.qiu.bookstore;

import android.app.Fragment;
import android.support.annotation.MainThread;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qiu.bookstore.models.Book;
import com.example.qiu.bookstore.models.ResponseUserController;
import com.example.qiu.bookstore.models.ShoppingCart;
import com.example.qiu.bookstore.network.RRetrofit;
import com.example.qiu.bookstore.network.ShoppingCartService;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class AddShoppingCart extends AppCompatActivity{
    private final String TAG="AddShoppingCart";
    TextView textViewForPrice;
    TextView textViewForName;
    TextView textViewForPublishinghouse;
    TextView textViewForPublishingDate;
    Button buttonForAddShoppingCart;
    ShoppingCart shoppingCart=new ShoppingCart();
    Book book;
    int userId;

    public void setBook() {
        Book book1=(Book)getIntent().getSerializableExtra("bookFromShowBook");
        this.book = book1;
    }

    public Book getBook() {
        return book;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        book=(Book)getIntent().getSerializableExtra("bookFromShowBook");
        userId=getIntent().getIntExtra("userId",0);
        System.out.println(book.getClickCount()+" "+book.getPrice());
        setContentView(R.layout.activity_shopping_cart);
        textViewForName=(TextView)findViewById(R.id.textforbookname);
        textViewForPrice=(TextView)findViewById(R.id.textforprice);
        textViewForPublishinghouse=(TextView)findViewById(R.id.textforpublishinghouse);
        textViewForPublishingDate=(TextView)findViewById(R.id.textforpublishingdate);

        textViewForName.setText(book.getName());
        textViewForPrice.setText(Integer.toString(book.getPrice()));
        System.out.println(TAG+" "+"text");
        textViewForPublishinghouse.setText(book.getPublishingHouse());
        SimpleDateFormat format =  new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
        System.out.println("TAG"+" "+format.format(book.getPublishingDate()));
        textViewForPublishingDate.setText(format.format(book.getPublishingDate()).toString());
        System.out.println(TAG+" "+"userId"+userId );
        shoppingCart.setUserId(userId);
        shoppingCart.setBookId(book.getId());
        shoppingCart.setCost(book.getPrice());
        System.out.println(TAG+" "+shoppingCart.getCost());
        buttonForAddShoppingCart=(Button)findViewById(R.id.buttonforaddshoppingcart);
        buttonForAddShoppingCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShoppingCartService shoppingCartService=RRetrofit.create(ShoppingCartService.class);
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
                                 Toast.makeText(getApplicationContext(),"加入购物车成功",Toast.LENGTH_LONG).show();
                             }
                             if(responseUserController.getStatus().equals("10003")){
                                 Toast.makeText(getApplicationContext(),"你的书本id不正确",Toast.LENGTH_LONG).show();
                             }
                            }
                        });
            }
        });
    }


}
