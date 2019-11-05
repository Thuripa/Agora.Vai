package com.example.agoravai.Helper;

import com.example.agoravai.Model.Abastecimento;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

public class Helper {
    Realm realm;
    RealmResults<Abastecimento> abastecimentos;

    public Helper(Realm realm) {
        this.realm = realm;
    }

    public void selectFromDB() {
        abastecimentos = realm.where(Abastecimento.class).findAll();
    }

    public ArrayList<Abastecimento> justRefresh() {

        ArrayList<Abastecimento> listitem = new ArrayList<>();
        for (Abastecimento a : abastecimentos) {
            listitem.add(a);
        }

        return listitem;
    }
}
