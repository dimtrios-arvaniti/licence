package com.example.dim.licence.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.dim.licence.vignerons.VigneronActivity;

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
        return "Liste";/*(position == 0) ? "hello !" :
                (position == 1) ? "Liste" :
                        (position == 3) ? "Edition" :
                                (position == 2) ? (parentActivity.isNewMode()) ? "Creation" : "Detail"
                                        : "";*/
    }


    /**
     * @see android.support.v4.view.PagerAdapter#getCount()
     */
    @Override
    public int getCount() {
        /*return parentActivity.isEditMode() ? 4
                : parentActivity.isDetailMode() ? 3
                : parentActivity.isNewMode() ? 3
                : 2;*/
        return parentActivity.isDetailMode() ? 2
                : 1;
    }

    /**
     * @see android.support.v4.view.PagerAdapter#getItemPosition(java.lang.Object)
     */
    @Override
    public int getItemPosition(Object object) {
       /* if (object.getClass().getSimpleName().equalsIgnoreCase("MainFragment")) {
            return 0;
        }*/
        if (object.getClass().getSimpleName().equalsIgnoreCase("VigneronListFragment")) {
            return 0;
        }
        if (object.getClass().getSimpleName().equalsIgnoreCase("VigneronDetailFragment")) {
            return 1;
        }
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
