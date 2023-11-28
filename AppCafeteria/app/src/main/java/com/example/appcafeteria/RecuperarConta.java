package com.example.appcafeteria;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RecuperarConta extends AppCompatActivity {

    private EditText recuperarEmail;
    private AppCompatButton bt_recuperar;
    private ImageButton bt_back;
    private FirebaseAuth auth;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar_conta);
        this.iniciarComponentes();
        this.voltarTelaLogin();
        this.coreRecuperarConta();
    }//fim onCreate

    private void coreRecuperarConta(){
        bt_recuperar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recuperarConta();
            }
        });
    }//fim coreRecuperarConta

    private void recuperarConta(){
        String email= recuperarEmail.getText().toString();
        auth= FirebaseAuth.getInstance();
        if(!email.isEmpty()){// usuário preencheu o email? função isEmpty retorna true caso email não esteja preenchido, porém a ! nega o resultado
            //ou seja, se tiver preenchido a função retorna false e aí entra no if, pq para nós só interessa se o campo email estiver preenchido

            auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(getBaseContext(), "Enviado com sucesso!", Toast.LENGTH_SHORT).show();
                    }//fim if
                }//fim onComplete
            }).addOnFailureListener(
                    new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            trataErro(e.toString());//trataErro não existe, temos que ainda implementar
                        }
                    }
            );

        }//fim if deu certo

        else{//o usuário não preencheu o campo email
            Toast.makeText(getBaseContext(), "Insira um e-mail!", Toast.LENGTH_SHORT).show();
        }//fim else


    }//fim recuperarSenha

    public void trataErro(String erro){
        if(erro.contains("address is badly")){
            Toast.makeText(getBaseContext(), "E-mail inválido!", Toast.LENGTH_SHORT).show();
        }//email inválido
        else if(erro.contains("there is no user")){
            Toast.makeText(getBaseContext(), "E-mail não cadastrado!", Toast.LENGTH_SHORT).show();
        }//email não cadastrado
        else if(erro.contains("INVALID_EMAIL")){
            Toast.makeText(getBaseContext(), "E-mail inválido!", Toast.LENGTH_SHORT).show();
        }//email INVÁLIDO

        else
        {
            Toast.makeText(getBaseContext(), erro, Toast.LENGTH_SHORT).show();
        }//fim else
    }//fim trataErro

    private void iniciarComponentes(){
        recuperarEmail= findViewById(R.id.editTextEmailRecuperarConta);
        bt_recuperar= findViewById(R.id.bt_recuperar);
        bt_back= findViewById(R.id.ButtonBackRecuperarConta);
    }//fim iniciarComponentes

    private void voltarTelaLogin(){
        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(RecuperarConta.this, FormLogin.class);
                startActivity(intent);
                finish();
            }
        }//fim voltarTelaLogin
        );
    }
}