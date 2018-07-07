package com.example.dim.licence.utils.uicustoms;

import android.content.Context;
import android.content.res.Resources.Theme;
import android.support.v7.widget.AppCompatSpinner;
import android.util.AttributeSet;

public class TypedSpinner<T> extends AppCompatSpinner {

    public TypedSpinner(Context context) {
        super(context);
    }

    public TypedSpinner(Context context, int mode) {
        super(context, mode);
    }

    public TypedSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TypedSpinner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public TypedSpinner(Context context, AttributeSet attrs, int defStyleAttr, int mode) {
        super(context, attrs, defStyleAttr, mode);
    }

    public TypedSpinner(Context context, AttributeSet attrs, int defStyleAttr, int mode, Theme popupTheme) {
        super(context, attrs, defStyleAttr, mode, popupTheme);
    }

    @Override
    public T getSelectedItem() {
        return (T)super.getSelectedItem();
    }
}
