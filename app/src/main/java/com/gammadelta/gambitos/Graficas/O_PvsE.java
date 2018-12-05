package com.gammadelta.gambitos.Graficas;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.gammadelta.gambitos.Medico.IngresoDatosActivity;
import com.gammadelta.gambitos.R;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.PointsGraphSeries;


public class O_PvsE extends AppCompatActivity implements IngresoDatosActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_ingreso_datos);


            GraphView points_OPvsE =(GraphView) findViewById(R.id.graph);


            PointsGraphSeries<DataPoint> ingreso_medico=new PointsGraphSeries<>(a);





            points_OPvsE.addSeries(ingreso_medico);
            ingreso_medico.setShape(PointsGraphSeries.Shape.POINT);


            GraphView graph_O_PvsE = (GraphView) findViewById(R.id.graph);


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
                    new DataPoint(3,5), new DataPoint(4,5.6), new DataPoint(5,5.6),
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



            graph_O_PvsE.getViewport().setYAxisBoundsManual(true);
            graph_O_PvsE.getViewport().setMinY(40);
            graph_O_PvsE.getViewport().setMaxY(125);

            graph_O_PvsE.getViewport().setXAxisBoundsManual(true);
            graph_O_PvsE.getViewport().setMinX(0);
            graph_O_PvsE.getViewport().setMaxX(25);


            graph_O_PvsE.getViewport().setScalable(true);
            graph_O_PvsE.getViewport().setScalableY(true);



            graph_O_PvsE.getViewport().setScrollable(true);
            graph_O_PvsE.getViewport().setScrollableY(true);
        }
    }



