package com.example.varzea_organizada;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.InetAddresses;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.share.Share;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {



    private SharedPreferences sharedPreferences;

    private SharedPreferences.Editor editor;

    int count=0;
    Timer timer;



    FirebaseAuth auth;
    FloatingActionButton fb, ig, google;
    TextView cadastre, esqueci, validaLogin, vitrine;
    float v = 0;
    LinearLayout linear;

    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = this.getSharedPreferences("login", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        auth = FirebaseAuth.getInstance();



        fb = findViewById(R.id.fab_fb);
        ig = findViewById(R.id.fab_insta);
        google = findViewById(R.id.fab_google);

        final EditText mEmail = findViewById(R.id.email);
        final EditText mSenha = findViewById(R.id.senha);
        vitrine = findViewById(R.id.vitrine);
        cadastre = findViewById(R.id.cadastro);
        esqueci = findViewById(R.id.recuperarSenha);
        validaLogin = findViewById(R.id.validaLogin);
        progressBar = findViewById(R.id.progressBar);
        linear = findViewById(R.id.linear);
        timer = new Timer();


        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                count++;
                progressBar.setProgress(count);
                if (count==100){
                    timer.cancel();
                }
            }
        };
        timer.schedule(timerTask, 0, 10);

        vitrine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent i = new Intent(getApplicationContext(), com.example.varzea_organizada.vitrine.class);
                startActivity(i);
            }
        });


        validaLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = mEmail.getText().toString().trim();
                String senha = mSenha.getText().toString().trim();

                if (mEmail.getText().toString().isEmpty()) {
                    mEmail.setError("Informe o seu email.");
                    return;
                }

                if (mSenha.getText().toString().isEmpty()) {
                    mSenha.setError("Informe a sua senha.");
                    return;
                } else
                    {
                        progressBar.setVisibility(View.VISIBLE);
                        auth.signInWithEmailAndPassword(email, senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressBar.setVisibility(View.INVISIBLE);
                            if (task.isSuccessful())
                            {   progressBar.setVisibility(View.INVISIBLE);
                                Intent i = new Intent(getApplicationContext(), newPassword.class);
                                i.putExtra("email", mEmail.getText().toString());
                                startActivity(i);
                                //startActivity(new Intent(getApplicationContext(), newPassword.class));

                            }

                            else {
                                String resposta = task.getException().toString();
                                opcoesErro(resposta);
                            }

                        }
                    });
                }

            }
        });

        esqueci.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), recuperarSenha.class);
                startActivity(intent);
            }
                                       });
        cadastre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), cadastro_comissao.class);
                startActivity(i);
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
                startActivity(i);            }
        });


        fb.setTranslationY(300);
        ig.setTranslationY(300);
        google.setTranslationY(300);

        fb.setAlpha(v);
        ig.setAlpha(v);
        google.setAlpha(v);

        fb.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(400).start();
        ig.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(400).start();
        google.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(400).start();
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