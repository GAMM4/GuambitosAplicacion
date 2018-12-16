package com.gammadelta.gambitos.Padre;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.gammadelta.gambitos.Graficas.GraficasPadreNinaActivity;
import com.gammadelta.gambitos.Graficas.GraficasPadreNinoActivity;
import com.gammadelta.gambitos.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.gammadelta.gambitos.Padre.InicioPadresActivity.IDhijo;

public class HijoMenuActivity extends AppCompatActivity {
    private static final String USUARIO_NODE = "Usuarios";
    private static final String PADRE_NODE = "Padres";
    private static final String TAG = "HijoMenuActivity" ;
    private DatabaseReference databaseReference;

    private FirebaseUser firebaseUser;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth firebaseAuth;

    private TextView nombre_hijo;

    private CardView botonGraficaMedico;

    private String genero = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hijo_menu);

        nombre_hijo = (TextView) findViewById(R.id.nombre_hijo);

        botonGraficaMedico = (CardView) findViewById(R.id.botonGraficaMedico);

        botonGraficaMedico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(genero.equals("Niño")){
                    Intent x = new Intent(HijoMenuActivity.this, GraficasPadreNinoActivity.class);
                    startActivity(x);
                } else if (genero.equals("Niña")){
                    Intent x = new Intent(HijoMenuActivity.this, GraficasPadreNinaActivity.class);
                    startActivity(x);
                }

            }
        });


        firebaseAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null){
                    databaseReference = FirebaseDatabase.getInstance().getReference();
                    databaseReference.child(USUARIO_NODE).child(PADRE_NODE).child(firebaseAuth.getCurrentUser().getUid()).child("Hijos").child(IDhijo).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()){
                                nombre_hijo.setText(String.valueOf(dataSnapshot.child("Nombre").getValue()));
                                genero = dataSnapshot.child("Sexo").getValue().toString();
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            nombre_hijo.setText("No existe");
                        }
                    });
                } else {
                    startActivity(new Intent(HijoMenuActivity.this, InicioPadresActivity.class));
                    finish();
                }
            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(mAuthListener);
    }

    public final void volverInicioPadres(View view) {
        finish();
    }
}
