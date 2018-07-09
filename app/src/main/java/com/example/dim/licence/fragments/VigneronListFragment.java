package com.example.dim.licence.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.dim.licence.R;
import com.example.dim.licence.VigneronActivity;
import com.example.dim.licence.entities.Vigneron;
import com.example.dim.licence.utils.adapters.VigneronRecyclerAdapter;
import com.example.dim.licence.utils.interfaces.CrudFragmentInterface;
import com.example.dim.licence.utils.uicustoms.ListItemDecoration;

import static com.example.dim.licence.MainActivity.ARG_DEBUG;
import static com.example.dim.licence.utils.commons.Commons.AUCUN;
import static com.example.dim.licence.utils.commons.Commons.VIGN_;
import static com.example.dim.licence.utils.commons.Commons.VIGN_COUNT;
import static com.example.dim.licence.utils.commons.Commons.VIGN_FILTER_DEPARTEMENT;
import static com.example.dim.licence.utils.commons.Commons.VIGN_FILTER_DOMAINE;
import static com.example.dim.licence.utils.commons.Commons.VIGN_FILTER_NOM;
import static com.example.dim.licence.utils.commons.Commons.VIGN_FILTER_REGION;
import static com.example.dim.licence.utils.commons.Commons.VIGN_FILTER_VILLE;
import static com.example.dim.licence.utils.commons.Commons.VIGN_FILTER_ZIPCODE;

/**
 * A simple {@link Fragment} subclass.
 */
public class VigneronListFragment extends Fragment {

    private CrudFragmentInterface<Vigneron> activityCallback;
    private RecyclerView recyclerView;
    private SparseArray<Vigneron> backingList;
    private SparseArray<Vigneron> filteredList;
    private String filterValue;
    private String filterField;
    private VigneronRecyclerAdapter recyclerAdapter;
    private Button createBtn;
    private Button filterBtn;

    public VigneronListFragment() {
        // Required empty public constructor
    }

    public static VigneronListFragment newInstance(Bundle args) {
        VigneronListFragment fragment = new VigneronListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // be careful this get called every time the fragment is rebuilt by viewPager,
        if (backingList == null) { // do not remove this line
            filterField = AUCUN;
            filterValue = "";
            backingList = new SparseArray<>();
            filteredList = new SparseArray<>();

            // make backing list
            int count = getArguments().getInt(VIGN_COUNT, -1);
            if (count != -1) {
                Vigneron v;
                for (int i = 0; i < count; i++) {
                    v = new Vigneron(getArguments().getBundle(VIGN_ + i));
                    backingList.put(v.getVigneronId().intValue(), v);
                }
            }

            for (int i = 0; i < backingList.size(); i++) {
                int key = backingList.keyAt(i);
                filteredList.put(key, backingList.get(key));
            }
        }
    }

    private void makeFilteredFromFilter(String filter, String value) {
        filteredList.clear();

        switch (filter) {
            case VIGN_FILTER_NOM:
                for (int i = 0; i < backingList.size(); i++) {
                    int key = backingList.keyAt(i);
                    if (backingList.get(key).getVigneronLibelle().toLowerCase().trim().contains(value.toLowerCase().trim())) {
                        filteredList.put(key, backingList.get(key));
                    }
                }
                break;

            case VIGN_FILTER_DOMAINE:
                for (int i = 0; i < backingList.size(); i++) {
                    int key = backingList.keyAt(i);
                    if (backingList.get(key).getVigneronDomaine().toLowerCase().trim().contains(value.toLowerCase().trim())) {
                        filteredList.put(key, backingList.get(key));
                    }
                }
                break;

            case VIGN_FILTER_DEPARTEMENT:
                for (int i = 0; i < backingList.size(); i++) {
                    int key = backingList.keyAt(i);
                    if (backingList.get(key).getVigneronGeoloc().getGeolocVille()
                            .getVilleDepartement().getDepartementLibelle()
                            .toLowerCase().trim().startsWith(value.toLowerCase().trim())) {

                        filteredList.put(key, backingList.get(key));
                    }
                }
                break;

            case VIGN_FILTER_REGION:
                for (int i = 0; i < backingList.size(); i++) {
                    int key = backingList.keyAt(i);
                    if (backingList.get(key).getVigneronGeoloc().getGeolocVille()
                            .getVilleDepartement().getDepartementRegion().getRegionLibelle()
                            .toLowerCase().trim().startsWith(value.toLowerCase().trim())) {

                        filteredList.put(key, backingList.get(key));
                    }
                }
                break;

            case VIGN_FILTER_VILLE:
                for (int i = 0; i < backingList.size(); i++) {
                    int key = backingList.keyAt(i);
                    if (backingList.get(key).getVigneronGeoloc().getGeolocVille()
                            .getVilleLibelle().toLowerCase().trim().contains(value.toLowerCase().trim())) {
                        filteredList.put(key, backingList.get(key));
                    }
                }
                break;

            case VIGN_FILTER_ZIPCODE:
                for (int i = 0; i < backingList.size(); i++) {
                    int key = backingList.keyAt(i);
                    if (backingList.get(key).getVigneronGeoloc().getGeolocVille().getVilleZipCode()
                            .toLowerCase().trim().startsWith(value.toLowerCase().trim())) {

                        filteredList.put(key, backingList.get(key));
                    }
                }
                break;

            default:
                Log.e(ARG_DEBUG, "makeFilteredFromFilter: unknown FILTER TYPE for filter value [" + filter + "]");
                break;
        }
    }

    private void makeFilteredFromBacking() {
        filteredList.clear();

        for (int i = 0; i < backingList.size(); i++) {
            int key = backingList.keyAt(i);
            filteredList.put(key, backingList.get(key));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_vigneron_list, container, false);
        recyclerView = rootView.findViewById(R.id.vigneron_list);
        createBtn = rootView.findViewById(R.id.vigneron_btnCreate);
        filterBtn = rootView.findViewById(R.id.vigneron_btnFilter);

        recyclerAdapter = new VigneronRecyclerAdapter(this);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.addItemDecoration(new ListItemDecoration(getContext()));

        createBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                activityCallback.onNewClick();
            }
        });

        filterBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                activityCallback.onFilterClick(filterField);
            }
        });
        return rootView;
    }

    private void printData() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < backingList.size();
             i++) {
            int key = backingList.keyAt(i);
            sb.append(backingList.get(key).toString()).append("\n");
        }

        sb.append("\nFILTERED LIST = \n");
        for (int i = 0; i < backingList.size();
             i++) {
            int key = backingList.keyAt(i);
            sb.append(backingList.get(key).toString()).append("\n");
        }

        Log.i(ARG_DEBUG, "printData: BACKING LIST  = \n" + sb.toString());
    }


    public void updateRecyclerAdpater(Vigneron item, boolean isDelete, boolean isNew) {
        if (isDelete) {
            int position = filteredList.indexOfValue(item);
            Log.i(ARG_DEBUG, "updateRecyclerAdapter: item position in list = " + position);
            filteredList.remove(item.getVigneronId().intValue());
            int bPosition = backingList.indexOfValue(item);
            backingList.remove(item.getVigneronId().intValue());
            recyclerAdapter.notifyItemRemoved(position);
            printData();

        } else {

            filteredList.put(item.getVigneronId().intValue(), item);
            backingList.put(item.getVigneronId().intValue(), item);
            int position = filteredList.indexOfKey(item.getVigneronId().intValue());
            Log.i(ARG_DEBUG, "updateRecyclerAdapter: item position in list = " + position);
            if (isNew) {
                recyclerAdapter.notifyItemInserted(item.getVigneronId().intValue());
            } else {
                recyclerAdapter.notifyItemChanged(item.getVigneronId().intValue(), item);
            }
            printData();
        }
    }

    public void updateRecyclerAdapter() {
        recyclerView.setAdapter(recyclerAdapter);
        printData();
    }

    public void applyFilter(String filter, String value) {
        filterField = filter;
        filterValue = value;

        if (filter.equalsIgnoreCase(AUCUN)) {
            makeFilteredFromBacking();
            Log.i(ARG_DEBUG, "applyFilter: THIS IS NO FILTER !");
        } else {

            makeFilteredFromFilter(filter, value);
            Log.i(ARG_DEBUG, "applyFilter: THIS IS FILTER !");

            if (filteredList.size() == 0) {
                Log.i(ARG_DEBUG, "applyFilter: THIS WAS EMPTY FILTER !");
                Toast.makeText(getContext(), "Aucune valeur correspondante pour '" + value + "' en tant que " + filter, Toast.LENGTH_LONG).show();
                makeFilteredFromBacking();
            }
        }

        recyclerAdapter.notifyDataSetChanged();
        updateRecyclerAdapter();
    }

    public void updateSelectedItem(int key) {
        activityCallback.updateDetailFragment(filteredList.get(key));
    }

    public void onLocateClick(int key) {
        ((VigneronActivity) getActivity()).onLocateClick(filteredList.get(key));
    }

    public void onContactClick(int key) {
        ((VigneronActivity) getActivity()).onContactClick(filteredList.get(key));
    }


    public SparseArray<Vigneron> getList() {
        return filteredList;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            activityCallback = (CrudFragmentInterface<Vigneron>) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString()
                    + " must implement CrudFragmentInterface<"
                    + getActivity().getClass().getSimpleName()
                    + ">");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        activityCallback = null;
    }

}
