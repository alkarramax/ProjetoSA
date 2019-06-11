package com.example.alkar.projetosa.Firebase;

public class Softplayer {

    private String uuid;
    private String nome;
    private String sobrenome;
    private String email;
    private String cargo;
    private String unidade;
    private String profileUrl;
    private int contador;



    public Softplayer() {
    }

    public Softplayer(String uuid, String nome, String sobrenome, String email, String cargo, String unidade, String profileUrl) {
        this.uuid = uuid;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.email = email;
        this.cargo = cargo;
        this.unidade = unidade;
        this.profileUrl = profileUrl;
    }

    public Softplayer(int contador) {
        this.contador = contador;
    }

    public String getUuid() {
        return uuid;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public String getNome() {
        return nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public String getEmail() {
        return email;
    }

    public String getCargo() {
        return cargo;
    }

    public String getUnidade() {
        return unidade;
    }

    public int getContador() {
        return contador;
    }
}
