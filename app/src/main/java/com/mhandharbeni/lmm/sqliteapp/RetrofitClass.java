package com.mhandharbeni.lmm.sqliteapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.mhandharbeni.lmm.sqliteapp.generator.ServiceGenerator;
import com.mhandharbeni.lmm.sqliteapp.model.DatabaseChuckNorris;
import com.mhandharbeni.lmm.sqliteapp.services.ChuckService;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by LMM on 11/16/2017.
 */

public class RetrofitClass extends AppCompatActivity {
    private ChuckService chuckService;
    private TextView txtChuck;
    private ImageView imgChuck;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fetch_module();
        setContentView(R.layout.retrofit_layout);
        fetch_component();
        do_call();
    }

    private void fetch_module(){
        chuckService = ServiceGenerator.createService(ChuckService.class);
    }
    private void fetch_component(){
        txtChuck = findViewById(R.id.txtChuck);
        imgChuck = findViewById(R.id.imgChuck);
    }
    private void do_call(){
        Call<DatabaseChuckNorris> call = chuckService.getQuote();
        call.enqueue(new Callback<DatabaseChuckNorris>() {
            @Override
            public void onResponse(Call<DatabaseChuckNorris> call, Response<DatabaseChuckNorris> response) {
                txtChuck.setText(response.body().getValue());
                Picasso.with(getApplicationContext()).load(response.body().getIconUrl()).into(imgChuck);
            }

            @Override
            public void onFailure(Call<DatabaseChuckNorris> call, Throwable t) {

            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_retrofit, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_reload) {
            do_call();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
