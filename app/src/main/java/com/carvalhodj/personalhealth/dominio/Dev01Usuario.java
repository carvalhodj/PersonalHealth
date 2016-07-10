package com.carvalhodj.personalhealth.dominio;

public class Dev01Usuario {
    private String nome;
    private String email;
    private String pass;
    private long id;
    private Dev01PerfilBiologico perfBio;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
