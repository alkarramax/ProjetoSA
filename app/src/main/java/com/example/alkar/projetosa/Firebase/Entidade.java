package com.example.alkar.projetosa.Firebase;

public class Entidade {

    private String nome;
    private String descricao;
    private String entidadeUrl;

    public Entidade() {
    }

    public Entidade(String nome, String descricao, String entidadeUrl) {
        this.nome = nome;
        this.descricao = descricao;
        this.entidadeUrl = entidadeUrl;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getEntidadeUrl() {
        return entidadeUrl;
    }
}
