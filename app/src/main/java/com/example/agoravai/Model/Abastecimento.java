package com.example.agoravai.Model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Abastecimento extends RealmObject {

    @PrimaryKey
    private int ID;
    private String posto;
    private float valor;
    private int km;
    private float litros;


    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getPosto() {
        return posto;
    }

    public void setPosto(String posto) {
        this.posto = posto;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public int getKm() {
        return km;
    }

    public void setKm(int km) {
        this.km = km;
    }

    public float getLitros() {
        return litros;
    }

    public void setLitros(float litros) {
        this.litros = litros;
    }
}
