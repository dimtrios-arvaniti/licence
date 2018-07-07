package com.example.dim.licence.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AlertDialog.Builder;
import android.support.v7.view.ContextThemeWrapper;
import android.util.Log;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Toast;

import com.example.dim.licence.R;
import com.example.dim.licence.entities.Cave;
import com.example.dim.licence.utils.interfaces.CrudDialogsInterface;

import static com.example.dim.licence.CaveActivity.C_DIALOG_TYPE;
import static com.example.dim.licence.CaveActivity.C_FILTER_TYPE;
import static com.example.dim.licence.CaveActivity.C_SELECTED;
import static com.example.dim.licence.MainActivity.ARG_DEBUG;

public class CaveDialogs extends DialogFragment {

    private CrudDialogsInterface<Cave> activityCallback;
    private final String[] changeImageOptions = new String[]{"Prendre une photo", "Choisir dans ma galerie"};
    private final String[] filterData = new String[]{"AUCUN", "NOM", "TYPE", "APPELATION", "VIGNERON", "FAVORIS"};
    //private final String[] contactData = new String[]{"FIXE", "MOBILE", "FAX", "MAIL"};

   // private String contactSelected;
    private LinearLayout filterSearchLayout; // layout containing filterDialog editText
    private EditText filterSearchEdit;
    private String filterSelected;
    private String changeImageSelection;

    public static CaveDialogs newInstance(Bundle args) {
        CaveDialogs dialog = new CaveDialogs();
        dialog.setArguments(args);
        return dialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // ContextThemeWrapper is needed for custom content in dialogs
        AlertDialog.Builder builder = new Builder(new ContextThemeWrapper(getContext(), R.style.AppTheme_Dialog));
        String dialogType = getArguments().getString(C_DIALOG_TYPE);

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
            default:
                Log.i(ARG_DEBUG, "onCreateDialog: Unkonwn dialog type for '" + dialogType + "'value !");
                break;
        }

        return builder.create();
    }


    private void makeDeleteDialog(Builder builder) {
        builder.setTitle("SUPPRIMER").setMessage("Etes vous sûr de vouloir supprimer "
                + getArguments().getString(C_SELECTED)
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
                        Cave cave = new Cave(getArguments().getBundle(C_SELECTED));
                        activityCallback.save(cave);
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

        int currentChoiceIndex = getFilterChoiceIndex(getArguments().getString(C_FILTER_TYPE));

        builder.setTitle("FILTRER")
                .setView(filterSearchLayout)
                .setSingleChoiceItems(filterData, currentChoiceIndex
                        , new OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                filterSelected = filterData[i];
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

    @NonNull
    private void initFilterDialog() {
        filterSearchEdit = new EditText(getContext());
        filterSearchEdit.setHint(R.string.chercher);
        filterSearchEdit.setGravity(Gravity.CENTER);

        filterSearchLayout = new LinearLayout(getContext());
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        filterSearchLayout.setOrientation(LinearLayout.VERTICAL);
        filterSearchLayout.setLayoutParams(params);

        filterSearchLayout.setPadding(2, 2, 2, 2);
        filterSearchLayout.addView(filterSearchEdit);

    }

    private int getFilterChoiceIndex(String currentChoice) {
        return currentChoice.equalsIgnoreCase("NONE") ? 0 :
                currentChoice.equalsIgnoreCase("NOM") ? 1 :
                        currentChoice.equalsIgnoreCase("APPELATION") ? 2 :
                                currentChoice.equalsIgnoreCase("TYPE") ? 3 :
                                        currentChoice.equalsIgnoreCase("VIGNERON") ? 4 :
                                                currentChoice.equalsIgnoreCase("FAVORIS") ? 5 : -1;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            activityCallback = (CrudDialogsInterface<Cave>) getActivity();
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
