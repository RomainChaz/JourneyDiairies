package dodochazoenterprise.journeydiaries.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Romain on 10/10/2017.
 */

public class Database  extends SQLiteOpenHelper {

    private static Database db;

    private static final String DATABASE_NAME = "JourneyDiairies";

    private static final int DATABASE_VERSION = 3;

    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table JOURNEY( id integer primary key ,name text not null, dateFrom date not null, dateTo date not null, latitude double, longitude double, note text);";

    private Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static Database getInstance(Context context){
        if(db == null){
            db = new Database(context);
        }
        return db;
    }

    // Method is called during creation of the database
    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    // Method is called during an upgrade of the database,
    @Override
    public void onUpgrade(SQLiteDatabase database,int oldVersion,int newVersion){
        Log.w(Database.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        database.execSQL("DROP TABLE IF EXISTS JOURNEY");
        onCreate(database);
    }
}
