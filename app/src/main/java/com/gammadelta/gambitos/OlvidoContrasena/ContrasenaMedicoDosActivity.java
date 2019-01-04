package com.gammadelta.gambitos.OlvidoContrasena;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gammadelta.gambitos.Medico.InicioMedicoActivity;
import com.gammadelta.gambitos.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.gammadelta.gambitos.OlvidoContrasena.ContrasenaMedicoUnoActivity.documento_medi;


public class ContrasenaMedicoDosActivity extends AppCompatActivity {
    private static final String USUARIO_NODE = "Usuarios";
    private static final String MEDICO_NODE = "Medicos";
    private static final String TAG = "ContrasenaMedicoDos";
    private DatabaseReference databaseReference;

    private ProgressDialog progressDialog;

    private FirebaseUser firebaseUser;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth firebaseAuth;

    private ImageView atras;
    private TextView pregunta;
    private EditText respuesta;
    private Button verificar;

    String respuesta_firebase = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contrasena_medico_dos);

        atras       = (ImageView)   findViewById(R.id.menu_atras);
        pregunta    = (TextView)    findViewById(R.id.pregunta_seguridad);
        respuesta   = (EditText)    findViewById(R.id.respuesta_pregunta_seguridad);
        verificar   = (Button)      findViewById(R.id.verificar_respuesta);

        progressDialog = new ProgressDialog(this);

        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        verificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                respuestaMedico();
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null){
                    databaseReference = FirebaseDatabase.getInstance().getReference();

                    databaseReference.child(USUARIO_NODE).child(MEDICO_NODE).child(documento_medi).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.child("Pregunta").exists()){
                                pregunta.setText(String.valueOf(dataSnapshot.child("Pregunta").getValue()));
                                respuesta_firebase = dataSnapshot.child("Respuesta").getValue().toString().trim();
                            } else {
                                pregunta.setText("Error no pregunta");
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            pregunta.setText("Error no existe pregunta");
                            startActivity(new Intent(ContrasenaMedicoDosActivity.this, ContrasenaMedicoUnoActivity.class));
                            finish();
                        }
                    });

                } else {
                    startActivity(new Intent(ContrasenaMedicoDosActivity.this, ContrasenaMedicoUnoActivity.class));
                    finish();
                }
            }
        };
    }

    private void respuestaMedico(){
        final String respuesta_medico = respuesta.getText().toString();

        databaseReference.child(USUARIO_NODE).child(MEDICO_NODE).child(documento_medi).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    if(respuesta_medico.equals(respuesta_firebase)){
                        Intent b = new Intent(ContrasenaMedicoDosActivity.this, ContrasenaMedicoTresActivity.class);
                        startActivity(b);
                        finish();
                    } else {
                        Toast.makeText(ContrasenaMedicoDosActivity.this, "Respuesta incorrecta", Toast.LENGTH_SHORT).show();
                        respuesta.getText().clear();
                    }
                } else {
                    Toast.makeText(ContrasenaMedicoDosActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    respuesta.getText().clear();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ContrasenaMedicoDosActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(mAuthListener);
    }
}
