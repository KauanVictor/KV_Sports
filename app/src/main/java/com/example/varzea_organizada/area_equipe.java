package com.example.varzea_organizada;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.util.ArrayList;

public class area_equipe extends AppCompatActivity {

    public ListView listView;
    private SQLiteDatabase bancoDados;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area_equipe);

        ListarDados();




        ListView listView = findViewById(R.id.listView);

        findViewById(R.id.Editarcadastro).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Toast.makeText(getApplicationContext(), "Editar Cadastro", Toast.LENGTH_SHORT).show();

            }
        });

          findViewById(R.id.sair).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(area_equipe.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }



    private void ListarDados()
    {
        try {
            bancoDados = openOrCreateDatabase("Login.db", MODE_PRIVATE, null);
            Cursor meuCursor = bancoDados.rawQuery("SELECT id, username, password FROM coisa", null);
            ArrayList<String> linhas = new ArrayList<String>();
            ArrayAdapter meuadapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                    android.R.id.text1, linhas);
                    listView.setAdapter(meuadapter);
                    meuCursor.moveToFirst();
                    while (meuCursor!=null)
                    {
                        linhas.add(meuCursor.getString(1));
                        meuCursor.moveToNext();


                    }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}