package com.carvalhodj.personalhealth.negocio;

import android.content.Context;

import com.carvalhodj.personalhealth.dao.Dev00PersonalHealthDAO;
import com.carvalhodj.personalhealth.dominio.Dev00Drug;
import com.carvalhodj.personalhealth.dominio.Dev00PerfilBiologico;
import com.carvalhodj.personalhealth.dominio.Dev00Usuario;
import com.carvalhodj.personalhealth.infra.Dev00Sessao;

public class Dev00PersonalHealthService {
    private Dev00Sessao sessao = Dev00Sessao.getInstancia();
    private Dev00PersonalHealthDAO personalHealthDAO;

    public Dev00PersonalHealthService(Context context) {
        personalHealthDAO = new Dev00PersonalHealthDAO(context);

    }

    public void cadastrarUsuario(String nome, String email, String senha, Dev00PerfilBiologico perfilBiologico) throws Exception{
        sessao.reset();

        Dev00Usuario usuario = personalHealthDAO.getUsuario(email);

        if (usuario!=null){
            throw new Exception("Email already registered");
        }

        usuario = new Dev00Usuario();
        usuario.setNome(nome);
        usuario.setEmail(email);
        usuario.setPass(senha);
        usuario.setPerfBio(perfilBiologico);

        long idUsuario = personalHealthDAO.inserir(usuario);

        usuario.setId(idUsuario);

        sessao.setUsuario(usuario);

    }

    public Dev00Usuario login(String email, String senha) throws Exception{
        sessao.reset();

        Dev00Usuario usuario = personalHealthDAO.getUsuario(email, senha);

        if(usuario==null) {
            throw new Exception("Email or Password invalid");
        }

        sessao.setUsuario(usuario);

        return usuario;
    }

    public void setPerfilBiologico(Dev00Usuario usuario, Dev00PerfilBiologico perfilBiologico) {
        personalHealthDAO.cadastrarPerfilBiologico(usuario, perfilBiologico);
    }

    public Dev00Drug getBestOption(String sintoma, String perfBio) {
        return personalHealthDAO.getBestOption(sintoma, perfBio);
    }
}
