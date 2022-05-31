package com.example.cartasvshumanidad;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Partida {

    private Integer idPartida;
    private Usuario usuario;

    FirebaseDatabase dataBase;
    DatabaseReference roomRef;

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
