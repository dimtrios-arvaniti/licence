package com.example.dim.licence.utils;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dim.licence.R;
import com.example.dim.licence.vignerons.VigneronListFragment;

import static com.example.dim.licence.MainActivity.ARG_DEBUG;

/**
 * PASS bundle from fragment instead of parent activity ! ....
 * **/
public class VigneronRecyclerAdapter
        extends RecyclerView.Adapter<VigneronRecyclerAdapter.ViewHolder> {

    private View.OnClickListener mOnClickListener;
    private VigneronListFragment parent;

    public VigneronRecyclerAdapter(final VigneronListFragment parent) {
        this.parent = parent;

        mOnClickListener = new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null == view.getTag()) {
                    Log.i(ARG_DEBUG, "onClick: VIEW TAG IS NULL !");
                    return;
                }

                String[] str = view.getTag().toString().split("_");
                if (str[0].isEmpty()) {
                    Log.i(ARG_DEBUG, "onClick: VIEW TAG ACTION IS EMPTY");
                    return;
                }

                if (str[1].isEmpty()) {
                    Log.i(ARG_DEBUG, "onClick: VIEW TAG ITEM IS EMPTY");
                    return;
                }

                int key = (Integer.valueOf(str[1]));
                if (str[0].equalsIgnoreCase("READ")) {
                    Log.i(ARG_DEBUG, "onClick: READ");
                    parent.updateSelectedItem(key);
                    return;
                }

                if (str[0].equalsIgnoreCase("LOCATE")) {
                    Log.i(ARG_DEBUG, "onClick: LOCATE");

                    //removeBottle(parentActivity.getGrapesModel().get(key));
                    return;
                }

                if (str[0].equalsIgnoreCase("CONTACT")) {
                    Log.i(ARG_DEBUG, "onClick: CONTACT");
                    //addBottle(parentActivity.getGrapesModel().get(key));
                    return;
                }
            }
        };
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.vigneron_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        int keyAt = parent.getList().keyAt(position);

        holder.vhIndex.setText(String.valueOf(position));
        holder.vhLabel.setText(parent.getList().get(keyAt).getVigneronLibelle());
        holder.vhDomaine.setText(String.valueOf(parent.getList().get(keyAt).getVigneronDomaine()));
        if (parent.getList().get(keyAt).getVigneronGeoloc().getGeolocVille() != null) {
            holder.vhVille.setText(String.valueOf(parent.getList().get(keyAt).getVigneronGeoloc().getGeolocVille()));
        }
        if (parent.getList().get(keyAt).getVigneronGeoloc().getGeolocCode() != null) {
            holder.vhCp.setText(String.valueOf(parent.getList().get(keyAt).getVigneronGeoloc().getGeolocCode()));

        }
        holder.vhLayout.setTag("READ_" + String.valueOf(position));
        holder.ibtnLocate.setTag("LOCATE_" + String.valueOf(position));
        holder.ibtnContact.setTag("CONTACT_" + String.valueOf(position));
    }

    @Override
    public int getItemCount() {
        return parent.getList().size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final TextView vhIndex;
        final TextView vhLabel;
        final TextView vhDomaine;
        final TextView vhVille;
        final TextView vhCp;

        final LinearLayout vhLayout;
        final Button ibtnLocate;
        final Button ibtnContact;

        ViewHolder(View view) {
            super(view);

            vhLayout = view.findViewById(R.id.v_li);
            vhLayout.setOnClickListener(mOnClickListener);

            vhIndex = view.findViewById(R.id.v_li_row);
            vhLabel = view.findViewById(R.id.v_li_libelle);
            vhDomaine = view.findViewById(R.id.v_li_domaine);
            vhVille = view.findViewById(R.id.v_li_geoloc_ville);
            vhCp = view.findViewById(R.id.v_li_geoloc_cp);

            ibtnLocate = view.findViewById(R.id.v_li_locate);
            ibtnLocate.setOnClickListener(mOnClickListener);

            ibtnContact = view.findViewById(R.id.v_li_contact);
            ibtnContact.setOnClickListener(mOnClickListener);
        }
    }
}
