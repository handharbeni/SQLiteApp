package com.mhandharbeni.lmm.sqliteapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mhandharbeni.lmm.sqliteapp.R;
import com.mhandharbeni.lmm.sqliteapp.model.DatabaseBook;

import java.util.ArrayList;

/**
 * Created by LMM on 11/16/2017.
 */

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {
    private Context context;
    private ArrayList<DatabaseBook> listBook;

    public BookAdapter(Context context, ArrayList<DatabaseBook> listBook) {
        this.context = context;
        this.listBook = listBook;
    }

    @Override
    public BookAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder vh = new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book, parent, false));
        return vh;
    }

    @Override
    public void onBindViewHolder(BookAdapter.ViewHolder holder, int position) {
        DatabaseBook book = listBook.get(position);
        holder.txtNama.setText(book.getName());
        holder.txtCountry.setText(book.getCountry());
        holder.txtPublisher.setText(book.getPublisher());
        holder.txtISBN.setText(book.getIsbn());
    }

    @Override
    public int getItemCount() {
        return listBook.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtNama, txtISBN, txtPublisher, txtCountry;
        public ViewHolder(View itemView) {
            super(itemView);
            txtNama = itemView.findViewById(R.id.txtNama);
            txtISBN = itemView.findViewById(R.id.txtISBN);
            txtPublisher = itemView.findViewById(R.id.txtPublisher);
            txtCountry = itemView.findViewById(R.id.txtCountry);
        }
    }
}
