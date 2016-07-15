package com.carvalhodj.personalhealth.gui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.carvalhodj.personalhealth.R;
import com.carvalhodj.personalhealth.dominio.Dev01PerfilBiologico;
import com.carvalhodj.personalhealth.dominio.Dev01PerfilBiologicoEnum;
import com.carvalhodj.personalhealth.infra.Dev00GuiUtil;
import com.carvalhodj.personalhealth.infra.Dev01Validacao;
import com.carvalhodj.personalhealth.negocio.Dev01PersonalHealthService;

public class CadastroUsuarioActivity extends AppCompatActivity {
    private Dev01Validacao validacaoUtil = Dev01Validacao.getValidacaoUtil();
    private Dev01PersonalHealthService personalHealthService = new Dev01PersonalHealthService(this);
    private Dev00GuiUtil guiUtil = Dev00GuiUtil.getGuiUtil();
    private boolean biosensor = false;
    private boolean cadastroPerfBio = false;
    private Dev01PerfilBiologico perfBio = new Dev01PerfilBiologico();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);

    }

    public void onButtonClickCadastroUsuario(View v) {
        if (v.getId() == R.id.botao_cadastrar_usuario) {
            EditText nome = (EditText) findViewById(R.id.campoNome);
            EditText email = (EditText) findViewById(R.id.campoEmail);
            EditText senha = (EditText) findViewById(R.id.campoSenha);
            EditText repSenha = (EditText) findViewById(R.id.campoRepeteSenha);

            String nomeString = nome.getText().toString();
            String emailString = email.getText().toString();
            String senhaString = senha.getText().toString();
            String repSenhaString = repSenha.getText().toString();

            if (validacaoUtil.isFieldEmpty(nome)) {
                nome.requestFocus();
                nome.setError("Nome vazio!");
                return;
            }

            if (validacaoUtil.isFieldEmpty(email)) {
                email.requestFocus();
                email.setError("Email vazio!");
                return;
            }

            if (validacaoUtil.isFieldEmpty(senha) || validacaoUtil.isFieldEmpty(repSenha)) {
                if (validacaoUtil.isFieldEmpty(senha)) {
                    senha.requestFocus();
                    senha.setError("Senha vazia!");
                }

                if (validacaoUtil.isFieldEmpty(repSenha)) {
                    repSenha.requestFocus();
                    repSenha.setError("Repita a senha!");
                }

                return;
            }

            if (!validacaoUtil.hasSpacePassword(senha)) {
                senha.requestFocus();
                senha.setError("Não é permitido espaços em branco!");
                return;
            }

            if (!validacaoUtil.isEmailValid(emailString)) {
                email.requestFocus();
                email.setError("Email inválido!");
                return;
            }

            if (!senhaString.equals(repSenhaString)) {
                senha.requestFocus();
                senha.setError("Senhas não coincidem!");
                repSenha.requestFocus();
                repSenha.setError("Senhas não coincidem!");
            }

            if (!biosensor || !cadastroPerfBio) {
                guiUtil.toastShort(getApplicationContext(),"Falta parear um biosensor e/ou coletar os dados biológicos");
            } else {
                try {
                    personalHealthService.cadastrarUsuario(nomeString, emailString, senhaString, perfBio);
                    guiUtil.toastLong(getApplicationContext(), "Cadastro realizado com sucesso");
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                } catch (Exception exception) {
                    guiUtil.toastLong(getApplicationContext(), exception.getMessage());
                }
            }
        }

        if (v.getId() == R.id.cadastro_botao_parear_biosensor) {
            guiUtil.toastLong(getApplicationContext(), "Buscando biosensor. Aguarde...");
            guiUtil.toastShort(getApplicationContext(), "Biosensor pareado");
            biosensor = true;
        }

        if (v.getId() == R.id.cadastro_botao_perfil_biologico) {
            if (cadastroPerfBio) {
                guiUtil.toastShort(getApplicationContext(), "Perfil biológico já coletado");
            } else {
                guiUtil.toastLong(getApplicationContext(), "Aguarde, estamos coletando seus dados...");
                perfBio = new Dev01PerfilBiologico();
                String seqDna = Dev01PerfilBiologicoEnum.Caracteristica.getRandom().getSequencia();
                perfBio.setDNASeq(seqDna);
                cadastroPerfBio = true;
                guiUtil.toastLong(getApplicationContext(), "Dados coletados");
            }
        }
    }
}
