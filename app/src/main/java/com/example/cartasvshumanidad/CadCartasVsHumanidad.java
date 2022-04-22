package com.example.cartasvshumanidad;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CadCartasVsHumanidad {

    Connection conexion;
    public CadCartasVsHumanidad() throws ExceptionCartasVsHumanidad{
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");

        } catch (ClassNotFoundException ex) {
            ExceptionCartasVsHumanidad e = new ExceptionCartasVsHumanidad();
            e.setCodigoErrorBD(null);
            e.setMensajeErrorBD(ex.getMessage());
            e.setMensajeUsuario("Error general del sistema. Cunsulte con el administrador");
            throw e;
        }
    }
    private void conectar() throws ExceptionCartasVsHumanidad  {
        try{
            conexion = DriverManager.getConnection("jdbc:oracle:thin:@192.168.0.70:1521:xe", "System", "kk");
        }       catch (SQLException ex){
            ExceptionCartasVsHumanidad e = new ExceptionCartasVsHumanidad();
            e.setCodigoErrorBD(ex.getErrorCode());
            e.setMensajeErrorBD(ex.getMessage());
            e.setMensajeUsuario("Error general del sistema. Cunsulte con el administrador");
            throw e;
        }
    }


    public int insertarPartida(Partida partida) throws ExceptionCartasVsHumanidad{
        CadCartasVsHumanidad es = new CadCartasVsHumanidad();
        conectar();
        String dml ="insert into Partida (idPartida, idUsuario) VALUES (SEC_PARTIDA.NEXTVAL,?,?)";
        int registrosAfectados;

        try {
            PreparedStatement sentenciaPreparada = conexion.prepareStatement(dml);
            sentenciaPreparada.setObject(1,partida.getIdPartida());
            sentenciaPreparada.setInt(2, partida.getUsuario().getIdUsuario());
            registrosAfectados = sentenciaPreparada.executeUpdate();

            sentenciaPreparada.close();
            conexion.close();
        }catch (SQLException ex) {
            ExceptionCartasVsHumanidad e = new ExceptionCartasVsHumanidad();
            e.setCodigoErrorBD(ex.getErrorCode());
            e.setMensajeErrorBD(ex.getMessage());
            e.setSentanciaSQL(dml);
            switch (ex.getErrorCode()){
                case 2291: e.setMensajeUsuario("El Usuario introducido no existe");
                    break;
                case 1400: e.setMensajeUsuario("Faltan por rellenar campos obligatorios estos no pueden ser nulos, vuelva a revisar los datos introdcucidos");
                    break;
                default: e.setMensajeUsuario("Error general del sistema. Cunsulte con el administrador");
            }
            throw e;
        }
        return registrosAfectados;
    }

    public int eliminarPartida(Integer idPartida) throws ExceptionCartasVsHumanidad{
        CadCartasVsHumanidad es = new CadCartasVsHumanidad();
        conectar();
        String dml = "delete from Partida where idPartida = " + idPartida;
        int registrosAfectados;
        try {
            Statement sentencia = conexion.createStatement();
            registrosAfectados = sentencia.executeUpdate(dml);
            sentencia.close();
            conexion.close();

        }catch (SQLException ex) {
            ExceptionCartasVsHumanidad e = new ExceptionCartasVsHumanidad();
            e.setCodigoErrorBD(ex.getErrorCode());
            e.setMensajeErrorBD(ex.getMessage());
            e.setMensajeUsuario("Error al borrar. Compruebe los datos introducidos.");
            e.setSentanciaSQL(dml);

            switch(ex.getErrorCode()){
                case 2292: e.setMensajeUsuario("No se puede borrar una partida que tiene asignado usuarios.");
                    break;

                default: e.setMensajeUsuario("Error general del sistema. Consulte con el administrador");
            }
            throw e;
        }
        return registrosAfectados;
    }


    public int actualizarPartida(Integer idPartida, Partida partida) throws ExceptionCartasVsHumanidad{
        CadCartasVsHumanidad es = new CadCartasVsHumanidad();
        conectar();
        String dml = "update Partida set IDPARTIDA=?, IDUSUARIO=? where IDPARTIDA=?";
        int registrosAfectados = 0;
        try {
            PreparedStatement sentenciaPreparada = conexion.prepareStatement(dml);
            sentenciaPreparada.setInt(1, partida.getIdPartida());
            sentenciaPreparada.setInt(2, partida.getUsuario().getIdUsuario());
            sentenciaPreparada.setInt(3, idPartida);
            //sentenciaPreparada.setObject(2, null,Types.INTEGER);
            registrosAfectados = sentenciaPreparada.executeUpdate();
            sentenciaPreparada.close();
            conexion.close();

        } catch (SQLException ex) {
            ExceptionCartasVsHumanidad e = new ExceptionCartasVsHumanidad();
            e.setCodigoErrorBD(ex.getErrorCode());
            e.setMensajeErrorBD(ex.getMessage());
            e.setSentanciaSQL(dml);
            switch (ex.getErrorCode()){
                case 2291: e.setMensajeUsuario("El Usuario introducido no existe");
                    break;
                case 1: e.setMensajeUsuario("La partida no se puede repetir");
                    break;
                case 1407: e.setMensajeUsuario("El identificador de Usuario y Partida son obligatorias");
                    break;
                default: e.setMensajeUsuario("Error general del sistema. Cunsulte con el administrador");
            }
            throw e;
        }
        return registrosAfectados;
    }

    public Partida leerPartida(Integer idPartida) throws ExceptionCartasVsHumanidad{
        Partida c = new Partida();
        conectar();
        String dql = "select * from partida p, usuario u where u.idUsuario=p.idUsuario and p.idPartida="+idPartida;
        try {
            Statement sentencia = conexion.createStatement();
            ResultSet res = sentencia.executeQuery(dql);

            while (res.next()) {
                c.setIdPartida(res.getInt("idPartida"));
                Usuario g = new Usuario();
                g.setIdUsuario(res.getInt("idUsuario"));
                g.setNick(res.getString("nick"));
                g.setCorreo(res.getString("correo"));
                g.setAvatar(res.getInt("avatar"));
                c.setUsuario(g);
            }
            res.close();
            sentencia.close();
            conexion.close();
        }catch (SQLException ex) {
            ExceptionCartasVsHumanidad e = new ExceptionCartasVsHumanidad();
            e.setCodigoErrorBD(ex.getErrorCode());
            e.setMensajeErrorBD(ex.getMessage());
            e.setSentanciaSQL(dql);
            e.setMensajeUsuario("Error general del sistema. Consulte con el administrador");
            throw e;
        }
        return c;
    }

    public ArrayList<Partida> leerParitdas() throws ExceptionCartasVsHumanidad{
        CadCartasVsHumanidad es = new CadCartasVsHumanidad();
        conectar();
        String dql = "select * from usuario u, partida p where u.idUsuario=p.idUsuario";
        ArrayList<Partida> listaPartidas = new ArrayList();
        Partida partida;
        Usuario usuario;
        try {
            Statement sentencia = conexion.createStatement();
            ResultSet resultado = sentencia.executeQuery(dql);
            while (resultado.next()) {
                partida = new Partida();
                partida.setIdPartida(resultado.getInt("idPartida"));

                usuario = new Usuario();
                usuario.setIdUsuario(resultado.getInt("idUsuario"));
                usuario.setNick(resultado.getString("nick"));
                usuario.setCorreo(resultado.getString("correo"));
                usuario.setAvatar(resultado.getInt("avatar"));
                partida.setUsuario(usuario);
                listaPartidas.add(partida);
            }
            resultado.close();
            sentencia.close();
            conexion.close();
        }catch (SQLException ex) {
            ExceptionCartasVsHumanidad e = new ExceptionCartasVsHumanidad();
            e.setCodigoErrorBD(ex.getErrorCode());
            e.setMensajeErrorBD(ex.getMessage());
            e.setSentanciaSQL(dql);
            e.setMensajeUsuario("Error general del sistema. Cunsulte con el administrador");
            throw e;
        }
        return listaPartidas;
    }

    public int insertarUsuario(Usuario usuario) throws ExceptionCartasVsHumanidad{
        CadCartasVsHumanidad es = new CadCartasVsHumanidad();
        conectar();
        int registrosAfectados;
        String dml ="insert into Usuario (idUsuario, nick, correo, avatar) VALUES (SEC_USUARIO.NEXTVAL,?,?,?)";
        try {

            PreparedStatement sentenciaPreparada = conexion.prepareStatement(dml);
            sentenciaPreparada.setString(1,usuario.getNick());
            sentenciaPreparada.setString(2,usuario.getCorreo());
            sentenciaPreparada.setInt(3,usuario.getAvatar());
            registrosAfectados = sentenciaPreparada.executeUpdate();
            sentenciaPreparada.close();
            conexion.close();

        } catch (SQLException ex) {
            ExceptionCartasVsHumanidad e = new ExceptionCartasVsHumanidad();
            e.setCodigoErrorBD(ex.getErrorCode());
            e.setMensajeErrorBD(ex.getMessage());
            e.setSentanciaSQL(dml);
            switch (ex.getErrorCode()){
                case 2290: e.setMensajeUsuario("");
                    break;
                case 1: e.setMensajeUsuario("El Usuario no se puede repetir");
                    break;
                default: e.setMensajeUsuario("Error general del sistema. Cunsulte con el administrador");
            }
            throw e;
        }
        return registrosAfectados;
    }

    public int eliminarUsuario(Integer idUsuario) throws ExceptionCartasVsHumanidad{
        String dml = "delete from usuario where idUsuario = " + idUsuario;
        CadCartasVsHumanidad es = new CadCartasVsHumanidad();
        conectar();
        int registrosAfectados;

        try {
            Statement sentencia = conexion.createStatement();
            registrosAfectados = sentencia.executeUpdate(dml);
            sentencia.close();
            conexion.close();
        } catch (SQLException ex) {
            ExceptionCartasVsHumanidad e = new ExceptionCartasVsHumanidad();
            e.setCodigoErrorBD(ex.getErrorCode());
            e.setMensajeErrorBD(ex.getMessage());
            e.setSentanciaSQL(dml);
            e.setMensajeUsuario("Error al borrar, compruebe los datos.");
            throw e;
        }
        return registrosAfectados;
    }

    public int actualizarUsuario(Integer idUsuario, Usuario usuario) throws ExceptionCartasVsHumanidad{
        CadCartasVsHumanidad es = new CadCartasVsHumanidad();
        conectar();
        String dml = "update USUARIO set IDUSUARIO=?, NICK=?, CORREO=?, AVATAR=? where IDUSUARIO=?";
        int registrosAfectados = 0;
        try {
            PreparedStatement sentenciaPreparada = conexion.prepareStatement(dml);
            sentenciaPreparada.setInt(1, usuario.getIdUsuario());
            sentenciaPreparada.setString(2, usuario.getNick());
            sentenciaPreparada.setString(3, usuario.getCorreo());
            sentenciaPreparada.setInt(4, usuario.getAvatar());
            sentenciaPreparada.setInt(5, idUsuario);
            //sentenciaPreparada.setObject(2, null,Types.INTEGER);
            registrosAfectados = sentenciaPreparada.executeUpdate();
            sentenciaPreparada.close();
            conexion.close();

        }catch (SQLException ex) {
            ExceptionCartasVsHumanidad e = new ExceptionCartasVsHumanidad();
            e.setCodigoErrorBD(ex.getErrorCode());
            e.setMensajeErrorBD(ex.getMessage());
            e.setSentanciaSQL(dml);
            switch (ex.getErrorCode()){
                case 2291: e.setMensajeUsuario("El Usuario introducido no existe");
                    break;
                default: e.setMensajeUsuario("Error general del sistema. Cunsulte con el administrador");
            }
            throw e;
        }
        return registrosAfectados;
    }

    public Usuario leerUsuario(Integer idUsuario) throws ExceptionCartasVsHumanidad{
        String dql = "select * from usuario where idUsuario=" + idUsuario;
        Usuario g = new Usuario();
        conectar();
        try {
            Statement sentencia = conexion.createStatement();
            ResultSet resultado = sentencia.executeQuery(dql);

            while (resultado.next()) {
                g.setIdUsuario(resultado.getInt("idUsuario"));
                g.setNick(resultado.getString("nick"));
                g.setCorreo(resultado.getString("correo"));
                g.setAvatar(resultado.getInt("avatar"));
            }
            resultado.close();
            sentencia.close();
            conexion.close();
        } catch (SQLException ex) {
            ExceptionCartasVsHumanidad e = new ExceptionCartasVsHumanidad();
            e.setCodigoErrorBD(ex.getErrorCode());
            e.setMensajeErrorBD(ex.getMessage());
            e.setSentanciaSQL(dql);
            e.setMensajeUsuario("Error general del sistema. Consulte con el administrador");
            throw e;
        }
        return g;
    }

    public ArrayList<Usuario>leerUsuario() throws ExceptionCartasVsHumanidad{
        String dql = "select * from usuario";
        ArrayList<Usuario> listaEquipos = new ArrayList();
        Usuario usuario;
        conectar();
        try {
            Statement sentencia = conexion.createStatement();
            ResultSet resultado = sentencia.executeQuery(dql);
            while (resultado.next()) {
                usuario = new Usuario();
                usuario.setIdUsuario(resultado.getInt("idUsuario"));
                usuario.setNick(resultado.getString("nick"));
                usuario.setCorreo(resultado.getString("correo"));
                usuario.setAvatar(resultado.getInt("avatar"));
                listaEquipos.add(usuario);
            }
            resultado.close();
            sentencia.close();
            conexion.close();

        }catch (SQLException ex) {
            ExceptionCartasVsHumanidad e = new ExceptionCartasVsHumanidad();
            e.setCodigoErrorBD(ex.getErrorCode());
            e.setMensajeErrorBD(ex.getMessage());
            e.setSentanciaSQL(dql);
            e.setMensajeUsuario("Error general del sistema. Cunsulte con el administrador");
            throw e;
        }
        return listaEquipos;
    }
}
