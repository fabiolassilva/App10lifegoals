package com.example.aluno.myapplication.Modelo;

public class PessoaSalvaMeta {

    private String usuarioLogado;
    private String usuarioCriador;
    private String descrMeta;
    private String efetiva;
    private int id;

    public PessoaSalvaMeta() {
    }

    public String getEfetiva() {
        return efetiva;
    }

    public void setEfetiva(String efetiva) {
        this.efetiva = efetiva;
    }

    public PessoaSalvaMeta(String usuarioLogado, String usuarioCriador, String descrMeta, String efetiva) {

        this.usuarioLogado = usuarioLogado;
        this.usuarioCriador = usuarioCriador;
        this.descrMeta = descrMeta;
        this.efetiva = efetiva;

    }

    public String getUsuarioLogado() {
        return usuarioLogado;
    }

    public void setUsuarioLogado(String usuarioLogado) {
        this.usuarioLogado = usuarioLogado;
    }

    public String getUsuarioCriador() {
        return usuarioCriador;
    }

    public void setUsuarioCriador(String usuarioCriador) {
        this.usuarioCriador = usuarioCriador;
    }

    public String getDescrMeta() {
        return descrMeta;
    }

    public void setDescrMeta(String descrMeta) {
        this.descrMeta = descrMeta;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String toLista() {
        return "\n" + usuarioCriador + "\n" + descrMeta + "\n";
    }

}
