package mx.diosito.adventcalendar.database.api;

import mx.diosito.adventcalendar.Main;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MySQLInsert {
    private String tableName;
    private List<String> rows = new ArrayList<>();
    private List<String> values = new ArrayList<>();
    private boolean enableDebugExecution = false;
    private boolean retrieveID = false;

    public MySQLInsert(String tableName) {
        this.tableName = tableName;
    }

    public MySQLInsert enableDebugExecution() {
        enableDebugExecution = true;
        return this;
    }

    public void setRetrieveID() {
        this.retrieveID = true;
    }

    public MySQLInsert insert(String row, String value) {
        if (row == null || value == null)
            throw new IllegalArgumentException("La columna o el valor no puede ser null");
        rows.add(row);
        values.add(value);
        return this;
    }

    public int execute() {
        if (rows.isEmpty())
            throw new UnsupportedOperationException("No se puede usar execute() si no se ha usado insert() para definir las columnas a afectar en la operacion INSERT");

        Statement stm = null;

        try {
            stm = MySQL.getConnection().createStatement();

            StringBuilder query = new StringBuilder("INSERT INTO ").append(tableName).append(" (");

            Iterator<String> rowsIterator = rows.iterator();
            while (rowsIterator.hasNext()) {
                String row = rowsIterator.next();
                query.append(row);

                if (rowsIterator.hasNext()) {
                    query.append(",");
                }
            }

            query.append(") VALUES (");

            Iterator<String> valuesIterator = values.iterator();
            while (valuesIterator.hasNext()) {
                String value = valuesIterator.next();
                query.append("'").append(value).append("'");

                if (valuesIterator.hasNext()) {
                    query.append(",");
                }
            }

            query.append(")");

            if (enableDebugExecution)
                Main.logInfo("[MySQLInsert] Query: " + query.toString());

            //Ejecutar Query armado.
            if (retrieveID) {
                stm.executeUpdate(query.toString(), Statement.RETURN_GENERATED_KEYS);
                ResultSet keys = stm.getGeneratedKeys();
                if (keys.next())
                    return keys.getInt(1);
            } else {
                stm.executeUpdate(query.toString());
                return -1;
            }
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
        return -1;
    }
}
