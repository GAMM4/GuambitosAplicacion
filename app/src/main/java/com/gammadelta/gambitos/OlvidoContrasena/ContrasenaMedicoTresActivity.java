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
import android.widget.Toast;

import com.gammadelta.gambitos.Login.IngresarActivity;
import com.gammadelta.gambitos.Login.IngresoMedicoIndependiente;
import com.gammadelta.gambitos.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.gammadelta.gambitos.OlvidoContrasena.ContrasenaMedicoUnoActivity.documento_medi;

public class ContrasenaMedicoTresActivity extends AppCompatActivity {
    private static final String USUARIO_NODE = "Usuarios";
    private static final String MEDICO_NODE = "Medicos";
    private static final String TAG = "ContrasenaMedicoTres";
    private DatabaseReference databaseReference;

    private ProgressDialog progressDialog;

    private FirebaseUser firebaseUser;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth firebaseAuth;

    private ImageView atras;
    private EditText contrasena1;
    private EditText contrasena2;
    private Button guardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contrasena_medico_tres);

        atras       = (ImageView)   findViewById(R.id.menu_atras);
        contrasena1 = (EditText)    findViewById(R.id.nueva_contrasena1);
        contrasena2 = (EditText)    findViewById(R.id.nueva_contrasena2);
        guardar     = (Button)      findViewById(R.id.guardar_contraseña);

        progressDialog = new ProgressDialog(this);

        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                respuestaMedico();
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();
        //FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    private void respuestaMedico(){
        final String contrasena_1 = contrasena1.getText().toString();
        final String contrasena_2 = contrasena2.getText().toString();

        databaseReference.child(USUARIO_NODE).child(MEDICO_NODE).child(documento_medi).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    if(contrasena_1.equals(contrasena_2)){
                        databaseReference.child(USUARIO_NODE).child(MEDICO_NODE).child(documento_medi).child("Contraseña").setValue(contrasena_1);

                        Intent p = new Intent(ContrasenaMedicoTresActivity.this, IngresoMedicoIndependiente.class);
                        startActivity(p);
                        //signOut();
                        finish();
                    } else {
                        Toast.makeText(ContrasenaMedicoTresActivity.this, "La contraseñas no son iguales", Toast.LENGTH_SHORT).show();
                        contrasena1.getText().clear();
                        contrasena2.getText().clear();
                    }

                } else {
                    Toast.makeText(ContrasenaMedicoTresActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    contrasena1.getText().clear();
                    contrasena2.getText().clear();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ContrasenaMedicoTresActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        //firebaseAuth.addAuthStateListener(mAuthListener);
    }

    private void signOut(){
        firebaseAuth.signOut();
        updateUI(null);
    }

    private void updateUI(FirebaseUser currentUser) {
    }
}
