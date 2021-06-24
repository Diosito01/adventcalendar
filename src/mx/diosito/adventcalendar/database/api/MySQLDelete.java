package mx.diosito.adventcalendar.database.api;

import mx.diosito.adventcalendar.Main;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MySQLDelete {
    private String tableName;
    private List<String> where = new ArrayList<>();
    private boolean enableDebugExecution = false;

    public MySQLDelete(String tableName) {
        if (tableName == null)
            throw new IllegalArgumentException("El nombre de la tabla no puede ser null");
        this.tableName = tableName;
    }

    public MySQLDelete enableDebugExecution() {
        enableDebugExecution = true;
        return this;
    }

    public MySQLDelete whereEquals(String row, String value) {
        if (row == null || value == null)
            throw new IllegalArgumentException("La columna o el valor no puede ser null");

        where.add(row + " = '" + value + "'");
        return this;
    }

    public void execute() {
        if (where.isEmpty())
            throw new UnsupportedOperationException("No se puede usar execute() si no se ha usado where() para definir el discriminador de la eliminación a realizar.");

        Statement stm = null;

        try {
            stm = MySQL.getConnection().createStatement();

            StringBuilder query = new StringBuilder("DELETE FROM ").append(tableName).append(" WHERE ");

            Iterator<String> whereIterator = where.iterator();
            while (whereIterator.hasNext()) {
                String whereSubQuery = whereIterator.next();

                query.append(whereSubQuery);

                if (whereIterator.hasNext())
                    query.append(" AND ");
            }

            if (enableDebugExecution)
                Main.logInfo("[MySQLDelete] Query: " + query.toString());

            //Ejecutar Query armado.
            stm.executeUpdate(query.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
