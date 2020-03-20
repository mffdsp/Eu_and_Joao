package com.example.tentativa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projetoJuau.BarNav;
import com.example.projetoJuau.EditarInfo;
import com.example.projetoJuau.EnviarDepoimento;
import com.example.projetoJuau.LerDepoimentos;
import com.example.projetoJuau.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.icu.text.IDNA;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity<mFirebaseRef> extends AppCompatActivity {

    String FINAL_NAME = "oo";
    DatabaseReference DB;
    DatabaseReference DR;
    List<Pessoa> listaDePessoas;
    ProgressBar PB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //justTORELOAD
        DB = FirebaseDatabase.getInstance().getReference("texts");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_nav);
          PB = (ProgressBar) findViewById(R.id.progressBar4);
          PB.setVisibility(View.INVISIBLE);

        try {
            saveUser();
        }catch (Exception e){
            Toast.makeText(MainActivity.this, "Erro de Conexão", Toast.LENGTH_LONG).show();

        }
        DB = FirebaseDatabase.getInstance().getReference("texts");

        Toast.makeText(MainActivity.this, "Bem vindo(a), " + InfoClass.getAccountName(), Toast.LENGTH_LONG).show();

        InfoClass.LISTA = new ArrayList<>();

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);

        if(InfoClass.SEND){

            saveText();
            InfoClass.SEND = false;
        }
       // ((EditText) findViewById(R.id.editText3)).setText(InfoClass.getAccountName());
      //  ((EditText) findViewById(R.id.datatext)).setText(InfoClass.getAccountEmail());
        getSupportActionBar().hide();
        TextView TV = findViewById(R.id.boasV);
        TV.setText("Olá " + InfoClass.getAccountName() +"\nseja bem vindo!");
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    public void chooseWhatToDo(View v){
        startActivity(new Intent(getBaseContext(), OptionScreen.class));

    }
//    public void saveToDB(){
//
//        String id = DB.push().getKey();
//
//        TextStructure TS = new TextStructure(InfoClass.title, InfoClass.boby, id);
//        TS.EMAIL = InfoClass.getAccountEmail();
//
//        DB.child(id).setValue(TS);
//
//        Toast.makeText(this, "SALVANDINHO", Toast.LENGTH_LONG).show();
//
//    }

    @Override
    protected void onStart(){
        super.onStart();

        DB.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                DB = FirebaseDatabase.getInstance().getReference("texts");
                InfoClass.LISTA = new ArrayList<>();

                for (DataSnapshot postSnapshot: snapshot.getChildren()) {

                        TextStructure TS = postSnapshot.getValue(TextStructure.class);
                        InfoClass.LISTA.add(TS);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void goToLogin(View v){
        startActivity(new Intent(getBaseContext(), LoginActivity.class));
        finish();
    }

    public void bt1(View v){
        startActivity(new Intent(getBaseContext(), BarNav.class));
        finish();
    }

    public void testando(View v){
        saveUser();
    }

    public void saveText(){
        try {
            DB = FirebaseDatabase.getInstance().getReference("texts");

            String id = DB.push().getKey();

            TextStructure TS = new TextStructure(InfoClass.title, InfoClass.boby, id, InfoClass.signature);

            DB.child(id).setValue(TS);

            Toast.makeText(MainActivity.this, "Sua história foi submetido com sucesso ao banco de dados :)", Toast.LENGTH_LONG).show();
            InfoClass.title = "";
            InfoClass.boby = "";

        }catch (Exception e){
            Toast.makeText(MainActivity.this, "Erro ao enviar o texto, verifique sua conexão!", Toast.LENGTH_LONG).show();

        }


    }


    public void saveUser(){

        if(InfoClass.SAVED) {
            DB = FirebaseDatabase.getInstance().getReference("users");

            String id = InfoClass.ACCOUNT_ID;

            User TS = new User(InfoClass.ACCOUNT_NAME, InfoClass.getAccountEmail(), InfoClass.IDADE, InfoClass.ACCOUNT_ID, "sou legal demais", InfoClass.CONTATO, "2020");

            DB.child(id).setValue(TS);

            Toast.makeText(this, "SALVANDINHO", Toast.LENGTH_LONG).show();

        }


    }

    public void logOut(View v){

        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Deseja realmente sair?")
                .setMessage("")
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        InfoClass.LOG_OUT = true;
                        startActivity(new Intent(getBaseContext(), LoginActivity.class));
                        finish();

                    }

                })
                .setNegativeButton("Não", null)
                .show();

    }

    public void enviarDepoimento(View v){
        startActivity(new Intent(getBaseContext(), EnviarDepoimento.class));
    }

    public void lerDepoimento(View v){

        DB.child("00").setValue(null);
        PB.setVisibility(View.VISIBLE);
        Toast.makeText(this, "Buscando mensagem aleatória...", Toast.LENGTH_LONG).show();


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getBaseContext(), LerDepoimentos.class));
                PB.setVisibility(View.INVISIBLE);

            }
        }, 3000);


    }

    public void config(View v){

        PB.setVisibility(View.VISIBLE);
        Toast.makeText(this, "Aguarde...", Toast.LENGTH_LONG).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getBaseContext(), EditarInfo.class));
                PB.setVisibility(View.INVISIBLE);

            }
        }, 2000);
    }


}

