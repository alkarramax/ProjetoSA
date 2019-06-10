package com.example.alkar.projetosa.Firebase;

public class Doacao {

    private String tipo1;
    private String tipo2;
    private String tipo3;
    private String tipo4;
    private String objetivo;
    private String local;
    private String hora;
    private String data;

    public Doacao() {
    }

    public Doacao(String tipo1, String tipo2, String tipo3, String tipo4, String objetivo, String local, String hora, String data){
        this.tipo1 = tipo1;
        this.tipo2 = tipo2;
        this.tipo3 = tipo3;
        this.tipo4 = tipo4;
        this.objetivo = objetivo;
        this.local = local;
        this.hora = hora;
        this.data = data;
    }

    public String getTipo1() {
        return tipo1;
    }

    public String getTipo2() {
        return tipo2;
    }

    public String getTipo3() {
        return tipo3;
    }

    public String getTipo4() {
        return tipo4;
    }

    public String getObjetivo() {
        return objetivo;
    }

    public String getLocal() {
        return local;
    }

    public String getHora() {
        return hora;
    }

    public String getData() {
        return data;
    }
}
