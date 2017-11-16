package com.mhandharbeni.lmm.sqliteapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.mhandharbeni.lmm.sqliteapp.adapter.UserAdapter;
import com.mhandharbeni.lmm.sqliteapp.database.User;
import com.mhandharbeni.lmm.sqliteapp.model.DatabaseUser;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private DatabaseUser db;

    private RecyclerView mainlist;
    private ArrayList<User> listUser;
    private UserAdapter adapter;
    private LinearLayoutManager llm;

    private TextView txtCountData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fetch_modules();
        setContentView(R.layout.activity_main);
        fetch_component();
        fetch_data();
        fetch_adapter();
        fetch_count_data();
    }

    private void fetch_modules(){
        db = new DatabaseUser(this);
    }

    private void fetch_component(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addDialog();
            }
        });
        mainlist = findViewById(R.id.mainlist);
        llm = new LinearLayoutManager(this);
        mainlist.setLayoutManager(llm);
        txtCountData = findViewById(R.id.txtCountData);
    }

    private void fetch_count_data(){
        int total_data = db.getCountAll();
        txtCountData.setText("TOTAL DATA :"+String.valueOf(total_data));
    }

    private void fetch_data(){
        listUser = new ArrayList<>();
        Cursor cursor = db.get();
        if (cursor != null){
            if (cursor.moveToNext()){
                cursor.moveToFirst();
                do {
                    listUser.add(new User(cursor.getInt(0), cursor.getString(1), cursor.getString(2)));
                }while (cursor.moveToNext());
            }
        }
    }
    private void update_data(){
        listUser = new ArrayList<>();
        Cursor cursor = db.get();
        if (cursor != null){
            if (cursor.moveToNext()){
                cursor.moveToFirst();
                do {
                    listUser.add(new User(cursor.getInt(0), cursor.getString(1), cursor.getString(2)));
                }while (cursor.moveToNext());
            }
        }
        adapter.updateData(listUser);
        fetch_count_data();
    }
    private void fetch_adapter(){
        adapter = new UserAdapter(this, listUser);
        mainlist.setAdapter(adapter);
    }

    private Boolean validateInput(EditText editText){
        return !editText.getText().toString().isEmpty();
    }
    private void doAddUser(String id, String nama, String alamat){
        User user = new User();
        user.setId(Integer.valueOf(id));
        user.setNama(nama);
        user.setAlamat(alamat);
        db.add(user);
    }
    private void doUpdateUser(String id, String nama, String alamat){
        User user = new User();
        user.setId(Integer.valueOf(id));
        user.setNama(nama);
        user.setAlamat(alamat);
        db.update(user);
    }
    private void doDeleteUser(String id, String nama, String alamat){
        User user = new User();
        user.setId(Integer.valueOf(id));
        user.setNama(nama);
        user.setAlamat(alamat);
        db.delete(user);
    }
    private void addDialog(){
        final EditText id, nama, alamat;

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.add_dialog, null);

        id = view.findViewById(R.id.id);
        nama = view.findViewById(R.id.nama);
        alamat = view.findViewById(R.id.alamat);

        builder.setView(view);
        builder.setTitle("ADD DATA USER");
        builder.setNegativeButton(R.string.batal, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        builder.setPositiveButton(R.string.simpan, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (validateInput(id) && validateInput(nama) && validateInput(alamat)){
                    doAddUser(id.getText().toString(), nama.getText().toString(), alamat.getText().toString());
                    update_data();
                }
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private void editDialog(int iId){
        final EditText id, nama, alamat;

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.add_dialog, null);

        id = view.findViewById(R.id.id);
        nama = view.findViewById(R.id.nama);
        alamat = view.findViewById(R.id.alamat);

        User user = db.get(iId);
        id.setText(String.valueOf(user.getId()));
        id.setEnabled(false);
        nama.setText(user.getNama());
        alamat.setText(user.getAlamat());

        builder.setView(view);
        builder.setTitle("UPDATE DATA USER");
        builder.setNegativeButton(R.string.batal, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        builder.setPositiveButton(R.string.simpan, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (validateInput(nama) && validateInput(alamat)){
                    doUpdateUser(id.getText().toString(), nama.getText().toString(), alamat.getText().toString());
                    update_data();
                }
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    private void deleteDialog(final int id){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("YAKIN AKAN DIHAPUS DATA DENGAN ID "+String.valueOf(id));
        builder.setNegativeButton(R.string.batal, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.setPositiveButton(R.string.hapus, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                doDeleteUser(String.valueOf(id), "", "");
                update_data();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_retrofit) {
            Intent i = new Intent(MainActivity.this, RetrofitClass.class);
            startActivity(i);
            return true;
        }else if (id == R.id.action_listretrofit){
            Intent i = new Intent(MainActivity.this, ListRetrofitActivity.class);
            startActivity(i);
            return true;
        }else if (id == R.id.action_book){
            Intent i = new Intent(MainActivity.this, BookActivity.class);
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getTitle() == "EDIT"){
            editDialog(item.getOrder());
        }else if(item.getTitle() == "DELETE"){
            deleteDialog(item.getOrder());
        }
        return super.onContextItemSelected(item);
    }

    @Override
    protected void onPause() {
        db.closeSQL();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (db != null) {
            db = new DatabaseUser(this);
        }
    }

    @Override
    protected void onStop() {
        db.closeSQL();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        db.closeSQL();
        super.onDestroy();
    }
}
