package com.example.dim.licence.utils.uicustoms;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import static com.example.dim.licence.MainActivity.ARG_DEBUG;


/**
 * Used to change background of FloatingActionButton.
 *
 * @Deprecated Since background can't be changed in FloatingActionButton ..
 */
@Deprecated
public class FavoriteFloatingButton extends FloatingActionButton{

    private boolean favSelected;
    private Drawable favOnDrawable;
    private Drawable favOffDrawable;
    private OnClickListener favListener = new OnClickListener() {
        @Override
        public void onClick(View view) {
            favSelected = !favSelected; // reverse boolean value
            if (favSelected) {
                setBackground(favOnDrawable);
            } else {
                setBackground(favOffDrawable);
            }
            Log.i(ARG_DEBUG, "onClick: After logic reverse - IS FAVORITE "+ favSelected);
        }
    };

    public FavoriteFloatingButton(Context context) {
        super(context);
        favSelected = false;
        favOnDrawable = context.getResources().getDrawable(android.R.drawable.btn_star_big_on);
        favOffDrawable = context.getResources().getDrawable(android.R.drawable.btn_star_big_off);
        setOnClickListener(favListener);
        setBackground(favOffDrawable);
    }

    public FavoriteFloatingButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        favSelected = false;
        favOnDrawable = context.getResources().getDrawable(android.R.drawable.btn_star_big_on);
        favOffDrawable = context.getResources().getDrawable(android.R.drawable.btn_star_big_off);
        setOnClickListener(favListener);
        setBackground(favOffDrawable);
    }

    public FavoriteFloatingButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        favSelected = false;
        favOnDrawable = context.getResources().getDrawable(android.R.drawable.btn_star_big_on);
        favOffDrawable = context.getResources().getDrawable(android.R.drawable.btn_star_big_off);
        setOnClickListener(favListener);
        setBackground(favOffDrawable);
    }

    public boolean isFavSelected() {
        return favSelected;
    }

    public void setFavSelected(boolean value) {
        favSelected = value;
        if (favSelected) {
            setBackground(favOnDrawable);
        } else {
            setBackground(favOffDrawable);
        }
    }

    public Drawable getFavOnDrawable() {
        return favOnDrawable;
    }

    public void setFavOnDrawable(Drawable favOnDrawable) {
        this.favOnDrawable = favOnDrawable;
    }

    public Drawable getFavOffDrawable() {
        return favOffDrawable;
    }

    public void setFavOffDrawable(Drawable favOffDrawable) {
        this.favOffDrawable = favOffDrawable;
    }
}
