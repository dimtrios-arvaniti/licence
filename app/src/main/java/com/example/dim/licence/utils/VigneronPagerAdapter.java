package com.example.dim.licence.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.example.dim.licence.vignerons.VigneronActivity;

import static com.example.dim.licence.MainActivity.ARG_DEBUG;

public class VigneronPagerAdapter extends FragmentStatePagerAdapter {

    private VigneronActivity parentActivity;

    public VigneronPagerAdapter(VigneronActivity activity, FragmentManager fm) {
        super(fm);
        parentActivity = activity;

    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return parentActivity.getVigneronListFragment();
        }

        if (position == 1) {
                return parentActivity.getVigneronDetailFragment();
        }

        if (position == 2) {
                return parentActivity.getVigneronEditFragment();
        }


/*        if (position == 2) {
            if (parentActivity.isDetailMode()) {
                return parentActivity.getDetailFragment();
            }
            if (parentActivity.isNewMode()) {
                return parentActivity.getEditFragment();
            }

        }

        if (position == 3) {
            if (parentActivity.isEditMode()) {
                return parentActivity.getEditFragment();
            }
        }*/
        return null;
    }

    public Fragment getPage(int position) {
        return getItem(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0){
            return "Vignerons";
        }

        if (position == 1) {
            return "Detail";
        }

        if (position == 2) {
            return parentActivity.isNewMode() ? "Nouveau" : "Edition";
        }

        return  "";
    }


    /**
     * @see android.support.v4.view.PagerAdapter#getCount()
     */
    @Override
    public int getCount() {
        return parentActivity.isDetailMode() ? 2
                : parentActivity.isNewMode() ? 2
                : parentActivity.isEditMode() ? 3
                : 1;
    }

    /**
     * @see android.support.v4.view.PagerAdapter#getItemPosition(java.lang.Object)
     */
    @Override
    public int getItemPosition(Object object) {

        if (object.getClass().getSimpleName().equalsIgnoreCase("VigneronListFragment")) {
            return 0;
        }
        if (object.getClass().getSimpleName().equalsIgnoreCase("VigneronDetailFragment")) {
            return 1;
        }

        if (object.getClass().getSimpleName().equalsIgnoreCase("VigneronEditFragment")) {
                return (parentActivity.isNewMode() ? 1 : 2);
        }

        Log.i(ARG_DEBUG, "getItemPosition: POSITION == NONE");
        /*if (object.getClass().getSimpleName().equalsIgnoreCase("GrapeDetailFragment")) {
            if (parentActivity.isDetailMode()) {
                return 2;
            }
        }
        if (object.getClass().getSimpleName().equalsIgnoreCase("GrapeEditFragment")) {
            if (parentActivity.isNewMode()) {
                return 2;
            }
            if (parentActivity.isEditMode()) {
                return 3;
            }
        }*/
        return POSITION_NONE;
    }
}
