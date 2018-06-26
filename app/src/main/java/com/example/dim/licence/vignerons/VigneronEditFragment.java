package com.example.dim.licence.vignerons;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.dim.licence.R;
import com.example.dim.licence.entities.Vigneron;
import com.example.dim.licence.utils.EditFragmentInterface;

import static com.example.dim.licence.MainActivity.ARG_DEBUG;

/**
 * A simple {@link Fragment} subclass.
 */
public class VigneronEditFragment extends Fragment
        implements EditFragmentInterface<Vigneron> {

    private EditText et_libelle;
    private EditText et_domaine;
    private EditText et_fixe;
    private EditText et_mobile;
    private EditText et_mail;
    private EditText et_fax;
    private EditText et_pays;
    private EditText et_ville;
    private EditText et_code;
    private EditText et_adresse;
    private EditText et_comment;

    private Vigneron item;

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
        View rootView = inflater.inflate(R.layout.fragment_vigneron_edit, container, false);
        Log.i(ARG_DEBUG, "onCreateView: CREATED FUCKING MOTHERFUCKER VIEW");
        et_libelle = rootView.findViewById(R.id.edt_vigneron_label_value);
        et_domaine = rootView.findViewById(R.id.edt_vigneron_domaine_value);
        et_fixe = rootView.findViewById(R.id.edt_vigneron_fixe_value);
        et_mobile = rootView.findViewById(R.id.edt_vigneron_mobile_value);
        et_mail = rootView.findViewById(R.id.edt_vigneron_mail_value);
        et_fax = rootView.findViewById(R.id.edt_vigneron_fax_value);
        et_pays = rootView.findViewById(R.id.edt_vigneron_pays_value);
        et_ville = rootView.findViewById(R.id.edt_vigneron_ville_value);
        et_code = rootView.findViewById(R.id.edt_vigneron_code_value);
        et_adresse = rootView.findViewById(R.id.edt_vigneron_adresse_value);
        et_comment = rootView.findViewById(R.id.edt_vigneron_comment_value);
        return rootView;
    }

    @Override
    public void updateEditFragment(Vigneron _item) {
        this.item = _item;

        if (item != null) {
/*            et_libelle.setText("");
            et_domaine.setText(item.getVigneronDomaine());
            et_fixe.setText(item.getVigneronFixe());
            et_mobile.setText(item.getVigneronMobile());
            et_mail.setText(item.getVigneronMail());
            et_fax.setText(item.getVigneronFax());
            et_pays.setText(item.getVigneronGeoloc().getGeolocPays());
            et_ville.setText(item.getVigneronGeoloc().getGeolocVille());
            et_code.setText(item.getVigneronGeoloc().getGeolocCode());
            et_adresse.setText(item.getVigneronGeoloc().getGeolocAdresse());
            et_comment.setText(item.getVigneronComment());
  */
            Log.i(ARG_DEBUG, "updateEditFragment: FIX VIEW !");
        }
    }

    @Override
    public void updateActivity(Vigneron item) {

    }
}
