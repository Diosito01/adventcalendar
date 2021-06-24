package mx.diosito.adventcalendar.database.api;

import mx.diosito.adventcalendar.Main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQL {

    private static Connection connection;

    public static boolean setConnection() {
        if (connection != null)
            throw new IllegalStateException("¡La conexión a la base de datos ya está establecida!");
        try {
            Class.forName("com.mysql.jdbc.Driver");

            String host = "localhost";
            String user = "root";
            String pass = "";
            String database = "minecraft";

            Main.logInfo("-----------------------------------");
            Main.logInfo("Intentando establecer conexión MySQL...");
            Main.logInfo("Host: " + host);
            Main.logInfo("Usuario: " + user);
            Main.logInfo("DB: " + database);

            connection = DriverManager.getConnection("jdbc:mysql://" + host + ":3306/" + database, user, pass);

            Main.logInfo("¡Conexión con MySQL establecida!");
            Main.logInfo("-----------------------------------");
            return true;
        } catch (ClassNotFoundException e) {
            Main.logSevere("¡No se encontró el driver JDBC para MySQL!");
            Main.logException(e);
            Main.logInfo("-----------------------------------");
        } catch (SQLException e) {
            Main.logSevere("¡Error al establecer conexión con servidor MySQL!");
            Main.logException(e);
            Main.logInfo("-----------------------------------");
        }
        return false;
    }

    public static Connection getConnection() {
        if (connection == null)
            throw new IllegalStateException("¡No se esperaba que la conexión no estuviera definida (null)!");
        return connection;
    }

    public static void closeConnection() {
        try {
            Main.logInfo("Cerrando conexión con MySQL...");
            connection.close();
        } catch (SQLException e) {
            Main.logSevere("¡Error al terminar conexión MySQL!");
            Main.logException(e);
        }
        connection = null;
    }
}
