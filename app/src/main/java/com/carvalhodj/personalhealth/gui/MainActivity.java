package com.carvalhodj.personalhealth.gui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.carvalhodj.personalhealth.R;
import com.carvalhodj.personalhealth.dominio.Dev01PerfilBiologico;
import com.carvalhodj.personalhealth.dominio.Dev01PerfilBiologicoEnum;
import com.carvalhodj.personalhealth.dominio.Dev01Usuario;
import com.carvalhodj.personalhealth.infra.Dev00GuiUtil;
import com.carvalhodj.personalhealth.infra.Dev00Sessao;
import com.carvalhodj.personalhealth.negocio.Dev01PersonalHealthService;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private Dev00GuiUtil guiUtil = Dev00GuiUtil.getGuiUtil();
    private Dev00Sessao sessao = Dev00Sessao.getInstancia();
    private Dev01PersonalHealthService personalHealthService = new Dev01PersonalHealthService(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onButtonClickMain(View v) {
        if (v.getId() == R.id.main_btn_perfil_biologico) {
            Dev01Usuario usuario = sessao.getUsuario();
            String a = usuario.getPerfBio();

            if (!Objects.equals(usuario.getPerfBio(), "0")) {
                guiUtil.toastShort(getApplicationContext(), usuario.getPerfBio());
            } else {
                Dev01PerfilBiologico perfBio = new Dev01PerfilBiologico();
                String x = Dev01PerfilBiologicoEnum.Caracteristica.getRandom().getSequencia();
                perfBio.setDNASeq(x);
                personalHealthService.setPerfilBiologico(usuario, perfBio);
            }

            //sessao.getUsuario().setPerfBio(perfBio);
            //guiUtil.toastShort(getApplicationContext(), sessao.getUsuario().getPerfBio());
        }
    }
}
