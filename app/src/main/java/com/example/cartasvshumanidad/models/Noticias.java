package com.example.cartasvshumanidad.models;

public class Noticias {

    String descripcion;
    String fechaPublicacion;
    private String titulo;

    public Noticias() {

    }

    public Noticias(String descripcion, String fechaPublicacion, String titulo) {
        this.descripcion = descripcion;
        this.fechaPublicacion = fechaPublicacion;
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(String fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
}
