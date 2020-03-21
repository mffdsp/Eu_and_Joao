package com.example.tentativa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.text.IDNA;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.projetoJuau.MsgBox;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SendActivity extends AppCompatActivity {

    EditText ET;
    DatabaseReference DB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);
        ET = findViewById(R.id.editText);
        DB = FirebaseDatabase.getInstance().getReference("msgBox");

        getSupportActionBar().setTitle("Enviar Mensagem á Autora");

    }

    public void send(View v){

        if(ET.getText().toString().equals("")){
            Toast.makeText(SendActivity.this, "O corpo da mensagem está vazio", Toast.LENGTH_LONG).show();
            return;
        }

        DB = FirebaseDatabase.getInstance().getReference("msgBox");

        String id = DB.push().getKey();

        MsgBox TS = new MsgBox(InfoClass.ACCOUNT_EMAIL, InfoClass.TO, ET.getText().toString(), id);

        DB.child(id).setValue(TS);

        Toast.makeText(SendActivity.this, "Mensagem enviada com Sucesso!", Toast.LENGTH_LONG).show();
        finish();

    }
}
