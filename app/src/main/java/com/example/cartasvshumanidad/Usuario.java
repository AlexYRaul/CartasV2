package com.example.cartasvshumanidad;

public class Usuario {
    private Integer idUsuario;
    private String nick;
    private String correo;
    private Integer avatar;

    public Usuario() {
    }

    public Usuario(Integer idUsuario, String nick, String correo, Integer avatar) {
        this.idUsuario = idUsuario;
        this.nick = nick;
        this.correo = correo;
        this.avatar = avatar;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public Integer getAvatar() {
        return avatar;
    }

    public void setAvatar(Integer avatar) {
        this.avatar = avatar;
    }

    @Override
    public String toString() {
        return "Usuario{" + "idUsuario=" + idUsuario + ", nick=" + nick + ", correo=" + correo + ", avatar=" + avatar + '}';
    }

}
