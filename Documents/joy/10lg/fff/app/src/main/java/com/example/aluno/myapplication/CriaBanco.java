package com.example.aluno.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CriaBanco extends SQLiteOpenHelper {

    public CriaBanco(Context context){
        super(context,"10lifegoals.db", null,1);
    }

    /*private static final String NOME_BANCO = "10lifegoals.db";
    private static final String NUMERO = "numero";
    private static final String DESCRICAO ="descricao";
    private static final String PUBLICA = "publica";
    private static final String PERIODO = "periodo";
    private static final String SITUACAO  = "situacao";
    private static final String DATA  = "data";

    private static final String USUARIO = "usuario";
    private static final String NOME = "nome";
    private static final String SENHA = "senha";
    private static final String EMAIL = "email";
    private static final String DATA_NASC = "dataNasc";
    private static final String TERMOS = "termos";

    private static final String ID = "id";
    private static final String USUARIOLOGADO = "usuarioLogado";
    private static final String USUARIOCRIADOR = "usuarioCriador";
    private static final String DESCRMETA = "descrMeta";*/


    @Override
    public void onCreate(SQLiteDatabase db) {
        StringBuilder comando = new StringBuilder();
        comando.append("CREATE TABLE pessoa(");
        comando.append("usuario TEXT PRIMARY KEY,");
        comando.append("nome TEXT,");
        comando.append("senha TEXT,");
        comando.append("email TEXT,");
        comando.append("dataNasc TEXT,");
        comando.append("termos TEXT,");
        comando.append("bio TEXT,");
        comando.append("image BLOB);");
        db.execSQL(comando.toString());

        comando = new StringBuilder();

        comando.append("CREATE TABLE meta(");
        comando.append("numero INTEGER PRIMARY KEY AUTOINCREMENT,");
        comando.append("descricao TEXT,");
        comando.append("publica TEXT,");
        comando.append("periodo INTEGER,");
        comando.append("situacao TEXT,");
        comando.append("data INTEGER,");
        comando.append("nomeUser TEXT,");
        comando.append("FOREIGN KEY(nomeUser) REFERENCES pessoa(usuario));");

        db.execSQL(comando.toString());

        comando = new StringBuilder();

        comando.append("CREATE TABLE pessoasalvameta(");
        comando.append("id INTEGER PRIMARY KEY AUTOINCREMENT,");
        comando.append("usuarioLogado TEXT,");
        comando.append("usuarioCriador TEXT,");
        comando.append("descrMeta TEXT,");
        comando.append("efetiva TEXT);");

        db.execSQL(comando.toString());

        comando = new StringBuilder();

        comando.append("CREATE TABLE amigos(");
        comando.append("id INTEGER PRIMARY KEY AUTOINCREMENT,");
        comando.append("usuarioManda TEXT,");
        comando.append("usuarioRecebe TEXT,");
        comando.append("amigo TEXT,");
        comando.append("FOREIGN KEY(usuarioRecebe) REFERENCES pessoa(usuario),");
        comando.append("FOREIGN KEY(usuarioManda) REFERENCES pessoa(usuario));");

        db.execSQL(comando.toString());

        comando = new StringBuilder();

        comando.append("CREATE TABLE comenta(");
        comando.append("num INTEGER PRIMARY KEY AUTOINCREMENT,");
        comando.append("coment  TEXT,");
        comando.append("usuarioComenta TEXT,");
        comando.append("numMeta INTEGER,");
        comando.append("FOREIGN KEY(numMeta) REFERENCES meta(numero),");
        comando.append("FOREIGN KEY(usuarioComenta) REFERENCES pessoa(usuario));");
        db.execSQL(comando.toString());
        comando = new StringBuilder();

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
