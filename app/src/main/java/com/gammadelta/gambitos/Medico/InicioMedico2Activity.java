package com.gammadelta.gambitos.Medico;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextClock;
import android.widget.TextView;

import com.gammadelta.gambitos.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.gammadelta.gambitos.Login.IngresoMedicoIndependiente.documento_medic;
import static com.gammadelta.gambitos.Medico.InicioMedicoActivity.id_hijo_medico;
import static com.gammadelta.gambitos.Medico.InicioMedicoActivity.id_padre_medico;

public class InicioMedico2Activity extends AppCompatActivity {
    private static final String USUARIO_NODE = "Usuarios";
    private static final String PADRE_NODE = "Padres";
    private static final String MEDICO_NODE = "Medicos";
    private static final String TAG = "InicioMedico2";
    private static final String HIJO_NODE = "Hijos";
    private DatabaseReference databaseReference;

    private FirebaseUser firebaseUser;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth firebaseAuth;

    private TextView nombreMedico;
    private TextView nombreHijo;
    private TextView edadHijo;
    private TextView fechaActualizacion;
    private Button   cerrarSesionMedico;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_medico2);

        nombreMedico        = (TextView)    findViewById(R.id.nombre_medico);
        nombreHijo          = (TextView)    findViewById(R.id.nombre_hijo);
        edadHijo            = (TextView)    findViewById(R.id.edad_hijo);
        fechaActualizacion  = (TextView)    findViewById(R.id.fecha_ultimoRegistro);
        cerrarSesionMedico  = (Button)      findViewById(R.id.boton_cerrarsesion_medico);

        cerrarSesionMedico.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(InicioMedico2Activity.this, InicioMedicoActivity.class);
                startActivity(i);
                finish();
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null){
                    databaseReference = FirebaseDatabase.getInstance().getReference();

                    databaseReference.child(USUARIO_NODE).child(MEDICO_NODE).child(documento_medic).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()){
                                String nombre_doc = "";
                                String apellido_doc = "";
                                nombre_doc = dataSnapshot.child("Nombre").getValue().toString();
                                apellido_doc = dataSnapshot.child("Apellido").getValue().toString();
                                nombreMedico.setText(nombre_doc + " " + apellido_doc);
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            nombreMedico.setText("No existe");
                        }
                    });

                    databaseReference.child(USUARIO_NODE).child(PADRE_NODE).child(id_padre_medico).child(HIJO_NODE).child(id_hijo_medico).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()){
                                nombreHijo.setText(String.valueOf(dataSnapshot.child("Nombre").getValue()));
                                edadHijo.setText(String.valueOf(dataSnapshot.child("Fecha de nacimiento").getValue()));
                                //fechaActualizacion.setText(String.valueOf(dataSnapshot.child("Ultima actualizacion").getValue()));
                                fechaActualizacion.setText("null");
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            nombreHijo.setText("Null");
                            edadHijo.setText("Null");
                            fechaActualizacion.setText("Null");

                        }
                    });
                } else {
                    startActivity(new Intent(InicioMedico2Activity.this, InicioMedicoActivity.class));
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
}
