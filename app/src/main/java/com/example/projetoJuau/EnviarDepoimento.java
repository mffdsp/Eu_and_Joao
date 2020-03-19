package com.example.projetoJuau;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.text.IDNA;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.tentativa.InfoClass;
import com.example.tentativa.MainActivity;
import com.example.tentativa.R;

public class EnviarDepoimento extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enviar_depoimento);
        getSupportActionBar().hide();
    }

    public void sendText(View v){
        InfoClass.signature = ((EditText)findViewById(R.id.sigInput)).getText().toString();
                InfoClass.boby = ((EditText)findViewById(R.id.textInput)).getText().toString();
                        InfoClass.title = ((EditText)findViewById(R.id.titleInput)).getText().toString();

                        InfoClass.SEND = true;
                        startActivity(new Intent(getBaseContext(), MainActivity.class));
                        finish();
    }
}
