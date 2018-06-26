package com.example.dim.licence.vignerons;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dim.licence.R;
import com.example.dim.licence.entities.Vigneron;
import com.example.dim.licence.utils.ListFragmentInterface;
import com.example.dim.licence.utils.ListItemDecoration;
import com.example.dim.licence.utils.VigneronRecyclerAdapter;
import com.example.dim.licence.utils.VigneronRecyclerInterface;

import static com.example.dim.licence.MainActivity.ARG_DEBUG;
import static com.example.dim.licence.vignerons.VigneronActivity.V_;
import static com.example.dim.licence.vignerons.VigneronActivity.V_COUNT;

/**
 * A simple {@link Fragment} subclass.
 */
public class VigneronListFragment extends Fragment implements ListFragmentInterface<Vigneron>, VigneronRecyclerInterface {

    private RecyclerView recyclerView;
    private SparseArray<Vigneron> list;

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
        Log.i(ARG_DEBUG, "onCreate: CREATE !");
        list = new SparseArray<>();
        Vigneron v;
        int count = getArguments().getInt(V_COUNT, -1);
        Log.i(ARG_DEBUG, "VigneronRecyclerAdapter: COUNT ="+count);
        if (count != -1) {
            for (int i = 1; i < count; i++) {
                v = new Vigneron();
                Log.i(ARG_DEBUG, "VigneronRecyclerAdapter: "+(getArguments().getBundle(V_+i) != null));
                v.entityFromBundle(getArguments().getBundle(V_+i));
                //if (v != null) {
                list.put(i, v);
                //}
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_vigneron_list, container, false);
        recyclerView = rootView.findViewById(R.id.list);
        recyclerView.setAdapter(new VigneronRecyclerAdapter(this));
        recyclerView.addItemDecoration(new ListItemDecoration(getContext()));
        return rootView;
    }

    @Override
    public void updateRecyclerAdpater(Vigneron item, boolean isDelete) {

    }

    @Override
    public void updateRecyclerAdapter() {
        recyclerView.setAdapter(null);
        recyclerView.setAdapter(new VigneronRecyclerAdapter(this));
    }


    @Override
    public void updateSelectedItem(int selectedItemPos) {
        // row number starts at 1, not sparse array index !
        ((VigneronActivity)getActivity()).updateDetailFragment(list.get(selectedItemPos+1));
        Log.i(ARG_DEBUG, "updateSelectedItem: PASSED !");
    }

    @Override
    public void locate(Vigneron selected) {
        //parentActivity.addBottle(selected);
    }

    @Override
    public void contact(Vigneron selected) {
        //parentActivity.removeBottle(selected);
    }

    public SparseArray<Vigneron> getList() {
        return list;
    }
}
