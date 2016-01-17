package cz.pavelpilar.calculator.calculator.history;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

        mRecycler.setAdapter(new HistoryAdapter(getHistory(), this));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) finish();
        return super.onOptionsItemSelected(menuItem);
    }

    private List<String> getHistory() {
        ArrayList<String> history = new ArrayList<>();

        DatabaseHelper helper = new DatabaseHelper(this);
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

        return history;
    }

}
