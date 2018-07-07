package com.example.dim.licence;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.NavigationView.OnNavigationItemSelectedListener;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager.SimpleOnPageChangeListener;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.dim.licence.dialogs.CaveDialogs;
import com.example.dim.licence.dialogs.VigneronDialogs;
import com.example.dim.licence.entities.Appelation;
import com.example.dim.licence.entities.Cave;
import com.example.dim.licence.entities.TypeVin;
import com.example.dim.licence.entities.Vigneron;
import com.example.dim.licence.fragments.CaveDetailFragment;
import com.example.dim.licence.fragments.CaveEditFragment;
import com.example.dim.licence.fragments.CaveListFragment;
import com.example.dim.licence.models.MasterModel;
import com.example.dim.licence.utils.adapters.CavePagerAdapter;
import com.example.dim.licence.utils.commons.CrudViewPager;
import com.example.dim.licence.utils.interfaces.CrudDialogsInterface;
import com.example.dim.licence.utils.interfaces.CrudFragmentInterface;

import java.util.List;

import static com.example.dim.licence.MainActivity.ARG_DEBUG;

public class CaveActivity extends AppCompatActivity
        implements CrudFragmentInterface<Cave>
        , CrudDialogsInterface<Cave> {

    /**
     * PARAM for selected item
     */
    public static final String C_SELECTED = "C_SELECTED";
    /**
     * PARAM for each data passed in list fragment bundle
     */
    public static final String C_ = "C_";
    /**
     * PARAM number of items in list
     */
    public static final String C_COUNT = "C_COUNT";
    /**
     * PARAM for selected filter on vignerons list
     */
    public static final String C_DIALOG_TYPE = "C_DIALOG_TYPE";
    /**
     * PARAM for vignerons list onContactClick type
     */
    public static final String C_FILTER_TYPE = "C_FILTER_TYPE";

    /**
     * Bundle containing data
     */
    private Bundle caveBundle;
    private Bundle ditionnaryBundle;
    /**
     * Accessor to DAO's
     */
    private MasterModel model;
    /**
     * Selected item
     */
    private Cave c_selected;
    /**
     * Current page index
     */
    private int currentPage;
    /**
     * ListFragment
     */
    private CaveListFragment listFragment;
    /**
     * Detail fragment
     */
    private CaveDetailFragment detailFragment;
    /**
     * Edition fragment
     */
    private CaveEditFragment editFragment;
    /**
     * View pager adapter for Vignerons Activity
     * Handles fragments navigation logic
     */
    private CavePagerAdapter pagerAdapter;
    /**
     * Container for managing CRUD fragments
     */
    private CrudViewPager mViewPager;
    /**
     * Utilise pour l'etat de l'UI
     */
    private boolean newMode;
    private DrawerLayout drawerMenu;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cave);

        // set toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_icon);
        actionbar.setTitle("Ma cave");

        mViewPager = findViewById(R.id.cave_vp_container);
        initCaveMenu(toolbar);
        // init before fragments set up !
        initCaveData();


        // setting up fragments
        listFragment = CaveListFragment.newInstance(caveBundle);
        detailFragment = CaveDetailFragment.newInstance();
        editFragment = CaveEditFragment.newInstance(ditionnaryBundle);

        // setting up page adapter
        pagerAdapter = new CavePagerAdapter(CaveActivity.this, getSupportFragmentManager());
        mViewPager.setAdapter(pagerAdapter);
        mViewPager.addOnPageChangeListener(pageChangeListener);


    }

    private void initCaveMenu(Toolbar toolbar) {
        drawerMenu = findViewById(R.id.cave_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerMenu, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerMenu.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.cave_nav_view);
        navigationView.setNavigationItemSelectedListener(new OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                item.setChecked(true);
                drawerMenu.closeDrawers();

                if (item.getItemId() == R.id.nav_cave) {
                    Intent intent = new Intent(getApplicationContext(), CaveActivity.class);
                    startActivity(intent);
                    return true;
                }

                if (item.getItemId() == R.id.nav_vignerons) {
                    Intent intent = new Intent(getApplicationContext(), VigneronActivity.class);
                    startActivity(intent);
                    return true;
                }

                return false;
            }
        });
    }

    /**
     * On toolbar item selection
     * Return true if selection is valid
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            drawerMenu.openDrawer(GravityCompat.START);
            return true;
        }

        return false;
    }

    private void initCaveData() {
        // init database access and DAO's
        model = MasterModel.getInstance(getApplicationContext());

        // init bundle for first launch of list fragment
        caveBundle = new Bundle();
        int vIndex = 0;
        List<Cave> list = model.getAllCave();
        if (list != null) {
            for (Cave cave : list) {
                // Log.i(ARG_DEBUG, "initCaveData: CAVE "+cave.getCaveId() +"\n"+ cave.toString());
                caveBundle.putBundle(C_ + vIndex, cave.entityToBundle());
                vIndex += 1;
            }
        }
        caveBundle.putInt(C_COUNT, vIndex);
        caveBundle.putInt(C_SELECTED, -1);
        caveBundle.putString(C_FILTER_TYPE, "AUCUN"); // remove line

        // DITIONNARY DATA FOR CAVE
        // appelation
        ditionnaryBundle = new Bundle();
        vIndex = 0;
        List<Appelation> appelations = model.getAllAppelations();
        if (appelations != null) {
            for (Appelation appelation : appelations) {
                ditionnaryBundle.putBundle("A_" + vIndex, appelation.entityToBundle());
                vIndex += 1;
            }
        }
        ditionnaryBundle.putInt("A_COUNT", vIndex);
        ditionnaryBundle.putInt("A_SELECTED", -1);

        // typevin
        vIndex = 0;
        List<TypeVin> typeVins = model.getAllTypeVins();
        if (typeVins != null) {
            for (TypeVin typeVin : typeVins) {
                ditionnaryBundle.putBundle("TV_" + vIndex, typeVin.entityToBundle());
                vIndex += 1;
            }
        }
        ditionnaryBundle.putInt("TV_COUNT", vIndex);
        ditionnaryBundle.putInt("TV_SELECTED", -1);

        // typevin
        vIndex = 0;
        List<Vigneron> vignerons = model.getAllVignerons();
        if (vignerons != null) {
            for (Vigneron vigneron : vignerons) {
                ditionnaryBundle.putBundle("V_" + vIndex, vigneron.entityToBundle());
                vIndex += 1;
            }
        }
        ditionnaryBundle.putInt("V_COUNT", vIndex);
        ditionnaryBundle.putInt("V_SELECTED", -1);

        // init some settings for pager
        newMode = false;
        currentPage = 0;
    }

    /**
     * Needed to detect page change on swipe back
     */
    private SimpleOnPageChangeListener pageChangeListener = new SimpleOnPageChangeListener() {
        @Override
        public void onPageSelected(int position) {
            currentPage = position;
            pagerAdapter.notifyDataSetChanged();
        }
    };

    /**
     * On list fragment new button click
     */
    @Override
    public void onNewClick() {
        c_selected = new Cave();
        newMode = true;

        currentPage = 2;
        pagerAdapter.notifyDataSetChanged();
        mViewPager.setCurrentItem(currentPage);

        // fragments update
        editFragment.updateFragment(c_selected);
        // force user to click back or cancel on edition mode
        mViewPager.disablePaging();
    }

    /**
     * Updates detail fragment.
     *
     * @param item select item from list fragment.
     */
    @Override
    public void updateDetailFragment(Cave item) {
        c_selected = item;

        currentPage = 1;
        pagerAdapter.notifyDataSetChanged();

        detailFragment.updateFragment(c_selected);
        mViewPager.setCurrentItem(currentPage, true);
    }


    @Override
    public void updateEditFragment(Cave item) {
        c_selected = item;

        currentPage = 2;
        pagerAdapter.notifyDataSetChanged();

        editFragment.updateFragment(c_selected);
        mViewPager.setCurrentItem(currentPage, true);
        mViewPager.disablePaging();
    }

    @Override
    public void onDeleteClick() {
        Bundle b = new Bundle();
        b.putString(C_DIALOG_TYPE, "DELETE");
        b.putString(C_SELECTED, c_selected.getCaveVin().getVinLibelle());
        CaveDialogs saveDialog = CaveDialogs.newInstance(b);
        saveDialog.show(getSupportFragmentManager(), "saveBtnDialog");
    }


    @Override
    public void onSaveClick(Cave item) {
        if (isNewMode()) {
            c_selected = item;
            saveNew();
        } else {
            Bundle b = new Bundle();
            b.putString(C_DIALOG_TYPE, "SAVE");
            b.putBundle(C_SELECTED, item.entityToBundle());
            Log.i(ARG_DEBUG, "onSaveClick: ----------------------------------- " + item.getCaveVin().getVinImage());
            CaveDialogs saveDialog = CaveDialogs.newInstance(b);
            saveDialog.show(getSupportFragmentManager(), "saveBtnDialog");
        }
    }

    @Override
    public void onFilterClick(String currentFilterType) {
        Bundle b = new Bundle();
        b.putString(C_DIALOG_TYPE, "FILTER");
        b.putString(C_FILTER_TYPE, currentFilterType);
        VigneronDialogs vigneronDialogs = VigneronDialogs.newInstance(b);
        vigneronDialogs.show(getSupportFragmentManager(), "filterDialog");
    }


    @Override
    public void onBackPressed() {
        if (currentPage > 0) {
            if (currentPage == 2) {
                Bundle b = new Bundle();
                b.putString(C_DIALOG_TYPE, "CANCEL");
                CaveDialogs caveDialogs = CaveDialogs.newInstance(b);
                caveDialogs.show(getSupportFragmentManager(), "cancelBtnDialog");
            } else {
                currentPage -= 1;
                goToPage(currentPage);
            }
        } else {
            super.onBackPressed();
        }
    }

    public void onQuantityChange(Cave cave, boolean plus) {
        // retrieve qty value
        int qty = cave.getCaveQuantite();
        // change accordingly
        if (plus) {
            qty += 1;
        } else {
            qty -= 1;
        }
        // setting modified value back
        cave.setCaveQuantite(qty);

        // save modified value
        model.updateCave(cave);

        // update view
        listFragment.updateRecyclerAdapter();
    }

    public void addNewVigneronToDictionary(Vigneron vigneron) {
        int vigneronsCount = ditionnaryBundle.getInt("V_COUNT");
        vigneronsCount += 1;

        ditionnaryBundle.putBundle("V_" + vigneronsCount, vigneron.entityToBundle());
    }

    // from dialogs
    @Override
    public void delete() {
        model.deleteCave(c_selected);
        Log.i(ARG_DEBUG, "delete: newMode = " + newMode);
        goToPage(0);
        listFragment.updateRecyclerAdapter(c_selected, true, newMode);
    }

    @Override
    public void applyFilter(String filter, String value) {
        listFragment.applyFilter(filter, value);
    }

    @Override
    public void save(Cave cave) {
        c_selected = cave;
        Log.i(ARG_DEBUG, "save: UPDATE= " + c_selected.toString());
        model.updateCave(c_selected);
        Log.i(ARG_DEBUG, "save: SAVED ITEM = " + c_selected.toString());
        Log.i(ARG_DEBUG, "save: newMode = " + newMode);
        goToPage(1);
        listFragment.updateRecyclerAdapter(c_selected, false, newMode);
    }

    public void saveNew() {
        Log.i(ARG_DEBUG, "saveNew: NEW =" + c_selected.toString());
        model.insertCave(c_selected);

        Log.i(ARG_DEBUG, "saveNew: SAVED NEW ITEM = " + c_selected.toString());
        Log.i(ARG_DEBUG, "saveNew: newMode = " + newMode);
        goToPage(1);
        listFragment.updateRecyclerAdapter(c_selected, false, newMode);
    }

    @Override
    public void goToPage(int page) {
        handlePageState(page);
        pagerAdapter.notifyDataSetChanged();
        handlePageNavigation();
    }


    private void handlePageNavigation() {
        if (currentPage == 0) {
            listFragment.updateRecyclerAdapter();
        }
        if (currentPage == 1) {
            updateDetailFragment(c_selected);
        }
        if (currentPage == 2) {
            updateEditFragment(c_selected);
        }
        mViewPager.setCurrentItem(currentPage, false);
    }

    private void handlePageState(int page) {
        currentPage = page;
        if (isNewMode() && currentPage != 2) {
            newMode = false;
        }
        if (currentPage != 2) {
            enablePaging();
        } else {
            mViewPager.disablePaging();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_vigneron, menu);

        return true;
    }


    /*
            @Override
            protected void onSaveInstanceState(Bundle outState) {
                super.onSaveInstanceState(outState);

                outState.putBundle("CAVE_BUNDLE", caveBundle);
                outState.putBundle("DICO_BUNDLE", ditionnaryBundle);
                outState.putInt("CAVE_PAGE", currentPage);
                outState.putBoolean("CAVE_NEW_MODE", newMode);
                outState.putBundle("CAVE_SELECTED", c_selected.entityToBundle());
            }

            @Override
            protected void onRestoreInstanceState(Bundle savedInstanceState) {
                super.onRestoreInstanceState(savedInstanceState);
                if (savedInstanceState != null) {
                    currentPage = savedInstanceState.getInt("CAVE_PAGE", 0);
                    c_selected = new Cave(savedInstanceState.getBundle("CAVE_SELECTED"));
                    caveBundle = savedInstanceState.getBundle("CAVE_BUNDLE");
                    ditionnaryBundle = savedInstanceState.getBundle("DICO_BUNDLE");
                    newMode = savedInstanceState.getBoolean("CAVE_NEW_MODE");
                }
            }
        */
    public void enablePaging() {
        mViewPager.enablePaging();
    }

    public CaveListFragment getListFragment() {
        return listFragment;
    }

    public CaveDetailFragment getDetailFragment() {
        return detailFragment;
    }

    public CaveEditFragment getEditFragment() {
        return editFragment;
    }

    public boolean isNewMode() {
        return newMode;
    }

    public int getCurrentPage() {
        return currentPage;
    }
}
