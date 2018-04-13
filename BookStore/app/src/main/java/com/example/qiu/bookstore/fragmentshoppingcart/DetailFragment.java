package com.example.qiu.bookstore.fragmentshoppingcart;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.qiu.bookstore.R;

/**
 * Created by qiu on 2018/4/11.
 */

public class DetailFragment extends Fragment {
    private View mView;

    public DetailFragment() {
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(this.mView == null) {
            this.mView = inflater.inflate(R.layout.fragment_detail, container, false);
        }

        return this.mView;
    }

    public void onDestroyView() {
        super.onDestroyView();
        ((ViewGroup)this.mView.getParent()).removeView(this.mView);
    }
}
