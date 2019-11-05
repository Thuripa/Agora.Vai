package com.example.agoravai.Adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.agoravai.R;

import io.realm.com_example_agoravai_Model_AbastecimentoRealmProxy;

public class ViewHolder extends RecyclerView.ViewHolder {

    TextView tvPosto;
    TextView tvLitros;
    TextView tvValor;
    TextView tvKm;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        tvPosto = itemView.findViewById(R.id.tvPosto);
        tvLitros = itemView.findViewById(R.id.tvLitros);
        tvValor = itemView.findViewById(R.id.tvValor);
        tvKm = itemView.findViewById(R.id.tvKm);
    }
}
