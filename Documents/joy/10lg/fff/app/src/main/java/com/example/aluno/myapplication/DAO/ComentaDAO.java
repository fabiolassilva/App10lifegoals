package com.example.aluno.myapplication.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.aluno.myapplication.CriaBanco;
import com.example.aluno.myapplication.Modelo.Comenta;

import java.util.ArrayList;

public class ComentaDAO {

    private static SQLiteDatabase bd;
    private CriaBanco openHelper;

    public ComentaDAO(Context contexto) {
        openHelper = new CriaBanco(contexto);
    }

    public void abrir() {
        bd = openHelper.getWritableDatabase();
    }

    public void fechar() {
        bd.close();
    }

    public Comenta comentar(String coment, String usuarioComenta, int num) {
        ContentValues valores = new ContentValues();
        valores.put("coment", coment);
        valores.put("usuarioComenta", usuarioComenta);
        valores.put("numMeta", num);
        abrir();
        bd.insert("comenta", null, valores);
        Comenta comentario = new Comenta();
        comentario.setComent(coment);
        comentario.setUsuarioComenta(usuarioComenta);
        comentario.setNumMeta(num);
        fechar();
        return comentario;
    }

    public ArrayList<Comenta> comentarios(int numMeta) {
        ArrayList<Comenta> comentarios = new ArrayList<>();
        int i;
        abrir();
        //for (i=0; i < numMeta.size(); i++) {
            Cursor cursor = bd.rawQuery("Select coment, usuarioComenta from comenta where numMeta = ?", new String[]{"" + numMeta});
            while (cursor.moveToNext()) {
                Comenta c = new Comenta();
                c.setUsuarioComenta(cursor.getString(cursor.getColumnIndex("usuarioComenta")));
                c.setComent(cursor.getString(cursor.getColumnIndex("coment")));
                c.setNumMeta(numMeta);
                comentarios.add(c);
                //System.out.println(c.getComent()+" "+i+"   "+cursor.getCount());
            }
            cursor.close();
        //}
        fechar();
        return comentarios;
    }

    public ArrayList<Integer> numeroMeta(String usuarioComenta) {
        ArrayList<Integer> numero = new ArrayList<>();
        abrir();
        Cursor cursor = bd.rawQuery("Select numMeta from comenta where usuarioComenta = ?", new String[]{"" + usuarioComenta});
        while (cursor.moveToNext()) {
            numero.add(Integer.parseInt(cursor.getString(cursor.getColumnIndex("numMeta"))));
        }
        cursor.close();
        fechar();
        return numero;
    }
}
