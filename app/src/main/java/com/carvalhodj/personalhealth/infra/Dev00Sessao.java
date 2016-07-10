package com.carvalhodj.personalhealth.infra;

import com.carvalhodj.personalhealth.dominio.Dev01Usuario;

import java.util.Date;

public class Dev00Sessao {
    private static Dev00Sessao instancia = new Dev00Sessao();
    private Dev01Usuario usuario;
    private Date horaLogin;

    private Dev00Sessao(){
    }

    public static Dev00Sessao getInstancia(){
        return instancia;
    }

    public Date getHoraLogin() {
        return horaLogin;
    }

    public void setHoraLogin(Date horaLogin) {
        this.horaLogin = horaLogin;
    }

    public void reset(){
        this.usuario = null;
        this.horaLogin = null;
    }

    public Dev01Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Dev01Usuario usuario) {
        this.usuario = usuario;
    }
}
