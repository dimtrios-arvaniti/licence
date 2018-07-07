package com.example.dim.licence.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dim.licence.R;
import com.example.dim.licence.VigneronActivity;
import com.example.dim.licence.entities.Geolocalisation;
import com.example.dim.licence.entities.Vigneron;
import com.example.dim.licence.utils.interfaces.CrudFragmentInterface;
import com.example.dim.licence.dialogs.VigneronDialogs;

import static com.example.dim.licence.utils.commons.Commons.MAIL_REGEX;
import static com.example.dim.licence.utils.commons.Commons.TEL_REGEX;
import static com.example.dim.licence.VigneronActivity.V_DIALOG_TYPE;

/**
 * A simple {@link Fragment} subclass.
 */
public class VigneronEditFragment extends Fragment {

    private CrudFragmentInterface<Vigneron> activityCallback;
    private Vigneron item;

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
    private Button btnCancel;
    private Button btnSave;

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
        btnSave = rootView.findViewById(R.id.btnSave);
        btnCancel = rootView.findViewById(R.id.btnCancel);

        btnCancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle b = new Bundle();
                b.putString(V_DIALOG_TYPE, "CANCEL");
                VigneronDialogs vigneronDialogs = VigneronDialogs.newInstance(b);
                vigneronDialogs.show(getActivity().getSupportFragmentManager(), "VigneronDialogs");
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateInput()) {
                    // if intent from cave
                    if (getActivity().getIntent() != null) {
                        // create new item !
                        item = new Vigneron();
                        ((VigneronActivity)getActivity()).setIsNewMode();
                        // do basic stuff
                        //Vigneron v = updateVigneronFromView();
                        activityCallback.onSaveClick(updateVigneronFromView());

                    } else {
                        //Vigneron vigneron = ;
                        activityCallback.onSaveClick(updateVigneronFromView());
                    }

                }

            }
        });
        return rootView;
    }


    @NonNull
    private Vigneron updateVigneronFromView() {
        Vigneron vigneron = new Vigneron();
        // retrieving old id if any
        if (item != null) {
            if (item.getVigneronId() != null) {
                vigneron.setVigneronId(item.getVigneronId());
            }

            vigneron.setVigneronLibelle(et_libelle.getText().toString());
            vigneron.setVigneronDomaine(et_domaine.getText().toString());
            vigneron.setVigneronFixe(et_fixe.getText().toString());
            vigneron.setVigneronMobile(et_mobile.getText().toString());
            vigneron.setVigneronMail(et_mail.getText().toString());
            vigneron.setVigneronFax(et_fax.getText().toString());
            vigneron.setVigneronComment(et_comment.getText().toString());

            // retrieving geoloc id if any
            Geolocalisation geoloc = new Geolocalisation();
            if (item.getVigneronGeoloc().getGeolocId() != null) {
                geoloc.setGeolocId(item.getVigneronGeoloc().getGeolocId());
            }
            geoloc.setGeolocPays(et_pays.getText().toString());
            geoloc.setGeolocVille(et_ville.getText().toString());
            geoloc.setGeolocCode(et_code.getText().toString());
            geoloc.setGeolocAdresse(et_adresse.getText().toString());

            vigneron.setVigneronGeoloc(geoloc);
        }

        return vigneron;
    }

    private boolean validateInput() {
        if (et_libelle.getText().toString().isEmpty()) {
            Toast.makeText(getContext(), "Le libelle est requis", Toast.LENGTH_LONG).show();
            et_libelle.requestFocus();
            return false;
        }

        if (isInvalidDbConstraints()) {
            return false;
        }

        if (isInvalidFormat()) {
            return false;
        }

        return true;
    }

    private boolean isInvalidFormat() {
        if (!isEditTextValid(et_fixe, TEL_REGEX)) {
            return true;
        }
        if (!isEditTextValid(et_fax, TEL_REGEX)) {
            return true;
        }
        if (!isEditTextValid(et_mobile, TEL_REGEX)) {
            return true;
        }
        if (!isEditTextValid(et_mail, MAIL_REGEX)) {
            return true;
        }
        return false;
    }

    private boolean isInvalidDbConstraints() {
        if (et_libelle.getText().toString().length() >= 200) {
            Toast.makeText(getContext(), "Le nom ne peut avoir plus de 200 caractères !", Toast.LENGTH_LONG).show();
            et_libelle.requestFocus();
            return true;
        }
        if (et_domaine.getText().toString().length() >= 200) {
            Toast.makeText(getContext(), "Le domaine ne peut avoir plus de 200 caractères !", Toast.LENGTH_LONG).show();
            et_domaine.requestFocus();
            return true;
        }
        if (et_comment.getText().toString().length() >= 500) {
            Toast.makeText(getContext(), "Le commentaire ne peut avoir plus de 500 caractères !", Toast.LENGTH_LONG).show();
            et_comment.requestFocus();
            return true;
        }
        if (et_fixe.getText().toString().length() >= 20) {
            Toast.makeText(getContext(), "Le telephone ne peut avoir plus de 20 chiffres !", Toast.LENGTH_LONG).show();
            et_fixe.requestFocus();
            return true;
        }
        if (et_fax.getText().toString().length() >= 20) {
            Toast.makeText(getContext(), "Le fax ne peut avoir plus de 20 chiffres !", Toast.LENGTH_LONG).show();
            et_fax.requestFocus();
            return true;
        }
        if (et_mobile.getText().toString().length() >= 20) {
            Toast.makeText(getContext(), "Le mobile ne peut avoir plus de 20 chiffres !", Toast.LENGTH_LONG).show();
            et_fax.requestFocus();
            return true;
        }
        if (et_mail.getText().toString().length() >= 200) {
            Toast.makeText(getContext(), "Le mail ne peut avoir plus de 200 caractères !", Toast.LENGTH_LONG).show();
            et_mail.requestFocus();
            return true;
        }

        if (et_pays.getText().toString().length() >= 200) {
            Toast.makeText(getContext(), "Le pays ne peut avoir plus de 200 caractères !", Toast.LENGTH_LONG).show();
            et_pays.requestFocus();
            return true;
        }

        if (et_ville.getText().toString().length() >= 200) {
            Toast.makeText(getContext(), "La ville ne peut avoir plus de 200 caractères !", Toast.LENGTH_LONG).show();
            et_ville.requestFocus();
            return true;
        }

        if (et_code.getText().toString().length() >= 20) {
            Toast.makeText(getContext(), "Le code postal ne peut avoir plus de 20 caractères !", Toast.LENGTH_LONG).show();
            et_code.requestFocus();
            return true;
        }

        if (et_adresse.getText().toString().length() >= 500) {
            Toast.makeText(getContext(), "L'adresse ne peut avoir plus de 500 caractères !", Toast.LENGTH_LONG).show();
            et_adresse.requestFocus();
            return true;
        }
        return false;
    }

    private boolean isEditTextValid(EditText editText, String regex) {
        if (!editText.getText().toString().isEmpty()) {
            if (!editText.getText().toString().matches(regex)) {
                Toast.makeText(getContext(), "Le champs "+editText.getHint()+" est invalide !", Toast.LENGTH_LONG).show();
                editText.requestFocus();
                return false;
            }
        }
        return true;
    }

    public void updateFragment(Vigneron _item) {
        this.item = _item;
        if (item != null) {
            et_libelle.setText(item.getVigneronLibelle() == null ? "" : item.getVigneronLibelle());
            et_domaine.setText(item.getVigneronDomaine() == null ? "" : item.getVigneronDomaine());
            et_fixe.setText(item.getVigneronFixe() == null ? "" : item.getVigneronFixe());
            et_mobile.setText(item.getVigneronMobile() == null ? "" : item.getVigneronMobile());
            et_mail.setText(item.getVigneronMail() == null ? "" : item.getVigneronMail());
            et_fax.setText(item.getVigneronFax() == null ? "" : item.getVigneronFax());
            et_comment.setText(item.getVigneronComment() == null ? "" : item.getVigneronComment());
            
            if (item.getVigneronGeoloc() != null) {
                et_pays.setText(item.getVigneronGeoloc().getGeolocPays() == null ? "" : item.getVigneronGeoloc().getGeolocPays());
                et_ville.setText(item.getVigneronGeoloc().getGeolocVille() == null ? "" : item.getVigneronGeoloc().getGeolocVille());
                et_code.setText(item.getVigneronGeoloc().getGeolocCode() == null ? "" : item.getVigneronGeoloc().getGeolocCode());
                et_adresse.setText(item.getVigneronGeoloc().getGeolocAdresse() == null ? "" : item.getVigneronGeoloc().getGeolocAdresse());
            }
        }
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
