package com.mhandharbeni.lmm.sqliteapp;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mhandharbeni.lmm.sqliteapp.adapter.CategoryAdapter;
import com.mhandharbeni.lmm.sqliteapp.generator.ServiceGenerator;
import com.mhandharbeni.lmm.sqliteapp.model.DatabaseCategory;
import com.mhandharbeni.lmm.sqliteapp.model.DatabaseChuckNorris;
import com.mhandharbeni.lmm.sqliteapp.services.ChuckService;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by LMM on 11/16/2017.
 */

public class ListRetrofitActivity extends AppCompatActivity {

    private ChuckService chuckService;
    private ChuckService chuckServiceDetail;

    private CategoryAdapter categoryAdapter;
    private ArrayList<DatabaseCategory> listCategory;

    private RecyclerView listcategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fetch_module();

        setContentView(R.layout.listretrofit_activity);

        fetch_component();
        fetch_adapter();
        fetch_data();
    }

    private void fetch_module(){
        chuckService = ServiceGenerator.createService(ChuckService.class);
        listCategory = new ArrayList<>();
    }
    private void fetch_component(){
        listcategory = findViewById(R.id.listcategory);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        listcategory.setLayoutManager(llm);
    }
    private void fetch_data(){
        Call<List<String>> call = chuckService.getCategories();
        call.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if (response.body().size()>0){
                    for (int i=0;i<response.body().size();i++){
                        listCategory.add(new DatabaseCategory(response.body().get(i)));
                    }
                    categoryAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {

            }
        });
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getTitle() == "DETAIL"){
            showDialogs(item.getOrder());
        }
        return super.onContextItemSelected(item);
    }
    private void fetch_adapter(){
        categoryAdapter = new CategoryAdapter(listCategory, this);
        listcategory.setAdapter(categoryAdapter);
    }
    private void showDialogs(int position){

        TextView txtChuck;
        ImageView imgChuck;

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.retrofit_layout, null);

        txtChuck = view.findViewById(R.id.txtChuck);
        imgChuck = view.findViewById(R.id.imgChuck);

        doCallDetail(position, txtChuck, imgChuck);

        builder.setView(view);
        builder.setTitle("DETAIL JOKES CATEGORY");
        builder.setNegativeButton(R.string.tutup, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void doCallDetail(int id, final TextView txt, final ImageView img){
        chuckServiceDetail = ServiceGenerator.createService(ChuckService.class);
        Call<DatabaseChuckNorris> chuckNorrisCall = chuckService.getDetailQuote(listCategory.get(id).getValue());
        chuckNorrisCall.enqueue(new Callback<DatabaseChuckNorris>() {
            @Override
            public void onResponse(Call<DatabaseChuckNorris> call, Response<DatabaseChuckNorris> response) {
                txt.setText(response.body().getValue());
                Picasso.with(getApplicationContext()).load(response.body().getIconUrl()).into(img);
            }

            @Override
            public void onFailure(Call<DatabaseChuckNorris> call, Throwable t) {

            }
        });
    }
}
