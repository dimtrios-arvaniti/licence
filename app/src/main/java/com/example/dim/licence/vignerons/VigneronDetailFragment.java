package com.example.dim.licence.vignerons;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dim.licence.R;
import com.example.dim.licence.entities.Vigneron;
import com.example.dim.licence.utils.DetailFragmentInterface;

import static com.example.dim.licence.MainActivity.ARG_DEBUG;

/**
 * A simple {@link Fragment} subclass.
 */
public class VigneronDetailFragment extends Fragment implements DetailFragmentInterface<Vigneron>{

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

    private Vigneron item;

    public static VigneronDetailFragment newInstance() {
        VigneronDetailFragment fragment = new VigneronDetailFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public VigneronDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(ARG_DEBUG, "onCreateView: CREATED FUCKING DETAIL VIEW !");
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
        return rootView;
    }

    @Override
    public void updateDetailFragment(Vigneron _item) {
        this.item = _item;

        if (item != null) {
            tv_libelle.setText(item.getVigneronLibelle());
            tv_domaine.setText(item.getVigneronDomaine());
            tv_fixe.setText(item.getVigneronFixe());
            tv_mobile.setText(item.getVigneronMobile());
            tv_mail.setText(item.getVigneronMail());
            tv_fax.setText(item.getVigneronFax());
            tv_pays.setText(item.getVigneronGeoloc().getGeolocPays());
            tv_ville.setText(item.getVigneronGeoloc().getGeolocVille());
            tv_code.setText(item.getVigneronGeoloc().getGeolocCode());
            tv_adresse.setText(item.getVigneronGeoloc().getGeolocAdresse());
            tv_comment.setText(item.getVigneronComment());
        }
    }

    public Vigneron getItem() {
        return item;
    }
}
