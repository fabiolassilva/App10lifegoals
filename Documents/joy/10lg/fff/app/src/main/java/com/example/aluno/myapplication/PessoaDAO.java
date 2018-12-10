package com.example.aluno.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.aluno.myapplication.Modelo.Pessoa;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

public class PessoaDAO {
    private static SQLiteDatabase bd;
    private static CriaBanco openHelper;

    public PessoaDAO(Context contexto){
        openHelper = new CriaBanco(contexto);
    }

    static void abrir(){
        bd = openHelper.getWritableDatabase();
    }

    private static void fechar (){
        bd.close();
    }

    public Pessoa cadastrarPessoa(String usuario, String nome, String senha, String email, String dataNasc, String termos, byte[] image, String bio){
        ContentValues valores = new ContentValues();
        valores.put("usuario",usuario );
        valores.put("nome", nome);
        valores.put("senha",senha );
        valores.put("email", email);
        valores.put("dataNasc", dataNasc);
        valores.put("termos", termos);
        valores.put("bio", bio);
        valores.put("image", image);
        abrir();
        bd.insert("pessoa", null, valores);

        Pessoa pessoa = new Pessoa();
        pessoa.setUsuario(usuario);
        pessoa.setNome(nome);
        pessoa.setSenha(senha);
        pessoa.setEmail(email);
        pessoa.setDataNasc(dataNasc);
        pessoa.setTermos(termos);
        if(image!=null) {
            byte[] imageByteArray = image;
            ByteArrayInputStream imageStream = new ByteArrayInputStream(imageByteArray);
            Bitmap theImage = BitmapFactory.decodeStream(imageStream);
            pessoa.setImage(theImage);
        }
        pessoa.setBio(bio);

        return pessoa;
    }

    public static List<Pessoa> lerPessoa(){
        List<Pessoa>pessoas = new ArrayList<>();
        Cursor cursor = bd.query("pessoa", null, null, null, null,null, null);
        while(cursor.moveToNext()){
            Pessoa i = new Pessoa();
            i.setUsuario(cursor.getString(0));
            i.setNome(cursor.getString(1));
            i.setSenha(cursor.getString(2));
            i.setEmail(cursor.getString(3));
            i.setDataNasc(cursor.getString(4));
            i.setTermos(cursor.getString(5));
            i.setBio(cursor.getString(6));
            //i.setImage(cursor.getBlob(7));
            if(cursor.getBlob(7)!=null) {
                byte[] imageByteArray = cursor.getBlob(7);
                ByteArrayInputStream imageStream = new ByteArrayInputStream(imageByteArray);
                Bitmap theImage = BitmapFactory.decodeStream(imageStream);
                i.setImage(theImage);
            }
            pessoas.add(i);

        }
        cursor.close();
        return pessoas;
    }

    public Pessoa buscarPessoa(String usuario) {
        System.out.println("buscarPOessoa");
        Pessoa i = new Pessoa();
        String[] campos = {"usuario", "nome", "senha", "email", "dataNasc", "termos", "bio", "image"};
        //String where = "usuario" + "= '" + usuario +"'";
        //String[] selectionArgs = {"" + usuario};
        bd = openHelper.getReadableDatabase();
        Cursor cursor;
        cursor = bd.rawQuery("Select* from pessoa where usuario=?", new String[]{""+usuario});
        while (cursor.moveToNext()) {
            i = new Pessoa();
            i.setUsuario(cursor.getString(cursor.getColumnIndex("usuario")));
            i.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            i.setSenha(cursor.getString(cursor.getColumnIndex("senha")));
            i.setEmail(cursor.getString(cursor.getColumnIndex("email")));
            i.setDataNasc(cursor.getString(cursor.getColumnIndex("dataNasc")));
            i.setTermos(cursor.getString(cursor.getColumnIndex("termos")));
            i.setBio(cursor.getString(cursor.getColumnIndex("bio")));
            //i.setImage(cursor.getBlob(7));
            if (cursor.getBlob(cursor.getColumnIndex("image")) != null) {
                byte[] imageByteArray = cursor.getBlob(cursor.getColumnIndex("image"));
                ByteArrayInputStream imageStream = new ByteArrayInputStream(imageByteArray);
                Bitmap theImage = BitmapFactory.decodeStream(imageStream);
                i.setImage(theImage);
            }
        }
        cursor.close();
        fechar();
        return i;
    }

    public boolean carregaLogin(String usuario, String senha){
        /*Cursor cursor;
        String[] campos =  {"usuario", "senha"};
        String where = "usuario" + "= '" + usuario  +"' and "+ "senha" + "='" +senha + "'";
        bd = openHelper.getReadableDatabase();
        cursor = bd.query("pessoa" ,campos,where, null, null, null, null, null);



        if(cursor!=null){
            cursor.moveToFirst();
            Pessoa pessoa = new Pessoa();
            pessoa.setUsuario(cursor.getString(0));
            pessoa.setNome(cursor.getString(1));
            pessoa.setSenha(cursor.getString(2));
            pessoa.setEmail(cursor.getString(3));
            pessoa.setDataNasc(cursor.getString(4));
            pessoa.setTermos(cursor.getString(5));

            bd.close();
            return pessoa;
        }else{
            bd.close();
            return null;
        }*/
        bd = openHelper.getReadableDatabase();
        Cursor cursor = bd.rawQuery("Select * from pessoa where usuario=? and senha=?", new String[]{usuario,senha});
        if(cursor.getCount()>0) return true;
        else return false;
    }

    public void atualizar(String user, String nome, String niver,String email,byte[] image, String bio){
        ContentValues values = new ContentValues();
        values.put("usuario", user);
        values.put("nome", nome);
        values.put("dataNasc", niver);
        values.put("email", email);
        values.put("bio", bio);
        values.put("image", image);

        String[] selectionArgs =  {""+user};
       // String where = "usuario" + "= '" + user +"'";
        bd = openHelper.getReadableDatabase();
        int count = bd.update("pessoa", values, "usuario=?", selectionArgs);
        fechar();
    }

    public void atualizarSenha(String user, String senha){
        ContentValues values = new ContentValues();
        values.put("usuario", user);
        values.put("senha", senha);

        String[] campos =  {"nome", "email","bio","image"};
        String where = "usuario" + "= '" + user +"'";
        bd = openHelper.getReadableDatabase();

        int count = bd.update("pessoa", values, where, null);
        fechar();

    }

    public ArrayList<String> pesquisar(String usuario, String user){
        ArrayList<String> p = new ArrayList<String>();
        bd = openHelper.getReadableDatabase();
        System.out.println("estou no pesquisar");
        //Cursor cursor = bd.rawQuery("Select usuario, nome from pessoa where usuario like ?", new String[]{usuario});
        String sql = "Select usuario from pessoa where nome like '%"+usuario+"%' and usuario!='"+user+"'";
        //
        //Cursor cursor = bd.rawQuery("Select descricao, nomeUser from meta where publica='sim' AND nomeUser!=? ORDER BY RANDOM() LIMIT 20" , new String[]{""+usuario});
        Cursor cursor = bd.rawQuery(sql, null);
        //cursor.moveToFirst();
        while(cursor.moveToNext()){
            System.out.println("estou no if");
            /*Pessoa pessoa = new Pessoa();
            pessoa.setNome(cursor.getString(cursor.getColumnIndex("usuario")));*/
            p.add(cursor.getString(cursor.getColumnIndex("usuario")));
        }
        cursor.close();
        bd.close();

        return p;

    }

    public String procuraNome(String usuario){
        String nome = "teste";
        abrir();
        Cursor cursor = bd.rawQuery("Select nome from pessoa where usuario=?", new String[]{"" + usuario,});
        while (cursor.moveToNext()) {
            nome = cursor.getString(cursor.getColumnIndex("nome"));
        }

        cursor.close();
        fechar();
        return nome;
    }

    public ArrayList<String> procuraUsuario(){
        ArrayList<String> usuario = new ArrayList<String>();
        abrir();
        Cursor cursor = bd.rawQuery("Select usuario from pessoa", new String[]{});
        while (cursor.moveToNext()) {
            usuario.add(cursor.getString(cursor.getColumnIndex("usuario")));
        }

        cursor.close();
        fechar();
        return usuario;
    }


    public String procuraEmail(String usuario){
        String email = "email teste";
        abrir();
        Cursor cursor = bd.rawQuery("Select email from pessoa where usuario=?", new String[]{"" + usuario,});
        while (cursor.moveToNext()) {
            email = cursor.getString(cursor.getColumnIndex("email"));
        }

        cursor.close();
        fechar();
        return email;
        }
}

