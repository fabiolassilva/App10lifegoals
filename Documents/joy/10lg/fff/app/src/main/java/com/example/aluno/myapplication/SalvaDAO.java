package com.example.aluno.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.aluno.myapplication.Modelo.PessoaSalvaMeta;

import java.util.ArrayList;

public class SalvaDAO {
    private static SQLiteDatabase bd;
    private static CriaBanco openHelper;

    public SalvaDAO(Context contexto){
        openHelper = new CriaBanco(contexto);
    }

    static void abrir(){
        bd = openHelper.getWritableDatabase();
    }

    private static void fechar (){
        bd.close();
    }

    public PessoaSalvaMeta salvar(String usuarioLogado, String usuarioCriador, String descrMeta, String efetiva){
        ContentValues valores = new ContentValues();
        valores.put("usuarioLogado",usuarioLogado );
        valores.put("usuarioCriador", usuarioCriador);
        valores.put("descrMeta",descrMeta);
        valores.put("efetiva",efetiva);

        bd.insert("pessoasalvameta", null, valores);
        PessoaSalvaMeta psm = new PessoaSalvaMeta();
        psm.setUsuarioLogado(usuarioLogado);
        psm.setUsuarioCriador(usuarioCriador);
        psm.setDescrMeta(descrMeta);
        psm.setEfetiva(efetiva);

        return psm;
    }

    public ArrayList<PessoaSalvaMeta> metasSalvas(String usuario){
        ArrayList<PessoaSalvaMeta> metasalvas = new ArrayList<PessoaSalvaMeta>();
        String[] campos =  {"usuarioCriador", "descrMeta"};
        String where = "usuarioLogado" + "= '" + usuario +"'";
        abrir();
        Cursor cursor = bd.rawQuery("Select descrMeta, usuarioCriador from pessoasalvameta where efetiva='N' AND usuarioLogado=?" , new String[]{""+usuario});
        while(cursor.moveToNext()){
            PessoaSalvaMeta m = new PessoaSalvaMeta();
            m.setUsuarioCriador(cursor.getString(cursor.getColumnIndex("usuarioCriador")));
            m.setDescrMeta(cursor.getString(cursor.getColumnIndex("descrMeta")));
            metasalvas.add(m);
        }

        cursor.close();
        fechar();
        return metasalvas;
    }

    public void atualizar(String descricao){
        ContentValues values = new ContentValues();
        String efe = "S";
        values.put("efetiva", efe);

        String[] selectionArgs =  {""+descricao};
        bd = openHelper.getReadableDatabase();
        bd.update("pessoasalvameta", values, "descrMeta=?", selectionArgs);
        fechar();
    }

    public ArrayList<String> top10Metas(){
        ArrayList<String> topmetas = new ArrayList<String>();
        abrir();
        Cursor cursor = bd.rawQuery("Select * from pessoasalvameta group by descrMeta having count(*)>1 limit 10" , new String[]{});
        while(cursor.moveToNext()){
            PessoaSalvaMeta m = new PessoaSalvaMeta();
            m.setUsuarioCriador(cursor.getString(cursor.getColumnIndex("usuarioCriador")));
            m.setDescrMeta(cursor.getString(cursor.getColumnIndex("descrMeta")));
            topmetas.add(m.toLista());
        }

        cursor.close();
        fechar();
        return topmetas;
    }
}
