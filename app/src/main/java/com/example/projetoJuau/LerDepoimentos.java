package com.example.projetoJuau;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tentativa.InfoClass;
import com.example.tentativa.MainActivity;
import com.example.tentativa.R;
import com.example.tentativa.TextStructure;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

public class LerDepoimentos extends AppCompatActivity {

    EditText mEdit;
    EditText mEdit2;
    DatabaseReference DB;
    EditText mEdit3;
    TextView mEdit4;
    String id;
    boolean LIKED = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ler_depoimentos);

        getSupportActionBar().hide();

        mEdit = findViewById(R.id.titleInput);
        mEdit2 = findViewById(R.id.sigInput);
        mEdit3 = findViewById(R.id.textInput);
        mEdit4 = findViewById(R.id.upInput);

        mEdit.setEnabled(false);
        mEdit2.setEnabled(false);
        mEdit3.setEnabled(false);

        try {
            int random = new Random().nextInt(InfoClass.LISTA.size());
            int i = 0;

            for (TextStructure TS : InfoClass.LISTA) {
                if (i == random) {
                    mEdit.setText(TS.getTitle());
                    mEdit2.setText(TS.SIGN);
                    mEdit3.setText(TS.getBody());
                    mEdit4.setText(Integer.toString(TS.UPVOTE));
                    id = TS.getID();
                    break;
                }
                i += 1;

            }
        }catch (Exception e){

            DB = FirebaseDatabase.getInstance().getReference("texts");
            DB.child("AGR").setValue("heh");
            Toast.makeText(LerDepoimentos.this, "Erro ao ler mensagens", Toast.LENGTH_LONG).show();
        }


    }
    public void upButton(View v){


        try{
            DB = FirebaseDatabase.getInstance().getReference("texts");
            int value = Integer.parseInt(mEdit4.getText().toString()) + 1;
            if(LIKED){
                DB.child(id).child("UPVOTE").setValue(value - 2);
                mEdit4.setText(Integer.toString(value - 2));
                LIKED = false;
                return;
            }
            DB.child(id).child("UPVOTE").setValue(value);
            mEdit4.setText(Integer.toString(value));
            LIKED = true;
        }catch (Exception e){
            Toast.makeText(LerDepoimentos.this, "Erro, verifique conexão!", Toast.LENGTH_LONG).show();

        }

    }
    public void setValue(){

    }
}
