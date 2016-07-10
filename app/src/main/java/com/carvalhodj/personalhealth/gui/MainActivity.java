package com.carvalhodj.personalhealth.gui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.carvalhodj.personalhealth.R;
import com.carvalhodj.personalhealth.dominio.Dev01PerfilBiologicoEnum;
import com.carvalhodj.personalhealth.infra.Dev00GuiUtil;

public class MainActivity extends AppCompatActivity {
    private Dev00GuiUtil guiUtil = Dev00GuiUtil.getGuiUtil();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onButtonClickMain(View v) {
        if (v.getId() == R.id.main_btn_perfil_biologico) {
            String x = Dev01PerfilBiologicoEnum.Caracteristica.getRandom().getSequencia();
            guiUtil.toastShort(getApplicationContext(), x);
        }
    }
}
