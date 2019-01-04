package com.gammadelta.gambitos.OlvidoContrasena;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.gammadelta.gambitos.Login.IngresoMedicoIndependiente;
import com.gammadelta.gambitos.Medico.InicioMedicoActivity;
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

public class ContrasenaMedicoUnoActivity extends AppCompatActivity {
    private static final String USUARIO_NODE = "Usuarios";
    private static final String MEDICO_NODE = "Medicos";
    private static final String TAG = "ContrasenaMedicoUno" ;
    private DatabaseReference databaseReference;

    private ProgressDialog progressDialog;

    private FirebaseUser firebaseUser;
    private FirebaseAuth firebaseAuth;
    private ImageView atras;
    private EditText documento_medico;
    private Button verificar;

    public static String documento_medi = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contrasena_medico_uno);

        atras               = (ImageView)   findViewById(R.id.menu_atras);
        documento_medico    = (EditText)    findViewById(R.id.documento_identidad_medico);
        verificar           = (Button)      findViewById(R.id.verificar_documento);

        progressDialog = new ProgressDialog(this);

        firebaseAuth = FirebaseAuth.getInstance();
        //FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        databaseReference = FirebaseDatabase.getInstance().getReference();

        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                //signOut();
            }
        });

        verificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recuperarMedico();
            }
        });
    }

    private void recuperarMedico(){
        progressDialog.setMessage("Buscando médico...");
        progressDialog.show();

        firebaseAuth.signInAnonymously()
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInAnonymously:success");
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            updateUI(user);

                            documendoMedico();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInAnonymously:failure", task.getException());
                            Toast.makeText(ContrasenaMedicoUnoActivity.this, "Error de autenticación",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                        progressDialog.dismiss();
                    }
                });
    }

    private void documendoMedico(){
        final String id_medico = documento_medico.getText().toString();

        databaseReference.child(USUARIO_NODE).child(MEDICO_NODE).child(id_medico).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                        documento_medi = id_medico;

                        Intent b = new Intent(ContrasenaMedicoUnoActivity.this, ContrasenaMedicoDosActivity.class);
                        startActivity(b);
                        finish();
                } else {
                    Toast.makeText(ContrasenaMedicoUnoActivity.this, "Cuenta no existente", Toast.LENGTH_SHORT).show();
                    documento_medico.getText().clear();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ContrasenaMedicoUnoActivity.this, "Error", Toast.LENGTH_SHORT).show();
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

    private void signOut(){
        firebaseAuth.signOut();
        updateUI(null);
    }

    private void updateUI(FirebaseUser currentUser) {
    }
}
