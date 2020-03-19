    package com.example.projetoJuau;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.tentativa.InfoClass;
import com.example.tentativa.R;

    public class BarNav extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_nav);

        getSupportActionBar().hide();

        TextView TV = findViewById(R.id.boasV);
        TV.setText("Olá " + InfoClass.getAccountName() +"\n seja bem vindo!");
    }
}
