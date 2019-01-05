package com.gammadelta.gambitos.Padre;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.solver.widgets.Snapshot;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.gammadelta.gambitos.Login.IngresarActivity;
import com.gammadelta.gambitos.R;
import com.gammadelta.gambitos.Registro.RegistroMedicoActivity;
import com.gammadelta.gambitos.Registro.RegistroPadreActivity;
import com.gammadelta.gambitos.Registro.RegistroPadreDosActivity;
import com.gammadelta.gambitos.model.Hijos;
import com.gammadelta.gambitos.model.Padres;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class InicioPadresActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

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

    public ArrayList<String> keyHijo = new ArrayList<String>();
    public static String IDhijo = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prueba);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //      .setAction("Action", null).show();
                Intent i = new Intent(InicioPadresActivity.this, RegistroPadreDosActivity.class);
                startActivity(i);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        initialView();
        viewClickListener();
        //loadData();


        nombre_padre        = (TextView) findViewById(R.id.nombre_padre);
        pin                 = (TextView) findViewById(R.id.pin);

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
                int i=0;
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    String nombre = "";
                    String fecha_nacimiento = "";
                    String edad_Actual = "";
                    String ultimaActualizacion = "null";
                    if (snapshot.child("Fecha de nacimiento").exists()){
                        nombre              = snapshot.child("Nombre").getValue().toString();

                        fecha_nacimiento    = snapshot.child("Fecha de nacimiento").getValue().toString();
                        edad_Actual = edadActual(fecha_nacimiento);

                        if(snapshot.child("Fecha de actualizacion").exists()){
                            ultimaActualizacion = snapshot.child("Fecha de actualizacion").getValue().toString();
                        } else {
                            ultimaActualizacion = "---";
                        }

                        myDataset.add(new Hijos(nombre,edad_Actual,ultimaActualizacion));
                        keyHijo.add(i, snapshot.getKey());
                        i++;
                    }
                    else {
                        myDataset.add(new Hijos("Null","Null","Null"));
                        i++;
                    }

                }
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                myDataset.add(new Hijos("null","null","null"));
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.prueba, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_hijo) {
            // Handle the camera action
        } else if (id == R.id.nav_editar) {

        //} else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_ajustes) {

        //} else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_cerrar_sesion) {
            Intent o = new Intent(InicioPadresActivity.this, IngresarActivity.class);
            startActivity(o);
            signOut();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void initialView() {
        //setContentView(R.layout.activity_inicio_padres);

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
                //Toast.makeText(mContext,"ID: " + keyHijo.get(position), Toast.LENGTH_SHORT).show();
                IDhijo = keyHijo.get(position);
                Intent o = new Intent(InicioPadresActivity.this, HijoMenuActivity.class);
                startActivity(o);
            }
        });
    }

    public String edadActual(String fechaNac){
        ArrayList<String> fechaNacimiento;
        ArrayList<String> fechaActual;

        Date hoy = new Date();
        DateFormat fechaHoy = new SimpleDateFormat("dd/MM/yyyy");
        String fecha = fechaHoy.format(hoy);

        fechaNacimiento = Lists.newArrayList(Splitter.on('/').trimResults().omitEmptyStrings().splitToList(fechaNac));
        fechaActual     = Lists.newArrayList(Splitter.on('/').trimResults().omitEmptyStrings().splitToList(fecha));

        Integer anoNac = Integer.valueOf(fechaNacimiento.get(2));
        Integer mesNac = Integer.valueOf(fechaNacimiento.get(1));
        Integer diaNac = Integer.valueOf(fechaNacimiento.get(0));

        Integer anoHoy = Integer.valueOf(fechaActual.get(2));
        Integer mesHoy = Integer.valueOf(fechaActual.get(1));
        Integer diaHoy = Integer.valueOf(fechaActual.get(0));

        int ano;
        int mes;
        int dia;

        if(mesHoy >= mesNac){
            ano = anoHoy - anoNac;
            mes = mesHoy - mesNac;
        } else {
            ano = anoHoy - anoNac - 1;
            mes = mesHoy + (12 - mesNac);
        }
        if(diaHoy >= diaNac){
            dia = diaHoy - diaNac;
        } else{
            mes = mes - 1;
            dia = diaHoy + (30 - diaNac);
        }

        String edadActual = "";
        if (ano > 1){
            if(mes > 1 && dia > 1){
                edadActual = ano + " años, " + mes + " meses y " + dia + " días";
            } else if(mes > 1 && dia == 1){
                edadActual = ano + " años, " + mes + " meses y " + dia + " día";
            } else if(mes > 1 && dia == 0){
                edadActual = ano + " años, " + mes + " meses";
            } else if(mes == 1 && dia > 1){
                edadActual = ano + " años, " + mes + " mes y " + dia + " días";
            } else if(mes == 1 && dia == 1){
                edadActual = ano + " años, " + mes + " mes y " + dia + " día";
            } else if(mes == 1 && dia <= 0){
                edadActual = ano + " años, " + mes + " mes";
            } else if(mes <= 0 && dia > 1){
                edadActual = ano + " años y " + dia + " días";
            } else if(mes <= 0 && dia == 1){
                edadActual = ano + " años y " + dia + " día";
            } else if(mes <= 0 && dia <= 0) {
                edadActual = ano + " años";
            }
        } else if (ano == 1){
            if(mes > 1 && dia > 1){
                edadActual = ano + " año, " + mes + " meses y " + dia + " días";
            } else if(mes > 1 && dia == 1){
                edadActual = ano + " año, " + mes + " meses y " + dia + " día";
            } else if(mes > 1 && dia == 0){
                edadActual = ano + " año, " + mes + " meses";
            } else if(mes == 1 && dia > 1){
                edadActual = ano + " año, " + mes + " mes y " + dia + " días";
            } else if(mes == 1 && dia == 1){
                edadActual = ano + " año, " + mes + " mes y " + dia + " día";
            } else if(mes == 1 && dia <= 0){
                edadActual = ano + " año, " + mes + " mes";
            } else if(mes <= 0 && dia > 1){
                edadActual = ano + " año y " + dia + " días";
            } else if(mes <= 0 && dia == 1){
                edadActual = ano + " año y " + dia + " día";
            } else if(mes <= 0 && dia <= 0) {
                edadActual = ano + " año";
            }
        } else {
            if(mes > 1 && dia > 1){
                edadActual = mes + " meses y " + dia + " días";
            } else if(mes > 1 && dia == 1){
                edadActual = mes + " meses y " + dia + " día";
            } else if(mes > 1 && dia == 0){
                edadActual = mes + " meses";
            } else if(mes == 1 && dia > 1){
                edadActual = mes + " mes y " + dia + " días";
            } else if(mes == 1 && dia == 1){
                edadActual = mes + " mes y " + dia + " día";
            } else if(mes == 1 && dia <= 0){
                edadActual = mes + " mes";
            } else if(mes <= 0 && dia > 1){
                edadActual = dia + " días";
            } else if(mes <= 0 && dia == 1){
                edadActual = dia + " día";
            } else if(mes <= 0 && dia <= 0) {
                edadActual = "Error";
            }
        }
        edadActual = "Edad: " + edadActual;

        return edadActual;
    }

    private void signOut(){
        firebaseAuth.signOut();
        updateUI(null);
    }

    private void updateUI(FirebaseUser currentUser) {
    }

}
