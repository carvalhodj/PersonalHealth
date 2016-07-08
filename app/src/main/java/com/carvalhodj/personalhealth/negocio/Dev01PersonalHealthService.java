package com.carvalhodj.personalhealth.negocio;

import android.content.Context;

import com.carvalhodj.personalhealth.dao.Dev01PersonalHealthDAO;
import com.carvalhodj.personalhealth.dominio.Dev00Usuario;
import com.carvalhodj.personalhealth.infra.Dev00Sessao;

public class Dev01PersonalHealthService {
    private Dev00Sessao sessao = Dev00Sessao.getInstancia();
    private Dev01PersonalHealthDAO personalHealthDAO;

    public Dev01PersonalHealthService(Context context) {
        personalHealthDAO = new Dev01PersonalHealthDAO(context);

    }

    public void cadastrarUsuario(String nome, String email, String senha) throws Exception{
        sessao.reset();

        Dev00Usuario usuario = personalHealthDAO.getUsuario(email);

        if (usuario!=null){
            throw new Exception("Email já cadastrado");
        }

        usuario = new Dev00Usuario();
        usuario.setNome(nome);
        usuario.setEmail(email);
        usuario.setPass(senha);

        long idUsuario = personalHealthDAO.inserir(usuario);

        usuario.setId(idUsuario);

        sessao.setUsuario(usuario);

    }

    public Dev00Usuario login(String email, String senha) throws Exception{
        sessao.reset();

        Dev00Usuario usuario = personalHealthDAO.getUsuario(email, senha);

        if(usuario==null) {
            throw new Exception("Usuário ou senha inválidos");
        }

        sessao.setUsuario(usuario);

        return usuario;
    }
}