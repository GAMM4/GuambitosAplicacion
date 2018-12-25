package com.gammadelta.gambitos.Padre;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.gammadelta.gambitos.R;

public class ProximamenteActivity extends AppCompatActivity {

    private TextView texto;
    private Typeface coolvetica;

    private ImageView atras;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proximamente);

        String fuente1  = "fuentes/coolvetica.ttf";
        this.coolvetica = Typeface.createFromAsset(this.getAssets(), fuente1);
        this.texto      = (TextView)this.findViewById(R.id.proximamo);

        atras = (ImageView) findViewById(R.id.atras);

        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
