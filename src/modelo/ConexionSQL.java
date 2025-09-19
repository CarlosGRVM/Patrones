package Modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionSQL {

    // Configuración de la base de datos
    private static final String URL = "jdbc:mysql://localhost:3306/hospital"; // cambia "hospital" si tu BD tiene otro nombre
    private static final String USER = "root";   // tu usuario de MySQL
    private static final String PASSWORD = "abd1234"; // tu contraseña de MySQL

    // Método para obtener la conexión
    public static Connection conectar() {
        Connection con = null;
        try {
            // Cargar driver MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Crear la conexión
            con = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("✅ Conexión exitosa a la base de datos");
        } catch (ClassNotFoundException e) {
            System.out.println("❌ Error: Driver no encontrado - " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("❌ Error: No se pudo conectar a la BD - " + e.getMessage());
        }
        return con;
    }

    // Método de prueba
    public static void main(String[] args) {
        Connection prueba = ConexionSQL.conectar();
        if (prueba != null) {
            System.out.println("Conexión probada correctamente.");
        } else {
            System.out.println("No se pudo establecer la conexión.");
        }
    }
}
