package com.gammadelta.gambitos.Padre;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.gammadelta.gambitos.R;
import com.gammadelta.gambitos.Registro.RegistroMedicoActivity;
import com.gammadelta.gambitos.Registro.RegistroPadreActivity;
import com.gammadelta.gambitos.Registro.RegistroPadreDosActivity;
import com.gammadelta.gambitos.model.Padres;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class InicioPadresActivity extends AppCompatActivity{

    private static final String USUARIO_NODE = "Usuarios";
    private static final String PADRE_NODE = "Padres";
    private static final String TAG = "InicioPadresActicity" ;
    private DatabaseReference databaseReference;

    private ListView lstHijos;
    private ArrayAdapter arrayAdapter;
    private List<String> hijosNombres;

    private FirebaseUser firebaseUser;
    private FirebaseAuth firebaseAuth;

    private TextView nombre_padre;
    private TextView pin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_padres);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        lstHijos = (ListView) findViewById(R.id.lstHijos);
        hijosNombres = new ArrayList<>();
        arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, hijosNombres);
        lstHijos.setAdapter(arrayAdapter);

        nombre_padre = (TextView) findViewById(R.id.nombre_padre);
        pin = (TextView) findViewById(R.id.pin);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        String userID = firebaseAuth.getCurrentUser().getUid();

        databaseReference.child(USUARIO_NODE).child(PADRE_NODE).child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    nombre_padre.setText(String.valueOf(dataSnapshot.child("Nombre").getValue()));
                    pin.setText(String.valueOf(dataSnapshot.child("PIN").getValue()));
                }
                else {
                    nombre_padre.setText("Padre");
                    pin.setText("000000");
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                nombre_padre.setText("Padre");
                pin.setText("000000");
            }
        });

        databaseReference.child(USUARIO_NODE).child(PADRE_NODE).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                hijosNombres.clear();
                if (dataSnapshot.exists()){
                    for (DataSnapshot snapshot:dataSnapshot.getChildren()) {
                        Padres padres = dataSnapshot.getValue(Padres.class);
                        Log.w(TAG, "Nombre hijo: " + padres.getNombre());
                        hijosNombres.add(padres.getNombre());
                    }
                }
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent i = new Intent(InicioPadresActivity.this, RegistroPadreDosActivity.class);
                startActivity(i);
            }
        });
    }


    public void irMenuHijos(View view){
        Intent intent = new Intent(this, HijoMenuActivity.class);
        startActivity(intent);
    }


}
