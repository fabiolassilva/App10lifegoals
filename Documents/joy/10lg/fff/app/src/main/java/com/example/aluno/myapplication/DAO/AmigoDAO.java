package com.example.aluno.myapplication.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.aluno.myapplication.CriaBanco;
import com.example.aluno.myapplication.Modelo.Amigo;

import java.util.ArrayList;

public class AmigoDAO {
    private static SQLiteDatabase bd;
    private CriaBanco openHelper;

    public AmigoDAO(Context contexto) {
        openHelper = new CriaBanco(contexto);
    }

    public void abrir() {
        bd = openHelper.getWritableDatabase();
    }

    public void fechar() {
        bd.close();
    }

    public Amigo adicionarAmigo(String usuarioManda, String usuarioRecebe) {
        ContentValues valores = new ContentValues();
        valores.put("usuarioManda", usuarioManda);//nome do solicitado
        valores.put("usuarioRecebe", usuarioRecebe);//quem enviovu
        valores.put("amigo", "N");//pra saber se ele acietou ou nao
        abrir();
        //long numero = bd.insert("meta", null, valores);
        bd.insert("amigos", null, valores);
        fechar();
        Amigo amg = new Amigo();
        //meta.setNumero(numero);
        amg.setNomeAmg(usuarioRecebe);
        amg.setUser(usuarioManda);
        return amg;
    }

    //PROCURAR AMIGOS DO USUARIO QUE RECEBE
    public ArrayList<String> procurarSolicitacoes(String usuario) {
        ArrayList<String> amigos = new ArrayList<String>();
        abrir();
        Cursor cursor = bd.rawQuery("Select usuarioManda from amigos where usuarioRecebe=? and amigo=?", new String[]{"" + usuario, "N"});
        while (cursor.moveToNext()) {
            amigos.add(cursor.getString(cursor.getColumnIndex("usuarioManda")));
        }

        cursor.close();
        fechar();
        return amigos;
    }

    public boolean procurarAmigos(String usuario, String amigo) {
        boolean amg = false;
        abrir();
        Cursor cursor = bd.rawQuery("Select usuarioManda from amigos where usuarioRecebe=? and amigo=?", new String[]{"" + usuario, "S"});
        Cursor cursor2 = bd.rawQuery("Select usuarioRecebe from amigos where usuarioManda=? and amigo=?", new String[]{"" + usuario, "S"});
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                if(cursor.getString(cursor.getColumnIndex("usuarioManda")).equals(amigo)){
                    amg=true;
                }
            }
        }
        if (cursor2.getCount() > 0) {
            while (cursor2.moveToNext()) {
                if(cursor2.getString(cursor2.getColumnIndex("usuarioRecebe")).equals(amigo)){
                    amg=true;
                }
            }
        }



        cursor.close();
        cursor2.close();
        fechar();

        return amg;
    }

    public boolean mandouSolicitacao(String usuario, String amigo){
        boolean amg = false;
        abrir();
        Cursor cursor2 = bd.rawQuery("Select usuarioRecebe from amigos where usuarioManda=? and amigo=?", new String[]{"" + usuario, "N"});
        if (cursor2.getCount() > 0) {
            while (cursor2.moveToNext()) {
                if(cursor2.getString(cursor2.getColumnIndex("usuarioRecebe")).equals(amigo)){
                    amg=true;
                }
            }
        }

        cursor2.close();
        fechar();

        return amg;
    }

    public ArrayList<String> listarAmigos(String usuario) {
        ArrayList<String> amigos = new ArrayList<String>();
        abrir();
        Cursor cursor = bd.rawQuery("Select usuarioManda from amigos where usuarioRecebe=? and amigo=?", new String[]{"" + usuario, "S"});
        Cursor cursor2 = bd.rawQuery("Select usuarioRecebe from amigos where usuarioManda=? and amigo=?", new String[]{"" + usuario, "S"});
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                amigos.add(cursor.getString(cursor.getColumnIndex("usuarioManda")));
            }
        }
        if (cursor2.getCount() > 0) {
            while (cursor2.moveToNext()) {
                amigos.add(cursor2.getString(cursor2.getColumnIndex("usuarioRecebe")));
            }
        }
        cursor.close();
        fechar();
        return amigos;
    }

    public void remover(String usuarioManda, String usuarioRecebe) {

        String[] selectionArgs = {"" + usuarioManda, "" + usuarioRecebe, "" + usuarioRecebe, "" + usuarioManda};

        bd = openHelper.getReadableDatabase();
        bd.delete("amigos", "usuarioManda=? and usuarioRecebe=? or (usuarioManda=? and usuarioRecebe=?)", selectionArgs);
        fechar();
    }

    public void atualizar(String usuarioManda, String usuarioRecebe) {
        System.out.println("entrei aqui");
        ContentValues values = new ContentValues();
        bd = openHelper.getReadableDatabase();
        values.put("amigo", "S");
        values.put("usuarioManda", usuarioManda);
        values.put("usuarioRecebe", usuarioRecebe);

        String[] selectionArgs = {"" + usuarioManda, "" + usuarioRecebe};
        // String where = "usuario" + "= '" + user +"'";

        bd.update("amigos", values, "usuarioManda=? and usuarioRecebe=?", new String[]{usuarioManda,usuarioRecebe});
        fechar();
    }

    public boolean recebeuSolicitacao(String amigo, String eu) {
        boolean amg = false;
        abrir();
        Cursor cursor = bd.rawQuery("select usuarioManda from amigos where usuarioRecebe = ? and amigo = ?", new String[]{eu, "N"});
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                if(cursor.getString(cursor.getColumnIndex("usuarioManda")).equals(amigo)){
                    amg=true;
                }
            }
        }
        cursor.close();
        fechar();

        return amg;
    }
}
