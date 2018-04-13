package com.example.qiu.bookstore;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.CharacterPickerDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.qiu.bookstore.fragmentshoppingcart.FragementShoppingCartMain;
import com.example.qiu.bookstore.fragmentshoppingcart.ShoppingCartFragment;
import com.example.qiu.bookstore.models.Book;
import com.example.qiu.bookstore.models.BookAndUserId;
import com.example.qiu.bookstore.models.ResponseUserController;
import com.example.qiu.bookstore.models.SearchResponse;
import com.example.qiu.bookstore.models.ShoppingCart;
import com.example.qiu.bookstore.models.UserAndResponse;
import com.example.qiu.bookstore.myutil.BookAndUserIdManage;
import com.example.qiu.bookstore.network.BookService;
import com.example.qiu.bookstore.network.RRetrofit;

import java.text.SimpleDateFormat;
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
    private final String TAG = "showBook";
    private TextView textViewForUserName;
    private ImageButton buttonForShowShoppingCart;
    private ImageButton buttonToLogin;
    private ImageButton buttonForSearch;
    private EditText editTextForSearchStr;
    private SwipeRefreshLayout mRefreshLayout;
    UserAndResponse userAndResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("test1");

        userAndResponse = (UserAndResponse) getIntent().getSerializableExtra("userAndResponse");

        setContentView(R.layout.activity_show_book);
        mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.layout_swipe_refresh);

        textViewForUserName = (TextView) findViewById(R.id.textforshowusername);
        textViewForUserName.setText(userAndResponse.getName());
        buttonToLogin =(ImageButton)findViewById(R.id.buttontologin);
        buttonToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ShowBook.this,MainActivity.class);
                intent.putExtra("flag",true);
                startActivity(intent);
            }
        });
        //点击按钮进入购物车
        buttonForShowShoppingCart = (ImageButton) findViewById(R.id.buttonforshowshoppingcart);
        buttonForShowShoppingCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowBook.this, ShowShoppingCart.class);
                intent.putExtra("userId", userAndResponse.getId());
                startActivity(intent);
            }
        });
        //点击进行搜索
        buttonForSearch = (ImageButton) findViewById(R.id.button_for_search);
        buttonForSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextForSearchStr = (EditText) findViewById(R.id.edittext_for_search);
                String strForSearch = editTextForSearchStr.getText().toString();
                System.out.println(TAG + " " + "strForSearch" + " " + strForSearch);
                searchData(strForSearch);
            }
        });
        Log.d(TAG, "text");
        initData();
        //刷新recycleview
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();
                //mAdapter.notifyDataSetChanged();
                mRefreshLayout.setRefreshing(false);
            }
        });

    }

    //获取搜索的数据
    void searchData(String strForSearch) {
        BookService bookService = RRetrofit.create(BookService.class);
        bookService.searchBookByPublishingHouseOrBookName(strForSearch)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<SearchResponse>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "搜索结束");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "搜索出现错误");
                    }

                    @Override
                    public void onNext(SearchResponse searchResponse) {
                        List<Book> books = new ArrayList<Book>();
                        Book book = new Book();
                        System.out.println(TAG + " " + "获取课本");
                        Iterator<Book> iterator = searchResponse.getSearchByPublishName().iterator();
                        while (iterator.hasNext()) {
                            book = iterator.next();
                            books.add(book);
                            System.out.println(TAG + " " + book.getName() + " " + "名字");
                        }
                        iterator = searchResponse.getSearchByBookName().iterator();
                        while (iterator.hasNext()) {
                            book = iterator.next();
                            books.add(book);
                            System.out.println(TAG + " " + book.getName() + " " + "名字");
                        }
                        mDatas = books;
                        mRecyclerView = (RecyclerView) findViewById(R.id.id_recyclerview);
                        mRecyclerView.setLayoutManager(new LinearLayoutManager(ShowBook.this));
                        mRecyclerView.setAdapter(mAdapter = new HomeAdapter());
                    }
                });
    }

    //获取所有数据
    protected void initData() {
        BookService bookservice = RRetrofit.create(BookService.class);
        bookservice.getAllBook()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Book>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "出现错误");
                        Log.d(TAG, e.getMessage());
                    }

                    @Override
                    public void onNext(List<Book> books) {
                        Log.d(TAG, "进到onnext函数");
                        mDatas = books;
                        mRecyclerView = (RecyclerView) findViewById(R.id.id_recyclerview);
                        mRecyclerView.setLayoutManager(new LinearLayoutManager(ShowBook.this));
                        mRecyclerView.setAdapter(mAdapter = new HomeAdapter());
                    }
                });

    }

    //使用adapter，recycleview专用
    class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {

        @Override
        public MyViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
            System.out.println("1");
            final MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                    parent.getContext()).inflate(R.layout.item_home, parent,
                    false));

            //点击按钮触发事件
            holder.parentView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int position = holder.getAdapterPosition();
                    Book book = mDatas.get(position);

                    System.out.println(TAG + " " + "发送数据出错");
                    BookService bookService = RRetrofit.create(BookService.class);
                    bookService.addclick(book)
                            .subscribeOn(Schedulers.newThread())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Subscriber<ResponseUserController>() {
                                @Override
                                public void onCompleted() {

                                }

                                @Override
                                public void onError(Throwable e) {
                                    System.out.println(TAG + "! " + e.getMessage());
                                }

                                @Override
                                public void onNext(ResponseUserController responseUserController) {
                                    System.out.println(TAG + "!! " + responseUserController.getStatus());
                                }
                            });



                    Intent intent = new Intent(ShowBook.this, FragementShoppingCartMain.class);
                    BookAndUserId bookAndUserId=changeBookToBookAndUserId(book,userAndResponse.getId());
                    BookAndUserIdManage.getInstance().saveBookAndUserIdInfo(ShowBook.this,bookAndUserId);
//                    Bundle bundle = new Bundle();
//                    bundle.putSerializable("bookFromShowBook", book);
//
//                    intent.putExtras(bundle);
//                    System.out.println(TAG + " " + "userId" + userAndResponse.getId());
//                    intent.putExtra("userId", userAndResponse.getId());

//                    ShoppingCartFragment myFragment = new ShoppingCartFragment();
//                    Bundle bundle1 = new Bundle();
//                    bundle1.putSerializable("bookFromShowBook",book);//这里的values就是我们要传的值
//                    bundle1.putInt("userId",userAndResponse.getId());
//                    myFragment.setArguments(bundle1);
//                    getSupportFragmentManager().beginTransaction().commit();


                    startActivity(intent);

                }
            });
            return holder;
        }
        public BookAndUserId changeBookToBookAndUserId(Book book,int userId){
            BookAndUserId bookAndUserId=new BookAndUserId();
            bookAndUserId.setUserId(userId);
            bookAndUserId.setPublishHouse(book.getPublishingHouse());
            bookAndUserId.setPrice(book.getPrice());
            bookAndUserId.setName(book.getName());
            bookAndUserId.setBookId(book.getId());
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            String publishingDateTemp= format.format(book.getPublishingDate()).toString();
            bookAndUserId.setPublishDate(publishingDateTemp);
            return bookAndUserId;
        }
        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            System.out.println("2");
            Book book = mDatas.get(position);


            System.out.println(TAG + " " + book.getId());
            System.out.println(TAG + " " + book.getName());
            System.out.println(TAG + " " + book.getPrice());
            System.out.println(TAG + " " + book.getBookCategory());
            holder.price.setText(Integer.toString(book.getPrice()));
            holder.bookName.setText(book.getName());
            holder.bookCategory.setText(book.getBookCategory());
            System.out.println("书本是否有活动 "+book.getIsActivity());
             if(book.getIsActivity()==1){
                 System.out.println("变颜色");
                 holder.price.setTextColor(Color.RED);
             }

        }

        @Override
        public int getItemCount() {

            return mDatas.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            View parentView;
            TextView bookCategory;
            TextView price;
            TextView bookName;

            public MyViewHolder(View view) {

                super(view);
                parentView = view;
                System.out.println("4");
                bookCategory = (TextView) view.findViewById(R.id.bookcategory);
                bookName = (TextView) view.findViewById(R.id.bookname);
                price = (TextView) view.findViewById(R.id.price);
            }


        }
    }
}
