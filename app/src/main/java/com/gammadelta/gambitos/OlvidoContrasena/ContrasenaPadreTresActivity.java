package com.gammadelta.gambitos.OlvidoContrasena;

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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gammadelta.gambitos.Login.IngresarActivity;
import com.gammadelta.gambitos.Padre.InicioPadresActivity;
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

import static com.gammadelta.gambitos.OlvidoContrasena.ContrasenaPadreUnoActivity.id_padre_contrasena;

public class ContrasenaPadreTresActivity extends AppCompatActivity {

    private static final String USUARIO_NODE = "Usuarios";
    private static final String PADRE_NODE = "Padres";
    private static final String TAG = "ContrasenaPadreTres" ;
    private DatabaseReference databaseReference;

    private ProgressDialog progressDialog;

    private FirebaseUser firebaseUser;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private ImageView atras;
    private EditText contrasena1;
    private EditText contrasena2;
    private Button guardar;

    public static String contrasena_padre = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contrasena_padre_tres);

        atras       = (ImageView) findViewById(R.id.menu_atras);
        contrasena1 = (EditText)  findViewById(R.id.nueva_contrasena1);
        contrasena2 = (EditText)  findViewById(R.id.nueva_contrasena2);
        guardar     = (Button)    findViewById(R.id.guardar_contraseña);

        progressDialog = new ProgressDialog(this);

        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cambiarContrasena(contrasena1,contrasena2);
            }
        });

    }

    private void cambiarContrasena(EditText contra1, EditText contra2){
        final String contrasena_1 = contra1.getText().toString().trim();
        String contrasena_2 = contra2.getText().toString().trim();

        progressDialog.setMessage("Cambiando contraseña...");
        progressDialog.show();

        if(contrasena_1.equals(contrasena_2)){

            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            final String newContrasena = contrasena_1;

            if(!TextUtils.isEmpty(contrasena_1) && !TextUtils.isEmpty(contrasena_2)){

                user.updatePassword(newContrasena)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    Log.d(TAG, "Usuario actualizo contraseña");
                                    //databaseReference.child(USUARIO_NODE).child(PADRE_NODE).child(id_padre_contrasena).child("Contraseña").setValue(newContrasena);
                                    progressDialog.dismiss();
                                    contrasena_padre = contrasena_1;

                                    databaseReference.child(USUARIO_NODE).child(PADRE_NODE).child(firebaseAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            databaseReference.child(USUARIO_NODE).child(PADRE_NODE).child(firebaseAuth.getCurrentUser().getUid()).child("Contraseña").setValue(contrasena_padre);

                                            Intent p = new Intent(ContrasenaPadreTresActivity.this, IngresarActivity.class);
                                            startActivity(p);
                                            finish();

                                            signOut();
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });
                                    progressDialog.dismiss();
                                }
                                progressDialog.dismiss();
                            }
                        });

            } else {
                Toast.makeText(this,"Faltan completar campos",Toast.LENGTH_LONG).show();
            }

        } else{
            Toast.makeText(this,"Las contraseñas no coinciden",Toast.LENGTH_LONG).show();
            contrasena1.getText().clear();
            contrasena2.getText().clear();
            progressDialog.dismiss();
        }
        progressDialog.dismiss();
    }

    private void signOut(){
        firebaseAuth.signOut();
        updateUI(null);
    }

    @Override
    public void onStart() {
        super.onStart();
        //firebaseAuth.addAuthStateListener(mAuthListener);
    }

    private void updateUI(FirebaseUser currentUser) {
    }
}
