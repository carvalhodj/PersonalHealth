package com.carvalhodj.personalhealth.gui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.carvalhodj.personalhealth.R;
import com.carvalhodj.personalhealth.dominio.Dev01Usuario;
import com.carvalhodj.personalhealth.infra.Dev00GuiUtil;
import com.carvalhodj.personalhealth.infra.Dev00Sessao;
import com.carvalhodj.personalhealth.infra.Dev01Validacao;
import com.carvalhodj.personalhealth.negocio.Dev01PersonalHealthService;

public class LoginActivity extends AppCompatActivity {
    private Dev01Validacao validacaoUtil = Dev01Validacao.getValidacaoUtil();
    private Dev00Sessao sessao = Dev00Sessao.getInstancia();
    private Dev01PersonalHealthService personalHealthService = new Dev01PersonalHealthService(this);
    private Dev00GuiUtil guiUtil = Dev00GuiUtil.getGuiUtil();
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sessao.reset();
    }

    public void onClickLogin(View v){

        if (v.getId() == R.id.login_btn) {
            EditText usuarioEmail = (EditText) findViewById(R.id.login_email);
            EditText usuarioSenha = (EditText) findViewById(R.id.login_senha);
            String usuarioEmailString = usuarioEmail.getText().toString();
            String usuarioSenhaString = usuarioSenha.getText().toString();

            if (validacaoUtil.isFieldEmpty(usuarioEmail)){
                usuarioEmail.requestFocus();
                usuarioEmail.setError("Digite email!");
                return;
            }

            if (validacaoUtil.isFieldEmpty(usuarioSenha)){
                usuarioSenha.requestFocus();
                usuarioSenha.setError("Digite a senha!");
                return;
            }

            if(!validacaoUtil.isEmailValid(usuarioEmailString)){
                usuarioEmail.requestFocus();
                usuarioEmail.setError("Email inválido!");
                return;
            }

            try {
                Dev01Usuario usuario = personalHealthService.login(usuarioEmailString, usuarioSenhaString);
                if (sessao.getUsuario()!=null){
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }

            } catch (Exception exception) {
                guiUtil.toastLong(getApplicationContext(), exception.getMessage());
            }

        }

        else if (v.getId() == R.id.btn_login_facebook) {
            //DO FACEBOOK STUFF
        }

        else if (v.getId() == R.id.btn_login_google) {
            //DO GOOGLE STUFF
        }

        else if (v.getId() == R.id.link_cadastro_usuario) {
            Intent intent = new Intent(getApplicationContext(), CadastroUsuarioActivity.class);
            startActivity(intent);
        }

    }
}
