package com.example.varzea_organizada;

import android.net.Uri;

public class Salvos
{
    private String Nome;
    private String Telefone;
    private String Email;
    private String Estado;
    private String Modalidade;
    private String Disponibilidade;
    private String Peso;
    private String Altura;
    private String Idade;
    private String Posicao;
    private String Categoria;
    private Uri Img;

    public Salvos () {}


    public String getNome() {
        return Nome;
    }
    public void setNome(String nome) {
        Nome = nome;
    }

    public Uri getImg() { return Img; }
    public void setImg(Uri img) {
        Img = img;
    }

    public String getCategoria() {
        return Categoria;
    }
    public void setCategoria(String categoria) {
        Categoria = categoria;
    }

    public String getIdade(){return Idade;}
    public void setIdade(String idade) { Idade = idade; }

    public String getTelefone() {
        return Telefone;
    }
    public void setTelefone(String telefone) {
        Telefone = telefone;
    }

    public String getEmail() {return Email;}
    public void setEmail(String email) {Email = email;}

    public String getEstado() {
        return Estado;
    }
    public void setEstado(String estado) {
        this.Estado = estado;
    }

    public String getAltura() {
        return Altura;
    }
    public void setAltura(String altura) {
        this.Altura = altura;
    }

    public String getModalidade() {
        return Modalidade;
    }
    public void setModalidade(String modalidade) {
        this.Modalidade = modalidade;
    }

    public String getPeso() {
        return Peso;
    }
    public void setPeso(String peso) {
        this.Peso = peso;
    }


    public String getPosicao() {
        return Posicao;
    }
    public void setPosicao(String posicao) {
        this.Posicao = posicao;
    }


    public String getDisponibilidade() {return Disponibilidade;}
    public void setDisponibilidade(String disponibilidade) {Disponibilidade = disponibilidade;}


}
