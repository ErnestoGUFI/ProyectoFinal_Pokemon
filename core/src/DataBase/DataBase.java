package DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataBase {
    private static final String URL = "jdbc:mysql://sql.freedb.tech:3306/freedb_PokemonGame";
    private static final String USER = "freedb_Admin123";
    private static final String PASSWORD = "d?fc#TKp5*8d6YG";
    private static final Logger LOGGER = Logger.getLogger(DataBase.class.getName());

    // Guarda el nombre de usuario en la base de datos
    public static void saveUserName(String playerName) {
        // Verifica si el nombre de usuario ya existe
        if (!isUserNameExists(playerName)) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver"); // Cargar el driver de MySQL
            } catch (ClassNotFoundException e) {
                LOGGER.log(Level.SEVERE, "MySQL Driver not found", e);
                return;
            }

            // Prepara la consulta SQL para insertar el nombre de usuario
            String sql = "INSERT INTO Jugador (Nombre) VALUES (?)";
            try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                 PreparedStatement statement = connection.prepareStatement(sql)) {

                statement.setString(1, playerName); // Establece el nombre de usuario en la consulta
                statement.executeUpdate(); // Ejecuta la consulta para insertar el nombre de usuario
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "Database error", e);
            }
        } else {
            LOGGER.log(Level.INFO, "User name already exists: " + playerName);
        }
    }

    // Verifica si el nombre de usuario ya existe en la base de datos
    private static boolean isUserNameExists(String playerName) {
        String sql = "SELECT COUNT(*) FROM Jugador WHERE Nombre = ?";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, playerName); 
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0; // Retorna true si el nombre de usuario existe
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Database error", e);
        }
        return false; // Retorna false si el nombre de usuario no existe o hay un error
    }

    // Obtiene el ID de jugador asociado a un nombre de usuario
    public static int getPlayerId(String playerName) {
        String sql = "SELECT ID_Jugador FROM Jugador WHERE Nombre = ?";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, playerName); 
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("ID_Jugador"); // Retorna el ID de jugador
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Database error", e);
        }
        return -1; // Retorna -1 si hay un error o el jugador no existe
    }

    // Guarda el puntaje de un jugador en la base de datos
    public static void saveScore(String playerName, int score) {
        int playerId = getPlayerId(playerName); // Obtiene el ID de jugador
        if (playerId != -1) { // Verifica si el jugador existe
            String sql = "INSERT INTO Partida (ID_Jugador, Guardado, Score) VALUES (?, NOW(), ?)";
            try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                 PreparedStatement statement = connection.prepareStatement(sql)) {

                statement.setInt(1, playerId); // Establece el ID de jugador en la consulta
                statement.setInt(2, score); // Establece el puntaje en la consulta
                statement.executeUpdate(); // Ejecuta la consulta para guardar el puntaje
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "Database error", e);
            }
        } else {
            LOGGER.log(Level.SEVERE, "Player ID not found for player: " + playerName);
        }
    }

    // Obtiene todos los puntajes de la base de datos
    public static ArrayList<String[]> getAllScores() {
        ArrayList<String[]> scores = new ArrayList<>();
        String sql = "SELECT j.Nombre, p.Score FROM Jugador j JOIN Partida p ON j.ID_Jugador = p.ID_Jugador";
        
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String playerName = resultSet.getString("Nombre");
                int score = resultSet.getInt("Score");
                scores.add(new String[]{playerName, String.valueOf(score)}); // Agrega el puntaje a la lista
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Database error", e);
        }
        
        return scores; // Retorna la lista de puntajes
    }
}
