package com.example.aluno.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.ImageView;
import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;

public class EditarPerfil extends AppCompatActivity {
    EditText edtNome;
    EditText edtBio;
    EditText edtAniv;
    EditText edtEmail, edtAtual, edtNova;
    ImageView foto;
    Button salvar;
    Button sen;
    PessoaDAO pDao;
    String nome,niver, bio, email,  senha;
    int REQUEST_CODE_GALLERY = 999;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editar_perfil);

        pDao = new PessoaDAO(getBaseContext());
        SharedPreferences sharedPreferences =  getBaseContext().getSharedPreferences("10lifegoals", Context.MODE_PRIVATE);
        final String usuario = sharedPreferences.getString("usuario", "Usário não encontrado");
        final String nom = sharedPreferences.getString("nome", "Nome não encontrado");
        final String mail = sharedPreferences.getString("email", "Email não encontrado");
        final String n = sharedPreferences.getString("niver", "Data de nascimento não encontrada");
        final String b = sharedPreferences.getString("bio", "Bio não encontrada");
        final String s = sharedPreferences.getString("senha", "Senha não encontrada");

        edtNome = (EditText) findViewById(R.id.edtNome);
        edtBio = (EditText) findViewById(R.id.edtBio);
        edtAniv = (EditText) findViewById(R.id.edtAniv);
        edtAtual = (EditText) findViewById(R.id.edtSenhaAtual);
        edtNova = (EditText) findViewById(R.id.edtNovaSenha);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        foto = (ImageView) findViewById(R.id.foto);
        salvar = (Button) findViewById(R.id.btnSalvarSobremim);
        sen = (Button) findViewById(R.id.btnSalvarSenha);

        foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(EditarPerfil.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_GALLERY);
            }
        });

        salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nome = edtNome.getText().toString();
                bio = edtBio.getText().toString();
                email = edtEmail.getText().toString();
                niver = edtAniv.getText().toString();

                pDao.atualizar(usuario, nome,niver,email,imageViewToByte(foto),bio);

                SharedPreferences sharedPreferences = getSharedPreferences("10lifegoals", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor;
                editor = sharedPreferences.edit(); //Escrever dentro do arquivo

                editor.putString("usuario", usuario);
                editor.putString("nome", nome);
                editor.putString("email", email);
                editor.putString("bio", bio);
                editor.putString("niver", niver);
                editor.commit();

                //telaCadM();
                Intent i = new Intent(EditarPerfil.this, MainActivity.class);
                startActivity(i);
            }
        });

        sen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                senha = edtNova.getText().toString();

                pDao.atualizarSenha(usuario, senha);

                SharedPreferences sharedPreferences = getSharedPreferences("10lifegoals", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor;
                editor = sharedPreferences.edit(); //Escrever dentro do arquivo

                editor.putString("usuario", usuario);
                editor.putString("senha", senha);
                editor.commit();

                //telaCadM();
                Intent i = new Intent(EditarPerfil.this, MainActivity.class);
                startActivity(i);
            }
        });

        edtNome.setText(nom);
        edtEmail.setText(mail);
        edtAtual.setText(s);
        if(!n.equals("Data de nascimento não encontrada")) {
            edtAniv.setText(n);}
        if(!b.equals("Bio não encontrada")) {
            edtBio.setText(b);
        }
    }

    public static byte[] imageViewToByte(ImageView imageView) {
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_CODE_GALLERY){
            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, REQUEST_CODE_GALLERY);
            }else{
                Toast.makeText(this, "Sem permissão pra acessar localização do arquivo", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK){
            Uri imageUri = data.getData();

            CropImage.activity(imageUri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1,1)
                    .start(this);
        }
        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if(resultCode == RESULT_OK){
                Uri resultUri = result.getUri();
                foto.setImageURI(resultUri);
            }else if(resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                Exception error = result.getError();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}

