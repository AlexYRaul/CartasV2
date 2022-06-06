package com.example.cartasvshumanidad;

public class Usuario {
    //Atributos de la clase
    private Integer idUsuario;
    private String nick;
    private String correo;
    private Integer avatar;

    //Constructor vacio de la clase Usuario
    public Usuario() {
    }

    /**
     * Constructor parametrizado de la clase
     * @param idUsuario Almacena id de usuario
     * @param nick Almacena el nick del uuario
     * @param correo Almacena el correo del usuario
     * @param avatar Almacena el avatar del usario.
     */
    public Usuario(Integer idUsuario, String nick, String correo, Integer avatar) {
        this.idUsuario = idUsuario;
        this.nick = nick;
        this.correo = correo;
        this.avatar = avatar;
    }

    //Metodos Getter y Setter de la clase
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

    /**
     * Este metodo sirve para mostrar los atributos de la clase en forma de cadena de texto
     * @return Devuelve los atributos de la clase en forma de cadena String.
     */
    @Override
    public String toString() {
        return "Usuario{" + "idUsuario=" + idUsuario + ", nick=" + nick + ", correo=" + correo + ", avatar=" + avatar + '}';
    }

}
