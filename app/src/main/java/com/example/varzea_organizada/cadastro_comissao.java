package com.example.varzea_organizada;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.InputStream;
import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.UUID;

public class cadastro_comissao extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final int REQUEST_CODE_STORAGE_PERMISSION = 1;
    private static final int REQUEST_CODE_SELECT_IMAGE = 2;
    private FirebaseAuth auth;
    public Uri imageUri;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    DBHelper DB;

    ProgressDialog progressDialog;

    ScrollView mainLayout;

    String item;
    String itemmodalidade;
    String itemcategoria;


    String[] estados = {"Selecione o seu estado...", "Acre-AC", "Alagoas-AL", "Amapá-AP", "Amazonas-AM", "Bahia-BA", "Ceará-CE",
            "Distrito Federal-DF", "Espírito Santo-ES", "Goiás-GO", "Maranhão-MA", "Mato Grosso-MT", "Mato Grosso do Sul-MS", "Minas Gerais-MG", "Pará-PA", "Paraíba-PB",
            "Paraná-PR", "Pernambuco-PE", "Piauí-PI", "Rio de Janeiro-RJ", "Rio Grande do Norte-RN", "Rio Grande do Sul-RS", "Rondônia-RO",
            "Roraima-RR", "Santa Catarina-SC", "São Paulo-SP", "Sergipe-SE", "Tocantins-TO"};

    String[] modalidades = {"Selecione a modalidade...", "Futebol", "Basquete", "Vôlei", "Futsal", "Handebol"};

    String[] futebol = {"Goleiro", "Lateral Esquerdo", "Lateral Direito", "Zagueiro", "Volante", "Meia", "Atacante"};

    String[] cate = {"Selecione a categoria...",
            "Sub-7 (6 e 7 anos)",
            "Sub-8 (8 anos)",
            "Sub-9 (8 e 9 anos)",
            "Sub-11 (10 e 11 anos)",
            "Sub-13 (12 e 13 anos)",
            "Sub-15 (14 e 15 anos)",
            "Sub-17 (16 e 17 anos)",
            "Sub-20 (18, 19 e 20 anos)",
            "Adulto (20 anos em diante)",
            "Veterano (A partir dos 35 anos)"};


    String[] basquete = {"Ala", "Ala-Pivô", "Ala-Armador", "Armador", "Pivô"};

    String[] volei = {"Levantador", "Líbero", "Ponta Esquerda", "Ponta Direita", "Central", "Oposto"};

    String[] futsal = {"Ala-Direita", "Ala-Esquerda", "Fixo", "Goleiro", "Pivô"};

    String[] handebol = {"Armador Central", "Goleiro", "Meias", "Pivô", "Pontas"};


    EditText nomeAtleta, telefoneAtleta, emailAtleta, senhaAtleta, alturaAtleta, pesoAtleta, idadeAtleta, posicaoAtleta;
    Spinner estado, modalidade, categoria;

    TextView registrar;

    ImageView img;

    DatabaseReference reference;
    Salvos salvos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_comissao);
        nomeAtleta = findViewById(R.id.nomeAtleta);
        mainLayout = findViewById(R.id.mainLayout);
        emailAtleta = findViewById(R.id.emailAtleta);
        telefoneAtleta = findViewById(R.id.telefoneAtleta);
        senhaAtleta = findViewById(R.id.senhaAtleta);
        estado = findViewById(R.id.estado);
        modalidade = findViewById(R.id.modalidade);
        categoria = findViewById(R.id.categoria);
        alturaAtleta = findViewById(R.id.alturaAtleta);
        pesoAtleta = findViewById(R.id.pesoAtleta);
        idadeAtleta = findViewById(R.id.idadeAtleta);
        posicaoAtleta = findViewById(R.id.posicaoAtleta);
        img = findViewById(R.id.img);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        registrar = findViewById(R.id.registrar);
        reference = FirebaseDatabase.getInstance().getReference("Atletas");

        estado.setOnItemSelectedListener(this);
        modalidade.setOnItemSelectedListener(this);
        categoria.setOnItemSelectedListener(this);

        salvos = new Salvos();
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, estados);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter arrayAdapterCategoria = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, cate);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter arrayAdaptermodalidade = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, modalidades);
        arrayAdaptermodalidade.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        ArrayAdapter arrayAdapterfutebol = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, futebol);
        arrayAdapterfutebol.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        ArrayAdapter arrayAdapterbasquete = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, basquete);
        arrayAdapterbasquete.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        ArrayAdapter arrayAdaptervolei = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, volei);
        arrayAdaptervolei.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        ArrayAdapter arrayAdapterfutsal = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, futsal);
        arrayAdapterfutsal.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        ArrayAdapter arrayAdapterhandebol = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, handebol);
        arrayAdapterhandebol.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        estado.setAdapter(arrayAdapter);
        modalidade.setAdapter(arrayAdaptermodalidade);
        categoria.setAdapter(arrayAdapterCategoria);

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choosePicture();
            }
        });

        DB = new DBHelper(this);


        auth = FirebaseAuth.getInstance();


        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (nomeAtleta.getText().toString().isEmpty()) {
                    nomeAtleta.setError("Informe o nome seu nome.");
                    return;
                }
                if (telefoneAtleta.getText().toString().isEmpty()) {
                    telefoneAtleta.setError("Informe o telefone para contato.");
                    return;
                }

                if (alturaAtleta.getText().toString().isEmpty()) {
                    alturaAtleta.setError("Informe a sua altura.");
                    return;
                }

                if (pesoAtleta.getText().toString().isEmpty()) {
                    pesoAtleta.setError("Informe o seu peso.");
                    return;
                }

                if (idadeAtleta.getText().toString().isEmpty()) {
                    idadeAtleta.setError("Informe a sua idade.");
                    return;
                }

                if (emailAtleta.getText().toString().isEmpty()) {
                    emailAtleta.setError("Cadastre o seu email de acesso.");
                    return;
                }
                if (senhaAtleta.getText().toString().isEmpty()) {
                    senhaAtleta.setError("Cadastre a sua senha de acesso.");
                    return;
                }

                if (posicaoAtleta.getText().toString().isEmpty()) {
                    posicaoAtleta.setError("Informe a sua posição.");
                    return;
                }


                if (item == "Selecione o seu estado...") {
                    Toast.makeText(getApplicationContext(), "Selecione o seu estado.", Toast.LENGTH_SHORT).show();
                } else {
                    progressDialog = new ProgressDialog(cadastro_comissao.this);
                    progressDialog.show();
                    progressDialog.setContentView(R.layout.progress_dialog);
                    progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);


                    final String email = emailAtleta.getText().toString().trim();
                    String senha = senhaAtleta.getText().toString().trim();

                    auth.createUserWithEmailAndPassword(email, senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(cadastro_comissao.this, "Cadastro realizado com sucesso.", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(i);


                                salvos.setNome(nomeAtleta.getText().toString());
                                salvos.setTelefone(telefoneAtleta.getText().toString());
                                salvos.setEmail(emailAtleta.getText().toString());
                                salvos.setAltura(alturaAtleta.getText().toString());
                                salvos.setIdade(idadeAtleta.getText().toString());
                                salvos.setPeso(pesoAtleta.getText().toString());
                                salvos.setPosicao(posicaoAtleta.getText().toString());
                                //uploadPicture();
                                SaveValue(item, itemmodalidade, itemcategoria);


                                String key = reference.child("Atletas").push().getKey();
                                reference.child(key).setValue(salvos);


                            } else {
                                String resposta = task.getException().toString();
                                opcoesErro(resposta);
                            }
                        }
                    });


                    cadastrar();
                    auth = FirebaseAuth.getInstance();

                }
            }
        });
    }

    private void choosePicture() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data!=null && data.getData()!=null){
            imageUri = data.getData();
            img.setImageURI(imageUri);
            uploadPicture();
                
        }
    }

    private void uploadPicture() {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("Carregando...");
        pd.show();
        final String randomKey = UUID.randomUUID().toString();
        StorageReference riversRef = storageReference.child("images/" + randomKey);

        riversRef.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        pd.dismiss();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        pd.dismiss();
                        Snackbar.make(findViewById(android.R.id.content), "Erro ao carregar imagem, tente novamente.", Snackbar.LENGTH_LONG).show();
                        }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        double progressPercent = (100.00 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                        pd.setMessage((int) progressPercent + "%");
                    }
                });
    }

    private void opcoesErro(String resposta) {
        if (resposta.contains("least 6 characters")) {
            Snackbar snack = Snackbar.make(findViewById(android.R.id.content), "A sua senha deve ser maior que 5 caracteres.", Snackbar.LENGTH_LONG)
                    .setAction("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    }).setActionTextColor(getResources().getColor(R.color.colorPrimary));
            View view = snack.getView();
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();
            params.gravity = Gravity.TOP;
            view.setLayoutParams(params);
            snack.show();
        } else if (resposta.contains("address is badly")) {
            Snackbar snack = Snackbar.make(findViewById(android.R.id.content), "Formato de email inválido" +
                    ".", Snackbar.LENGTH_LONG)
                    .setAction("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    }).setActionTextColor(getResources().getColor(R.color.colorPrimary));
            View view = snack.getView();
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();
            params.gravity = Gravity.TOP;
            view.setLayoutParams(params);
            snack.show();
        } else if (resposta.contains("address is already")) {
            Snackbar snack = Snackbar.make(findViewById(android.R.id.content), "Email já cadastrado.", Snackbar.LENGTH_LONG)
                    .setAction("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    }).setActionTextColor(getResources().getColor(R.color.colorPrimary));
            View view = snack.getView();
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();
            params.gravity = Gravity.TOP;
            view.setLayoutParams(params);
            snack.show();
        } else if (resposta.contains("interrupted connection")) {
            Snackbar snack = Snackbar.make(findViewById(android.R.id.content), "Sem conexão com a internet.", Snackbar.LENGTH_LONG)
                    .setAction("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    }).setActionTextColor(getResources().getColor(R.color.colorPrimary));
            View view = snack.getView();
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();
            params.gravity = Gravity.TOP;
            view.setLayoutParams(params);
            snack.show();
        } else {
            Snackbar snack = Snackbar.make(findViewById(android.R.id.content), resposta, Snackbar.LENGTH_LONG)
                    .setAction("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    }).setActionTextColor(getResources().getColor(R.color.colorPrimary));
            View view = snack.getView();
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();
            params.gravity = Gravity.TOP;
            view.setLayoutParams(params);
            snack.show();
        }

    }


    private void cadastrar()
    {
        auth.createUserWithEmailAndPassword("kauan@gmail.com", "marciaregina").addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

            }
        });
    }




    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CODE_STORAGE_PERMISSION && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                selecionarFoto();
            } else {

                androidx.appcompat.app.AlertDialog.Builder dialog = new AlertDialog.Builder(cadastro_comissao.this);
                dialog.setIcon(R.drawable.ic_baseline_error_outline_24);
                dialog.setTitle("Erro");
                dialog.setMessage("Permissão negada.");
                dialog.setNeutralButton("Ok", null);
                dialog.show();
            }
        }
    }

    private void selecionarFoto() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_CODE_SELECT_IMAGE);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        //estado e spinnerModalidade
        item = estado.getSelectedItem().toString();
        itemmodalidade = modalidade.getSelectedItem().toString();
        itemcategoria = categoria.getSelectedItem().toString();


    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void SaveValue(String item, String itemmodalidade, String itemcategoria) {
        if (item == "Selecione o seu estado...") {
            Toast.makeText(this, "Selecione o seu estado.", Toast.LENGTH_SHORT).show();
        } else {
            salvos.setEstado(item);
            salvos.setModalidade(itemmodalidade);
            salvos.setCategoria(itemcategoria);

        }
    }

        }
