package com.example.qiu.bookstore;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.qiu.bookstore.models.Book;

public class ShoppingCart extends AppCompatActivity {

    TextView textViewForPrice;
    TextView textViewForName;
    TextView textViewForPublishinghouse;
    TextView textViewForPublishingDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Book book=(Book)getIntent().getSerializableExtra("bookFromShowBook");
        System.out.println(book.getClickCount()+" "+book.getPrice());
        setContentView(R.layout.activity_shopping_cart);
        textViewForName=(TextView)findViewById(R.id.textforbookname);
        textViewForPrice=(TextView)findViewById(R.id.textforpublishinghouse);
        textViewForPublishinghouse=(TextView)findViewById(R.id.textforpublishingdate);
        textViewForName.setText(book.getName());
        textViewForPrice.setText(Integer.toString(book.getPrice()));
        textViewForPublishinghouse.setText(book.getPublishingHouse());
    }
}
