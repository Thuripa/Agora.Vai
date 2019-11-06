package com.example.agoravai;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.agoravai.Adapter.Adapter;
import com.example.agoravai.Helper.Helper;
import com.example.agoravai.Model.Abastecimento;

import io.realm.Realm;
import io.realm.RealmChangeListener;

public class Listagem extends AppCompatActivity {

    Realm realm;

    RecyclerView recyclerView;
    Helper helper;
    RealmChangeListener realmChangeListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listagem);

        realm = Realm.getDefaultInstance();
        recyclerView = findViewById(R.id.recyclerView);

        helper = new Helper(realm);
        helper.selectFromDB();

        Adapter adapter = new Adapter(this, helper.justRefresh());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        refresh();
    }



    private void refresh() {
        realmChangeListener = new RealmChangeListener() {
            @Override
            public void onChange(Object o) {
                Adapter adapter = new Adapter(Listagem.this, helper.justRefresh());
                recyclerView.setAdapter(adapter);
            }
        };
        realm.addChangeListener(realmChangeListener);
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.removeChangeListener(realmChangeListener);
        realm.close();
    }

}
