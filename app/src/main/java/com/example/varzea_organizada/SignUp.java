package com.example.varzea_organizada;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class SignUp extends Fragment

{

    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)

    {

        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.activity_cadastro_comissao, container, false);
        final EditText nomeAtleta = root.findViewById(R.id.nomeAtleta);
        final EditText emailAtleta = root.findViewById(R.id.emailAtleta);
        final EditText telefoneAtleta = root.findViewById(R.id.telefoneAtleta);
        final EditText senhaAtleta = root.findViewById(R.id.senhaAtleta);

        final TextView registro = root.findViewById(R.id.registrar);

        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nomeAtleta.getText().toString().isEmpty()) {
                    nomeAtleta.setError("Informe o seu nome.");
                    return;
                }

                if (telefoneAtleta.getText().toString().isEmpty()) {
                    telefoneAtleta.setError("Informe o telefone para contato.");
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

                else {
                    Toast.makeText(getActivity(), "Funfou", Toast.LENGTH_LONG).show();}
            }
        });




        return root;
    }
}
