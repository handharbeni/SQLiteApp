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
import com.mhandharbeni.lmm.sqliteapp.database.User;

import java.util.ArrayList;

/**
 * Created by LMM on 11/15/2017.
 */

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder>{
    private Context context;
    private ArrayList<User> listUsers;
    public UserAdapter(Context context, ArrayList<User> listUsers) {
        this.context = context;
        this.listUsers = listUsers;
    }

    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder viewHolder = new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(UserAdapter.ViewHolder holder, int position) {
        final User user = listUsers.get(position);
        holder.txtID.setText(String.valueOf(user.getId()));
        holder.txtNama.setText(user.getNama());
        holder.txtAlamat.setText(user.getAlamat());
        holder.mainitem.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
                contextMenu.setHeaderTitle("MENU");
                contextMenu.add(0, view.getId(), user.getId(), "EDIT");
                contextMenu.add(0, view.getId(), user.getId(), "DELETE");
            }
        });
    }
    public void updateData(ArrayList<User> listUsers){
        this.listUsers.clear();
        this.listUsers = listUsers;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return listUsers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtID, txtNama, txtAlamat;
        ConstraintLayout mainitem;
        public ViewHolder(View itemView) {
            super(itemView);
            mainitem = itemView.findViewById(R.id.mainitem);
            txtID = itemView.findViewById(R.id.txtID);
            txtNama = itemView.findViewById(R.id.txtNama);
            txtAlamat = itemView.findViewById(R.id.txtAlamat);
        }
    }
}
