package com.example.cartasvshumanidad.models;

public class Noticias {
    // Declaracion de atributos iniciales
    private String descripcion;
    private String fechaPublicacion;
    private String titulo;

    //Constructor vacio de la clase
    public Noticias() {
    }

    //Constructor completo de la clase
    public Noticias(String descripcion, String fechaPublicacion, String titulo) {
        this.descripcion = descripcion;
        this.fechaPublicacion = fechaPublicacion;
        this.titulo = titulo;
    }

    // Metodos Getter y Setter de la clase
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

    //Metodo que devuelve todos los atributos de la clase en forma de cadena String
    @Override
    public String toString() {
        return "Noticias{" +
                "descripcion='" + descripcion + '\'' +
                ", fechaPublicacion='" + fechaPublicacion + '\'' +
                ", titulo='" + titulo + '\'' +
                '}';
    }
}
