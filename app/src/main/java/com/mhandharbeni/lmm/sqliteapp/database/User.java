package com.mhandharbeni.lmm.sqliteapp.database;

/**
 * Created by LMM on 11/15/2017.
 */

public class User {
    int id;
    String nama, alamat;

    public User() {
    }

    public User(int id, String nama, String alamat) {
        this.id = id;
        this.nama = nama;
        this.alamat = alamat;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }
}
