package com.example.dim.licence.fragments;

import android.Manifest.permission;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog.Builder;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatSpinner;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.dim.licence.CaveActivity;
import com.example.dim.licence.R;
import com.example.dim.licence.VigneronActivity;
import com.example.dim.licence.dialogs.CaveDialogs;
import com.example.dim.licence.entities.Appelation;
import com.example.dim.licence.entities.Cave;
import com.example.dim.licence.entities.TypeVin;
import com.example.dim.licence.entities.Vigneron;
import com.example.dim.licence.entities.Vin;
import com.example.dim.licence.utils.interfaces.CrudFragmentInterface;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import static android.app.Activity.RESULT_OK;
import static com.example.dim.licence.MainActivity.ARG_DEBUG;
import static com.example.dim.licence.utils.commons.Commons.APLN_;
import static com.example.dim.licence.utils.commons.Commons.APLN_COUNT;
import static com.example.dim.licence.utils.commons.Commons.APLN_ID;
import static com.example.dim.licence.utils.commons.Commons.APLN_LABEL;
import static com.example.dim.licence.utils.commons.Commons.DIALOG_CANCEL;
import static com.example.dim.licence.utils.commons.Commons.DIALOG_TYPE;
import static com.example.dim.licence.utils.commons.Commons.IMAGE_RESIZE;
import static com.example.dim.licence.utils.commons.Commons.TVIN_;
import static com.example.dim.licence.utils.commons.Commons.TVIN_COUNT;
import static com.example.dim.licence.utils.commons.Commons.TVIN_ID;
import static com.example.dim.licence.utils.commons.Commons.TVIN_LABEL;
import static com.example.dim.licence.utils.commons.Commons.VIGN_;
import static com.example.dim.licence.utils.commons.Commons.VIGN_COUNT;
import static com.example.dim.licence.utils.commons.Commons.VIGN_ID;
import static com.example.dim.licence.utils.commons.Commons.VIGN_INTENT_RETURN;
import static com.example.dim.licence.utils.commons.Commons.VIGN_LABEL;
import static com.example.dim.licence.utils.commons.Commons.YEAR_REGEX;
import static com.example.dim.licence.utils.commons.Commons.vinDateFormat;

public class CaveEditFragment extends Fragment {

    private static final int ARG_CAMERA_INTENT = 1;
    private static final int ARG_FILE_CHOOSE_INTENT = 2;
    public static final int ARG_VIGNERON_INTENT = 4;
    private static final int PERMISSION_REQUEST_CAMERA_TAG = 9;
    private static final int PERMISSION_REQUEST_WRITE_STORAGE_TAG = 7;

    private CrudFragmentInterface<Cave> activityCallback;
    private Cave item;

    private EditText et_libelle;
    private AppCompatSpinner sp_appelation;
    private AppCompatSpinner sp_type;
    private AppCompatSpinner sp_vigneron;
    private EditText et_annee;
    private EditText et_annee_max;
    private EditText et_commentaire;
    private EditText et_qte;
    private EditText et_prix;
    // used to switch background only ..!
    private FloatingActionButton fabFavorisOn;
    private FloatingActionButton fabFavorisOff;

    private String[] imageChangeOptions;// = new String[]{"Prendre une photo", "Choisir dans mes fichiers"};
    private AppCompatImageView iv_image;
    private Button btnNewVigneron;
    private Button btnCancel;
    private Button btnSave;
    private List<Vigneron> vigneronsBackingList;
    private List<HashMap<String, String>> appelations;
    private List<HashMap<String, String>> typevins;
    private List<HashMap<String, String>> vignerons;

    private Uri pictureUri;// picturePath;
    private boolean photo;


    public CaveEditFragment() {
        // Required empty public constructor
    }

    public static CaveEditFragment newInstance(Bundle dictionnary) {
        CaveEditFragment fragment = new CaveEditFragment();
        fragment.setArguments(dictionnary);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_cave_edit, container, false);
        et_libelle = rootView.findViewById(R.id.edt_cave_label);
        sp_appelation = rootView.findViewById(R.id.edt_cave_appelation);
        sp_type = rootView.findViewById(R.id.edt_cave_type);
        sp_vigneron = rootView.findViewById(R.id.edt_cave_vigneron);
        et_annee = rootView.findViewById(R.id.edt_cave_annee);
        et_annee_max = rootView.findViewById(R.id.edt_cave_annee_max);
        et_commentaire = rootView.findViewById(R.id.edt_cave_commentaire);
        et_qte = rootView.findViewById(R.id.edt_cave_qte);
        et_prix = rootView.findViewById(R.id.edt_cave_prix);
        btnNewVigneron = rootView.findViewById(R.id.edt_cave_plus);
        btnSave = rootView.findViewById(R.id.cave_btnSave);
        btnCancel = rootView.findViewById(R.id.cave_btnCancel);
        fabFavorisOn = rootView.findViewById(R.id.edt_cave_favorisOn);
        fabFavorisOff = rootView.findViewById(R.id.edt_cave_favorisOff);
        iv_image = rootView.findViewById(R.id.edt_cave_image);

        // retrieving values for spinners
        initFragmentDataFromBundle();


        // init favorite state
        fabFavorisOn.setVisibility(View.GONE);
        fabFavorisOff.setVisibility(View.VISIBLE);

        // setting simple list adapter to spinners
        sp_appelation.setAdapter(new SimpleAdapter(getContext(),
                appelations, R.layout.spinner_standard_layout,
                new String[]{APLN_LABEL}, new int[]{R.id.sp_text}));

        sp_type.setAdapter(new SimpleAdapter(getContext(),
                typevins, R.layout.spinner_standard_layout,
                new String[]{TVIN_LABEL}, new int[]{R.id.sp_text}));

        sp_vigneron.setAdapter(new SimpleAdapter(getContext(),
                vignerons, R.layout.spinner_standard_layout,
                new String[]{VIGN_LABEL}, new int[]{R.id.sp_text}));

        // and click listeners to buttons
        btnCancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle b = new Bundle();
                b.putString(DIALOG_TYPE, DIALOG_CANCEL);
                CaveDialogs dialogs = CaveDialogs.newInstance(b);
                dialogs.show(getActivity().getSupportFragmentManager(),
                        DIALOG_CANCEL);
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateInput()) {
                    Cave cave = updateCaveFromView();
                    activityCallback.onSaveClick(cave);
                    pictureUri = null;
                }
            }
        });

        btnNewVigneron.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                new Builder(getContext()).setTitle("Créer un nouveau vigneron")
                        .setMessage("Etes-vous sûr de vouloir créer un nouveau vigneron ?")
                        .setNegativeButton(R.string.annuler, null)
                        .setPositiveButton(R.string.valider, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                launchVigneronIntent();
                            }
                        }).show();

            }
        });

        fabFavorisOff.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                // change item value
                item.setCaveFavoris(!item.isCaveFavoris());
                handleFavoriteButton();

            }
        });

        fabFavorisOn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                // change item value
                item.setCaveFavoris(!item.isCaveFavoris());
                handleFavoriteButton();

            }
        });

        iv_image.setOnClickListener(new OnClickListener() {
            String changeImageSelection = "";

            @Override
            public void onClick(View view) {
                handleOnImageChangeClick();
            }

            private void handleOnImageChangeClick() {
                new Builder(getContext()).setTitle("Action")
                        .setSingleChoiceItems(R.array.cave_image_selection_type, -1,
                                new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                changeImageSelection = imageChangeOptions[i];
                            }
                        })
                        .setNegativeButton(R.string.annuler, null)
                        .setPositiveButton(R.string.valider, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // return in unvalid selection
                                if (changeImageSelection.isEmpty()) {
                                    Toast.makeText(getContext(), "Selection invalide !",
                                            Toast.LENGTH_SHORT).show();
                                    return;
                                }

                                if (changeImageSelection.equalsIgnoreCase(getResources()
                                        .getString(R.string.cave_image_prendre_photo))) {
                                    handleCameraIntent();
                                } else {
                                    handleFileChooserIntent();
                                }
                            }
                        }).show();
            }
        });

        return rootView;
    }

    private void launchVigneronIntent() {
        Intent intent = new Intent(getActivity(), VigneronActivity.class);
        intent.putExtra("VIGNERON_PAGE", 2);
        intent.putExtra("VIGNERON_FROM_CAVE", true);
        startActivityForResult(intent, ARG_VIGNERON_INTENT);
    }

    private void handleFileChooserIntent() {
        if (ContextCompat.checkSelfPermission(getContext(),
                permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestFilesPermission();
        }

        if (ContextCompat.checkSelfPermission(getContext(),
                permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            launchFileChooser();
        }
    }

    private void handleCameraIntent() {
        if (testCameraExists()) {

            if (ContextCompat.checkSelfPermission(getContext(),
                    permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                requestCameraPermission();
            }

            // check for permissions and launch prompt if necessary
            if (ContextCompat.checkSelfPermission(getContext(),
                    permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestFilesPermission();
            }

            // if all necessary permissions are granted, launch camera
            if (ContextCompat.checkSelfPermission(getContext(),
                    permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(getContext(),
                    permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                launchCameraIntent();
            }
        }
    }

    private void handleFavoriteButton() {
        // switch buttons visibility
        fabFavorisOn.setVisibility(item.isCaveFavoris() ? View.VISIBLE : View.GONE);
        fabFavorisOff.setVisibility(item.isCaveFavoris() ? View.GONE : View.VISIBLE);
    }

    private void requestCameraPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                permission.CAMERA)) {
            // make dialog prompt to explain why permission is needed
            new Builder(getContext())
                    .setTitle("Autorisation")
                    .setMessage(R.string.permission_camera_rationale)
                    .setNegativeButton(R.string.annuler, null)
                    .setPositiveButton(R.string.valider, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            // prompt permission if user agrees
                            ActivityCompat.requestPermissions(getActivity(),
                                    new String[]{permission.CAMERA},
                                    PERMISSION_REQUEST_CAMERA_TAG);
                        }
                    }).show();

        } else {
            // No explanation needed; request the permission
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{permission.CAMERA},
                    PERMISSION_REQUEST_CAMERA_TAG);
        }
    }

    private void launchFileChooser() {
        Intent intent;

        if (Build.VERSION.SDK_INT < 19) {
            intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);

            PackageManager packageManager = getActivity().getPackageManager();
            List<ResolveInfo> activities = packageManager.queryIntentActivities(intent, 0);
            boolean isIntentSafe = activities.size() > 0;

            // Start an activity if it's safe
            if (isIntentSafe) {
                startActivityForResult(
                        Intent.createChooser(intent, "Séléctionnez une image"),
                        ARG_FILE_CHOOSE_INTENT);
            } else {
                Toast.makeText(getActivity(), getResources().getString(R.string.file_app_required),
                        Toast.LENGTH_SHORT).show();
            }

        } else {
            intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("image/*");

            PackageManager packageManager = getActivity().getPackageManager();
            List<ResolveInfo> activities = packageManager.queryIntentActivities(intent, 0);
            boolean isIntentSafe = activities.size() > 0;

            // Start an activity if it's safe
            if (isIntentSafe) {
                startActivityForResult(
                        Intent.createChooser(intent, "Séléctionnez une image"),
                        ARG_FILE_CHOOSE_INTENT);
            } else {
                Toast.makeText(getActivity(), getResources().getString(R.string.file_app_required),
                        Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void requestFilesPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                permission.WRITE_EXTERNAL_STORAGE)) {

            new Builder(getContext()).setTitle("Autorisation")
                    .setMessage(R.string.permission_files_rationale)
                    .setNegativeButton(R.string.annuler, null)
                    .setPositiveButton(R.string.valider, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ActivityCompat.requestPermissions(getActivity(),
                                    new String[]{permission.CAMERA},
                                    PERMISSION_REQUEST_CAMERA_TAG);
                        }
                    }).show();
        } else {
            // No explanation needed; request the permission
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{permission.WRITE_EXTERNAL_STORAGE},
                    PERMISSION_REQUEST_WRITE_STORAGE_TAG);
        }
    }


    private void initFragmentDataFromBundle() {
        imageChangeOptions = new String[]{getResources().getString(R.string.cave_image_prendre_photo),
                getResources().getString(R.string.cave_image_choisir_galerie)};
        // init vars
        appelations = new ArrayList<>();
        int count = getArguments().getInt(APLN_COUNT, -1);

        if (count != -1) {
            HashMap<String, String> row;
            Appelation appelation;
            // add to list for simpleAdapter
            for (int i = 0; i < count; i++) {
                row = new HashMap<>();
                appelation = new Appelation(getArguments().getBundle(APLN_ + i));
                row.put(APLN_LABEL, appelation.getAppelationLibelle());
                row.put(APLN_ID, String.valueOf(appelation.getAppelationId()));
                appelations.add(i, row);
            }
        }

        typevins = new ArrayList<>();
        count = getArguments().getInt(TVIN_COUNT, -1);

        if (count != -1) {
            HashMap<String, String> row;
            TypeVin type;

            for (int i = 0; i < count; i++) {
                row = new HashMap<>();
                type = new TypeVin(getArguments().getBundle(TVIN_ + i));
                row.put(TVIN_LABEL, type.getTypeVinLibelle());
                row.put(TVIN_ID, String.valueOf(type.getTypeVinId()));
                typevins.add(i, row);
            }
        }

        vignerons = new ArrayList<>();
        vigneronsBackingList = new ArrayList<>();
        count = getArguments().getInt(VIGN_COUNT, -1);

        if (count != -1) {
            HashMap<String, String> row;
            Vigneron vigneron;

            // adding data to list
            for (int i = 0; i < count; i++) {
                row = new HashMap<>();

                vigneron = new Vigneron(getArguments().getBundle(VIGN_ + i));
                row.put(VIGN_LABEL, vigneron.getVigneronLibelle());
                row.put(VIGN_ID, String.valueOf(vigneron.getVigneronId()));
                vignerons.add(row);
                vigneronsBackingList.add(vigneron);
            }
        }

    }

    private void printData() {
        StringBuilder dataStringBuilder = new StringBuilder();
        dataStringBuilder.append("\n\nVIGNERONS");
        for (HashMap<String, String> map :
                vignerons) {
            for (Entry<String, String> entry : map.entrySet()) {
                dataStringBuilder.append("KEY=[").append(entry.getKey()).append("]   - VALUE=[")
                        .append(entry.getValue()).append("]\n");
            }
        }

        dataStringBuilder.append("\n\nTYPES VINS");
        for (HashMap<String, String> map :
                typevins) {
            for (Entry<String, String> entry : map.entrySet()) {
                dataStringBuilder.append("KEY=[").append(entry.getKey()).append("]   - VALUE=[")
                        .append(entry.getValue()).append("]\n");
            }
        }

        dataStringBuilder.append("\n\nAPPELATIONS");
        for (HashMap<String, String> map :
                appelations) {
            for (Entry<String, String> entry : map.entrySet()) {
                dataStringBuilder.append("KEY=[").append(entry.getKey()).append("]   - VALUE=[")
                        .append(entry.getValue()).append("]\n");
            }
        }

        Log.i(ARG_DEBUG, "initFragmentDataFromBundle: " + dataStringBuilder.toString());
    }

    private File createPictureFile() throws IOException {
        String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmmss").format(new Date());
        String fileName = "WineRoom_" + timeStamp;
        File pictureStorageDir = getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File appStorageDir = new File(pictureStorageDir.getAbsolutePath());

        if (!appStorageDir.exists()) {
            appStorageDir.mkdir();
            //Log.i(ARG_DEBUG, "createPictureFile: CREATED ? "+appStorageDir.exists());;
        } else {
            Log.i(ARG_DEBUG, "createPictureFile: ALREADY EXISTS !");
        }
        String path = appStorageDir + fileName + ".jpg";
        File picture = new File(path);
        if (!picture.exists()) {
            picture.createNewFile();
        }
        String picturePath = picture.getAbsolutePath();
        pictureUri = FileProvider.getUriForFile(getContext(),
                "com.example.android.fileprovider",
                picture);
        //Log.i(ARG_DEBUG, "createPictureFile: "+picturePath);
        return picture;
    }

    private void launchCameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) { // if camera app exists
            File pictureFile = null;
            try {
                pictureFile = createPictureFile();
            } catch (IOException exc) {
                Log.e(ARG_DEBUG, "launchCameraIntent: ERROR DURING FILE CREATION !", exc);
            }

            if (pictureFile != null) {
                pictureUri = FileProvider.getUriForFile(getActivity(),
                        "com.example.android.fileprovider",
                        pictureFile);

                // launch camera
                intent.putExtra(MediaStore.EXTRA_OUTPUT, pictureUri);
                startActivityForResult(intent, ARG_CAMERA_INTENT);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ARG_CAMERA_INTENT) {
            if (resultCode == RESULT_OK) {

                // as MediaStore.EXTRA_OUTPUT was used to launch camera intent
                // data value is going to be null !
                try {

                    File pictureStorageDir = getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                    File appStorageDir = new File(pictureStorageDir.getAbsolutePath());
                    String filePath = appStorageDir + pictureUri.getPath();

                    //Bitmap bitmap = BitmapFactory.decodeFile(picturePath);
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), pictureUri);

                    // setting to imageView
                    if (null != bitmap) {
                        item.getCaveVin().setVinImage(filePath);
                        iv_image.setImageBitmap(getResizedBitmap(bitmap));
                    }

                    Toast.makeText(getContext(), "Image changée !", Toast.LENGTH_LONG).show();
                } catch (IOException e) {
                    Log.e(ARG_DEBUG, "onActivityResult: ", e);
                }
            }
            return;
        }

        if (requestCode == ARG_FILE_CHOOSE_INTENT) {
            if (resultCode == RESULT_OK) {


                if (Build.VERSION.SDK_INT < 19) {
                    pictureUri = data.getData();
                } else {
                    pictureUri = data.getData();
                    final int takeFlags = data.getFlags()
                            & (Intent.FLAG_GRANT_READ_URI_PERMISSION
                            | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

                    try {
                        getActivity().getContentResolver().takePersistableUriPermission(pictureUri, takeFlags);
                    } catch (SecurityException e) {
                        e.printStackTrace();
                    }
                }
                //Log.i(ARG_DEBUG, "onActivityResult: " + pictureUri.getPath());

                Log.i(ARG_DEBUG, "onActivityResult: " + pictureUri.getPath());

                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), pictureUri);
                    // setting to imageView
                    if (null != bitmap) {
                        iv_image.setImageBitmap(getResizedBitmap(bitmap));
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
                return;
            }
        }

        if (requestCode == ARG_VIGNERON_INTENT) {
            if (resultCode == RESULT_OK) {
                Log.i(ARG_DEBUG, "onActivityResult: VIGNERON INTENT  -- RESULT OK !");
                Vigneron vigneron = new Vigneron(data.getBundleExtra("VIGNERON_CREATED"));
                // add to dictionnary bundle
                Log.i(ARG_DEBUG, "onActivityResult: ---------------------------------- " + vigneron.toString());
                ((CaveActivity) getActivity()).addNewVigneronToDictionary(vigneron);

                // handle new vigneron adapter
                handleNewVigneron(vigneron);
                return;
            } else {
                Intent intent = new Intent(getActivity(), CaveActivity.class);
                intent.putExtra(VIGN_INTENT_RETURN, true);
                startActivity(intent);
            }
        }
    }

    // called from activity
    public void handleNewVigneron(Vigneron vigneron) {
        // creating adapter row value
        HashMap<String, String> row = new HashMap<>();
        row.put(VIGN_LABEL, vigneron.getVigneronLibelle());
        row.put(VIGN_ID, String.valueOf(vigneron.getVigneronId()));
        // update vignerons adapter rows
        vignerons.add(row);
        // update vigneron backing list
        vigneronsBackingList.add(vigneron);
        // set new adapter
        sp_vigneron.setAdapter(new SimpleAdapter(getContext(),
                vignerons, R.layout.spinner_standard_layout,
                new String[]{VIGN_LABEL}, new int[]{R.id.sp_text}));

        sp_vigneron.setSelection(getVigneronItemPosition(vigneron.getVigneronLibelle()));

        // set new vigneron to item (Cave)
        item.getCaveVin().setVinVigneron(vigneron);
        // set image back
        if (pictureUri != null) {
            item.getCaveVin().setVinImage(pictureUri.toString());
        }

    }

    public Bitmap getResizedBitmap(Bitmap bm) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) IMAGE_RESIZE) / width;
        float scaleHeight = ((float) IMAGE_RESIZE) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }

    public boolean testCameraExists() {
        return getActivity().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }


    @NonNull
    private Cave updateCaveFromView() {
        Cave cave = new Cave();
        // retrieving old id if any
        if (item.getCaveId() != null) {
            cave.setCaveId(item.getCaveId());
        }
        if (!et_qte.getText().toString().isEmpty()) {
            cave.setCaveQuantite(Integer.valueOf(et_qte.getText().toString()));
        } else {
            cave.setCaveQuantite(0);
        }

        cave.setCaveFavoris(fabFavorisOn.getVisibility() == View.VISIBLE);

        // create new wine to add cave
        Vin vin = null;
        if (item.getCaveVin() != null) {
            vin = new Vin();
            vin.setVinId(item.getCaveVin().getVinId());

            if (null != pictureUri) {
                if (!pictureUri.getPath().isEmpty()) {
                    String path = pictureUri.toString();
                    item.getCaveVin().setVinImage(path);
                    vin.setVinImage(path);
                }
            }

            vin.setVinLibelle(et_libelle.getText().toString());

            if (!et_prix.getText().toString().isEmpty()) {
                vin.setVinPrix(Double.valueOf(et_prix.getText().toString()));
            } else {
                vin.setVinPrix(0.0);
            }
            vin.setVinCommentaire(et_commentaire.getText().toString());

            // set wine appelation
            if (sp_appelation.getSelectedItem() != null) {
                HashMap<String, String> map = (HashMap<String, String>) sp_appelation.getSelectedItem();
                Appelation appelation = new Appelation();
                appelation.setAppelationId(Long.valueOf(map.get(APLN_ID)));
                appelation.setAppelationLibelle(map.get(APLN_LABEL));
                vin.setVinAppelation(appelation);
            }
            // avoid none database values ( 'AUCUN' )
            if (sp_vigneron.getSelectedItem() != null) {
                HashMap<String, String> map = (HashMap<String, String>) sp_vigneron.getSelectedItem();
                if (map.get(VIGN_ID) != null) {
                    if (!map.get(VIGN_ID).equalsIgnoreCase("0")) {
                        for (Vigneron vigneron :
                                vigneronsBackingList) {
                            if (vigneron.getVigneronId().toString().equalsIgnoreCase(map.get(VIGN_ID))) {
                                vin.setVinVigneron(vigneron);
                            }
                        }
                    }
                }

            }
            // set wine type, can't be null
            if (sp_type.getSelectedItem() != null) {
                HashMap<String, String> map = (HashMap<String, String>) sp_type.getSelectedItem();
                TypeVin typeVin = new TypeVin();
                typeVin.setTypeVinId(Long.valueOf(map.get(TVIN_ID)));
                typeVin.setTypeVinLibelle(map.get(TVIN_LABEL));
                vin.setVinType(typeVin);
            }
            // set item dates from string, only the year is of interest
            try {
                if (!et_annee.getText().toString().isEmpty()) {
                    vin.setVinAnnee(vinDateFormat.parse(et_annee.getText().toString()));
                }
                if (!et_annee_max.getText().toString().isEmpty()) {
                    vin.setVinAnneeMax(vinDateFormat.parse(et_annee_max.getText().toString()));
                }
            } catch (ParseException pe) {
                Log.e(ARG_DEBUG, "updateVigneronFromView: ", pe);
            }
        }





        // set wine to cave
        if (null != vin) {
            cave.setCaveVin(vin);
        }


        return cave;
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
        if (!isEditTextValid(et_annee, YEAR_REGEX)) {
            return true;
        }
        if (!isEditTextValid(et_annee_max, YEAR_REGEX)) {
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

        if (et_commentaire.getText().toString().length() >= 500) {
            Toast.makeText(getContext(), "L'adresse ne peut avoir plus de 500 caractères !", Toast.LENGTH_LONG).show();
            et_commentaire.requestFocus();
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


    public void updateFragment(Cave _item) {
        this.item = _item;
        if (item != null) {
            if (item.getCaveVin() != null) {
                et_libelle.setText(item.getCaveVin().getVinLibelle() == null ? "" : item.getCaveVin().getVinLibelle());
                et_annee.setText(item.getCaveVin().getVinAnnee() == null ? "" : vinDateFormat.format(item.getCaveVin().getVinAnnee()));
                et_annee_max.setText(item.getCaveVin().getVinAnneeMax() == null ? "" : vinDateFormat.format(item.getCaveVin().getVinAnneeMax()));
                et_commentaire.setText(item.getCaveVin().getVinCommentaire() == null ? "" : item.getCaveVin().getVinCommentaire());
                et_prix.setText(item.getCaveVin().getVinPrix() == null ? "" : String.valueOf(item.getCaveVin().getVinPrix()));
                sp_vigneron.setSelection(item.getCaveVin().getVinVigneron() == null ? 0 : getVigneronItemPosition(item.getCaveVin().getVinVigneron().getVigneronLibelle()));
                sp_type.setSelection(item.getCaveVin().getVinAppelation() == null ? 0 : getTypeVinItemPosition(item.getCaveVin().getVinType().getTypeVinLibelle()));
                sp_appelation.setSelection(item.getCaveVin().getVinType() == null ? 0 : getAppelationItemPosition(item.getCaveVin().getVinAppelation().getAppelationLibelle()));
            }
            et_qte.setText(item.getCaveQuantite() == null ? "" : String.valueOf(item.getCaveQuantite()));

            // handle image
            boolean customImage = false;
            if (item.getCaveVin().getVinImage() != null) {
                if (!item.getCaveVin().getVinImage().isEmpty()) {

                    try {
                        pictureUri = Uri.parse(item.getCaveVin().getVinImage());
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), pictureUri);

                        if (null != bitmap) {
                            iv_image.setImageBitmap(getResizedBitmap(bitmap));
                        }

                        // stays in last position, exception would have already been thrown
                        customImage = true;
                    } catch (IOException e) {
                        Log.e(ARG_DEBUG, "updateFragment: ", e);
                        //e.printStackTrace();
                    }
                }
            }

            // handle if no image is defined
            if (!customImage) {
                iv_image.setImageDrawable(getResources().getDrawable(R.drawable.ic_image_default));
            }

            handleFavoriteButton();

        }

    }

    private int getTypeVinItemPosition(String typevinLabel) {
        for (HashMap<String, String> map :
                typevins) {
            for (String entryValue :
                    map.values()) {
                if (entryValue.equalsIgnoreCase(typevinLabel)) {
                    int idx = typevins.indexOf(map);
                    return idx;
                }
            }
        }
        Log.e(ARG_DEBUG, "getTypeVinItemPosition: NOT FOUND !!! RETURNING 0 to go to first !");
        return 0;
    }

    private int getAppelationItemPosition(String appelationLabel) {
        // getting appelation hashmap items
        for (HashMap<String, String> map : appelations) {
            // getting hashmap item values
            for (String entryValue : map.values()) {
                // if equals selected label, return index
                if (entryValue.equalsIgnoreCase(appelationLabel)) {
                    int idx = appelations.indexOf(map);
                    return idx;
                }
            }
        }
        // if not found return first - 'AUCUN'
        Log.e(ARG_DEBUG, "getAppelationItemPosition: NOT FOUND !!! RETURNING 0 to go to first !");
        return 0;
    }

    private int getVigneronItemPosition(String vigneronLabel) {
        for (HashMap<String, String> map :
                vignerons) {
            for (String entryValue :
                    map.values()) {
                if (entryValue.equalsIgnoreCase(vigneronLabel)) {
                    int idx = vignerons.indexOf(map);
                    return idx;
                }
            }
        }
        Log.e(ARG_DEBUG, "getVigneronItemPosition: NOT FOUND !!! RETURNING 0 to go to first !");
        return 0;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == ARG_CAMERA_INTENT) {
            if (grantResults.length > 0) {
                if (grantResults[0] == RESULT_OK) {
                    launchCameraIntent();
                } else {
                    Toast.makeText(getContext(), R.string.photo_perms_off, Toast.LENGTH_LONG).show();
                }
            }
            return;
        }

        if (requestCode == ARG_FILE_CHOOSE_INTENT) {
            if (grantResults.length > 0) {
                if (grantResults[0] == RESULT_OK) {
                    launchFileChooser();
                } else {
                    Toast.makeText(getContext(), R.string.files_perms_off, Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            activityCallback = (CrudFragmentInterface<Cave>) getActivity();
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

