package mx.diosito.adventcalendar.database.api;

import mx.diosito.adventcalendar.Main;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class MySQLQuery {
    private String tableName;
    private List<String> selects = new ArrayList<>();
    private List<String> wheres = new ArrayList<>();
    private int limit1 = -1;
    private int limit2 = -1;
    private String orderBy;
    private OrderByOrientation orientation = null;
    private boolean enableDebugExecution = false;

    public MySQLQuery(String tableName) {
        this.tableName = tableName;
    }

    public MySQLQuery enableDebugExecution() {
        enableDebugExecution = true;
        return this;
    }

    public MySQLQuery select(String row) {
        selects.add(row);
        return this;
    }

    public MySQLQuery whereEquals(String row, String value) {
        wheres.add(row + " = '" + value + "'");
        return this;
    }

    public MySQLQuery orderBy(String row) {
        return orderBy(row, null);
    }

    public MySQLQuery orderBy(String row, OrderByOrientation orientation) {
        orderBy = row;
        this.orientation = orientation;
        return this;
    }

    public MySQLQuery limit(int amount) {
        limit1 = amount;
        return this;
    }

    public MySQLQuery limit(int offset, int amount) {
        this.limit1 = offset;
        this.limit2 = amount;
        return this;
    }

    public enum OrderByOrientation {
        ASC,
        DESC,
        ;
    }

    public MySQLResult execute() {
        Statement stm = null;

        try {
            stm = MySQL.getConnection().createStatement();

            StringBuilder query = new StringBuilder("SELECT ");

            if (selects.isEmpty()) {
                query.append("*");
            } else {
                Iterator<String> selectIterator = selects.iterator();
                while (selectIterator.hasNext()) {
                    String select = selectIterator.next();

                    query.append(select);

                    if (selectIterator.hasNext()) {
                        query.append(",");
                    }
                }
            }

            query.append(" FROM ").append(tableName);

            if (!wheres.isEmpty()) {
                query.append(" WHERE ");

                Iterator<String> wheresIterator = wheres.iterator();
                while (wheresIterator.hasNext()) {
                    query.append(wheresIterator.next());

                    if (wheresIterator.hasNext())
                        query.append(" AND ");
                }
            }

            if (orderBy != null) {
                query.append(" ORDER BY ").append(orderBy);

                if (orientation != null)
                    query.append(" ").append(orientation.toString());
            }

            if (limit1 > 0) {
                query.append(" LIMIT ").append(limit1);

                if (limit2 > 0)
                    query.append(",").append(limit2);
            }

            if (enableDebugExecution)
                Main.logInfo("[MySQLQuery] Query: " + query.toString());

            //Ejecutar Query armado.
            ResultSet res = stm.executeQuery(query.toString());
            ResultSetMetaData meta = res.getMetaData();
            List<String> columnsNames = new ArrayList<>();

            for (int i = 0; i < meta.getColumnCount(); i++)
                columnsNames.add(meta.getColumnName(i + 1));

            List<Map<String, Object>> results = new ArrayList<>();

            while (res.next()) {
                Map<String, Object> subresult = new HashMap<>();

                for (int i = 0; i < meta.getColumnCount(); i++)
                    subresult.put(columnsNames.get(i), res.getObject(i + 1));

                results.add(subresult);
            }

            return new MySQLResult(results);
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

        return new MySQLResult();
    }

}
