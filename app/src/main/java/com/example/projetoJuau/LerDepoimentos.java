package com.example.projetoJuau;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ler_depoimentos);

        getSupportActionBar().hide();

        mEdit = findViewById(R.id.titleInput3);
        mEdit2 = findViewById(R.id.sigInput3);
        mEdit3 = findViewById(R.id.textInput3);
        mEdit4 = findViewById(R.id.upInput);

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
                    mEdit4.setText(Integer.toString(TS.UPVOTE.get(0)) + " - Votos positivos");
                    newInt = TS.UPVOTE.get(0);
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
    public void upButton(View v){

            try {
                DB = FirebaseDatabase.getInstance().getReference("texts");
                int value = newInt + 1;

                List<Integer> listUPVOTE = new ArrayList<>();
                listUPVOTE.add(value);
                listUPVOTE.add(value);
                listUPVOTE.add(3);

                if (LIKED) {
                    listUPVOTE.set(0, value -1);
                    DB.child(id).child("UPVOTE").setValue(listUPVOTE);
                    mEdit4.setText((value - 1) + " - Votos positivos");
                    LIKED = false;
                    return;
                }

                DB.child(id).child("UPVOTE").setValue(listUPVOTE);

                mEdit4.setText(value + " - Votos positivos");

                LIKED = true;
            }catch (Exception e){
                Toast.makeText(LerDepoimentos.this, "Erro de conexão", Toast.LENGTH_LONG).show();

            }


    }

    public void sendMsg(View v){

        startActivity(new Intent(getBaseContext(), SendActivity.class));

    }
    public void setValue(){


    }
}
