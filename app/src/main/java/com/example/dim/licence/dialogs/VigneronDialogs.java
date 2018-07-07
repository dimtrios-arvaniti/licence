package com.example.dim.licence.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AlertDialog.Builder;
import android.support.v7.view.ContextThemeWrapper;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

import com.example.dim.licence.R;
import com.example.dim.licence.VigneronActivity;
import com.example.dim.licence.entities.Vigneron;
import com.example.dim.licence.utils.interfaces.CrudDialogsInterface;

import static com.example.dim.licence.MainActivity.ARG_DEBUG;
import static com.example.dim.licence.VigneronActivity.V_AVAILABLE_CONTACT_TYPES;
import static com.example.dim.licence.VigneronActivity.V_CONTACT_TYPE;
import static com.example.dim.licence.VigneronActivity.V_DIALOG_TYPE;
import static com.example.dim.licence.VigneronActivity.V_FILTER_TYPE;
import static com.example.dim.licence.VigneronActivity.V_SELECTED;

public class VigneronDialogs extends android.support.v4.app.DialogFragment {

    private CrudDialogsInterface<Vigneron> activityCallback;
    private final String[] filterOptions = new String[]{"AUCUN", "NOM", "DOMAINE", "PAYS", "VILLE", "CODE POSTAL"};
    private final String[] contactOptions = new String[]{"FIXE", "MOBILE", "MAIL"};

    private String contactSelected;
    private LinearLayout filterSearchLayout; // layout containing filterDialog editText
    private EditText filterSearchEdit;
    private String filterSelected;

    /**
     * Layout container for optionsLayout
     */
    private LinearLayout contactOptionsLayout;
    /**
     * Radio group for contact options selection
     */
    private RadioGroup contactOptionsRadio; //

    /**
     * Fixe phone RadioButton
     */
    RadioButton rbFixe;
    /**
     * Mobile phone RadioButton
     */
    RadioButton rbMobile;
    /**
     * Mail RadioButton
     */
    RadioButton rbMail;


    public static VigneronDialogs newInstance(Bundle args) {
        VigneronDialogs dialog = new VigneronDialogs();
        dialog.setArguments(args);
        return dialog;
    }


    /**
     * Overridable method from DialogFragment to allow custom content in dialog view.
     * Activity [X_]DIALOG_TYPE must be defined by having called newInstance before use.
     *
     * @param savedInstanceState Bundle - state
     * @return Dialog - the corresponding type dialog
     */
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new Builder(new ContextThemeWrapper(getContext(), R.style.AppTheme_Dialog));
        String dialogType = getArguments().getString(V_DIALOG_TYPE);

        switch (dialogType) {
            case "FILTER":
                makeFilterDialog(builder);
                break;
            case "CANCEL":
                makeCancelDialog(builder);
                break;
            case "SAVE":
                makeSaveDialog(builder);
                break;
            case "DELETE":
                makeDeleteDialog(builder);
                break;
            case "CONTACT":
                makeContactTypeDialog(builder);
                break;
            default:
                Log.i(ARG_DEBUG, "onCreateDialog: Unkonwn dialog type for '" + dialogType + "'value !");
                break;
        }

        return builder.create();
    }


    private void makeDeleteDialog(Builder builder) {
        builder.setTitle("SUPPRIMER").setMessage("Etes vous sûr de vouloir supprimer "
                + getArguments().getString(V_SELECTED)
                + " ? \nLes données seront perdues !")
                .setNegativeButton("Non", null)
                .setPositiveButton("Oui", new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        activityCallback.delete();
                    }
                });
    }

    private void makeSaveDialog(Builder builder) {
        builder.setTitle("SAUVEGARDER").setMessage("Etes vous sûr de vouloir sauvegarder ?" +
                "\nLes anciennes données seront perdues !")
                .setNegativeButton("Non", null)
                .setPositiveButton("Oui", new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Vigneron v = new Vigneron(getArguments().getBundle(V_SELECTED));
                        activityCallback.save(v);
                    }
                });
    }

    private void makeCancelDialog(Builder builder) {
        builder.setTitle("ANNULER").setMessage("Etes vous sûr de vouloir annuler ?" +
                "\nLes données actuelles seront perdues !")
                .setNegativeButton("Non", null)
                .setPositiveButton("Oui", new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (activityCallback.isNewMode()) {
                            activityCallback.goToPage(0);
                        } else {
                            activityCallback.goToPage(1);
                        }
                    }
                });
    }

    private void makeFilterDialog(Builder builder) {
        if (filterSearchLayout == null) {
            initFilterDialog();
        }

        int currentChoiceIndex = getFilterChoiceIndex(getArguments().getString(V_FILTER_TYPE));

        builder.setTitle("FILTRER")
                .setView(filterSearchLayout)
                .setSingleChoiceItems(filterOptions, currentChoiceIndex
                        , new OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                filterSelected = filterOptions[i];
                            }
                        })
                .setNegativeButton("Annuler", null)
                .setPositiveButton("Valider", new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (filterSearchEdit.getText().toString().isEmpty() && !filterSelected.equalsIgnoreCase("NONE")) {
                            Toast.makeText(getContext(), "Aucune recherche n'à été spécifié !", Toast.LENGTH_LONG).show();
                            return;
                        }
                        if (!filterSearchEdit.getText().toString().isEmpty() && filterSelected.equalsIgnoreCase("NONE")) {
                            Toast.makeText(getContext(), "Auncun filtre, la recherche saisie n'à pas été effectué !", Toast.LENGTH_LONG).show();
                            activityCallback.applyFilter(filterSelected, "");
                            Log.i(ARG_DEBUG, "onClick: SEND FILTER " + filterSelected + " WITH EMPTY VALUE ");
                            return;
                        }

                        activityCallback.applyFilter(filterSelected, filterSearchEdit.getText().toString());
                        Log.i(ARG_DEBUG, "onClick: SEND FILTER " + filterSelected + " WITH VALUE " + filterSearchEdit.getText().toString());
                    }
                });

    }

    private void makeContactTypeDialog(Builder builder) {
        final int contactIndex = getContactChoiceIndex(getArguments().getString(V_CONTACT_TYPE));
        if (contactOptionsLayout == null) {
            initContactDialog();
        }
        builder.setTitle("CONTACTER")
                .setView(contactOptionsLayout)
                .setNegativeButton("Annuler", null)
                .setPositiveButton("Valider", new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // DO NOT UNCOMMENT ! Directly handled in contactOptionsLayout
                        // contactSelected = contactOptions[contactIndex];

                        ((VigneronActivity) getActivity()).applyContact(contactSelected);
                    }
                });
    }

    @NonNull
    private void initContactDialog() {
        boolean types[] = getArguments().getBooleanArray(V_AVAILABLE_CONTACT_TYPES);

        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        contactOptionsLayout = new LinearLayout(getContext());
        contactOptionsLayout.setLayoutParams(layoutParams);
        View view = getLayoutInflater().inflate(R.layout.dialog_contact_options, contactOptionsLayout);
        contactOptionsRadio = view.findViewById(R.id.dial_contact_opt_layout);
        rbFixe = view.findViewById(R.id.dial_contact_opt_dial);
        rbMobile = view.findViewById(R.id.dial_contact_opt_mobile);
        rbMail = view.findViewById(R.id.dial_contact_opt_mail);

        rbFixe.setEnabled(types[0]);
        rbMobile.setEnabled(types[1]);
        rbMail.setEnabled(types[2]);

        contactOptionsRadio.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
               switch (i) {
                   case R.id.dial_contact_opt_dial:
                       contactSelected = contactOptions[0];
                       contactOptionsRadio.check(R.id.dial_contact_opt_dial);
                       break;
                   case R.id.dial_contact_opt_mail:
                       contactSelected = contactOptions[1];
                       contactOptionsRadio.check(R.id.dial_contact_opt_mail);
                       break;
                   case R.id.dial_contact_opt_mobile:
                       contactSelected = contactOptions[2];
                       contactOptionsRadio.check(R.id.dial_contact_opt_mobile);
                       break;
               }
            }
        });

    }



    // If exception thrown remove textInputLayout
    @NonNull
    private void initFilterDialog() {
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

        filterSearchEdit = new TextInputEditText(getContext());
        filterSearchEdit.setHint(R.string.chercher);
        filterSearchEdit.setGravity(Gravity.CENTER);

        TextInputLayout textInputLayout = new TextInputLayout(getContext());
        textInputLayout.setLayoutParams(layoutParams);
        textInputLayout.addView(filterSearchEdit);

        filterSearchLayout = new LinearLayout(getContext());
        filterSearchLayout.setOrientation(LinearLayout.VERTICAL);
        filterSearchLayout.setLayoutParams(layoutParams);

        filterSearchLayout.setPadding(2, 2, 2, 2);
        filterSearchLayout.addView(textInputLayout);

    }

    private int getFilterChoiceIndex(String currentChoice) {
        return currentChoice.equalsIgnoreCase("NONE") ? 0 :
                currentChoice.equalsIgnoreCase("NOM") ? 1 :
                        currentChoice.equalsIgnoreCase("DOMAINE") ? 2 :
                                currentChoice.equalsIgnoreCase("PAYS") ? 3 :
                                        currentChoice.equalsIgnoreCase("VILLE") ? 4 :
                                                currentChoice.equalsIgnoreCase("CODE POSTAL") ? 5 : -1;
    }

    private int getContactChoiceIndex(String currentChoice) {
        return currentChoice.equalsIgnoreCase("FIXE") ? 0 :
                currentChoice.equalsIgnoreCase("MOBILE") ? 1 :
                        currentChoice.equalsIgnoreCase("MAIL") ? 2 : -1;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            activityCallback = (CrudDialogsInterface<Vigneron>) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString()
                    + " must implement CrudDialogsInterface<T>");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        activityCallback = null;
    }
}