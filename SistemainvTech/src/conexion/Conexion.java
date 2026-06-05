package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Admin
 */
public class Conexion {

    //Conexion local : metodo llamado conectar 
    public static Connection conectar() {
        try {
            Connection cn = DriverManager.getConnection("jdbc:mysql://localhost/sistemainventario", "root", "");
            return cn;
        } catch (SQLException e) {
            System.out.println("Error en la conexion local" + e);

        }
        return null;
    }

}