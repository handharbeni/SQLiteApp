package com.mhandharbeni.lmm.sqliteapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.mhandharbeni.lmm.sqliteapp.adapter.BookAdapter;
import com.mhandharbeni.lmm.sqliteapp.generator.ServiceGenerator;
import com.mhandharbeni.lmm.sqliteapp.model.DatabaseBook;
import com.mhandharbeni.lmm.sqliteapp.services.ChuckService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by LMM on 11/16/2017.
 */

public class BookActivity extends AppCompatActivity {
    private ChuckService chuckService;

    private BookAdapter bookAdapter;
    private ArrayList<DatabaseBook> listBook;

    private RecyclerView listbook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fetch_module();

        setContentView(R.layout.book_layout);

        fetch_component();
        fetch_adapter();

    }


    private void fetch_module(){

        listBook = new ArrayList<>();

        ServiceGenerator.changeApiBAseUrl("https://anapioficeandfire.com/");
        chuckService = ServiceGenerator.createService(ChuckService.class);

        Call<ArrayList<DatabaseBook>> call = chuckService.getAllBook();
        call.enqueue(new Callback<ArrayList<DatabaseBook>>() {
            @Override
            public void onResponse(Call<ArrayList<DatabaseBook>> call, Response<ArrayList<DatabaseBook>> response) {

                listBook.addAll(response.body());
                bookAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ArrayList<DatabaseBook>> call, Throwable t) {

            }
        });
    }

    private void fetch_component(){
        listbook = findViewById(R.id.listbook);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        listbook.setLayoutManager(llm);
    }
    private void fetch_adapter(){
        bookAdapter = new BookAdapter(this, listBook);
        listbook.setAdapter(bookAdapter);
    }

}
