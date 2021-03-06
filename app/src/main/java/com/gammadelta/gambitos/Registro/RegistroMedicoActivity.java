package com.gammadelta.gambitos.Registro;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.gammadelta.gambitos.Login.IngresarActivity;
import com.gammadelta.gambitos.Medico.InicioMedicoActivity;
import com.gammadelta.gambitos.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.gammadelta.gambitos.Login.IngresoMedicoIndependiente.documento_medic;

public class RegistroMedicoActivity extends AppCompatActivity {

    private static final String USUARIO_NODE = "Usuarios";
    private static final String TAG = "RegistroMedicoActivity";
    private static final String MEDICO_NODE = "Medicos";
    private DatabaseReference databaseReference;

    private ProgressDialog progressDialog;

    private FirebaseUser firebaseUser;
    private FirebaseAuth firebaseAuth;

    private TextView nombre_medico;
    private TextView apellido_medico;
    private TextView documento_medico;
    private TextView pregunta_seguridad;
    private TextView respuesta_seguridad;
    private TextView contrasena;
    private Button btnRegistrar;

    boolean Verificacion = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_medico);

        nombre_medico       = (TextView) findViewById(R.id.nombre_medico);
        apellido_medico     = (TextView) findViewById(R.id.apellido_medico);
        documento_medico    = (TextView) findViewById(R.id.documento_identidad_medico);
        contrasena          = (TextView) findViewById(R.id.crear_contraseña);
        btnRegistrar        = (Button)   findViewById(R.id.registro_medico);
        pregunta_seguridad  = (TextView) findViewById(R.id.pregunta_seguridad);
        respuesta_seguridad = (TextView) findViewById(R.id.respuesta_pregunta_seguridad);

        progressDialog = new ProgressDialog(this);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        databaseReference = FirebaseDatabase.getInstance().getReference();

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarMedico();
            }
        });
    }

    private void registrarMedico(){
        final String nombre = nombre_medico.getText().toString().trim();
        final String apellido = apellido_medico.getText().toString().trim();
        final String documento = documento_medico.getText().toString().trim();
        final String contraseña = contrasena.getText().toString().trim();
        final String pregunta = pregunta_seguridad.getText().toString().trim();
        final String respuesta = respuesta_seguridad.getText().toString().trim();

        //Verificar que los campos no esten vacios
        if(!TextUtils.isEmpty(nombre) && !TextUtils.isEmpty(apellido) &&
                !TextUtils.isEmpty(documento) && !TextUtils.isEmpty(contraseña)
                && !TextUtils.isEmpty(pregunta) && !TextUtils.isEmpty(respuesta)){

            progressDialog.setMessage("Creando médico...");
            progressDialog.show();

            firebaseAuth.signInAnonymously()
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "signInAnonymously:success");
                                FirebaseUser user = firebaseAuth.getCurrentUser();
                                updateUI(user);
                                databaseReference.child(USUARIO_NODE).child(MEDICO_NODE).child(documento).child("Nombre").setValue(nombre);
                                databaseReference.child(USUARIO_NODE).child(MEDICO_NODE).child(documento).child("Apellido").setValue(apellido);
                                databaseReference.child(USUARIO_NODE).child(MEDICO_NODE).child(documento).child("Documento identidad").setValue(documento);
                                databaseReference.child(USUARIO_NODE).child(MEDICO_NODE).child(documento).child("Contraseña").setValue(contraseña);
                                databaseReference.child(USUARIO_NODE).child(MEDICO_NODE).child(documento).child("Verificacion").setValue(0);
                                databaseReference.child(USUARIO_NODE).child(MEDICO_NODE).child(documento).child("Pregunta").setValue(pregunta);
                                databaseReference.child(USUARIO_NODE).child(MEDICO_NODE).child(documento).child("Respuesta").setValue(respuesta);
                                documento_medic = documento;
                                Intent i = new Intent(RegistroMedicoActivity.this, InicioMedicoActivity.class);
                                startActivity(i);
                                finish();
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInAnonymously:failure", task.getException());
                                Toast.makeText(RegistroMedicoActivity.this, "Faltan completar datos",
                                        Toast.LENGTH_SHORT).show();
                                updateUI(null);
                            }
                            progressDialog.dismiss();
                        }

                    });

        } else {
            Toast.makeText(this,"Faltan completar campos",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        updateUI(currentUser);
    };

    private void updateUI(FirebaseUser currentUser) {
    }
}
