package DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataBase {
    private static final String URL = "jdbc:mysql://sql.freedb.tech:3306/freedb_PokemonGame";
    private static final String USER = "freedb_Admin123";
    private static final String PASSWORD = "d?fc#TKp5*8d6YG";
    private static final Logger LOGGER = Logger.getLogger(DataBase.class.getName());

    public static void saveUserName(String playerName) {
        // Cargar el driver de MySQL
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, "MySQL Driver not found", e);
            return;
        }

        // Establecer la conexión a la base de datos y ejecutar la consulta
        String sql = "INSERT INTO nombre_usuario (nombre_usuario) VALUES (?)";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            // Establecer los parámetros de la consulta
            statement.setString(1, playerName);

            // Ejecutar la consulta para insertar el registro
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Database error", e);
        }
    }
}
