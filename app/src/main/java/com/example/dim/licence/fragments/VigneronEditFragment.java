package com.example.dim.licence.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.dim.licence.R;
import com.example.dim.licence.VigneronActivity;
import com.example.dim.licence.dialogs.VigneronDialogs;
import com.example.dim.licence.entities.Geolocalisation;
import com.example.dim.licence.entities.Vigneron;
import com.example.dim.licence.entities.Ville;
import com.example.dim.licence.models.MasterModel;
import com.example.dim.licence.utils.interfaces.CrudFragmentInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.example.dim.licence.MainActivity.ARG_DEBUG;
import static com.example.dim.licence.utils.commons.Commons.DIALOG_CANCEL;
import static com.example.dim.licence.utils.commons.Commons.DIALOG_TYPE;
import static com.example.dim.licence.utils.commons.Commons.MAIL_REGEX;
import static com.example.dim.licence.utils.commons.Commons.TEL_REGEX;
import static com.example.dim.licence.utils.commons.Commons.VILLE_ID;
import static com.example.dim.licence.utils.commons.Commons.VILLE_LIBELLE;
import static com.example.dim.licence.utils.commons.Commons.VILLE_ZIP_CODE;

/**
 * A simple {@link Fragment} subclass.
 */
public class VigneronEditFragment extends Fragment {

    private CrudFragmentInterface<Vigneron> activityCallback;
    private Vigneron item;
    private Ville ville;
    private MasterModel model;

    private TextInputEditText et_libelle;
    private TextInputEditText et_domaine;
    private TextInputEditText et_fixe;
    private TextInputEditText et_mobile;
    private TextInputEditText et_mail;
    private TextInputEditText et_fax;
    private AutoCompleteTextView et_ville;
    private TextInputEditText et_adresse1;
    private TextInputEditText et_adresse2;
    private TextInputEditText et_adresse3;
    private TextInputEditText et_complement;
    private TextInputEditText et_comment;
    private Button btnCancel;
    private Button btnSave;

    private List<Ville> villes;

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void afterTextChanged(Editable editable) {
            String input = et_ville.getText().toString();
            Log.i(ARG_DEBUG, "afterTextChanged: " + et_ville.getText().toString());

            if (input.length() > 0) {
                if (!input.contains("'")) {
                    villes = input.matches("[0-9]{1,}") ?
                            model.filterVilleByZipCode(input) :
                            model.filterVilleByLibelle(input);
                }
            }

            if (villes != null) {
                makeFilteredList();
            }
        }
    };

    public VigneronEditFragment() {
        // Required empty public constructor
    }

    public static VigneronEditFragment newInstance() {
        VigneronEditFragment fragment = new VigneronEditFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
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
        et_ville = rootView.findViewById(R.id.edt_vigneron_ville);
        et_adresse1 = rootView.findViewById(R.id.edt_vigneron_adresse1);
        et_adresse2 = rootView.findViewById(R.id.edt_vigneron_adresse2);
        et_adresse3 = rootView.findViewById(R.id.edt_vigneron_adresse3);
        et_complement = rootView.findViewById(R.id.edt_vigneron_complement);
        et_comment = rootView.findViewById(R.id.edt_vigneron_comment_value);
        btnSave = rootView.findViewById(R.id.btnSave);
        btnCancel = rootView.findViewById(R.id.btnCancel);

        model = MasterModel.getInstance(getContext());
        villes = new ArrayList<>();
        //villes = model.getFirst15Villes();
        //makeFilteredList();

       /* et_ville.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    et_ville.addTextChangedListener(textWatcher);
                }
            }
        });*/

        et_ville.addTextChangedListener(textWatcher);

        et_ville.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                HashMap<String, String> sMap = (HashMap<String, String>) adapterView.getAdapter().getItem(i);
                int id = Integer.valueOf(sMap.get(VILLE_ID));
                ville = model.getVilleById(id);
               /* et_ville.removeTextChangedListener(textWatcher);*/
                et_ville.setText(ville.getVilleLibelle());
                Log.e("MainActivity", "OnItemClick Method Working..");
//                Toast.makeText(getContext(), adapterView.getAdapter().getItem(i).toString(), Toast.LENGTH_SHORT).show();
            }
        });


        btnCancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle b = new Bundle();
                b.putString(DIALOG_TYPE, DIALOG_CANCEL);
                VigneronDialogs vigneronDialogs = VigneronDialogs.newInstance(b);
                vigneronDialogs.show(getActivity().getSupportFragmentManager(),
                        DIALOG_CANCEL);
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
                        ((VigneronActivity) getActivity()).setIsNewMode();
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

    private void makeFilteredList() {
        List<HashMap<String, String>> list = new ArrayList<>();
        HashMap<String, String> map = null;
        for (Ville ville :
                villes) {
            Log.i(ARG_DEBUG, "makeFilteredList: "+ville.toString());
            map = new HashMap<>();
            map.put(VILLE_ID, String.valueOf(ville.getVilleId()));
            map.put(VILLE_LIBELLE, ville.getVilleLibelle());
            map.put(VILLE_ZIP_CODE, ville.getVilleZipCode());
            list.add(map);
        }
//        et_ville.getAdapter().getCount();
        et_ville.setAdapter(new SimpleAdapter(getContext(), list, R.layout.autocomplete_list_item,
                new String[]{VILLE_LIBELLE, VILLE_ZIP_CODE}, new int[]{R.id.ac_li_ville, R.id.ac_li_zip}));
        //Log.i(ARG_DEBUG, "makeFilteredList: POP SHOWN : "+et_ville.isPopupShowing());
        //if ()
        //if ()
        //et_ville.showDropDown();
        Log.i(ARG_DEBUG, "makeFilteredList:----------- itemscount "+et_ville.getAdapter().getCount());
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
            if (ville != null) {
                geoloc.setGeolocVille(ville);
            }

            geoloc.setGeolocAdresse1(et_adresse1.getText().toString());
            geoloc.setGeolocAdresse2(et_adresse2.getText().toString());
            geoloc.setGeolocAdresse3(et_adresse3.getText().toString());
            geoloc.setGeolocComplement(et_complement.getText().toString());

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

        if (et_adresse1.getText().toString().length() >= 200) {
            Toast.makeText(getContext(), "Chaque champs d'adresse ne peut avoir plus de 200 caractères !", Toast.LENGTH_LONG).show();
            et_adresse1.requestFocus();
            return true;
        }

        if (et_adresse2.getText().toString().length() >= 200) {
            Toast.makeText(getContext(), "Chaque champs d'adresse ne peut avoir plus de 200 caractères !", Toast.LENGTH_LONG).show();
            et_adresse2.requestFocus();
            return true;
        }

        if (et_adresse3.getText().toString().length() >= 200) {
            Toast.makeText(getContext(), "Chaque champs d'adresse ne peut avoir plus de 200 caractères !", Toast.LENGTH_LONG).show();
            et_adresse3.requestFocus();
            return true;
        }

        if (et_complement.getText().toString().length() > 3) {
            Toast.makeText(getContext(), "Le complément ne peut avoir plus de 3 caractères !", Toast.LENGTH_LONG).show();
            et_complement.requestFocus();
            return true;
        }
        return false;
    }

    private boolean isEditTextValid(EditText editText, String regex) {
        if (!editText.getText().toString().isEmpty()) {
            if (!editText.getText().toString().matches(regex)) {
                Toast.makeText(getContext(), "Le champs " + editText.getHint() + " est invalide !", Toast.LENGTH_LONG).show();
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
                et_ville.setText(item.getVigneronGeoloc().getGeolocVille() == null ? ""
                        : item.getVigneronGeoloc().getGeolocVille().getVilleLibelle());
                et_adresse1.setText(item.getVigneronGeoloc().getGeolocAdresse1() == null ? ""
                        : item.getVigneronGeoloc().getGeolocAdresse1());
                et_adresse2.setText(item.getVigneronGeoloc().getGeolocAdresse2() == null ? ""
                        : item.getVigneronGeoloc().getGeolocAdresse2());
                et_adresse3.setText(item.getVigneronGeoloc().getGeolocAdresse3() == null ? ""
                        : item.getVigneronGeoloc().getGeolocAdresse3());
                et_complement.setText(item.getVigneronGeoloc().getGeolocComplement() == null ? ""
                        : item.getVigneronGeoloc().getGeolocComplement());
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

    public List<Ville> getVilles() {
        return villes;
    }
}