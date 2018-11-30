package com.gammadelta.gambitos.Login;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.gammadelta.gambitos.Padre.InicioPadresActivity;
import com.gammadelta.gambitos.R;
import com.gammadelta.gambitos.Registro.RegistroActivity;

import kotlin.jvm.internal.Intrinsics;

public final class IngresarActivity extends AppCompatActivity {
    private TextView texto;
    private Typeface coolvetica;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_ingresar);

        String fuente1 = "fuentes/coolvetica.ttf";
        this.coolvetica = Typeface.createFromAsset(this.getAssets(), fuente1);
        this.texto = (TextView)this.findViewById(R.id.guambitos);
    }

    public final void irInicioPadre(View view) {
        Intrinsics.checkParameterIsNotNull(view, "view");
        Intent intent = new Intent((Context)this, InicioPadresActivity.class);
        this.startActivity(intent);
    }

    public final void irRegistro(View view) {
        Intrinsics.checkParameterIsNotNull(view, "view");
        Intent intent = new Intent((Context)this, RegistroActivity.class);
        this.startActivity(intent);
    }
}
