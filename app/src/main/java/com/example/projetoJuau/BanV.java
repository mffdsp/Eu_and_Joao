package com.example.projetoJuau;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tentativa.InfoClass;
import com.example.tentativa.TextStructure;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BanV extends AppCompatActivity {

    DatabaseReference DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    public void verifyBAN(){
        DB = FirebaseDatabase.getInstance().getReference("bannedUsers");

    }

    @Override
    protected void onStart(){
        super.onStart();

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

    }
}
