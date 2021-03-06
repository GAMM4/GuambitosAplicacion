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

import com.gammadelta.gambitos.Login.IngresarActivity;
import com.gammadelta.gambitos.Padre.InicioPadresActivity;
import com.gammadelta.gambitos.R;
import com.gammadelta.gambitos.model.Hijos;
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

public class RegistroPadreDosActivity extends AppCompatActivity{

    private static final String USUARIO_NODE = "Usuarios";
    private static final String PADRE_NODE = "Padres";
    private static final String TAG = "RegistroPadreDosActicity";
    private static final String HIJO_NODE = "Hijos";
    private DatabaseReference databaseReference;

    private ProgressDialog progressDialog;

    private FirebaseUser firebaseUser;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth firebaseAuth;

    private TextView pin;
    private TextView nombre_hijo;
    private TextView documento_hijo;
    private Spinner genero;
    private TextView estatura_madre;
    private TextView estatura_padre;
    private TextView peso_nacimiento;
    private TextView longitud_nacimiento;
    private TextView perimetro_nacimiento;
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

    int verificado = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_padre_dos);

        fecha = (EditText) findViewById(R.id.fecha);
        obtener_imagen = (ImageButton) findViewById(R.id.obtener_fecha);
        obtener_imagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obtenerFecha();
            }
        });

        pin                 = (TextView) findViewById(R.id.pin);
        nombre_hijo         = (TextView) findViewById(R.id.nombre_hijo);
        documento_hijo      = (TextView) findViewById(R.id.documento_identidad_hijo);
        genero              = (Spinner) findViewById(R.id.spinner);
        estatura_madre      = (TextView) findViewById(R.id.estatura_madre);
        estatura_padre      = (TextView) findViewById(R.id.estatura_padre);
        registra_hijo       = (Button) findViewById(R.id.boton_registro_hijo);
        agregar_otro_hijo   = (Button) findViewById(R.id.boton_agregar_otrohijo);
        peso_nacimiento     = (TextView) findViewById(R.id.peso_nacimineto);
        longitud_nacimiento = (TextView) findViewById(R.id.longitud_nacimineto);
        perimetro_nacimiento= (TextView) findViewById(R.id.perimetro_nacimineto);

        progressDialog = new ProgressDialog(this);

        registra_hijo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarHijo();
                verificado = 1;

            }
        });
        agregar_otro_hijo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarHijo();
                verificado = 2;
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null){
                    databaseReference = FirebaseDatabase.getInstance().getReference().child(USUARIO_NODE).child(PADRE_NODE);
                    databaseReference.child(firebaseAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()){
                                pin.setText(String.valueOf(dataSnapshot.child("PIN").getValue()));
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                } else {
                    startActivity(new Intent(RegistroPadreDosActivity.this, IngresarActivity.class));
                    finish();
                }
            }
        };

    }

    @Override
    public void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(mAuthListener);
    }


    private void registrarHijo(){
        final String nombre = nombre_hijo.getText().toString().trim();
        final String documento = documento_hijo.getText().toString().trim();
        final String sexo = genero.getSelectedItem().toString();
        final String es_madre = estatura_madre.getText().toString().trim();
        final String es_padre = estatura_padre.getText().toString().trim();
        final String fecha_naci = fecha.getText().toString();
        final String peso_naci = peso_nacimiento.getText().toString();
        final String longitud_naci = longitud_nacimiento.getText().toString();
        final String perimetro_naci = perimetro_nacimiento.getText().toString();

        progressDialog.setMessage("Creando hijo...");
        progressDialog.show();

        //Verificar que los campos no esten vacios
        if (!TextUtils.isEmpty(nombre) && !TextUtils.isEmpty(documento) &&
                !TextUtils.isEmpty(sexo) && !TextUtils.isEmpty(fecha_naci)){

            final String userID = firebaseAuth.getCurrentUser().getUid();



            databaseReference.child(userID).child(HIJO_NODE).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    databaseReference.child(userID).child(HIJO_NODE).child(documento).child("Nombre").setValue(nombre);
                    databaseReference.child(userID).child(HIJO_NODE).child(documento).child("Sexo").setValue(sexo);
                    databaseReference.child(userID).child(HIJO_NODE).child(documento).child("Estatura madre").setValue(es_madre);
                    databaseReference.child(userID).child(HIJO_NODE).child(documento).child("Estatura padre").setValue(es_padre);
                    databaseReference.child(userID).child(HIJO_NODE).child(documento).child("Fecha de nacimiento").setValue(fecha_naci);

                    if (!TextUtils.isEmpty(peso_naci)){
                        databaseReference.child(userID).child(HIJO_NODE).child(documento).child("Peso de nacimiento").setValue(peso_naci);
                    }
                    if (!TextUtils.isEmpty(longitud_naci)){
                        databaseReference.child(userID).child(HIJO_NODE).child(documento).child("Longitud de nacimiento").setValue(longitud_naci);
                    }
                    if (!TextUtils.isEmpty(perimetro_naci)){
                        databaseReference.child(userID).child(HIJO_NODE).child(documento).child("Perimetro de nacimiento").setValue(perimetro_naci);
                    }

                    if(verificado == 1){
                        Intent i = new Intent(RegistroPadreDosActivity.this, InicioPadresActivity.class);
                        startActivity(i);
                        finish();
                    }

                    if(verificado == 2){
                        Intent a = new Intent(RegistroPadreDosActivity.this, RegistroPadreDosActivity.class);
                        startActivity(a);
                        finish();
                    }

                    if (dataSnapshot.child(documento).child("Nombre").exists()
                            && dataSnapshot.child(documento).child("Sexo").exists()
                            && dataSnapshot.child(documento).child("Estatura madre").exists()
                            && dataSnapshot.child(documento).child("Estatura padre").exists()
                            && dataSnapshot.child(documento).child("Fecha de nacimiento").exists()) {

                        Toast.makeText(RegistroPadreDosActivity.this, "Hijo registrado", Toast.LENGTH_LONG);

                    } else {
                        Toast.makeText(RegistroPadreDosActivity.this, "Datos no registrados", Toast.LENGTH_LONG);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            progressDialog.dismiss();

        }else{

            Toast.makeText(this,"Faltan completar campos",Toast.LENGTH_LONG).show();

        }
        progressDialog.dismiss();
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
