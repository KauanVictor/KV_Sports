package com.example.varzea_organizada;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class LogintabFragment extends Fragment


{


    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.login, container, false);

        final EditText email = root.findViewById(R.id.email);
        final EditText senha = root.findViewById(R.id.senha);
        final TextView entrar = root.findViewById(R.id.validaLogin);
        final TextView recuperarSenha = root.findViewById(R.id.recuperarSenha);



        entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (email.getText().toString().isEmpty()) {
                    email.setError("Informe o seu email.");
                    return;
                }

                if (senha.getText().toString().isEmpty()) {
                    senha.setError("Informe a sua senha.");
                    return;
                } else {
                    Toast.makeText(getActivity(), "Funfou!", Toast.LENGTH_SHORT).show();
                }

            }
        });


        recuperarSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), recuperarSenha.class);
                startActivity(i);
            }
        });



        return root;
    }
}
