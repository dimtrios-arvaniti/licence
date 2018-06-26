package com.example.dim.licence.vignerons;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.SimpleOnPageChangeListener;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.example.dim.licence.R;
import com.example.dim.licence.entities.Vigneron;
import com.example.dim.licence.models.ApplicationModel;
import com.example.dim.licence.utils.DetailFragmentInterface;
import com.example.dim.licence.utils.EditFragmentInterface;
import com.example.dim.licence.utils.VigneronPagerAdapter;

import static com.example.dim.licence.MainActivity.ARG_DEBUG;

public class VigneronActivity extends AppCompatActivity
        implements DetailFragmentInterface<Vigneron>,
        EditFragmentInterface<Vigneron> {

    public static final String V_SELECTED = "V_SELECTED";
    public static final String V_ = "V_";
    public static final String V_COUNT = "V_COUNT";

    private Bundle vigneronsBundle; // replace by list
    private ApplicationModel model;
    private Vigneron v_selected;

    private VigneronDetailFragment vigneronDetailFragment;
    private VigneronListFragment vigneronListFragment;
    private VigneronEditFragment vigneronEditFragment;

    private boolean editMode;
    private boolean detailMode;
    private boolean newMode;

    private Button btnCreate;
    private Button btnSort;
    private Button btnEdit;

    private VigneronPagerAdapter pagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vigneron);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        model = ApplicationModel.getInstance(getApplicationContext());
        vigneronsBundle = new Bundle();

        int vIndex = 1;
        for (Vigneron v : model.getVigneronDAO().getAll(model.getReadableDatabase())) {
            vigneronsBundle.putBundle(V_ + vIndex, v.entityToBundle());
            //Vigneron rv = new Vigneron();
            //rv.entityFromBundle(vigneronsBundle.getBundle(V_ + vIndex));
            //Log.i(ARG_DEBUG, "onCreate: "+ rv.toString());
            vIndex += 1;
        }
        vigneronsBundle.putInt(V_COUNT, vIndex);
        vigneronsBundle.putInt(V_SELECTED, -1);


        btnCreate = findViewById(R.id.btnCreate);
        btnSort = findViewById(R.id.btnSort);
        btnEdit = findViewById(R.id.btnEdit);
        btnCreate.setVisibility(View.VISIBLE);
        btnCreate.setEnabled(true);
        btnEdit.setVisibility(View.INVISIBLE);
        btnEdit.setEnabled(false);

        vigneronListFragment = VigneronListFragment.newInstance(vigneronsBundle);
        vigneronDetailFragment = VigneronDetailFragment.newInstance();
        vigneronEditFragment = VigneronEditFragment.newInstance();

        pagerAdapter = new VigneronPagerAdapter(VigneronActivity.this, getSupportFragmentManager());
        mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(pagerAdapter);
        mViewPager.addOnPageChangeListener(pageChangeListener);
    }

    @Override
    public void updateDetailFragment(Vigneron item) {
        detailMode = true;
        v_selected = item;
        Log.i(ARG_DEBUG, "updateDetailFragment: "+v_selected.toString());
        pagerAdapter.notifyDataSetChanged();
        vigneronDetailFragment.updateDetailFragment(v_selected);
        pagerAdapter.getPage(1);
        mViewPager.setCurrentItem(1);
        Log.i(ARG_DEBUG, "updateDetailFragment: PASSED !");
    }

    private SimpleOnPageChangeListener pageChangeListener = new SimpleOnPageChangeListener() {
        @Override
        public void onPageSelected(int position) {
            super.onPageSelected(position);

            if (position == 0) {
                btnCreate.setVisibility(View.VISIBLE);
                btnCreate.setEnabled(true);
                btnEdit.setVisibility(View.INVISIBLE);
                btnEdit.setEnabled(false);
                detailMode = false;
                newMode = false;

                btnCreate.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.i(ARG_DEBUG, "onClick: CLICKED CREATE");
                        newMode = true;
                        v_selected = new Vigneron();
                        updateDetailFragment(v_selected);
                        updateEditFragment(v_selected);
                    }
                });
                return;
            }

                if (position == 1) {
                    if (detailMode){
                        btnCreate.setVisibility(View.INVISIBLE);
                        btnCreate.setEnabled(false);
                        btnEdit.setVisibility(View.VISIBLE);
                        btnEdit.setEnabled(true);

                    btnEdit.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Log.i(ARG_DEBUG, "onClick: CLICKED EDIT");
                            editMode = true;
                            Log.i(ARG_DEBUG, "onClick: "+v_selected.toString());
                            updateDetailFragment(v_selected);
                            updateEditFragment(v_selected);
                        }
                    });
                    return;
                }

                if (newMode) {
                    Log.i(ARG_DEBUG, "onPageSelected: new mode to do !");
                    pagerAdapter.notifyDataSetChanged();
                    return;
                }
            }

            if (position == 2) {
                pagerAdapter.notifyDataSetChanged();
                return;
            }

/*
            if (detailMode) {
                if (position == 2) {
                    add.hide();
                    sort.hide();
                    save.hide();
                    cancel.hide();
                    remove.show();
                    remove.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            applicationModel.getDatabase().deleteGrape(selectedGrape.getTypeVinId());
                            updateRecyclerAdpater(selectedGrape, true);
                            mViewPager.setCurrentItem(1);
                        }
                    });
                    edit.show();
                    edit.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            updateEditFragment(selectedGrape);
                        }
                    });
                    editMode = false;
                    mSectionsPagerAdapter.notifyDataSetChanged();
                    return;
                }
            }

            if (newMode) {
                if (position == 2) {
                    add.hide();
                    sort.hide();
                    remove.hide();
                    edit.hide();
                    save.show();
                    save.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            long id = applicationModel.getDatabase().insertTypeVin(newOrModified);
                            newOrModified.setTypeVinId(Integer.valueOf(String.valueOf(id)));
                            Log.i(ARG_DEBUG, "onClick: SAVE! " + newOrModified.toString());
                            updateRecyclerAdpater(newOrModified, false);
                            mViewPager.setCurrentItem(1);
                        }
                    });
                    cancel.show();
                    mSectionsPagerAdapter.notifyDataSetChanged();
                    return;
                }
            }

            if (editMode) {
                if (position == 3) {
                    add.hide();
                    sort.hide();
                    remove.hide();
                    edit.hide();
                    save.show();
                    save.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (newOrModified.equals(selectedGrape)) {
                                Toast.makeText(MainActivity.this, "Aucun changement Ã  sauvegarder", Toast.LENGTH_LONG).show();
                            } else {
                                newOrModified.setTypeVinId(selectedGrape.getTypeVinId());
                                applicationModel.getDatabase().updateTypeVin(newOrModified);
                                Log.i(ARG_DEBUG, "onClick: UPDATE ! " + newOrModified.toString());
                                updateRecyclerAdpater(newOrModified, false);
                                updateDetailFragment(newOrModified);
                                mViewPager.setCurrentItem(2);
                            }
                        }
                    });
                    cancel.show();
                }
            }*/
        }
    };

    @Override
    public void updateActivity(Vigneron item) {
        v_selected = item;
    }


    @Override
    public void updateEditFragment(Vigneron item) {
        Log.i(ARG_DEBUG, "updateEditFragment: isNew ? " + newMode);
        if (newMode) {

            pagerAdapter.notifyDataSetChanged();
            vigneronEditFragment.updateEditFragment(v_selected);
            pagerAdapter.getPage(2);
            mViewPager.setCurrentItem(2);
            return;
        }

        if (editMode){
            pagerAdapter.notifyDataSetChanged();
            vigneronEditFragment.updateEditFragment(v_selected);
            pagerAdapter.getPage(2);
            mViewPager.setCurrentItem(2);
            return;
        }
    }


    public Vigneron getV_selected() {
        return v_selected;
    }

    public VigneronListFragment getVigneronListFragment() {
        return vigneronListFragment;
    }

    public VigneronDetailFragment getVigneronDetailFragment() {
        return vigneronDetailFragment;
    }

    public VigneronEditFragment getVigneronEditFragment() {
        return vigneronEditFragment;
    }

    public boolean isEditMode() {
        return editMode;
    }

    public boolean isDetailMode() {
        return detailMode;
    }

    public boolean isNewMode() {
        return newMode;
    }
}
