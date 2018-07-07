package com.example.dim.licence.utils.commons;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class CrudViewPager extends ViewPager{

    private boolean pagingEnable = true;

    public CrudViewPager(@NonNull Context context) {
        super(context);
    }

    public CrudViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return pagingEnable && super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return pagingEnable && super.onTouchEvent(ev);
    }

    public boolean isPagingEnable() {
        return pagingEnable;
    }

    public void enablePaging() {
        pagingEnable = true;
    }

    public void disablePaging() {
        pagingEnable = false;
    }
}
