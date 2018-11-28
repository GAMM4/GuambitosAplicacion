package com.gammadelta.gambitos;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class IngresarActivity extends AppCompatActivity {

    private TextView texto;
    private Typeface coolvetica;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingresar);

        String fuente1 = "fuentes/coolvetica.ttf";
        this.coolvetica = Typeface.createFromAsset(getAssets(),fuente1);

        texto = (TextView) findViewById(R.id.guambitos);
        texto.setTypeface(coolvetica);

    }
}
