package cz.pavelpilar.calculator.calculator.history;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import cz.pavelpilar.calculator.MainActivity;
import cz.pavelpilar.calculator.R;

public class HistoryActivity extends AppCompatActivity {

    RecyclerView mRecycler;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_history);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.toolbarDark));
        mRecycler = (RecyclerView) findViewById(R.id.history_recycler);

        mRecycler.setHasFixedSize(true);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));

        new ReadDBTask().execute(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) finish();
        return super.onOptionsItemSelected(menuItem);
    }

    private class ReadDBTask extends AsyncTask<AppCompatActivity, Void, HistoryAdapter> {

        @Override
        protected HistoryAdapter doInBackground(AppCompatActivity ... activity) {
            ArrayList<String> history = new ArrayList<>();

            DatabaseHelper helper = new DatabaseHelper(MainActivity.mContext);
            SQLiteDatabase db = helper.getReadableDatabase();

            Cursor c = db.query(
                    DatabaseHelper.Columns.TABLE_NAME,
                    new String[] {DatabaseHelper.Columns.COLUMN_INPUT, DatabaseHelper.Columns.COLUMN_RESULT},
                    null, null, null, null,
                    DatabaseHelper.Columns._ID + " DESC"
            );

            if (c.moveToFirst()){
                do history.add(c.getString(0)  + " = " + c.getString(1));
                while(c.moveToNext());
            }
            c.close();
            db.close();
            helper.close();

            return new HistoryAdapter(history, activity[0]);
        }

        protected void onPostExecute(HistoryAdapter adapter) {
            mRecycler.setAdapter(adapter);
        }

    }

}
