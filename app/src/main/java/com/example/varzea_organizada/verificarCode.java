package com.example.varzea_organizada;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class verificarCode extends AppCompatActivity {

    TextView verificarCodigo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verificar_code);
        verificarCodigo = findViewById(R.id.verificarCodigo);

        verificarCodigo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), newPassword.class);
                startActivity(intent);
            }
        });
    }
}