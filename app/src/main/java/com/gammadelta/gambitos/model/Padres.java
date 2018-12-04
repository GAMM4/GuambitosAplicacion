package com.gammadelta.gambitos.model;

public class Padres {

    private String id;
    private String nombre;
    private String correo;
    private String contraseña;
    private boolean es_padre;
    private String pregunta_seguridad;
    private String respuesta_pregunta;
    private String pin;

    public Padres() {

    }

    public Padres(String id, String nombre, String pregunta_seguridad,String respuesta_pregunta) {
        this.id = id;
        this.nombre = nombre;
        this.pregunta_seguridad = pregunta_seguridad;
        this.respuesta_pregunta = respuesta_pregunta;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPregunta_seguridad() {
        return pregunta_seguridad;
    }

    public void setPregunta_seguridad(String pregunta_seguridad) {
        this.pregunta_seguridad = pregunta_seguridad;
    }

    public String getRespuesta_pregunta() {
        return respuesta_pregunta;
    }

    public void setRespuesta_pregunta(String respuesta_pregunta) {
        this.respuesta_pregunta = respuesta_pregunta;
    }
}
