package com.gammadelta.gambitos.Login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
    private static final String TAG = "InicioPadresActicity" ;
    private DatabaseReference databaseReference;

    private ProgressDialog progressDialog;

    private FirebaseUser firebaseUser;
    private FirebaseAuth firebaseAuth;

    private TextView documento_medico;
    private TextView contrasena_medico;
    private Button ingresar_medico;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingreso_medico_independiente);

        documento_medico = (TextView) findViewById(R.id.documento_identidad_medico);
        contrasena_medico = (TextView) findViewById(R.id.contraseña_id_medico_independiente);
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
        String documento = documento_medico.getText().toString().trim();
        final String contrasena_med =contrasena_medico.getText().toString().trim();

        progressDialog.setMessage("Iniciando sesión...");
        progressDialog.show();

        databaseReference.child(USUARIO_NODE).child(MEDICO_NODE).child(documento).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String contrasena = dataSnapshot.getValue(String.class);
                if (dataSnapshot.exists()){
                    if(contrasena.equals(contrasena_med)==true){
                        firebaseAuth.signInAnonymously()
                                .addOnCompleteListener(IngresoMedicoIndependiente.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            Log.d(TAG, "signInAnonymously:success");
                                            FirebaseUser user = firebaseAuth.getCurrentUser();
                                            updateUI(user);
                                            Intent b = new Intent(IngresoMedicoIndependiente.this, InicioMedicoActivity.class);
                                            startActivity(b);
                                        } else {
                                            // If sign in fails, display a message to the user.
                                            Log.w(TAG, "signInAnonymously:failure", task.getException());
                                            Toast.makeText(IngresoMedicoIndependiente.this, "Error",
                                                    Toast.LENGTH_SHORT).show();
                                            updateUI(null);
                                        }
                                        progressDialog.dismiss();
                                    }
                                });
                    }
                }
                else {
                    progressDialog.dismiss();
                    Toast.makeText(IngresoMedicoIndependiente.this, "Cuenta incorrecta", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(IngresoMedicoIndependiente.this, "Error", Toast.LENGTH_SHORT).show();
                Intent b = new Intent(IngresoMedicoIndependiente.this, IngresoMedicoIndependiente.class);
                startActivity(b);
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
