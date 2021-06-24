package mx.diosito.adventcalendar.database;

import mx.diosito.adventcalendar.database.api.MySQLInsert;
import mx.diosito.adventcalendar.database.api.MySQLQuery;
import mx.diosito.adventcalendar.database.api.MySQLResult;

import java.util.UUID;

public class Methods {

    public boolean isClaimed(UUID uuid, int day) {
        MySQLQuery query = new MySQLQuery("advent");
        query.select("id");
        query.whereEquals("uuid", uuid.toString());
        query.whereEquals("day", String.valueOf(day));
        MySQLResult result = query.execute();
        if (result.next())
            return true;
        return false;
    }

    public void claimDay(UUID uuid, int day) {
        if (!isClaimed(uuid, day)) {
            MySQLInsert insertQuery = new MySQLInsert("advent");
            insertQuery.insert("uuid", uuid.toString());
            insertQuery.insert("day", String.valueOf(day));
            insertQuery.execute();
            return;
        }
    }
}
