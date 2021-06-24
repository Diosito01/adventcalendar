package mx.diosito.adventcalendar.database.api;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MySQLResult {
    private Iterator<Map<String, Object>> iterator;
    private Map<String, Object> lastElement = null;
    private int size;

    protected MySQLResult(List<Map<String, Object>> results) {
        this.size = results.size();
        this.iterator = results.iterator();
    }

    protected MySQLResult() {
        this.iterator = Collections.emptyIterator();
    }

    public boolean next() {
        if (iterator.hasNext()) {
            lastElement = iterator.next();
            return true;
        }
        return false;
    }

    public boolean hasRow(String row) {
        if (lastElement == null)
            throw new UnsupportedOperationException("Usa next() primero antes de usar hasRow()");
        if (lastElement.containsKey(row))
            return true;
        return false;
    }

    public <T> T getValue(String row) {
        if (lastElement.containsKey(row)) {
            try {
                return (T) lastElement.get(row);
            } catch (ClassCastException e) {
                e.printStackTrace();
                return null;
            }
        }
        throw new IllegalArgumentException("La columna " + row + " no está definido en el Resultado de esta peticion. La columna es sensible a mayusculas.");
    }

    public int getValueAsInt(String row, int defValue) {
        if (lastElement.containsKey(row)) {
            try {
                return (int) lastElement.get(row);
            } catch (ClassCastException e) {
                e.printStackTrace();
                return defValue;
            }
        }
        throw new IllegalArgumentException("La columna " + row + " no está definido en el Resultado de esta peticion. La columna es sensible a mayusculas.");
    }

    public int getSize() {
        return size;
    }
}
