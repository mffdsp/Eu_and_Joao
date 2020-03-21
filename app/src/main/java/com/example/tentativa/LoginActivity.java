package com.example.tentativa;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.Image;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projetoJuau.User;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.net.InetAddress;
import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    boolean INTERNET = false;
    //Criar lista com "Notas"
    //Criar objeto Notas, upar pro FireBase;
    //Dar o get nesse objeto, igual fiz com "pessoa"
    //Manipular titulo e corpo da Nota, ao clicar em alguem da lista, retorna O titulo e o corpo

    private SignInButton SB;
    private Button SOB;
    private GoogleSignInClient GSC;
    private FirebaseAuth FBA;
    private int RC_SIGN_IN = 1;
    private ImageView profileImage;
    private ProgressBar progressBar;
    private int LOGIN_DELAY = 2000;
    DatabaseReference DB;
    TextView TV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        DB = FirebaseDatabase.getInstance().getReference("users");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        TV = findViewById(R.id.textView4);
        //Mudar cor
        getWindow().getDecorView().setBackgroundColor(Color.rgb(255,70, 0));
        getSupportActionBar().hide();

        SB = findViewById(R.id.gbutton);
        //SOB = findViewById(R.id.signOutButton);
        FBA = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar3);
        progressBar.setVisibility(View.INVISIBLE);

        GoogleSignInOptions GSO = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        GSC = GoogleSignIn.getClient(this, GSO);

        logOutAction();

        SB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signIntent = GSC.getSignInIntent();
                startActivityForResult(signIntent, RC_SIGN_IN);
            }
        });

        if(GoogleSignIn.getLastSignedInAccount(getApplicationContext()) != null){
            LOGIN_DELAY = 0;
            //FirebaseGoogleAuth(GoogleSignIn.getLastSignedInAccount(getApplicationContext())); //Para verificar ban, mas demora
            updateUI(FBA.getCurrentUser());
        }

        ProgressBar spinner = findViewById(R.id.progressBar3);

        spinner.getIndeterminateDrawable().setColorFilter(Color.rgb(248,4,156), android.graphics.PorterDuff.Mode.MULTIPLY);
    }

    public void logOutAction(){
        if(InfoClass.LOG_OUT) {
            GSC.signOut();
            InfoClass.LOG_OUT = false;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleResult(task);
        }
    }

    public void handleResult(Task<GoogleSignInAccount> task){
        try{
            GoogleSignInAccount acc = task.getResult(ApiException.class);
            //Toast.makeText(LoginActivity.this, "Sucesso", Toast.LENGTH_LONG).show();
            FirebaseGoogleAuth(acc);
        }catch (ApiException e){
            Toast.makeText(LoginActivity.this, "Verifique a conexão", Toast.LENGTH_LONG).show();
            FirebaseGoogleAuth(null );

        }
    }

    private void FirebaseGoogleAuth(GoogleSignInAccount acc){
        if(acc == null){
            return;
        }
        AuthCredential AC = GoogleAuthProvider.getCredential(acc.getIdToken(), null);
        FBA.signInWithCredential(AC).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                   // Toast.makeText(LoginActivity.this, "", Toast.LENGTH_LONG).show();
                    FirebaseUser user = FBA.getCurrentUser();
                    updateUI(user);
                }else{
                    Toast.makeText(LoginActivity.this, "A conta atual encontra-se desativada :(\nEntre em contato com mffsp@ic.ufal.com para informações adicionais", Toast.LENGTH_LONG).show();
                    updateUI(null);
                }
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void updateUI(FirebaseUser user){

        GoogleSignInAccount acc = GoogleSignIn.getLastSignedInAccount(getApplicationContext());

        if(user == null){
            GSC.signOut();
            return;
        }
            //Salvando nas variaveis globais
            InfoClass.acc = acc;

            InfoClass.setAccountName(acc.getDisplayName());
            InfoClass.setAccountEmail(acc.getEmail());
            InfoClass.setAccountId(acc.getId());
            InfoClass.setAccountPhoto(acc.getPhotoUrl());

            //Toast.makeText(LoginActivity.this,   acc.getServerAuthCode(), Toast.LENGTH_LONG).show();

            progressBar.setVisibility(View.VISIBLE);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(getBaseContext(), MainActivity.class));
                    finish();
                }
            }, LOGIN_DELAY);
            //((TextView) findViewById(R.id.infoText)).setText(InfoClass.getAccountName() + "\n" + InfoClass.getAccountEmail());


    }

    private boolean isNetworkConnected() {
        try {
            InetAddress ipAddr = InetAddress.getByName("google.com");
            //You can replace it with your name
            return !ipAddr.equals("");

        } catch (Exception e) {
            return false;
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

                    if(TS.id.equals(InfoClass.getAccountId())){
                        InfoClass.CONTATO = TS.contato;
                        InfoClass.IDADE = TS.idade;
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
