package com.example.mychat;

public class Mensaje {

    private String Mensaje;
    private  String Nombre;
    private String fotoPerfil;
    private String type_mensaje;
    private String hora;

    public Mensaje() {
    }

    public String getMensaje() {
        return Mensaje;
    }

    public Mensaje(String mensaje, String nombre, String fotoPerfil, String type_mensaje, String hora) {
        Mensaje = mensaje;
        Nombre = nombre;
        this.fotoPerfil = fotoPerfil;
        this.type_mensaje = type_mensaje;
        this.hora = hora;
    }

    public void setMensaje(String mensaje) {
        Mensaje = mensaje;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getFotoPerfil() {
        return fotoPerfil;
    }

    public void setFotoPerfil(String fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }

    public String getType_mensaje() {
        return type_mensaje;
    }

    public void setType_mensaje(String type_mensaje) {
        this.type_mensaje = type_mensaje;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }
}
