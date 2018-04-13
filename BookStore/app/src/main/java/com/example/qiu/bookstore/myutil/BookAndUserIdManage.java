package com.example.qiu.bookstore.myutil;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.example.qiu.bookstore.models.Book;
import com.example.qiu.bookstore.models.BookAndUserId;

import java.util.Date;

/**
 * Created by qiu on 2018/4/12.
 */

public class BookAndUserIdManage {
    private static BookAndUserIdManage instance;
    private BookAndUserIdManage(){

    }
    public static BookAndUserIdManage getInstance(){
        if(instance==null){
            instance=new BookAndUserIdManage();
        }
        return instance;
    }
    public void saveBookAndUserIdInfo(Context context, BookAndUserId bookAndUserId){
        SharedPreferences sp=context.getSharedPreferences("BOOKANDUSERID",context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putInt("USERID",bookAndUserId.getUserId());
        editor.putInt("PRICE",bookAndUserId.getPrice());
        editor.putString("PUBLISHING_HOUSE",bookAndUserId.getPublishHouse());
        editor.putString("PUBLISHING_DATE",bookAndUserId.getPublishDate());
        editor.putString("BOOKNAME",bookAndUserId.getName());
        editor.putInt("BOOKID",bookAndUserId.getBookId());
        editor.commit();
    }
    public BookAndUserId getBookAndUserIdInfo(Context context){
        SharedPreferences sp=context.getSharedPreferences("BOOKANDUSERID",context.MODE_PRIVATE);
        BookAndUserId bookAndUserId=new BookAndUserId();
        bookAndUserId.setName(sp.getString("BOOKNAME",""));
        bookAndUserId.setPrice(sp.getInt("PRICE",0));
        bookAndUserId.setPublishDate(sp.getString("PUBLISHING_DATE",""));
        bookAndUserId.setPublishHouse(sp.getString("PUBLISHING_HOUSE",""));
        bookAndUserId.setUserId(sp.getInt("USERID",0));
        bookAndUserId.setBookId(sp.getInt("BOOKID",0));
        return bookAndUserId;
    }
    public boolean hasBookAndUserIdInfo(Context context){
        BookAndUserId bookAndUserId=getBookAndUserIdInfo(context);
        if(bookAndUserId!=null){
            if((!TextUtils.isEmpty(bookAndUserId.getName()))&&(!TextUtils.isEmpty(bookAndUserId.getPublishDate()))&&bookAndUserId.getPrice()!=0
                    &&(!TextUtils.isEmpty(bookAndUserId.getPublishHouse()))&&bookAndUserId.getUserId()!=0){
                return true;
            }else {
                return false;
            }
        }
        return false;
    }

}
