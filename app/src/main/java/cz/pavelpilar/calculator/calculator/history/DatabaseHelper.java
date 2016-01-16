package cz.pavelpilar.calculator.calculator.history;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "history.db";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + Columns.TABLE_NAME + " (" +
                              Columns._ID + " INTEGER PRIMARY KEY," +
                              Columns.COLUMN_INPUT + " TEXT," +
                              Columns.COLUMN_RESULT + "TEXT )";

    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + Columns.TABLE_NAME;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public static abstract class Columns implements BaseColumns {
        public static final String TABLE_NAME = "history";
        public static final String COLUMN_INPUT = "input";
        public static final String COLUMN_RESULT = "result";
    }

}
