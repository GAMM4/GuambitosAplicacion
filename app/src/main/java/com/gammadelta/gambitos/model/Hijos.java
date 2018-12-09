package com.gammadelta.gambitos.model;

public class Hijos {
    private String nombre;
    //private String documento_identidad;
    //private String estatura_madre;
    //private String estatura_padre;
    private String fecha_nacimiento;
    private String ultimaActualizacion;

    public Hijos(String nombre, String fecha_nacimiento, String ultimaActualizacion) {
        this.nombre = nombre;
        this.fecha_nacimiento = fecha_nacimiento;
        this.ultimaActualizacion = ultimaActualizacion;
    }

    public Hijos(){
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public void setFecha_nacimiento(String fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public String getUltimaActualizacion() {
        return ultimaActualizacion;
    }

    public void setUltimaActualizacion(String ultimaActualizacion) {
        this.ultimaActualizacion = ultimaActualizacion;
    }

    @Override
    public String toString() {
        return "Hijos{" +
                ", Nombre='" + fecha_nacimiento + '\'' +
                ", Fecha de nacimiento='" + fecha_nacimiento + '\'' +
                ", Ultima actualizacion='" + ultimaActualizacion + '\'' +
                '}';
    }
}
