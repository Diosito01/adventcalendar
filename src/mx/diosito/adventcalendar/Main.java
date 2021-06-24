package mx.diosito.adventcalendar;

import mx.diosito.adventcalendar.commands.AdventCommand;
import mx.diosito.adventcalendar.database.api.MySQL;
import mx.diosito.adventcalendar.listeners.ClicksEvents;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Main extends JavaPlugin {
    private static Logger pluginLogger;

    @Override
    public void onEnable() {
        pluginLogger = getLogger();
        if (!MySQL.setConnection()) {
            getLogger().severe("¡Plugin no habilitado por un error al establecer conexión con MySQL!");
            return;
        }
        Bukkit.getPluginManager().registerEvents(new ClicksEvents(), this);
        Bukkit.getPluginCommand("advent").setExecutor(new AdventCommand());
    }

    @Override
    public void onDisable() {
        MySQL.closeConnection();
    }

    public static void logInfo(String message) {
        if (pluginLogger == null)
            throw new IllegalArgumentException("PluginLogger no definido (null). Probablemente se ha usado logInfo() en un contexto estático antes del onEnable()");
        pluginLogger.info(message);
    }

    public static void logSevere(String message) {
        if (pluginLogger == null)
            throw new IllegalArgumentException("PluginLogger no definido (null). Probablemente se ha usado logSevere() en un contexto estático antes del onEnable()");
        pluginLogger.severe(message);
    }

    public static void logException(Throwable exception) {
        if (pluginLogger == null)
            throw new IllegalArgumentException("PluginLogger no definido (null). Probablemente se ha usado logException() en un contexto estático antes del onEnable()");
        pluginLogger.log(Level.SEVERE, exception.getMessage(), exception);
    }
}
