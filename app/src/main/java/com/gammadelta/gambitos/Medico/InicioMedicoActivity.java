package com.gammadelta.gambitos.Medico;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gammadelta.gambitos.Graficas.GraficasMedicoNinoActivity;
import com.gammadelta.gambitos.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class InicioMedicoActivity extends AppCompatActivity {
    private static final String USUARIO_NODE = "Usuarios";
    private static final String PADRE_NODE = "Padres";
    private static final String TAG = "InicioMedicoActivity";
    private static final String HIJO_NODE = "Hijos";
    private DatabaseReference databaseReference;

    private ProgressDialog progressDialog;

    private FirebaseUser firebaseUser;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth firebaseAuth;

    private EditText id_hijo;
    private EditText pin_padre;
    private Button ingresar_paciente;

    public static String id_padre_medico = "";
    public static String id_hijo_medico = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_medico);

        id_hijo             = (EditText)    findViewById(R.id.documento_identidad_hijo_medico);
        pin_padre           = (EditText)    findViewById(R.id.pin_padre_medico);
        ingresar_paciente   = (Button)      findViewById(R.id.ingresar_paciente);

        progressDialog = new ProgressDialog(this);

        ingresar_paciente.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ingresarHijo();
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();
    }

    private void ingresarHijo(){
        final String id = id_hijo.getText().toString().trim();
        final String pin = pin_padre.getText().toString().trim();

        databaseReference = FirebaseDatabase.getInstance().getReference().child(USUARIO_NODE).child(PADRE_NODE);

        //Verificar que los campos no esten vacios
        if (!TextUtils.isEmpty(id) && !TextUtils.isEmpty(pin)) {

            progressDialog.setMessage("Ubicando hijo...");
            progressDialog.show();

            id_hijo_medico = id;

            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String pin_base = "";
                        id_padre_medico = snapshot.getKey().toString();

                        if (snapshot.child("Hijos").child(id).exists()) {
                            pin_base = snapshot.child("PIN").getValue().toString();
                            if (pin.equals(pin_base)) {
                                //Toast.makeText(InicioMedicoActivity.this, "PIN Correcto " + id_padre_medico, Toast.LENGTH_SHORT).show();

                                Intent a = new Intent(InicioMedicoActivity.this, GraficasMedicoNinoActivity.class);
                                startActivity(a);
                                finish();
                            } else {
                                Toast.makeText(InicioMedicoActivity.this, "PIN Incorrecto", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            //Toast.makeText(InicioMedicoActivity.this, "No existe usuario", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            progressDialog.dismiss();
        }else{
            Toast.makeText(this,"Faltan completar campos",Toast.LENGTH_LONG).show();
        }
        //progressDialog.dismiss();
    }

    @Override
    public void onStart() {
        super.onStart();
        //firebaseAuth.addAuthStateListener(mAuthListener);
    }

}
