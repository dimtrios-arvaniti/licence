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

public class VigneronActivity extends AppCompatActivity implements DetailFragmentInterface<Vigneron>, EditFragmentInterface<Vigneron> {

    public static final String V_SELECTED = "V_SELECTED";
    public static final String V_ = "V_";
    public static final String V_COUNT = "V_COUNT";
    private Bundle vigneronsBundle;


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
        btnEdit.setVisibility(View.INVISIBLE);

        vigneronListFragment = VigneronListFragment.newInstance(vigneronsBundle);
        vigneronDetailFragment = VigneronDetailFragment.newInstance();
        vigneronEditFragment = VigneronEditFragment.newInstance();

        pagerAdapter = new VigneronPagerAdapter(VigneronActivity.this, getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(pagerAdapter);
        mViewPager.addOnPageChangeListener(pageChangeListener);
    }

    @Override
    public void updateDetailFragment(Vigneron item) {
        detailMode = true;
        v_selected = item;
        vigneronsBundle.putBundle(V_SELECTED, v_selected.entityToBundle());
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
                btnEdit.setVisibility(View.INVISIBLE);
                detailMode = false;
                newMode = false;
                pagerAdapter.notifyDataSetChanged();
                //btnCreate.show();
                btnCreate.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.i(ARG_DEBUG, "onClick: CLICK");
                        newMode = true;
                        v_selected = new Vigneron();
                        updateEditFragment(v_selected);
                    }
                });
                return;
            }

            if (detailMode) {
                if (position == 1) {
                    //detailMode = false;
                    btnEdit.setVisibility(View.VISIBLE);
                    btnCreate.setVisibility(View.INVISIBLE);
                    newMode = false;
                    pagerAdapter.notifyDataSetChanged();
                    return;
                }
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
            vigneronEditFragment.updateEditFragment(new Vigneron());
            pagerAdapter.getPage(1);
            mViewPager.setCurrentItem(1);
            return;
        } else {
            editMode = true;
            pagerAdapter.notifyDataSetChanged();
            vigneronEditFragment.updateEditFragment(item);

            pagerAdapter.getPage(1);
            mViewPager.setCurrentItem(1);
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

    public boolean isEditMode() {
        return editMode;
    }

    public boolean isDetailMode() {
        return detailMode;
    }
}
