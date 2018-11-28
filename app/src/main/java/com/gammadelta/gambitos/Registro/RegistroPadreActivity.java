package com.gammadelta.gambitos.Registro;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.gammadelta.gambitos.R;

public class RegistroPadreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_padre);
    }

    public void irRegistroPadre2(View view){
        Intent intent = new Intent(this, RegistroPadre2Activity.class);
        startActivity(intent);
    }
}
