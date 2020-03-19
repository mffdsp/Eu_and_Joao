package com.example.tentativa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projetoJuau.BarNav;
import com.example.projetoJuau.EnviarDepoimento;
import com.example.projetoJuau.LerDepoimentos;
import com.example.projetoJuau.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
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
        DB = FirebaseDatabase.getInstance().getReference("count");
        DB.child("AGR").setValue("heh");


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PB = (ProgressBar) findViewById(R.id.opa);
        PB.setVisibility(View.INVISIBLE);


        DB = FirebaseDatabase.getInstance().getReference("texts");

        Toast.makeText(MainActivity.this, "Bem vindo(a), " + InfoClass.getAccountName(), Toast.LENGTH_LONG).show();

        InfoClass.LISTA = new ArrayList<>();

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);

        if(InfoClass.SEND){

            saveText();
            Toast.makeText(MainActivity.this, "Não é possível enviar textos com titulos repetidos!", Toast.LENGTH_LONG).show();

            InfoClass.SEND = false;
        }
        ((EditText) findViewById(R.id.editText3)).setText(InfoClass.getAccountName());
        ((EditText) findViewById(R.id.datatext)).setText(InfoClass.getAccountEmail());
        getSupportActionBar().hide();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    public void chooseWhatToDo(View v){
        startActivity(new Intent(getBaseContext(), OptionScreen.class));

    }
    public void saveToDB(){

        String id = DB.push().getKey();

//        //TextStructure TS = new TextStructure(InfoClass.title, InfoClass.boby, id);
//        TS.EMAIL = InfoClass.getAccountEmail();
//
//        DB.child(id).setValue(TS);
//
//        Toast.makeText(this, "SALVANDINHO", Toast.LENGTH_LONG).show();

    }

    @Override
    protected void onStart(){
        super.onStart();

        DB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                InfoClass.LISTA = new ArrayList<>();
                InfoClass.USERS = new ArrayList<>();

                for (DataSnapshot postSnapshot: snapshot.getChildren()) {

                    TextStructure TS = postSnapshot.getValue(TextStructure.class);

                    if(!InfoClass.USERS.contains(TS.EMAIL)) {
                        InfoClass.USERS.add(TS.EMAIL);
                    }

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
        DB = FirebaseDatabase.getInstance().getReference("texts");

        String id = DB.push().getKey();;

        TextStructure TS = new TextStructure(InfoClass.title, InfoClass.boby, InfoClass.ACCOUNT_ID, InfoClass.signature);

        DB.child(id).setValue(TS);

        Toast.makeText(this, "SALVANDINHO", Toast.LENGTH_LONG).show();
    }


    public void saveUser(){
        String id = InfoClass.ACCOUNT_ID;

        User TS = new User(InfoClass.ACCOUNT_NAME, InfoClass.getAccountEmail(), "12", InfoClass.ACCOUNT_ID, "sou legal demais");

        DB.child(id).setValue(TS);

        Toast.makeText(this, "SALVANDINHO", Toast.LENGTH_LONG).show();


    }

    public void logOut(View v){
        InfoClass.LOG_OUT = true;
        startActivity(new Intent(getBaseContext(), LoginActivity.class));
        finish();
    }

    public void enviarDepoimento(View v){
        startActivity(new Intent(getBaseContext(), EnviarDepoimento.class));
    }

    public void lerDepoimento(View v){

        DB = FirebaseDatabase.getInstance().getReference("count");
        DB.child("AGR").setValue("heh");
        PB.setVisibility(View.VISIBLE);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getBaseContext(), LerDepoimentos.class));
            }
        }, 2000);


    }


}

