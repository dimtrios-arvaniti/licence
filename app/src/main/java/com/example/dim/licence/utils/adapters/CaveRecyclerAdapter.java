package com.example.dim.licence.utils.adapters;

import android.support.annotation.Nullable;
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
import com.example.dim.licence.entities.Cave;
import com.example.dim.licence.fragments.CaveListFragment;

import static com.example.dim.licence.MainActivity.ARG_DEBUG;

public class CaveRecyclerAdapter extends RecyclerView.Adapter<CaveRecyclerAdapter.ViewHolder> {

    private CaveListFragment parent;

    private View.OnClickListener mOnClickListener = new OnClickListener() {
        @Override
        public void onClick(View view) {
            String[] str = isInvalidClick(view);

            if (null == str) {
                return;
            }

            int key = (Integer.valueOf(str[1]));
            if (str[0].equalsIgnoreCase("READ")) {
                parent.updateSelectedItem(key);
                return;
            }

            if (str[0].equalsIgnoreCase("PLUS")) {
                Log.i(ARG_DEBUG, "onClick: PLUS CLICKED");
                parent.onQuantityChange(key, true);
                return;
            }

            if (str[0].equalsIgnoreCase("MOINS")) {
                 parent.onQuantityChange(key, false);
                return;
            }
        }
    };

    @Nullable
    private String[] isInvalidClick(View view) {
        if (null == view.getTag()) {
            Log.e(ARG_DEBUG, "onClick: VIEW TAG IS NULL !");
            return null;
        }

        String[] str = view.getTag().toString().split("_");
        if (str[0].isEmpty()) {
            Log.e(ARG_DEBUG, "onClick: VIEW TAG ACTION IS EMPTY");
            return null;
        }

        if (str[1].isEmpty()) {
            Log.e(ARG_DEBUG, "onClick: VIEW TAG ITEM IS EMPTY");
            return null;
        }
        return str;
    }

    public CaveRecyclerAdapter(CaveListFragment _parent) {
        this.parent = _parent;
    }

    @Override
    public CaveRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cave_list_item, parent, false);
        return new CaveRecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CaveRecyclerAdapter.ViewHolder holder, int position) {
        int keyAt = parent.getList().keyAt(position);
        Cave cave = parent.getList().get(keyAt);

        holder.vhIndex.setText(String.valueOf(position + 1));
        holder.vhLabel.setText(cave.getCaveVin().getVinLibelle());
        if (cave.getCaveVin().getVinAppelation() != null) {
            holder.vhDomaine.setText(String.valueOf(cave.getCaveVin().getVinAppelation().getAppelationLibelle()));
        }
        if (cave.getCaveVin().getVinType() != null) {
            holder.vhType.setText(String.valueOf(cave.getCaveVin().getVinType().getTypeVinLibelle()));
        }
        if (parent.getList().get(keyAt).getCaveQuantite() != null) {
            holder.vhQuantity.setText(String.valueOf(cave.getCaveQuantite()));

        }
        holder.vhLayout.setTag("READ_" + cave.getCaveId());
        holder.ibtnPlus.setTag("PLUS_" + cave.getCaveId());
        holder.ibtnMoins.setTag("MOINS_" + cave.getCaveId());
    }

    @Override
    public int getItemCount() {
        return parent.getList().size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final TextView vhIndex;
        final TextView vhLabel;
        final TextView vhDomaine;
        final TextView vhType;
        final TextView vhQuantity;

        final LinearLayout vhLayout;
        final Button ibtnPlus;
        final Button ibtnMoins;

        ViewHolder(View view) {
            super(view);

            vhLayout = view.findViewById(R.id.vin_li);
            vhLayout.setOnClickListener(mOnClickListener);

            vhIndex = view.findViewById(R.id.vin_li_row);
            vhLabel = view.findViewById(R.id.vin_li_libelle);
            vhDomaine = view.findViewById(R.id.vin_li_domaine);
            vhType = view.findViewById(R.id.vin_li_type);
            vhQuantity = view.findViewById(R.id.vin_li_quantity);

            ibtnPlus = view.findViewById(R.id.vin_li_plus);
            ibtnPlus.setOnClickListener(mOnClickListener);

            ibtnMoins = view.findViewById(R.id.vin_li_moins);
            ibtnMoins.setOnClickListener(mOnClickListener);
        }
    }
}
