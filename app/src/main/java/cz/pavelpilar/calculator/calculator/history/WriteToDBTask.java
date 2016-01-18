package cz.pavelpilar.calculator.calculator.history;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import cz.pavelpilar.calculator.MainActivity;

public class WriteToDBTask extends AsyncTask<String, Void, Void> {

    protected Void doInBackground(String ... strings) {
        DatabaseHelper helper = new DatabaseHelper(MainActivity.mContext);

        //Check 20 last items and don't add if exists
        SQLiteDatabase dbRead = helper.getReadableDatabase();

        Cursor c = dbRead.query(DatabaseHelper.Columns.TABLE_NAME,
                            new String[] {DatabaseHelper.Columns.COLUMN_INPUT},
                            null, null, null, null, DatabaseHelper.Columns._ID + " DESC", "20");
        if (c.moveToFirst()){
            do if(strings[0].equals(c.getString(0))) return null;
            while(c.moveToNext());
        }
        c.close();
        dbRead.close();

        SQLiteDatabase dbWrite = helper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.Columns.COLUMN_INPUT, strings[0]);
        values.put(DatabaseHelper.Columns.COLUMN_RESULT, strings[1]);

        dbWrite.insert(DatabaseHelper.Columns.TABLE_NAME, "null", values);

        dbWrite.close();
        helper.close();

        return null;
    }

}
