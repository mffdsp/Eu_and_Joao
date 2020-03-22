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
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity<mFirebaseRef> extends AppCompatActivity {

    String FINAL_NAME = "oo";
    DatabaseReference DB;
    DatabaseReference DBAN;
    DatabaseReference SIZE;

    DatabaseReference DR;
    List<Pessoa> listaDePessoas;
    ProgressBar PB;

    Button bt1; //8
    Button bt2; //6
    Button bt3; //16
    Button bt4; //15
    TextView TV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //justTORELOAD

        DB = FirebaseDatabase.getInstance().getReference("texts");
        DBAN = FirebaseDatabase.getInstance().getReference("bannedUsers");
        SIZE = FirebaseDatabase.getInstance().getReference("size");


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_nav);

        bt1 = findViewById(R.id.button8);
        bt2 = findViewById(R.id.button6);
        bt3 = findViewById(R.id.bt3);
        bt4 = findViewById(R.id.button15);

        ENABLEALL();

        PB = (ProgressBar) findViewById(R.id.progressBar4);
        PB.setVisibility(View.INVISIBLE);

       //onde();

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
        TV = findViewById(R.id.boasV);
        TV.setText("Olá, " + InfoClass.getAccountName() + "!\nJá são " + InfoClass.size +" histórias contadas.\nAdicione a sua :)");

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
        if(InfoClass.BAN){
            logOut(null);
        }
        ENABLEALL();
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

        DBAN.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try{
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {

                    GenericTypeIndicator<List<String>> t = new GenericTypeIndicator<List<String>>() {};

                    List<String> ban = postSnapshot.getValue(t);

                    for(String b : ban){

                        if(b.equals(InfoClass.ACCOUNT_EMAIL)) {
                            InfoClass.BAN = true;
                        }

                    }
                }}catch (Exception e){
                    Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        SIZE.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                    TV = findViewById(R.id.boasV);

                    InfoClass.size = snapshot.child("atual").getValue(Integer.class);

                TV.setText("Olá, " + InfoClass.getAccountName() + "!\nJá são " + InfoClass.size +" histórias contadas.\nAdicione a sua :)");

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
            InfoClass.size += 1;
            SIZE.child("atual").setValue(InfoClass.size);

            Toast.makeText(MainActivity.this, "Sua história foi submetido com sucesso ao banco de dados :)", Toast.LENGTH_LONG).show();

            InfoClass.title = "";
            InfoClass.boby = "";

        }catch (Exception e){
            Toast.makeText(MainActivity.this, "Erro ao enviar o texto, verifique sua conexão!", Toast.LENGTH_LONG).show();

        }


    }

    public void toMsg(View v){

        DISABLEEALL();
        startActivity(new Intent(getBaseContext(), ChatMaybe.class));

    }
//    public void onde(){
//        DB = FirebaseDatabase.getInstance().getReference("msgBox");
//
//        String id = DB.push().getKey();
//
//        MsgBox TS = new MsgBox("mffdsplol@gmail.com", "joao@gmail.com", "FOIMASSASAASAS");
//
//        DB.child(id).setValue(TS);
//
//    }
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

        if(InfoClass.BAN){
            InfoClass.LOG_OUT = true;
            startActivity(new Intent(getBaseContext(), LoginActivity.class));
            finish();
            return;
        }
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
        DISABLEEALL();
        startActivity(new Intent(getBaseContext(), EnviarDepoimento.class));
    }

    public void lerDepoimento(View v){

        DISABLEEALL();

        DB.child("00").setValue(null);
        PB.setVisibility(View.VISIBLE);
        Toast.makeText(this, "Buscando mensagem aleatória...", Toast.LENGTH_LONG).show();


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getBaseContext(), LerDepoimentos.class));
                PB.setVisibility(View.INVISIBLE);

            }
        }, 1500);


    }

    public void config(View v){

        DISABLEEALL();

        PB.setVisibility(View.VISIBLE);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getBaseContext(), EditarInfo.class));
                PB.setVisibility(View.INVISIBLE);

            }
        }, 300);
    }

    public void ENABLEALL(){
        bt1.setClickable(true);
        bt2.setClickable(true);
        bt3.setClickable(true);
        bt4.setClickable(true);

    }



    public void DISABLEEALL(){
        bt1.setClickable(false);
        bt2.setClickable(false);
        bt3.setClickable(false);
        bt4.setClickable(false);
    }

}

