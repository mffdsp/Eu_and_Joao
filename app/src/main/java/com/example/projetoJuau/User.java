package com.example.projetoJuau;

public class User {
    public String nome;
    public String email;
    public String idade;
    public String id;
    public String about;

    public User(){

    }

    public User(String nome, String email, String idade, String id, String about){
        this.idade = idade;
        this.email = email;
        this.id = id;
        this.nome = nome;
        this.about = about;
    }
}
