package org.emu.lightningx.services;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import org.emu.lightningx.models.ProfessorModel;

/**
 * This class should does raw database access
 *
 */
public class DatabaseService extends SQLiteOpenHelper {
    private static final String DB_NAME = "pf_db.db";
    private static final int DB_VER = 2;

    private static DatabaseService instance = null;

    private final SQLiteDatabase db;
    private DatabaseService(Context context) {
        super(context, DB_NAME, null, DB_VER);

        db = getWritableDatabase();
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

        sqLiteDatabase.execSQL("CREATE TABLE Professor(uuid INTEGER, name varchar(255));");
        sqLiteDatabase.execSQL("CREATE TABLE Class(uuid Integer PRIMARY KEY AUTOINCREMENT, name varchar(255));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Log.println(Log.INFO, this.getClass().getSimpleName(), "New database version specified and old exists, upgrading...");

        try {
            sqLiteDatabase.execSQL("DROP TABLE Professor");
            sqLiteDatabase.execSQL("DROP TABLE Class");
        } catch (Exception ignore) {}

        onCreate(sqLiteDatabase);
    }

    public boolean insertProfessor(ProfessorModel prof) {
        try {
            // ADD NEW PROFESSOR TO TABLE
            String addNewProfessor = kAddNewProfessor.replace("$",
                            prof.getUuid() + "\",\"" +
                            prof.getName());


            db.execSQL(addNewProfessor);
            return true;
        } catch (SQLException ex) {
            Log.println(Log.WARN, "LightningX", ex.getMessage());
            return false;
        }
    }

    public ProfessorModel getProfessor(int uuid) {
        try {
            // GET THE UUID BASED ON ROW NUMBER
            SQLiteStatement getUuid = db.compileStatement("SELECT * FROM Professor");

            Cursor result = db.rawQuery("SELECT * FROM Professor WHERE uuid=?", new String[] {String.valueOf(uuid)});

            result.moveToFirst();
            int resultUuid = result.getInt(0);
            String name = result.getString(1);

            ProfessorModel professor = new ProfessorModel();
            professor.setUuid(uuid);
            professor.setName(name);

            result.close();

            return professor;
        } catch (SQLException ex) {
            Log.println(Log.WARN, "LightningX", ex.getMessage());
            return null;
        }
    }

    // SQL QUERIES
    private static final String kDatabaseCreateQuery = "";
    private static final String kAddNewProfessor = "INSERT INTO Professor (uuid, name) VALUES (\"$\");";
    private static final String kGetNewUuid = "SELECT * FROM Professor WHERE BLAH"; // GET ROW
    private static final String kUpdateProfessor = "";

}
