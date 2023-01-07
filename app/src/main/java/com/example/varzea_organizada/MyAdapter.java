package com.example.varzea_organizada;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{

    Context context;
    ArrayList<Salvos> list;
    ImageView img;
    private FirebaseDatabase firebaseDatabase  = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();
    private DatabaseReference first = databaseReference.child("images");

    public MyAdapter(Context context, ArrayList<Salvos> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        img = v.findViewById(R.id.img);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Salvos salvos = list.get(position);
        holder.firstName.setText(salvos.getNome());
        holder.phone.setText(salvos.getTelefone());
        holder.email.setText(salvos.getEmail());
        holder.estado.setText(salvos.getEstado());
        holder.modalidade.setText(salvos.getModalidade());
        holder.altura.setText(salvos.getAltura());
        holder.peso.setText(salvos.getPeso());
        holder.idade.setText(salvos.getIdade());
        holder.posicao.setText(salvos.getPosicao());
        holder.categoria.setText(salvos.getCategoria());
        holder.img.setImageURI(salvos.getImg());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView firstName, phone, email, estado, modalidade, idade, altura, peso, posicao, categoria;
        ImageView img;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            firstName = itemView.findViewById(R.id.tvfirstname);
            phone = itemView.findViewById(R.id.tvphone);
            email = itemView.findViewById(R.id.tvEmail);
            estado = itemView.findViewById(R.id.tvestado);
            modalidade = itemView.findViewById(R.id.tvmodalidade);
            idade = itemView.findViewById(R.id.tvIdade);
            altura = itemView.findViewById(R.id.tvaltura);
            peso = itemView.findViewById(R.id.tvpeso);
            posicao = itemView.findViewById(R.id.tvposicao);
            categoria = itemView.findViewById(R.id.tvCategoria);
            img = itemView.findViewById(R.id.img);
        }
    }
    
    protected void onStart(){
        first.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String link = snapshot.getValue(String.class);
                Picasso.get().load(link).into(img);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
