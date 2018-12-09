package com.gammadelta.gambitos.Medico;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.gammadelta.gambitos.Login.IngresoMedicoIndependiente;
import com.gammadelta.gambitos.R;

public class InicioMedicoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_medico);
    }

    public final void irIngresoMedicoGrafica(View view) {
        Intent intent = new Intent((Context)this, IngresoMedicoIndependiente.class);
        this.startActivity(intent);
    }
}
