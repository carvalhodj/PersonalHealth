package com.carvalhodj.personalhealth.infra;

import android.text.TextUtils;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Dev00Validacao {
    private static Dev00Validacao validacaoUtil = new Dev00Validacao();

    private Dev00Validacao(){
    }

    public static Dev00Validacao getValidacaoUtil(){
        return validacaoUtil;
    }

    public boolean isEmailValid(CharSequence email) { return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches(); }

    public boolean isFieldEmpty(EditText campo){ return TextUtils.isEmpty(campo.getText().toString()); }

    public boolean hasSpacePassword(EditText campo){
        String senha = campo.getText().toString();
        Pattern p= Pattern.compile("^[^\\s]+$");
        Matcher m = p.matcher(senha);
        return m.matches();
    }
}
