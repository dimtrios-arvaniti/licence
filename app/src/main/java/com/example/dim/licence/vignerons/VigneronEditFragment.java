package com.example.dim.licence.vignerons;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dim.licence.R;
import com.example.dim.licence.entities.Vigneron;
import com.example.dim.licence.utils.EditFragmentInterface;

import static com.example.dim.licence.vignerons.VigneronActivity.V_SELECTED;

/**
 * A simple {@link Fragment} subclass.
 */
public class VigneronEditFragment extends Fragment implements EditFragmentInterface<Vigneron> {


    public VigneronEditFragment() {
        // Required empty public constructor
    }
    public static VigneronEditFragment newInstance() {
        VigneronEditFragment fragment = new VigneronEditFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_vigneron_edit, container, false);
    }

    @Override
    public void updateEditFragment(Vigneron item) {
        getArguments().putBundle(V_SELECTED, item.entityToBundle());
    }

    @Override
    public void updateActivity(Vigneron item) {

    }
}
