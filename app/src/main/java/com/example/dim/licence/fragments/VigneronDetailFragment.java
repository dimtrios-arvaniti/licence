package com.example.dim.licence.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.dim.licence.R;
import com.example.dim.licence.entities.Vigneron;
import com.example.dim.licence.utils.interfaces.CrudFragmentInterface;

/**
 * A simple {@link Fragment} subclass.
 */
public class VigneronDetailFragment extends Fragment {

    private CrudFragmentInterface<Vigneron> activityCallback;
    private Vigneron item;

    private TextView tv_libelle;
    private TextView tv_domaine;
    private TextView tv_fixe;
    private TextView tv_mobile;
    private TextView tv_mail;
    private TextView tv_fax;
    private TextView tv_pays;
    private TextView tv_ville;
    private TextView tv_code;
    private TextView tv_adresse;
    private TextView tv_comment;
    private Button editBtn;
    private Button deleteBtn;


    private View.OnClickListener onEditClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //((VigneronActivity) getActivity()).updateEditFragment(item);
            activityCallback.updateEditFragment(item);
        }
    };

    private View.OnClickListener onDeleteClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //((VigneronActivity) getActivity()).onDeleteClick();
            activityCallback.onDeleteClick();
        }
    };

    public static VigneronDetailFragment newInstance() {
        VigneronDetailFragment fragment = new VigneronDetailFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public static VigneronDetailFragment newInstance(Bundle selected) {
        VigneronDetailFragment fragment = new VigneronDetailFragment();
        fragment.setArguments(selected);
        return fragment;
    }

    public VigneronDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_vigneron_detail, container, false);
        tv_libelle = rootView.findViewById(R.id.dtl_vigneron_label_value);
        tv_domaine = rootView.findViewById(R.id.dtl_vigneron_domaine_value);
        tv_fixe = rootView.findViewById(R.id.dtl_vigneron_fixe_value);
        tv_mobile = rootView.findViewById(R.id.dtl_vigneron_mobile_value);
        tv_mail = rootView.findViewById(R.id.dtl_vigneron_mail_value);
        tv_fax = rootView.findViewById(R.id.dtl_vigneron_fax_value);
        tv_pays = rootView.findViewById(R.id.dtl_vigneron_pays_value);
        tv_ville = rootView.findViewById(R.id.dtl_vigneron_ville_value);
        tv_code = rootView.findViewById(R.id.dtl_vigneron_code_value);
        tv_adresse = rootView.findViewById(R.id.dtl_vigneron_adresse_value);
        tv_comment = rootView.findViewById(R.id.dtl_vigneron_comment_value);
        editBtn = rootView.findViewById(R.id.btnEdit);
        deleteBtn = rootView.findViewById(R.id.btnDel);

        editBtn.setOnClickListener(onEditClickListener);
        deleteBtn.setOnClickListener(onDeleteClickListener);
        return rootView;
    }

    public void updateFragment(Vigneron _item) {
        this.item = _item;

        if (item != null) {
            tv_libelle.setText(item.getVigneronLibelle() == null ? "" : item.getVigneronLibelle());
            tv_domaine.setText(item.getVigneronDomaine() == null ? "" : item.getVigneronDomaine());
            tv_fixe.setText(item.getVigneronFixe() == null ? "" : item.getVigneronFixe());
            tv_mobile.setText(item.getVigneronMobile() == null ? "" : item.getVigneronMobile());
            tv_mail.setText(item.getVigneronMail() == null ? "" : item.getVigneronMail());
            tv_fax.setText(item.getVigneronFax() == null ? "" : item.getVigneronFax());
            tv_comment.setText(item.getVigneronComment() == null ? "" : item.getVigneronComment());

            if (item.getVigneronGeoloc() != null) {
                tv_pays.setText(item.getVigneronGeoloc().getGeolocPays() == null ? "" : item.getVigneronGeoloc().getGeolocPays());
                tv_ville.setText(item.getVigneronGeoloc().getGeolocVille() == null ? "" : item.getVigneronGeoloc().getGeolocVille());
                tv_code.setText(item.getVigneronGeoloc().getGeolocCode() == null ? "" : item.getVigneronGeoloc().getGeolocCode());
                tv_adresse.setText(item.getVigneronGeoloc().getGeolocAdresse() == null ? "" : item.getVigneronGeoloc().getGeolocAdresse());
            }
        }
    }

    public Vigneron getItem() {
        return item;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            activityCallback = (CrudFragmentInterface<Vigneron>) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString()
                    + " must implement CrudFragmentInterface<T>");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        activityCallback = null;
    }
}
