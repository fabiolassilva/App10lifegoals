package com.example.aluno.myapplication.Modelo;

import java.io.Serializable;

public class Meta implements Serializable {
    private int numero;
    private String descricao;
    private String publica;
    private int periodo;
    private String situacao;
    private String nomeUser;
    private int data; //data de criação das metas



    public Meta() {
    }

    public Meta(int numero, String descricao, String publica, int periodo, String situacao, String nomeUser, int data) {
        this.numero = numero;
        this.descricao = descricao;
        this.publica = publica;
        this.periodo = periodo;
        this.situacao = situacao;
        this.nomeUser = nomeUser;
        this.data = data;
    }


    public String getNomeUser(){
        return nomeUser;
    }


    public void setNomeUser(String nome){
        this.nomeUser = nome;
    }


    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getPublica() {
        return publica;
    }

    public void setPublica(String publica) {
        this.publica = publica;
    }

    public int getPeriodo() {
        return periodo;
    }

    public void setPeriodo(int periodo) {
        this.periodo = periodo;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }
}
