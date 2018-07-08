package com.example.dim.licence.utils.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dim.licence.R;
import com.example.dim.licence.entities.Ville;
import com.example.dim.licence.fragments.VigneronEditFragment;

public class AutoCompleteRecyclerAdapter extends RecyclerView.Adapter<AutoCompleteRecyclerAdapter.ViewHolder> {

    private VigneronEditFragment parent;

    public AutoCompleteRecyclerAdapter(VigneronEditFragment _parent) {
        this.parent = _parent;
    }

    @Override
    public AutoCompleteRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.autocomplete_list_item, parent, false);
        return new AutoCompleteRecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final AutoCompleteRecyclerAdapter.ViewHolder holder, int position) {

        Ville ville = parent.getVilles().get(position);

        if (ville != null) {
            holder.vhLibelle.setText(ville.getVilleLibelle() == null ? "" : ville.getVilleLibelle());
            //holder.vhZipCode.setText(ville.getVilleZipCode() == null ? "" : ville.getVilleZipCode());
        }
    }

    @Override
    public int getItemCount() {
        return parent.getVilles().size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final TextView vhLibelle;
        //final TextView vhZipCode;

        ViewHolder(View view) {
            super(view);
            vhLibelle = view.findViewById(R.id.ac_li_ville);
          //  vhZipCode = view.findViewById(R.id.ac_li_code_postale);

        }
    }


}
