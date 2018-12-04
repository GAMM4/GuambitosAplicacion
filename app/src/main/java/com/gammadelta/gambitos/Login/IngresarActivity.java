package com.gammadelta.gambitos.Login;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gammadelta.gambitos.Padre.InicioPadresActivity;
import com.gammadelta.gambitos.R;
import com.gammadelta.gambitos.Registro.RegistroActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public final class IngresarActivity extends AppCompatActivity {
    private static final String TAG = "IngresarActivity";
    private TextView texto;
    private Typeface coolvetica;

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    private Button btnIngresar;

    private EditText edtCorreo;
    private EditText edtContrasena;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_ingresar);

        String fuente1  = "fuentes/coolvetica.ttf";
        this.coolvetica = Typeface.createFromAsset(this.getAssets(), fuente1);
        this.texto      = (TextView)this.findViewById(R.id.guambitos);

        btnIngresar     = (Button) findViewById(R.id.boton_ingresar);

        edtCorreo    = (EditText) findViewById(R.id.usuario_id);
        edtContrasena   = (EditText) findViewById(R.id.contraseña_id);



        inicialize();

        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ingresar(edtCorreo.getText().toString(),edtContrasena.getText().toString());
            }
        });

    }


    private void inicialize(){
        firebaseAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if (firebaseUser != null){
                    Log.w(TAG, "onAuthStateChanged - signed_in" + firebaseUser.getUid());
                    Log.w(TAG, "onAuthStateChanged - signed_in" + firebaseUser.getEmail());
                } else {
                    Log.w(TAG, "onAuthStateChanged - signed_in");
                }
            }
        };

    }

    private void ingresar(String correo, String contrasena){
        firebaseAuth.signInWithEmailAndPassword(correo,contrasena).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(IngresarActivity.this, "Autenticación Exitosa", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(IngresarActivity.this, InicioPadresActivity.class);
                    startActivity(i);
                    finish();
                } else {
                    Toast.makeText(IngresarActivity.this, "Error en Autenticación", Toast.LENGTH_SHORT).show();
                    edtContrasena.getText().clear();
                }
            }
        });
    }



    @Override
    public void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop(){
        super.onStop();
        firebaseAuth.removeAuthStateListener(authStateListener);
    }

    public final void irRegistro(View view) {
        Intent intent = new Intent((Context)this, RegistroActivity.class);
        this.startActivity(intent);
    }
}
