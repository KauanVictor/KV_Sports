package com.example.varzea_organizada;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class newPassword extends AppCompatActivity {

   private EditText ETinstituicao, ETtelefone, EThorario, ETdata, ETendereco, ETemail, ETsenha, ETestado, ETmodalidade, ETcategoria, ETposicao;
   private TextView registrar;
   private TextInputLayout fullname;
   private ImageView img;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    FirebaseUser user;

    private String mEmail, password;
    private FirebaseDatabase database;
    private DatabaseReference useRef;
    private static final String USERS = "Atletas";
    String getmEmail;


    String nome, idade, altura, peso, telefone, email, estado, modalidade, posicao;

    DatabaseReference reference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_password);

        Intent intent = getIntent();
        mEmail = intent.getStringExtra("email");


        reference = FirebaseDatabase.getInstance().getReference("Atletas");

        ETinstituicao = findViewById(R.id.ETinstituicao);
        ETtelefone = findViewById(R.id.ETtelefone);
        EThorario = findViewById(R.id.EThorario);
        ETdata = findViewById(R.id.ETdata);
        ETendereco = findViewById(R.id.ETendereco);
        ETemail = findViewById(R.id.ETemail);
        ETestado = findViewById(R.id.ETestado);
        ETmodalidade = findViewById(R.id.ETmodalidade);
        ETcategoria = findViewById(R.id.ETcategoria);
        ETposicao = findViewById(R.id.ETposicao);



        img = findViewById(R.id.img);
        registrar = findViewById(R.id.registrar);
        fullname = findViewById(R.id.fullname);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        user = fAuth.getCurrentUser();

        database = FirebaseDatabase.getInstance();
        useRef = database.getReference(USERS);

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choosePicture();
            }
        });

        useRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()){
                    if (ds.child("email").getValue().equals(mEmail)){
                        ETinstituicao.setText(ds.child("nome").getValue(String.class));
                        ETtelefone.setText(ds.child("telefone").getValue(String.class));
                        EThorario.setText(ds.child("altura").getValue(String.class));
                        ETdata.setText(ds.child("peso").getValue(String.class));
                        ETendereco.setText(ds.child("idade").getValue(String.class));
                        ETemail.setText(email);
                        ETestado.setText(ds.child("estado").getValue(String.class));
                        ETmodalidade.setText(ds.child("modalidade").getValue(String.class));
                        ETcategoria.setText(ds.child("categoria").getValue(String.class));
                        ETposicao.setText(ds.child("posicao").getValue(String.class));



                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        showAllUserData();



    }

    private void choosePicture() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }

    private void showAllUserData()
    {
        Intent intent = getIntent();
        nome = intent.getStringExtra("nome");
        idade = intent.getStringExtra("idade");
        altura = intent.getStringExtra("altura");
        peso = intent.getStringExtra("peso");
        telefone = intent.getStringExtra("telefone");
        email = intent.getStringExtra("email");
        estado = intent.getStringExtra("estado");
        modalidade = intent.getStringExtra("modalidade");
        posicao = intent.getStringExtra("posicao");
    }

    public void update(View view){
        if (isNameChanged()){
            Toast.makeText(this, "Data has been updated", Toast.LENGTH_SHORT).show();
        }
        
        else Toast.makeText(this, "Data is sama and can not be updated", Toast.LENGTH_SHORT).show();

    }

    private boolean isNameChanged() {
        if (!nome.equals(fullname.getEditText().getText().toString()))
        {
            reference.child(email).child("nome").setValue(fullname.getEditText().getText().toString());
            return true;

        }else {return false;}
    }
}