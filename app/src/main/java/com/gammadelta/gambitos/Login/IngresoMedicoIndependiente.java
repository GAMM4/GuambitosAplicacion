package com.gammadelta.gambitos.Login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gammadelta.gambitos.Medico.InicioMedicoActivity;
import com.gammadelta.gambitos.R;
import com.gammadelta.gambitos.Registro.RegistroMedicoActivity;
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

public class IngresoMedicoIndependiente extends AppCompatActivity {

    private static final String USUARIO_NODE = "Usuarios";
    private static final String MEDICO_NODE = "Medicos";
    private static final String TAG = "IngresoMedicoIndepen" ;
    private DatabaseReference databaseReference;

    private ProgressDialog progressDialog;

    private FirebaseUser firebaseUser;
    private FirebaseAuth firebaseAuth;

    private EditText documento_medico;
    private EditText contrasena_medico;
    private Button ingresar_medico;

    public static String documento_medic = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingreso_medico_independiente);

        documento_medico = (EditText) findViewById(R.id.documento_medico_independiente);
        contrasena_medico = (EditText) findViewById(R.id.contraseña_id_medico_independiente);
        ingresar_medico = (Button) findViewById(R.id.boton_ingresar_medico_independiente);

        progressDialog = new ProgressDialog(this);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        databaseReference = FirebaseDatabase.getInstance().getReference();

        ingresar_medico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inicioMedico();
            }
        });
    }

    private void inicioMedico(){

            progressDialog.setMessage("Iniciando sesión...");
            progressDialog.show();

            firebaseAuth.signInAnonymously()
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "signInAnonymously:success");
                                FirebaseUser user = firebaseAuth.getCurrentUser();
                                updateUI(user);

                                inicioSesion();

                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInAnonymously:failure", task.getException());
                                Toast.makeText(IngresoMedicoIndependiente.this, "Error de autenticación",
                                        Toast.LENGTH_SHORT).show();
                                updateUI(null);
                            }
                            progressDialog.dismiss();
                        }
                    });
    }

    private void inicioSesion(){
        final String id_medico = documento_medico.getText().toString();
        final String contrasena = contrasena_medico.getText().toString();

        databaseReference.child(USUARIO_NODE).child(MEDICO_NODE).child(id_medico).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String contra = "";
                contra = dataSnapshot.child("Contraseña").getValue().toString();
                if (dataSnapshot.exists()){

                    if(contrasena.equals(contra)){
                        //Toast.makeText(IngresoMedicoIndependiente.this, "Cuenta correcta", Toast.LENGTH_SHORT).show();
                        documento_medic = id_medico;
                        Intent b = new Intent(IngresoMedicoIndependiente.this, InicioMedicoActivity.class);
                        startActivity(b);
                        finish();
                    } else{
                        Toast.makeText(IngresoMedicoIndependiente.this, "Cuenta erronea", Toast.LENGTH_SHORT).show();
                        contrasena_medico.getText().clear();
                    }
                } else {
                    Toast.makeText(IngresoMedicoIndependiente.this, "Cuenta incorrecta", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(IngresoMedicoIndependiente.this, "Error", Toast.LENGTH_SHORT).show();
                //Intent b = new Intent(IngresoMedicoIndependiente.this, IngresoMedicoIndependiente.class);
                //startActivity(b);
            }
        });
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
