package com.example.projetoJuau;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.tentativa.InfoClass;
import com.example.tentativa.MainActivity;
import com.example.tentativa.R;
import com.google.firebase.database.DatabaseReference;

public class EnviarDepoimento extends AppCompatActivity {

    DatabaseReference DB;
    EditText et1;
    EditText et2;
    EditText et3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enviar_depoimento);

        et1 = ((EditText)findViewById(R.id.sigInput3));
        et2 = ((EditText)findViewById(R.id.textInput3));
        et3 = ((EditText)findViewById(R.id.titleInput3));

        et1.setText(InfoClass.signature);
        et2.setText(InfoClass.boby);
        et3.setText(InfoClass.title);

        getSupportActionBar().hide();

    }

    public void sendText(View v){
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Deseja confirmar o envio?")
                .setMessage("")
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        InfoClass.signature = ((EditText)findViewById(R.id.sigInput3)).getText().toString();
                        InfoClass.boby = ((EditText)findViewById(R.id.textInput3)).getText().toString();
                        InfoClass.title = ((EditText)findViewById(R.id.titleInput3)).getText().toString();
                        InfoClass.SEND = true;
                        startActivity(new Intent(getBaseContext(), MainActivity.class));
                        finish();
                    }

                })
                .setNegativeButton("Não", null)
                .show();
    }
}
