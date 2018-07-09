package com.example.dim.licence.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.dim.licence.R;
import com.example.dim.licence.entities.Cave;
import com.example.dim.licence.utils.interfaces.CrudFragmentInterface;

import java.io.IOException;

import static android.view.View.GONE;
import static com.example.dim.licence.MainActivity.ARG_DEBUG;
import static com.example.dim.licence.utils.commons.Commons.IMAGE_RESIZE;
import static com.example.dim.licence.utils.commons.Commons.vinDateFormat;

public class CaveDetailFragment extends Fragment {


    private CrudFragmentInterface<Cave> activityCallback;
    private Cave item;

    private TextView tv_libelle;
    private TextView tv_appelation;
    private TextView tv_type;
    private TextView tv_vigneron;
    private TextView tv_annee;
    private TextView tv_annee_max;
    private TextView tv_commentaire;
    private TextView tv_prix;
    private TextView tv_qte;
    private Button editBtn;
    private Button deleteBtn;
    private AppCompatImageView iv_image;
    private FloatingActionButton btnFavoriteOn;
    private FloatingActionButton btnFavoriteOff;


    public static CaveDetailFragment newInstance() {
        CaveDetailFragment fragment = new CaveDetailFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public static CaveDetailFragment newInstance(Bundle selected) {
        CaveDetailFragment fragment = new CaveDetailFragment();
        fragment.setArguments(selected);
        return fragment;
    }

    public CaveDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_cave_detail, container, false);

        tv_libelle = rootView.findViewById(R.id.dtl_cave_label_value);
        tv_appelation = rootView.findViewById(R.id.dtl_cave_appelation_value);
        tv_type = rootView.findViewById(R.id.dtl_cave_type_value);
        tv_annee = rootView.findViewById(R.id.dtl_cave_annee_value);
        tv_annee_max = rootView.findViewById(R.id.dtl_cave_annee_max_value);
        tv_prix = rootView.findViewById(R.id.dtl_cave_prix_value);
        tv_qte = rootView.findViewById(R.id.dtl_cave_qte_value);
        tv_vigneron = rootView.findViewById(R.id.dtl_cave_vigneron_value);
        tv_commentaire = rootView.findViewById(R.id.dtl_cave_commentaire_value);
        iv_image = rootView.findViewById(R.id.dtl_cave_image);
        btnFavoriteOn = rootView.findViewById(R.id.dtl_cave_favoris_on);
        btnFavoriteOff = rootView.findViewById(R.id.dtl_cave_favoris_off);

        editBtn = rootView.findViewById(R.id.cave_btnEdit);
        deleteBtn = rootView.findViewById(R.id.cave_btnDel);

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityCallback.updateEditFragment(item);
            }
        });
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityCallback.onDeleteClick();
            }
        });
        return rootView;
    }

    public void updateFragment(Cave _item) {
        this.item = _item;

        if (item != null) {
            tv_libelle.setText(item.getCaveVin().getVinLibelle() == null ? "" : item.getCaveVin().getVinLibelle());
            tv_appelation.setText(item.getCaveVin().getVinAppelation() == null ? "" : item.getCaveVin().getVinAppelation().getAppelationLibelle());
            tv_commentaire.setText(item.getCaveVin().getVinCommentaire() == null ? "" : item.getCaveVin().getVinCommentaire());
            tv_annee.setText(item.getCaveVin().getVinAnnee() == null ? "" : vinDateFormat.format(item.getCaveVin().getVinAnnee()));
            tv_annee_max.setText(item.getCaveVin().getVinAnneeMax() == null ? "" : vinDateFormat.format(item.getCaveVin().getVinAnneeMax()));
            tv_vigneron.setText(item.getCaveVin().getVinVigneron() == null ? "" : item.getCaveVin().getVinVigneron().getVigneronLibelle());
            tv_qte.setText(String.valueOf(item.getCaveQuantite()));
            tv_prix.setText(String.valueOf(item.getCaveVin().getVinPrix()));
            tv_type.setText(item.getCaveVin().getVinType() == null ? "" : item.getCaveVin().getVinType().getTypeVinLibelle());

            boolean customImage = false;
            if (item.getCaveVin().getVinImage() != null) {
                if (!item.getCaveVin().getVinImage().isEmpty()) {

                    try {
                        // stays in last position, exception would have already been thrown
                        customImage = true;

                        Uri pictureUri = Uri.parse(item.getCaveVin().getVinImage());
                        Log.i(ARG_DEBUG, "updateFragment: " + pictureUri.getPath());

                        //Bitmap bitmap = BitmapFactory.decodeFile(picturePath);
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), pictureUri);

                        // setting to imageView
                        if (null != bitmap) {
                            iv_image.setImageBitmap(getResizedBitmap(bitmap));
                        }


                    } catch (IOException e) {
                        Log.e(ARG_DEBUG, "updateFragment: ", e);
                    }
                }
            }

            // handle if no image is defined
            if (!customImage) {
                iv_image.setImageDrawable(getResources().getDrawable(R.drawable.ic_image_default));
            }

            if (item.isCaveFavoris()) {
                btnFavoriteOn.setVisibility(View.VISIBLE);
                btnFavoriteOff.setVisibility(GONE);
            } else {
                btnFavoriteOff.setVisibility(View.VISIBLE);
                btnFavoriteOn.setVisibility(GONE);
            }
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

    /*
        @Override
        public void onSaveInstanceState(@NonNull Bundle outState) {
            super.onSaveInstanceState(outState);
            outState.putBundle("CAVE_DETAIL_ITEM", item.entityToBundle());
        }

        @Override
        public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
            super.onViewStateRestored(savedInstanceState);
            if (savedInstanceState != null) {
                item = new Cave(savedInstanceState.getBundle("CAVE_DETAIL_ITEM"));
            }
        }
    */
    public Cave getItem() {
        return item;
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
