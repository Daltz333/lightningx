package org.emu.lightningx.services;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * This class should does raw database access
 *
 */
public class DatabaseService extends SQLiteOpenHelper {
    private static final String DB_NAME = "pf_db";
    private static final int DB_VER = 1;

    private static DatabaseService instance = null;

    private DatabaseService(Context context) {
        super(context, DB_NAME, null, DB_VER);
    }

    /**
     * This function **must** be called before get instance
     * @param context
     */
    public static void initDatabase(Context context) {
        instance = new DatabaseService(context);
    }

    /**
     * Call getInstance() to modify members of this class
     * @return
     */
    public static DatabaseService getInstance() {
        if (instance == null) {
            Log.println(Log.WARN, DatabaseService.class.getSimpleName() ,"Database service has not been initialized! Please call initDatabase()...");
        }

        return instance;
    }
    /**
     * TODO, lookup UUID in database
     * @return
     */
    public boolean doesUserExist(int uuid) {

        return false;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.println(Log.INFO, this.getClass().getSimpleName(), "Creating SQLite database...");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Log.println(Log.INFO, this.getClass().getSimpleName(), "New database version specified and old exists, upgrading...");
    }

    // SQL QUERIES
    private static final String kDatabaseCreateQuery = "";
    private static final String kAddNewProfessor = "";
    private static final String kUpdateProfessor = "";
}
