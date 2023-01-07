package com.example.varzea_organizada;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class vitrine extends AppCompatActivity {

    Button voltar;


    DBHelper DB;

    TextView TelaFiltrar;

    RecyclerView recyclerview;
    DatabaseReference database;
    MyAdapter myAdapter;
    ArrayList <Salvos> list;

    ProgressDialog progressDialog;

    int count=0;
    Timer timer;
    ProgressBar progressBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vitrine);
        DB = new DBHelper(this);


        progressBar = findViewById(R.id.progressBar);
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
        timer.schedule(timerTask, 0, 1);



        voltar = findViewById(R.id.voltar);
        TelaFiltrar = findViewById(R.id.Telafiltrar);

        recyclerview = findViewById(R.id.userList);
        database = FirebaseDatabase.getInstance().getReference("Atletas");
        recyclerview.setHasFixedSize(true);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<> ();
        myAdapter = new MyAdapter(this, list);
        recyclerview.setAdapter(myAdapter);

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Salvos salvos = dataSnapshot.getValue(Salvos.class);
                    list.add(salvos);
                }
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DB = new DBHelper(this);



        TelaFiltrar.setOnClickListener(new View.OnClickListener()
                                       {
            @Override
            public void onClick(View view)
            {
                final androidx.appcompat.app.AlertDialog.Builder alert = new AlertDialog.Builder(vitrine.this);
                final  View custom = getLayoutInflater().inflate(R.layout.activity_filtrar, null);
                alert.setView(custom);
                alert.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });


                alert.setPositiveButton("Concluir", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {

                    }
                });
                alert.show();
            }});





        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(vitrine.this, MainActivity.class);
                startActivity(intent);
                }
        });
    }


}
