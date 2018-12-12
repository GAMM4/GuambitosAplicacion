package com.gammadelta.gambitos.Graficas;

import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.gammadelta.gambitos.R;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.PointsGraphSeries;

import java.util.ArrayList;

public class GraficasMedicoActivity extends AppCompatActivity {

    public ArrayList<String> pesoEdad = new ArrayList<String>();

    LineGraphSeries<DataPoint> pointPeso = new LineGraphSeries<>();
    LineGraphSeries<DataPoint> pointLongitud = new LineGraphSeries<>();
    LineGraphSeries<DataPoint> pointCabeza = new LineGraphSeries<>();
    LineGraphSeries<DataPoint> pointIMC = new LineGraphSeries<>();

    private Button datoPeso;
    private Button datoLongitud;
    private Button datoCabeza;
    private Button datoIMC;
    private TextView ultimoPeso;
    private TextView ultimoLongitud;
    private TextView ultimoCabeza;
    private TextView ultimoIMC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graficas_medico);

        datoPeso        = (Button) findViewById(R.id.dato_P);
        datoLongitud    = (Button) findViewById(R.id.dato_L);
        datoCabeza      = (Button) findViewById(R.id.dato_C);
        datoIMC         = (Button) findViewById(R.id.dato_IMC);
        ultimoPeso      = (TextView) findViewById(R.id.ultimo_P);
        ultimoLongitud  = (TextView) findViewById(R.id.ultimo_L);
        ultimoCabeza    = (TextView) findViewById(R.id.ultimo_C);
        ultimoIMC    = (TextView) findViewById(R.id.ultimo_IMC);

        grafica_O_PvsE();
        grafica_O_LvsE();
        grafica_O_LvsP();
        //grafica_O_LvsP2();
        grafica_O_IMCvsE();
        grafica_O_CvsE();

        datoPeso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogPeso();
            }
        });

    }

    public void showDialogPeso() {
        LayoutInflater layoutInflater = LayoutInflater.from(GraficasMedicoActivity.this);
        View promptView = layoutInflater.inflate(R.layout.input_dialogo, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(GraficasMedicoActivity.this);
        alertDialogBuilder.setView(promptView);

        final EditText dato1 = (EditText) promptView.findViewById(R.id.dato1);
        dato1.setHint("Nuevo peso");

        alertDialogBuilder.setCancelable(false).setPositiveButton("Crear", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        pointPeso.appendData(new DataPoint(27,28),true,1000);
                    }
                }).setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    public void grafica_O_PvsE(){
        GraphView graph_O_PvsE = (GraphView) findViewById(R.id.graph_O_PvsE);


        LineGraphSeries<DataPoint> median= new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(0,3.3), new DataPoint(1,4.50), new DataPoint(2,5.6),
                new DataPoint(3,6.4), new DataPoint(4,7), new DataPoint(5,7.5),
                new DataPoint(6,7.9), new DataPoint(7,8.3), new DataPoint(8,8.6),
                new DataPoint(9,8.9), new DataPoint(10,9.2), new DataPoint(11,9.4),
                new DataPoint(12,9.6), new DataPoint(13,9.9), new DataPoint(14,10.1),
                new DataPoint(15,10.3), new DataPoint(16,10.5), new DataPoint(17,10.7),
                new DataPoint(18,10.9), new DataPoint(19,11.1), new DataPoint(20,11.3),
                new DataPoint(21,11.5), new DataPoint(22,11.8), new DataPoint(23,12),
                new DataPoint(24,12.2), new DataPoint(25,12.4), new DataPoint(26,12.5),
                new DataPoint(27,12.7), new DataPoint(28,12.9), new DataPoint(29,13.1),
                new DataPoint(30,13.3), new DataPoint(31, 13.5), new DataPoint(32,13.7),
                new DataPoint(33,13.8), new DataPoint(34, 14), new DataPoint(35,14.2),
                new DataPoint(36,14.3), new DataPoint(37,14.5), new DataPoint(38,14.7),
                new DataPoint(39,14.8), new DataPoint(40,15), new DataPoint(41,15.2),
                new DataPoint(42,15.3), new DataPoint(43,15.5), new DataPoint(44,15.7),
                new DataPoint(45,15.8),new DataPoint(46,16), new DataPoint(47,16.2),
                new DataPoint(48,16.3),new DataPoint(49,16.5), new DataPoint(50,16.7),
                new DataPoint(51,16.8), new DataPoint(52,17), new DataPoint(53,17.2),
                new DataPoint(54,17.3), new DataPoint(55,17.5), new DataPoint(56,17.7),
                new DataPoint(57,17.8), new DataPoint(58,18), new DataPoint(59,18.2),
                new DataPoint(60,18.3)
        });
        graph_O_PvsE.addSeries(median);
        median.setColor(Color.GREEN);


        LineGraphSeries<DataPoint> unosd = new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(0,2.9), new DataPoint(1,3.9), new DataPoint(2,4.9),
                new DataPoint(3,5.7),new DataPoint(4,6.2), new DataPoint(5,6.7),
                new DataPoint(6,7.1), new DataPoint(7,7.4), new DataPoint(8,7.7),
                new DataPoint(9,8), new DataPoint(10,8.2), new DataPoint(11,8.4),
                new DataPoint(12,8.6), new DataPoint(13,8.8), new DataPoint(14,9),
                new DataPoint(15,9.2), new DataPoint(16,9.4), new DataPoint(17,9.6),
                new DataPoint(18,9.8), new DataPoint(19,10), new DataPoint(20,10.1),
                new DataPoint(21,10.3), new DataPoint(22,10.5), new DataPoint(23,10.7),
                new DataPoint(24,10.8), new DataPoint(25,11), new DataPoint(26,11.2),
                new DataPoint(27, 11.3), new DataPoint(28,11.5), new DataPoint(29,11.7),
                new DataPoint(30,11.8), new DataPoint(31,12), new DataPoint(32,12.1),
                new DataPoint(33,12.3), new DataPoint(34,12.4), new DataPoint(35,12.6),
                new DataPoint(36,12.7), new DataPoint(37,12.9), new DataPoint(38,13),
                new DataPoint(39,13.1), new DataPoint(40,13.3), new DataPoint(41,13.4),
                new DataPoint(42,13.6), new DataPoint(43,13.7), new DataPoint(44,13.8),
                new DataPoint(45,14), new DataPoint(46,14.1), new DataPoint(47,14.3),
                new DataPoint(48,14.4), new DataPoint(49,14.5), new DataPoint(50,14.7),
                new DataPoint(51,14.8), new DataPoint(52,15), new DataPoint(53,15.1),
                new DataPoint(54,15.2), new DataPoint(55,15.4), new DataPoint(56,15.5),
                new DataPoint(57,15.6), new DataPoint(58,15.8), new DataPoint(59,15.9),
                new DataPoint(60,16)
        });
        graph_O_PvsE.addSeries(unosd);
        unosd.setColor(Color.YELLOW);



        LineGraphSeries<DataPoint> dossd = new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(0,2.5), new DataPoint(1,3.4), new DataPoint(2,4.3),
                new DataPoint(3,5), new DataPoint(4,5.6), new DataPoint(5,6),
                new DataPoint(6,6.4),new DataPoint(7,6.7), new DataPoint(8,6.9),
                new DataPoint(9,7.1), new DataPoint(10,7.4), new DataPoint(11,7.6),
                new DataPoint(12,7.7), new DataPoint(13,7.9), new DataPoint(14,8.1),
                new DataPoint(15,8.3), new DataPoint(16,8.4), new DataPoint(17,8.6),
                new DataPoint(18,8.8), new DataPoint(19,8.9), new DataPoint(20, 9.1),
                new DataPoint(21,9.2), new DataPoint(22,9.4), new DataPoint(23,9.5),
                new DataPoint(24,9.7), new DataPoint(25,9.8), new DataPoint(26,10),
                new DataPoint(27,10.1), new DataPoint(28,10.2), new DataPoint(29,10.4),
                new DataPoint(30,10.5), new DataPoint(31,10.7), new DataPoint(32,10.8),
                new DataPoint(33,10.9), new DataPoint(34,11), new DataPoint(35,11.2),
                new DataPoint(36,11.3), new DataPoint(37,11.4), new DataPoint(38,11.5),
                new DataPoint(39,11.6), new DataPoint(40,11.8), new DataPoint(41,11.9),
                new DataPoint(42, 12), new DataPoint(43,12.1), new DataPoint(44,12.2),
                new DataPoint(45,12.4), new DataPoint(46,12.5), new DataPoint(47,12.60),
                new DataPoint(48,12.7), new DataPoint(49,12.8), new DataPoint(50,12.9),
                new DataPoint(51,13.1), new DataPoint(52,13.2), new DataPoint(53,13.3),
                new DataPoint(54,13.4), new DataPoint(55,13.5), new DataPoint(56,13.6),
                new DataPoint(57,13.7), new DataPoint(58,13.8), new DataPoint(59,14),
                new DataPoint(60,14.1)
        });

        graph_O_PvsE.addSeries(dossd);
        dossd.setColor(Color.rgb(250,105,0));

        LineGraphSeries<DataPoint> tressd = new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(0,2.1), new DataPoint(1,2.9), new DataPoint(2,3.8),
                new DataPoint(3,4.4), new DataPoint(4,4.9), new DataPoint(5,5.3),
                new DataPoint(6,5.7), new DataPoint(7,5.9), new DataPoint(8,6.2),
                new DataPoint(9,6.4), new DataPoint(10,6.6), new DataPoint(11,6.8),
                new DataPoint(12,6.9), new DataPoint(13,7.1), new DataPoint(14,7.2),
                new DataPoint(15,7.4), new DataPoint(16,7.5), new DataPoint(17,7.7),
                new DataPoint(18,7.8), new DataPoint(19,8), new DataPoint(20,8.1),
                new DataPoint(21,8.2),new DataPoint(22,8.4), new DataPoint(23,8.5),
                new DataPoint(24,8.6), new DataPoint(25,8.8), new DataPoint(26,8.9),
                new DataPoint(27,9), new DataPoint(28,9.1), new DataPoint(29,9.2),
                new DataPoint(30,9.4), new DataPoint(31,9.5), new DataPoint(32,9.6),
                new DataPoint(33,9.7), new DataPoint(34,9.8), new DataPoint(35, 9.9),
                new DataPoint(36,10), new DataPoint(37,10.1), new DataPoint(38,10.2),
                new DataPoint(39,10.3),new DataPoint(40,10.4), new DataPoint(41,10.5),
                new DataPoint(42,10.6), new DataPoint(43,10.7), new DataPoint(44,10.8),
                new DataPoint(45,10.9), new DataPoint(46,11), new DataPoint(47,11.1),
                new DataPoint(48,11.2),new DataPoint(49,11.3), new DataPoint(50,11.4),
                new DataPoint(51,11.5), new DataPoint(52,11.6), new DataPoint(53,11.7),
                new DataPoint(54,11.8), new DataPoint(55,11.9), new DataPoint(56,12),
                new DataPoint(57,12.1), new DataPoint(58,12.2), new DataPoint(59,12.3),
                new DataPoint(60,12.4)
        });

        graph_O_PvsE.addSeries(tressd);
        tressd.setColor(Color.RED);

        LineGraphSeries<DataPoint> Unosd=new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(0,3.9), new DataPoint(1,5.1),new DataPoint(2,6.3),
                new DataPoint(3,7.2),new DataPoint(4,7.8),new DataPoint(5,8.4),
                new DataPoint(6,8.8), new DataPoint(7,9.2),new DataPoint(8,9.6),
                new DataPoint(9,9.9), new DataPoint(10,10.2), new DataPoint(11,10.5),
                new DataPoint(12,10.8), new DataPoint(13,11), new DataPoint(14,11.3),
                new DataPoint(15,11.5), new DataPoint(16,11.7), new DataPoint(17,12),
                new DataPoint(18,12.2), new DataPoint(19,12.5), new DataPoint(20,12.7),
                new DataPoint(21,12.9), new DataPoint(22,13.2), new DataPoint(23,13.4),
                new DataPoint(24,13.6), new DataPoint(25,13.9), new DataPoint(26,14.1),
                new DataPoint(27,14.3), new DataPoint(28,14.5), new DataPoint(29,14.8),
                new DataPoint(30,15), new DataPoint(31,15.2), new DataPoint(32,15.4),
                new DataPoint(33,15.6), new DataPoint(34,15.8), new DataPoint(35,16),
                new DataPoint(36,16.2), new DataPoint(37,16.4),new DataPoint(38,16.6),
                new DataPoint(39,16.8), new DataPoint(40,17), new DataPoint(41,17.2),
                new DataPoint(42,17.4), new DataPoint(43,17.6), new DataPoint(44,17.8),
                new DataPoint(45,18), new DataPoint(46,18.2),new DataPoint(47,18.4),
                new DataPoint(48,18.6),new DataPoint(49,18.8), new DataPoint(50,19),
                new DataPoint(51,19.2), new DataPoint(52,19.4), new DataPoint(53,19.6),
                new DataPoint(54,19.80), new DataPoint(55,20), new DataPoint(56,20.2),
                new DataPoint(57,20.4), new DataPoint(58,20.6), new DataPoint(59,20.8),
                new DataPoint(60,21)
        });
        graph_O_PvsE.addSeries(Unosd);
        Unosd.setColor(Color.YELLOW);

        LineGraphSeries<DataPoint> Dossd= new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(0,4.4), new DataPoint(1,5.8), new DataPoint(2,7.1),
                new DataPoint(3,8), new DataPoint(4,8.7), new DataPoint(5,9.3),
                new DataPoint(6,9.8), new DataPoint(7,10.3), new DataPoint(8,10.7),
                new DataPoint(9,11), new DataPoint(10,11.4), new DataPoint(11,11.7),
                new DataPoint(12,12),new DataPoint(13,12.3), new DataPoint(14, 12.6),
                new DataPoint(15,12.8), new DataPoint(16,13.1), new DataPoint(17,13.4),
                new DataPoint(18,13.7), new DataPoint(19,13.9), new DataPoint(20,14.2),
                new DataPoint(21,14.5), new DataPoint(22, 14.7), new DataPoint(23,15),
                new DataPoint(24,15.3), new DataPoint(25,15.5), new DataPoint(26,15.8),
                new DataPoint(27,16.1),new DataPoint(28,16.3), new DataPoint(29,16.6),
                new DataPoint(30,16.9), new DataPoint(31,17.1), new DataPoint(32,17.4),
                new DataPoint(33,17.6), new DataPoint(34,17.8),new DataPoint(35,18.1),
                new DataPoint(36,18.3),new DataPoint(37,18.6),new DataPoint(38,18.8),
                new DataPoint(39,19),new DataPoint(40,19.3), new DataPoint(41,19.5),
                new DataPoint(42,19.7), new DataPoint(43,20), new DataPoint(44,20.2),
                new DataPoint(45,20.5), new DataPoint(46,20.7), new DataPoint(47,20.9),
                new DataPoint(48,21.2), new DataPoint(49,21.4), new DataPoint(50,21.7),
                new DataPoint(51,21.9), new DataPoint(52,22.2),new DataPoint(53,22.4),
                new DataPoint(54,22.7), new DataPoint(55,22.9), new DataPoint(56,23.2),
                new DataPoint(57,23.4), new DataPoint(58,23.7), new DataPoint(59,23.9),
                new DataPoint(60,24.2)
        });
        graph_O_PvsE.addSeries(Dossd);
        Dossd.setColor(Color.rgb(250,105,0));

        LineGraphSeries<DataPoint> Tressd = new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(0,5), new DataPoint(1,6.6), new DataPoint(2,8),
                new DataPoint(3,9), new DataPoint(4,9.7), new DataPoint(5,10.4),
                new DataPoint(6,10.9), new DataPoint(7,11.4), new DataPoint(8,11.9),
                new DataPoint(9,12.3), new DataPoint(10,12.7), new DataPoint(11,13),
                new DataPoint(12,13.3), new DataPoint(13,13.7), new DataPoint(14,14),
                new DataPoint(15,14.3),new DataPoint(16,14.6),new DataPoint(17,14.9),
                new DataPoint(18,15.3),new DataPoint(19,15.6),new DataPoint(20,15.9),
                new DataPoint(21,16.2),new DataPoint(22,16.5),new DataPoint(23,16.8),
                new DataPoint(24,17.1),new DataPoint(25,17.5),new DataPoint(26,17.8),
                new DataPoint(27,18.1),new DataPoint(28,18.4),new DataPoint(29,18.7),
                new DataPoint(30,19),new DataPoint(31,19.3),new DataPoint(32,19.6),
                new DataPoint(33,19.9),new DataPoint(34,20.2),new DataPoint(35,20.4),
                new DataPoint(36,20.7),new DataPoint(37,21),new DataPoint(38,21.3),
                new DataPoint(39,21.6),new DataPoint(40,21.9),new DataPoint(41,22.1),
                new DataPoint(42,22.4),new DataPoint(43,22.7),new DataPoint(44,23),
                new DataPoint(45,23.3),new DataPoint(46,23.6),new DataPoint(47,23.9),
                new DataPoint(48,24.2),new DataPoint(49,24.5),new DataPoint(50,24.8),
                new DataPoint(51,25.1),new DataPoint(52,25.4),new DataPoint(53,25.7),
                new DataPoint(54,26),new DataPoint(55,26.3),new DataPoint(56,26.6),
                new DataPoint(57,26.9),new DataPoint(58,27.2),new DataPoint(59,27.6),
                new DataPoint(60,27.9)
        });
        graph_O_PvsE.addSeries(Tressd);
        Tressd.setColor(Color.RED);

        //------------------------------

        pointPeso.appendData(new DataPoint(10,9),true,1000);
        pointPeso.appendData(new DataPoint(15,15),true,1000);
        pointPeso.appendData(new DataPoint(20,20),true,1000);
        graph_O_PvsE.addSeries(pointPeso);
        pointPeso.setDrawDataPoints(true);
        pointPeso.setDataPointsRadius(10);
        pointPeso.setColor(Color.BLACK);
        //------------------------------

        graph_O_PvsE.getViewport().setYAxisBoundsManual(true);
        graph_O_PvsE.getViewport().setMinY(2);
        graph_O_PvsE.getViewport().setMaxY(30);

        graph_O_PvsE.getViewport().setXAxisBoundsManual(true);
        graph_O_PvsE.getViewport().setMinX(0);
        graph_O_PvsE.getViewport().setMaxX(61);

        pointPeso.appendData(new DataPoint(25,25),true,1000);


        graph_O_PvsE.getViewport().setScalable(true);
        graph_O_PvsE.getViewport().setScalableY(true);



        graph_O_PvsE.getViewport().setScrollable(true);
        graph_O_PvsE.getViewport().setScrollableY(true);
    }
    public void grafica_O_LvsE(){



        GraphView graph_O_LvsE = (GraphView) findViewById(R.id.graph_O_LvsE);

        PointsGraphSeries<DataPoint> usuario= new PointsGraphSeries<>(new DataPoint[]{
                new DataPoint(1,55.2), new DataPoint(2,60), new DataPoint(3,60.8)
        });
        graph_O_LvsE.addSeries(usuario);
        usuario.setColor(Color.BLUE);
        usuario.setSize(8);

        LineGraphSeries<DataPoint> median = new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(0, 49.9), new DataPoint(1, 54.7), new DataPoint(2, 58.4),
                new DataPoint(3, 61.4), new DataPoint(4, 63.9), new DataPoint(5, 65.9),
                new DataPoint(6, 67.6), new DataPoint(7, 69.2), new DataPoint(8, 70.6),
                new DataPoint(9, 72), new DataPoint(10, 73.3), new DataPoint(11, 74.5),
                new DataPoint(12, 75.7), new DataPoint(13, 76.9), new DataPoint(14, 78),
                new DataPoint(15, 79.1), new DataPoint(16, 80.2), new DataPoint(17, 81.2),
                new DataPoint(18, 82.3), new DataPoint(19, 83.2), new DataPoint(20, 84.2),
                new DataPoint(21, 85.1), new DataPoint(22, 86), new DataPoint(23, 86.9),
                new DataPoint(24, 87.1), new DataPoint(25, 88), new DataPoint(26, 88.8),
                new DataPoint(27, 89.6), new DataPoint(28, 90.4), new DataPoint(29, 91.2),
                new DataPoint(30, 91.9), new DataPoint(31, 92.7), new DataPoint(32, 93.4),
                new DataPoint(33, 94.1), new DataPoint(34, 94.8), new DataPoint(35, 95.4),
                new DataPoint(36, 96.1), new DataPoint(37, 96.7), new DataPoint(38, 97.4),
                new DataPoint(39, 98), new DataPoint(40, 98.6), new DataPoint(41, 99.2),
                new DataPoint(42, 99.90), new DataPoint(43, 100.4), new DataPoint(44, 101),
                new DataPoint(45, 101.6), new DataPoint(46, 102.2), new DataPoint(47, 102.8),
                new DataPoint(48, 103.3), new DataPoint(49, 103.9), new DataPoint(50, 104.4),
                new DataPoint(51, 105), new DataPoint(52, 105.6), new DataPoint(53, 106.1),
                new DataPoint(54, 106.7), new DataPoint(55, 107.2), new DataPoint(56, 107.8),
                new DataPoint(57, 108.3), new DataPoint(58, 108.9), new DataPoint(59, 109.4),
                new DataPoint(60, 110)});

        graph_O_LvsE.addSeries(median);
        median.setColor(Color.GREEN);

        LineGraphSeries<DataPoint> unosd = new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(0, 48), new DataPoint(1, 52.8), new DataPoint(2, 56.4),
                new DataPoint(3, 59.4), new DataPoint(4, 61.80), new DataPoint(5, 63.8),
                new DataPoint(6, 65.5), new DataPoint(7, 67), new DataPoint(8, 68.4),
                new DataPoint(9, 69.70), new DataPoint(10, 71), new DataPoint(11, 72.2),
                new DataPoint(12, 73.4), new DataPoint(13, 74.5), new DataPoint(14, 75.6),
                new DataPoint(15, 76.6), new DataPoint(16, 77.6), new DataPoint(17, 78.6),
                new DataPoint(18, 79.6), new DataPoint(19, 80.5), new DataPoint(20, 81.4),
                new DataPoint(21, 82.3), new DataPoint(22, 83.1), new DataPoint(23, 83.9),
                new DataPoint(24, 84.1), new DataPoint(25, 84.9), new DataPoint(26, 85.6),
                new DataPoint(27, 86.4), new DataPoint(28, 87.1), new DataPoint(29, 87.8),
                new DataPoint(30, 88.5), new DataPoint(31, 89.2), new DataPoint(32, 89.9),
                new DataPoint(33, 90.5), new DataPoint(34, 91.1), new DataPoint(35, 91.8),
                new DataPoint(36, 92.4), new DataPoint(37, 93), new DataPoint(38, 93.6),
                new DataPoint(39, 94.2), new DataPoint(40, 94.7), new DataPoint(41, 95.3),
                new DataPoint(42, 95.9), new DataPoint(43, 96.4), new DataPoint(44, 97),
                new DataPoint(45, 97.5), new DataPoint(46, 98.1), new DataPoint(47, 98.6),
                new DataPoint(48, 99.1), new DataPoint(49, 99.7), new DataPoint(50, 100.2),
                new DataPoint(51, 100.7), new DataPoint(52, 101.2), new DataPoint(53, 101.7),
                new DataPoint(54, 102.3), new DataPoint(55, 102.8), new DataPoint(56, 103.3),
                new DataPoint(57, 103.8), new DataPoint(58, 104.3), new DataPoint(59, 104.8),
                new DataPoint(60, 105.3)});


        graph_O_LvsE.addSeries(unosd);
        unosd.setColor(Color.YELLOW);


        LineGraphSeries<DataPoint> dossd = new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(0, 46.1), new DataPoint(1, 50.8), new DataPoint(2, 54.4),
                new DataPoint(3, 57.3), new DataPoint(4, 59.7), new DataPoint(5, 61.7),
                new DataPoint(6, 63.3), new DataPoint(7, 64.8), new DataPoint(8, 66.2),
                new DataPoint(9, 67.5), new DataPoint(10, 68.7), new DataPoint(11, 69.9),
                new DataPoint(12, 71), new DataPoint(13, 72.1), new DataPoint(14, 73.1),
                new DataPoint(15, 74.1), new DataPoint(16, 75), new DataPoint(17, 76),
                new DataPoint(18, 76.9), new DataPoint(19, 77.7), new DataPoint(20, 78.6),
                new DataPoint(21, 79.4), new DataPoint(22, 80.2), new DataPoint(23, 81),
                new DataPoint(24, 81.7), new DataPoint(25, 82), new DataPoint(26, 82.5),
                new DataPoint(27, 83.1), new DataPoint(28, 83.8), new DataPoint(29, 84.5),
                new DataPoint(30, 85.1), new DataPoint(31, 85.7), new DataPoint(32, 86.4),
                new DataPoint(33, 86.9), new DataPoint(34, 87.5), new DataPoint(35, 88.1),
                new DataPoint(36, 88.7), new DataPoint(37, 89.2), new DataPoint(38, 89.8),
                new DataPoint(39, 90.3), new DataPoint(40, 90.9), new DataPoint(41, 91.4),
                new DataPoint(42, 91.9), new DataPoint(43, 92.4), new DataPoint(44, 93),
                new DataPoint(45, 93.5), new DataPoint(46, 94), new DataPoint(47, 94.4),
                new DataPoint(48, 94.9), new DataPoint(49, 95.4), new DataPoint(50, 95.9),
                new DataPoint(51, 96.4), new DataPoint(52, 96.9), new DataPoint(53, 97.4),
                new DataPoint(54, 97.8), new DataPoint(55, 98.3), new DataPoint(56, 98.8),
                new DataPoint(57, 99.3), new DataPoint(58, 99.7), new DataPoint(59, 100.2),
                new DataPoint(60, 100.7)
        });
        graph_O_LvsE.addSeries(dossd);
        dossd.setColor(Color.rgb(250, 105, 0));

        LineGraphSeries<DataPoint> tressd = new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(0, 44.2), new DataPoint(1, 48.9), new DataPoint(2, 52.4),
                new DataPoint(3, 55.3), new DataPoint(4, 57.6), new DataPoint(5, 59.6),
                new DataPoint(6, 61.2), new DataPoint(7, 62.7), new DataPoint(8, 64),
                new DataPoint(9, 65.2), new DataPoint(10, 66.4), new DataPoint(11, 67.6),
                new DataPoint(12, 68.6), new DataPoint(13, 69.6), new DataPoint(14, 70.6),
                new DataPoint(15, 71.6), new DataPoint(16, 72.5), new DataPoint(17, 73.3),
                new DataPoint(18, 74.2), new DataPoint(19, 75), new DataPoint(20, 75.8),
                new DataPoint(21, 76.5), new DataPoint(22, 77.2), new DataPoint(23, 78),
                new DataPoint(24, 78.7), new DataPoint(25, 79), new DataPoint(26, 79.3),
                new DataPoint(27, 79.9), new DataPoint(28, 80.5), new DataPoint(29, 81.1),
                new DataPoint(30, 81.7), new DataPoint(31, 82.3), new DataPoint(32, 82.8),
                new DataPoint(33, 83.4), new DataPoint(34, 83.9), new DataPoint(35, 84.4),
                new DataPoint(36, 85), new DataPoint(37, 85.5), new DataPoint(38, 86),
                new DataPoint(39, 86.5), new DataPoint(40, 87), new DataPoint(41, 87.5),
                new DataPoint(42, 88), new DataPoint(43, 88.4), new DataPoint(44, 88.9),
                new DataPoint(45, 89.4), new DataPoint(46, 89.8), new DataPoint(47, 90.3),
                new DataPoint(48, 90.7), new DataPoint(49, 91.2), new DataPoint(50, 91.6),
                new DataPoint(51, 92.1), new DataPoint(52, 92.5), new DataPoint(53, 93),
                new DataPoint(54, 93.4), new DataPoint(55, 93.9), new DataPoint(56, 94.3),
                new DataPoint(57, 94.7), new DataPoint(58, 95.2), new DataPoint(59, 95.6),
                new DataPoint(60, 96.1)
        });
        graph_O_LvsE.addSeries(tressd);
        tressd.setColor(Color.RED);

        LineGraphSeries<DataPoint> Unosd = new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(0, 51.8), new DataPoint(1, 56.7), new DataPoint(2, 60.4),
                new DataPoint(3, 63.5), new DataPoint(4, 66), new DataPoint(5, 68),
                new DataPoint(6, 69.8), new DataPoint(7, 71.3), new DataPoint(8, 72.8),
                new DataPoint(9, 74.2), new DataPoint(10, 75.6), new DataPoint(11, 76.9),
                new DataPoint(12, 78.1), new DataPoint(13, 79.3), new DataPoint(14, 80.5),
                new DataPoint(15, 81.7), new DataPoint(16, 82.8), new DataPoint(17, 83.9),
                new DataPoint(18, 85), new DataPoint(19, 86), new DataPoint(20, 87),
                new DataPoint(21, 88), new DataPoint(22, 89), new DataPoint(23, 89.9),
                new DataPoint(24, 90.8), new DataPoint(25, 91.1), new DataPoint(26, 92),
                new DataPoint(27, 92.9), new DataPoint(28, 93.7), new DataPoint(29, 94.5),
                new DataPoint(30, 95.3), new DataPoint(31, 96.1), new DataPoint(32, 96.9),
                new DataPoint(33, 97.6), new DataPoint(34, 98.4), new DataPoint(35, 99.1),
                new DataPoint(36, 99.8), new DataPoint(37, 100.5), new DataPoint(38, 101.2),
                new DataPoint(39, 101.8), new DataPoint(40, 102.5), new DataPoint(41, 103.2),
                new DataPoint(42, 103.8), new DataPoint(43, 104.5), new DataPoint(44, 105.1),
                new DataPoint(45, 105.7), new DataPoint(46, 106.3), new DataPoint(47, 106.9),
                new DataPoint(48, 107.5), new DataPoint(49, 108.1), new DataPoint(50, 108.7),
                new DataPoint(51, 109.3), new DataPoint(52, 109.9), new DataPoint(53, 110.5),
                new DataPoint(54, 111.1), new DataPoint(55, 111.7), new DataPoint(56, 112.3),
                new DataPoint(57, 112.8), new DataPoint(58, 113.4), new DataPoint(59, 114),
                new DataPoint(60, 114.6)
        });
        graph_O_LvsE.addSeries(Unosd);
        Unosd.setColor(Color.YELLOW);

        LineGraphSeries<DataPoint> Dossd = new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(0, 53.7), new DataPoint(1, 58.6), new DataPoint(2, 62.4),
                new DataPoint(3, 65.5), new DataPoint(4, 68), new DataPoint(5, 70.1),
                new DataPoint(6, 71.9), new DataPoint(7, 73.5), new DataPoint(8, 75),
                new DataPoint(9, 76.5), new DataPoint(10, 77.9), new DataPoint(11, 79.2),
                new DataPoint(12, 80.5), new DataPoint(13, 81.8), new DataPoint(14, 83),
                new DataPoint(15, 84.2), new DataPoint(16, 85.4), new DataPoint(17, 86.5),
                new DataPoint(18, 87.7), new DataPoint(19, 88.8), new DataPoint(20, 89.8),
                new DataPoint(21, 90.9), new DataPoint(22, 91.9), new DataPoint(23, 92.9),
                new DataPoint(24, 93.9), new DataPoint(25, 94.2), new DataPoint(26, 95.2),
                new DataPoint(27, 96.1), new DataPoint(28, 97), new DataPoint(29, 97.9),
                new DataPoint(30, 98.7), new DataPoint(31, 99.6), new DataPoint(32, 100.4),
                new DataPoint(33, 101.2), new DataPoint(34, 102), new DataPoint(35, 102.7),
                new DataPoint(36, 103.5), new DataPoint(37, 104.2), new DataPoint(38, 105),
                new DataPoint(39, 105.7), new DataPoint(40, 106.4), new DataPoint(41, 107.1),
                new DataPoint(42, 107.8), new DataPoint(43, 108.5), new DataPoint(44, 109.1),
                new DataPoint(45, 109.8), new DataPoint(46, 110.4), new DataPoint(47, 111.1),
                new DataPoint(48, 111.7), new DataPoint(49, 112.4), new DataPoint(50, 113),
                new DataPoint(51, 113.6), new DataPoint(52, 114.2), new DataPoint(53, 114.9),
                new DataPoint(54, 115.5), new DataPoint(55, 116.1), new DataPoint(56, 116.7),
                new DataPoint(57, 117.4), new DataPoint(58, 118), new DataPoint(59, 118.6),
                new DataPoint(60, 119.2)
        });
        graph_O_LvsE.addSeries(Dossd);
        Dossd.setColor(Color.rgb(250, 105, 0));

        LineGraphSeries<DataPoint> Tressd = new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(0, 55.6), new DataPoint(1, 60.6), new DataPoint(2, 64.4),
                new DataPoint(3, 67.6), new DataPoint(4, 70.1), new DataPoint(5, 72.2),
                new DataPoint(6, 74), new DataPoint(7, 75.7), new DataPoint(8, 77.2),
                new DataPoint(9, 78.7), new DataPoint(10, 80.1), new DataPoint(11, 81.5),
                new DataPoint(12, 82.9), new DataPoint(13, 84.2), new DataPoint(14, 85.5),
                new DataPoint(15, 86.7), new DataPoint(16, 88), new DataPoint(17, 89.2),
                new DataPoint(18, 90.4), new DataPoint(19, 91.5), new DataPoint(20, 92.6),
                new DataPoint(21, 93.8), new DataPoint(22, 94.9), new DataPoint(23, 95.9),
                new DataPoint(24, 97), new DataPoint(25, 97.3), new DataPoint(26, 98.3),
                new DataPoint(27, 99.3), new DataPoint(28, 100.3), new DataPoint(29, 101.2),
                new DataPoint(30, 102.1), new DataPoint(31, 103), new DataPoint(32, 103.9),
                new DataPoint(33, 104.8), new DataPoint(34, 105.6), new DataPoint(35, 106.4),
                new DataPoint(36, 107.2), new DataPoint(37, 108), new DataPoint(38, 108.8),
                new DataPoint(39, 109.5), new DataPoint(40, 110.3), new DataPoint(41, 111),
                new DataPoint(42, 111.7), new DataPoint(43, 112.5), new DataPoint(44, 113.2),
                new DataPoint(45, 113.9), new DataPoint(46, 114.6), new DataPoint(47, 115.2),
                new DataPoint(48, 115.9), new DataPoint(49, 116.6), new DataPoint(50, 117.3),
                new DataPoint(51, 117.9), new DataPoint(52, 118.6), new DataPoint(53, 119.2),
                new DataPoint(54, 119.9), new DataPoint(55, 120.6), new DataPoint(56, 121.6),
                new DataPoint(57, 121.9), new DataPoint(58, 122.6), new DataPoint(59, 123.2),
                new DataPoint(60, 123.9)
        });
        graph_O_LvsE.addSeries(Tressd);
        Tressd.setColor(Color.RED);


        graph_O_LvsE.setTitle("Altura por la Edad");

        graph_O_LvsE.getGridLabelRenderer().getGridStyle().drawHorizontal();

        graph_O_LvsE.getViewport().setYAxisBoundsManual(true);
        graph_O_LvsE.getViewport().setMinY(40);
        graph_O_LvsE.getViewport().setMaxY(125);

        graph_O_LvsE.getViewport().setXAxisBoundsManual(true);
        graph_O_LvsE.getViewport().setMinX(0);
        graph_O_LvsE.getViewport().setMaxX(61);


        graph_O_LvsE.getViewport().setScalable(true);
        graph_O_LvsE.getViewport().setScalableY(true);


        graph_O_LvsE.getViewport().setScrollable(true);
        graph_O_LvsE.getViewport().setScrollableY(true);
    }

    public void grafica_O_LvsP() {
        /*GraphView graph_O_PvsL = (GraphView) findViewById(R.id.grapH_OLvsP);



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

        graph_O_PvsL.addSeries(median);
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

        graph_O_PvsL.addSeries(unosd);
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
        graph_O_PvsL.addSeries(dossd);
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
        graph_O_PvsL.addSeries(tressd);
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

        graph_O_PvsL.addSeries(Unosd);
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

        graph_O_PvsL.addSeries(Dossd);
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

        graph_O_PvsL.addSeries(Tressd);
        Tressd.setColor(Color.RED);

        graph_O_PvsL.setTitle("Altura por el peso");

        graph_O_PvsL.getViewport().setYAxisBoundsManual(true);
        graph_O_PvsL.getViewport().setMinY(1.5);
        graph_O_PvsL.getViewport().setMaxY(25);

        graph_O_PvsL.getViewport().setXAxisBoundsManual(true);
        graph_O_PvsL.getViewport().setMinX(45);
        graph_O_PvsL.getViewport().setMaxX(112);

        graph_O_PvsL.getViewport().setScalable(true);
        graph_O_PvsL.getViewport().setScalableY(true);

        graph_O_PvsL.getViewport().setScrollable(true);
        graph_O_PvsL.getViewport().setScrollableY(true);



    }
    public void grafica_O_LvsP2(){

        GraphView graph_LvsP2 = (GraphView) findViewById(R.id.graph_OLvsP2);


        LineGraphSeries<DataPoint> media =new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(65,7.4), new DataPoint(65.5,7.6),new DataPoint(66,7.7),
                new DataPoint(66.5,7.8),new DataPoint(67,7.9),new DataPoint(67.5,8),
                new DataPoint(68,8.1),new DataPoint(68.5,8.2),new DataPoint(69,8.4),
                new DataPoint(69.5,8.5),new DataPoint(70,8.6),new DataPoint(70.5,8.7),
                new DataPoint(71,8.8),new DataPoint(71.5,8.9),new DataPoint(72,9),
                new DataPoint(72.5,9.1),new DataPoint(73,9.2),new DataPoint(73.5,9.3),
                new DataPoint(74,9.4),new DataPoint(74.5,9.5),new DataPoint(75,9.6),
                new DataPoint(75.5,9.7),new DataPoint(76,9.8),new DataPoint(76.5,9.9),
                new DataPoint(77,10),new DataPoint(77.5,10.1),new DataPoint(78,10.2),
                new DataPoint(78.5,10.3),new DataPoint(79,10.4),new DataPoint(79.5,10.5),
                new DataPoint(80,10.6),new DataPoint(80.5,10.7),new DataPoint(81,10.8),
                new DataPoint(81.5,10.9),new DataPoint(82,11),new DataPoint(82.5,11.1),
                new DataPoint(83,11.2),new DataPoint(83.5,11.3),new DataPoint(84,11.4),
                new DataPoint(84.5,11.5),new DataPoint(85,11.7),new DataPoint(85.5,11.8),
                new DataPoint(86,11.9),new DataPoint(86.5,12),new DataPoint(87,12.2),
                new DataPoint(87.5,12.3),new DataPoint(88,12.4),new DataPoint(88.5,12.5),
                new DataPoint(89,12.6),new DataPoint(89.5,12.8),new DataPoint(90,12.9),
                new DataPoint(90.5,13),new DataPoint(91,13.1),new DataPoint(91.5,13.2),
                new DataPoint(92,13.4),new DataPoint(92.5,13.5),new DataPoint(93,13.6),
                new DataPoint(93.5,13.7),new DataPoint(94,13.8),new DataPoint(94.5,13.9),
                new DataPoint(95,14.1),new DataPoint(95.5,14.2),new DataPoint(96,14.3),
                new DataPoint(96.5,14.4),new DataPoint(97,14.6),new DataPoint(97.5,14.7),
                new DataPoint(98,14.8),new DataPoint(98.5,14.9),new DataPoint(99,15.1),
                new DataPoint(99.5,15.2),new DataPoint(100,15.4),new DataPoint(100.5,15.5),
                new DataPoint(101,15.6),new DataPoint(101.5,15.8),new DataPoint(102,15.9),
                new DataPoint(102.5,16.1),new DataPoint(103,16.2),new DataPoint(103.5,16.4),
                new DataPoint(104,16.5),new DataPoint(104.5,16.7),new DataPoint(105,16.8),
                new DataPoint(105.5,17),new DataPoint(106,17.3),new DataPoint(106.5,17.3),
                new DataPoint(107,17.5),new DataPoint(107.5,17.7),new DataPoint(108,17.8),
                new DataPoint(108.5,18),new DataPoint(109,18.2),new DataPoint(109.5,18.3),
                new DataPoint(110,18.5),new DataPoint(110.5,18.7),new DataPoint(111,18.9),
                new DataPoint(111.5,19.1),new DataPoint(112,19.2),new DataPoint(112.5,19.4),
                new DataPoint(113,19.6),new DataPoint(113.5,19.8),new DataPoint(114,20),
                new DataPoint(114.5,20.2),new DataPoint(115,20.4),new DataPoint(115.5,20.6),
                new DataPoint(116,20.8),new DataPoint(116.5,21),new DataPoint(117,21.2),
                new DataPoint(117.5,21.4),new DataPoint(118,21.6),new DataPoint(118.5,21.8),
                new DataPoint(119,22),new DataPoint(119.5,22.2),new DataPoint(120,22.4)
        });
        graph_LvsP2.addSeries(media);
        media.setColor(Color.GREEN);

        LineGraphSeries<DataPoint> unossd=new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(65,6.9),new DataPoint(65.5,7),new DataPoint(66,7.1),
                new DataPoint(66.5,7.2),new DataPoint(67,7.3),new DataPoint(67.5,7.4),
                new DataPoint(68,7.5),new DataPoint(68.5,7.6),new DataPoint(69,7.7),
                new DataPoint(69.5,7.8),new DataPoint(70,7.9),new DataPoint(70.5,8),
                new DataPoint(71,8.1),new DataPoint(71.5,8.2),new DataPoint(72,8.3),
                new DataPoint(72.5,8.4),new DataPoint(73,8.5),new DataPoint(73.5,8.6),
                new DataPoint(74,8.7),new DataPoint(74.5,8.8),new DataPoint(75,8.9),
                new DataPoint(75.5,9),new DataPoint(76,9.1),new DataPoint(76.5,9.2),
                new DataPoint(77,9.2),new DataPoint(77.5,9.3),new DataPoint(78,9.4),
                new DataPoint(78.5,9.5),new DataPoint(79,9.6),new DataPoint(79.5,9.7),
                new DataPoint(80,9.7),new DataPoint(80.5,9.8),new DataPoint(81,9.9),
                new DataPoint(81.5,10),new DataPoint(82,10.1),new DataPoint(82.5,10.1),
                new DataPoint(83,10.3),new DataPoint(83.5,10.4),new DataPoint(84,10.5),
                new DataPoint(84.5,10.7),new DataPoint(85,10.8),new DataPoint(85.5,10.9),
                new DataPoint(86,11),new DataPoint(86.5,11.1),new DataPoint(87,11.2),
                new DataPoint(87.5,11.3),new DataPoint(88,11.5),new DataPoint(88.5,11.6),
                new DataPoint(89,11.7),new DataPoint(89.5,11.8),new DataPoint(90,11.9),
                new DataPoint(90.5,12),new DataPoint(91,12.1),new DataPoint(91.5,12.2),
                new DataPoint(92,12.3),new DataPoint(92.5,12.4),new DataPoint(93,12.6),
                new DataPoint(93.5,12.7),new DataPoint(94,12.8),new DataPoint(94.5,12.9),
                new DataPoint(95,13),new DataPoint(95.5,13.1),new DataPoint(96,13.2),
                new DataPoint(96.5,13.3),new DataPoint(97,13.4),new DataPoint(97.5,13.6),
                new DataPoint(98,13.7),new DataPoint(98.5,13.8),new DataPoint(99,13.9),
                new DataPoint(99.5,14),new DataPoint(100,14.2),new DataPoint(100.5,14.3),
                new DataPoint(101,14.4),new DataPoint(101.5,14.5),new DataPoint(102,14.7),
                new DataPoint(102.5,14.8),new DataPoint(103,14.9),new DataPoint(103.5,15.1),
                new DataPoint(104,15.2),new DataPoint(104.5,15.4),new DataPoint(105,15.5),
                new DataPoint(105.5,15.6),new DataPoint(106,15.8),new DataPoint(106.5,15.9),
                new DataPoint(107,16.1),new DataPoint(107.5,16.2),new DataPoint(108,16.4),
                new DataPoint(108.5,16.5),new DataPoint(109,16.7),new DataPoint(109.5,16.8),
                new DataPoint(110,17),new DataPoint(110.5,17.1),new DataPoint(111,17.3),
                new DataPoint(111.5,17.5),new DataPoint(112,17.6),new DataPoint(112.5,17.8),
                new DataPoint(113,18),new DataPoint(113.5,18.1),new DataPoint(114,18.3),
                new DataPoint(114.5,18.5),new DataPoint(115,18.6),new DataPoint(115.5,18.8),
                new DataPoint(116,19),new DataPoint(116.5,19.2),new DataPoint(117,19.3),
                new DataPoint(117.5,19.5),new DataPoint(118,19.7),new DataPoint(118.5,19.9),
                new DataPoint(119,20),new DataPoint(119.5,20.2),new DataPoint(120,20.4),
        });

        graph_LvsP2.addSeries(unossd);
        unossd.setColor(Color.YELLOW);

        LineGraphSeries<DataPoint> dossd=new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(65,6.3),new DataPoint(65.5,6.4),new DataPoint(66,6.5),
                new DataPoint(66.5,6.6),new DataPoint(67,6.7),new DataPoint(67.5,6.8),
                new DataPoint(68,6.9),new DataPoint(68.5,7),new DataPoint(69,7.1),
                new DataPoint(69.5,7.2),new DataPoint(70,7.3),new DataPoint(70.5,7.4),
                new DataPoint(71,7.5),new DataPoint(71.5,7.6),new DataPoint(72,7.7),
                new DataPoint(72.5,7.8),new DataPoint(73,7.9),new DataPoint(73.5,7.9),
                new DataPoint(74,8),new DataPoint(74.5,8.1),new DataPoint(75,8.2),
                new DataPoint(75.5,8.3),new DataPoint(76,8.4),new DataPoint(76.5,8.5),
                new DataPoint(77,8.5),new DataPoint(77.5,8.6),new DataPoint(78,8.7),
                new DataPoint(78.5,8.8),new DataPoint(79,8.8),new DataPoint(79.5,8.9),
                new DataPoint(80,9),new DataPoint(80.5,9.1),new DataPoint(81,9.2),
                new DataPoint(81.5,9.3),new DataPoint(82,9.3),new DataPoint(82.5,9.4),
                new DataPoint(83,9.5),new DataPoint(83.5,9.6),new DataPoint(84,9.7),
                new DataPoint(84.5,9.9),new DataPoint(85,10),new DataPoint(85.5,10.1),
                new DataPoint(86,10.2),new DataPoint(86.5,10.3),new DataPoint(87,10.4),
                new DataPoint(87.5,10.5),new DataPoint(88,10.6),new DataPoint(88.5,10.7),
                new DataPoint(89,10.8),new DataPoint(89.5,10.9),new DataPoint(90,11),
                new DataPoint(90.5,11.1),new DataPoint(91,11.2),new DataPoint(91.5,11.3),
                new DataPoint(92,11.4),new DataPoint(92.5,11.5),new DataPoint(93,11.6),
                new DataPoint(93.5,11.7),new DataPoint(94,11.8),new DataPoint(94.5,11.9),
                new DataPoint(95,12),new DataPoint(95.5,12.1),new DataPoint(96,12.2),
                new DataPoint(96.5,12.3),new DataPoint(97,12.4),new DataPoint(97.5,12.5),
                new DataPoint(98,12.6),new DataPoint(98.5,12.8),new DataPoint(99,12.9),
                new DataPoint(99.5,13),new DataPoint(100,13.1),new DataPoint(100.5,13.2),
                new DataPoint(101,13.3),new DataPoint(101.5,13.4),new DataPoint(102,13.6),
                new DataPoint(102.5,13.7),new DataPoint(103,13.8),new DataPoint(103.5,13.9),
                new DataPoint(104,14),new DataPoint(104.5,14.2),new DataPoint(105,14.3),
                new DataPoint(105.5,14.4),new DataPoint(106,14.5),new DataPoint(106.5,14.7),
                new DataPoint(107,14.8),new DataPoint(107.5,14.9),new DataPoint(108,15.1),
                new DataPoint(108.5,15.2),new DataPoint(109,15.3),new DataPoint(109.5,15.5),
                new DataPoint(110,15.6),new DataPoint(110.5,15.8),new DataPoint(111,15.9),
                new DataPoint(111.5,16),new DataPoint(112,16.2),new DataPoint(112.5,16.3),
                new DataPoint(113,16.5),new DataPoint(113.5,16.6),new DataPoint(114,16.8),
                new DataPoint(114.5,16.9),new DataPoint(115,17.1),new DataPoint(115.5,17.2),
                new DataPoint(116,17.4),new DataPoint(116.5,17.5),new DataPoint(117,17.7),
                new DataPoint(117.5,17.9),new DataPoint(118,18),new DataPoint(118.5,18.2),
                new DataPoint(119,18.3),new DataPoint(119.5,18.5),new DataPoint(120,18.6)

        });
        graph_LvsP2.addSeries(dossd);
        dossd.setColor(Color.rgb(250,105,0));

        LineGraphSeries<DataPoint> tressd=new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(65,5.9),new DataPoint(65.5,6),new DataPoint(66,6.1),
                new DataPoint(66.5,6.1),new DataPoint(67,6.2),new DataPoint(67.5,6.3),
                new DataPoint(68,6.4),new DataPoint(68.5,6.5),new DataPoint(69,6.6),
                new DataPoint(69.5,6.7),new DataPoint(70,6.8),new DataPoint(70.5,6.9),
                new DataPoint(71,6.9),new DataPoint(71.5,7),new DataPoint(72,7.1),
                new DataPoint(72.5,7.2),new DataPoint(73,7.3),new DataPoint(73.5,7.4),
                new DataPoint(74,7.4),new DataPoint(74.5,7.5),new DataPoint(75,7.6),
                new DataPoint(75.5,7.7),new DataPoint(76,7.7),new DataPoint(76.5,7.8),
                new DataPoint(77,7.9),new DataPoint(77.5,8), new DataPoint(78,8),
                new DataPoint(78.5,8.1),new DataPoint(79,8.2),new DataPoint(79.5,8.3),
                new DataPoint(80,8.3),new DataPoint(80.5,8.4),new DataPoint(81,8.5),
                new DataPoint(81.5,8.6),new DataPoint(82,8.7),new DataPoint(82.5,8.7),
                new DataPoint(83,8.8),new DataPoint(83.5,8.9),new DataPoint(84,9),
                new DataPoint(84.5,9.1),new DataPoint(85,9.2),new DataPoint(85.5,9.3),
                new DataPoint(86,9.4),new DataPoint(86.5,9.5),new DataPoint(87,9.6),
                new DataPoint(87.5,9.7),new DataPoint(88,9.8),new DataPoint(88.5,9.9),
                new DataPoint(89,10),new DataPoint(89.5,10.1),new DataPoint(90,10.2),
                new DataPoint(90.5,10.3),new DataPoint(91,10.4),new DataPoint(91.5,10.5),
                new DataPoint(92,10.6),new DataPoint(92.5,10.7),new DataPoint(93,10.8),
                new DataPoint(93.5,10.9),new DataPoint(94,11),new DataPoint(94.5,11.1),
                new DataPoint(95,11.1),new DataPoint(95.5,11.2),new DataPoint(96,11.3),
                new DataPoint(96.5,11.4),new DataPoint(97,11.5),new DataPoint(97.5,11.6),
                new DataPoint(98,11.7),new DataPoint(98.5,11.8),new DataPoint(99,11.9),
                new DataPoint(99.5,12),new DataPoint(100,12.1),new DataPoint(100.5,12.2),
                new DataPoint(101,12.3),new DataPoint(101.5,12.4),new DataPoint(102,12.5),
                new DataPoint(102.5,12.6),new DataPoint(103,12.8),new DataPoint(103.5,12.9),
                new DataPoint(104,13),new DataPoint(104.5,13.1),new DataPoint(105,13.2),
                new DataPoint(105.5,13.3),new DataPoint(106,13.4),new DataPoint(106.5,13.5),
                new DataPoint(107,13.7),new DataPoint(107.5,13.8),new DataPoint(108,13.9),
                new DataPoint(108.5,14),new DataPoint(109,14.1),new DataPoint(109.5,14.3),
                new DataPoint(110,14.4),new DataPoint(110.5,14.5),new DataPoint(111,14.6),
                new DataPoint(111.5,14.8),new DataPoint(112,14.9),new DataPoint(112.5,15),
                new DataPoint(113,15.2),new DataPoint(113.5,15.3),new DataPoint(114,15.4),
                new DataPoint(114.5,15.6),new DataPoint(115,15.7),new DataPoint(115.5,15.8),
                new DataPoint(116,16),new DataPoint(116.5,16.1),new DataPoint(117,16.2),
                new DataPoint(117.5,16.5),new DataPoint(118,16.5),new DataPoint(118.5,16.7),
                new DataPoint(119,16.8),new DataPoint(119.5,16.9),new DataPoint(120,17.1)

        });

        graph_LvsP2.addSeries(tressd);
        tressd.setColor(Color.RED);

        LineGraphSeries<DataPoint> Unosd = new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(65,8.1),new DataPoint(65.5,8.2),new DataPoint(66,8.3),
                new DataPoint(66.5,8.5),new DataPoint(67,8.6),new DataPoint(67.5,8.7),
                new DataPoint(68,8.8),new DataPoint(68.5,9),new DataPoint(69,9.1),
                new DataPoint(69.5,9.2),new DataPoint(70,9.3),new DataPoint(70.5,9.5),
                new DataPoint(71,9.6),new DataPoint(71.5,9.7),new DataPoint(72,9.8),
                new DataPoint(72.5,9.9),new DataPoint(73,10),new DataPoint(73.5,10.2),
                new DataPoint(74,10.3),new DataPoint(74.5,10.4),new DataPoint(75,10.5),
                new DataPoint(75.5,10.6),new DataPoint(76,10.7),new DataPoint(76.5,10.8),
                new DataPoint(77,10.9),new DataPoint(77.5,11),new DataPoint(78,11.1),
                new DataPoint(78.5,11.2),new DataPoint(79,11.3),new DataPoint(79.5,11.4),
                new DataPoint(80,11.5),new DataPoint(80.5,11.6),new DataPoint(81,11.7),
                new DataPoint(81.5,11.8),new DataPoint(82,11.9),new DataPoint(82.5,12.1),
                new DataPoint(83,12.2),new DataPoint(83.5,12.3),new DataPoint(84,12.4),
                new DataPoint(84.5,12.5),new DataPoint(85,12.7),new DataPoint(85.5,12.8),
                new DataPoint(86,12.9),new DataPoint(86.5,13.1),new DataPoint(87,13.2),
                new DataPoint(87.5,13.3),new DataPoint(88,13.5),new DataPoint(88.5,13.6),
                new DataPoint(89,13.7),new DataPoint(89.5,13.9),new DataPoint(90,14),
                new DataPoint(90.5,14.1),new DataPoint(91,14.2),new DataPoint(91.5,14.4),
                new DataPoint(92,14.5),new DataPoint(92.5,14.6),new DataPoint(93,14.7),
                new DataPoint(93.5,14.9),new DataPoint(94,15),new DataPoint(94.5,15.1),
                new DataPoint(95,15.3),new DataPoint(95.5,15.4),new DataPoint(96,15.5),
                new DataPoint(96.5,15.7),new DataPoint(97,15.8),new DataPoint(97.5,15.9),
                new DataPoint(98,16.1),new DataPoint(98.5,16.2),new DataPoint(99,16.4),
                new DataPoint(99.5,16.5),new DataPoint(100,16.7),new DataPoint(100.5,16.9),
                new DataPoint(101,17),new DataPoint(101.5,17.2),new DataPoint(102,17.3),
                new DataPoint(102.5,17.5),new DataPoint(103,17.7),new DataPoint(103.5,17.8),
                new DataPoint(104,18),new DataPoint(104.5,18.2),new DataPoint(105,18.4),
                new DataPoint(105.5,18.5),new DataPoint(106,18.7),new DataPoint(106.5,18.9),
                new DataPoint(107,19.1),new DataPoint(107.5,19.3),new DataPoint(108,19.5),
                new DataPoint(108.5,19.7),new DataPoint(109,19.8),new DataPoint(109.5,20),
                new DataPoint(110,20.2),new DataPoint(110.5,20.4),new DataPoint(111,20.7),
                new DataPoint(111.5,20.9),new DataPoint(112,21.1),new DataPoint(112.5,21.3),
                new DataPoint(113,21.5),new DataPoint(113.5,21.7),new DataPoint(114,21.9),
                new DataPoint(114.5,22.1),new DataPoint(115,22.4),new DataPoint(115.5,22.6),
                new DataPoint(116,22.8),new DataPoint(116.5,23),new DataPoint(117,23.3),
                new DataPoint(117.5,23.5),new DataPoint(118,23.7),new DataPoint(118.5,23.9),
                new DataPoint(119,24.1),new DataPoint(119.5,24.4),new DataPoint(120,24.6)
        });

        graph_LvsP2.addSeries(Unosd);
        Unosd.setColor(Color.YELLOW);

        LineGraphSeries<DataPoint> Dossd =new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(65,8.8),new DataPoint(65.5,8.9),new DataPoint(66,9.1),
                new DataPoint(66.5,9.2),new DataPoint(67,9.4),new DataPoint(67.5,9.5),
                new DataPoint(68,9.6),new DataPoint(68.5,9.8),new DataPoint(69,9.9),
                new DataPoint(69.5,10),new DataPoint(70,10.2),new DataPoint(70.5,10.3),
                new DataPoint(71,10.4),new DataPoint(71.5,10.6),new DataPoint(72,10.7),
                new DataPoint(72.5,10.8),new DataPoint(73,11),new DataPoint(73.5,11.1),
                new DataPoint(74,11.2),new DataPoint(74.5,11.3),new DataPoint(75,11.4),
                new DataPoint(75.5,11.6),new DataPoint(76,11.7),new DataPoint(76.5,11.8),
                new DataPoint(77,11.9),new DataPoint(77.5,12),new DataPoint(78,12.1),
                new DataPoint(78.5,12.2),new DataPoint(79,12.3),new DataPoint(79.5,12.4),
                new DataPoint(80,12.6),new DataPoint(80.5,12.7),new DataPoint(81,12.8),
                new DataPoint(81.5,12.9),new DataPoint(82,13),new DataPoint(82.5,13.1),
                new DataPoint(83,13.3),new DataPoint(83.5,13.4),new DataPoint(84,13.5),
                new DataPoint(84.5,13.7),new DataPoint(85,13.8),new DataPoint(85.5,13.9),
                new DataPoint(86,14.1),new DataPoint(86.5,14.2),new DataPoint(87,14.4),
                new DataPoint(87.5,14.5),new DataPoint(88,14.7),new DataPoint(88.5,14.8),
                new DataPoint(89,14.9),new DataPoint(89.5,15.1),new DataPoint(90,15.2),
                new DataPoint(90.5,15.3),new DataPoint(91,15.5),new DataPoint(91.5,15.6),
                new DataPoint(92,15.8),new DataPoint(92.5,15.9),new DataPoint(93,16),
                new DataPoint(93.5,16.2),new DataPoint(94,16.3),new DataPoint(94.5,16.5),
                new DataPoint(95,16.6),new DataPoint(95.5,16.7),new DataPoint(96,16.9),
                new DataPoint(96.5,17),new DataPoint(97,17.2),new DataPoint(97.5,17.4),
                new DataPoint(98,17.5),new DataPoint(98.5,17.7),new DataPoint(99,17.9),
                new DataPoint(99.5,18),new DataPoint(100,18.2),new DataPoint(100.5,18.4),
                new DataPoint(101,18.5),new DataPoint(101.5,18.7),new DataPoint(102,18.9),
                new DataPoint(102.5,19.1),new DataPoint(103,19.3),new DataPoint(103.5,19.5),
                new DataPoint(104,19.7),new DataPoint(104.5,19.9),new DataPoint(105,20.1),
                new DataPoint(105.5,20.3),new DataPoint(106,20.5),new DataPoint(106.5,20.7),
                new DataPoint(107,20.90),new DataPoint(107.5,21.1),new DataPoint(108,21.3),
                new DataPoint(108.5,21.5),new DataPoint(109,21.8),new DataPoint(109.5,22),
                new DataPoint(110,22.2),new DataPoint(110.5,22.4),new DataPoint(111,22.7),
                new DataPoint(111.5,22.9),new DataPoint(112,23.1),new DataPoint(112.5,23.4),
                new DataPoint(113,23.6),new DataPoint(113.5,23.9),new DataPoint(114,24.1),
                new DataPoint(114.5,24.4),new DataPoint(115,24.6),new DataPoint(115.5,24.9),
                new DataPoint(116,25.1),new DataPoint(116.5,25.4),new DataPoint(117,25.6),
                new DataPoint(117.5,25.9),new DataPoint(118,26.1),new DataPoint(118.5,26.4),
                new DataPoint(119,26.6),new DataPoint(119.5,26.9),new DataPoint(120,27.2)
        });

        graph_LvsP2.addSeries(Dossd);
        Dossd.setColor(Color.rgb(250,105,0));

        LineGraphSeries<DataPoint> Tressd =new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(65,9.6),new DataPoint(65.5,9.8),new DataPoint(66,9.9),
                new DataPoint(66.5,10.1),new DataPoint(67,10.2),new DataPoint(67.5,10.4),
                new DataPoint(68,10.5),new DataPoint(68.5,10.7),new DataPoint(69,10.8),
                new DataPoint(69.5,11),new DataPoint(70,11.1),new DataPoint(70.5,11.3),
                new DataPoint(71,11.4),new DataPoint(71.5,11.6),new DataPoint(72,11.7),
                new DataPoint(72.5,11.8),new DataPoint(73,12),new DataPoint(73.5,12.1),
                new DataPoint(74,12.2),new DataPoint(74.5,12.4),new DataPoint(75,12.5),
                new DataPoint(75.5,12.6),new DataPoint(76,12.8),new DataPoint(76.5,12.9),
                new DataPoint(77,13),new DataPoint(77.5,13.1),new DataPoint(78,13.3),
                new DataPoint(78.5,13.4),new DataPoint(79,13.5),new DataPoint(79.5,13.6),
                new DataPoint(80,13.7),new DataPoint(80.5,13.8),new DataPoint(81,14),
                new DataPoint(81.5,14.1),new DataPoint(82,14.2),new DataPoint(82.5,14.4),
                new DataPoint(83,14.5),new DataPoint(83.5,14.6),new DataPoint(84,14.8),
                new DataPoint(84.5,14.9),new DataPoint(85,15.1),new DataPoint(85.5,15.2),
                new DataPoint(86,15.4),new DataPoint(86.5,15.5),new DataPoint(87,15.7),
                new DataPoint(87.5,15.8),new DataPoint(88,16),new DataPoint(88.5,16.1),
                new DataPoint(89,16.3),new DataPoint(89.5,16.4),new DataPoint(90,16.6),
                new DataPoint(90.5,16.7),new DataPoint(91,16.9),new DataPoint(91.5,17),
                new DataPoint(92,17.2),new DataPoint(92.5,17.3),new DataPoint(93,17.5),
                new DataPoint(93.5,17.6),new DataPoint(94,17.8),new DataPoint(94.5,17.9),
                new DataPoint(95,18.1),new DataPoint(95.5,18.3),new DataPoint(96,18.4),
                new DataPoint(96.5,18.6),new DataPoint(97,18.8),new DataPoint(97.5,18.9),
                new DataPoint(98,19.1),new DataPoint(98.5,19.3),new DataPoint(99,19.5),
                new DataPoint(99.5,19.7),new DataPoint(100,19.9),new DataPoint(100.5,20.1),
                new DataPoint(101,20.3),new DataPoint(101.5,20.5),new DataPoint(102,20.7),
                new DataPoint(102.5,20.9),new DataPoint(103,21.1),new DataPoint(103.5,21.3),
                new DataPoint(104,21.6),new DataPoint(104.5,21.8),new DataPoint(105,22),
                new DataPoint(105.5,22.2),new DataPoint(106,22.5),new DataPoint(106.5,22.7),
                new DataPoint(107,22.9),new DataPoint(107.5,23.2),new DataPoint(108,23.4),
                new DataPoint(108.5,23.7),new DataPoint(109,23.9),new DataPoint(109.5,24.2),
                new DataPoint(110,24.4),new DataPoint(110.5,24.7),new DataPoint(111,25),
                new DataPoint(111.5,25.2),new DataPoint(112,25.5),new DataPoint(112.5,25.8),
                new DataPoint(113,26),new DataPoint(113.5,26.3),new DataPoint(114,26.6),
                new DataPoint(114.5,26.9),new DataPoint(115,27.2),new DataPoint(115.5,27.5),
                new DataPoint(116,27.8),new DataPoint(116.5,28),new DataPoint(117,28.3),
                new DataPoint(117.5,28.6),new DataPoint(118,28.9),new DataPoint(118.5,29.2),
                new DataPoint(119,29.5),new DataPoint(119.5,29.8),new DataPoint(120,30.1)

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

    public void grafica_O_IMCvsE(){

        GraphView graph_O_IMC_E = (GraphView) findViewById(R.id.graph_O_IMCvsE);

        LineGraphSeries<DataPoint> median= new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(0,13.4),new DataPoint(1,14.9), new DataPoint(2,16.3),
                new DataPoint(3,16.9), new DataPoint(4,17.2),new DataPoint(5,17.3),
                new DataPoint(6,17.3),new DataPoint(7,17.3),new DataPoint(8,17.3),
                new DataPoint(9,17.2),new DataPoint(10,17),new DataPoint(11,16.9),
                new DataPoint(12,16.8),new DataPoint(13,16.7),new DataPoint(14,16.6),
                new DataPoint(15,16.4),new DataPoint(16,16.3),new DataPoint(17,16.2),
                new DataPoint(18,16.1),new DataPoint(19,16.1),new DataPoint(20,16),
                new DataPoint(21,15.9),new DataPoint(22,15.8),new DataPoint(23,15.8),
                new DataPoint(24,15.7),new DataPoint(25,16),new DataPoint(26,15.9),
                new DataPoint(27,15.9),new DataPoint(28,15.9),new DataPoint(29,15.8),
                new DataPoint(30,15.8),new DataPoint(31,15.8),new DataPoint(32,15.7),
                new DataPoint(33,15.7),new DataPoint(34,15.7),new DataPoint(35,15.6),
                new DataPoint(36,15.6),new DataPoint(37,15.6),new DataPoint(38,15.5),
                new DataPoint(39,15.5),new DataPoint(40,15.5),new DataPoint(41,15.5),
                new DataPoint(42,15.4),new DataPoint(43,15.4),new DataPoint(44,15.4),
                new DataPoint(45,15.4),new DataPoint(46,15.4),new DataPoint(47,15.3),
                new DataPoint(48,15.3),new DataPoint(49,15.3),new DataPoint(50,15.3),
                new DataPoint(51,15.3),new DataPoint(52,15.3),new DataPoint(53,15.3),
                new DataPoint(54,15.3),new DataPoint(55,15.2),new DataPoint(56,15.2),
                new DataPoint(57,15.2),new DataPoint(58,15.2),new DataPoint(59,15.2),
                new DataPoint(60,15.2)

        });

        graph_O_IMC_E.addSeries(median);
        median.setColor(Color.GREEN);

        LineGraphSeries<DataPoint> unosd = new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(0,12.2),new DataPoint(1,13.6),new DataPoint(2,15),
                new DataPoint(3,15.5),new DataPoint(4,15.8),new DataPoint(5,15.9),
                new DataPoint(6,16),new DataPoint(7,16),new DataPoint(8,15.9),
                new DataPoint(9,15.8),new DataPoint(10,15.7),new DataPoint(11,15.6),
                new DataPoint(12,15.5),new DataPoint(13,15.4),new DataPoint(14,15.3),
                new DataPoint(15,15.2),new DataPoint(16,15.1),new DataPoint(17,15),
                new DataPoint(18,14.9),new DataPoint(19,14.9),new DataPoint(20,14.8),
                new DataPoint(21,14.7),new DataPoint(22,14.7),new DataPoint(23,14.6),
                new DataPoint(24,14.6),new DataPoint(25,14.8),new DataPoint(26,14.8),
                new DataPoint(27,14.7),new DataPoint(28,14.7),new DataPoint(29,14.7),
                new DataPoint(30,14.6),new DataPoint(31,14.6),new DataPoint(32,14.6),
                new DataPoint(33,14.5),new DataPoint(34,14.5),new DataPoint(35,14.5),
                new DataPoint(36,14.4),new DataPoint(37,14.4),new DataPoint(38,14.4),
                new DataPoint(39,14.3),new DataPoint(40,14.3),new DataPoint(41,14.3),
                new DataPoint(42,14.3),new DataPoint(43,14.2),new DataPoint(44,14.2),
                new DataPoint(45,14.2),new DataPoint(46,14.2),new DataPoint(47,14.2),
                new DataPoint(48,14.1),new DataPoint(49,14.1),new DataPoint(50,14.1),
                new DataPoint(51,14.1),new DataPoint(52,14.1),new DataPoint(53,14.1),
                new DataPoint(54,14),new DataPoint(55,14),new DataPoint(56,14),
                new DataPoint(57,14),new DataPoint(58,14),new DataPoint(59,14),
                new DataPoint(60,14)
        });

        graph_O_IMC_E.addSeries(unosd);
        unosd.setColor(Color.YELLOW);

        LineGraphSeries<DataPoint> dossd = new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(0,11.1),new DataPoint(1,12.4),new DataPoint(2,13.7),
                new DataPoint(3,14.3),new DataPoint(4,14.5),new DataPoint(5,14.7),
                new DataPoint(6,14.7),new DataPoint(7,14.8),new DataPoint(8,14.7),
                new DataPoint(9,14.7),new DataPoint(10,14.6),new DataPoint(11,14.5),
                new DataPoint(12,14.4),new DataPoint(13,14.3),new DataPoint(14,14.2),
                new DataPoint(15,14.1),new DataPoint(16,14),new DataPoint(17,13.9),
                new DataPoint(18,13.9),new DataPoint(19,13.8),new DataPoint(20,13.7),
                new DataPoint(21,13.7),new DataPoint(22,13.6),new DataPoint(23,13.6),
                new DataPoint(24,13.6),new DataPoint(25,13.8),new DataPoint(26,13.7),
                new DataPoint(27,13.7),new DataPoint(28,13.6),new DataPoint(29,13.6),
                new DataPoint(30,13.6),new DataPoint(31,13.5),new DataPoint(32,13.5),
                new DataPoint(33,13.5),new DataPoint(34,13.4), new DataPoint(35,13.4),
                new DataPoint(36,13.4),new DataPoint(37,13.3),new DataPoint(38,13.3),
                new DataPoint(39,13.3),new DataPoint(40,13.2),new DataPoint(41,13.2),
                new DataPoint(42,13.2),new DataPoint(43,13.2),new DataPoint(44,13.1),
                new DataPoint(45,13.1),new DataPoint(46,13.1),new DataPoint(47,13.1),
                new DataPoint(48,13.1),new DataPoint(49,13),new DataPoint(50,13),
                new DataPoint(51,13),new DataPoint(52,13),new DataPoint(53,13),
                new DataPoint(54,13),new DataPoint(55,13),new DataPoint(56,12.9),
                new DataPoint(57,12.9),new DataPoint(58,12.9),new DataPoint(59,12.9),
                new DataPoint(60,12.9)
        });

        graph_O_IMC_E.addSeries(dossd);
        dossd.setColor(Color.rgb(250,105,0));

        LineGraphSeries<DataPoint> tressd = new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(0,10.2),new DataPoint(1,11.3),new DataPoint(2,12.5),
                new DataPoint(3,13.1),new DataPoint(4,13.4),new DataPoint(5,13.5),
                new DataPoint(6,13.6),new DataPoint(7,13.7),new DataPoint(8,13.6),
                new DataPoint(9,13.6),new DataPoint(10,13.5),new DataPoint(11,13.4),
                new DataPoint(12,13.4),new DataPoint(13,13.3),new DataPoint(14,13.2),
                new DataPoint(15,13.1),new DataPoint(16,13.1),new DataPoint(17,13),
                new DataPoint(18,12.9),new DataPoint(19,12.9),new DataPoint(20,12.8),
                new DataPoint(21,12.8),new DataPoint(22,12.7),new DataPoint(23,12.7),
                new DataPoint(24,12.7),new DataPoint(25,12.8),new DataPoint(26,12.8),
                new DataPoint(27,12.7),new DataPoint(28,12.7),new DataPoint(29,12.7),
                new DataPoint(30,12.6),new DataPoint(31,12.6),new DataPoint(32,12.5),
                new DataPoint(33,12.5),new DataPoint(34,12.5),new DataPoint(35,12.4),
                new DataPoint(36,12.4),new DataPoint(37,12.4),new DataPoint(38,12.3),
                new DataPoint(39,12.3),new DataPoint(40,12.3),new DataPoint(41,12.2),
                new DataPoint(42,12.2),new DataPoint(43,12.2),new DataPoint(44,12.2),
                new DataPoint(45,12.2),new DataPoint(46,12.1),new DataPoint(47,12.1),
                new DataPoint(48,12.1),new DataPoint(49,12.1),new DataPoint(50,12.1),
                new DataPoint(51,12.1),new DataPoint(52,12),new DataPoint(53,12),
                new DataPoint(54,12),new DataPoint(55,12),new DataPoint(56,12),
                new DataPoint(57,12),new DataPoint(58,12),new DataPoint(59,12),
                new DataPoint(60,12)

        });

        graph_O_IMC_E.addSeries(tressd);
        tressd.setColor(Color.RED);

        LineGraphSeries<DataPoint> Unosd = new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(0,14.8),new DataPoint(1,16.3),new DataPoint(2,17.8),
                new DataPoint(3,18.4),new DataPoint(4,18.7),new DataPoint(5,18.8),
                new DataPoint(6,18.8),new DataPoint(7,18.8),new DataPoint(8,18.7),
                new DataPoint(9,18.9),new DataPoint(10,18.5),new DataPoint(11,18.4),
                new DataPoint(12,18.2),new DataPoint(13,18.1),new DataPoint(14,18),
                new DataPoint(15,17.8),new DataPoint(16,17.7),new DataPoint(17,17.6),
                new DataPoint(18,17.5),new DataPoint(19,17.4),new DataPoint(20,17.3),
                new DataPoint(21,17.2),new DataPoint(22,17.2),new DataPoint(23,17.1),
                new DataPoint(24,17),new DataPoint(25,17.3),new DataPoint(26,17.3),
                new DataPoint(27,17.2),new DataPoint(28,17.2),new DataPoint(29,17.1),
                new DataPoint(30,17.1),new DataPoint(31,17.1),new DataPoint(32,17),
                new DataPoint(33,17),new DataPoint(34,17),new DataPoint(35,16.9),
                new DataPoint(36,16.9),new DataPoint(37,16.9),new DataPoint(38,16.8),
                new DataPoint(39,16.8),new DataPoint(40,16.8),new DataPoint(41,16.8),
                new DataPoint(42,16.8),new DataPoint(43,16.7),new DataPoint(44,16.7),
                new DataPoint(45,16.7),new DataPoint(46,16.7),new DataPoint(47,16.7),
                new DataPoint(48,16.7),new DataPoint(49,16.7),new DataPoint(50,16.7),
                new DataPoint(51,16.6),new DataPoint(52,16.6),new DataPoint(53,16.6),
                new DataPoint(54,16.6),new DataPoint(55,16.6),new DataPoint(56,16.6),
                new DataPoint(57,16.6),new DataPoint(58,16.6),new DataPoint(59,16.6),
                new DataPoint(60,16.6)
        });

        graph_O_IMC_E.addSeries(Unosd);
        Unosd.setColor(Color.YELLOW);

        LineGraphSeries<DataPoint> Dossd = new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(0,16.3),new DataPoint(1,17.8),new DataPoint(2,19.4),
                new DataPoint(3,20),new DataPoint(4,20.3),new DataPoint(5,20.5),
                new DataPoint(6,20.5),new DataPoint(7,20.5),new DataPoint(8,20.4),
                new DataPoint(9,20.3),new DataPoint(10,20.1),new DataPoint(11,20),
                new DataPoint(12,19.8),new DataPoint(13,19.7),new DataPoint(14,19.5),
                new DataPoint(15,19.4),new DataPoint(16,19.3),new DataPoint(17,19.1),
                new DataPoint(18,19),new DataPoint(19,18.9),new DataPoint(20,18.8),
                new DataPoint(21,18.7),new DataPoint(22,18.7),new DataPoint(23,18.6),
                new DataPoint(24,18.5),new DataPoint(25,18.8),new DataPoint(26,18.8),
                new DataPoint(27,18.7),new DataPoint(28,18.7),new DataPoint(29,18.6),
                new DataPoint(30,18.6),new DataPoint(31,18.5),new DataPoint(32,18.5),
                new DataPoint(33,18.5),new DataPoint(34,18.4),new DataPoint(35,18.4),
                new DataPoint(36,18.4),new DataPoint(37,18.3),new DataPoint(38,18.3),
                new DataPoint(39,18.3),new DataPoint(40,18.2),new DataPoint(41,18.2),
                new DataPoint(42,18.2),new DataPoint(43,18.2),new DataPoint(44,18.2),
                new DataPoint(45,18.2),new DataPoint(46,18.2),new DataPoint(47,18.2),
                new DataPoint(48,18.2),new DataPoint(49,18.2),new DataPoint(50,18.2),
                new DataPoint(51,18.2),new DataPoint(52,18.2),new DataPoint(53,18.2),
                new DataPoint(54,18.2),new DataPoint(55,18.2),new DataPoint(56,18.2),
                new DataPoint(57,18.2),new DataPoint(58,18.3),new DataPoint(59,18.3),
                new DataPoint(60,18.3)
        });

        graph_O_IMC_E.addSeries(Dossd);
        Dossd.setColor(Color.rgb(250,105,0));

        LineGraphSeries<DataPoint> Tressd = new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(0,18.1),new DataPoint(1,19.4),new DataPoint(2,21.1),
                new DataPoint(3,21.8),new DataPoint(4,22.1),new DataPoint(5,22.3),
                new DataPoint(6,22.3),new DataPoint(7,22.3),new DataPoint(8,22.2),
                new DataPoint(9,22.1),new DataPoint(10,22),new DataPoint(11,21.8),
                new DataPoint(12,21.6),new DataPoint(13,21.5),new DataPoint(14,21.3),
                new DataPoint(15,21.2),new DataPoint(16,21),new DataPoint(17,20.9),
                new DataPoint(18,20.8),new DataPoint(19,20.7),new DataPoint(20,20.6),
                new DataPoint(21,20.5),new DataPoint(22,20.4),new DataPoint(23,20.3),
                new DataPoint(24,20.3),new DataPoint(25,20.5),new DataPoint(26,20.5),
                new DataPoint(27,20.4),new DataPoint(28,20.4),new DataPoint(29,20.3),
                new DataPoint(30,20.2),new DataPoint(31,20.2),new DataPoint(32,20.1),
                new DataPoint(33,20.1),new DataPoint(34,20),new DataPoint(35,20),
                new DataPoint(36,20),new DataPoint(37,19.9),new DataPoint(38,19.9),
                new DataPoint(39,19.9),new DataPoint(40,19.9),new DataPoint(41,19.9),
                new DataPoint(42,19.8),new DataPoint(43,19.8),new DataPoint(44,19.8),
                new DataPoint(45,19.8),new DataPoint(46,19.8),new DataPoint(47,19.9),
                new DataPoint(48,19.9),new DataPoint(49,19.9),new DataPoint(50,19.9),
                new DataPoint(51,19.9),new DataPoint(52,19.9),new DataPoint(53,20),
                new DataPoint(54,20),new DataPoint(55,20),new DataPoint(56,20.1),
                new DataPoint(57,20.1),new DataPoint(58,20.2),new DataPoint(59,20.2),
                new DataPoint(60,20.3)
        }) ;

        graph_O_IMC_E.addSeries(Tressd);
        Tressd.setColor(Color.RED);

        graph_O_IMC_E.setTitle("IMC por la Edad");

        graph_O_IMC_E.getGridLabelRenderer().getGridStyle().drawHorizontal();

        graph_O_IMC_E.getViewport().setYAxisBoundsManual(true);
        graph_O_IMC_E.getViewport().setMinY(10);
        graph_O_IMC_E.getViewport().setMaxY(24);

        graph_O_IMC_E.getViewport().setXAxisBoundsManual(true);
        graph_O_IMC_E.getViewport().setMinX(0);
        graph_O_IMC_E.getViewport().setMaxX(61);


        graph_O_IMC_E.getViewport().setScalable(true);
        graph_O_IMC_E.getViewport().setScalableY(true);



        graph_O_IMC_E.getViewport().setScrollable(true);
        graph_O_IMC_E.getViewport().setScrollableY(true);
    }
    public void grafica_O_CvsE(){

        GraphView graph_O_C_E = (GraphView) findViewById(R.id.graph_O_CvsE);

        LineGraphSeries<DataPoint> media =new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(0,34.5),new DataPoint(1,37.3),new DataPoint(2,39.1),
                new DataPoint(3,40.5),new DataPoint(4,41.6),new DataPoint(5,42.6),
                new DataPoint(6,43.3),new DataPoint(7,44),new DataPoint(8,44.5),
                new DataPoint(9,45),new DataPoint(10,45.4),new DataPoint(11,45.8),
                new DataPoint(12,46.1),new DataPoint(13,46.3),new DataPoint(14,46.6),
                new DataPoint(15,46.8),new DataPoint(16,47),new DataPoint(17,47.2),
                new DataPoint(18,47.4),new DataPoint(19,47.5),new DataPoint(20,47.7),
                new DataPoint(21,47.8),new DataPoint(22,48),new DataPoint(23,48.1),
                new DataPoint(24,48.3),new DataPoint(25,48.4),new DataPoint(26,48.5),
                new DataPoint(27,48.6),new DataPoint(28,48.7),new DataPoint(29,48.8),
                new DataPoint(30,48.9),new DataPoint(31,49),new DataPoint(32,49.1),
                new DataPoint(33,49.2),new DataPoint(34,49.3),new DataPoint(35,49.4),
                new DataPoint(36,49.5),new DataPoint(37,49.5),new DataPoint(38,49.6),
                new DataPoint(39,49.7),new DataPoint(40,49.7),new DataPoint(41,49.8),
                new DataPoint(42,49.9),new DataPoint(43,49.9),new DataPoint(44,50),
                new DataPoint(45,50.1),new DataPoint(46,50.1),new DataPoint(47,50.2),
                new DataPoint(48,50.2),new DataPoint(49,50.3),new DataPoint(50,50.3),
                new DataPoint(51,50.4),new DataPoint(52,50.4),new DataPoint(53,50.4),
                new DataPoint(54,50.5),new DataPoint(55,50.5),new DataPoint(56,50.6),
                new DataPoint(57,50.6),new DataPoint(58,50.7),new DataPoint(59,50.7),
                new DataPoint(60,50.7)
        });

        graph_O_C_E.addSeries(media);
        media.setColor(Color.GREEN);

        LineGraphSeries<DataPoint> unossd =new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(0,33.2),new DataPoint(1,36.1),new DataPoint(2,38),
                new DataPoint(3,39.3),new DataPoint(4,40.4),new DataPoint(5,41.4),
                new DataPoint(6,42.1),new DataPoint(7,42.7),new DataPoint(8,43.3),
                new DataPoint(9,43.7),new DataPoint(10,44.1),new DataPoint(11,44.5),
                new DataPoint(12,44.8),new DataPoint(13,45),new DataPoint(14,45.3),
                new DataPoint(15,45.5),new DataPoint(16,45.7),new DataPoint(17,45.9),
                new DataPoint(18,46),new DataPoint(19,46.2),new DataPoint(20,46.4),
                new DataPoint(21,46.5),new DataPoint(22,46.6),new DataPoint(23,46.8),
                new DataPoint(24,46.9),new DataPoint(25,47),new DataPoint(26,47.1),
                new DataPoint(27,47.2),new DataPoint(28,47.3),new DataPoint(29,47.4),
                new DataPoint(30,47.5),new DataPoint(31,47.6),new DataPoint(32,47.7),
                new DataPoint(33,47.8),new DataPoint(34,47.9),new DataPoint(35,48),
                new DataPoint(36,48),new DataPoint(37,48.1),new DataPoint(38,48.2),
                new DataPoint(39,48.2),new DataPoint(40,48.3),new DataPoint(41,48.4),
                new DataPoint(42,48.4),new DataPoint(43,48.5),new DataPoint(44,48.5),
                new DataPoint(45,48.6),new DataPoint(46,48.7),new DataPoint(47,48.7),
                new DataPoint(48,48.7),new DataPoint(49,48.8),new DataPoint(50,48.8),
                new DataPoint(51,48.9),new DataPoint(52,48.9),new DataPoint(53,49),
                new DataPoint(54,49),new DataPoint(55,49.1),new DataPoint(56,49.1),
                new DataPoint(57,49.1),new DataPoint(58,49.2),new DataPoint(59,49.2),
                new DataPoint(60,49.2)
        });

        graph_O_C_E.addSeries(unossd);
        unossd.setColor(Color.YELLOW);

        LineGraphSeries<DataPoint> dossd = new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(0,31.9),new DataPoint(1,34.9),new DataPoint(2,36.8),
                new DataPoint(3,38.1),new DataPoint(4,39.2),new DataPoint(5,40.1),
                new DataPoint(6,40.9),new DataPoint(7,41.5),new DataPoint(8,42),
                new DataPoint(9,42.5),new DataPoint(10,42.9),new DataPoint(11,43.2),
                new DataPoint(12,43.5),new DataPoint(13,43.8),new DataPoint(14,44),
                new DataPoint(15,44.2),new DataPoint(16,44.4),new DataPoint(17,44.6),
                new DataPoint(18,44.7),new DataPoint(19,44.9),new DataPoint(20,45),
                new DataPoint(21,45.2),new DataPoint(22,45.3),new DataPoint(23,45.4),
                new DataPoint(24,45.5),new DataPoint(25,45.6),new DataPoint(26,45.8),
                new DataPoint(27,45.9),new DataPoint(28,46),new DataPoint(29,46.1),
                new DataPoint(30,46.1),new DataPoint(31,46.2),new DataPoint(32,46.3),
                new DataPoint(33,46.4),new DataPoint(34,46.5),new DataPoint(35,46.6),
                new DataPoint(36,46.6),new DataPoint(37,46.7),new DataPoint(38,46.80),
                new DataPoint(39,46.8),new DataPoint(40,46.8),new DataPoint(41,46.9),
                new DataPoint(42,47),new DataPoint(43,47),new DataPoint(44,47.1),
                new DataPoint(45,47.1),new DataPoint(46,47.2),new DataPoint(47,47.2),
                new DataPoint(48,47.3),new DataPoint(49,47.3),new DataPoint(50,47.4),
                new DataPoint(51,47.4),new DataPoint(52,47.5),new DataPoint(53,47.5),
                new DataPoint(54,47.5),new DataPoint(55,47.6),new DataPoint(56,47.6),
                new DataPoint(57,47.6),new DataPoint(58,47.7),new DataPoint(59,47.7),
                new DataPoint(60,47.7)

        });

        graph_O_C_E.addSeries(dossd);
        dossd.setColor(Color.rgb(250,105,0));

        LineGraphSeries<DataPoint> tressd = new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(0,30.7),new DataPoint(1,33.8),new DataPoint(2,35.6),
                new DataPoint(3,37),new DataPoint(4,38),new DataPoint(5,38.9),
                new DataPoint(6,39.7),new DataPoint(7,40.3),new DataPoint(8,40.8),
                new DataPoint(9,41.2),new DataPoint(10,41.6),new DataPoint(11,41.9),
                new DataPoint(12,42.2),new DataPoint(13,42.5),new DataPoint(14,42.7),
                new DataPoint(15,42.9),new DataPoint(16,43.1),new DataPoint(17,43.2),
                new DataPoint(18,43.4),new DataPoint(19,43.5),new DataPoint(20,43.7),
                new DataPoint(21,43.8),new DataPoint(22,43.9),new DataPoint(23,44.1),
                new DataPoint(24,44.2),new DataPoint(25,44.3),new DataPoint(26,44.4),
                new DataPoint(27,44.5),new DataPoint(28,44.6),new DataPoint(29,44.7),
                new DataPoint(30,44.8),new DataPoint(31,44.8),new DataPoint(32,44.9),
                new DataPoint(33,45),new DataPoint(34,45.1),new DataPoint(35,45.1),
                new DataPoint(36,45.2),new DataPoint(37,45.3),new DataPoint(38,45.3),
                new DataPoint(39,45.4),new DataPoint(40,45.4),new DataPoint(41,45.5),
                new DataPoint(42,45.5),new DataPoint(43,45.6),new DataPoint(44,45.6),
                new DataPoint(45,45.7),new DataPoint(46,45.7),new DataPoint(47,45.8),
                new DataPoint(48,45.8),new DataPoint(49,45.9),new DataPoint(50,45.9),
                new DataPoint(51,45.9),new DataPoint(52,46),new DataPoint(53,46),
                new DataPoint(54,46.1),new DataPoint(55,46.1),new DataPoint(56,46.1),
                new DataPoint(57,46.2),new DataPoint(58,46.2),new DataPoint(59,46.2),
                new DataPoint(60,46.3)

        });

        graph_O_C_E.addSeries(tressd);
        tressd.setColor(Color.RED);

        LineGraphSeries<DataPoint> Unossd =new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(0,35.7),new DataPoint(1,38.4),new DataPoint(2,40.3),
                new DataPoint(3,41.7),new DataPoint(4,42.8),new DataPoint(5,43.8),
                new DataPoint(6,44.6),new DataPoint(7,45.2),new DataPoint(8,45.8),
                new DataPoint(9,46.3),new DataPoint(10,46.7),new DataPoint(11,47),
                new DataPoint(12,47.4),new DataPoint(13,47.6),new DataPoint(14,47.9),
                new DataPoint(15,48.1),new DataPoint(16,48.3),new DataPoint(17,48.5),
                new DataPoint(18,48.7),new DataPoint(19,48.9),new DataPoint(20,49),
                new DataPoint(21,49.2),new DataPoint(22,49.3),new DataPoint(23,49.50),
                new DataPoint(24,49.6),new DataPoint(25,49.7),new DataPoint(26,49.9),
                new DataPoint(27,50),new DataPoint(28,50.1),new DataPoint(29,50.2),
                new DataPoint(30,50.3),new DataPoint(31,50.4),new DataPoint(32,50.5),
                new DataPoint(33,50.6),new DataPoint(34,50.7),new DataPoint(35,50.8),
                new DataPoint(36,50.9),new DataPoint(37,51),new DataPoint(38,51),
                new DataPoint(39,51.1),new DataPoint(40,51.2),new DataPoint(41,51.3),
                new DataPoint(42,51.3),new DataPoint(43,51.4),new DataPoint(44,51.4),
                new DataPoint(45,51.5),new DataPoint(46,51.6),new DataPoint(47,51.6),
                new DataPoint(48,51.7),new DataPoint(49,51.7),new DataPoint(50,51.8),
                new DataPoint(51,51.8),new DataPoint(52,51.9),new DataPoint(53,51.9),
                new DataPoint(54,52),new DataPoint(55,52),new DataPoint(56,52.1),
                new DataPoint(57,52.1),new DataPoint(58,52.1),new DataPoint(59,52.2),
                new DataPoint(60,52.2)

        });

        graph_O_C_E.addSeries(Unossd);
        Unossd.setColor(Color.YELLOW);

        LineGraphSeries<DataPoint> Dossd = new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(0,37),new DataPoint(1,39.6),new DataPoint(2,41.5),
                new DataPoint(3,42.9),new DataPoint(4,44),new DataPoint(5,45),
                new DataPoint(6,45.8),new DataPoint(7,46.4),new DataPoint(8,47),
                new DataPoint(9,47.5),new DataPoint(10,47.9),new DataPoint(11,48.3),
                new DataPoint(12,48.6),new DataPoint(13,48.9),new DataPoint(14,49.2),
                new DataPoint(15,49.4),new DataPoint(16,49.6),new DataPoint(17,49.8),
                new DataPoint(18,50),new DataPoint(19,50.2),new DataPoint(20,50.4),
                new DataPoint(21,50.5),new DataPoint(22,50.7),new DataPoint(23,50.8),
                new DataPoint(24,51),new DataPoint(25,51.1),new DataPoint(26,51.2),
                new DataPoint(27,51.4),new DataPoint(28,51.5),new DataPoint(29,51.6),
                new DataPoint(30,51.7),new DataPoint(31,51.8),new DataPoint(32,51.9),
                new DataPoint(33,52),new DataPoint(34,52.1),new DataPoint(35,52.2),
                new DataPoint(36,52.3),new DataPoint(37,52.4),new DataPoint(38,52.5),
                new DataPoint(39,52.5),new DataPoint(40,52.6),new DataPoint(41,52.7),
                new DataPoint(42,52.8),new DataPoint(43,52.8),new DataPoint(44,52.9),
                new DataPoint(45,53),new DataPoint(46,53),new DataPoint(47,53.1),
                new DataPoint(48,53.1),new DataPoint(49,53.2),new DataPoint(50,53.2),
                new DataPoint(51,53.3),new DataPoint(52,53.4),new DataPoint(53,53.4),
                new DataPoint(54,53.5),new DataPoint(55,53.5),new DataPoint(56,53.5),
                new DataPoint(57,53.6),new DataPoint(58,53.6),new DataPoint(59,53.7),
                new DataPoint(60,53.7)

        });

        graph_O_C_E.addSeries(Dossd);
        Dossd.setColor(Color.rgb(250,105,0));

        LineGraphSeries<DataPoint> Tressd = new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(0,38.3),new DataPoint(1,40.8),new DataPoint(2,42.6),
                new DataPoint(3,44.1),new DataPoint(4,45.2),new DataPoint(5,46.2),
                new DataPoint(6,47),new DataPoint(7,47.7),new DataPoint(8,48.3),
                new DataPoint(9,48.8),new DataPoint(10,49.2),new DataPoint(11,49.6),
                new DataPoint(12,49.9),new DataPoint(13,50.2),new DataPoint(14,50.5),
                new DataPoint(15,50.7),new DataPoint(16,51),new DataPoint(17,51.2),
                new DataPoint(18,51.4),new DataPoint(19,51.5),new DataPoint(20,51.7),
                new DataPoint(21,51.9),new DataPoint(22,52),new DataPoint(23,52.2),
                new DataPoint(24,52.3),new DataPoint(25,52.5),new DataPoint(26,52.6),
                new DataPoint(27,52.7),new DataPoint(28,52.9),new DataPoint(29,53),
                new DataPoint(30,53.1),new DataPoint(31,53.2),new DataPoint(32,53.3),
                new DataPoint(33,53.4),new DataPoint(34,53.5),new DataPoint(35,53.6),
                new DataPoint(36,53.7),new DataPoint(37,53.8),new DataPoint(38,53.9),
                new DataPoint(39,54),new DataPoint(40,54.1),new DataPoint(41,54.1),
                new DataPoint(42,54.2),new DataPoint(43,54.3),new DataPoint(44,54.3),
                new DataPoint(45,54.4),new DataPoint(46,54.5),new DataPoint(47,54.5),
                new DataPoint(48,54.6),new DataPoint(49,54.7),new DataPoint(50,54.7),
                new DataPoint(51,54.8),new DataPoint(52,54.8),new DataPoint(53,54.9),
                new DataPoint(54,54.9),new DataPoint(55,55),new DataPoint(56,55),
                new DataPoint(57,55.1),new DataPoint(58,55.1),new DataPoint(59,55.2),
                new DataPoint(60,55.2)

        });

        graph_O_C_E.addSeries(Tressd);
        Tressd.setColor(Color.RED);


        graph_O_C_E.setTitle("Perímetro cefálico por la Edad");

        graph_O_C_E.getGridLabelRenderer().getGridStyle().drawHorizontal();

        graph_O_C_E.getViewport().setYAxisBoundsManual(true);
        graph_O_C_E.getViewport().setMinY(29);
        graph_O_C_E.getViewport().setMaxY(56);

        graph_O_C_E.getViewport().setXAxisBoundsManual(true);
        graph_O_C_E.getViewport().setMinX(0);
        graph_O_C_E.getViewport().setMaxX(61);

        graph_O_C_E.getViewport().setScalable(true);
        graph_O_C_E.getViewport().setScalableY(true);

        graph_O_C_E.getViewport().setScrollable(true);
        graph_O_C_E.getViewport().setScrollableY(true);
    }
}
