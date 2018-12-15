package com.gammadelta.gambitos.Graficas;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.gammadelta.gambitos.Medico.InicioMedicoActivity;
import com.gammadelta.gambitos.R;
import com.gammadelta.gambitos.model.ExpandAndCollapseViewUtil;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static com.gammadelta.gambitos.Login.IngresoMedicoIndependiente.documento_medic;
import static com.gammadelta.gambitos.Medico.InicioMedico2Activity.id_hijo_medico;
import static com.gammadelta.gambitos.Medico.InicioMedico2Activity.id_padre_medico;

public class GraficasMedicoNinaActivity extends AppCompatActivity {
    private static final String USUARIO_NODE = "Usuarios";
    private static final String PADRE_NODE = "Padres";
    private static final String MEDICO_NODE = "Medicos";
    private static final String TAG = "GraficasMedicoNina";
    private static final String HIJO_NODE = "Hijos";
    private DatabaseReference databaseReference;

    private FirebaseUser firebaseUser;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth firebaseAuth;

    private ProgressDialog progressDialog;

    public ArrayList<String> pesoEdad;
    public ArrayList<String> longitudEdad;
    public ArrayList<String> cabezaEdad;
    public ArrayList<String> IMEEdad;

    LineGraphSeries<DataPoint> pointPeso = new LineGraphSeries<>();
    LineGraphSeries<DataPoint> pointLongitud = new LineGraphSeries<>();
    LineGraphSeries<DataPoint> pointCabeza = new LineGraphSeries<>();
    LineGraphSeries<DataPoint> pointIMC = new LineGraphSeries<>();

    private TextView nombreMedico;
    private TextView nombreHijo;
    private TextView edadHijo;
    private TextView fechaActualizacion;
    private Button   cerrarSesionMedico;

    private Button datoPeso;
    private Button datoLongitud;
    private Button datoCabeza;
    private Button datoIMC;
    private TextView ultimoPeso;
    private TextView ultimoLongitud;
    private TextView ultimoCabeza;
    private TextView ultimoIMC;

    private ViewGroup detallePesoOnClic;
    private ViewGroup detalleLongitudOnClic;
    private ViewGroup detalleCabezaOnClic;
    private ViewGroup detalleIMCOnClic;
    private ViewGroup detallePeso;
    private ViewGroup detalleLongitud;
    private ViewGroup detalleCabeza;
    private ViewGroup detalleIMC;
    private ImageView botonPeso;
    private ImageView botonLongitud;
    private ImageView botonCabeza;
    private ImageView botonIMC;
    private TextView  historialPeso;
    private TextView  historialLongitud;
    private TextView  historialCabeza;
    private TextView  historialIMC;
    private TextView  historialPeso_fecha;
    private TextView  historialLongitud_fecha;
    private TextView  historialCabeza_fecha;
    private TextView  historialIMC_fecha;


    private static final String CERO = "0";
    private static final String BARRA = "/";
    public final Calendar c = Calendar.getInstance();
    final int dia = c.get(Calendar.DAY_OF_MONTH);
    final int mes = c.get(Calendar.MONTH);
    final int anio = c.get(Calendar.YEAR);

    boolean datosI_PesoEdad = false;
    boolean datosI_LongitudEdad = false;
    boolean datosI_CabezaEdad = false;
    boolean datosI_IMCEdad = false;
    String Firebase_PesoEdad = "";
    String Firebase_LongitudEdad = "";
    String Firebase_CabezaEdad = "";
    String Firebase_IMCEdad = "";

    boolean cierre = false;
    public String fecha_nacimiento = "";
    private static final int DURATION = 250;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graficas_medico_nina);

        nombreMedico        = (TextView)    findViewById(R.id.nombre_medico);
        nombreHijo          = (TextView)    findViewById(R.id.nombre_hijo);
        edadHijo            = (TextView)    findViewById(R.id.edad_hijo);
        fechaActualizacion  = (TextView)    findViewById(R.id.fecha_ultimoRegistro);
        cerrarSesionMedico  = (Button)      findViewById(R.id.boton_cerrarsesion_medico);

        datoPeso        = (Button) findViewById(R.id.dato_P);
        datoLongitud    = (Button) findViewById(R.id.dato_L);
        datoCabeza      = (Button) findViewById(R.id.dato_C);
        datoIMC         = (Button) findViewById(R.id.dato_IMC);
        ultimoPeso      = (TextView) findViewById(R.id.ultimo_P);
        ultimoLongitud  = (TextView) findViewById(R.id.ultimo_L);
        ultimoCabeza    = (TextView) findViewById(R.id.ultimo_C);
        ultimoIMC    = (TextView) findViewById(R.id.ultimo_IMC);

        detallePesoOnClic   = (ViewGroup) findViewById(R.id.detallePesoOnClic);
        detallePeso         = (ViewGroup) findViewById(R.id.detallePeso);
        botonPeso           = (ImageView) findViewById(R.id.botonPeso);
        historialPeso       = (TextView)  findViewById(R.id.historialPeso);
        historialPeso_fecha = (TextView)  findViewById(R.id.historialPeso_Fecha);

        detalleLongitudOnClic   = (ViewGroup) findViewById(R.id.detalleLongitudOnClic);
        detalleLongitud         = (ViewGroup) findViewById(R.id.detalleLongitud);
        botonLongitud           = (ImageView) findViewById(R.id.botonLongitud);
        historialLongitud       = (TextView)  findViewById(R.id.historialLongitud);
        historialLongitud_fecha = (TextView)  findViewById(R.id.historialLongitud_Fecha);

        detalleCabezaOnClic   = (ViewGroup) findViewById(R.id.detalleCabezaOnClic);
        detalleCabeza         = (ViewGroup) findViewById(R.id.detalleCabeza);
        botonCabeza           = (ImageView) findViewById(R.id.botonCabeza);
        historialCabeza       = (TextView)  findViewById(R.id.historialCabeza);
        historialCabeza_fecha = (TextView)  findViewById(R.id.historialCabeza_Fecha);

        detalleIMCOnClic   = (ViewGroup) findViewById(R.id.detalleIMCOnClic);
        detalleIMC         = (ViewGroup) findViewById(R.id.detalleIMC);
        botonIMC           = (ImageView) findViewById(R.id.botonIMC);
        historialIMC       = (TextView)  findViewById(R.id.historialIMC);
        historialIMC_fecha = (TextView)  findViewById(R.id.historialIMC_Fecha);

        progressDialog = new ProgressDialog(this);

        grafica_A_PvsE();
        grafica_A_LvsE();
        //grafica_A_LvsP();
        //grafica_A_LvsP2();
        grafica_A_IMCvsE();
        grafica_A_CvsE();

        cerrarSesionMedico.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent x = new Intent(GraficasMedicoNinaActivity.this, InicioMedicoActivity.class);
                startActivity(x);
                finish();
            }
        });

        detallePesoOnClic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (detallePeso.getVisibility() == View.GONE) {
                    ExpandAndCollapseViewUtil.expand(detallePeso, DURATION);
                    botonPeso.setImageResource(R.drawable.flecha_ampliar);
                    rotate(-180.0f,botonPeso);
                } else {
                    ExpandAndCollapseViewUtil.collapse(detallePeso, DURATION);
                    botonPeso.setImageResource(R.drawable.flecha_reducir);
                    rotate(180.0f,botonPeso);
                }
            }
        });

        detalleLongitudOnClic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (detalleLongitud.getVisibility() == View.GONE) {
                    ExpandAndCollapseViewUtil.expand(detalleLongitud, DURATION);
                    botonLongitud.setImageResource(R.drawable.flecha_ampliar);
                    rotate(-180.0f,botonLongitud);
                } else {
                    ExpandAndCollapseViewUtil.collapse(detalleLongitud, DURATION);
                    botonLongitud.setImageResource(R.drawable.flecha_reducir);
                    rotate(180.0f,botonLongitud);
                }
            }
        });

        detalleCabezaOnClic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (detalleCabeza.getVisibility() == View.GONE) {
                    ExpandAndCollapseViewUtil.expand(detalleCabeza, DURATION);
                    botonCabeza.setImageResource(R.drawable.flecha_ampliar);
                    rotate(-180.0f,botonCabeza);
                } else {
                    ExpandAndCollapseViewUtil.collapse(detalleCabeza, DURATION);
                    botonCabeza.setImageResource(R.drawable.flecha_reducir);
                    rotate(180.0f,botonCabeza);
                }
            }
        });

        detalleIMCOnClic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (detalleIMC.getVisibility() == View.GONE) {
                    ExpandAndCollapseViewUtil.expand(detalleIMC, DURATION);
                    botonIMC.setImageResource(R.drawable.flecha_ampliar);
                    rotate(-180.0f,botonIMC);
                } else {
                    ExpandAndCollapseViewUtil.collapse(detalleIMC, DURATION);
                    botonIMC.setImageResource(R.drawable.flecha_reducir);
                    rotate(180.0f,botonIMC);
                }
            }
        });

        datoPeso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogPeso();
            }
        });

        datoLongitud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogLongitud();
            }
        });

        datoCabeza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogCabeza();
            }
        });

        datoIMC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogIMC();
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

                    databaseReference.child(USUARIO_NODE).child(PADRE_NODE).child(id_padre_medico).child(HIJO_NODE).child(id_hijo_medico).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()){
                                nombreHijo.setText(String.valueOf(dataSnapshot.child("Nombre").getValue()));
                                edadHijo.setText(String.valueOf(dataSnapshot.child("Fecha de nacimiento").getValue()));
                                fecha_nacimiento = dataSnapshot.child("Fecha de nacimiento").getValue().toString();
                                //fechaActualizacion.setText(String.valueOf("Es" + fecha_nacimiento));
                                //fechaActualizacion.setText("Niña");
                            }
                            if (dataSnapshot.child("PesoEdad").exists() && (datosI_PesoEdad == false)) {
                                String datosGraficas = "";
                                datosGraficas = dataSnapshot.child("PesoEdad").getValue().toString();

                                pesoEdad = Lists.newArrayList(Splitter.on(',').trimResults().omitEmptyStrings().splitToList(datosGraficas));
                                for (int i = 0; i < pesoEdad.size(); i = i + 2) {
                                    int o = i + 1;

                                    double X = restarFechas(fecha_nacimiento,pesoEdad.get(i));
                                    //double X = Double.parseDouble(pesoEdad.get(i));
                                    double Y = Double.parseDouble(pesoEdad.get(o));

                                    pointPeso.appendData(new DataPoint(X, Y), true, 1000);
                                }
                                String fecha = String.valueOf(pesoEdad.get(pesoEdad.size()-2));
                                String punto = String.valueOf(pesoEdad.get(pesoEdad.size()-1));
                                ultimoPeso.setText("Peso: " + punto + "kg" + "\n" + "Fecha: " + fecha);

                                String historiaDato = "";
                                String historiaFecha = "";
                                for (int i = pesoEdad.size()-1; i >= 0; i = i - 2) {
                                    int o = i - 1;
                                    historiaDato = historiaDato + String.valueOf(pesoEdad.get(i)) + "kg" +"\n";
                                    historiaFecha = historiaFecha + String.valueOf(pesoEdad.get(o)) +"\n";
                                }
                                historialPeso.setText(historiaDato);
                                historialPeso_fecha.setText(historiaFecha);

                                pesoEdad.clear();
                                datosI_PesoEdad = true;
                            }
                            if (dataSnapshot.child("LongitudEdad").exists() && (datosI_LongitudEdad == false)) {
                                String datosGraficas = "";
                                datosGraficas = dataSnapshot.child("LongitudEdad").getValue().toString();

                                longitudEdad = Lists.newArrayList(Splitter.on(',').trimResults().omitEmptyStrings().splitToList(datosGraficas));
                                for (int i = 0; i < longitudEdad.size(); i = i + 2) {
                                    int o = i + 1;

                                    double X = restarFechas(fecha_nacimiento,longitudEdad.get(i));
                                    //double X = Double.parseDouble(longitudEdad.get(i));
                                    double Y = Double.parseDouble(longitudEdad.get(o));

                                    pointLongitud.appendData(new DataPoint(X, Y), true, 1000);
                                }
                                String fecha = String.valueOf(longitudEdad.get(longitudEdad.size()-2));
                                String punto = String.valueOf(longitudEdad.get(longitudEdad.size()-1));
                                ultimoLongitud.setText("Longitud: " + punto + "cm" +"\n" + "Fecha: " + fecha);

                                String historiaDato = "";
                                String historiaFecha = "";
                                for (int i = longitudEdad.size()-1; i >= 0; i = i - 2) {
                                    int o = i - 1;
                                    historiaDato = historiaDato + String.valueOf(longitudEdad.get(i)) + "cm" +"\n";
                                    historiaFecha = historiaFecha + String.valueOf(longitudEdad.get(o)) +"\n";
                                }
                                historialLongitud.setText(historiaDato);
                                historialLongitud_fecha.setText(historiaFecha);

                                longitudEdad.clear();
                                datosI_LongitudEdad = true;
                            }
                            if (dataSnapshot.child("CabezaEdad").exists() && (datosI_CabezaEdad == false)) {
                                String datosGraficas = "";
                                datosGraficas = dataSnapshot.child("CabezaEdad").getValue().toString();

                                cabezaEdad = Lists.newArrayList(Splitter.on(',').trimResults().omitEmptyStrings().splitToList(datosGraficas));
                                for (int i = 0; i < cabezaEdad.size(); i = i + 2) {
                                    int o = i + 1;

                                    double X = restarFechas(fecha_nacimiento,cabezaEdad.get(i));
                                    //double X = Double.parseDouble(cabezaEdad.get(i));
                                    double Y = Double.parseDouble(cabezaEdad.get(o));

                                    pointCabeza.appendData(new DataPoint(X, Y), true, 1000);
                                }
                                String fecha = String.valueOf(cabezaEdad.get(cabezaEdad.size()-2));
                                String punto = String.valueOf(cabezaEdad.get(cabezaEdad.size()-1));
                                ultimoCabeza.setText("Perímetro: " + punto + "cm" + "\n" + "Fecha: " + fecha);

                                String historiaDato = "";
                                String historiaFecha = "";
                                for (int i = cabezaEdad.size()-1; i >= 0; i = i - 2) {
                                    int o = i - 1;
                                    historiaDato = historiaDato + String.valueOf(cabezaEdad.get(i)) + "cm" +"\n";
                                    historiaFecha = historiaFecha + String.valueOf(cabezaEdad.get(o)) +"\n";
                                }
                                historialCabeza.setText(historiaDato);
                                historialCabeza_fecha.setText(historiaFecha);

                                cabezaEdad.clear();
                                datosI_CabezaEdad = true;
                            }
                            if (dataSnapshot.child("IMCEdad").exists() && (datosI_IMCEdad == false)) {
                                String datosGraficas = "";
                                datosGraficas = dataSnapshot.child("IMCEdad").getValue().toString();

                                IMEEdad = Lists.newArrayList(Splitter.on(',').trimResults().omitEmptyStrings().splitToList(datosGraficas));
                                for (int i = 0; i < IMEEdad.size(); i = i + 2) {
                                    int o = i + 1;

                                    double X = restarFechas(fecha_nacimiento,IMEEdad.get(i));
                                    //double X = Double.parseDouble(IMEEdad.get(i));
                                    double Y = Double.parseDouble(IMEEdad.get(o));

                                    pointIMC.appendData(new DataPoint(X, Y), true, 1000);
                                }
                                String fecha = String.valueOf(IMEEdad.get(IMEEdad.size()-2));
                                double punto = Double.parseDouble(IMEEdad.get(IMEEdad.size()-1));
                                String puntoSalida = String.format("%.2f",punto);
                                ultimoIMC.setText("IMC: " + puntoSalida + "kg/m2" +"\n" + "Fecha: " + fecha);

                                String historiaDato = "";
                                String historiaFecha = "";
                                for (int i = IMEEdad.size()-1; i >= 0; i = i - 2) {
                                    int o = i - 1;
                                    double punto1 = Double.parseDouble(IMEEdad.get(i));
                                    historiaDato = historiaDato + String.format("%.2f",punto1) + "kg/m2" +"\n";
                                    historiaFecha = historiaFecha + String.valueOf(IMEEdad.get(o)) +"\n";
                                }
                                historialIMC.setText(historiaDato);
                                historialIMC_fecha.setText(historiaFecha);

                                IMEEdad.clear();
                                datosI_IMCEdad = true;
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            nombreHijo.setText("Null");
                            edadHijo.setText("Null");
                            fechaActualizacion.setText("Null");

                        }
                    });
                } else {
                    startActivity(new Intent(GraficasMedicoNinaActivity.this, InicioMedicoActivity.class));
                    finish();
                }
            }
        };
    }

    public void showDialogPeso() {

        LayoutInflater layoutInflater = LayoutInflater.from(GraficasMedicoNinaActivity.this);
        View promptView = layoutInflater.inflate(R.layout.input_dialogo, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(GraficasMedicoNinaActivity.this);
        alertDialogBuilder.setView(promptView);

        final TextView dato1 = (TextView) promptView.findViewById(R.id.dato1);
        dato1.setHint("Peso (kg)");

        final TextView fecha = (TextView) promptView.findViewById(R.id.fecha);

        fecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obtenerFecha(fecha);
            }
        });
        alertDialogBuilder.setCancelable(false).setPositiveButton("Crear", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                final String fechaFinal = fecha.getText().toString().trim();
                String Peso = dato1.getText().toString().trim();

                final double X = restarFechas(fecha_nacimiento,fechaFinal);
                double Y = Double.parseDouble(Peso);
                pointPeso.appendData(new DataPoint(X,Y),true,1000);
                String pesoN = dato1.getText().toString().trim();
                Firebase_PesoEdad = Firebase_PesoEdad + "," + fechaFinal + "," + pesoN;

                databaseReference.child(USUARIO_NODE).child(PADRE_NODE).child(id_padre_medico).child(HIJO_NODE).child(id_hijo_medico).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (cierre == false){
                            if (dataSnapshot.child("PesoEdad").exists()){
                                String datosFirebase = "";
                                datosFirebase = dataSnapshot.child("PesoEdad").getValue().toString();
                                Firebase_PesoEdad = datosFirebase + Firebase_PesoEdad;
                                databaseReference.child(USUARIO_NODE).child(PADRE_NODE).child(id_padre_medico).child(HIJO_NODE).child(id_hijo_medico).child("PesoEdad").setValue(Firebase_PesoEdad);
                                cierre = true;
                            } else {
                                String datosFirebase = "";
                                datosFirebase = Firebase_PesoEdad;
                                databaseReference.child(USUARIO_NODE).child(PADRE_NODE).child(id_padre_medico).child(HIJO_NODE).child(id_hijo_medico).child("PesoEdad").setValue(datosFirebase);
                                cierre = true;
                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }
        }).setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        progressDialog.dismiss();
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    public void showDialogLongitud() {

        LayoutInflater layoutInflater = LayoutInflater.from(GraficasMedicoNinaActivity.this);
        View promptView = layoutInflater.inflate(R.layout.input_dialogo, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(GraficasMedicoNinaActivity.this);
        alertDialogBuilder.setView(promptView);

        final TextView dato1 = (TextView) promptView.findViewById(R.id.dato1);
        dato1.setHint("Longitud (cm)");

        final TextView fecha = (TextView) promptView.findViewById(R.id.fecha);

        fecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obtenerFecha(fecha);
            }
        });
        alertDialogBuilder.setCancelable(false).setPositiveButton("Crear", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                final String fechaFinal = fecha.getText().toString().trim();
                String Longitud = dato1.getText().toString().trim();

                final double X = restarFechas(fecha_nacimiento,fechaFinal);
                double Y = Double.parseDouble(Longitud);

                pointLongitud.appendData(new DataPoint(X,Y),true,1000);

                String pesoN = dato1.getText().toString().trim();
                Firebase_LongitudEdad = Firebase_LongitudEdad + "," + fechaFinal + "," + pesoN;

                databaseReference.child(USUARIO_NODE).child(PADRE_NODE).child(id_padre_medico).child(HIJO_NODE).child(id_hijo_medico).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (cierre == false){
                            if (dataSnapshot.child("LongitudEdad").exists()){
                                String datosFirebase = "";
                                datosFirebase = dataSnapshot.child("LongitudEdad").getValue().toString();
                                Firebase_LongitudEdad = datosFirebase + Firebase_LongitudEdad;
                                databaseReference.child(USUARIO_NODE).child(PADRE_NODE).child(id_padre_medico).child(HIJO_NODE).child(id_hijo_medico).child("LongitudEdad").setValue(Firebase_LongitudEdad);
                                cierre = true;
                            } else {
                                String datosFirebase = "";
                                datosFirebase = Firebase_LongitudEdad;
                                databaseReference.child(USUARIO_NODE).child(PADRE_NODE).child(id_padre_medico).child(HIJO_NODE).child(id_hijo_medico).child("LongitudEdad").setValue(datosFirebase);
                                cierre = true;
                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }
        }).setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        progressDialog.dismiss();
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    public void showDialogCabeza() {

        LayoutInflater layoutInflater = LayoutInflater.from(GraficasMedicoNinaActivity.this);
        View promptView = layoutInflater.inflate(R.layout.input_dialogo, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(GraficasMedicoNinaActivity.this);
        alertDialogBuilder.setView(promptView);

        final TextView dato1 = (TextView) promptView.findViewById(R.id.dato1);
        dato1.setHint("Perímetro (cm)");

        final TextView fecha = (TextView) promptView.findViewById(R.id.fecha);

        fecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obtenerFecha(fecha);
            }
        });
        alertDialogBuilder.setCancelable(false).setPositiveButton("Crear", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                final String fechaFinal = fecha.getText().toString().trim();
                String Cabeza = dato1.getText().toString().trim();

                final double X = restarFechas(fecha_nacimiento,fechaFinal);
                double Y = Double.parseDouble(Cabeza);
                pointCabeza.appendData(new DataPoint(X,Y),true,1000);
                String pesoN = dato1.getText().toString().trim();
                Firebase_CabezaEdad = Firebase_CabezaEdad + "," + fechaFinal + "," + pesoN;

                databaseReference.child(USUARIO_NODE).child(PADRE_NODE).child(id_padre_medico).child(HIJO_NODE).child(id_hijo_medico).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (cierre == false){
                            if (dataSnapshot.child("CabezaEdad").exists()){
                                String datosFirebase = "";
                                datosFirebase = dataSnapshot.child("CabezaEdad").getValue().toString();
                                Firebase_CabezaEdad = datosFirebase + Firebase_CabezaEdad;
                                databaseReference.child(USUARIO_NODE).child(PADRE_NODE).child(id_padre_medico).child(HIJO_NODE).child(id_hijo_medico).child("CabezaEdad").setValue(Firebase_CabezaEdad);
                                cierre = true;
                            } else {
                                String datosFirebase = "";
                                datosFirebase = Firebase_CabezaEdad;
                                databaseReference.child(USUARIO_NODE).child(PADRE_NODE).child(id_padre_medico).child(HIJO_NODE).child(id_hijo_medico).child("CabezaEdad").setValue(datosFirebase);
                                cierre = true;
                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }
        }).setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        progressDialog.dismiss();
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    public void showDialogIMC() {

        LayoutInflater layoutInflater = LayoutInflater.from(GraficasMedicoNinaActivity.this);
        View prompView = layoutInflater.inflate(R.layout.input_dialogo_imc, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(GraficasMedicoNinaActivity.this);
        alertDialogBuilder.setView(prompView);

        final TextView dato1 = (TextView) prompView.findViewById(R.id.dato_1);
        dato1.setHint("Peso (kg)");

        final TextView dato2 = (TextView) prompView.findViewById(R.id.dato_2);
        dato2.setHint("Longitud (cm)");

        final TextView resultado = (TextView) prompView.findViewById(R.id.resultado_imc);

        //resultado.setText(String.valueOf(resultadoIMC));

        final TextView fecha = (TextView) prompView.findViewById(R.id.fecha);

        fecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obtenerFecha(fecha);
            }
        });
        alertDialogBuilder.setCancelable(false).setPositiveButton("Crear", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                double resultadoIMC;
                String Peso = dato1.getText().toString().trim();
                String Longitud = dato2.getText().toString().trim();
                Double peso = Double.parseDouble(Peso);
                Double longitud = Double.parseDouble(Longitud);

                if(peso == 0 && longitud == 0) {
                    resultadoIMC = 0;
                } else {
                    resultadoIMC = peso / ((longitud/100) * (longitud/100));
                }

                final String fechaFinal = fecha.getText().toString().trim();
                double X = restarFechas(fecha_nacimiento,fechaFinal);
                double Y = resultadoIMC;
                pointIMC.appendData(new DataPoint(X,Y),true,1000);
                String IMCdato = String.valueOf(resultadoIMC);
                Firebase_IMCEdad = Firebase_IMCEdad + "," + fechaFinal + "," + IMCdato;

                databaseReference.child(USUARIO_NODE).child(PADRE_NODE).child(id_padre_medico).child(HIJO_NODE).child(id_hijo_medico).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (cierre == false){
                            if (dataSnapshot.child("IMCEdad").exists()){
                                String datosFirebase = "";
                                datosFirebase = dataSnapshot.child("IMCEdad").getValue().toString();
                                Firebase_IMCEdad = datosFirebase + Firebase_IMCEdad;
                                databaseReference.child(USUARIO_NODE).child(PADRE_NODE).child(id_padre_medico).child(HIJO_NODE).child(id_hijo_medico).child("IMCEdad").setValue(Firebase_IMCEdad);
                                cierre = true;
                            } else {
                                String datosFirebase = "";
                                datosFirebase = Firebase_IMCEdad;
                                databaseReference.child(USUARIO_NODE).child(PADRE_NODE).child(id_padre_medico).child(HIJO_NODE).child(id_hijo_medico).child("IMCEdad").setValue(datosFirebase);
                                cierre = true;
                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }
        }).setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        progressDialog.dismiss();
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    private void rotate(float angle, ImageView imagen) {
        Animation animation = new RotateAnimation(0.0f, angle, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setFillAfter(true);
        animation.setDuration(DURATION);
        imagen.startAnimation(animation);
    }

    private void obtenerFecha(final TextView fecha){
        DatePickerDialog recogerFecha = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                final int mesActual = month + 1;
                String diaFormateado = (dayOfMonth < 10)? CERO + String.valueOf(dayOfMonth):String.valueOf(dayOfMonth);
                String medFormateado = (mesActual < 10)? CERO + String.valueOf(mesActual):String.valueOf(mesActual);
                fecha.setText(diaFormateado + BARRA + medFormateado + BARRA + year);
            }
        },anio,mes,dia);
        recogerFecha.show();
    }

    public double restarFechas(String Inicio, String Final) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        Date fechaInicial= null;
        try {
            fechaInicial = dateFormat.parse(Inicio);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date fechaFinal= null;
        try {
            fechaFinal = dateFormat.parse(Final);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        int dias=(int) ((fechaFinal.getTime()-fechaInicial.getTime())/86400000);
        double resultado = (double) dias/30;
        return resultado;
        //String meses = Double.toString(mes);
    }

    @Override
    public void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(mAuthListener);
    }


    public void grafica_A_PvsE(){
        final GraphView graph_A_PvsE = (GraphView) findViewById(R.id.graph_A_PvsE);

        LineGraphSeries<DataPoint> median =new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(0,3.2), new DataPoint(1,4.2), new DataPoint(2,5.1),
                new DataPoint(3,5.8), new DataPoint(4,6.4), new DataPoint(5,6.9),
                new DataPoint(6,7.3), new DataPoint(7,7.6), new DataPoint(8,7.9),
                new DataPoint(9,8.2), new DataPoint(10,8.5), new DataPoint(11,8.7),
                new DataPoint(12,8.9), new DataPoint(13,9.2), new DataPoint(14,9.4),
                new DataPoint(15,9.6), new DataPoint(16,9.8), new DataPoint(17,10.0),
                new DataPoint(18,10.2), new DataPoint(19,10.4), new DataPoint(20,10.6),
                new DataPoint(21,10.9), new DataPoint(22,11.1), new DataPoint(23,11.3),
                new DataPoint(24,11.5), new DataPoint(25,11.7), new DataPoint(26,11.9),
                new DataPoint(27,12.1), new DataPoint(28,12.3), new DataPoint(29,12.5),
                new DataPoint(30,12.7), new DataPoint(31,12.9), new DataPoint(32,13.1),
                new DataPoint(33,13.3), new DataPoint(34,13.5), new DataPoint(35,13.7),
                new DataPoint(36,13.9), new DataPoint(37,14.0), new DataPoint(38,14.2),
                new DataPoint(39,14.4), new DataPoint(40,14.6), new DataPoint(41,14.8),
                new DataPoint(42,15.0), new DataPoint(43,15.2), new DataPoint(44,15.3),
                new DataPoint(45,15.5), new DataPoint(46,15.7), new DataPoint(47,15.9),
                new DataPoint(48,16.1), new DataPoint(49,16.3), new DataPoint(50,16.4),
                new DataPoint(51,16.6), new DataPoint(52,16.8), new DataPoint(53,17.0),
                new DataPoint(54,17.2), new DataPoint(55,17.3), new DataPoint(56,17.5),
                new DataPoint(57,17.7), new DataPoint(58,17.9), new DataPoint(59,18.0),
                new DataPoint(60,18.2)
        });
        graph_A_PvsE.addSeries(median);
        median.setColor(Color.GREEN);


        LineGraphSeries<DataPoint> unosd = new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(0,2.8), new DataPoint(1,3.6), new DataPoint(2,4.5),
                new DataPoint(3,5.2), new DataPoint(4,5.7), new DataPoint(5,6.1),
                new DataPoint(6,6.5), new DataPoint(7,6.8), new DataPoint(8,7.0),
                new DataPoint(9,7.3), new DataPoint(10,7.5), new DataPoint(11,7.7),
                new DataPoint(12,7.9), new DataPoint(13,8.1), new DataPoint(14,8.3),
                new DataPoint(15,8.5), new DataPoint(16,8.7), new DataPoint(17,8.9),
                new DataPoint(18,9.1), new DataPoint(19,9.2), new DataPoint(20,9.4),
                new DataPoint(21,9.6), new DataPoint(22,9.8), new DataPoint(23,10.0),
                new DataPoint(24,10.2), new DataPoint(25,10.3), new DataPoint(26,10.5),
                new DataPoint(27,10.7), new DataPoint(28,10.9), new DataPoint(29,11.1),
                new DataPoint(30,11.2), new DataPoint(31,11.4), new DataPoint(32,11.6),
                new DataPoint(33,11.7), new DataPoint(34,11.9), new DataPoint(35,12.0),
                new DataPoint(36,12.2), new DataPoint(37,12.4), new DataPoint(38,12.5),
                new DataPoint(39,12.7), new DataPoint(40,12.8), new DataPoint(41,13.0),
                new DataPoint(42,13.1), new DataPoint(43,13.3), new DataPoint(44,13.4),
                new DataPoint(45,13.6), new DataPoint(46,13.7), new DataPoint(47,13.9),
                new DataPoint(48,14.0), new DataPoint(49,14.2), new DataPoint(50,14.3),
                new DataPoint(51,14.5), new DataPoint(52,14.6), new DataPoint(53,14.8),
                new DataPoint(54,14.9), new DataPoint(55,15.1), new DataPoint(56,15.2),
                new DataPoint(57,15.3), new DataPoint(58,15.5), new DataPoint(59,15.6),
                new DataPoint(60,15.8)
        });
        graph_A_PvsE.addSeries(unosd);
        unosd.setColor(Color.YELLOW);



        LineGraphSeries<DataPoint> dossd = new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(0,2.4), new DataPoint(1,3.2), new DataPoint(2,3.9),
                new DataPoint(3,4.5), new DataPoint(4,5.0), new DataPoint(5,5.4),
                new DataPoint(6,5.7), new DataPoint(7,6.0), new DataPoint(8,6.3),
                new DataPoint(9,6.5), new DataPoint(10,6.7), new DataPoint(11,6.9),
                new DataPoint(12,7.0), new DataPoint(13,7.2), new DataPoint(14,7.4),
                new DataPoint(15,7.6), new DataPoint(16,7.7), new DataPoint(17,7.9),
                new DataPoint(18,8.1), new DataPoint(19,8.2), new DataPoint(20,8.4),
                new DataPoint(21,8.6), new DataPoint(22,8.7), new DataPoint(23,8.9),
                new DataPoint(24,9.0), new DataPoint(25,9.2), new DataPoint(26,9.4),
                new DataPoint(27,9.5), new DataPoint(28,9.7), new DataPoint(29,9.8),
                new DataPoint(30,10.0), new DataPoint(31,10.1), new DataPoint(32,10.3),
                new DataPoint(33,10.4), new DataPoint(34,10.5), new DataPoint(35,10.7),
                new DataPoint(36,10.8), new DataPoint(37,10.9), new DataPoint(38,11.1),
                new DataPoint(39,11.2), new DataPoint(40,11.3), new DataPoint(41,11.5),
                new DataPoint(42,11.6), new DataPoint(43,11.7), new DataPoint(44,11.8),
                new DataPoint(45,12.0), new DataPoint(46,12.1), new DataPoint(47,12.2),
                new DataPoint(48,12.3), new DataPoint(49,12.4), new DataPoint(50,12.6),
                new DataPoint(51,12.7), new DataPoint(52,12.8), new DataPoint(53,12.9),
                new DataPoint(54,13.0), new DataPoint(55,13.2), new DataPoint(56,13.3),
                new DataPoint(57,13.4), new DataPoint(58,13.5), new DataPoint(59,13.6),
                new DataPoint(60,13.7)
        });

        graph_A_PvsE.addSeries(dossd);
        dossd.setColor(Color.rgb(250,105,0));

        LineGraphSeries<DataPoint> tressd = new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(0,2.0), new DataPoint(1,2.7), new DataPoint(2,3.4),
                new DataPoint(3,4.0), new DataPoint(4,4.4), new DataPoint(5,4.8),
                new DataPoint(6,5.1), new DataPoint(7,5.3), new DataPoint(8,5.6),
                new DataPoint(9,5.8), new DataPoint(10,5.9), new DataPoint(11,6.1),
                new DataPoint(12,6.3), new DataPoint(13,6.4), new DataPoint(14,6.6),
                new DataPoint(15,6.7), new DataPoint(16,6.9), new DataPoint(17,7.0),
                new DataPoint(18,7.2), new DataPoint(19,7.3), new DataPoint(20,7.5),
                new DataPoint(21,7.6), new DataPoint(22,7.8), new DataPoint(23,7.9),
                new DataPoint(24,8.1), new DataPoint(25,8.2), new DataPoint(26,8.4),
                new DataPoint(27,8.5), new DataPoint(28,8.6), new DataPoint(29,8.8),
                new DataPoint(30,8.9), new DataPoint(31,9.0), new DataPoint(32,9.1),
                new DataPoint(33,9.3), new DataPoint(34,9.4), new DataPoint(35,9.5),
                new DataPoint(36,9.6), new DataPoint(37,9.7), new DataPoint(38,9.8),
                new DataPoint(39,9.9), new DataPoint(40,10.1), new DataPoint(41,10.2),
                new DataPoint(42,10.3), new DataPoint(43,10.4), new DataPoint(44,10.5),
                new DataPoint(45,10.6), new DataPoint(46,10.7), new DataPoint(47,10.8),
                new DataPoint(48,10.9), new DataPoint(49,11.0), new DataPoint(50,11.1),
                new DataPoint(51,11.2), new DataPoint(52,11.3), new DataPoint(53,11.4),
                new DataPoint(54,11.5), new DataPoint(55,11.6), new DataPoint(56,11.7),
                new DataPoint(57,11.8), new DataPoint(58,11.9), new DataPoint(59,12.0),
                new DataPoint(60,12.1)
        });

        graph_A_PvsE.addSeries(tressd);
        tressd.setColor(Color.RED);

        LineGraphSeries<DataPoint> Unosd=new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(0,3.7), new DataPoint(1,4.8), new DataPoint(2,5.8),
                new DataPoint(3,6.6), new DataPoint(4,7.3), new DataPoint(5,7.8),
                new DataPoint(6,8.3), new DataPoint(7,8.6), new DataPoint(8,9.0),
                new DataPoint(9,9.3), new DataPoint(10,9.6), new DataPoint(11,9.9),
                new DataPoint(12,10.1), new DataPoint(13,10.4), new DataPoint(14,10.6),
                new DataPoint(15,10.9), new DataPoint(16,11.1), new DataPoint(17,11.4),
                new DataPoint(18,11.6), new DataPoint(19,11.8), new DataPoint(20,12.1),
                new DataPoint(21,12.3), new DataPoint(22,12.5), new DataPoint(23,12.8),
                new DataPoint(24,13.0), new DataPoint(25,13.3), new DataPoint(26,13.5),
                new DataPoint(27,13.7), new DataPoint(28,14.0), new DataPoint(29,14.2),
                new DataPoint(30,14.4), new DataPoint(31,14.7), new DataPoint(32,14.9),
                new DataPoint(33,15.1), new DataPoint(34,15.4), new DataPoint(35,15.6),
                new DataPoint(36,15.8), new DataPoint(37,16.0), new DataPoint(38,16.3),
                new DataPoint(39,16.5), new DataPoint(40,16.7), new DataPoint(41,16.9),
                new DataPoint(42,17.2), new DataPoint(43,17.4), new DataPoint(44,17.6),
                new DataPoint(45,17.8), new DataPoint(46,18.1), new DataPoint(47,18.3),
                new DataPoint(48,18.5), new DataPoint(49,18.8), new DataPoint(50,19.0),
                new DataPoint(51,19.2), new DataPoint(52,19.4), new DataPoint(53,19.7),
                new DataPoint(54,19.9), new DataPoint(55,20.1), new DataPoint(56,20.3),
                new DataPoint(57,20.6), new DataPoint(58,20.8), new DataPoint(59,21.0),
                new DataPoint(60,21.2)
        });
        graph_A_PvsE.addSeries(Unosd);
        Unosd.setColor(Color.YELLOW);

        final LineGraphSeries<DataPoint> Dossd= new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(0,4.2), new DataPoint(1,5.5), new DataPoint(2,6.6),
                new DataPoint(3,7.5), new DataPoint(4,8.2), new DataPoint(5,8.8),
                new DataPoint(6,9.3), new DataPoint(7,9.8), new DataPoint(8,10.2),
                new DataPoint(9,10.5), new DataPoint(10,10.9), new DataPoint(11,11.2),
                new DataPoint(12,11.5), new DataPoint(13,11.8), new DataPoint(14,12.1),
                new DataPoint(15,12.4), new DataPoint(16,12.6), new DataPoint(17,12.9),
                new DataPoint(18,13.2), new DataPoint(19,13.5), new DataPoint(20,13.7),
                new DataPoint(21,14.0), new DataPoint(22,14.3), new DataPoint(23,14.6),
                new DataPoint(24,14.8), new DataPoint(25,15.1), new DataPoint(26,15.4),
                new DataPoint(27,15.7), new DataPoint(28,16.0), new DataPoint(29,16.2),
                new DataPoint(30,16.5), new DataPoint(31,16.8), new DataPoint(32,17.1),
                new DataPoint(33,17.3), new DataPoint(34,17.6), new DataPoint(35,17.9),
                new DataPoint(36,18.1), new DataPoint(37,18.4), new DataPoint(38,18.7),
                new DataPoint(39,19.0), new DataPoint(40,19.2), new DataPoint(41,19.5),
                new DataPoint(42,19.8), new DataPoint(43,20.1), new DataPoint(44,20.4),
                new DataPoint(45,20.7), new DataPoint(46,20.9), new DataPoint(47,21.2),
                new DataPoint(48,21.5), new DataPoint(49,21.8), new DataPoint(50,22.1),
                new DataPoint(51,22.4), new DataPoint(52,22.6), new DataPoint(53,22.9),
                new DataPoint(54,23.2), new DataPoint(55,23.5), new DataPoint(56,23.8),
                new DataPoint(57,24.1), new DataPoint(58,24.4), new DataPoint(59,24.6),
                new DataPoint(60,24.9)
        });
        graph_A_PvsE.addSeries(Dossd);
        Dossd.setColor(Color.rgb(250,105,0));

        LineGraphSeries<DataPoint> Tressd = new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(0,4.8), new DataPoint(1,6.2), new DataPoint(2,7.5),
                new DataPoint(3,8.5), new DataPoint(4,9.3), new DataPoint(5,10.0),
                new DataPoint(6,10.6), new DataPoint(7,11.1), new DataPoint(8,11.6),
                new DataPoint(9,12.0), new DataPoint(10,12.4), new DataPoint(11,12.8),
                new DataPoint(12,13.1), new DataPoint(13,13.5), new DataPoint(14,13.8),
                new DataPoint(15,14.1), new DataPoint(16,14.5), new DataPoint(17,14.8),
                new DataPoint(18,15.1), new DataPoint(19,15.4), new DataPoint(20,15.7),
                new DataPoint(21,16.0), new DataPoint(22,16.4), new DataPoint(23,16.7),
                new DataPoint(24,17.0), new DataPoint(25,17.3), new DataPoint(26,17.7),
                new DataPoint(27,18.0), new DataPoint(28,18.3), new DataPoint(29,18.7),
                new DataPoint(30,19.0), new DataPoint(31,19.3), new DataPoint(32,19.6),
                new DataPoint(33,20.0), new DataPoint(34,20.3), new DataPoint(35,20.6),
                new DataPoint(36,20.9), new DataPoint(37,21.3), new DataPoint(38,21.6),
                new DataPoint(39,22.0), new DataPoint(40,22.3), new DataPoint(41,22.7),
                new DataPoint(42,23.0), new DataPoint(43,23.4), new DataPoint(44,23.7),
                new DataPoint(45,24.1), new DataPoint(46,24.5), new DataPoint(47,24.8),
                new DataPoint(48,25.2), new DataPoint(49,25.5), new DataPoint(50,25.9),
                new DataPoint(51,26.3), new DataPoint(52,26.6), new DataPoint(53,27.0),
                new DataPoint(54,27.4), new DataPoint(55,27.7), new DataPoint(56,28.1),
                new DataPoint(57,28.5), new DataPoint(58,28.8), new DataPoint(59,29.2),
                new DataPoint(60,29.5)
        });
        graph_A_PvsE.addSeries(Tressd);
        Tressd.setColor(Color.RED);

        //------------------------------

        graph_A_PvsE.addSeries(pointPeso);
        pointPeso.setDrawDataPoints(true);
        pointPeso.setDataPointsRadius(8);
        pointPeso.setColor(Color.rgb(21,43,60));

        //------------------------------

        graph_A_PvsE.setTitle("kg - meses");
        graph_A_PvsE.setTitleColor(Color.rgb(117,117,117));

        graph_A_PvsE.getGridLabelRenderer().getGridStyle().drawHorizontal();

        graph_A_PvsE.getViewport().setYAxisBoundsManual(true);
        graph_A_PvsE.getViewport().setMinY(2);
        graph_A_PvsE.getViewport().setMaxY(30);

        graph_A_PvsE.getViewport().setXAxisBoundsManual(true);
        graph_A_PvsE.getViewport().setMinX(0);
        graph_A_PvsE.getViewport().setMaxX(61);
    }
    public void grafica_A_LvsE(){
        GraphView graph_A_LvsE = (GraphView) findViewById(R.id.graph_A_LvsE);

        LineGraphSeries<DataPoint> median = new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(0,49.1),new DataPoint(1,53.7),new DataPoint(2,57.1),
                new DataPoint(3,59.8),new DataPoint(4,62.1),new DataPoint(5,64),
                new DataPoint(6,65.7),new DataPoint(7,67.3),new DataPoint(8,68.7),
                new DataPoint(9,70.1),new DataPoint(10,71.5),new DataPoint(11,72.8),
                new DataPoint(12,74),new DataPoint(13,75.2),new DataPoint(14,76.4),
                new DataPoint(15,77.5),new DataPoint(16,78.6),new DataPoint(17,79.7),
                new DataPoint(18,80.7),new DataPoint(19,81.7),new DataPoint(20,82.7),
                new DataPoint(21,83.7),new DataPoint(22,84.6),new DataPoint(23,85.5),
                new DataPoint(24,86.4),new DataPoint(25,86.6),new DataPoint(26,87.4),
                new DataPoint(27,88.3),new DataPoint(28,89.1),new DataPoint(29,89.9),
                new DataPoint(30,90.7),new DataPoint(31,91.4),new DataPoint(32,92.2),
                new DataPoint(33,92.9),new DataPoint(34,93.6),new DataPoint(35,94.4),
                new DataPoint(36,95.1),new DataPoint(37,95.7),new DataPoint(38,96.4),
                new DataPoint(39,97.1),new DataPoint(40,97.7),new DataPoint(41,98.4),
                new DataPoint(42,99),new DataPoint(43,99.7),new DataPoint(44,100.3),
                new DataPoint(45,100.9),new DataPoint(46,101.5),new DataPoint(47,102.1),
                new DataPoint(48,102.7),new DataPoint(49,103.3),new DataPoint(50,103.9),
                new DataPoint(51,104.5),new DataPoint(52,105),new DataPoint(53,105.6),
                new DataPoint(54,106.2),new DataPoint(55,106.7),new DataPoint(56,107.3),
                new DataPoint(57,107.8),new DataPoint(58,108.4),new DataPoint(59,108.9),
                new DataPoint(60,109.4)
        });
        graph_A_LvsE.addSeries(median);
        median.setColor(Color.GREEN);

        LineGraphSeries<DataPoint> unosd = new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(0,47.3),new DataPoint(1,51.7),new DataPoint(2,55),
                new DataPoint(3,57.7),new DataPoint(4,59.9),new DataPoint(5,61.8),
                new DataPoint(6,63.5),new DataPoint(7,65),new DataPoint(8,66.4),
                new DataPoint(9,67.7),new DataPoint(10,69),new DataPoint(11,70.3),
                new DataPoint(12,71.4),new DataPoint(13,72.6),new DataPoint(14,73.7),
                new DataPoint(15,74.8),new DataPoint(16,75.8),new DataPoint(17,76.8),
                new DataPoint(18,77.8),new DataPoint(19,78.8),new DataPoint(20,79.7),
                new DataPoint(21,80.6),new DataPoint(22,81.5),new DataPoint(23,82.3),
                new DataPoint(24,83.2),new DataPoint(25,83.3),new DataPoint(26,84.1),
                new DataPoint(27,84.9),new DataPoint(28,85.7),new DataPoint(29,86.4),
                new DataPoint(30,87.1),new DataPoint(31,87.9),new DataPoint(32,88.6),
                new DataPoint(33,89.3),new DataPoint(34,89.9),new DataPoint(35,90.6),
                new DataPoint(36,91.2),new DataPoint(37,91.9),new DataPoint(38,92.5),
                new DataPoint(39,93.1),new DataPoint(40,93.8),new DataPoint(41,94.4),
                new DataPoint(42,95),new DataPoint(43,95.6),new DataPoint(44,96.2),
                new DataPoint(45,96.7),new DataPoint(46,97.3),new DataPoint(47,97.9),
                new DataPoint(48,98.4),new DataPoint(49,99),new DataPoint(50,99.5),
                new DataPoint(51,100.1),new DataPoint(52,100.6),new DataPoint(53,101.1),
                new DataPoint(54,101.6),new DataPoint(55,102.2),new DataPoint(56,102.7),
                new DataPoint(57,103.2),new DataPoint(58,103.7),new DataPoint(59,104.2),
                new DataPoint(60,104.7)
        });
        graph_A_LvsE.addSeries(unosd);
        unosd.setColor(Color.YELLOW);


        LineGraphSeries<DataPoint> dossd = new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(0,45.4),new DataPoint(1,49.8),new DataPoint(2,53),
                new DataPoint(3,55.6),new DataPoint(4,57.8),new DataPoint(5,59.6),
                new DataPoint(6,61.2),new DataPoint(7,62.7),new DataPoint(8,64),
                new DataPoint(9,65.3),new DataPoint(10,66.5),new DataPoint(11,67.7),
                new DataPoint(12,68.9),new DataPoint(13,70),new DataPoint(14,71),
                new DataPoint(15,72),new DataPoint(16,73),new DataPoint(17,74),
                new DataPoint(18,74.9),new DataPoint(19,75.8),new DataPoint(20,76.7),
                new DataPoint(21,77.5),new DataPoint(22,78.4),new DataPoint(23,79.2),
                new DataPoint(24,79.8),new DataPoint(25,80),new DataPoint(26,80.8),
                new DataPoint(27,81.5),new DataPoint(28,82.2),new DataPoint(29,82.9),
                new DataPoint(30,83.6),new DataPoint(31,84.3),new DataPoint(32,84.9),
                new DataPoint(33,85.6),new DataPoint(34,86.2),new DataPoint(35,86.8),
                new DataPoint(36,87.4),new DataPoint(37,88),new DataPoint(38,88.6),
                new DataPoint(39,89.2),new DataPoint(40,89.8),new DataPoint(41,90.4),
                new DataPoint(42,90.9),new DataPoint(43,91.3),new DataPoint(44,92),
                new DataPoint(45,92.5),new DataPoint(46,93.1),new DataPoint(47,93.6),
                new DataPoint(48,94.1),new DataPoint(49,94.6),new DataPoint(50,95.1),
                new DataPoint(51,95.6),new DataPoint(52,96.1),new DataPoint(53,96.6),
                new DataPoint(54,97.1),new DataPoint(55,97.6),new DataPoint(56,98.1),
                new DataPoint(57,98.5),new DataPoint(58,99),new DataPoint(59,99.5),
                new DataPoint(60,99.9)
        });
        graph_A_LvsE.addSeries(dossd);
        dossd.setColor(Color.rgb(250, 105, 0));

        LineGraphSeries<DataPoint> tressd = new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(0,43.6),new DataPoint(1,47.8),new DataPoint(2,51),
                new DataPoint(3,53.5),new DataPoint(4,55.6),new DataPoint(5,57.4),
                new DataPoint(6,58.9),new DataPoint(7,60.3),new DataPoint(8,61.7),
                new DataPoint(9,62.9),new DataPoint(10,64.1),new DataPoint(11,65.2),
                new DataPoint(12,66.3),new DataPoint(13,67.3),new DataPoint(14,68.3),
                new DataPoint(15,69.3),new DataPoint(16,70.2),new DataPoint(17,71.1),
                new DataPoint(18,72),new DataPoint(19,72.8),new DataPoint(20,73.7),
                new DataPoint(21,74.5),new DataPoint(22,75.2),new DataPoint(23,76),
                new DataPoint(24,76.4),new DataPoint(25,76.8),new DataPoint(26,77.5),
                new DataPoint(27,78.1),new DataPoint(28,78.8),new DataPoint(29,79.5),
                new DataPoint(30,80.1),new DataPoint(31,80.7),new DataPoint(32,81.3),
                new DataPoint(33,81.9),new DataPoint(34,82.5),new DataPoint(35,83.1),
                new DataPoint(36,83.6),new DataPoint(37,84.2),new DataPoint(38,84.7),
                new DataPoint(39,85.3),new DataPoint(40,85.8),new DataPoint(41,86.3),
                new DataPoint(42,86.8),new DataPoint(43,87.4),new DataPoint(44,87.9),
                new DataPoint(45,88.4),new DataPoint(46,88.9),new DataPoint(47,89.3),
                new DataPoint(48,89.8),new DataPoint(49,90.3),new DataPoint(50,90.7),
                new DataPoint(51,91.2),new DataPoint(52,91.7),new DataPoint(53,92.1),
                new DataPoint(54,92.6),new DataPoint(55,93),new DataPoint(56,93.4),
                new DataPoint(57,93.9),new DataPoint(58,94.3),new DataPoint(59,94.7),
                new DataPoint(60,95.2)
        });
        graph_A_LvsE.addSeries(tressd);
        tressd.setColor(Color.RED);

        LineGraphSeries<DataPoint> Unosd = new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(0,51),new DataPoint(1,55.6),new DataPoint(2,59.1),
                new DataPoint(3,61.9),new DataPoint(4,64.3),new DataPoint(5,66.2),
                new DataPoint(6,68),new DataPoint(7,69.6),new DataPoint(8,71.1),
                new DataPoint(9,72.6),new DataPoint(10,73.9),new DataPoint(11,75.3),
                new DataPoint(12,76.6),new DataPoint(13,77.8),new DataPoint(14,79.1),
                new DataPoint(15,80.2),new DataPoint(16,81.4),new DataPoint(17,82.5),
                new DataPoint(18,83.6),new DataPoint(19,84.7),new DataPoint(20,85.7),
                new DataPoint(21,86.7),new DataPoint(22,87.7),new DataPoint(23,88.7),
                new DataPoint(24,89.6),new DataPoint(25,89.9),new DataPoint(26,90.8),
                new DataPoint(27,91.7),new DataPoint(28,92.5),new DataPoint(29,93.4),
                new DataPoint(30,94.2),new DataPoint(31,95),new DataPoint(32,95.8),
                new DataPoint(33,96.6),new DataPoint(34,97.4),new DataPoint(35,98.1),
                new DataPoint(36,98.9),new DataPoint(37,99.6),new DataPoint(38,100.3),
                new DataPoint(39,101),new DataPoint(40,101.7),new DataPoint(41,102.4),
                new DataPoint(42,103.1),new DataPoint(43,103.8),new DataPoint(44,104.5),
                new DataPoint(45,105.1),new DataPoint(46,105.8),new DataPoint(47,106.4),
                new DataPoint(48,107),new DataPoint(49,107.7),new DataPoint(50,108.3),
                new DataPoint(51,108.9),new DataPoint(52,109.5),new DataPoint(53,110.1),
                new DataPoint(54,110.7),new DataPoint(55,111.3),new DataPoint(56,111.9),
                new DataPoint(57,112.5),new DataPoint(58,113),new DataPoint(59,113.6),
                new DataPoint(60,114.2)
        });
        graph_A_LvsE.addSeries(Unosd);
        Unosd.setColor(Color.YELLOW);

        LineGraphSeries<DataPoint> Dossd = new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(0,52.9),new DataPoint(1,57.6),new DataPoint(2,61.1),
                new DataPoint(3,64),new DataPoint(4,66.4),new DataPoint(5,68.5),
                new DataPoint(6,70.3),new DataPoint(7,71.9),new DataPoint(8,73.5),
                new DataPoint(9,75),new DataPoint(10,76.4),new DataPoint(11,77.8),
                new DataPoint(12,79.2),new DataPoint(13,80.5),new DataPoint(14,81.7),
                new DataPoint(15,83),new DataPoint(16,84.2),new DataPoint(17,85.5),
                new DataPoint(18,86.5),new DataPoint(19,87.6),new DataPoint(20,88.7),
                new DataPoint(21,89.8),new DataPoint(22,90.8),new DataPoint(23,91.9),
                new DataPoint(24,92.9),new DataPoint(25,93.1),new DataPoint(26,94.1),
                new DataPoint(27,95),new DataPoint(28,96),new DataPoint(29,96.9),
                new DataPoint(30,97.7),new DataPoint(31,98.6),new DataPoint(32,99.4),
                new DataPoint(33,100.3),new DataPoint(34,101.1),new DataPoint(35,101.9),
                new DataPoint(36,102.7),new DataPoint(37,103.4),new DataPoint(38,104.2),
                new DataPoint(39,105),new DataPoint(40,105.7),new DataPoint(41,106.4),
                new DataPoint(42,107.2),new DataPoint(43,107.9),new DataPoint(44,108.6),
                new DataPoint(45,109.3),new DataPoint(46,110),new DataPoint(47,110.7),
                new DataPoint(48,111.3),new DataPoint(49,112),new DataPoint(50,112.7),
                new DataPoint(51,113.3),new DataPoint(52,114),new DataPoint(53,114.6),
                new DataPoint(54,115.2),new DataPoint(55,115.9),new DataPoint(56,116.5),
                new DataPoint(57,117.1),new DataPoint(58,117.7),new DataPoint(59,118.3),
                new DataPoint(60,118.9)
        });
        graph_A_LvsE.addSeries(Dossd);
        Dossd.setColor(Color.rgb(250, 105, 0));

        LineGraphSeries<DataPoint> Tressd = new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(0,54.7),new DataPoint(1,59.5),new DataPoint(2,63.2),
                new DataPoint(3,66.1),new DataPoint(4,68.6),new DataPoint(5,70.7),
                new DataPoint(6,72.5),new DataPoint(7,74.2),new DataPoint(8,75.8),
                new DataPoint(9,77.4),new DataPoint(10,78.9),new DataPoint(11,80.3),
                new DataPoint(12,81.7),new DataPoint(13,83.1),new DataPoint(14,84.4),
                new DataPoint(15,85.7),new DataPoint(16,87),new DataPoint(17,88.2),
                new DataPoint(18,89.4),new DataPoint(19,90.6),new DataPoint(20,91.7),
                new DataPoint(21,92.9),new DataPoint(22,94),new DataPoint(23,95),
                new DataPoint(24,96.1),new DataPoint(25,96.4),new DataPoint(26,97.4),
                new DataPoint(27,98.4),new DataPoint(28,99.4),new DataPoint(29,100.3),
                new DataPoint(30,101.3),new DataPoint(31,102.2),new DataPoint(32,103.1),
                new DataPoint(33,103.9),new DataPoint(34,104.8),new DataPoint(35,105.6),
                new DataPoint(36,106.5),new DataPoint(37,107.3),new DataPoint(38,108.1),
                new DataPoint(39,108.9),new DataPoint(40,109.7),new DataPoint(41,110.5),
                new DataPoint(42,111.2),new DataPoint(43,112),new DataPoint(44,112.7),
                new DataPoint(45,113.5),new DataPoint(46,114.2),new DataPoint(47,114.9),
                new DataPoint(48,115.7),new DataPoint(49,116.4),new DataPoint(50,117.1),
                new DataPoint(51,117.7),new DataPoint(52,118.4),new DataPoint(53,119.1),
                new DataPoint(54,119.8),new DataPoint(55,120.4),new DataPoint(56,121.1),
                new DataPoint(57,121.8),new DataPoint(58,122.4),new DataPoint(59,123.1),
                new DataPoint(60,123.7)
        });
        graph_A_LvsE.addSeries(Tressd);
        Tressd.setColor(Color.RED);

        //------------------------------

        graph_A_LvsE.addSeries(pointLongitud);
        pointLongitud.setDrawDataPoints(true);
        pointLongitud.setDataPointsRadius(8);
        pointLongitud.setColor(Color.rgb(21,43,60));

        //------------------------------


        graph_A_LvsE.setTitle("cm - meses");
        graph_A_LvsE.setTitleColor(Color.rgb(117,117,117));

        graph_A_LvsE.getGridLabelRenderer().getGridStyle().drawHorizontal();

        graph_A_LvsE.getViewport().setYAxisBoundsManual(true);
        graph_A_LvsE.getViewport().setMinY(40);
        graph_A_LvsE.getViewport().setMaxY(125);

        graph_A_LvsE.getViewport().setXAxisBoundsManual(true);
        graph_A_LvsE.getViewport().setMinX(0);
        graph_A_LvsE.getViewport().setMaxX(61);
    }

    public void grafica_A_LvsP() {
        /*GraphView graph_A_PvsL = (GraphView) findViewById(R.id.grapH_OLvsP);



        LineGraphSeries<DataPoint> median = new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(45, 2.4), new DataPoint(45.5, 2.5), new DataPoint(46, 2.6),
                new DataPoint(46.5, 2.7), new DataPoint(47, 2.8), new DataPoint(47.5, 2.9),
                new DataPoint(48, 2.9), new DataPoint(48.5, 3), new DataPoint(49, 3.1),
                new DataPoint(49.5, 3.2), new DataPoint(50, 3.3), new DataPoint(50.5, 3.4),
                new DataPoint(51, 3.5), new DataPoint(51.5, 3.6), new DataPoint(52, 3.8),
                new DataPoint(52.5, 3.9), new DataPoint(53, 4), new DataPoint(53.5, 4.1),
                new DataPoint(54, 4.3), new DataPoint(54.5, 4.4), new DataPoint(55, 4.5),
                new DataPoint(55.5, 4.7), new DataPoint(56, 4.8), new DataPoint(56.5, 5),
                new DataPoint(57, 5.1), new DataPoint(57.5, 5.3), new DataPoint(58, 5.4),
                new DataPoint(58.5, 5.6), new DataPoint(59, 5.7), new DataPoint(59.5, 5.9),
                new DataPoint(60, 6), new DataPoint(60.5, 6.1), new DataPoint(61, 6.3),
                new DataPoint(61.5, 6.4), new DataPoint(62, 6.5), new DataPoint(62.5, 6.7),
                new DataPoint(63, 6.8), new DataPoint(63.5, 6.9), new DataPoint(64, 7),
                new DataPoint(64.5, 7.1), new DataPoint(65, 7.3), new DataPoint(65.5, 7.4),
                new DataPoint(66, 7.5), new DataPoint(66.5, 7.6), new DataPoint(67, 7.7),
                new DataPoint(67.5, 7.9), new DataPoint(68, 8), new DataPoint(68.5, 8.1),
                new DataPoint(69, 8.2), new DataPoint(69.5, 8.3), new DataPoint(70, 8.4),
                new DataPoint(70.5, 8.5), new DataPoint(71, 8.6), new DataPoint(71.5, 8.8),
                new DataPoint(72, 8.9), new DataPoint(72.5, 9), new DataPoint(73, 9.1),
                new DataPoint(73.5, 9.2), new DataPoint(74, 9.3), new DataPoint(74.5, 9.4),
                new DataPoint(75, 9.5), new DataPoint(75.5, 9.6), new DataPoint(76, 9.7),
                new DataPoint(76.5, 9.8), new DataPoint(77, 9.9), new DataPoint(77.5, 10),
                new DataPoint(78, 10.1), new DataPoint(78.5, 10.2), new DataPoint(79, 10.3),
                new DataPoint(79.5, 10.4), new DataPoint(80, 10.4), new DataPoint(80.5, 10.5),
                new DataPoint(81, 10.6), new DataPoint(81.5, 10.7), new DataPoint(82, 10.8),
                new DataPoint(82.5, 10.9), new DataPoint(83, 11), new DataPoint(83.5, 11.2),
                new DataPoint(84, 11.3), new DataPoint(84.5, 11.4), new DataPoint(85, 11.5),
                new DataPoint(85.5, 11.6), new DataPoint(86, 11.7), new DataPoint(86.5, 11.9),
                new DataPoint(87, 12), new DataPoint(87.5, 12.1), new DataPoint(88, 12.2),
                new DataPoint(88.5, 12.4), new DataPoint(89, 12.5), new DataPoint(89.5, 12.6),
                new DataPoint(90, 12.7), new DataPoint(90.5, 12.8), new DataPoint(91, 13),
                new DataPoint(91.5, 13.1), new DataPoint(92, 13.2), new DataPoint(92.5, 13.3),
                new DataPoint(93, 13.4), new DataPoint(93.5, 13.5), new DataPoint(94, 13.7),
                new DataPoint(94.5, 13.8), new DataPoint(95, 13.9), new DataPoint(95.5, 14),
                new DataPoint(96, 14.1), new DataPoint(96.5, 14.3), new DataPoint(97, 14.4),
                new DataPoint(97.5, 14.5), new DataPoint(98, 14.6), new DataPoint(98.5, 14.8),
                new DataPoint(99, 14.9), new DataPoint(99.5, 15), new DataPoint(100, 15.2),
                new DataPoint(100.5, 15.3), new DataPoint(101, 15.4), new DataPoint(101.5, 15.6),
                new DataPoint(102, 15.7), new DataPoint(102.5, 15.9), new DataPoint(103, 16),
                new DataPoint(103.5, 16.2), new DataPoint(104, 16.3), new DataPoint(104.5, 16.5),
                new DataPoint(105, 16.6), new DataPoint(105.5, 16.8), new DataPoint(106, 16.9),
                new DataPoint(106.5, 17.1), new DataPoint(107, 17.3), new DataPoint(107.5, 17.4),
                new DataPoint(108, 17.6), new DataPoint(108.5, 17.8), new DataPoint(109, 17.9),
                new DataPoint(109.5, 18.1), new DataPoint(110, 18.3)
        });

        graph_A_PvsL.addSeries(median);
        median.setColor(Color.GREEN);

        LineGraphSeries<DataPoint> unosd = new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(45, 2.2), new DataPoint(45.5, 2.3), new DataPoint(46, 2.4),
                new DataPoint(46.5, 2.5), new DataPoint(47, 2.5), new DataPoint(47.5, 2.6),
                new DataPoint(48, 2.7), new DataPoint(48.5, 2.8), new DataPoint(49, 2.9),
                new DataPoint(49.5, 3), new DataPoint(50, 3), new DataPoint(50.5, 3.1),
                new DataPoint(51, 3.2), new DataPoint(51.5, 3.3), new DataPoint(52, 3.5),
                new DataPoint(52.5, 3.6), new DataPoint(53, 3.7), new DataPoint(53.5, 3.8),
                new DataPoint(54, 3.9), new DataPoint(54.5, 4), new DataPoint(55, 4.2),
                new DataPoint(55.5, 4.3), new DataPoint(56, 4.4), new DataPoint(56.5, 4.6),
                new DataPoint(57, 4.7), new DataPoint(57.5, 4.9), new DataPoint(58, 5),
                new DataPoint(58.5, 5.1), new DataPoint(59, 5.3), new DataPoint(59.5, 5.4),
                new DataPoint(60, 5.5), new DataPoint(60.5, 5.6), new DataPoint(61, 5.8),
                new DataPoint(61.5, 5.9), new DataPoint(62, 6), new DataPoint(62.5, 6.1),
                new DataPoint(63, 6.2), new DataPoint(63.5, 6.4), new DataPoint(64, 6.5),
                new DataPoint(64.5, 6.6), new DataPoint(65, 6.7), new DataPoint(65.5, 6.8),
                new DataPoint(66, 6.9), new DataPoint(66.5, 7), new DataPoint(67, 7.1),
                new DataPoint(67.5, 7.2), new DataPoint(68, 7.3), new DataPoint(68.5, 7.5),
                new DataPoint(69, 7.6), new DataPoint(69.5, 7.7), new DataPoint(70, 7.8),
                new DataPoint(70.5, 7.9), new DataPoint(71, 8), new DataPoint(71.5, 8.1),
                new DataPoint(72, 8.2), new DataPoint(72.5, 8.3), new DataPoint(73, 8.4),
                new DataPoint(73.5, 8.5), new DataPoint(74, 8.6), new DataPoint(74.5, 8.7),
                new DataPoint(75, 8.8), new DataPoint(75.5, 8.8), new DataPoint(76, 8.9),
                new DataPoint(76.5, 9), new DataPoint(77, 9.1), new DataPoint(77.5, 9.2),
                new DataPoint(78, 9.3), new DataPoint(78.5, 9.4), new DataPoint(79, 9.5),
                new DataPoint(79.5, 9.5), new DataPoint(80, 9.6), new DataPoint(80.5, 9.7),
                new DataPoint(81, 9.8), new DataPoint(81.5, 9.9), new DataPoint(82, 10),
                new DataPoint(82.5, 10.1), new DataPoint(83, 10.2), new DataPoint(83.5, 10.3),
                new DataPoint(84, 10.4), new DataPoint(84.5, 10.5), new DataPoint(85, 10.6),
                new DataPoint(85.5, 10.7), new DataPoint(86, 10.8), new DataPoint(86.5, 11),
                new DataPoint(87, 11.1), new DataPoint(87.5, 11.2), new DataPoint(88, 11.3),
                new DataPoint(88.5, 11.4), new DataPoint(89, 11.5), new DataPoint(89.5, 11.6),
                new DataPoint(90, 11.8), new DataPoint(90.5, 11.9), new DataPoint(91, 12),
                new DataPoint(91.5, 12.1), new DataPoint(92, 12.2), new DataPoint(92.5, 12.3),
                new DataPoint(93, 12.4), new DataPoint(93.5, 12.5), new DataPoint(94, 12.6),
                new DataPoint(94.5, 12.7), new DataPoint(95, 12.8), new DataPoint(95.5, 12.9),
                new DataPoint(96, 13.1), new DataPoint(96.5, 13.2), new DataPoint(97, 13.3),
                new DataPoint(97.5, 13.4), new DataPoint(98, 13.5), new DataPoint(98.5, 13.6),
                new DataPoint(99, 13.7), new DataPoint(99.5, 13.9), new DataPoint(100, 14),
                new DataPoint(100.5, 14.1), new DataPoint(101, 14.2), new DataPoint(101.5, 14.4),
                new DataPoint(102, 14.5), new DataPoint(102.5, 14.6), new DataPoint(103, 14.8),
                new DataPoint(103.5, 14.9), new DataPoint(104, 15), new DataPoint(104.5, 15.2),
                new DataPoint(105, 15.3), new DataPoint(105.5, 15.4), new DataPoint(106, 15.6),
                new DataPoint(106.5, 15.7), new DataPoint(107, 15.9), new DataPoint(107.5, 16),
                new DataPoint(108, 16.2), new DataPoint(108.5, 16.3), new DataPoint(109, 16.5),
                new DataPoint(109.5, 16.6), new DataPoint(110, 16.8)

        });

        graph_A_PvsL.addSeries(unosd);
        unosd.setColor(Color.YELLOW);

        LineGraphSeries<DataPoint> dossd = new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(45, 2), new DataPoint(45.5, 2.1), new DataPoint(46, 2.2),
                new DataPoint(46.5, 2.3), new DataPoint(47, 2.3), new DataPoint(47.5, 2.4),
                new DataPoint(48, 2.5), new DataPoint(48.5, 2.6), new DataPoint(49, 2.6),
                new DataPoint(49.5, 2.7), new DataPoint(50, 2.8), new DataPoint(50.5, 2.9),
                new DataPoint(51, 3), new DataPoint(51.5, 3.1), new DataPoint(52, 3.2),
                new DataPoint(52.5, 3.3), new DataPoint(53, 3.4), new DataPoint(53.5, 3.5),
                new DataPoint(54, 3.6), new DataPoint(54.5, 3.7), new DataPoint(55, 3.8),
                new DataPoint(55.5, 4), new DataPoint(56, 4.1), new DataPoint(56.5, 4.2),
                new DataPoint(57, 4.3), new DataPoint(57.5, 4.5), new DataPoint(58, 4.6),
                new DataPoint(58.5, 4.7), new DataPoint(59, 4.8), new DataPoint(59.5, 5),
                new DataPoint(60, 5.1), new DataPoint(60.5, 5.2), new DataPoint(61, 5.3),
                new DataPoint(61.5, 5.4), new DataPoint(62, 5.6), new DataPoint(62.5, 5.7),
                new DataPoint(63, 5.8), new DataPoint(63.5, 5.9), new DataPoint(64, 6),
                new DataPoint(64.5, 6.1), new DataPoint(65, 6.2), new DataPoint(65.5, 6.3),
                new DataPoint(66, 6.4), new DataPoint(66.5, 6.5), new DataPoint(67, 6.6),
                new DataPoint(67.5, 6.7), new DataPoint(68, 6.8), new DataPoint(68.5, 6.9),
                new DataPoint(69, 7), new DataPoint(69.5, 7.1), new DataPoint(70, 7.2),
                new DataPoint(70.5, 7.3), new DataPoint(71, 7.4), new DataPoint(71.5, 7.5),
                new DataPoint(72, 7.6), new DataPoint(72.5, 7.6), new DataPoint(73, 7.7),
                new DataPoint(73.5, 7.8), new DataPoint(74, 7.9), new DataPoint(74.5, 8),
                new DataPoint(75, 8.1), new DataPoint(75.5, 8.2), new DataPoint(76, 8.3),
                new DataPoint(76.5, 8.3), new DataPoint(77, 8.4), new DataPoint(77.5, 8.5),
                new DataPoint(78, 8.6), new DataPoint(78.5, 8.7), new DataPoint(79, 8.7),
                new DataPoint(79.5, 8.8), new DataPoint(80, 8.9), new DataPoint(80.5, 9),
                new DataPoint(81, 9.1), new DataPoint(81.5, 9.1), new DataPoint(82, 9.2),
                new DataPoint(82.5, 9.3), new DataPoint(83, 9.4), new DataPoint(83.5, 9.5),
                new DataPoint(84, 9.6), new DataPoint(84.5, 9.7), new DataPoint(85, 9.8),
                new DataPoint(85.5, 9.9), new DataPoint(86, 10), new DataPoint(86.5, 10.1),
                new DataPoint(87, 10.2), new DataPoint(87.5, 10.4), new DataPoint(88, 10.5),
                new DataPoint(88.5, 10.6), new DataPoint(89, 10.7), new DataPoint(89.5, 10.8),
                new DataPoint(90, 10.9), new DataPoint(90.5, 11), new DataPoint(91, 11.1),
                new DataPoint(91.5, 11.2), new DataPoint(92, 11.3), new DataPoint(92.5, 11.4),
                new DataPoint(93, 11.5), new DataPoint(93.5, 11.6), new DataPoint(94, 11.7),
                new DataPoint(94.5, 11.8), new DataPoint(95, 11.9), new DataPoint(95.5, 12),
                new DataPoint(96, 12.1), new DataPoint(96.5, 12.2), new DataPoint(97, 12.3),
                new DataPoint(97.5, 12.4), new DataPoint(98, 12.5), new DataPoint(98.5, 12.6),
                new DataPoint(99, 12.7), new DataPoint(99.5, 12.8), new DataPoint(100, 12.9),
                new DataPoint(100.5, 13), new DataPoint(101, 13.2), new DataPoint(101.5, 13.3),
                new DataPoint(102, 13.4), new DataPoint(102.5, 13.5), new DataPoint(103, 13.6),
                new DataPoint(103.5, 13.7), new DataPoint(104, 13.9), new DataPoint(104.5, 14),
                new DataPoint(105, 14.1), new DataPoint(105.5, 14.2), new DataPoint(106, 14.4),
                new DataPoint(106.5, 14.5), new DataPoint(107, 14.6), new DataPoint(107.5, 14.7),
                new DataPoint(108, 14.9), new DataPoint(108.5, 15), new DataPoint(109, 15.1),
                new DataPoint(109.5, 15.3), new DataPoint(110, 15.4)

        });
        graph_A_PvsL.addSeries(dossd);
        dossd.setColor(Color.rgb(250,105,0));

        LineGraphSeries<DataPoint> tressd = new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(45, 1.9), new DataPoint(45.5, 1.9), new DataPoint(46, 2),
                new DataPoint(46.5, 2.1), new DataPoint(47, 2.1), new DataPoint(47.5, 2.2),
                new DataPoint(48, 2.3), new DataPoint(48.5, 2.3), new DataPoint(49, 2.4),
                new DataPoint(49.5, 2.5), new DataPoint(50, 2.6), new DataPoint(50.5, 2.7),
                new DataPoint(51, 2.7), new DataPoint(51.5, 2.8), new DataPoint(52, 2.9),
                new DataPoint(52.5, 3), new DataPoint(53, 3.1), new DataPoint(53.5, 3.2),
                new DataPoint(54, 3.3), new DataPoint(54.5, 3.4), new DataPoint(55, 3.6),
                new DataPoint(55.5, 3.7), new DataPoint(56, 3.8), new DataPoint(56.5, 3.9),
                new DataPoint(57, 4), new DataPoint(57.5, 4.1), new DataPoint(58, 4.3),
                new DataPoint(58.5, 4.4), new DataPoint(59, 4.5), new DataPoint(59.5, 4.6),
                new DataPoint(60, 4.7), new DataPoint(60.5, 4.8), new DataPoint(61, 4.90),
                new DataPoint(61.5, 5), new DataPoint(62, 5.1), new DataPoint(62.5, 5.2),
                new DataPoint(63, 5.3), new DataPoint(63.5, 5.4), new DataPoint(64, 5.5),
                new DataPoint(64.5, 5.6), new DataPoint(65, 5.7), new DataPoint(65.5, 5.8),
                new DataPoint(66, 5.9), new DataPoint(66.5, 6), new DataPoint(67, 6.1),
                new DataPoint(67.5, 6.2), new DataPoint(68, 6.3), new DataPoint(68.5, 6.4),
                new DataPoint(69, 6.5), new DataPoint(69.5, 6.6), new DataPoint(70, 6.6),
                new DataPoint(70.5, 6.7), new DataPoint(71, 6.8), new DataPoint(71.5, 6.9),
                new DataPoint(72, 7), new DataPoint(72.5, 7.1), new DataPoint(73, 7.2),
                new DataPoint(73.5, 7.2), new DataPoint(74, 7.3), new DataPoint(74.5, 7.4),
                new DataPoint(75, 7.5), new DataPoint(75.5, 7.6), new DataPoint(76, 7.6),
                new DataPoint(76.5, 7.7), new DataPoint(77, 7.8), new DataPoint(77.5, 7.9),
                new DataPoint(78, 7.9), new DataPoint(78.5, 8), new DataPoint(79, 8.1),
                new DataPoint(79.5, 8.2), new DataPoint(80, 8.2), new DataPoint(80.5, 8.3),
                new DataPoint(81, 8.4), new DataPoint(81.5, 8.5), new DataPoint(82, 8.5),
                new DataPoint(82.5, 8.6), new DataPoint(83, 8.7), new DataPoint(83.5, 8.8),
                new DataPoint(84, 8.9), new DataPoint(84.5, 9), new DataPoint(85, 9.1),
                new DataPoint(85.5, 9.2), new DataPoint(86, 9.3), new DataPoint(86.5, 9.4),
                new DataPoint(87, 9.5), new DataPoint(87.5, 9.6), new DataPoint(88, 9.7),
                new DataPoint(88.5, 9.8), new DataPoint(89, 9.9), new DataPoint(89.5, 10),
                new DataPoint(90, 10.1), new DataPoint(90.5, 10.2), new DataPoint(91, 10.3),
                new DataPoint(91.5, 10.4), new DataPoint(92, 10.5), new DataPoint(92.5, 10.6),
                new DataPoint(93, 10.7), new DataPoint(93.5, 10.7), new DataPoint(94, 10.8),
                new DataPoint(94.5, 10.9), new DataPoint(95, 11), new DataPoint(95.5, 11.1),
                new DataPoint(96, 11.2), new DataPoint(96.5, 11.3), new DataPoint(97, 11.4),
                new DataPoint(97.5, 11.5), new DataPoint(98, 11.6), new DataPoint(98.5, 11.7),
                new DataPoint(99, 11.8), new DataPoint(99.5, 11.9), new DataPoint(100, 12),
                new DataPoint(100.5, 12.1), new DataPoint(101, 12.2), new DataPoint(101.5, 12.3),
                new DataPoint(102, 12.4), new DataPoint(102.5, 12.5), new DataPoint(103, 12.6),
                new DataPoint(103.5, 12.7), new DataPoint(104, 12.8), new DataPoint(104.5, 12.9),
                new DataPoint(105, 13), new DataPoint(105.5, 13.2), new DataPoint(106, 13.3),
                new DataPoint(106.5, 13.4), new DataPoint(107, 13.5), new DataPoint(107.5, 13.6),
                new DataPoint(108, 13.7), new DataPoint(108.5, 13.8), new DataPoint(109, 14),
                new DataPoint(109.5, 14.1), new DataPoint(110, 14.2)
        });
        graph_A_PvsL.addSeries(tressd);
        tressd.setColor(Color.RED);



        LineGraphSeries<DataPoint> Unosd = new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(45, 2.4), new DataPoint(45.5, 2.8), new DataPoint(46, 2.9),
                new DataPoint(46.5, 3), new DataPoint(47, 3), new DataPoint(47.5, 3.1),
                new DataPoint(48, 3.2), new DataPoint(48.5, 3.3), new DataPoint(49, 3.4),
                new DataPoint(49.5, 3.5), new DataPoint(50, 3.6), new DataPoint(50.5, 3.8),
                new DataPoint(51, 3.9), new DataPoint(51.5, 4), new DataPoint(52, 4.1),
                new DataPoint(52.5, 4.2), new DataPoint(53, 4.4), new DataPoint(53.5, 4.5),
                new DataPoint(54, 4.7), new DataPoint(54.5, 4.8), new DataPoint(55, 5),
                new DataPoint(55.5, 5.1), new DataPoint(56, 5.3), new DataPoint(56.5, 5.40),
                new DataPoint(57, 5.6), new DataPoint(57.5, 5.7), new DataPoint(58, 5.9),
                new DataPoint(58.5, 6.1), new DataPoint(59, 6.2), new DataPoint(59.5, 6.4),
                new DataPoint(60, 6.5), new DataPoint(60.5, 6.7), new DataPoint(61, 6.8),
                new DataPoint(61.5, 7), new DataPoint(62, 7.1), new DataPoint(62.5, 7.2),
                new DataPoint(63, 7.4), new DataPoint(63.5, 7.5), new DataPoint(64, 7.6),
                new DataPoint(64.5, 7.8), new DataPoint(65, 7.9), new DataPoint(65.5, 8),
                new DataPoint(66, 8.2), new DataPoint(66.5, 8.3), new DataPoint(67, 8.4),
                new DataPoint(67.5, 8.5), new DataPoint(68, 8.7), new DataPoint(68.5, 8.8),
                new DataPoint(69, 8.9), new DataPoint(69.5, 9), new DataPoint(70, 9.2),
                new DataPoint(70.5, 9.3), new DataPoint(71, 9.4), new DataPoint(71.5, 9.5),
                new DataPoint(72, 9.6), new DataPoint(72.5, 9.8), new DataPoint(73, 9.9),
                new DataPoint(73.5, 10), new DataPoint(74, 10.1), new DataPoint(74.5, 10.2),
                new DataPoint(75, 10.3), new DataPoint(75.5, 10.4), new DataPoint(76, 10.6),
                new DataPoint(76.5, 10.7), new DataPoint(77, 10.8), new DataPoint(77.5, 10.9),
                new DataPoint(78, 11), new DataPoint(78.5, 11.1), new DataPoint(79, 11.2),
                new DataPoint(79.5, 11.3), new DataPoint(80, 11.4), new DataPoint(80.5, 11.5),
                new DataPoint(81, 11.6), new DataPoint(81.5, 11.7), new DataPoint(82, 11.8),
                new DataPoint(82.5, 11.9), new DataPoint(83, 12), new DataPoint(83.5, 12.1),
                new DataPoint(84, 12.2), new DataPoint(84.5, 12.4), new DataPoint(85, 12.5),
                new DataPoint(85.5, 12.6), new DataPoint(86, 12.8), new DataPoint(86.5, 12.9),
                new DataPoint(87, 13), new DataPoint(87.5, 13.2), new DataPoint(88, 13.3),
                new DataPoint(88.5, 13.4), new DataPoint(89, 13.5), new DataPoint(89.5, 13.7),
                new DataPoint(90, 13.8), new DataPoint(90.5, 13.9), new DataPoint(91, 14.1),
                new DataPoint(91.5, 14.2), new DataPoint(92, 14.3), new DataPoint(92.5, 14.4),
                new DataPoint(93, 14.6), new DataPoint(93.5, 14.7), new DataPoint(94, 14.8),
                new DataPoint(94.5, 14.9), new DataPoint(95, 15.1), new DataPoint(95.5, 15.2),
                new DataPoint(96, 15.3), new DataPoint(96.5, 15.5), new DataPoint(97, 15.6),
                new DataPoint(97.5, 15.7), new DataPoint(98, 15.9), new DataPoint(98.5, 16),
                new DataPoint(99, 16.2), new DataPoint(99.5, 16.3), new DataPoint(100, 16.5),
                new DataPoint(100.5, 16.6), new DataPoint(101, 16.8), new DataPoint(101.5, 16.9),
                new DataPoint(102, 17.1), new DataPoint(102.5, 17.3), new DataPoint(103, 17.4),
                new DataPoint(103.5, 17.6), new DataPoint(104, 17.8), new DataPoint(104.5, 17.9),
                new DataPoint(105, 18.1), new DataPoint(105.5, 18.3), new DataPoint(106, 18.5),
                new DataPoint(106.5, 18.6), new DataPoint(107, 18.8), new DataPoint(107.5, 19),
                new DataPoint(108, 19.2), new DataPoint(108.5, 19.4), new DataPoint(109, 19.6),
                new DataPoint(109.5, 19.8), new DataPoint(110, 20)
        });

        graph_A_PvsL.addSeries(Unosd);
        Unosd.setColor(Color.YELLOW);

        LineGraphSeries<DataPoint> Dossd = new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(45, 3), new DataPoint(45.5, 3.1), new DataPoint(46, 3.1),
                new DataPoint(46.5, 3.2), new DataPoint(47, 3.3), new DataPoint(47.5, 3.4),
                new DataPoint(48, 3.6), new DataPoint(48.5, 3.7), new DataPoint(49, 3.8),
                new DataPoint(49.5, 3.9), new DataPoint(50, 4), new DataPoint(50.5, 4.1),
                new DataPoint(51, 4.2), new DataPoint(51.5, 4.4), new DataPoint(52, 4.5),
                new DataPoint(52.5, 4.6), new DataPoint(53, 4.8), new DataPoint(53.5, 4.9),
                new DataPoint(54, 5.1), new DataPoint(54.5, 5.3), new DataPoint(55, 5.4),
                new DataPoint(55.5, 5.6), new DataPoint(56, 5.8), new DataPoint(56.5, 5.9),
                new DataPoint(57, 6.1), new DataPoint(57.5, 6.3), new DataPoint(58, 6.4),
                new DataPoint(58.5, 6.6), new DataPoint(59, 6.8), new DataPoint(59.5, 7),
                new DataPoint(60, 7.1), new DataPoint(60.5, 7.3), new DataPoint(61, 7.4),
                new DataPoint(61.5, 7.6), new DataPoint(62, 7.7), new DataPoint(62.5, 7.9),
                new DataPoint(63, 8), new DataPoint(63.5, 8.2), new DataPoint(64, 8.3),
                new DataPoint(64.5, 8.5), new DataPoint(65, 8.6), new DataPoint(65.5, 8.7),
                new DataPoint(66, 8.9), new DataPoint(66.5, 9), new DataPoint(67, 9.2),
                new DataPoint(67.5, 9.3), new DataPoint(68, 9.4), new DataPoint(68.5, 9.6),
                new DataPoint(69, 9.7), new DataPoint(69.5, 9.8), new DataPoint(70, 10),
                new DataPoint(70.5, 10.1), new DataPoint(71, 10.2), new DataPoint(71.5, 10.4),
                new DataPoint(72, 10.5), new DataPoint(72.5, 10.6), new DataPoint(73, 10.8),
                new DataPoint(73.5, 10.9), new DataPoint(74, 11), new DataPoint(74.5, 11.2),
                new DataPoint(75, 11.3), new DataPoint(75.5, 11.4), new DataPoint(76, 11.5),
                new DataPoint(76.5, 11.6), new DataPoint(77, 11.7), new DataPoint(77.5, 11.9),
                new DataPoint(78, 12), new DataPoint(78.5, 12.1), new DataPoint(79, 12.2),
                new DataPoint(79.5, 12.3), new DataPoint(80, 12.4), new DataPoint(80.5, 12.5),
                new DataPoint(81, 12.6), new DataPoint(81.5, 12.7), new DataPoint(82, 12.8),
                new DataPoint(82.5, 13), new DataPoint(83, 13.1), new DataPoint(83.5, 13.2),
                new DataPoint(84, 13.3), new DataPoint(84.5, 13.5), new DataPoint(85, 13.6),
                new DataPoint(85.5, 13.7), new DataPoint(86, 13.9), new DataPoint(86.5, 14),
                new DataPoint(87, 14.2), new DataPoint(87.5, 14.3), new DataPoint(88, 14.5),
                new DataPoint(88.5, 14.6), new DataPoint(89, 14.7), new DataPoint(89.5, 14.9),
                new DataPoint(90, 15), new DataPoint(90.5, 15.1), new DataPoint(91, 15.3),
                new DataPoint(91.5, 15.4), new DataPoint(92, 15.6), new DataPoint(92.5, 15.7),
                new DataPoint(93, 15.8), new DataPoint(93.5, 16), new DataPoint(94, 16.1),
                new DataPoint(94.5, 16.3), new DataPoint(95, 16.4), new DataPoint(95.5, 16.5),
                new DataPoint(96, 16.7), new DataPoint(96.5, 16.8), new DataPoint(97, 17),
                new DataPoint(97.5, 17.1), new DataPoint(98, 17.3), new DataPoint(98.5, 17.5),
                new DataPoint(99, 17.6), new DataPoint(99.5, 17.8), new DataPoint(100, 18),
                new DataPoint(100.5, 18.1), new DataPoint(101, 18.3), new DataPoint(101.5, 18.5),
                new DataPoint(102, 18.7), new DataPoint(102.5, 18.8), new DataPoint(103, 19),
                new DataPoint(103.5, 19.2), new DataPoint(104, 19.4), new DataPoint(104.5, 19.6),
                new DataPoint(105, 19.8), new DataPoint(105.5, 20), new DataPoint(106, 20.2),
                new DataPoint(106.5, 20.4), new DataPoint(107, 20.6), new DataPoint(107.5, 20.8),
                new DataPoint(108, 21), new DataPoint(108.5, 21.2), new DataPoint(109, 21.4),
                new DataPoint(109.5, 21.7), new DataPoint(110, 21.9)
        });

        graph_A_PvsL.addSeries(Dossd);
        Dossd.setColor(Color.rgb(250,105,0));

        LineGraphSeries<DataPoint> Tressd = new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(45, 3.3), new DataPoint(45.5, 3.4), new DataPoint(46, 3.5),
                new DataPoint(46.5, 3.6), new DataPoint(47, 3.7), new DataPoint(47.5, 3.8),
                new DataPoint(48, 3.9), new DataPoint(48.5, 4), new DataPoint(49, 4.2),
                new DataPoint(49.5, 4.3), new DataPoint(50, 4.4), new DataPoint(50.5, 4.5),
                new DataPoint(51, 4.7), new DataPoint(51.5, 4.8), new DataPoint(52, 5),
                new DataPoint(52.5, 5.1), new DataPoint(53, 5.3), new DataPoint(53.5, 5.4),
                new DataPoint(54, 5.6), new DataPoint(54.5, 5.8), new DataPoint(55, 6),
                new DataPoint(55.5, 6.1), new DataPoint(56, 6.3), new DataPoint(56.5, 6.5),
                new DataPoint(57, 6.7), new DataPoint(57.5, 6.9), new DataPoint(58, 7.1),
                new DataPoint(58.5, 7.2), new DataPoint(59, 7.4), new DataPoint(59.5, 7.6),
                new DataPoint(60, 7.8), new DataPoint(60.5, 8), new DataPoint(61, 8.1),
                new DataPoint(61.5, 8.3), new DataPoint(62, 8.5), new DataPoint(62.5, 8.6),
                new DataPoint(63, 8.8), new DataPoint(63.5, 8.9), new DataPoint(64, 9.1),
                new DataPoint(64.5, 9.3), new DataPoint(65, 9.4), new DataPoint(65.5, 9.6),
                new DataPoint(66, 9.7), new DataPoint(66.5, 9.9), new DataPoint(67, 10),
                new DataPoint(67.5, 10.2), new DataPoint(68, 10.3), new DataPoint(68.5, 10.5),
                new DataPoint(69, 10.6), new DataPoint(69.5, 10.8), new DataPoint(70, 10.9),
                new DataPoint(70.5, 11.1), new DataPoint(71, 11.2), new DataPoint(71.5, 11.3),
                new DataPoint(72, 11.5), new DataPoint(72.5, 11.6), new DataPoint(73, 11.8),
                new DataPoint(73.5, 11.9), new DataPoint(74, 12.1), new DataPoint(74.5, 12.2),
                new DataPoint(75, 12.3), new DataPoint(75.5, 12.5), new DataPoint(76, 12.6),
                new DataPoint(76.5, 12.7), new DataPoint(77, 12.8), new DataPoint(77.5, 13),
                new DataPoint(78, 13.1), new DataPoint(78.5, 13.2), new DataPoint(79, 13.3),
                new DataPoint(79.5, 13.4), new DataPoint(80, 13.6), new DataPoint(80.5, 13.7),
                new DataPoint(81, 13.8), new DataPoint(81.5, 13.9), new DataPoint(82, 14),
                new DataPoint(82.5, 14.2), new DataPoint(83, 14.3), new DataPoint(83.5, 14.4),
                new DataPoint(84, 14.6), new DataPoint(84.5, 14.7), new DataPoint(85, 14.9),
                new DataPoint(85.5, 15), new DataPoint(86, 15.2), new DataPoint(86.5, 15.3),
                new DataPoint(87, 15.5), new DataPoint(87.5, 15.6), new DataPoint(88, 15.8),
                new DataPoint(88.5, 15.9), new DataPoint(89, 16.1), new DataPoint(89.5, 16.2),
                new DataPoint(90, 16.4), new DataPoint(90.5, 16.5), new DataPoint(91, 16.7),
                new DataPoint(91.5, 16.8), new DataPoint(92, 17), new DataPoint(92.5, 17.1),
                new DataPoint(93, 17.3), new DataPoint(93.5, 17.4), new DataPoint(94, 17.6),
                new DataPoint(94.5, 17.7), new DataPoint(95, 17.9), new DataPoint(95.5, 18),
                new DataPoint(96, 18.2), new DataPoint(96.5, 18.4), new DataPoint(97, 18.5),
                new DataPoint(97.5, 18.7), new DataPoint(98, 18.9), new DataPoint(98.5, 19.1),
                new DataPoint(99, 19.2), new DataPoint(99.5, 19.4), new DataPoint(100, 19.6),
                new DataPoint(100.5, 19.8), new DataPoint(101, 20), new DataPoint(101.5, 20.2),
                new DataPoint(102, 20.4), new DataPoint(102.5, 20.6), new DataPoint(103, 20.8),
                new DataPoint(103.5, 21), new DataPoint(104, 21.2), new DataPoint(104.5, 21.5),
                new DataPoint(105, 21.7), new DataPoint(105.5, 21.9), new DataPoint(106, 22.1),
                new DataPoint(106.5, 22.4), new DataPoint(107, 22.6), new DataPoint(107.5, 22.8),
                new DataPoint(108, 23.1), new DataPoint(108.5, 23.3), new DataPoint(109, 23.6),
                new DataPoint(109.5, 23.8), new DataPoint(110, 24.1)

        });

        graph_A_PvsL.addSeries(Tressd);
        Tressd.setColor(Color.RED);

        graph_A_PvsL.setTitle("Altura por el peso");

        graph_A_PvsL.getViewport().setYAxisBoundsManual(true);
        graph_A_PvsL.getViewport().setMinY(1.5);
        graph_A_PvsL.getViewport().setMaxY(25);

        graph_A_PvsL.getViewport().setXAxisBoundsManual(true);
        graph_A_PvsL.getViewport().setMinX(45);
        graph_A_PvsL.getViewport().setMaxX(112);

        graph_A_PvsL.getViewport().setScalable(true);
        graph_A_PvsL.getViewport().setScalableY(true);

        graph_A_PvsL.getViewport().setScrollable(true);
        graph_A_PvsL.getViewport().setScrollableY(true);



    }
    public void grafica_A_LvsP2(){

        GraphView graph_LvsP2 = (GraphView) findViewById(R.id.graph_OLvsP2);


        LineGraphSeries<DataPoint> media =new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(65,7.2),new DataPoint(65.5,7.4), new DataPoint(66,7.5),
                new DataPoint(66.5,7.6),new DataPoint(67,7.7),new DataPoint(67.5,7.8),
                new DataPoint(68,7.9),new DataPoint(68.5,8),new DataPoint(69,8.1),
                new DataPoint(69.5,8.2),new DataPoint(70,8.3),new DataPoint(70.5,8.4),
                new DataPoint(71,8.5),new DataPoint(71.5,8.6),new DataPoint(72,8.7),
                new DataPoint(72.5,8.8),new DataPoint(73,8.9),new DataPoint(73.5,9),
                new DataPoint(74,9.1),new DataPoint(74.5,9.2),new DataPoint(75,9.3),
                new DataPoint(75.5,9.4),new DataPoint(76,9.5),new DataPoint(76.5,9.6),
                new DataPoint(77,9.6),new DataPoint(77.5,9.7),new DataPoint(78,9.8),
                new DataPoint(78.5,9.9),new DataPoint(79,10),new DataPoint(79.5,10.1),
                new DataPoint(80,10.2),new DataPoint(80.5,10.3),new DataPoint(81,10.5),
                new DataPoint(81.5,10.6),new DataPoint(82,10.7),new DataPoint(82.5,10.8),
                new DataPoint(83,10.9),new DataPoint(83.5,11),new DataPoint(84,11.1),
                new DataPoint(84.5,11.3),new DataPoint(85,11.4),new DataPoint(85.5,11.5),
                new DataPoint(86,11.6),new DataPoint(86.5,11.8),new DataPoint(87,11.9),
                new DataPoint(87.5,12),new DataPoint(88,12.1),new DataPoint(88.5,12.3),
                new DataPoint(89,12.4),new DataPoint(89.5,12.5),new DataPoint(90,12.6),
                new DataPoint(90.5,12.8),new DataPoint(91,12.9),new DataPoint(91.5,13),
                new DataPoint(92,13.1),new DataPoint(92.5,13.3),new DataPoint(93,13.4),
                new DataPoint(93.5,13.5),new DataPoint(94,13.6),new DataPoint(94.5,13.8),
                new DataPoint(95,13.9), new DataPoint(95.5,14),new DataPoint(96,14.1),
                new DataPoint(96.5,14.3),new DataPoint(97,14.4),new DataPoint(97.5,14.5),
                new DataPoint(98,14.7),new DataPoint(98.5,14.8),new DataPoint(99,14.9),
                new DataPoint(99.5,15.1),new DataPoint(100,15.2),new DataPoint(100.5,15.4),
                new DataPoint(101,15.5),new DataPoint(101.5,15.7),new DataPoint(102,15.8),
                new DataPoint(102.5,16),new DataPoint(103,16.1),new DataPoint(103.5,16.3),
                new DataPoint(104,16.4),new DataPoint(104.5,16.6),new DataPoint(105,16.8),
                new DataPoint(105.5,16.9),new DataPoint(106,17.1),new DataPoint(106.5,17.3),
                new DataPoint(107,17.5),new DataPoint(107.5,17.7),new DataPoint(108,17.8),
                new DataPoint(108.5,18),new DataPoint(109,18.2),new DataPoint(109.5,18.4),
                new DataPoint(110,18.6),new DataPoint(110.5,18.8),new DataPoint(111,19),
                new DataPoint(111.5,19.2),new DataPoint(112,19.4),new DataPoint(112.5,19.6),
                new DataPoint(113,19.8),new DataPoint(113.5,20),new DataPoint(114,20.2),
                new DataPoint(114.5,20.5),new DataPoint(115,20.7),new DataPoint(115.5,20.9),
                new DataPoint(116,21.1),new DataPoint(116.5,21.3),new DataPoint(117,21.5),
                new DataPoint(117.5,21.7),new DataPoint(118,22),new DataPoint(118.5,22.2),
                new DataPoint(119,22.4),new DataPoint(119.5,22.6),new DataPoint(120,22.8)
        });
        graph_LvsP2.addSeries(media);
        media.setColor(Color.GREEN);

        LineGraphSeries<DataPoint> unossd=new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(65,6.6),new DataPoint(65.5,6.7), new DataPoint(66,6.8),
                new DataPoint(66.5,6.9),new DataPoint(67,7),new DataPoint(67.5,7.1),
                new DataPoint(68,7.2),new DataPoint(68.5,7.3),new DataPoint(69,7.4),
                new DataPoint(69.5,7.5),new DataPoint(70,7.6),new DataPoint(70.5,7.7),
                new DataPoint(71,7.8),new DataPoint(71.5,7.9),new DataPoint(72,8),
                new DataPoint(72.5,8.1),new DataPoint(73,8.1),new DataPoint(73.5,8.2),
                new DataPoint(74,8.3),new DataPoint(74.5,8.4),new DataPoint(75,8.5),
                new DataPoint(75.5,8.6),new DataPoint(76,8.7),new DataPoint(76.5,8.7),
                new DataPoint(77,8.8),new DataPoint(77.5,8.9),new DataPoint(78,9),
                new DataPoint(78.5,9.1),new DataPoint(79,9.2),new DataPoint(79.5,9.3),
                new DataPoint(80,9.4),new DataPoint(80.5,9.5),new DataPoint(81,9.6),
                new DataPoint(81.5,9.7),new DataPoint(82,9.8),new DataPoint(82.5,9.9),
                new DataPoint(83,10),new DataPoint(83.5,10.1),new DataPoint(84,10.2),
                new DataPoint(84.5,10.3),new DataPoint(85,10.4),new DataPoint(85.5,10.6),
                new DataPoint(86,10.7),new DataPoint(86.5,10.8),new DataPoint(87,10.9),
                new DataPoint(87.5,11),new DataPoint(88,11.1),new DataPoint(88.5,11.2),
                new DataPoint(89,11.4),new DataPoint(89.5,11.5),new DataPoint(90,11.6),
                new DataPoint(90.5,11.7),new DataPoint(91,11.8),new DataPoint(91.5,11.9),
                new DataPoint(92,12),new DataPoint(92.5,12.1),new DataPoint(93,12.3),
                new DataPoint(93.5,12.4),new DataPoint(94,12.5),new DataPoint(94.5,12.6),
                new DataPoint(95,12.7), new DataPoint(95.5,12.8),new DataPoint(96,12.9),
                new DataPoint(96.5,13.1),new DataPoint(97,13.2),new DataPoint(97.5,13.3),
                new DataPoint(98,13.4),new DataPoint(98.5,13.5),new DataPoint(99,13.7),
                new DataPoint(99.5,13.8),new DataPoint(100,13.9),new DataPoint(100.5,14.1),
                new DataPoint(101,14.2),new DataPoint(101.5,14.3),new DataPoint(102,14.5),
                new DataPoint(102.5,14.6),new DataPoint(103,14.7),new DataPoint(103.5,14.9),
                new DataPoint(104,15),new DataPoint(104.5,15.2),new DataPoint(105,15.3),
                new DataPoint(105.5,15.5),new DataPoint(106,15.6),new DataPoint(106.5,15.8),
                new DataPoint(107,15.9),new DataPoint(107.5,16.1),new DataPoint(108,16.3),
                new DataPoint(108.5,16.4),new DataPoint(109,16.6),new DataPoint(109.5,16.8),
                new DataPoint(110,17),new DataPoint(110.5,17.1),new DataPoint(111,17.3),
                new DataPoint(111.5,17.5),new DataPoint(112,17.7),new DataPoint(112.5,17.9),
                new DataPoint(113,18),new DataPoint(113.5,18.2),new DataPoint(114,18.4),
                new DataPoint(114.5,18.6),new DataPoint(115,18.8),new DataPoint(115.5,19),
                new DataPoint(116,19.2),new DataPoint(116.5,19.4),new DataPoint(117,19.6),
                new DataPoint(117.5,19.8),new DataPoint(118,19.9),new DataPoint(118.5,20.1),
                new DataPoint(119,20.3),new DataPoint(119.5,20.5),new DataPoint(120,20.7)
        });

        graph_LvsP2.addSeries(unossd);
        unossd.setColor(Color.YELLOW);

        LineGraphSeries<DataPoint> dossd=new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(65,6.1),new DataPoint(65.5,6.2),new DataPoint(66,6.3),
                new DataPoint(66.5,6.4),new DataPoint(67,6.4),new DataPoint(67.5,6.5),
                new DataPoint(68,6.6),new DataPoint(68.5,6.7),new DataPoint(69,6.8),
                new DataPoint(69.5,6.9),new DataPoint(70,7),new DataPoint(70.5,7.1),
                new DataPoint(71,7.1),new DataPoint(71.5,7.2),new DataPoint(72,7.3),
                new DataPoint(72.5,7.4),new DataPoint(73,7.5),new DataPoint(73.5,7.6),
                new DataPoint(74,7.6),new DataPoint(74.5,7.7),new DataPoint(75,7.8),
                new DataPoint(75.5,7.9),new DataPoint(76,8),new DataPoint(76.5,8),
                new DataPoint(77,8.1),new DataPoint(77.5,8.2),new DataPoint(78,8.3),
                new DataPoint(78.5,8.4),new DataPoint(79,8.4),new DataPoint(79.5,8.5),
                new DataPoint(80,8.6),new DataPoint(80.5,8.7),new DataPoint(81,8.8),
                new DataPoint(81.5,8.9),new DataPoint(82,9),new DataPoint(82.5,9.1),
                new DataPoint(83,9.2),new DataPoint(83.5,9.3),new DataPoint(84,9.4),
                new DataPoint(84.5,9.5),new DataPoint(85,9.6),new DataPoint(85.5,9.7),
                new DataPoint(86,9.8),new DataPoint(86.5,9.9),new DataPoint(87,10),
                new DataPoint(87.5,10.1),new DataPoint(88,10.2),new DataPoint(88.5,10.3),
                new DataPoint(89,10.4),new DataPoint(89.5,10.5),new DataPoint(90,10.6),
                new DataPoint(90.5,10.7),new DataPoint(91,10.9),new DataPoint(91.5,11),
                new DataPoint(92,11.1),new DataPoint(92.5,11.2),new DataPoint(93,11.3),
                new DataPoint(93.5,11.4),new DataPoint(94,11.5),new DataPoint(94.5,11.6),
                new DataPoint(95,11.7),new DataPoint(95.5,11.8),new DataPoint(96,11.9),
                new DataPoint(96.5,12),new DataPoint(97,12.1),new DataPoint(97.5,12.5),
                new DataPoint(98,12.3),new DataPoint(98.5,12.4),new DataPoint(99,12.5),
                new DataPoint(99.5,12.7),new DataPoint(100,12.8),new DataPoint(100.5,12.9),
                new DataPoint(101,13),new DataPoint(101.5,13.1),new DataPoint(102,13.3),
                new DataPoint(102.5,13.4),new DataPoint(103,13.5),new DataPoint(103.5,13.6),
                new DataPoint(104,13.8),new DataPoint(104.5,13.9),new DataPoint(105,14),
                new DataPoint(105.5,14.2),new DataPoint(106,14.3),new DataPoint(106.5,14.5),
                new DataPoint(107,14.6),new DataPoint(107.5,14.7),new DataPoint(108,14.9),
                new DataPoint(108.5,15),new DataPoint(109,15.2),new DataPoint(109.5,15.4),
                new DataPoint(110,15.5),new DataPoint(110.5,15.7),new DataPoint(111,15.8),
                new DataPoint(111.5,16),new DataPoint(112,16.2),new DataPoint(112.5,16.3),
                new DataPoint(113,16.5),new DataPoint(113.5,16.7),new DataPoint(114,16.8),
                new DataPoint(114.5,17),new DataPoint(115,17.2),new DataPoint(115.5,17.3),
                new DataPoint(116,17.5),new DataPoint(116.5,17.7),new DataPoint(117,17.8),
                new DataPoint(117.5,18),new DataPoint(118,18.2),new DataPoint(118.5,18.4),
                new DataPoint(119,18.5),new DataPoint(119.5,18.7),new DataPoint(120,18.9)

        });
        graph_LvsP2.addSeries(dossd);
        dossd.setColor(Color.rgb(250,105,0));

        LineGraphSeries<DataPoint> tressd=new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(65,5.6),new DataPoint(65.5,5.7),new DataPoint(66,5.8),
                new DataPoint(66.5,5.8),new DataPoint(67,5.9),new DataPoint(67.5,6),
                new DataPoint(68,6.1),new DataPoint(68.5,6.2),new DataPoint(69,6.3),
                new DataPoint(69.5,6.3),new DataPoint(70,6.4),new DataPoint(70.5,6.5),
                new DataPoint(71,6.6),new DataPoint(71.5,6.7),new DataPoint(72,6.7),
                new DataPoint(72.5,6.8),new DataPoint(73,6.9),new DataPoint(73.5,7),
                new DataPoint(74,7),new DataPoint(74.5,7.1),new DataPoint(75,7.2),
                new DataPoint(75.5,7.2),new DataPoint(76,7.3),new DataPoint(76.5,7.4),
                new DataPoint(77,7.5),new DataPoint(77.5,7.5),new DataPoint(78,7.6),
                new DataPoint(78.5,7.7),new DataPoint(79,7.8),new DataPoint(79.5,7.8),
                new DataPoint(80,7.9),new DataPoint(80.5,8),new DataPoint(81,8.1),
                new DataPoint(81.5,8.2),new DataPoint(82,8.3),new DataPoint(82.5,8.4),
                new DataPoint(83,8.5),new DataPoint(83.5,8.5),new DataPoint(84,8.6),
                new DataPoint(84.5,8.7),new DataPoint(85,8.8),new DataPoint(85.5,8.9),
                new DataPoint(86,9),new DataPoint(86.5,9.1),new DataPoint(87,9.2),
                new DataPoint(87.5,9.3),new DataPoint(88,9.4),new DataPoint(88.5,9.5),
                new DataPoint(89,9.6),new DataPoint(89.5,9.7),new DataPoint(90,9.8),
                new DataPoint(90.5,9.9),new DataPoint(91,10),new DataPoint(91.5,10.1),
                new DataPoint(92,10.2),new DataPoint(92.5,10.3),new DataPoint(93,10.4),
                new DataPoint(93.5,10.5),new DataPoint(94,10.6),new DataPoint(94.5,10.7),
                new DataPoint(95,10.8),new DataPoint(95.5,10.8),new DataPoint(96,10.9),
                new DataPoint(96.5,11),new DataPoint(97,11.1),new DataPoint(97.5,11.2),
                new DataPoint(98,11.3),new DataPoint(98.5,11.4),new DataPoint(99,11.5),
                new DataPoint(99.5,11.6),new DataPoint(100,11.7),new DataPoint(100.5,11.9),
                new DataPoint(101,12),new DataPoint(101.5,12.1),new DataPoint(102,12.2),
                new DataPoint(102.5,12.3),new DataPoint(103,12.4),new DataPoint(103.5,12.5),
                new DataPoint(104,12.6),new DataPoint(104.5,12.8),new DataPoint(105,12.9),
                new DataPoint(105.5,13),new DataPoint(106,13.1),new DataPoint(106.5,13.3),
                new DataPoint(107,13.4),new DataPoint(107.5,13.5),new DataPoint(108,13.7),
                new DataPoint(108.5,13.8),new DataPoint(109,13.9),new DataPoint(109.5,14.1),
                new DataPoint(110,14.2),new DataPoint(110.5,14.4),new DataPoint(111,14.5),
                new DataPoint(111.5,14.7),new DataPoint(112,14.8),new DataPoint(112.5,15),
                new DataPoint(113,15.1),new DataPoint(113.5,15.3),new DataPoint(114,15.4),
                new DataPoint(114.5,15.6),new DataPoint(115,15.7),new DataPoint(115.5,15.9),
                new DataPoint(116,16),new DataPoint(116.5,16.2),new DataPoint(117,16.3),
                new DataPoint(117.5,16.5),new DataPoint(118,16.6),new DataPoint(118.5,16.8),
                new DataPoint(119,16.9),new DataPoint(119.5,17.1),new DataPoint(120,17.3)

        });

        graph_LvsP2.addSeries(tressd);
        tressd.setColor(Color.RED);

        LineGraphSeries<DataPoint> Unosd = new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(65,7.9),new DataPoint(65.5,8.1),new DataPoint(66,8.2),
                new DataPoint(66.5,8.3),new DataPoint(67,8.4),new DataPoint(67.5,8.5),
                new DataPoint(68,8.7),new DataPoint(68.5,8.8),new DataPoint(69,8.9),
                new DataPoint(69.5,9),new DataPoint(70,9.1),new DataPoint(70.5,9.2),
                new DataPoint(71,9.3),new DataPoint(71.5,9.4),new DataPoint(72,9.5),
                new DataPoint(72.5,9.7),new DataPoint(73,9.8),new DataPoint(73.5,9.9),
                new DataPoint(74,10),new DataPoint(74.5,10.1),new DataPoint(75,10.2),
                new DataPoint(75.5,10.3),new DataPoint(76,10.4),new DataPoint(76.5,10.5),
                new DataPoint(77,10.6),new DataPoint(77.5,10.7),new DataPoint(78,10.8),
                new DataPoint(78.5,10.9),new DataPoint(79,11),new DataPoint(79.5,11.1),
                new DataPoint(80,11.2),new DataPoint(80.5,11.3),new DataPoint(81,11.4),
                new DataPoint(81.5,11.6),new DataPoint(82,11.7),new DataPoint(82.5,11.8),
                new DataPoint(83,11.9),new DataPoint(83.5,12.1),new DataPoint(84,12.2),
                new DataPoint(84.5,12.3),new DataPoint(85,12.5),new DataPoint(85.5,12.6),
                new DataPoint(86,12.7),new DataPoint(86.5,12.9),new DataPoint(87,13),
                new DataPoint(87.5,13.2),new DataPoint(88,13.3),new DataPoint(88.5,13.4),
                new DataPoint(89,13.6),new DataPoint(89.5,13.7),new DataPoint(90,13.8),
                new DataPoint(90.5,14),new DataPoint(91,14.1),new DataPoint(91.5,14.3),
                new DataPoint(92,14.4),new DataPoint(92.5,14.5),new DataPoint(93,14.7),
                new DataPoint(93.5,14.8),new DataPoint(94,14.9),new DataPoint(94.5,15.1),
                new DataPoint(95,15.2),new DataPoint(95.5,15.4),new DataPoint(96,15.5),
                new DataPoint(96.5,15.6),new DataPoint(97,15.8),new DataPoint(97.5,15.9),
                new DataPoint(98,16.1),new DataPoint(98.5,16.2),new DataPoint(99,16.4),
                new DataPoint(99.5,16.5),new DataPoint(100,16.7),new DataPoint(100.5,16.9),
                new DataPoint(101,17),new DataPoint(101.5,17.2),new DataPoint(102,17.4),
                new DataPoint(102.5,17.5),new DataPoint(103,107.7),new DataPoint(103.5,17.9),
                new DataPoint(104,18.1),new DataPoint(104.5,18.2),new DataPoint(105,18.4),
                new DataPoint(105.5,18.6),new DataPoint(106,18.8),new DataPoint(106.5,19),
                new DataPoint(107,19.2),new DataPoint(107.5,19.4),new DataPoint(108,19.6),
                new DataPoint(108.5,19.8),new DataPoint(109,20),new DataPoint(109.5,20.3),
                new DataPoint(110,20.5),new DataPoint(110.5,20.7),new DataPoint(111,20.9),
                new DataPoint(111.5,21.2),new DataPoint(112,21.4),new DataPoint(112.5,21.6),
                new DataPoint(113,21.8),new DataPoint(113.5,22.1),new DataPoint(114,22.3),
                new DataPoint(114.5,22.6),new DataPoint(115,22.8),new DataPoint(115.5,23),
                new DataPoint(116,23.2),new DataPoint(116.5,23.5),new DataPoint(117,23.8),
                new DataPoint(117.5,24),new DataPoint(118,24.2),new DataPoint(118.5,24.5),
                new DataPoint(119,24.7),new DataPoint(119.5,25),new DataPoint(120,25.2)
        });

        graph_LvsP2.addSeries(Unosd);
        Unosd.setColor(Color.YELLOW);

        LineGraphSeries<DataPoint> Dossd =new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(65,8.7),new DataPoint(65.5,8.9),new DataPoint(66,9),
                new DataPoint(66.5,9.1),new DataPoint(67,9.3),new DataPoint(67.5,9.4),
                new DataPoint(68,9.5),new DataPoint(68.5,9.7),new DataPoint(69,9.8),
                new DataPoint(69.5,9.9),new DataPoint(70,10),new DataPoint(70.5,10.1),
                new DataPoint(71,10.3),new DataPoint(71.5,10.4),new DataPoint(72,10.5),
                new DataPoint(72.5,10.6),new DataPoint(73,10.7),new DataPoint(73.5,10.8),
                new DataPoint(74,11),new DataPoint(74.5,11.1),new DataPoint(75,11.2),
                new DataPoint(75.5,11.3),new DataPoint(76,11.4),new DataPoint(76.5,11.5),
                new DataPoint(77,11.6),new DataPoint(77.5,11.7),new DataPoint(78,11.8),
                new DataPoint(78.5,12),new DataPoint(79,12.1),new DataPoint(79.5,12.2),
                new DataPoint(80,12.3),new DataPoint(80.5,12.4),new DataPoint(81,12.6),
                new DataPoint(81.5,12.7),new DataPoint(82,12.8),new DataPoint(82.5,13),
                new DataPoint(83,13.1),new DataPoint(83.5,13.3),new DataPoint(84,13.4),
                new DataPoint(84.5,13.5),new DataPoint(85,13.7),new DataPoint(85.5,13.8),
                new DataPoint(86,14),new DataPoint(86.5,14.2),new DataPoint(87,14.3),
                new DataPoint(87.5,14.5),new DataPoint(88,14.6),new DataPoint(88.5,14.8),
                new DataPoint(89,14.9),new DataPoint(89.5,15.1),new DataPoint(90,15.2),
                new DataPoint(90.5,15.4),new DataPoint(91,15.5),new DataPoint(91.5,15.7),
                new DataPoint(92,15.8),new DataPoint(92.5,16),new DataPoint(93,16.1),
                new DataPoint(93.5,16.3),new DataPoint(94,16.4),new DataPoint(94.5,16.6),
                new DataPoint(95,16.7),new DataPoint(95.5,16.9),new DataPoint(96,17),
                new DataPoint(96.5,17.2),new DataPoint(97,17.4),new DataPoint(97.5,17.5),
                new DataPoint(98,17.7),new DataPoint(98.5,17.9),new DataPoint(99,18),
                new DataPoint(99.5,18.2),new DataPoint(100,18.4),new DataPoint(100.5,18.6),
                new DataPoint(101,18.7),new DataPoint(101.5,18.9),new DataPoint(102,19.1),
                new DataPoint(102.5,19.3),new DataPoint(103,19.5),new DataPoint(103.5,19.7),
                new DataPoint(104,19.9),new DataPoint(104.5,20.1),new DataPoint(105,20.3),
                new DataPoint(105.5,20.5),new DataPoint(106,20.8),new DataPoint(106.5,21),
                new DataPoint(107,21.2),new DataPoint(107.5,21.4),new DataPoint(108,21.7),
                new DataPoint(108.5,21.9),new DataPoint(109,22.1),new DataPoint(109.5,22.4),
                new DataPoint(110,22.6),new DataPoint(110.5,22.9),new DataPoint(111,23.1),
                new DataPoint(111.5,23.4),new DataPoint(112,23.6),new DataPoint(112.5,23.9),
                new DataPoint(113,24.2),new DataPoint(113.5,24.4),new DataPoint(114,24.7),
                new DataPoint(114.5,25),new DataPoint(115,25.2),new DataPoint(115.5,25.5),
                new DataPoint(116,25.8),new DataPoint(116.5,26.1),new DataPoint(117,26.3),
                new DataPoint(117.5,26.6),new DataPoint(118,26.9),new DataPoint(118.5,27.2),
                new DataPoint(119,27.4),new DataPoint(119.5,27.7),new DataPoint(120,28)
        });

        graph_LvsP2.addSeries(Dossd);
        Dossd.setColor(Color.rgb(250,105,0));

        LineGraphSeries<DataPoint> Tressd =new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(65,9.7),new DataPoint(65.5,9.8),new DataPoint(66,10),
                new DataPoint(66.5,10.1),new DataPoint(67,10.2),new DataPoint(67.5,10.4),
                new DataPoint(68,10.5),new DataPoint(68.5,10.7),new DataPoint(69,10.8),
                new DataPoint(69.5,10.9),new DataPoint(70,11.1),new DataPoint(70.5,11.2),
                new DataPoint(71,11.3),new DataPoint(71.5,11.5),new DataPoint(72,11.6),
                new DataPoint(72.5,11.7),new DataPoint(73,11.8),new DataPoint(73.5,12),
                new DataPoint(74,12.1),new DataPoint(74.5,12.2),new DataPoint(75,12.3),
                new DataPoint(75.5,12.5),new DataPoint(76,12.6),new DataPoint(76.5,12.7),
                new DataPoint(77,12.8),new DataPoint(77.5,12.9),new DataPoint(78,13.1),
                new DataPoint(78.5,13.2),new DataPoint(79,13.3),new DataPoint(79.5,13.4),
                new DataPoint(80,13.6),new DataPoint(80.5,13.7),new DataPoint(81,13.9),
                new DataPoint(81.5,14),new DataPoint(82,14.1),new DataPoint(82.5,14.3),
                new DataPoint(83,14.5),new DataPoint(83.5,14.6),new DataPoint(84,14.8),
                new DataPoint(84.5,14.9),new DataPoint(85,15.1),new DataPoint(85.5,15.3),
                new DataPoint(86,15.4),new DataPoint(86.5,15.6),new DataPoint(87,15.8),
                new DataPoint(87.5,15.9),new DataPoint(88,16.1),new DataPoint(88.5,16.3),
                new DataPoint(89,16.4),new DataPoint(89.5,16.6),new DataPoint(90,16.8),
                new DataPoint(90.5,16.9),new DataPoint(91,17.1),new DataPoint(91.5,17.3),
                new DataPoint(92,17.4),new DataPoint(92.5,17.6),new DataPoint(93,17.8),
                new DataPoint(93.5,17.9),new DataPoint(94,18.1),new DataPoint(94.5,18.3),
                new DataPoint(95,18.5),new DataPoint(95.5,18.6),new DataPoint(96,18.8),
                new DataPoint(96.5,19),new DataPoint(97,19.2),new DataPoint(97.5,19.3),
                new DataPoint(98,19.5),new DataPoint(98.5,19.7),new DataPoint(99,19.9),
                new DataPoint(99.5,20.1),new DataPoint(100,20.3),new DataPoint(100.5,20.5),
                new DataPoint(101,20.7),new DataPoint(101.5,20.9),new DataPoint(102,21.1),
                new DataPoint(102.5,21.4),new DataPoint(103,21.6),new DataPoint(103.5,21.8),
                new DataPoint(104,22),new DataPoint(104.5,22.3),new DataPoint(105,22.5),
                new DataPoint(105.5,22.7),new DataPoint(106,23),new DataPoint(106.5,23.2),
                new DataPoint(107,23.5),new DataPoint(107.5,23.7),new DataPoint(108,24),
                new DataPoint(108.5,24.3),new DataPoint(109,24.5),new DataPoint(109.5,24.8),
                new DataPoint(110,25.1),new DataPoint(110.5,25.4),new DataPoint(111,25.7),
                new DataPoint(111.5,26),new DataPoint(112,26.2),new DataPoint(112.5,26.5),
                new DataPoint(113,26.8),new DataPoint(113.5,27.1),new DataPoint(114,27.4),
                new DataPoint(114.5,27.8),new DataPoint(115,28.1),new DataPoint(115.5,28.4),
                new DataPoint(116,28.7),new DataPoint(116.5,29),new DataPoint(117,29.3),
                new DataPoint(117.5,29.6),new DataPoint(118,29.9),new DataPoint(118.5,30.3),
                new DataPoint(119,30.6),new DataPoint(119.5,30.9),new DataPoint(120,31.2)

        });

        graph_LvsP2.addSeries(Tressd);
        Tressd.setColor(Color.RED);

        graph_LvsP2.setTitle("Longitud por la edad 2-5 años");

        graph_LvsP2.getViewport().setYAxisBoundsManual(true);
        graph_LvsP2.getViewport().setMinY(5.5);
        graph_LvsP2.getViewport().setMaxY(31);

        graph_LvsP2.getViewport().setXAxisBoundsManual(true);
        graph_LvsP2.getViewport().setMinX(65);
        graph_LvsP2.getViewport().setMaxX(121);


        graph_LvsP2.getViewport().setScalable(true);
        graph_LvsP2.getViewport().setScalableY(true);

        graph_LvsP2.getViewport().setScrollable(true);
        graph_LvsP2.getViewport().setScrollableY(true);*/

    }

    public void grafica_A_IMCvsE(){

        GraphView graph_A_IMC_E = (GraphView) findViewById(R.id.graph_A_IMCvsE);

        LineGraphSeries<DataPoint> median= new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(0,13.3),new DataPoint(1,14.6),new DataPoint(2,15.8),
                new DataPoint(3,16.4),new DataPoint(4,16.7),new DataPoint(5,16.8),
                new DataPoint(6,16.9),new DataPoint(7,16.9),new DataPoint(8,16.8),
                new DataPoint(9,16.7),new DataPoint(10,16.6),new DataPoint(11,16.5),
                new DataPoint(12,16.4),new DataPoint(13,16.2),new DataPoint(14,16.1),
                new DataPoint(15,16),new DataPoint(16,15.9),new DataPoint(17,15.8),
                new DataPoint(18,15.7),new DataPoint(19,15.7),new DataPoint(20,15.6),
                new DataPoint(21,15.5),new DataPoint(22,15.5),new DataPoint(23,15.4),
                new DataPoint(24,15.4),new DataPoint(25,15.7),new DataPoint(26,15.6),
                new DataPoint(27,15.6),new DataPoint(28,15.6),new DataPoint(29,15.6),
                new DataPoint(30,15.5),new DataPoint(31,15.5),new DataPoint(32,15.5),
                new DataPoint(33,15.5),new DataPoint(34,15.4),new DataPoint(35,15.4),
                new DataPoint(36,15.4),new DataPoint(37,15.4),new DataPoint(38,15.4),
                new DataPoint(39,15.3),new DataPoint(40,15.3),new DataPoint(41,15.3),
                new DataPoint(42,15.3),new DataPoint(43,15.3),new DataPoint(44,15.3),
                new DataPoint(45,15.3),new DataPoint(46,15.3),new DataPoint(47,15.3),
                new DataPoint(48,15.3),new DataPoint(49,15.3),new DataPoint(50,15.3),
                new DataPoint(51,15.3),new DataPoint(52,15.2),new DataPoint(53,15.3),
                new DataPoint(54,15.3),new DataPoint(55,15.3),new DataPoint(56,15.3),
                new DataPoint(57,15.3),new DataPoint(58,15.3),new DataPoint(59,15.3),
                new DataPoint(60,15.3)

        });

        graph_A_IMC_E.addSeries(median);
        median.setColor(Color.GREEN);

        LineGraphSeries<DataPoint> unosd = new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(0,12.2),new DataPoint(1,13.2),new DataPoint(2,14.3),
                new DataPoint(3,14.9),new DataPoint(4,15.2),new DataPoint(5,15.4),
                new DataPoint(6,15.5),new DataPoint(7,15.5),new DataPoint(8,15.4),
                new DataPoint(9,15.3),new DataPoint(10,15.2),new DataPoint(11,15.1),
                new DataPoint(12,15),new DataPoint(13,14.9),new DataPoint(14,14.8),
                new DataPoint(15,14.7),new DataPoint(16,14.6),new DataPoint(17,14.5),
                new DataPoint(18,14.4),new DataPoint(19,14.4),new DataPoint(20,14.3),
                new DataPoint(21,14.3),new DataPoint(22,14.2),new DataPoint(23,14.2),
                new DataPoint(24,14.2),new DataPoint(25,14.4),new DataPoint(26,14.4),
                new DataPoint(27,14.4),new DataPoint(28,14.3),new DataPoint(29,14.3),
                new DataPoint(30,14.3),new DataPoint(31,14.3),new DataPoint(32,14.3),
                new DataPoint(33,14.2),new DataPoint(34,14.2),new DataPoint(35,14.2),
                new DataPoint(36,14.2),new DataPoint(37,14.1),new DataPoint(38,14.1),
                new DataPoint(39,14.1),new DataPoint(40,14.1),new DataPoint(41,14.1),
                new DataPoint(42,14),new DataPoint(43,14),new DataPoint(44,14),
                new DataPoint(45,14),new DataPoint(46,14),new DataPoint(47,14),
                new DataPoint(48,14),new DataPoint(49,13.9),new DataPoint(50,13.9),
                new DataPoint(51,13.9),new DataPoint(52,13.9),new DataPoint(53,13.9),
                new DataPoint(54,13.9),new DataPoint(55,13.9),new DataPoint(56,13.9),
                new DataPoint(57,13.9),new DataPoint(58,13.9),new DataPoint(59,13.9),
                new DataPoint(60,13.9)
        });

        graph_A_IMC_E.addSeries(unosd);
        unosd.setColor(Color.YELLOW);

        LineGraphSeries<DataPoint> dossd = new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(0,11.1),new DataPoint(1,12),new DataPoint(2,13),
                new DataPoint(3,13.6),new DataPoint(4,13.9),new DataPoint(5,14.1),
                new DataPoint(6,14.1),new DataPoint(7,14.2),new DataPoint(8,14.1),
                new DataPoint(9,14.1),new DataPoint(10,14),new DataPoint(11,13.9),
                new DataPoint(12,13.8),new DataPoint(13,13.7),new DataPoint(14,13.6),
                new DataPoint(15,13.5),new DataPoint(16,13.5),new DataPoint(17,13.4),
                new DataPoint(18,13.3),new DataPoint(19,13.3),new DataPoint(20,13.2),
                new DataPoint(21,13.2),new DataPoint(22,13.1),new DataPoint(23,13.1),
                new DataPoint(24,13.1),new DataPoint(25,13.3),new DataPoint(26,13.3),
                new DataPoint(27,13.3),new DataPoint(28,13.3),new DataPoint(29,13.2),
                new DataPoint(30,13.2),new DataPoint(31,13.2),new DataPoint(32,13.2),
                new DataPoint(33,13.1),new DataPoint(34,13.1),new DataPoint(35,13.1),
                new DataPoint(36,13.1),new DataPoint(37,13.1),new DataPoint(38,13),
                new DataPoint(39,13),new DataPoint(40,13),new DataPoint(41,13),
                new DataPoint(42,12.9),new DataPoint(43,12.9),new DataPoint(44,12.9),
                new DataPoint(45,12.9),new DataPoint(46,12.9),new DataPoint(47,12.8),
                new DataPoint(48,12.8),new DataPoint(49,12.8),new DataPoint(50,12.8),
                new DataPoint(51,12.8),new DataPoint(52,12.8),new DataPoint(53,12.7),
                new DataPoint(54,12.7),new DataPoint(55,12.7),new DataPoint(56,12.7),
                new DataPoint(57,12.7),new DataPoint(58,12.7),new DataPoint(59,12.7),
                new DataPoint(60,12.7)
        });

        graph_A_IMC_E.addSeries(dossd);
        dossd.setColor(Color.rgb(250,105,0));

        LineGraphSeries<DataPoint> tressd = new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(0,10.1),new DataPoint(1,10.8),new DataPoint(2,11.8),
                new DataPoint(3,12.4),new DataPoint(4,12.7),new DataPoint(5,12.9),
                new DataPoint(6,13),new DataPoint(7,13),new DataPoint(8,13),
                new DataPoint(9,12.9),new DataPoint(10,12.9),new DataPoint(11,12.8),
                new DataPoint(12,12.7),new DataPoint(13,12.6),new DataPoint(14,12.6),
                new DataPoint(15,12.5),new DataPoint(16,12.4),new DataPoint(17,12.4),
                new DataPoint(18,12.3),new DataPoint(19,12.3),new DataPoint(20,12.2),
                new DataPoint(21,12.2),new DataPoint(22,12.2),new DataPoint(23,12.2),
                new DataPoint(24,12.1),new DataPoint(25,12.3),new DataPoint(26,12.3),
                new DataPoint(27,12.3),new DataPoint(28,12.3),new DataPoint(29,12.3),
                new DataPoint(30,12.3),new DataPoint(31,12.2),new DataPoint(32,12.2),
                new DataPoint(33,12.2),new DataPoint(34,12.2),new DataPoint(35,12.1),
                new DataPoint(36,12.1),new DataPoint(37,12.1),new DataPoint(38,12.1),
                new DataPoint(39,12),new DataPoint(40,12),new DataPoint(41,12),
                new DataPoint(42,12),new DataPoint(43,11.9),new DataPoint(44,11.9),
                new DataPoint(45,11.9),new DataPoint(46,11.9),new DataPoint(47,11.8),
                new DataPoint(48,11.8),new DataPoint(49,11.8),new DataPoint(50,11.8),
                new DataPoint(51,11.8),new DataPoint(52,11.7),new DataPoint(53,11.7),
                new DataPoint(54,11.7),new DataPoint(55,11.7),new DataPoint(56,11.7),
                new DataPoint(57,11.7),new DataPoint(58,11.7),new DataPoint(59,11.6),
                new DataPoint(60,11.6)

        });

        graph_A_IMC_E.addSeries(tressd);
        tressd.setColor(Color.RED);

        LineGraphSeries<DataPoint> Unosd = new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(0,14.6),new DataPoint(1,16),new DataPoint(2,17.3),
                new DataPoint(3,17.9),new DataPoint(4,18.3),new DataPoint(5,18.4),
                new DataPoint(6,18.5),new DataPoint(7,18.5),new DataPoint(8,18.4),
                new DataPoint(9,18.3),new DataPoint(10,18.2),new DataPoint(11,18),
                new DataPoint(12,17.9),new DataPoint(13,17.7),new DataPoint(14,17.6),
                new DataPoint(15,17.5),new DataPoint(16,17.4),new DataPoint(17,17.3),
                new DataPoint(18,17.2),new DataPoint(19,17.1),new DataPoint(20,17),
                new DataPoint(21,17),new DataPoint(22,16.9),new DataPoint(23,16.9),
                new DataPoint(24,16.8),new DataPoint(25,17.1),new DataPoint(26,17),
                new DataPoint(27,17),new DataPoint(28,17),new DataPoint(29,17),
                new DataPoint(30,16.9),new DataPoint(31,16.9),new DataPoint(32,16.9),
                new DataPoint(33,16.9),new DataPoint(34,16.8),new DataPoint(35,16.8),
                new DataPoint(36,16.8),new DataPoint(37,16.8),new DataPoint(38,16.8),
                new DataPoint(39,16.8),new DataPoint(40,16.8),new DataPoint(41,16.8),
                new DataPoint(42,16.8),new DataPoint(43,16.8),new DataPoint(44,16.8),
                new DataPoint(45,16.8),new DataPoint(46,16.8),new DataPoint(47,16.8),
                new DataPoint(48,16.8),new DataPoint(49,16.8),new DataPoint(50,16.8),
                new DataPoint(51,16.8),new DataPoint(52,16.8),new DataPoint(53,16.8),
                new DataPoint(54,16.8),new DataPoint(55,16.8),new DataPoint(56,16.8),
                new DataPoint(57,16.9),new DataPoint(58,16.9),new DataPoint(59,16.9),
                new DataPoint(60,16.9)
        });

        graph_A_IMC_E.addSeries(Unosd);
        Unosd.setColor(Color.YELLOW);

        LineGraphSeries<DataPoint> Dossd = new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(0,16.1),new DataPoint(1,17.5),new DataPoint(2,19),
                new DataPoint(3,19.7),new DataPoint(4,20),new DataPoint(5,20.2),
                new DataPoint(6,20.3),new DataPoint(7,20.3),new DataPoint(8,20.2),
                new DataPoint(9,20.1),new DataPoint(10,19.9),new DataPoint(11,19.8),
                new DataPoint(12,19.6),new DataPoint(13,19.5),new DataPoint(14,19.3),
                new DataPoint(15,19.2),new DataPoint(16,19.1),new DataPoint(17,18.9),
                new DataPoint(18,18.8),new DataPoint(19,18.8),new DataPoint(20,18.7),
                new DataPoint(21,18.6),new DataPoint(22,18.5),new DataPoint(23,18.5),
                new DataPoint(24,18.4),new DataPoint(25,18.7),new DataPoint(26,18.7),
                new DataPoint(27,18.6),new DataPoint(28,18.6),new DataPoint(29,18.6),
                new DataPoint(30,18.5),new DataPoint(31,18.5),new DataPoint(32,18.5),
                new DataPoint(33,18.5),new DataPoint(34,18.5),new DataPoint(35,18.4),
                new DataPoint(36,18.4),new DataPoint(37,18.4),new DataPoint(38,18.4),
                new DataPoint(39,18.4),new DataPoint(40,18.4),new DataPoint(41,18.4),
                new DataPoint(42,18.4),new DataPoint(43,18.4),new DataPoint(44,18.5),
                new DataPoint(45,18.5),new DataPoint(46,18.5),new DataPoint(47,18.5),
                new DataPoint(48,18.5),new DataPoint(49,18.5),new DataPoint(50,18.6),
                new DataPoint(51,18.6),new DataPoint(52,18.6),new DataPoint(53,18.6),
                new DataPoint(54,18.7),new DataPoint(55,18.7),new DataPoint(56,18.7),
                new DataPoint(57,18.7),new DataPoint(58,18.8),new DataPoint(59,18.8),
                new DataPoint(60,18.8)
        });

        graph_A_IMC_E.addSeries(Dossd);
        Dossd.setColor(Color.rgb(250,105,0));

        LineGraphSeries<DataPoint> Tressd = new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(0,17.7),new DataPoint(1,19.1),new DataPoint(2,20.7),
                new DataPoint(3,21.5),new DataPoint(4,22),new DataPoint(5,22.2),
                new DataPoint(6,22.3),new DataPoint(7,22.3),new DataPoint(8,22.2),
                new DataPoint(9,22.1),new DataPoint(10,21.9),new DataPoint(11,21.8),
                new DataPoint(12,21.6),new DataPoint(13,21.4),new DataPoint(14,21.3),
                new DataPoint(15,21.1),new DataPoint(16,21),new DataPoint(17,20.9),
                new DataPoint(18,20.8),new DataPoint(19,20.7),new DataPoint(20,20.6),
                new DataPoint(21,20.5),new DataPoint(22,20.4),new DataPoint(23,20.4),
                new DataPoint(24,20.6),new DataPoint(25,20.6),new DataPoint(26,20.6),
                new DataPoint(27,20.5),new DataPoint(28,20.5),new DataPoint(29,20.4),
                new DataPoint(30,20.4),new DataPoint(31,20.4),new DataPoint(32,20.4),
                new DataPoint(33,20.3),new DataPoint(34,20.3),new DataPoint(35,20.3),
                new DataPoint(36,20.3),new DataPoint(37,20.3),new DataPoint(38,20.3),
                new DataPoint(39,20.3),new DataPoint(40,20.3),new DataPoint(41,20.4),
                new DataPoint(42,20.4),new DataPoint(43,20.4),new DataPoint(44,20.4),
                new DataPoint(45,20.5),new DataPoint(46,20.5),new DataPoint(47,20.5),
                new DataPoint(48,20.6),new DataPoint(49,20.6),new DataPoint(50,20.7),
                new DataPoint(51,20.7),new DataPoint(52,20.7),new DataPoint(53,20.8),
                new DataPoint(54,20.8),new DataPoint(55,20.9),new DataPoint(56,20.9),
                new DataPoint(57,21),new DataPoint(58,21),new DataPoint(59,21),
                new DataPoint(60,21.1)
        }) ;

        graph_A_IMC_E.addSeries(Tressd);
        Tressd.setColor(Color.RED);

        //------------------------------

        graph_A_IMC_E.addSeries(pointIMC);
        pointIMC.setDrawDataPoints(true);
        pointIMC.setDataPointsRadius(8);
        pointIMC.setColor(Color.rgb(21,43,60));

        //------------------------------


        graph_A_IMC_E.setTitle("kg/m2 - meses");
        graph_A_IMC_E.setTitleColor(Color.rgb(117,117,117));

        graph_A_IMC_E.getGridLabelRenderer().getGridStyle().drawHorizontal();

        graph_A_IMC_E.getViewport().setYAxisBoundsManual(true);
        graph_A_IMC_E.getViewport().setMinY(10);
        graph_A_IMC_E.getViewport().setMaxY(24);

        graph_A_IMC_E.getViewport().setXAxisBoundsManual(true);
        graph_A_IMC_E.getViewport().setMinX(0);
        graph_A_IMC_E.getViewport().setMaxX(61);
    }
    public void grafica_A_CvsE(){

        GraphView graph_A_C_E = (GraphView) findViewById(R.id.graph_A_CvsE);

        LineGraphSeries<DataPoint> media =new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(0,33.9),new DataPoint(1,36.5),new DataPoint(2,38.3),
                new DataPoint(3,39.5),new DataPoint(4,40.6),new DataPoint(5,41.5),
                new DataPoint(6,42.2),new DataPoint(7,42.8),new DataPoint(8,43.4),
                new DataPoint(9,43.8),new DataPoint(10,44.2),new DataPoint(11,44.6),
                new DataPoint(12,44.9),new DataPoint(13,45.2),new DataPoint(14,45.4),
                new DataPoint(15,45.7),new DataPoint(16,45.9),new DataPoint(17,46.1),
                new DataPoint(18,46.2),new DataPoint(19,46.4),new DataPoint(20,46.6),
                new DataPoint(21,46.7),new DataPoint(22,46.9),new DataPoint(23,47),
                new DataPoint(24,47.2),new DataPoint(25,47.3),new DataPoint(26,47.5),
                new DataPoint(27,47.6),new DataPoint(28,47.7),new DataPoint(29,47.8),
                new DataPoint(30,47.9),new DataPoint(31,48),new DataPoint(32,48.1),
                new DataPoint(33,48.2),new DataPoint(34,48.3),new DataPoint(35,48.4),
                new DataPoint(36,48.5),new DataPoint(37,48.6),new DataPoint(38,48.7),
                new DataPoint(39,48.7),new DataPoint(40,48.8),new DataPoint(41,48.9),
                new DataPoint(42,49),new DataPoint(43,49),new DataPoint(44,49.1),
                new DataPoint(45,49.2),new DataPoint(46,49.2),new DataPoint(47,49.3),
                new DataPoint(48,49.3),new DataPoint(49,49.4),new DataPoint(50,49.4),
                new DataPoint(51,49.5),new DataPoint(52,49.5),new DataPoint(53,49.6),
                new DataPoint(54,49.6),new DataPoint(55,49.7),new DataPoint(56,49.7),
                new DataPoint(57,49.8),new DataPoint(58,49.8),new DataPoint(59,49.9),
                new DataPoint(60,49.9)
        });

        graph_A_C_E.addSeries(media);
        media.setColor(Color.GREEN);

        LineGraphSeries<DataPoint> unossd =new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(0,32.7), new DataPoint(1,35.4) ,new DataPoint(2,37),
                new DataPoint(3,38.3), new DataPoint(4,39.3) ,new DataPoint(5,40.2),
                new DataPoint(6,40.9), new DataPoint(7,41.5) ,new DataPoint(8,42),
                new DataPoint(9,42.5), new DataPoint(10,42.9),new DataPoint(11,43.2),
                new DataPoint(12,43.5),new DataPoint(13,43.8),new DataPoint(14,44.1),
                new DataPoint(15,44.3),new DataPoint(16,44.5),new DataPoint(17,44.7),
                new DataPoint(18,44.9),new DataPoint(19,45)  ,new DataPoint(20,45.2),
                new DataPoint(21,45.3),new DataPoint(22,45.5),new DataPoint(23,45.6),
                new DataPoint(24,45.8),new DataPoint(25,45.9),new DataPoint(26,46.1),
                new DataPoint(27,46.2),new DataPoint(28,46.3),new DataPoint(29,46.4),
                new DataPoint(30,46.5),new DataPoint(31,46.6),new DataPoint(32,46.7),
                new DataPoint(33,46.8),new DataPoint(34,46.9),new DataPoint(35,47),
                new DataPoint(36,47.1),new DataPoint(37,47.2),new DataPoint(38,47.3),
                new DataPoint(39,47.3),new DataPoint(40,47.4),new DataPoint(41,47.5),
                new DataPoint(42,47.5),new DataPoint(43,47.6),new DataPoint(44,47.7),
                new DataPoint(45,47.7),new DataPoint(46,47.8),new DataPoint(47,47.9),
                new DataPoint(48,47.9),new DataPoint(49,48)  ,new DataPoint(50,48),
                new DataPoint(51,48.1),new DataPoint(52,48.1),new DataPoint(53,48.2),
                new DataPoint(54,48.2),new DataPoint(55,48.3),new DataPoint(56,48.3),
                new DataPoint(57,48.4),new DataPoint(58,48.4),new DataPoint(59,48.5),
                new DataPoint(60,48.5)
        });

        graph_A_C_E.addSeries(unossd);
        unossd.setColor(Color.YELLOW);

        LineGraphSeries<DataPoint> dossd = new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(0,31.5) ,new DataPoint(1,34.2),new DataPoint(2,35.8),
                new DataPoint(3,37.1) ,new DataPoint(4,38.1),new DataPoint(5,38.9),
                new DataPoint(6,39.6) ,new DataPoint(7,40.2),new DataPoint(8,40.7),
                new DataPoint(9,41.2) ,new DataPoint(10,41.5),new DataPoint(11,41.9),
                new DataPoint(12,42.2),new DataPoint(13,42.4),new DataPoint(14,42.7),
                new DataPoint(15,42.9),new DataPoint(16,43.1),new DataPoint(17,43.3),
                new DataPoint(18,43.5),new DataPoint(19,43.7),new DataPoint(20,43.9),
                new DataPoint(21,44)  ,new DataPoint(22,44.1),new DataPoint(23,44.3),
                new DataPoint(24,44.4),new DataPoint(25,44.5),new DataPoint(26,44.7),
                new DataPoint(27,44.8),new DataPoint(28,44.9),new DataPoint(29,45),
                new DataPoint(30,45.1),new DataPoint(31,45.2),new DataPoint(32,45.3),
                new DataPoint(33,45.4),new DataPoint(34,45.5),new DataPoint(35,45.6),
                new DataPoint(36,45.7),new DataPoint(37,45.8),new DataPoint(38,45.8),
                new DataPoint(39,45.9),new DataPoint(40,46)  ,new DataPoint(41,46.1),
                new DataPoint(42,46.1),new DataPoint(43,46.2),new DataPoint(44,46.3),
                new DataPoint(45,46.3),new DataPoint(46,46.4),new DataPoint(47,46.4),
                new DataPoint(48,46.5),new DataPoint(49,46.5),new DataPoint(50,46.6),
                new DataPoint(51,46.7),new DataPoint(52,46.7),new DataPoint(53,46.8),
                new DataPoint(54,46.8),new DataPoint(55,46.9),new DataPoint(56,46.9),
                new DataPoint(57,46.9),new DataPoint(58,47),new DataPoint(59,47),
                new DataPoint(60,47.1)

        });

        graph_A_C_E.addSeries(dossd);
        dossd.setColor(Color.rgb(250,105,0));

        LineGraphSeries<DataPoint> tressd = new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(0,30.3),new DataPoint(1,33),new DataPoint(2,34.6),
                new DataPoint(3,35.8),new DataPoint(4,36.8),new DataPoint(5,37.6),
                new DataPoint(6,38.3),new DataPoint(7,38.9),new DataPoint(8,39.4),
                new DataPoint(9,39.8),new DataPoint(10,40.2),new DataPoint(11,40.5),
                new DataPoint(12,40.8),new DataPoint(13,41.1),new DataPoint(14,41.3),
                new DataPoint(15,41.5),new DataPoint(16,41.7),new DataPoint(17,41.9),
                new DataPoint(18,42.1),new DataPoint(19,42.3),new DataPoint(20,42.4),
                new DataPoint(21,42.6),new DataPoint(22,42.7),new DataPoint(23,42.9),
                new DataPoint(24,43),new DataPoint(25,43.1),new DataPoint(26,43.3),
                new DataPoint(27,43.4),new DataPoint(28,43.5),new DataPoint(29,43.6),
                new DataPoint(30,43.7),new DataPoint(31,43.8),new DataPoint(32,43.9),
                new DataPoint(33,44),new DataPoint(34,44.1),new DataPoint(35,44.2),
                new DataPoint(36,44.3),new DataPoint(37,44.4),new DataPoint(38,44.4),
                new DataPoint(39,44.5),new DataPoint(40,44.6),new DataPoint(41,44.6),
                new DataPoint(42,44.7),new DataPoint(43,44.8),new DataPoint(44,44.8),
                new DataPoint(45,44.9),new DataPoint(46,45),new DataPoint(47,45),
                new DataPoint(48,45.1),new DataPoint(49,45.1),new DataPoint(50,45.2),
                new DataPoint(51,45.2),new DataPoint(52,45.3),new DataPoint(53,45.3),
                new DataPoint(54,45.4),new DataPoint(55,45.4),new DataPoint(56,45.5),
                new DataPoint(57,45.5),new DataPoint(58,45.6),new DataPoint(59,45.6),
                new DataPoint(60,45.7)

        });

        graph_A_C_E.addSeries(tressd);
        tressd.setColor(Color.RED);

        LineGraphSeries<DataPoint> Unossd =new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(0,35.1),new DataPoint(1,37.7),new DataPoint(2,39.5),
                new DataPoint(3,40.8),new DataPoint(4,41.8),new DataPoint(5,42.7),
                new DataPoint(6,43.5),new DataPoint(7,44.1),new DataPoint(8,44.7),
                new DataPoint(9,45.2),new DataPoint(10,45.6),new DataPoint(11,45.9),
                new DataPoint(12,46.3),new DataPoint(13,46.5),new DataPoint(14,46.8),
                new DataPoint(15,47),new DataPoint(16,47.2),new DataPoint(17,47.4),
                new DataPoint(18,47.6),new DataPoint(19,47.8),new DataPoint(20,48),
                new DataPoint(21,48.1),new DataPoint(22,48.3),new DataPoint(23,48.4),
                new DataPoint(24,48.6),new DataPoint(25,48.7),new DataPoint(26,48.9),
                new DataPoint(27,49),new DataPoint(28,49.1),new DataPoint(29,49.2),
                new DataPoint(30,49.3),new DataPoint(31,49.4),new DataPoint(32,49.6),
                new DataPoint(33,49.7),new DataPoint(34,49.7),new DataPoint(35,49.8),
                new DataPoint(36,49.9),new DataPoint(37,50),new DataPoint(38,50.1),
                new DataPoint(39,50.2),new DataPoint(40,50.2),new DataPoint(41,50.3),
                new DataPoint(42,50.4),new DataPoint(43,50.4),new DataPoint(44,50.5),
                new DataPoint(45,50.6),new DataPoint(46,50.6),new DataPoint(47,50.7),
                new DataPoint(48,50.8),new DataPoint(49,50.8),new DataPoint(50,50.9),
                new DataPoint(51,50.9),new DataPoint(52,51),new DataPoint(53,51),
                new DataPoint(54,51.1),new DataPoint(55,51.1),new DataPoint(56,51.2),
                new DataPoint(57,51.2),new DataPoint(58,51.3),new DataPoint(59,51.3),
                new DataPoint(60,51.3)

        });

        graph_A_C_E.addSeries(Unossd);
        Unossd.setColor(Color.YELLOW);

        LineGraphSeries<DataPoint> Dossd = new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(0,36.2),new DataPoint(1,38.9),new DataPoint(2,40.7),
                new DataPoint(3,42),new DataPoint(4,43.1),new DataPoint(5,44),
                new DataPoint(6,44.8),new DataPoint(7,45.5),new DataPoint(8,46),
                new DataPoint(9,46.5),new DataPoint(10,46.9),new DataPoint(11,47.3),
                new DataPoint(12,47.6),new DataPoint(13,47.9),new DataPoint(14,48.2),
                new DataPoint(15,48.4),new DataPoint(16,48.6),new DataPoint(17,48.8),
                new DataPoint(18,49),new DataPoint(19,49.2),new DataPoint(20,49.4),
                new DataPoint(21,49.5),new DataPoint(22,49.7),new DataPoint(23,49.8),
                new DataPoint(24,50),new DataPoint(25,50.1),new DataPoint(26,50.3),
                new DataPoint(27,50.4),new DataPoint(28,50.5),new DataPoint(29,50.6),
                new DataPoint(30,50.7),new DataPoint(31,50.9),new DataPoint(32,51),
                new DataPoint(33,51.1),new DataPoint(34,51.2),new DataPoint(35,51.2),
                new DataPoint(36,51.3),new DataPoint(37,51.4),new DataPoint(38,51.5),
                new DataPoint(39,51.6),new DataPoint(40,51.7),new DataPoint(41,51.7),
                new DataPoint(42,51.8),new DataPoint(43,51.9),new DataPoint(44,51.9),
                new DataPoint(45,52),new DataPoint(46,52.1),new DataPoint(47,52.1),
                new DataPoint(48,52.2),new DataPoint(49,52.2),new DataPoint(50,52.3),
                new DataPoint(51,52.3),new DataPoint(52,52.4),new DataPoint(53,52.4),
                new DataPoint(54,52.5),new DataPoint(55,52.6),new DataPoint(56,52.6),
                new DataPoint(57,52.6),new DataPoint(58,52.7),new DataPoint(59,52.7),
                new DataPoint(60,52.8)

        });

        graph_A_C_E.addSeries(Dossd);
        Dossd.setColor(Color.rgb(250,105,0));

        LineGraphSeries<DataPoint> Tressd = new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(0,37.4),new DataPoint(1,40.1),new DataPoint(2,41.9),
                new DataPoint(3,43.3),new DataPoint(4,44.4),new DataPoint(5,45.3),
                new DataPoint(6,46.1),new DataPoint(7,46.8),new DataPoint(8,47.4),
                new DataPoint(9,47.8),new DataPoint(10,48.3),new DataPoint(11,48.6),
                new DataPoint(12,49),new DataPoint(13,49.3),new DataPoint(14,49.5),
                new DataPoint(15,49.8),new DataPoint(16,50),new DataPoint(17,50.2),
                new DataPoint(18,50.4),new DataPoint(19,50.6),new DataPoint(20,50.7),
                new DataPoint(21,50.9),new DataPoint(22,51.1),new DataPoint(23,51.2),
                new DataPoint(24,51.4),new DataPoint(25,51.5),new DataPoint(26,51.7),
                new DataPoint(27,51.8),new DataPoint(28,51.9),new DataPoint(29,52),
                new DataPoint(30,52.2),new DataPoint(31,52.3),new DataPoint(32,52.4),
                new DataPoint(33,52.5),new DataPoint(34,52.6),new DataPoint(35,52.7),
                new DataPoint(36,52.7),new DataPoint(37,52.8),new DataPoint(38,52.9),
                new DataPoint(39,53),new DataPoint(40,53.1),new DataPoint(41,53.1),
                new DataPoint(42,53.2),new DataPoint(43,53.3),new DataPoint(44,53.3),
                new DataPoint(45,53.4),new DataPoint(46,53.5),new DataPoint(47,53.5),
                new DataPoint(48,53.6),new DataPoint(49,53.6),new DataPoint(50,53.7),
                new DataPoint(51,53.8),new DataPoint(52,53.8),new DataPoint(53,53.9),
                new DataPoint(54,53.9),new DataPoint(55,54),new DataPoint(56,54),
                new DataPoint(57,54.1),new DataPoint(58,54.1),new DataPoint(59,54.1),
                new DataPoint(60,54.2)

        });

        graph_A_C_E.addSeries(Tressd);
        Tressd.setColor(Color.RED);

        //------------------------------

        graph_A_C_E.addSeries(pointCabeza);
        pointCabeza.setDrawDataPoints(true);
        pointCabeza.setDataPointsRadius(8);
        pointCabeza.setColor(Color.rgb(21,43,60));

        //------------------------------


        graph_A_C_E.setTitle("cm - meses");
        graph_A_C_E.setTitleColor(Color.rgb(117,117,117));

        graph_A_C_E.getGridLabelRenderer().getGridStyle().drawHorizontal();

        graph_A_C_E.getViewport().setYAxisBoundsManual(true);
        graph_A_C_E.getViewport().setMinY(29);
        graph_A_C_E.getViewport().setMaxY(56);

        graph_A_C_E.getViewport().setXAxisBoundsManual(true);
        graph_A_C_E.getViewport().setMinX(0);
        graph_A_C_E.getViewport().setMaxX(61);

        //graph_A_C_E.getViewport().setScalable(true);
        //graph_A_C_E.getViewport().setScalableY(true);

        //graph_A_C_E.getViewport().setScrollable(true);
        //graph_A_C_E.getViewport().setScrollableY(true);
    }
}
