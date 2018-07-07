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

import com.example.dim.licence.CaveActivity;
import com.example.dim.licence.R;
import com.example.dim.licence.entities.Cave;
import com.example.dim.licence.utils.adapters.CaveRecyclerAdapter;
import com.example.dim.licence.utils.interfaces.CrudFragmentInterface;
import com.example.dim.licence.utils.uicustoms.ListItemDecoration;

import static com.example.dim.licence.CaveActivity.C_;
import static com.example.dim.licence.CaveActivity.C_COUNT;
import static com.example.dim.licence.MainActivity.ARG_DEBUG;

public class CaveListFragment extends Fragment {

    private CrudFragmentInterface<Cave> activityCallback;
    private RecyclerView recyclerView;
    private SparseArray<Cave> backingList;
    private SparseArray<Cave> filteredList;
    private String filterValue;
    private String filterField;
    private CaveRecyclerAdapter recyclerAdapter;
    private Button createBtn;
    private Button filterBtn;

    public CaveListFragment() {
        // Required empty public constructor
    }

    public static CaveListFragment newInstance(Bundle args) {
        CaveListFragment fragment = new CaveListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // be careful this get called every time the fragment is rebuilt by viewPager,

        if (backingList == null) { // do not remove this line
            backingList = new SparseArray<>();
            filteredList = new SparseArray<>();
            filterField = "NONE";
            filterValue = "";
            // make backing list
            int count = getArguments().getInt(C_COUNT, -1);
            if (count != -1) {
                Cave v;
                for (int i = 0; i < count; i++) {
                    v = new Cave(getArguments().getBundle(C_ + i));
                    backingList.put(v.getCaveId().intValue(), v);
                }
            }

            for (int i = 0; i < backingList.size(); i++) {
                int key = backingList.keyAt(i);
                filteredList.put(key, backingList.get(key));
            }
        }
    }

    // called by recycler, calling activity
    public void onQuantityChange(int key, boolean plus) {
        ((CaveActivity) getActivity()).onQuantityChange(filteredList.get(key), plus);
    }


    private void makeFilteredFromFilter(String filter, String value) {
        filteredList.clear();

        switch (filter) {
            case "NOM":
                for (int i = 0; i < backingList.size(); i++) {
                   /* int key = backingList.keyAt(i);
                    if (backingList.get(key).getVigneronLibelle().toLowerCase().trim().contains(value.toLowerCase().trim())) {
                        filteredList.put(key, backingList.get(key));
                    }*/
                }
                break;
            case "APPELATION":
                for (int i = 0; i < backingList.size(); i++) {
                   /* int key = backingList.keyAt(i);
                    if (backingList.get(key).getVigneronDomaine().toLowerCase().trim().contains(value.toLowerCase().trim())) {
                        filteredList.put(key, backingList.get(key));
                    }*/
                }
                break;
            case "ANNEE":
                for (int i = 0; i < backingList.size(); i++) {
                   /* int key = backingList.keyAt(i);
                    if (backingList.get(key).getVigneronGeoloc().getGeolocPays().toLowerCase().trim().startsWith(value.toLowerCase().trim())) {
                        filteredList.put(key, backingList.get(key));
                    }*/
                }
                break;
            case "TYPE":
                for (int i = 0; i < backingList.size(); i++) {
                    /*int key = backingList.keyAt(i);
                    if (backingList.get(key).getVigneronGeoloc().getGeolocVille().toLowerCase().trim().startsWith(value.toLowerCase().trim())) {
                        filteredList.put(key, backingList.get(key));
                    }*/
                }
                break;
            case "VIGNERON":
                for (int i = 0; i < backingList.size(); i++) {
                    int key = backingList.keyAt(i);
                    /*if (backingList.get(key).getVigneronGeoloc().getGeolocCode().toLowerCase().trim().startsWith(value.toLowerCase().trim())) {
                        filteredList.put(key, backingList.get(key));
                    }*/
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
        View rootView = inflater.inflate(R.layout.fragment_cave_list, container, false);
        recyclerView = rootView.findViewById(R.id.cave_list);
        createBtn = rootView.findViewById(R.id.cave_btnCreate);
        filterBtn = rootView.findViewById(R.id.cave_btnFilter);

        recyclerAdapter = new CaveRecyclerAdapter(this);
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
        printData();
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


    public void updateRecyclerAdapter(Cave item, boolean isDelete, boolean isNew) {
        if (isDelete) {
            int position = filteredList.indexOfValue(item);
            Log.i(ARG_DEBUG, "updateRecyclerAdapter: item position in list = " + position);
            filteredList.remove(item.getCaveId().intValue());
            int bPosition = backingList.indexOfValue(item);
            backingList.remove(item.getCaveId().intValue());
            recyclerAdapter.notifyItemRemoved(position);
            printData();

        } else {

            filteredList.put(item.getCaveId().intValue(), item);
            backingList.put(item.getCaveId().intValue(), item);
            int position = filteredList.indexOfKey(item.getCaveId().intValue());
            Log.i(ARG_DEBUG, "updateRecyclerAdapter: item position in list = " + position);
            if (isNew) {
                recyclerAdapter.notifyItemInserted(item.getCaveId().intValue());
            } else {
                recyclerAdapter.notifyItemChanged(item.getCaveId().intValue(), item);
            }
            printData();
        }
    }


    public void updateRecyclerAdapter() {
        //recyclerAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(recyclerAdapter);
        printData();
    }

    public void applyFilter(String filter, String value) {
        filterField = filter;
        filterValue = value;

        if (filter.equalsIgnoreCase("NONE")) {
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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            activityCallback = (CrudFragmentInterface<Cave>) getActivity();
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
/*
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("CAVE_LIST_FILTER_FIELD", filterField);
        outState.putString("CAVE_LIST_FILTER_VALUE", filterValue);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        if (savedInstanceState != null) {
            filterField = savedInstanceState.getString("CAVE_LIST_FILTER_FIELD");
            filterValue = savedInstanceState.getString("CAVE_LIST_FILTER_VALUE");
        }
    }
*/
    public SparseArray<Cave> getList() {
        return filteredList;
    }
}
