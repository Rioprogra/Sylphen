package controlador;

import conexion.Conexion;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import modelo.Usuario;

public class Ctrl_Usuario {

    // LOGIN
    public boolean loginUser(Usuario objeto) {

        boolean respuesta = false;

        Connection cn = Conexion.conectar();

        String sql = "select nickname, password "
                + "from usuarios "
                + "where nickname = '"
                + objeto.getNickname()
                + "' and password = '"
                + md5(objeto.getPassword())
                + "'";

        Statement st;

        try {

            st = cn.createStatement();

            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                respuesta = true;
            }

        } catch (SQLException e) {

            System.out.println("Error al iniciar sesión");
            JOptionPane.showMessageDialog(null,
                    "Error al iniciar sesión");

        }

        return respuesta;
    }

    // REGISTRAR USUARIO
    public boolean registrarUsuario(Usuario objeto) {

        boolean respuesta = false;

        Connection cn = Conexion.conectar();

        try {

            Statement st = cn.createStatement();

            String sql = "insert into usuarios "
                    + "(nombre, apellidos, nickname, password) values ('"
                    + objeto.getNombre() + "','"
                    + objeto.getApellidos() + "','"
                    + objeto.getNickname() + "','"
                    + md5(objeto.getPassword()) + "')";

            st.executeUpdate(sql);

            respuesta = true;

        } catch (SQLException e) {

            System.out.println("Error al registrar usuario");
            JOptionPane.showMessageDialog(null,
                    "Error al registrar usuario");

        }

        return respuesta;
    }

    // CIFRADO MD5
    public String md5(String texto) {

        try {

            MessageDigest md =
                    MessageDigest.getInstance("MD5");

            byte[] mensaje =
                    md.digest(texto.getBytes());

            StringBuilder sb =
                    new StringBuilder();

            for (byte b : mensaje) {

                sb.append(
                        String.format("%02x", b)
                );
            }

            return sb.toString();

        } catch (NoSuchAlgorithmException e) {

            return null;
        }
    }
}

