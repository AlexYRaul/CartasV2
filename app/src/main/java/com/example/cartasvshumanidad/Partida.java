package com.example.cartasvshumanidad;

public class Partida {

    private Integer idPartida;
    private Usuario usuario;

    public Partida() {
    }

    public Partida(Integer idPartida, Usuario usuario) {
        this.idPartida = idPartida;
        this.usuario = usuario;
    }

    public Integer getIdPartida() {
        return idPartida;
    }

    public void setIdPartida(Integer idPartida) {
        this.idPartida = idPartida;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public String toString() {
        return "Partida{" + "idPartida=" + idPartida + ", usuario=" + usuario + '}';
    }
}
