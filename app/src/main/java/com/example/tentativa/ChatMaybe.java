package com.example.tentativa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projetoJuau.MsgBox;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChatMaybe extends AppCompatActivity {

    DatabaseReference DB;
    List<MsgBox> chatString;
    ListView CHAT;
    EditText textBox;
    EditText TV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        DB = FirebaseDatabase.getInstance().getReference("msgBox");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get);
        getSupportActionBar().setTitle("Caixa de Mensagens");

        TV = findViewById(R.id.sigInput3);

        chatString = new ArrayList<>();
        CHAT = findViewById(R.id.CHATO);

        TV.setText("");
        TV.setFocusableInTouchMode(false);
        TV.clearFocus();

        //saveToDB();

    }


    @Override
    protected void onStart(){
        super.onStart();

        DB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                chatString.clear();
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {

                    MsgBox TS = postSnapshot.getValue(MsgBox.class);
                    if(TS.PARA.equals(InfoClass.ACCOUNT_EMAIL)){
                        chatString.add(TS);
                    }

                }

                if(chatString.size() == 0){
                    TV.setText("Caixa de Mensagens Vazia :(");
                }
                ArrayAdapter<MsgBox> adapter = new ArrayAdapter<MsgBox>(ChatMaybe.this, android.R.layout.simple_list_item_1, chatString);
                CHAT.setAdapter(adapter);

                CHAT.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        String buscaID =  ((MsgBox)CHAT.getItemAtPosition(position)).ID;

                        for(MsgBox TS: chatString){
                            if(TS.ID.equals(buscaID)){
                                TV.setText(TS.TEXTO);
                            }
                        }
                    }
                });


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


}
