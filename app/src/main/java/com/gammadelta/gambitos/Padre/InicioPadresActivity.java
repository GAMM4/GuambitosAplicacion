package com.gammadelta.gambitos.Padre;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.solver.widgets.Snapshot;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.gammadelta.gambitos.R;
import com.gammadelta.gambitos.Registro.RegistroMedicoActivity;
import com.gammadelta.gambitos.Registro.RegistroPadreActivity;
import com.gammadelta.gambitos.Registro.RegistroPadreDosActivity;
import com.gammadelta.gambitos.model.Hijos;
import com.gammadelta.gambitos.model.Padres;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class InicioPadresActivity extends AppCompatActivity{

    private static final String USUARIO_NODE = "Usuarios";
    private static final String PADRE_NODE = "Padres";
    private static final String TAG = "InicioPadresActicity" ;
    private DatabaseReference databaseReference;

    private FirebaseUser firebaseUser;
    private FirebaseAuth firebaseAuth;

    private TextView nombre_padre;
    private TextView pin;

    private Context mContext;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Hijos> myDataset;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_inicio_padres);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        initialView();
        viewClickListener();
        //loadData();

        nombre_padre = (TextView) findViewById(R.id.nombre_padre);
        pin = (TextView) findViewById(R.id.pin);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        databaseReference = FirebaseDatabase.getInstance().getReference();

        final String userID = firebaseAuth.getCurrentUser().getUid();

        databaseReference.child(USUARIO_NODE).child(PADRE_NODE).child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    nombre_padre.setText(String.valueOf(dataSnapshot.child("Nombre").getValue()));
                    pin.setText(String.valueOf(dataSnapshot.child("PIN").getValue()));

                    /*String nombre = "";
                    //nombre.toString().valueOf(dataSnapshot.child("Hijos").child("1049657235").child("Nombre").getValue());
                    nombre = dataSnapshot.child("Hijos").child("1049657235").child("Nombre").getValue().toString();
                    myDataset.add(new Hijos(nombre,"null","null"));

                    String nombre2 = "";
                    nombre2.valueOf(dataSnapshot.child("Hijos").child("99051214648").child("Nombre").getValue(String.class));
                    myDataset.add(new Hijos(nombre2,"05/12/1999","Ayer"));*/
                }
                else {
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                nombre_padre.setText("Padre");
                pin.setText("000000");
            }
        });

        databaseReference.child(USUARIO_NODE).child(PADRE_NODE).child(userID).child("Hijos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    myDataset.removeAll(myDataset);
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    String nombre = "";
                    String fecha_nacimiento = "";
                    String ultimaActualizacion = "null";
                    nombre              = snapshot.child("Nombre").getValue().toString();
                    fecha_nacimiento    = snapshot.child("Fecha de nacimiento").getValue().toString();
                    //ultimaActualizacion = snapshot.child("Ultima actualizacion").getValue().toString();
                    myDataset.add(new Hijos(nombre,fecha_nacimiento,ultimaActualizacion));
                }
                mAdapter.notifyDataSetChanged();
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

    private void initialView() {
        setContentView(R.layout.activity_inicio_padres);

        mContext = this;
        mRecyclerView = findViewById(R.id.myRecycler);

        mLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(mLayoutManager);

        myDataset = new ArrayList<>();
        mAdapter = new MyAdapter(myDataset);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void viewClickListener() {
        ((MyAdapter)mAdapter).setOnItemCliclListener(new MyAdapter.ListItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Toast.makeText(mContext,"Position: ", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /*private void loadData() {
        String userID = firebaseAuth.getCurrentUser().getUid();

        databaseReference.child(USUARIO_NODE).child(PADRE_NODE).child(userID).child("Hijos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){

                    //myDataset.removeAll(myDataset);
                    myDataset.add(new Hijos("Emma","23/29","12"));
                    //for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                        //Hijos hijos = snapshot.getValue(Hijos.class);
                        //myDataset.add(hijos);

                    //}
                    mAdapter.notifyDataSetChanged();
                }
                else {
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        databaseReference.child(USUARIO_NODE).child(PADRE_NODE).child(userID).child("Hijos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    String nombre="", fecha_nacimiento="", actualizacion="";
                    nombre.valueOf(dataSnapshot.child("Nombre").getValue());

                    myDataset.add(new Hijos("Berlin","null","null"));
                    for(DataSnapshot snapshot:dataSnapshot.child("Nombre").getChildren()){
                        String nombre="", fecha_nacimiento="", actualizacion="";
                        nombre.valueOf(snapshot.getValue());


                        myDataset.add(new Hijos(nombre,"null","null"));
                    }
                    mAdapter.notifyDataSetChanged();
                }
                else {
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }*/

}
