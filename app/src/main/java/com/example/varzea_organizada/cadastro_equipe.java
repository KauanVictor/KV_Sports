package com.example.varzea_organizada;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.share.Share;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import java.io.InputStream;

public class cadastro_equipe extends AppCompatActivity {

    private static final int REQUEST_CODE_STORAGE_PERMISSION = 1;
    private static final int REQUEST_CODE_SELECT_IMAGE = 2;
    private ImageView viewImage;
    private SharedPreferences prefs;
    private Bitmap bitmap;
    String selectedImagePath;


    DBHelper DB;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_equipe);


        Button voltar = findViewById(R.id.voltar);
        TextView continuar = findViewById(R.id.finalizar);
        final EditText cnpj = findViewById(R.id.CNPJEquipe);
        TextView escudo = findViewById(R.id.escudo);
        TextView text = findViewById(R.id.text);
        final EditText cpf = findViewById(R.id.cpfResponsavel);
        final EditText telefone = findViewById(R.id.telefone);
        final EditText NomeEquipe = findViewById(R.id.nomeEquipe);
        final EditText senhaEquipe = findViewById(R.id.SenhaEquipe);
        final EditText NomeResponsavel = findViewById(R.id.NomeResponsavel);
        DB = new DBHelper(this);
        viewImage = findViewById(R.id.foto);



        final SharedPreferences fotoEscudo = getSharedPreferences("fotoEscudo", MODE_PRIVATE);
        SharedPreferences.Editor editor = fotoEscudo.edit();
        editor.putString("fotoEscudo", selectedImagePath);
        editor.commit();



        final SharedPreferences prefsNomeEquipe = PreferenceManager
                .getDefaultSharedPreferences(this);


        NomeEquipe.setText(prefsNomeEquipe.getString("autoSaveNE", ""));

        NomeEquipe.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count){

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after){

            }

            @Override
            public void afterTextChanged(Editable s){
                prefsNomeEquipe.edit().putString("autoSaveNE", s.toString()).commit();
            }

        });



        final SharedPreferences prefsCNPJ = PreferenceManager
                .getDefaultSharedPreferences(this);

        cnpj.setText(prefsCNPJ.getString("autoSavePJ", ""));

        cnpj.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count){

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after){

            }

            @Override
            public void afterTextChanged(Editable s){
                prefsCNPJ.edit().putString("autoSavePJ", s.toString()).commit();
            }

        });


        final SharedPreferences prefsSenhaEquipe = PreferenceManager
                .getDefaultSharedPreferences(this);
        senhaEquipe.setText(prefsSenhaEquipe.getString("autoSaveSE", ""));

        senhaEquipe.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count){

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after){

            }

            @Override
            public void afterTextChanged(Editable s){
                prefsSenhaEquipe.edit().putString("autoSaveSE", s.toString()).commit();
            }

        });

        final SharedPreferences prefsNomeResponsavel = PreferenceManager
                .getDefaultSharedPreferences(this);

        NomeResponsavel.setText(prefsNomeResponsavel.getString("autoSaveNR", ""));

        NomeResponsavel.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count){

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after){

            }

            @Override
            public void afterTextChanged(Editable s){
                prefsNomeResponsavel.edit().putString("autoSaveNR", s.toString()).commit();
            }

        });


        final SharedPreferences prefsCPF = PreferenceManager
                .getDefaultSharedPreferences(this);

        cpf.setText(prefsCPF.getString("autoSavePF", ""));

        cpf.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count){

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after){

            }

            @Override
            public void afterTextChanged(Editable s){
                prefsCPF.edit().putString("autoSavePF", s.toString()).commit();
            }

        });


        final SharedPreferences prefsTelefone = PreferenceManager
                .getDefaultSharedPreferences(this);

        telefone.setText(prefsTelefone.getString("autoSaveTEL", ""));

        telefone.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count){

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after){

            }

            @Override
            public void afterTextChanged(Editable s){
                prefsTelefone.edit().putString("autoSaveTEL", s.toString()).commit();
            }

        });



        escudo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(cadastro_equipe.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            REQUEST_CODE_STORAGE_PERMISSION);
                } else {
                    selecionarFoto();
                }
            }
        });


        continuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               agremiacao();

            }

            private void agremiacao() {
                String nome = NomeResponsavel.getText().toString();
                String CNPJ = cnpj.getText().toString();
                String CPF = cpf.getText().toString();
                String tele = telefone.getText().toString();

              /*  Boolean insert = DB.agremiacao(nome, CNPJ, CPF, tele);

                if (insert == true)
                {
                    Toast.makeText(getApplicationContext(), "True", Toast.LENGTH_LONG).show();
                }

                else {
                    Toast.makeText(cadastro_equipe.this, "False", Toast.LENGTH_SHORT).show();
                }*/
                }




        });

        Context context = getApplicationContext();
        SharedPreferences sharedPref = context.getSharedPreferences(
                getString(R.string.nomeEquipe), Context.MODE_PRIVATE);







        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), cadastro_comissao.class);
                startActivity(i);
            }
        });
    }

    private void selecionarFoto()
    {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_CODE_SELECT_IMAGE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CODE_STORAGE_PERMISSION && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                selecionarFoto();
            } else {

                androidx.appcompat.app.AlertDialog.Builder dialog = new AlertDialog.Builder(cadastro_equipe.this);
                dialog.setIcon(R.drawable.ic_baseline_error_outline_24);
                dialog.setTitle("Erro");
                dialog.setMessage("Permissão negada.");
                dialog.setNeutralButton("Ok", null);
                dialog.show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SELECT_IMAGE && resultCode == RESULT_OK) {
            if (data != null) {
                Uri selecionada = data.getData();
                if (selecionada != null) {
                    try {
                        InputStream inputStream = getContentResolver().openInputStream(selecionada);
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        viewImage.setImageBitmap(bitmap);

                    } catch (Exception exception) {
                        Toast.makeText(this, exception.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }


    }
}

