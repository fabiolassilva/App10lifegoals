package com.example.aluno.myapplication.Modelo;

import java.io.Serializable;
import java.util.ArrayList;

public class Comenta extends ArrayList<String> implements Serializable {

    private int num;
    private String coment;
    private String usuarioComenta;
    private int numMeta;

    public Comenta(){
        //.
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public Comenta(String coment, String usuarioComenta){
        this.coment = coment;
        this.usuarioComenta = usuarioComenta;
    }

    public String getComent() {
        return coment;
    }

    public void setComent(String coment) {
        this.coment = coment;
    }

    public String getUsuarioComenta() {
        return usuarioComenta;
    }

    public void setUsuarioComenta(String usuarioComenta) {
        this.usuarioComenta = usuarioComenta;
    }

    public int getNumMeta() {
        return numMeta;
    }

    public void setNumMeta(int numMeta) {
        this.numMeta = numMeta;
    }
}
