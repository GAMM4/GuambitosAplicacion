package com.gammadelta.gambitos.OlvidoContrasena;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gammadelta.gambitos.Login.IngresarActivity;
import com.gammadelta.gambitos.Login.IngresoMedicoIndependiente;
import com.gammadelta.gambitos.Medico.InicioMedicoActivity;
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

public class ContrasenaPadreUnoActivity extends AppCompatActivity {

    private static final String USUARIO_NODE = "Usuarios";
    private static final String TAG = "ContrasenaPadreUno" ;
    private static final String PADRE_NODE = "Padres";

    private DatabaseReference databaseReference;
    private FirebaseUser firebaseUser;
    private FirebaseAuth firebaseAuth;

    private ProgressDialog progressDialog;

    private ImageView atras;
    private TextView correo;
    private Button verificar;

    public static String id_padre_contrasena = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contrasena_padre_uno);

        atras       = (ImageView) findViewById(R.id.menu_atras);
        correo      = (TextView)  findViewById(R.id.correo_padre);
        verificar   = (Button)    findViewById(R.id.verificar_correo);

        progressDialog = new ProgressDialog(this);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        databaseReference = FirebaseDatabase.getInstance().getReference();

        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        verificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inicio();
            }
        });
    }

    private void inicio(){
        progressDialog.setMessage("Verificando correo...");
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
                            Toast.makeText(ContrasenaPadreUnoActivity.this, "Error de autenticación",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                        progressDialog.dismiss();
                    }
                });
    }

    private void inicioSesion(){
        final String email = correo.getText().toString();

        databaseReference.child(USUARIO_NODE).child(PADRE_NODE).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String emailPadre = "";
                    id_padre_contrasena = snapshot.getKey().toString();

                    if (snapshot.child("Correo").exists()) {
                        emailPadre = snapshot.child("Correo").getValue().toString();
                        if (email.equals(emailPadre)) {

                            Intent a = new Intent(ContrasenaPadreUnoActivity.this, ContrasenaPadreDosActivity.class);
                            startActivity(a);
                            finish();

                        } else {
                            Toast.makeText(ContrasenaPadreUnoActivity.this, "No existe cuenta con el correo", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        //Toast.makeText(ContrasenaPadreUnoActivity.this, "Error Desconocido", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ContrasenaPadreUnoActivity.this, "Error Desconocido", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        updateUI(currentUser);
    };

    private void updateUI(FirebaseUser currentUser) {
    }
}
