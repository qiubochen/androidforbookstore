package com.example.qiu.bookstore;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.qiu.bookstore.models.Book;
import com.example.qiu.bookstore.models.ListBooks;
import com.example.qiu.bookstore.network.BookService;
import com.example.qiu.bookstore.network.RRetrofit;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ShowBook extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private List<Book> mDatas;
    private HomeAdapter mAdapter;
    private  final String TAG="showBook";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("test1");
        setContentView(R.layout.activity_show_book);

        Log.d(TAG,"text");
        initData();

    }
    protected void initData()
    {
        BookService bookservice= RRetrofit.create(BookService.class);
        bookservice.getAllBook()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Book>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG,"出现错误" );
                        Log.d(TAG,e.getMessage());
                    }

                    @Override
                    public void onNext(List<Book> books) {
                       Log.d(TAG,"进到onnext函数");
                       mDatas=books;
                        mRecyclerView = (RecyclerView) findViewById(R.id.id_recyclerview);
                        mRecyclerView.setLayoutManager(new LinearLayoutManager(ShowBook.this));
                        mRecyclerView.setAdapter(mAdapter = new HomeAdapter());
                    }
                });

    }
    class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder>
    {

        @Override
        public MyViewHolder onCreateViewHolder(final ViewGroup parent, int viewType)
        {
            System.out.println("1");
            final MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                    parent.getContext()).inflate(R.layout.item_home, parent,
                    false));
            holder.parentView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position=holder.getAdapterPosition();
                    Book book=mDatas.get(position);
                    Intent intent=new Intent(ShowBook.this,ShoppingCart.class);
                    Bundle bundle=new Bundle();
                    bundle.putSerializable("bookFromShowBook",book);
                    intent.putExtras(bundle);
                    startActivity(intent);

                }
            });
            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position)
        {
            System.out.println("2");
            Book book=mDatas.get(position);



                System.out.println(TAG+" "+book.getId());
                System.out.println(TAG+" "+book.getName());
                System.out.println(TAG+" "+book.getPrice());
                System.out.println(TAG+" "+book.getBookCategory());
                holder.price.setText(Integer.toString(book.getPrice()));
                holder.bookName.setText(book.getName());
                holder.bookCategory.setText(book.getBookCategory());




        }

        @Override
        public int getItemCount()
        {

            return mDatas.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder
        {
            View parentView;
            TextView bookCategory;
            TextView price;
            TextView bookName;
            public MyViewHolder(View view)
            {

                super(view);
                parentView=view;
                System.out.println("4");
                bookCategory = (TextView) view.findViewById(R.id.bookcategory);
                bookName=(TextView)view.findViewById(R.id.bookname);
                price=(TextView)view.findViewById(R.id.price);
            }


        }
    }
}
