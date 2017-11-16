package com.mhandharbeni.lmm.sqliteapp.services;

import com.mhandharbeni.lmm.sqliteapp.model.DatabaseBook;
import com.mhandharbeni.lmm.sqliteapp.model.DatabaseChuckNorris;
import com.mhandharbeni.lmm.sqliteapp.model.DatabaseUser;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by LMM on 11/16/2017.
 */

public interface ChuckService {
    @GET("jokes/random")
    Call<DatabaseChuckNorris> getQuote();

    @GET("jokes/categories")
    Call<List<String>> getCategories();

    @GET("jokes/random")
    Call<DatabaseChuckNorris> getDetailQuote(@Query("category") String kategori);

    @GET("api/books")
    Call<ArrayList<DatabaseBook>> getAllBook();
}
