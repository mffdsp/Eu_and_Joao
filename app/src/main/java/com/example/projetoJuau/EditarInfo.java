package com.example.projetoJuau;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.text.IDNA;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tentativa.InfoClass;
import com.example.tentativa.LoginActivity;
import com.example.tentativa.MainActivity;
import com.example.tentativa.R;
import com.example.tentativa.TextStructure;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;

public class EditarInfo extends AppCompatActivity {

    EditText et1;
    EditText et2;
    EditText et3;
    EditText et4;
    DatabaseReference DB;
    boolean EDIT = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_info);
        getSupportActionBar().hide();
        DB = FirebaseDatabase.getInstance().getReference("users");


        et1 = findViewById(R.id.sigInput3);
        et2 = findViewById(R.id.sigInput4);
        et3 = findViewById(R.id.sigInput5);
        et4 = findViewById(R.id.sigInput6);
        et2.setEnabled(false);

//        Buscar info por ID
//        try {
//            for (User user : InfoClass.USERS) {
//                    if(user.id.equals(InfoClass.getAccountId())){
//
//                    }
//
//            }
//        }catch (Exception e){
//
//
//            Toast.makeText(EditarInfo.this, "Erro ao ler dados!", Toast.LENGTH_LONG).show();
//        }


    }

    public void updateButton(View v){
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Deseja confirmar as alterações?")
                .setMessage("")
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        update();
                    }

                })
                .setNegativeButton("Não", null)
                .show();
    }

    public void update(){

        try {
            DB = FirebaseDatabase.getInstance().getReference("users");

            String id = InfoClass.getAccountId();

            InfoClass.setAccountName(et1.getText().toString());
            InfoClass.setAccountEmail(et2.getText().toString());
            InfoClass.IDADE = et4.getText().toString();
            InfoClass.CONTATO = et3.getText().toString();

            User TS = new User(InfoClass.getAccountName(), InfoClass.getAccountEmail(),  et4.getText().toString(), id, "sou legal demais", et3.getText().toString(), "2020");

            DB.child(id).setValue(TS);

            Toast.makeText(EditarInfo.this, "Alterado com sucesso!", Toast.LENGTH_LONG).show();
            InfoClass.SAVED = true;
            startActivity(new Intent(getBaseContext(), MainActivity.class));
            finish();

        }catch (Exception e){
            Toast.makeText(EditarInfo.this, "Erro de conexão!", Toast.LENGTH_LONG).show();

        }



    }


    @Override
    protected void onStart(){
        super.onStart();

        DB.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                DB = FirebaseDatabase.getInstance().getReference("users");
                InfoClass.USERS = new ArrayList<>();

                for (DataSnapshot postSnapshot: snapshot.getChildren()) {

                    User TS = postSnapshot.getValue(User.class);
                    InfoClass.USERS.add(TS);

                    if(TS.id.equals(InfoClass.getAccountId()) && EDIT){
                        et1.setText(TS.nome);
                        et2.setText(TS.email);
                        et3.setText(TS.contato);
                        et4.setText(TS.idade);
                        EDIT = false;
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
