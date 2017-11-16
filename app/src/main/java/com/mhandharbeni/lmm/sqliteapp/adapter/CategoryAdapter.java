package com.mhandharbeni.lmm.sqliteapp.adapter;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mhandharbeni.lmm.sqliteapp.R;
import com.mhandharbeni.lmm.sqliteapp.model.DatabaseCategory;

import java.util.ArrayList;

/**
 * Created by LMM on 11/16/2017.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    private ArrayList<DatabaseCategory> listCategory;
    private Context context;

    public CategoryAdapter(ArrayList<DatabaseCategory> listCategory, Context context) {
        this.listCategory = listCategory;
        this.context = context;
    }

    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder vh = new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false));
        return vh;
    }

    @Override
    public void onBindViewHolder(CategoryAdapter.ViewHolder holder, final int position) {
        holder.txtCategory.setText(listCategory.get(position).getValue());
        holder.mainitem.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
                contextMenu.setHeaderTitle("MENU");
                contextMenu.add(0, view.getId(), position, "DETAIL");
            }
        });
    }

    @Override
    public int getItemCount() {
        return listCategory.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtCategory;
        ConstraintLayout mainitem;
        public ViewHolder(View itemView) {
            super(itemView);
            mainitem = itemView.findViewById(R.id.mainitem);
            txtCategory = itemView.findViewById(R.id.txtCategory);
        }
    }
}
