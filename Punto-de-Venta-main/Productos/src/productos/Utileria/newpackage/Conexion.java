package Utileria.newpackage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

     private static final String URL = "jdbc:mysql://localhost:3306/puntoventa";
    private static final String USER = "root";
    private static final String PASSWORD = "Roderik2442$";

    // Constructor privado para prevenir la creación de instancias de la clase
    private Conexion() {}

    // Método para obtener la conexión
    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexión exitosa a la base de datos.");
        } catch (SQLException e) {
            System.out.println("Error al conectar a la base de datos: " + e.getMessage());
        }
        return connection;
    }
}
