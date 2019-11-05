package com.example.agoravai;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.agoravai.Adapter.Adapter;
import com.example.agoravai.Helper.Helper;
import com.example.agoravai.Model.Abastecimento;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmChangeListener;

public class MainActivity extends AppCompatActivity {

    Realm realm;
    EditText etPosto;
    EditText etLitros;
    EditText etValor;
    EditText etKm;
    Button btnAdicionar;
    TextView tvKML;

    RecyclerView recyclerView;
    Helper helper;
    RealmChangeListener realmChangeListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        realm = Realm.getDefaultInstance();

        etPosto = findViewById(R.id.etPosto);
        etLitros = findViewById(R.id.etLitros);
        etValor = findViewById(R.id.etValor);
        etKm = findViewById(R.id.etKm);
        btnAdicionar = findViewById(R.id.btnAdicionar);
        tvKML = findViewById(R.id.textView);


        btnAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuListar:
                Toast.makeText(this, "Listando...", Toast.LENGTH_SHORT).show();
                listar();
                return true;
            case R.id.menuAdicionar:
                Toast.makeText(this, "Adicionando...", Toast.LENGTH_SHORT).show();
                saveData();
             return true;

        }
        return super.onOptionsItemSelected(item);
    }

    public void refreshKML() {

        //tentando implementar o visor de km/l usando realm
        if(tvKML != null) {
            ArrayList<Abastecimento> abastecimentos = new ArrayList();
            helper = new Helper(realm);
            helper.selectFromDB();
            abastecimentos = helper.justRefresh();

            int kmTotal = 0;
            float ltTotal = 0f;
            for (Abastecimento abast:abastecimentos) {
                kmTotal = kmTotal + abast.getKm();
                ltTotal = ltTotal + abast.getLitros();
            }
            float kml = kmTotal/ltTotal;
            Log.d("ALCM", "Calculo KM/L: "+kml);
            tvKML.setText(tvKML.getText()+Float.valueOf(kml).toString());
        }
        else {
            tvKML.setText(tvKML.getText()+"0");
        }


    }

    private void listar() {
        Intent intent = new Intent(this, Listagem.class);
        startActivity(intent);
    }

    private void saveData(){

        Toast.makeText(this, "Adicionando...", Toast.LENGTH_SHORT).show();

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                Number maxId = bgRealm.where(Abastecimento.class).max("ID");
                int newKey = (maxId == null) ? 1 : maxId.intValue()+1;
                Log.d("ALCM", "id:"+newKey);
                Abastecimento abastecimento = bgRealm.createObject(Abastecimento.class,newKey);
                Log.d("ALCM", "abastecimento id"+abastecimento.getID());
                abastecimento.setPosto(etPosto.getText().toString());
                abastecimento.setKm(Integer.valueOf(etKm.getText().toString()));
                Log.d("ALCM", "Teste de Cast - Litros:"+Float.valueOf(etLitros.getText().toString()));
                abastecimento.setLitros(Float.valueOf(etLitros.getText().toString()));
                abastecimento.setValor(Float.valueOf(etValor.getText().toString()));
                bgRealm.insert(abastecimento);

            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                refreshKML();
                Toast.makeText(MainActivity.this, "SUCESSO! ", Toast.LENGTH_SHORT).show();
            }
        }, new Realm.Transaction.OnError(){
            @Override
            public void onError(Throwable error) {
                Log.d("ALCM", "ERRO:"+error);
                Toast.makeText(MainActivity.this, "FRACASSO! ", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
