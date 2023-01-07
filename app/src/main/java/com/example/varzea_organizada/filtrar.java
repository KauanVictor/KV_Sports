package com.example.varzea_organizada;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class filtrar extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    TextView textView;

    SeekBar seekBar;

    Spinner txtPosicao, txtLocalizacao, estado, spinnermodalidade, spnposicao, posicaobasquete, posicaovolei, posicaofutsal, posicaohandebol;

    ListView listView;

    Button voltar;

    ArrayList<String> arrayList;

    String item;
    String itemmodalidade;

    ArrayList<String> arrayListLocalizacao;

    ArrayList<String> arrayListZona;

    Dialog dialog;

    String [] modalidades = {"Selecione a modalidade...", "Futebol", "Basquete", "Vôlei", "Futsal","Handebol"};





    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtrar);



        txtLocalizacao = findViewById(R.id.txtEstado);
        spinnermodalidade = findViewById(R.id.modalidade);
        spinnermodalidade.setOnItemSelectedListener(this);


        ArrayAdapter arrayAdaptermodalidade = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, modalidades);
        arrayAdaptermodalidade.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);



        voltar = findViewById(R.id.voltar);

        listView = findViewById(R.id.list_view);

        List<String> estado = new ArrayList<>();

        arrayListLocalizacao = new ArrayList<>();
        arrayListLocalizacao.add("Acre-AC");
        arrayListLocalizacao.add("Alagoas-AL");
        arrayListLocalizacao.add("Amapá-AP");
        arrayListLocalizacao.add("Amazonas-AM");
        arrayListLocalizacao.add("Bahia-BA");
        arrayListLocalizacao.add("Ceará-CE");
        arrayListLocalizacao.add("Espírito Santo-ES");
        arrayListLocalizacao.add("Goiás-GO");
        arrayListLocalizacao.add("Maranhão-MA");
        arrayListLocalizacao.add("Mato Grosso-MT");
        arrayListLocalizacao.add("Mato Grosso do Sul-MS");
        arrayListLocalizacao.add("Minas Gerais-MG");
        arrayListLocalizacao.add("Pará-PA");
        arrayListLocalizacao.add("Paraíba-PB");
        arrayListLocalizacao.add("Paraná-PR");
        arrayListLocalizacao.add("Pernambuco-PE");
        arrayListLocalizacao.add("Piauí-PI");
        arrayListLocalizacao.add("Rio de Janeiro-RJ");
        arrayListLocalizacao.add("Rio Grande do Norte-RN");
        arrayListLocalizacao.add("Rio Grande do Sul-RS");
        arrayListLocalizacao.add("Rondônia-RO");
        arrayListLocalizacao.add("Roraima-RR");
        arrayListLocalizacao.add("Santa Catarina-SC");
        arrayListLocalizacao.add("São Paulo-SP");
        arrayListLocalizacao.add("Sergipe-SE");
        arrayListLocalizacao.add("Tocantins-TO");
        arrayListLocalizacao.add("Distrito Federal-DF");


        ArrayAdapter<String> adapterEstado = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, estado);

        adapterEstado.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        txtLocalizacao.setAdapter(adapterEstado);

        List<String> posicao = new ArrayList<>();
        arrayList = new ArrayList<>();
        arrayList.add("Goleiro");
        arrayList.add("Lateral Direito");
        arrayList.add("Lateral Esquerdo");
        arrayList.add("Zagueiro");
        arrayList.add("Volante");
        arrayList.add("Meia");
        arrayList.add("Atacante");

        ArrayAdapter<String> adapterPosicao = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, posicao);

        adapterEstado.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        txtPosicao.setAdapter(adapterPosicao);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                textView.setText("Buscar atletas até: " + progress + " anos");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });





        txtPosicao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = new Dialog(filtrar.this);
                dialog.setContentView(R.layout.posicao);
                dialog.getWindow().setLayout(650, 850);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

                EditText editText = dialog.findViewById(R.id.edit_text);
                ListView listView = dialog.findViewById(R.id.list_view);

                final ArrayAdapter<String> adapter = new ArrayAdapter<>(filtrar.this
                        , android.R.layout.simple_list_item_1, arrayList);

                listView.setAdapter(adapter);


                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
                    {
                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        adapter.getFilter().filter(charSequence);
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        dialog.dismiss();
                    }
                });
            }
        });



        txtLocalizacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = new Dialog(filtrar.this);
                dialog.setContentView(R.layout.localizacao);
                dialog.getWindow().setLayout(650, 1000);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

                EditText editText = dialog.findViewById(R.id.edit_text);
                ListView listView = dialog.findViewById(R.id.list_view);

                final ArrayAdapter<String> adapter = new ArrayAdapter<>(filtrar.this
                        , android.R.layout.simple_list_item_1, arrayListLocalizacao);

                listView.setAdapter(adapter);


                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {



                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        adapter.getFilter().filter(charSequence);
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        dialog.dismiss();
                    }
                });
            }
        });



        arrayListZona = new ArrayList<>();
            arrayListZona.add("Norte");
            arrayListZona.add("Nordeste");
            arrayListZona.add("Centro-Oeste");
            arrayListZona.add("Sudeste");
            arrayListZona.add("Sul");





    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        if (position == 1)
        {
            spnposicao.setVisibility(View.VISIBLE);
            posicaobasquete.setVisibility(View.INVISIBLE);
            posicaovolei.setVisibility(View.INVISIBLE);
            posicaofutsal.setVisibility(View.INVISIBLE);
            posicaohandebol.setVisibility(View.INVISIBLE);

        }

        if (position == 2) {
            posicaobasquete.setVisibility(View.VISIBLE);
            spnposicao.setVisibility(View.INVISIBLE);
            posicaovolei.setVisibility(View.INVISIBLE);
            posicaofutsal.setVisibility(View.INVISIBLE);
            posicaohandebol.setVisibility(View.INVISIBLE);
        }

        if (position == 3){
            posicaobasquete.setVisibility(View.INVISIBLE);
            spnposicao.setVisibility(View.INVISIBLE);
            posicaovolei.setVisibility(View.VISIBLE);
            posicaofutsal.setVisibility(View.INVISIBLE);
            posicaohandebol.setVisibility(View.INVISIBLE);
        }

        if (position == 4){
            posicaobasquete.setVisibility(View.INVISIBLE);
            spnposicao.setVisibility(View.INVISIBLE);
            posicaovolei.setVisibility(View.INVISIBLE);
            posicaofutsal.setVisibility(View.VISIBLE);
            posicaohandebol.setVisibility(View.INVISIBLE);
        }

        if (position == 5){
            posicaobasquete.setVisibility(View.INVISIBLE);
            spnposicao.setVisibility(View.INVISIBLE);
            posicaovolei.setVisibility(View.INVISIBLE);
            posicaofutsal.setVisibility(View.INVISIBLE);
            posicaohandebol.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}