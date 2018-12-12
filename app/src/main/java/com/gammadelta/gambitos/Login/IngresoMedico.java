package com.gammadelta.gambitos.Login;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.gammadelta.gambitos.R;

public class IngresoMedico extends AppCompatActivity {
    private static final String USUARIO_NODE = "Usuarios";
    private static final String TAG = "ingresoMedico";
    private static final String MEDICO_NODE = "Medicos";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingreso_medico);
    }

    public final void irIngresoMedicoIndependiente(View view) {
        Intent intent = new Intent((Context)this, IngresoMedicoIndependiente.class);
        this.startActivity(intent);
    }
}
