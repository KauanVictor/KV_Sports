package com.example.varzea_organizada;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.opengl.Visibility;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class recuperarSenha extends AppCompatActivity {

    EditText txtEmail;
    TextView next, recuperar;
    FloatingActionButton fb, ig, google;
    private String email;
    LinearLayout linear;
     FirebaseAuth firebaseAuth;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar_senha);

        firebaseAuth = FirebaseAuth.getInstance();
        recuperar = findViewById(R.id.prosseguir);
        txtEmail = findViewById(R.id.forgetEmail);
        linear = findViewById(R.id.linear);
        next = findViewById(R.id.prosseguir);
        fb = findViewById(R.id.fab_fb);
        ig = findViewById(R.id.fab_insta);
        google = findViewById(R.id.fab_google);
        progressBar = findViewById(R.id.progressBar);


        recuperar.setOnClickListener(new View.OnClickListener() {
         @Override


             public void onClick(View view) {
             if (txtEmail.getText().toString().isEmpty())
             {txtEmail.setError("Este campo deve ser preenchido."); }
             else {
                 String mail = txtEmail.getText().toString();
                 firebaseAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                     @Override
                     public void onSuccess(Void aVoid) {
                         Snackbar snack = Snackbar.make(findViewById(android.R.id.content), "Redefinição de senha encaminhada para o seu email.", Snackbar.LENGTH_LONG)
                                 .setAction("OK", new View.OnClickListener() {
                                     @Override
                                     public void onClick(View view) {

                                     }
                                 }).setActionTextColor(getResources().getColor(R.color.colorPrimary));
                         View view = snack.getView();
                         FrameLayout.LayoutParams params =(FrameLayout.LayoutParams)view.getLayoutParams();
                         params.gravity = Gravity.TOP;
                         view.setLayoutParams(params);
                         snack.show();
                         txtEmail.setText("");


                     }
                 }).addOnFailureListener(new OnFailureListener() {
                     @Override
                     public void onFailure(@NonNull Exception e) {
                         String resposta = e.getMessage();
                         opcoesErro(resposta);

                     }
                 });
             }
         }
     });




        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://www.linkedin.com/in/kauan-victor-45754b192/";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });


        google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "http://google.com";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });


        ig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "http://instagram.com/kvsorocaba";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });


        findViewById(R.id.volta).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

    }

    private void validateData() {
        email = txtEmail.getText().toString();
        if (email.isEmpty()) {
            txtEmail.setError("Este campo deve ser preenchido.");
        } else {
            forgetPass();
        }
    }

    private void forgetPass() {
        firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(recuperarSenha.this, "Check your Email", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                } else {
                    String resposta = task.getException().toString();
                    opcoesErro(resposta);
                }
            }
        });
    }
    private void opcoesErro(String resposta) {
        if (resposta.contains( "least 6 characters")){
            Snackbar snack = Snackbar.make(findViewById(android.R.id.content), "A sua senha deve ser maior que 5 caracteres.", Snackbar.LENGTH_LONG)
                    .setAction("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    }).setActionTextColor(getResources().getColor(R.color.colorPrimary));
            View view = snack.getView();
            FrameLayout.LayoutParams params =(FrameLayout.LayoutParams)view.getLayoutParams();
            params.gravity = Gravity.TOP;
            view.setLayoutParams(params);
            snack.show();
        }
        else if (resposta.contains("address is badly")){
            Snackbar snack = Snackbar.make(findViewById(android.R.id.content), "Formato de email inválido.", Snackbar.LENGTH_LONG)
                    .setAction("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    }).setActionTextColor(getResources().getColor(R.color.colorPrimary));
            View view = snack.getView();
            FrameLayout.LayoutParams params =(FrameLayout.LayoutParams)view.getLayoutParams();
            params.gravity = Gravity.TOP;
            view.setLayoutParams(params);
            snack.show();
        }
        else if (resposta.contains("address is already")){
            Snackbar snack = Snackbar.make(findViewById(android.R.id.content), "Email já cadastrado.", Snackbar.LENGTH_LONG)
                    .setAction("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    }).setActionTextColor(getResources().getColor(R.color.colorPrimary));
            View view = snack.getView();
            FrameLayout.LayoutParams params =(FrameLayout.LayoutParams)view.getLayoutParams();
            params.gravity = Gravity.TOP;
            view.setLayoutParams(params);
            snack.show();
        }
        else if (resposta.contains("interrupted connection")){
            Snackbar snack = Snackbar.make(findViewById(android.R.id.content), "Sem conexão com a internet.", Snackbar.LENGTH_LONG)
                    .setAction("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    }).setActionTextColor(getResources().getColor(R.color.colorPrimary));
            View view = snack.getView();
            FrameLayout.LayoutParams params =(FrameLayout.LayoutParams)view.getLayoutParams();
            params.gravity = Gravity.TOP;
            view.setLayoutParams(params);
            snack.show();
        }

        else if (resposta.contains("password is invalid")){
            Snackbar snack = Snackbar.make(findViewById(android.R.id.content), "Senha incorreta.", Snackbar.LENGTH_LONG)
                    .setAction("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    }).setActionTextColor(getResources().getColor(R.color.colorPrimary));
            View view = snack.getView();
            FrameLayout.LayoutParams params =(FrameLayout.LayoutParams)view.getLayoutParams();
            params.gravity = Gravity.TOP;
            view.setLayoutParams(params);
            snack.show();
        }

        else if (resposta.contains("We have blocked all requests from this device due to unusual activity. Try again later.")){
            Snackbar snack = Snackbar.make(findViewById(android.R.id.content), "Bloqueamos todas as solicitações deste dispositivo devido a atividade incomum.", Snackbar.LENGTH_LONG)
                    .setAction("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    }).setActionTextColor(getResources().getColor(R.color.colorPrimary));
            View view = snack.getView();
            FrameLayout.LayoutParams params =(FrameLayout.LayoutParams)view.getLayoutParams();
            params.gravity = Gravity.TOP;
            view.setLayoutParams(params);
            snack.show();
        }


        else if (resposta.contains("There is no user record corresponding to this identifier. The user may have been deleted.")){
            Snackbar snack = Snackbar.make(findViewById(android.R.id.content), "Não há registro de usuário cadastrado correspondente a este email.", Snackbar.LENGTH_LONG)
                    .setAction("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    }).setActionTextColor(getResources().getColor(R.color.colorPrimary));
            View view = snack.getView();
            FrameLayout.LayoutParams params =(FrameLayout.LayoutParams)view.getLayoutParams();
            params.gravity = Gravity.TOP;
            view.setLayoutParams(params);
            snack.show();
        }

        else {

            Snackbar snack = Snackbar.make(findViewById(android.R.id.content), resposta, Snackbar.LENGTH_LONG)
                    .setAction("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    }).setActionTextColor(getResources().getColor(R.color.colorPrimary));
            View view = snack.getView();
            FrameLayout.LayoutParams params =(FrameLayout.LayoutParams)view.getLayoutParams();
            params.gravity = Gravity.TOP;
            view.setLayoutParams(params);
            snack.show();
        }
    }

}
