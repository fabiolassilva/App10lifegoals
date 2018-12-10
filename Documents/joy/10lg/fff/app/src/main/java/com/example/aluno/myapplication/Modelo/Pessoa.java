package com.example.aluno.myapplication.Modelo;

import android.graphics.Bitmap;

import java.io.Serializable;

public class Pessoa implements Serializable {

    private String usuario;
    private String nome;
    private String senha;
    private String email;
    private String dataNasc;
    private String termos;
    private Bitmap image;
    private String bio;

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public Pessoa() {
    }

    public Pessoa(String usuario, String nome, String senha, String email, String dataNasc, String termos) {
        this.usuario = usuario;
        this.nome = nome;
        this.senha = senha;
        this.email = email;
        this.dataNasc = dataNasc;
        this.termos = termos;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDataNasc() {
        return dataNasc;
    }

    public void setDataNasc(String dataNasc) {
        this.dataNasc = dataNasc;
    }

    public String getTermos() {
        return termos;
    }

    public void setTermos(String termos) {
        this.termos = termos;
    }
}
