package com.example.qiu.bookstore.myutil;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;

import com.example.qiu.bookstore.R;

/**
 * Created by qiu on 2018/3/20.
 */

public class PasswordToggleEditText extends android.support.v7.widget.AppCompatEditText implements View.OnFocusChangeListener,TextWatcher{
    private Drawable mToggleDrawable;
    public PasswordToggleEditText(Context context) {
        this(context, null);
    }
    public PasswordToggleEditText(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.editTextStyle);
    }
    public PasswordToggleEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }
    private void init() {
        //获取EditText的DrawableRight,主要是通过xml或者外部设置右边的按钮，如果没有设置就采用默认的
        mToggleDrawable = getCompoundDrawables()[2];
        if (mToggleDrawable == null) {
            mToggleDrawable = ContextCompat.getDrawable(getContext(), R.drawable.icon_toggle);
        }
        mToggleDrawable.setBounds(0, 0, mToggleDrawable.getIntrinsicWidth(), mToggleDrawable.getIntrinsicHeight());
        setToggleIconVisible(false);
        setOnFocusChangeListener(this);
        addTextChangedListener(this);
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (getCompoundDrawables()[2] != null) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                boolean touchable = event.getX() > (getWidth()
                        - getPaddingRight() - mToggleDrawable.getIntrinsicWidth())
                        && (event.getX() < ((getWidth() - getPaddingRight())));
                if (touchable) {
                    //显示密码明文
                    setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    postInvalidate();
                    CharSequence charSequence = getText();
                    //为了保证体验效果，需要保持输入焦点在文本最后一位
                    if (charSequence != null) {
                        Spannable spanText = (Spannable) charSequence;
                        Selection.setSelection(spanText, charSequence.length());
                    }
                }
            }else if(event.getAction() == MotionEvent.ACTION_UP){
                //隐藏密码明文
                setTransformationMethod(PasswordTransformationMethod.getInstance());
                postInvalidate();
                setSelection(getText().length());
            }
        }
        return super.onTouchEvent(event);
    }
    public void setToggleIconVisible(boolean visible) {
        Drawable right = visible ? mToggleDrawable : null;
        setCompoundDrawables(getCompoundDrawables()[0],
                getCompoundDrawables()[1], right, getCompoundDrawables()[3]);
    }
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        setToggleIconVisible(s.length() > 0);
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            setToggleIconVisible(getText().length() > 0);
        } else {
            setToggleIconVisible(false);
            setShakeAnimation();
        }
    }
    public void setShakeAnimation(){
        this.setAnimation(shakeAnimation(3));
    }
    public Animation shakeAnimation(int counts){
        Animation translateAnimation = new TranslateAnimation(0, 10, 0, 0);
        translateAnimation.setInterpolator(new CycleInterpolator(counts));
        translateAnimation.setDuration(500);
        return translateAnimation;
    }
}
