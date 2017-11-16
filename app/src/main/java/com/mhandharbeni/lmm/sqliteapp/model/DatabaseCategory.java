package com.mhandharbeni.lmm.sqliteapp.model;

/**
 * Created by LMM on 11/16/2017.
 */

public class DatabaseCategory {
    String value;

    public DatabaseCategory(String value) {
        this.value = value;
    }

    public DatabaseCategory() {
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
