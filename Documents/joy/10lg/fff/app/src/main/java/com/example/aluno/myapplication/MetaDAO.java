package com.example.aluno.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.aluno.myapplication.Modelo.Meta;

import java.util.ArrayList;
import java.util.List;

public class MetaDAO {
    private static SQLiteDatabase bd;
    private CriaBanco openHelper;

    public MetaDAO(Context contexto) {
        openHelper = new CriaBanco(contexto);
    }

    public void abrir() {
        bd = openHelper.getWritableDatabase();
    }

    public void fechar() {
        bd.close();
    }

    public Meta criarMeta(String descricao, String publica, int periodo, String situacao, int data, String nomeUser) {
        ContentValues valores = new ContentValues();
        valores.put("descricao", descricao);
        valores.put("publica", publica);
        valores.put("periodo", periodo);
        valores.put("situacao", situacao);
        valores.put("data", data);
        valores.put("nomeUser", nomeUser);
        abrir();
        //long numero = bd.insert("meta", null, valores);
        bd.insert("meta", null, valores);
        fechar();
        Meta meta = new Meta();
        //meta.setNumero(numero);
        meta.setDescricao(descricao);
        meta.setPublica(publica);
        meta.setPeriodo(periodo);
        meta.setSituacao(situacao);
        meta.setData(data);
        meta.setNomeUser(nomeUser);
        return meta;
    }

    public ArrayList<String> procurarMetas(String usuario) {
        ArrayList<String> metas = new ArrayList<String>();
        String[] campos = {"nomeUser", "descricao"};
        String where = "nomeUser" + "= '" + usuario + "'";
        abrir();
        Cursor cursor = bd.rawQuery("Select descricao from meta where nomeUser=?", new String[]{"" + usuario});
        while (cursor.moveToNext()) {
            metas.add(cursor.getString(cursor.getColumnIndex("descricao")));
        }

        cursor.close();
        fechar();
        return metas;
    }

    public List<Meta> lerMetas(String usuario) {
        List<Meta> metas = new ArrayList<>();
        abrir();
        Cursor cursor = bd.rawQuery("Select * from meta where nomeUser=?", new String[]{"" + usuario});
        while (cursor.moveToNext()) {
            Meta i = new Meta();
            i.setNumero(cursor.getInt(0));
            i.setDescricao(cursor.getString(1));
            i.setPublica(cursor.getString(2));
            i.setPeriodo(cursor.getInt(3));
            i.setSituacao(cursor.getString(4));
            i.setNomeUser(cursor.getString(5));
            metas.add(i);

        }
        cursor.close();
        fechar();
        return metas;

    }

    public int procurarNum(String usuario, String descricao) {
        int num = 0;
        abrir();
        Cursor cursor = bd.rawQuery("Select numero from meta where nomeUser=? and descricao=?", new String[]{"" + usuario, "" + descricao});
        while (cursor.moveToNext()) {
            num = Integer.parseInt(cursor.getString(cursor.getColumnIndex("numero")));
        }

        cursor.close();
        fechar();
        return num;
    }

    public void concluir(String usuario, int num) {
        ContentValues values = new ContentValues();
        values.put("nomeUser", usuario);
        values.put("numero", num);
        values.put("situacao", "C");

        String[] selectionArgs = {"" + usuario, "" + num};
        // String where = "usuario" + "= '" + user +"'";
        bd = openHelper.getReadableDatabase();
        int count = bd.update("meta", values, "nomeUser=? and numero=?", selectionArgs);
        fechar();
    }




    public ArrayList<Meta> metasHome(String usuario) {
        ArrayList<Meta> metas = new ArrayList<Meta>();
        String[] campos = {"nomeUser", "descricao"};
        String where = "nomeUser" + "!= '" + usuario + "'";
        abrir();
        Cursor cursor = bd.rawQuery("Select descricao, nomeUser, numero from meta where publica='sim' AND nomeUser=? LIMIT 20", new String[]{"" + usuario});
        while (cursor.moveToNext()) {
            Meta m = new Meta();
            m.setDescricao(cursor.getString(cursor.getColumnIndex("descricao")));
            m.setNomeUser(cursor.getString(cursor.getColumnIndex("nomeUser")));
            m.setNumero(cursor.getInt(cursor.getColumnIndex("numero")));
            metas.add(m);
        }

        cursor.close();
        fechar();
        return metas;
    }

    public ArrayList<Meta> metasConcluidas(String usuario) {

        ArrayList<Meta> metasconcluidas = new ArrayList<Meta>();
        abrir();
        Cursor cursor = bd.rawQuery("Select descricao from meta where situacao='C' and nomeUser=?", new String[]{""+usuario});

        while (cursor.moveToNext()) {
            Meta m = new Meta();
            m.setDescricao(cursor.getString(cursor.getColumnIndex("descricao")));
            metasconcluidas.add(m);
        }

        cursor.close();
        fechar();
        return metasconcluidas;
    }


    public ArrayList<Meta> metasAtuais(String usuario) {
        System.out.println("METAS ATUAIS");
        ArrayList<Meta> metasatuais = new ArrayList<Meta>();
        String[] campos = {"descricao", "periodo"};
        String where = "nomeUser" + "= '" + usuario + "'";
        abrir();
        Cursor cursor = bd.rawQuery("Select descricao, periodo from meta where situacao='N' and nomeUser=?", new String[]{"" + usuario});
        while (cursor.moveToNext()) {
            Meta m = new Meta();
            m.setDescricao(cursor.getString(cursor.getColumnIndex("descricao")));
            m.setPeriodo(cursor.getInt(cursor.getColumnIndex("periodo")));
            metasatuais.add(m);
        }

        cursor.close();
        fechar();
        System.out.println("ACHO QUE DEU CERTO");
        return metasatuais;
    }

    public ArrayList<Meta> procurarMetaSituacao(String usuario){
        System.out.println("PROCURAR META SITUAÇÃO");
        ArrayList<Meta> metasSituacao = new ArrayList<>();
        String[] campos =  {"nomeUser", "descricao"};
        String where = "nomeUser" + "= '" + usuario +"'";
        abrir();
        Cursor cursor = bd.rawQuery("Select descricao from meta where nomeUser=? and situacao='C'", new String[]{""+usuario});
        while(cursor.moveToNext()){
            Meta m = new Meta();
            m.setDescricao(cursor.getString(cursor.getColumnIndex("descricao")));
            metasSituacao.add(m);
        }

        cursor.close();
        fechar();
        System.out.println("ACHO QUE DEU CERTO");
        System.out.println(""+metasSituacao);
        return metasSituacao;
    }
}

