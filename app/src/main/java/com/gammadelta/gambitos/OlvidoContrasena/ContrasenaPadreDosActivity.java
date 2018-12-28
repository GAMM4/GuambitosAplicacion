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

import com.gammadelta.gambitos.Padre.InicioPadresActivity;
import com.gammadelta.gambitos.R;
import com.gammadelta.gambitos.Registro.RegistroPadreActivity;
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

import static com.gammadelta.gambitos.OlvidoContrasena.ContrasenaPadreUnoActivity.id_padre_contrasena;

public class ContrasenaPadreDosActivity extends AppCompatActivity {


    private static final String USUARIO_NODE = "Usuarios";
    private static final String PADRE_NODE = "Padres";
    private static final String TAG = "ContrasenaPadreDos" ;
    private DatabaseReference databaseReference;

    private ProgressDialog progressDialog;

    private FirebaseUser firebaseUser;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth firebaseAuth;

    private ImageView atras;
    private TextView pregunta;
    private EditText respuesta;
    private Button verificar;

    String respuestaPadre = "";
    public static String contrasena_padre = "";
    public static String correo_padre = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contrasena_padre_dos);

        atras       = (ImageView) findViewById(R.id.menu_atras);
        pregunta    = (TextView) findViewById(R.id.pregunta_seguridad);
        respuesta   = (EditText) findViewById(R.id.respuesta_pregunta_seguridad);
        verificar   = (Button)    findViewById(R.id.verificar_respuesta);

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
                verificacion();
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();
        //FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        databaseReference = FirebaseDatabase.getInstance().getReference();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null){
                    databaseReference = FirebaseDatabase.getInstance().getReference();

                    databaseReference.child(USUARIO_NODE).child(PADRE_NODE).child(id_padre_contrasena).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()){
                                pregunta.setText(String.valueOf(dataSnapshot.child("Pregunta").getValue()));
                                respuestaPadre = dataSnapshot.child("Respuesta").getValue().toString().trim();
                            }
                            else {
                                pregunta.setText("No existe (Error)");
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            pregunta.setText("Error de autenticación");
                        }
                    });

                } else {
                    startActivity(new Intent(ContrasenaPadreDosActivity.this, ContrasenaPadreUnoActivity.class));
                    finish();
                }
            }
        };


    }

    private void verificacion(){
        String respuest = respuesta.getText().toString().trim();

        progressDialog.setMessage("Verificando respuesta...");
        progressDialog.show();

        if(respuest.equals(respuestaPadre)){
            databaseReference.child(USUARIO_NODE).child(PADRE_NODE).child(id_padre_contrasena).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        correo_padre = dataSnapshot.child("Correo").getValue().toString().trim();
                        contrasena_padre = dataSnapshot.child("Contraseña").getValue().toString().trim();
                        Toast.makeText(ContrasenaPadreDosActivity.this, "Respuesta correcta", Toast.LENGTH_SHORT).show();
                        ingresar(correo_padre,contrasena_padre);
                    } else {
                    }
                    progressDialog.dismiss();
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    pregunta.setText("Error de autenticación");
                }
            });
        } else {
            Toast.makeText(ContrasenaPadreDosActivity.this, "Respuesta incorrecta", Toast.LENGTH_SHORT).show();
            respuesta.getText().clear();
            progressDialog.dismiss();
        }
    }

    private void ingresar(String correo, String contrasena){
        firebaseAuth.signInWithEmailAndPassword(correo,contrasena).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Intent i = new Intent(ContrasenaPadreDosActivity.this, ContrasenaPadreTresActivity.class);
                    startActivity(i);
                    finish();
                } else {
                    Toast.makeText(ContrasenaPadreDosActivity.this, "Error en Autenticación", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(mAuthListener);
    }

    private void signOut(){
        firebaseAuth.signOut();
        updateUI(null);
    }

    private void updateUI(FirebaseUser currentUser) {
    }
}
