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

import com.gammadelta.gambitos.R;
import com.gammadelta.gambitos.model.Padres;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class RegistroPadreActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String USUARIO_NODE = "Usuarios";
    private static final String PADRE_NODE = "Padres";
    private static final String TAG = "RegistroPadreActivity";
    private DatabaseReference databaseReference;

    private ProgressDialog progressDialog;

    private FirebaseUser firebaseUser;
    private FirebaseAuth firebaseAuth;

    private TextView nombre_padre;
    private TextView correo_padre;
    private TextView crear_contraseña;
    private TextView pregunta_seguridad;
    private TextView respuesta_seguridad;
    private TextView codigo;
    private Button btnSiguiente;

    float pin_creado = (float) (Math.random() * 899998) + 100000;

    /*public RegistroPadreActivity(FirebaseUser firebaseUser) {
        this.firebaseUser = firebaseUser;
    }*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_padre);

        nombre_padre = (TextView) findViewById(R.id.nombre_padre);
        correo_padre = (TextView) findViewById(R.id.correo_padre);
        crear_contraseña = (TextView) findViewById(R.id.crear_contraseña);
        pregunta_seguridad = (TextView) findViewById(R.id.pregunta_seguridad);
        respuesta_seguridad = (TextView) findViewById(R.id.respuesta_pregunta_seguridad);
        codigo = (TextView) findViewById(R.id.codigo);
        btnSiguiente = (Button) findViewById(R.id.boton_siguiente1);

        progressDialog = new ProgressDialog(this);

        btnSiguiente.setOnClickListener((View.OnClickListener) this);

        codigo.setText("PIN: " + String.format("%.0f",pin_creado));

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    private void registrarPadre(){
        final String nombre = nombre_padre.getText().toString().trim();
        final String correo = correo_padre.getText().toString().trim();
        final String contraseña = crear_contraseña.getText().toString().trim();
        final String pregunta = pregunta_seguridad.getText().toString().trim();
        final String respuesta = respuesta_seguridad.getText().toString().trim();
        final String pin = String.format("%.0f",pin_creado);




        //Verificar que los campos no esten vacios
        if(TextUtils.isEmpty(correo) && TextUtils.isEmpty(nombre) &&
                TextUtils.isEmpty(contraseña) && TextUtils.isEmpty(pregunta) && TextUtils.isEmpty(respuesta)){
            Toast.makeText(this,"Se deben completar todos los campos",Toast.LENGTH_LONG).show();
            return;
        }


        progressDialog.setMessage("Creando usuario...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(correo, contraseña)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            String userID = firebaseAuth.getCurrentUser().getUid();
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            updateUI(user);
                            databaseReference.child(USUARIO_NODE).child(PADRE_NODE).child(userID).child("Nombre").setValue(nombre);
                            databaseReference.child(USUARIO_NODE).child(PADRE_NODE).child(userID).child("Pregunta").setValue(pregunta);
                            databaseReference.child(USUARIO_NODE).child(PADRE_NODE).child(userID).child("Respuesta").setValue(respuesta);
                            databaseReference.child(USUARIO_NODE).child(PADRE_NODE).child(userID).child("Padre").setValue(true);
                            databaseReference.child(USUARIO_NODE).child(PADRE_NODE).child(userID).child("PIN").setValue(pin);
                            databaseReference.child(USUARIO_NODE).child(PADRE_NODE).child(userID).child("Correo").setValue(correo);
                            databaseReference.child(USUARIO_NODE).child(PADRE_NODE).child(userID).child("Contraseña").setValue(contraseña);

                            Intent i = new Intent(RegistroPadreActivity.this, RegistroPadreDosActivity.class);
                            startActivity(i);

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegistroPadreActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        progressDialog.dismiss();
                    }
                });
    }

    /*public void crearPadre(View view){
        Intent i = new Intent(this, RegistroPadre2Activity.class);
        startActivity(i);
    }*/

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        updateUI(currentUser);
    };

    private void updateUI(FirebaseUser currentUser) {
    }

    public void onClick(View view) {
        //Invocamos al metodo:
        registrarPadre();
    }
}
