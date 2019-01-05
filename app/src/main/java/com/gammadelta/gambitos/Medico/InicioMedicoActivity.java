package com.gammadelta.gambitos.Medico;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gammadelta.gambitos.Graficas.GraficasMedicoNinoActivity;
import com.gammadelta.gambitos.Login.IngresarActivity;
import com.gammadelta.gambitos.Login.IngresoMedicoIndependiente;
import com.gammadelta.gambitos.Padre.InicioPadresActivity;
import com.gammadelta.gambitos.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.gammadelta.gambitos.Login.IngresoMedicoIndependiente.documento_medic;

public class InicioMedicoActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String USUARIO_NODE = "Usuarios";
    private static final String PADRE_NODE = "Padres";
    private static final String MEDICO_NODE = "Medicos";
    private static final String TAG = "InicioMedico";
    private static final String HIJO_NODE = "Hijos";
    private DatabaseReference databaseReference;

    private ProgressDialog progressDialog;

    private FirebaseUser firebaseUser;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth firebaseAuth;

    private TextView nombreMedico;
    private CardView curva_crecimiento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_medico);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_medico);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_medico);
        navigationView.setNavigationItemSelectedListener(this);

        nombreMedico        = (TextView)   findViewById(R.id.nombre_medico);
        curva_crecimiento   = (CardView)   findViewById(R.id.curva_crecimiento);

        progressDialog = new ProgressDialog(this);

        curva_crecimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent b = new Intent(InicioMedicoActivity.this, InicioMedico2Activity.class);
                startActivity(b);
            }
        });


        firebaseAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null){
                    databaseReference = FirebaseDatabase.getInstance().getReference();

                    databaseReference.child(USUARIO_NODE).child(MEDICO_NODE).child(documento_medic).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()){
                                String nombre_doc = "";
                                String apellido_doc = "";
                                nombre_doc = dataSnapshot.child("Nombre").getValue().toString();
                                apellido_doc = dataSnapshot.child("Apellido").getValue().toString();
                                nombreMedico.setText(nombre_doc + " " + apellido_doc);
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            nombreMedico.setText("No existe");
                        }
                    });

                } else {
                    startActivity(new Intent(InicioMedicoActivity.this, InicioMedicoActivity.class));
                    finish();
                }
            }
        };
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_editar) {
            // Handle the camera action
        //} else if (id == R.id.nav_editar) {

            //} else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_ajustes) {

            //} else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_cerrar_sesion) {
            Intent o = new Intent(InicioMedicoActivity.this, IngresarActivity.class);
            startActivity(o);
            signOut();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(mAuthListener);
    }

    private void signOut(){
        firebaseAuth.signOut();
        updateUI(null);
    }

    private void updateUI(FirebaseUser currentUser) {
    }
}
