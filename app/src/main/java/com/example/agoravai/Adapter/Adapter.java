package com.example.agoravai.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.agoravai.EditAbast;
import com.example.agoravai.Model.Abastecimento;
import com.example.agoravai.R;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<ViewHolder> {
    Context c;
    ArrayList<Abastecimento> abastecimentos;

    public Adapter(Context c, ArrayList<Abastecimento> abastecimentos) {
        this.c = c;
        this.abastecimentos = abastecimentos;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(c).inflate(R.layout.list_item, viewGroup, false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getView();
            }
        });
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Abastecimento a = abastecimentos.get(position);

        holder.tvPosto.setText(a.getPosto());/*
        holder.tvLitros.setText(String.valueOf(a.getLitros()));
        holder.tvValor.setText(String.valueOf(a.getValor()));
        holder.tvKm.setText(a.getKm());
*/
    }

    @Override
    public int getItemCount() {
        return abastecimentos.size();
    }


    public void getView() {
        Intent intent = new Intent(c, EditAbast.class);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

}
