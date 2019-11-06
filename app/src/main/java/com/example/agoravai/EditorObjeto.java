package com.example.agoravai;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.agoravai.Model.Abastecimento;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import io.realm.Realm;

public class EditorObjeto extends AppCompatActivity {

    TextView tvEditorPosto;
    TextView tvEditorLitros;
    TextView  tvEditorValor;
    TextView tvEditorKM;
    Button btnEditar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor_objeto);

        tvEditorPosto = findViewById(R.id.tvEditPosto);
        tvEditorLitros = findViewById(R.id.tvEditLitros);
        tvEditorValor = findViewById(R.id.tvEditValor);
        tvEditorKM = findViewById(R.id.tvEditKm);
        btnEditar = findViewById(R.id.button);
        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editar();
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
        if (item.getItemId() == R.id.menuListar) {
              Toast.makeText(this, "Abrindo Câmera...", Toast.LENGTH_SHORT).show();
              abrirCamera();

        }
        return super.onOptionsItemSelected(item);
    }

    public void editar() {
        Toast.makeText(this, "Editando...", Toast.LENGTH_SHORT).show();

    }

    String caminhoDaFoto = null;

    private File criarArquivoParaSalvarFoto() throws IOException {
        String nomeFoto = UUID.randomUUID().toString();
        //getExternalStoragePublicDirectory()
        //    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
        File diretorio = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File fotografia = File.createTempFile(nomeFoto, ".jpg", diretorio);
        caminhoDaFoto = fotografia.getAbsolutePath();
        return fotografia;
    }


    public void abrirCamera() {
        Intent intecaoAbrirCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        File arquivoDaFoto = null;
        try {
            arquivoDaFoto = criarArquivoParaSalvarFoto();
        } catch (IOException ex) {
            Toast.makeText(this, "Não foi possível criar arquivo para foto", Toast.LENGTH_LONG).show();
        }
        if (arquivoDaFoto != null) {
            Uri fotoURI = FileProvider.getUriForFile(this,
                    "com.example.a02_listas.fileprovider",
                    arquivoDaFoto);
            intecaoAbrirCamera.putExtra(MediaStore.EXTRA_OUTPUT, fotoURI);
            startActivityForResult(intecaoAbrirCamera, 1);
        }

    }


// avisar o SO para atualizar o índice da galeria
//    private void galleryAddPic() {
//        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
//        File f = new File(currentPhotoPath);
//        Uri contentUri = Uri.fromFile(f);
//        mediaScanIntent.setData(contentUri);
//        this.sendBroadcast(mediaScanIntent);
//    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
//                Bundle extras = data.getExtras();
//                Bitmap bitmapaFoto = (Bitmap) extras.get("data");

                abastecimento.setCaminhoFotografia(caminhoDaFoto);
                atualizaFotografiaNaTela();

            }
        }
    }

    private void atualizaFotografiaNaTela() {
        if (abastecimento.getCaminhoFotografia() != null) {
            ImageView ivFotografia = findViewById(R.id.ivFotografia);
            ivFotografia.setImageURI(Uri.parse(abastecimento.getCaminhoFotografia()));
        }
    }

}
