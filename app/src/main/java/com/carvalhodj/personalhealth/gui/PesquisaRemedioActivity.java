package com.carvalhodj.personalhealth.gui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.carvalhodj.personalhealth.R;
import com.carvalhodj.personalhealth.dominio.Dev01Drug;

import java.util.ArrayList;

public class PesquisaRemedioActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesquisa_remedio);
    }

    public void onButtonClickPesquisa(View v) {
        if(v.getId() == R.id.busca_botao) {
            Spinner busca = (Spinner) findViewById(R.id.busca_parametro);
            TextView titleResultado = (TextView) findViewById(R.id.busca_titulo_resultado);
            TextView melhorRemedio = (TextView) findViewById(R.id.busca_remedio_resultado);

            String buscaString = busca.getSelectedItem().toString();


        }
    }
}
