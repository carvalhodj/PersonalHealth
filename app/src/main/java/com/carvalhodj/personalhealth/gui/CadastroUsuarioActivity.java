package com.carvalhodj.personalhealth.gui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.carvalhodj.personalhealth.R;
import com.carvalhodj.personalhealth.dominio.Dev00PerfilBiologico;
import com.carvalhodj.personalhealth.dominio.Dev00PerfilBiologicoEnum;
import com.carvalhodj.personalhealth.infra.Dev00GuiUtil;
import com.carvalhodj.personalhealth.infra.Dev00Validacao;
import com.carvalhodj.personalhealth.negocio.Dev00PersonalHealthService;

public class CadastroUsuarioActivity extends AppCompatActivity {
    private Dev00Validacao validacaoUtil = Dev00Validacao.getValidacaoUtil();
    private Dev00PersonalHealthService personalHealthService = new Dev00PersonalHealthService(this);
    private Dev00GuiUtil guiUtil = Dev00GuiUtil.getGuiUtil();
    private boolean biosensor = false;
    private boolean cadastroPerfBio = false;
    private Dev00PerfilBiologico perfBio = new Dev00PerfilBiologico();

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
                nome.setError(getString(R.string.reg_error_empty_name));
                return;
            }

            if (validacaoUtil.isFieldEmpty(email)) {
                email.requestFocus();
                email.setError(getString(R.string.reg_error_empty_email));
                return;
            }

            if (validacaoUtil.isFieldEmpty(senha) || validacaoUtil.isFieldEmpty(repSenha)) {
                if (validacaoUtil.isFieldEmpty(senha)) {
                    senha.requestFocus();
                    senha.setError(getString(R.string.reg_error_empty_pass));
                }

                if (validacaoUtil.isFieldEmpty(repSenha)) {
                    repSenha.requestFocus();
                    repSenha.setError(getString(R.string.reg_error_empty_pass));
                }

                return;
            }

            if (!validacaoUtil.hasSpacePassword(senha)) {
                senha.requestFocus();
                senha.setError(getString(R.string.reg_error_blank_space_pass));
                return;
            }

            if (!validacaoUtil.isEmailValid(emailString)) {
                email.requestFocus();
                email.setError(getString(R.string.reg_error_invalid_email));
                return;
            }

            if (!senhaString.equals(repSenhaString)) {
                senha.requestFocus();
                senha.setError(getString(R.string.reg_error_diff_pass));
                repSenha.requestFocus();
                repSenha.setError(getString(R.string.reg_error_diff_pass));
            }

            if (!biosensor || !cadastroPerfBio) {
                guiUtil.toastShort(getApplicationContext(),getString(R.string.reg_error_nopair_biosensor));
            } else {
                try {
                    personalHealthService.cadastrarUsuario(nomeString, emailString, senhaString, perfBio);
                    guiUtil.toastLong(getApplicationContext(), getString(R.string.reg_completed));
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                } catch (Exception exception) {
                    guiUtil.toastLong(getApplicationContext(), exception.getMessage());
                }
            }
        }

        if (v.getId() == R.id.cadastro_botao_parear_biosensor) {
            guiUtil.toastLong(getApplicationContext(), getString(R.string.reg_search_biosensor));
            guiUtil.toastShort(getApplicationContext(), getString(R.string.reg_biosensor_paired));
            biosensor = true;
        }

        if (v.getId() == R.id.cadastro_botao_perfil_biologico) {
            if (cadastroPerfBio) {
                guiUtil.toastShort(getApplicationContext(), getString(R.string.reg_biodata_already_collected));
            } else {
                guiUtil.toastLong(getApplicationContext(), getString(R.string.reg_collecting_biodata));
                perfBio = new Dev00PerfilBiologico();
                String seqDna = Dev00PerfilBiologicoEnum.Caracteristica.getRandom().getSequencia();
                perfBio.setDNASeq(seqDna);
                cadastroPerfBio = true;
                guiUtil.toastLong(getApplicationContext(), getString(R.string.reg_biodata_collected));
            }
        }
    }
}
