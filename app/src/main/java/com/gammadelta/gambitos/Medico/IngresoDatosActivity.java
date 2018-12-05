package com.gammadelta.gambitos.Medico;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.gammadelta.gambitos.R;
import com.jjoe64.graphview.series.DataPoint;


public class IngresoDatosActivity extends AppCompatActivity {

    public Button buttonDatos;
    public EditText peso;
    public Spinner edad;
    public DataPoint a;


    public IngresoDatosActivity(Button buttonDatos, EditText peso, Spinner edad) {
        this.buttonDatos = buttonDatos;
        this.peso = peso;
        this.edad = edad;
        this.a= a;
        String meses  = edad.getSelectedItem().toString();
        String pesos  = peso.toString().trim();
       a= new DataPoint(Double.parseDouble(meses), Double.parseDouble(pesos));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingreso_datos);

      buttonDatos   = (Button) findViewById(R.id.ingresar_datos);
      peso          = (EditText) findViewById(R.id.peso);
      edad          = (Spinner) findViewById(R.id.edad);

      String meses  = edad.getSelectedItem().toString();
      String pesos  = peso.toString().trim();

     new DataPoint(Double.parseDouble(meses), Double.parseDouble(pesos));



    }



    public Button getButtonDatos() {
        return buttonDatos;
    }

    public void setButtonDatos(Button buttonDatos) {
        this.buttonDatos = buttonDatos;
    }

    public EditText getPeso() {
        return peso;
    }

    public void setPeso(EditText peso) {
        this.peso = peso;
    }

    public Spinner getEdad() {
        return edad;
    }

    public void setEdad(Spinner edad) {
        this.edad = edad;
    }
    public DataPoint getA() {
        return a;
    }

    public void setA(DataPoint a) {
        this.a = a;
    }

}

