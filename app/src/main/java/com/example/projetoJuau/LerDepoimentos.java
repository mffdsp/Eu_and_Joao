package com.example.projetoJuau;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tentativa.InfoClass;
import com.example.tentativa.R;
import com.example.tentativa.SendActivity;
import com.example.tentativa.TextStructure;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LerDepoimentos extends AppCompatActivity {

    EditText mEdit;
    EditText mEdit2;
    DatabaseReference DB;
    EditText mEdit3;
    TextView mEdit4;
    String id;
    String newSTRING;
    int newInt;
    boolean LIKED = false;

    Button btSmile;
    Button btCry;
    Button btLove;

    ArrayList<Integer> reactionList;
    ArrayList<Boolean> C1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        DB = FirebaseDatabase.getInstance().getReference("texts");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ler_depoimentos);

        getSupportActionBar().hide();

        mEdit = findViewById(R.id.titleInput3);
        mEdit2 = findViewById(R.id.sigInput3);
        mEdit3 = findViewById(R.id.textInput3);

        btSmile = findViewById(R.id.bt1);
        btCry = findViewById(R.id.bt2);
        btLove = findViewById(R.id.bt3);

        reactionList = new ArrayList<>();
        C1 = new ArrayList<>();
        C1.add(false);         C1.add(false);      C1.add(false);


        mEdit.setEnabled(false);
        mEdit2.setEnabled(false);
        mEdit3.setFocusableInTouchMode(false);
        mEdit3.clearFocus();

        try {
            int random = new Random().nextInt(InfoClass.LISTA.size());
            int i = 0;

            for (TextStructure TS : InfoClass.LISTA) {
                if (i == random) {

                    mEdit.setText(TS.getTitle());
                    mEdit2.setText(TS.SIGN);
                    mEdit3.setText(TS.getBody());
                    reactionList.add(TS.UPVOTE.get(0)); reactionList.add(TS.UPVOTE.get(1)); reactionList.add(TS.UPVOTE.get(2));
                    setText();
                    id = TS.getID();
                    InfoClass.TO = TS.EMAIL;

                    break;
                }
                i += 1;

            }
        }catch (Exception e){

            DB = FirebaseDatabase.getInstance().getReference("texts");
            Toast.makeText(LerDepoimentos.this, "Erro ao ler mensagens", Toast.LENGTH_LONG).show();
        }


    }

    public void sendMsg(View v){

        startActivity(new Intent(getBaseContext(), SendActivity.class));

    }
    public void setText(){

        btSmile.setText(Integer.toString(reactionList.get(0)));
        btCry.setText(Integer.toString(reactionList.get(1)));
        btLove.setText(Integer.toString(reactionList.get(2)));

    }

    public void  upCry(View v){
        upAction(1);

    }
    public void  upLove(View v){
        upAction(2);
    }
    public void  upSmile(View v){
       upAction(0);
    }

    public void upAction(Integer x){
        try {
            if (C1.get(x)) {
                reactionList.set(x, reactionList.get(x) - 1);
                DB.child(id).child("UPVOTE").setValue(reactionList);
                C1.set(x, false);

            } else {
                reactionList.set(x, reactionList.get(x) + 1);
                DB.child(id).child("UPVOTE").setValue(reactionList);
                C1.set(x, true);
            }

            setText();
        }catch (Exception e){
            Toast.makeText(LerDepoimentos.this, e.toString(), Toast.LENGTH_LONG).show();
        }
    }
}
