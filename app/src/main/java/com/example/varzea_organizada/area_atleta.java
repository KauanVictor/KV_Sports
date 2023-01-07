package com.example.varzea_organizada;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.security.Key;
import java.util.ArrayList;

public class area_atleta extends AppCompatActivity {


    TextView atualizar, sair, nomeAtleta, idadeAtleta, alturaAtleta, pesoAtleta, telefoneAtleta, emailAtleta, estadoAtleta, modalidadeAtleta, posicaoAtleta;

    Button voltar;

    ImageView img;

    SQLiteOpenHelper openHelper;

    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area_atleta);
        voltar = findViewById(R.id.voltar);
        sair = findViewById(R.id.sair);
        nomeAtleta = findViewById(R.id.nomeAtleta);
        idadeAtleta = findViewById(R.id.idadeAtleta);
        alturaAtleta = findViewById(R.id.alturaAtleta);
        pesoAtleta = findViewById(R.id.pesoAtleta);
        telefoneAtleta = findViewById(R.id.telefoneAtleta);
        emailAtleta = findViewById(R.id.emailAtleta);
        estadoAtleta = findViewById(R.id.estadoAtleta);
        modalidadeAtleta = findViewById(R.id.modalidadeAtleta);
        posicaoAtleta = findViewById(R.id.posicaoAtleta);
        atualizar = findViewById(R.id.atualizar);
        img = findViewById(R.id.img);

        openHelper = new DatabaseHelper(this);

        reference = FirebaseDatabase.getInstance().getReference("Atletas");

        atualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), newPassword.class);
                i.putExtra("nome", nomeAtleta.getText().toString());
                i.putExtra("idade", idadeAtleta.getText().toString());
                i.putExtra("altura", alturaAtleta.getText().toString());
                i.putExtra("peso", pesoAtleta.getText().toString());
                i.putExtra("phone", telefoneAtleta.getText().toString());
                i.putExtra("email", emailAtleta.getText().toString());
                i.putExtra("estado", estadoAtleta.getText().toString());
                i.putExtra("modalidade", modalidadeAtleta.getText().toString());
                i.putExtra("posicao", posicaoAtleta.getText().toString());


                startActivity(i);
            }
        });


        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), newPassword.class);
                i.putExtra("nome", nomeAtleta.getText().toString());
                i.putExtra("email", "smallacademy1@gmail.com");
                i.putExtra("phone", "+55 (11) 98419 1811");
                startActivity(i);



            }
        });

    }



}

