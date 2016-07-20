package com.carvalhodj.personalhealth.gui;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import com.carvalhodj.personalhealth.R;
import com.carvalhodj.personalhealth.dominio.Dev00Usuario;
import com.carvalhodj.personalhealth.infra.Dev00GuiUtil;
import com.carvalhodj.personalhealth.infra.Dev00Sessao;
import com.carvalhodj.personalhealth.infra.Dev00Validacao;
import com.carvalhodj.personalhealth.negocio.Dev00PersonalHealthService;

public class LoginActivity extends AppCompatActivity {
    private Dev00Validacao validacaoUtil = Dev00Validacao.getValidacaoUtil();
    private Dev00Sessao sessao = Dev00Sessao.getInstancia();
    private Dev00PersonalHealthService personalHealthService = new Dev00PersonalHealthService(this);
    private Dev00GuiUtil guiUtil = Dev00GuiUtil.getGuiUtil();
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sessao.reset();

        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.backgroundPersonalHealthDark));
    }

    public void onClickLogin(View v){

        if (v.getId() == R.id.login_btn) {
            EditText usuarioEmail = (EditText) findViewById(R.id.login_email);
            EditText usuarioSenha = (EditText) findViewById(R.id.login_senha);
            String usuarioEmailString = usuarioEmail.getText().toString();
            String usuarioSenhaString = usuarioSenha.getText().toString();

            if (validacaoUtil.isFieldEmpty(usuarioEmail)){
                usuarioEmail.requestFocus();
                usuarioEmail.setError(getString(R.string.login_enter_email));
                return;
            }

            if (validacaoUtil.isFieldEmpty(usuarioSenha)){
                usuarioSenha.requestFocus();
                usuarioSenha.setError(getString(R.string.login_enter_pass));
                return;
            }

            if(!validacaoUtil.isEmailValid(usuarioEmailString)){
                usuarioEmail.requestFocus();
                usuarioEmail.setError(getString(R.string.reg_error_invalid_email));
                return;
            }

            try {
                Dev00Usuario usuario = personalHealthService.login(usuarioEmailString, usuarioSenhaString);
                if (sessao.getUsuario()!=null){
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }

            } catch (Exception exception) {
                guiUtil.toastLong(getApplicationContext(), exception.getMessage());
            }

        }

        else if (v.getId() == R.id.btn_login_facebook) {
            guiUtil.toastShort(getApplicationContext(), getString(R.string.login_to_be_impl));
        }

        else if (v.getId() == R.id.btn_login_google) {
            guiUtil.toastShort(getApplicationContext(), getString(R.string.login_to_be_impl));
        }

        else if (v.getId() == R.id.link_cadastro_usuario) {
            Intent intent = new Intent(getApplicationContext(), CadastroUsuarioActivity.class);
            startActivity(intent);
        }

    }
}
