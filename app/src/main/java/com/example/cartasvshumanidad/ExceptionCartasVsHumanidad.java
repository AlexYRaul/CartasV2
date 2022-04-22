package com.example.cartasvshumanidad;

public class ExceptionCartasVsHumanidad extends Throwable {
    private Integer codigoErrorBD;
    private String mensajeErrorBD;
    private String sentanciaSQL;
    private String mensajeUsuario;

    public ExceptionCartasVsHumanidad() {
    }

    public ExceptionCartasVsHumanidad(Integer codigoErrorBD, String mensajeErrorBD, String sentanciaSQL, String mensajeUsuario) {
        this.codigoErrorBD = codigoErrorBD;
        this.mensajeErrorBD = mensajeErrorBD;
        this.sentanciaSQL = sentanciaSQL;
        this.mensajeUsuario = mensajeUsuario;
    }

    public Integer getCodigoErrorBD() {
        return codigoErrorBD;
    }

    public void setCodigoErrorBD(Integer codigoErrorBD) {
        this.codigoErrorBD = codigoErrorBD;
    }

    public String getMensajeErrorBD() {
        return mensajeErrorBD;
    }

    public void setMensajeErrorBD(String mensajeErrorBD) {
        this.mensajeErrorBD = mensajeErrorBD;
    }

    public String getSentanciaSQL() {
        return sentanciaSQL;
    }

    public void setSentanciaSQL(String sentanciaSQL) {
        this.sentanciaSQL = sentanciaSQL;
    }

    public String getMensajeUsuario() {
        return mensajeUsuario;
    }

    public void setMensajeUsuario(String mensajeUsuario) {
        this.mensajeUsuario = mensajeUsuario;
    }

    @Override
    public String toString() {
        return "ExceptionCartasVsHumanidad{" + "codigoErrorBD=" + codigoErrorBD + ", mensajeErrorBD=" + mensajeErrorBD + ", sentanciaSQL=" + sentanciaSQL + ", mensajeUsuario=" + mensajeUsuario + '}';
    }
}
