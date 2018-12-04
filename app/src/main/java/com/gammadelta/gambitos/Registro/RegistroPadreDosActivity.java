package com.gammadelta.gambitos.Registro;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.gammadelta.gambitos.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class RegistroPadreDosActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String USUARIO_NODE = "Usuarios";
    private static final String PADRE_NODE = "Padres";
    private static final String TAG = "RegistroPadreDosActicity";
    private static final String HIJO_NODE = "Hijos";
    private DatabaseReference databaseReference;

    private ProgressDialog progressDialog;

    private FirebaseUser firebaseUser;
    private FirebaseAuth firebaseAuth;

    private TextView pin;
    private TextView nombre_hijo;
    private TextView documento_hijo;
    private Spinner genero;
    private TextView estatura_madre;
    private TextView estatura_padre;
    private Button registra_hijo;
    private Button agregar_otro_hijo;

    private static final String CERO = "0";
    private static final String BARRA = "/";
    public final Calendar c = Calendar.getInstance();
    final int dia = c.get(Calendar.DAY_OF_MONTH);
    final int mes = c.get(Calendar.MONTH);
    final int anio = c.get(Calendar.YEAR);
    EditText fecha;
    ImageButton obtener_imagen;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_padre_dos);

        fecha = (EditText) findViewById(R.id.fecha);
        obtener_imagen = (ImageButton) findViewById(R.id.obtener_fecha);
        obtener_imagen.setOnClickListener(this);

        pin                 = (TextView) findViewById(R.id.pin);
        nombre_hijo         = (TextView) findViewById(R.id.nombre_hijo);
        documento_hijo      = (TextView) findViewById(R.id.documento_identidad_hijo);
        genero              = (Spinner) findViewById(R.id.spinner);
        estatura_madre      = (TextView) findViewById(R.id.estatura_madre);
        estatura_padre      = (TextView) findViewById(R.id.estatura_padre);
        registra_hijo       = (Button) findViewById(R.id.boton_registro_hijo);
        agregar_otro_hijo   = (Button) findViewById(R.id.boton_agregar_otrohijo);


        //registra_hijo.setOnClickListener(this);
        //agregar_otro_hijo.setOnClickListener(this);

        //progressDialog = new ProgressDialog(this);

        /*firebaseAuth = FirebaseAuth.getInstance();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        databaseReference = FirebaseDatabase.getInstance().getReference();*/


        /*String userID = firebaseAuth.getCurrentUser().getUid();

        databaseReference.child(USUARIO_NODE).child(PADRE_NODE).child(userID).addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    pin.setText(String.valueOf(dataSnapshot.child("PIN").getValue()));
                }
                else {
                    pin.setText("000000");
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });*/


    }

    /*@Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser currentUser) {
    }*/

    ;

    /*private void registrarHijo(){
        final String nombre = nombre_hijo.getText().toString().trim();
        final String documento = documento_hijo.getText().toString().trim();
        final String sexo = genero.getSelectedItem().toString();
        final String es_madre = estatura_madre.getText().toString().trim();
        final String es_padre = estatura_padre.getText().toString().trim();
        final String fecha_naci = fecha.getText().toString();


        //Verificar que los campos no esten vacios
        if (!TextUtils.isEmpty(nombre) && !TextUtils.isEmpty(documento) &&
                !TextUtils.isEmpty(sexo) && !TextUtils.isEmpty(fecha_naci)){

            progressDialog.setMessage("Creando hijo...");
            progressDialog.show();

            String userID = firebaseAuth.getCurrentUser().getUid();

            databaseReference.child(USUARIO_NODE).child(PADRE_NODE).child(userID).child(HIJO_NODE).child("Nombre").setValue(nombre);
            databaseReference.child(USUARIO_NODE).child(PADRE_NODE).child(userID).child(HIJO_NODE).child("Documento de identidad").setValue(documento);
            databaseReference.child(USUARIO_NODE).child(PADRE_NODE).child(userID).child(HIJO_NODE).child("Sexo").setValue(sexo);
            databaseReference.child(USUARIO_NODE).child(PADRE_NODE).child(userID).child(HIJO_NODE).child("Estatura madre").setValue(es_madre);
            databaseReference.child(USUARIO_NODE).child(PADRE_NODE).child(userID).child(HIJO_NODE).child("Estatura padre").setValue(es_padre);
            databaseReference.child(USUARIO_NODE).child(PADRE_NODE).child(userID).child(HIJO_NODE).child("Fecha de nacimiento").setValue(fecha_naci);

            Toast.makeText(this,"Hijo registrado",Toast.LENGTH_LONG).show();

        }else{

            Toast.makeText(this,"Faltan completar campos",Toast.LENGTH_LONG).show();

        }

        progressDialog.dismiss();

    }*/

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.obtener_fecha:
                obtenerFecha();
                break;
            /*case R.id.boton_registro_hijo:
                registrarHijo();
                Intent i = new Intent(RegistroPadreDosActivity.this, RegistroMedicoActivity.class);
                startActivity(i);
                break;
            case R.id.boton_agregar_otrohijo:
                registrarHijo();
                Intent a = new Intent(RegistroPadreDosActivity.this, RegistroPadreDosActivity.class);
                startActivity(a);
                finish();
                break;*/
        }

    }

    private void obtenerFecha(){
        DatePickerDialog recogerFecha = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                final int mesActual = month + 1;
                String diaFormateado = (dayOfMonth < 10)? CERO + String.valueOf(dayOfMonth):String.valueOf(dayOfMonth);
                String medFormateado = (mesActual < 10)? CERO + String.valueOf(mesActual):String.valueOf(mesActual);
                fecha.setText(diaFormateado + BARRA + medFormateado + BARRA + year);
            }
        },anio,mes,dia);
        recogerFecha.show();
    }
}
