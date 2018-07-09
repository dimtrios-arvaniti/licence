package com.example.dim.licence.utils.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.dim.licence.VigneronActivity;

public class VigneronPagerAdapter extends FragmentStatePagerAdapter {

    private VigneronActivity parentActivity;
    public VigneronPagerAdapter(VigneronActivity activity, FragmentManager fm) {
        super(fm);
        parentActivity = activity;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return parentActivity.getListFragment();
        }

        if (position == 1) {
            return parentActivity.getDetailFragment();
        }

        if (position == 2) {
            return parentActivity.getEditFragment();
        }

        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return "Liste";
        }

        if (position == 1) {
            return "Detail";
        }

        if (position == 2) {
            return parentActivity.isNewMode() ? "Nouveau" : "Edition";
        }

        return "";
    }

    /**
     * @see android.support.v4.view.PagerAdapter#getCount()
     */
    @Override
    public int getCount() {
        return (parentActivity.getCurrentPage() + 1);
    }

    /**
     * @see android.support.v4.view.PagerAdapter#getItemPosition(java.lang.Object)
     */
    @Override
    public int getItemPosition(Object object) {
// 1 page
        if (parentActivity.getCurrentPage() == 0) {
            if (object.getClass().getSimpleName().equalsIgnoreCase("VigneronListFragment")) {
                return 0;
            } else {
                return POSITION_NONE;
            }
        }
// 2 pages
        if (parentActivity.getCurrentPage() == 1) {
            if (object.getClass().getSimpleName().equalsIgnoreCase("VigneronListFragment")) {
                return 0;
            } else if (object.getClass().getSimpleName().equalsIgnoreCase("VigneronDetailFragment")) {
                return 1;
            } else {
                return POSITION_NONE;
            }
        }
// 3 pages
        if (parentActivity.getCurrentPage() == 2) {
            if (object.getClass().getSimpleName().equalsIgnoreCase("VigneronListFragment")) {
                return 0;
            }

            if (object.getClass().getSimpleName().equalsIgnoreCase("VigneronDetailFragment")) {
                return 1;
            }

            if (object.getClass().getSimpleName().equalsIgnoreCase("VigneronEditFragment")) {
                return 2;
            }
        }

        return POSITION_NONE;
    }



}
