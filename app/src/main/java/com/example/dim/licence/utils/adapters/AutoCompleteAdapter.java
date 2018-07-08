package com.example.dim.licence.utils.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.dim.licence.R;
import com.example.dim.licence.entities.Ville;
import com.example.dim.licence.models.MasterModel;

import java.util.ArrayList;

// thks to hackerkernel.com for good example !
public class AutoCompleteAdapter extends ArrayAdapter<Ville> implements Filterable {
    private ArrayList<Ville> villes;
    private MasterModel model;

    public AutoCompleteAdapter(Context context, int resource) {
        super(context, resource);
        villes = new ArrayList<>();
        model = MasterModel.getInstance(context);
    }

    @Override
    public int getCount() {
        return villes.size();
    }

    @Override
    public Ville getItem(int position) {
        return villes.get(position);
    }

    @Override
    public Filter getFilter() {
        Filter myFilter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                FilterResults filterResults = new FilterResults();
                if (constraint != null) {
                    if (constraint.length() > 3) {

                        try {
                            //get data from the web
                            String term = constraint.toString();
                            if (term.matches("[0-9]{1,}")) {
                                villes = new ArrayList<>(model.filterVilleByZipCode(term));
                            } else {
                                villes = new ArrayList<>(model.filterVilleByLibelle(term));
                            }
                        } catch (Exception e) {
                            Log.d("HUS", "EXCEPTION " + e);
                        }
                        filterResults.values = villes;
                        filterResults.count = villes.size();
                    }
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null && results.count > 0) {
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }
        };

        return myFilter;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.autocomplete_list_item, parent, false);

        //get Country
        Ville ville = villes.get(position);

        TextView villeLibelle = view.findViewById(R.id.ac_li_ville);
        //TextView villeZipCode = view.findViewById(R.id.ac_li_code_postale);
        villeLibelle.setText(ville.getVilleLibelle());
        //villeZipCode.setText(ville.getVilleZipCode());

        return view;
    }

}
