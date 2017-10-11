package dodochazoenterprise.journeydiaries.database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by donatien on 11/10/17.
 */

public class DatabaseModel {

    private static Map<TablesName, List<String>> tables = new HashMap<>();;

    public DatabaseModel(){
    }

    private static void populateTables() {

        TablesName tableName=TablesName.JOURNEY;
        List<String> columns = new ArrayList<>();

        columns.add("id");
        columns.add("name");
        columns.add("dateFrom");
        columns.add("dateTo");
        columns.add("latitude");
        columns.add("longitude");

        tables.put(tableName,columns);
    }

    public static Map<TablesName, List<String>> getTables(){
        populateTables();

        return tables;
    }

    public enum TablesName{
        JOURNEY;
    }
}
