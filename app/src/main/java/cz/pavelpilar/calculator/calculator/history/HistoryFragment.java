package cz.pavelpilar.calculator.calculator.history;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import cz.pavelpilar.calculator.MainActivity;
import cz.pavelpilar.calculator.R;

public class HistoryFragment extends Fragment {

    private RecyclerView mRecycler;
    private HistoryAdapter mAdapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        View v = inflater.inflate(R.layout.history, container, false);

        mRecycler = (RecyclerView) v.findViewById(R.id.history_recycler);

        mRecycler.setHasFixedSize(true);
        mRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        if(MainActivity.isTablet) new ReadDBTask().execute();
        else new ReadDBTask().execute((AppCompatActivity) getActivity());

        return v;
    }

    public void addItem(String s) {
        if((mAdapter.history.size() > 0 && !mAdapter.history.get(0).split(" = ")[0].equals(s.split(" = ")[0]))
         || mAdapter.history.size() == 0) {
            mAdapter.history.add(0, s);
            mAdapter.notifyItemInserted(0);
            mRecycler.scrollToPosition(0);
        }
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

            if(activity.length > 0) return new HistoryAdapter(history, activity[0]);
            else return new HistoryAdapter(history, null);
        }

        protected void onPostExecute(HistoryAdapter adapter) {
            mAdapter = adapter;
            mRecycler.setAdapter(adapter);
        }

    }

}
