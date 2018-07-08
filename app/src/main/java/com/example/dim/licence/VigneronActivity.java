package com.example.dim.licence;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
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
import android.view.MenuItem;
import android.widget.Toast;

import com.example.dim.licence.dialogs.VigneronDialogs;
import com.example.dim.licence.entities.Vigneron;
import com.example.dim.licence.fragments.VigneronDetailFragment;
import com.example.dim.licence.fragments.VigneronEditFragment;
import com.example.dim.licence.fragments.VigneronListFragment;
import com.example.dim.licence.models.MasterModel;
import com.example.dim.licence.utils.adapters.VigneronPagerAdapter;
import com.example.dim.licence.utils.commons.CrudViewPager;
import com.example.dim.licence.utils.interfaces.CrudDialogsInterface;
import com.example.dim.licence.utils.interfaces.CrudFragmentInterface;

import java.util.List;

import static com.example.dim.licence.MainActivity.ARG_DEBUG;


// DrawerMenu a mettre dans une classe abstraite pour éviter la surcharge de code identique  !!
public class VigneronActivity extends AppCompatActivity
        implements CrudFragmentInterface<Vigneron>
        , CrudDialogsInterface<Vigneron> {

    /**
     * PARAM for selected item
     */
    public static final String V_SELECTED = "V_SELECTED";
    /**
     * PARAM for each data passed in list fragment bundle
     */
    public static final String V_ = "V_";
    /**
     * PARAM number of items in list
     */
    public static final String V_COUNT = "V_COUNT";
    /**
     * PARAM for selected filter on vignerons list
     */
    public static final String V_DIALOG_TYPE = "V_DIALOG_TYPE";
    /**
     * PARAM for vignerons list onContactClick type
     */
    public static final String V_FILTER_TYPE = "V_FILTER_TYPE";
    /**
     * PARAM for vignerons list contact field
     */
    public static final String V_CONTACT_TYPE = "V_CONTACT_TYPE";
    /**
     * PARAM for vignerons list contact available fields
     */
    public static final String V_AVAILABLE_CONTACT_TYPES = "V_AVAILABLES_CONTACT_TYPE";
    /**
     * Bundle containing data
     */
    private Bundle vigneronsBundle;
    private Bundle dictionnaryData;
    /**
     * Accessor to DAOS
     */
    private MasterModel model;
    /**
     * Selected item
     */
    private Vigneron v_selected;
    /**
     * Current page index
     */
    private int currentPage;
    /**
     * ListFragment
     */
    private VigneronListFragment listFragment;
    /**
     * Detail fragment
     */
    private VigneronDetailFragment detailFragment;
    /**
     * Edition fragment
     */
    private VigneronEditFragment editFragment;
    /**
     * View pager adapter for Vignerons Activity
     * Handles fragments navigation logic
     */
    private VigneronPagerAdapter pagerAdapter;
    /**
     * Container for fragments
     */
    private CrudViewPager mViewPager;
    /**
     * Utilise pour l'etat de l'UI
     */
    private boolean newMode;
    // drawer menu
    private DrawerLayout drawerMenu;
    // needed to handle intent from cave
    boolean isFromCave;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vigneron);

        // init menus and navigation
        initToolbarAndMenu();

        // init needed data
        initVigneronsData();

        Intent intent = getIntent();
        // for new vigneron from cave edition
        if (intent != null) {
            isFromCave = intent.getBooleanExtra("VIGNERON_FROM_CAVE", false);

            if (isFromCave) {
                // is equal 2
                int i = intent.getIntExtra("VIGNERON_PAGE", 0);
                // redirecting to edition
                goToPage(i);
            }
        }

    }


    private void initToolbarAndMenu() {
        // set toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // set toolbar as actionbar
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_icon);
        actionbar.setTitle("Mes Vignerons");

        drawerMenu = findViewById(R.id.vigneron_drawer_layout);

        // set drawer menu toggle lsitener
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerMenu, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerMenu.addDrawerListener(toggle);
        toggle.syncState(); // use to sync if toggled or not
        // set Navigation (Menu items) listener
        NavigationView navigationView = findViewById(R.id.vigneron_nav_view);
        navigationView.setNavigationItemSelectedListener(new OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                item.setChecked(true); // for visual effect
                drawerMenu.closeDrawers(); // close menu

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            drawerMenu.openDrawer(GravityCompat.START);
            return true;
        }

        return false;
    }

    private void initVigneronsData() {
        mViewPager = findViewById(R.id.vigneron_vp_container);

        // init database access and DAO's
        model = MasterModel.getInstance(getApplicationContext());

        // init bundle for first launch of list fragment
        vigneronsBundle = new Bundle();

        int vIndex = 0;
        List<Vigneron> list = model.getAllButDefaultVignerons();
        if (list != null) {
            for (Vigneron v : list) {
                vigneronsBundle.putBundle(V_ + vIndex, v.entityToBundle());
                vIndex += 1;
            }
        }
        vigneronsBundle.putInt(V_COUNT, vIndex);
        vigneronsBundle.putInt(V_SELECTED, -1);
        vigneronsBundle.putString(V_FILTER_TYPE, "NONE"); // remove line

/*        // to much data to do this by bundles !!

        dictionnaryData = new Bundle();
        vIndex = 0;
        List<Ville> villes = model.getAllVilles();
        if (villes != null) {
            for (Ville ville : villes) {
                //Log.i(ARG_DEBUG, "initVigneronsData: VILLE "+ville.toString());
                dictionnaryData.putBundle("VILLE_"+vIndex, ville.entityToBundle());
                vIndex+=1;
            }
        }
        dictionnaryData.putInt("VILLE_COUNT", vIndex);
*/

        // init some settings for pager
        newMode = false;
        currentPage = 0;
        isFromCave = false;

        // setting up fragments
        listFragment = VigneronListFragment.newInstance(vigneronsBundle);
        detailFragment = VigneronDetailFragment.newInstance();
        editFragment = VigneronEditFragment.newInstance();

        // setting up page adapter
        pagerAdapter = new VigneronPagerAdapter(VigneronActivity.this, getSupportFragmentManager());
        mViewPager.setAdapter(pagerAdapter);
        mViewPager.addOnPageChangeListener(pageChangeListener);
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
        v_selected = new Vigneron();
        newMode = true;

        currentPage = 2;
        pagerAdapter.notifyDataSetChanged();
        mViewPager.setCurrentItem(currentPage);

        detailFragment.updateFragment(v_selected);
        editFragment.updateFragment(v_selected);
        mViewPager.disablePaging();
    }

    /**
     * Updates detail fragment.
     *
     * @param item select item from list fragment.
     */
    @Override
    public void updateDetailFragment(Vigneron item) {
        v_selected = item;

        currentPage = 1;
        pagerAdapter.notifyDataSetChanged();

        detailFragment.updateFragment(v_selected);
        mViewPager.setCurrentItem(currentPage, true);
    }

    @Override
    public void updateEditFragment(Vigneron item) {
        v_selected = item;

        currentPage = 2;
        pagerAdapter.notifyDataSetChanged();

        editFragment.updateFragment(v_selected);
        mViewPager.setCurrentItem(currentPage, true);
        mViewPager.disablePaging();
    }

    @Override
    public void onDeleteClick() {
        Bundle b = new Bundle();
        b.putString(V_DIALOG_TYPE, "DELETE");
        b.putString(V_SELECTED, v_selected.getVigneronLibelle());
        VigneronDialogs saveDialog = VigneronDialogs.newInstance(b);
        saveDialog.show(getSupportFragmentManager(), "saveBtnDialog");
    }

    @Override
    public void onSaveClick(Vigneron item) {
        // if is new no prompt
        if (isNewMode()) {
            v_selected = item;
            model.insertVigneron(v_selected);
            // if from cave, return result and avoid update
            if (isFromCave) {
                Intent data = new Intent();
                // set data to pass to cave activity
                data.putExtra("VIGNERON_CREATED", v_selected.entityToBundle());
                // do not forget to return result_ok (-1) with data
                setResult(RESULT_OK, data);
                // close the activity
                finish();
                // return;
            }

            goToPage(1);
            listFragment.updateRecyclerAdpater(v_selected, false, newMode);
        } else {
            Bundle b = new Bundle();
            b.putString(V_DIALOG_TYPE, "SAVE");
            b.putBundle(V_SELECTED, item.entityToBundle());
            VigneronDialogs saveDialog = VigneronDialogs.newInstance(b);
            saveDialog.show(getSupportFragmentManager(), "saveBtnDialog");
        }
    }

    @Override
    public void onFilterClick(String currentFilterType) {
        Bundle b = new Bundle();
        b.putString(V_DIALOG_TYPE, "FILTER");
        b.putString(V_FILTER_TYPE, currentFilterType);
        VigneronDialogs vigneronDialogs = VigneronDialogs.newInstance(b);
        vigneronDialogs.show(getSupportFragmentManager(), "filterDialog");
    }

    @Override
    public void onBackPressed() {
        if (currentPage > 0) {
            if (currentPage == 2) {
                Bundle b = new Bundle();
                b.putString(V_DIALOG_TYPE, "CANCEL");
                VigneronDialogs vigneronDialogs = VigneronDialogs.newInstance(b);
                vigneronDialogs.show(getSupportFragmentManager(), "cancelBtnDialog");
            } else {
                currentPage -= 1;
                goToPage(currentPage);
            }
        } else {
            super.onBackPressed();
        }
    }

    // from dialogs
    @Override
    public void delete() {
        model.deleteVigneron(v_selected);
        Log.i(ARG_DEBUG, "delete: newMode = " + newMode);
        goToPage(0);
        listFragment.updateRecyclerAdpater(v_selected, true, newMode);
    }

    @Override
    public void applyFilter(String filter, String value) {
        listFragment.applyFilter(filter, value);
    }

    @Override
    public void save(Vigneron vigneron) {
        v_selected = vigneron;
        model.updateVigneron(v_selected);
        Log.i(ARG_DEBUG, "save: SAVED ITEM = " + v_selected.toString());
        Log.i(ARG_DEBUG, "save: newMode = " + newMode);
        goToPage(1);
        listFragment.updateRecyclerAdpater(v_selected, false, newMode);
    }

    //
    @Override
    public void goToPage(int page) {
        handlePageState(page);
        pagerAdapter.notifyDataSetChanged();
        handlePageNavigation();
    }


    public void onLocateClick(Vigneron vigneron) {
        v_selected = vigneron;


        if (v_selected.getVigneronGeoloc().getGeolocVille() == null) {
            Toast.makeText(this, "Aucune ville n'à été renseigné pour permettre la localisation ", Toast.LENGTH_SHORT).show();
            return;
        } else {
            Log.i(ARG_DEBUG, "onLocateClick: ");
            // Build the intent
            Uri location = Uri.parse(buildMapIntentUri());
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, location);

            // Verify it resolves
            PackageManager packageManager = getPackageManager();
            List<ResolveInfo> activities = packageManager.queryIntentActivities(mapIntent, 0);
            boolean isIntentSafe = activities.size() > 0;

            // Start an activity if it's safe
            // kinda rushy - user ain't have the time to choose 'one time' or 'always'
            // is it classic behavior ?
            if (isIntentSafe) {
                startActivity(mapIntent);
            }
        }
    }

    /**
     * Build the uri for map intent from vigneron geolocation data
     *
     * @return String - the location request Uri for map intent
     */
    private String buildMapIntentUri() {
        StringBuilder uriStringBuilder = new StringBuilder();
        uriStringBuilder.append("geo:0,0?q=");
        for (String stra : v_selected.getVigneronGeoloc().getGeolocVille().getVilleLibelle().split(" ")) {
            uriStringBuilder.append(stra);
            uriStringBuilder.append("+");
        }
        replaceLastCharBy(uriStringBuilder, "+", ",");

        if (!v_selected.getVigneronGeoloc().getGeolocComplement().isEmpty()) {
            for (String stra : v_selected.getVigneronGeoloc().getGeolocComplement().split(" ")) {
                uriStringBuilder.append(stra);
                uriStringBuilder.append("+");
            }
        }

        if (!v_selected.getVigneronGeoloc().getGeolocAdresse1().isEmpty()) {
            for (String stra : v_selected.getVigneronGeoloc().getGeolocAdresse1().split(" ")) {
                uriStringBuilder.append(stra);
                uriStringBuilder.append("+");
            }
        }

        if (!v_selected.getVigneronGeoloc().getGeolocAdresse2().isEmpty()) {
            for (String stra : v_selected.getVigneronGeoloc().getGeolocAdresse2().split(" ")) {
                uriStringBuilder.append(stra);
                uriStringBuilder.append("+");
            }
        }

        if (!v_selected.getVigneronGeoloc().getGeolocAdresse3().isEmpty()) {
            for (String stra : v_selected.getVigneronGeoloc().getGeolocAdresse3().split(" ")) {
                uriStringBuilder.append(stra);
                uriStringBuilder.append("+");
            }
        }

        replaceLastCharBy(uriStringBuilder, "+", " ");


        return uriStringBuilder.toString();
    }

    private void replaceLastCharBy(StringBuilder uriStringBuilder, String replace, String replaceBy) {
        // replacing last '+' character by ',' to match mapIntent Uri format
        uriStringBuilder.replace(uriStringBuilder.length() - 1, uriStringBuilder.length(), replaceBy);
    }

    public void makeACall() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_CALL);
        String uriStr = "tel:" + v_selected.getVigneronFixe();
        Uri uri = Uri.parse(uriStr);
        intent.setData(uri);
    }

    public void makeAMobileCall() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_CALL);
        String uriStr = "tel:" + v_selected.getVigneronMobile();
        Uri uri = Uri.parse(uriStr);
        intent.setData(uri);
    }

    // !! Requires subscription fees to online fax service !!
    public void sendAFax() {
        // fax intent
    }

    // needed for vigneron edition from cave
    public void setIsNewMode() {
        newMode = true;
    }

    //
    public void sendAMail() {
        // my mail intent
    }

    public void onContactClick(Vigneron vigneron) {
        v_selected = vigneron;

        if (v_selected.getVigneronMobile().isEmpty()
                && v_selected.getVigneronFixe().isEmpty()
                && v_selected.getVigneronMail().isEmpty()) {

            Toast.makeText(this, "Aucune donnée n'à été renseigné pour permettre la localisation ", Toast.LENGTH_SHORT).show();
            return;
        }

        Bundle b = new Bundle();
        b.putString(V_DIALOG_TYPE, "CONTACT");
        b.putString(V_CONTACT_TYPE, "NONE");

        // known option size - should be extracted
        // following is quite ...ty.  careful with field/index pairs
        boolean[] availableOptions = new boolean[3];
        availableOptions[0] = !v_selected.getVigneronFixe().isEmpty();
        availableOptions[1] = !v_selected.getVigneronMobile().isEmpty();
        availableOptions[2] = !v_selected.getVigneronMail().isEmpty();

        b.putBooleanArray(V_AVAILABLE_CONTACT_TYPES, availableOptions);

        VigneronDialogs vigneronDialogs = VigneronDialogs.newInstance(b);
        vigneronDialogs.show(getSupportFragmentManager(), "contactDialog");
    }

    public void applyContact(String contactMode) {
        if (contactMode != null) {
            if (!contactMode.isEmpty()) {
                Log.i(ARG_DEBUG, "applyContact: " + contactMode);
                if (contactMode.equalsIgnoreCase("FIXE")) {
                    makeACall();
                }
                if (contactMode.equalsIgnoreCase("MOBILE")) {
                    makeAMobileCall();
                }
                if (contactMode.equalsIgnoreCase("MAIL")) {
                    sendAMail();
                }
            }
        }
    }

    // handles actions to do depending on current page value
    private void handlePageNavigation() {
        if (currentPage == 0) {
            listFragment.updateRecyclerAdapter();
        }
        if (currentPage == 1) {
            updateDetailFragment(v_selected);
        }
        if (currentPage == 2) {
            updateEditFragment(v_selected);
        }
        mViewPager.setCurrentItem(currentPage, false);
    }

    // handle some navigation variables depending on current page
    private void handlePageState(int page) {
        currentPage = page;
        if (isNewMode() && currentPage != 2) {
            newMode = false;
        }
        if (currentPage != 2) {
            enablePaging();
        } else {
            mViewPager.enablePaging();
        }
    }


    public void enablePaging() {
        mViewPager.enablePaging();
    }

    public VigneronListFragment getListFragment() {
        return listFragment;
    }

    public VigneronDetailFragment getDetailFragment() {
        return detailFragment;
    }

    public VigneronEditFragment getEditFragment() {
        return editFragment;
    }

    public boolean isNewMode() {
        return newMode;
    }

    public int getCurrentPage() {
        return currentPage;
    }


}
