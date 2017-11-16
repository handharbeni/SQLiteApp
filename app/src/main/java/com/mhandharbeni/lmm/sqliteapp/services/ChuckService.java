package com.mhandharbeni.lmm.sqliteapp.services;

import com.mhandharbeni.lmm.sqliteapp.model.DatabaseChuckNorris;
import com.mhandharbeni.lmm.sqliteapp.model.DatabaseUser;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by LMM on 11/16/2017.
 */

public interface ChuckService {
    @GET("jokes/random")
    Call<DatabaseChuckNorris> getQuote();


}
