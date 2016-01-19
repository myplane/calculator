package cz.pavelpilar.calculator.graphs;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;

import cz.pavelpilar.calculator.MainActivity;
import cz.pavelpilar.calculator.R;

public class GraphActivity extends AppCompatActivity {

    private LineChart mChart;
    private String[] mData;
    private String mPrecision;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.graphs_graph);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.toolbarDark));
        mChart = (LineChart) findViewById(R.id.graphs_graph);
        mData = getIntent().getStringArrayExtra("DATA");
        mPrecision = MainActivity.mPreferences.getString("preferences_graphs_precision", "High");

        setupGraph();
        getGraph();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) finish();
        return super.onOptionsItemSelected(menuItem);
    }

    private void setupGraph() {
        if(MainActivity.mPreferences.getBoolean("preferences_graphs_legend", true)) {
            ArrayList<Integer> colors = new ArrayList<>();
            ArrayList<String> strings = new ArrayList<>();

            if (!mData[0].equals("")) {
                colors.add(ContextCompat.getColor(MainActivity.mContext, R.color.graph_color0));
                strings.add(mData[0].replace("<xxx>", "x"));
            }
            if (!mData[1].equals("")) {
                colors.add(ContextCompat.getColor(MainActivity.mContext, R.color.graph_color1));
                strings.add(mData[1].replace("<xxx>", "x"));
            }
            if (!mData[2].equals("")) {
                colors.add(ContextCompat.getColor(MainActivity.mContext, R.color.graph_color2));
                strings.add(mData[2].replace("<xxx>", "x"));
            }
            if (!mData[3].equals("")) {
                colors.add(ContextCompat.getColor(MainActivity.mContext, R.color.graph_color3));
                strings.add(mData[3].replace("<xxx>", "x"));
            }

            mChart.getLegend().setCustom(colors, strings);
            mChart.getLegend().setTextColor(Color.WHITE);
        } else mChart.getLegend().setEnabled(false);

        XAxis xAxis = mChart.getXAxis();
        if(mPrecision.equals("High")) xAxis.setLabelsToSkip(63);
        else xAxis.setLabelsToSkip(31);
        xAxis.setTextColor(Color.WHITE);
        xAxis.setAvoidFirstLastClipping(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        mChart.getAxisRight().setEnabled(false);

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setSpaceBottom(20);
        leftAxis.setSpaceTop(20);
        leftAxis.setLabelCount(11, false);
        leftAxis.setTextColor(Color.WHITE);

        mChart.setDescription("");
        mChart.setGridBackgroundColor(Color.parseColor("#546e7a"));
        mChart.setDoubleTapToZoomEnabled(false);
        mChart.setHighlightPerDragEnabled(false);
        mChart.setHighlightPerTapEnabled(false);
        mChart.setNoDataText("Calculating values, please wait");
    }

    private void getGraph() {
        new CalculateValuesTask().execute(null, null);
    }

    private class CalculateValuesTask extends AsyncTask<Void, Void, LineData> {

        protected LineData doInBackground(Void ... voids) {
            LineData data = new LineData();

            float precision;
            if(mPrecision.equals("High")) precision = 0.015625f;
            else precision = 0.03125f;


            for(int i = 0; i < 4; i++){
                if(mData[i].equals("")) {
                    if(i == 0) for(float xPos = -5; xPos <= 5; xPos= xPos + precision) data.addXValue(Float.toString(xPos));
                    continue;
                }
                ArrayList<Entry> yVals = new ArrayList<>();
                float xPos = -5;
                int index = 0;
                while (xPos <= 5) {
                    String yVal = Calculator.calculate(mData[i].replace("<xxx>", "(" + String.valueOf(xPos) + ")"));
                    float yValFloat = (yVal.startsWith("ERR")) ? 0 : Float.parseFloat(yVal);
                    if(yVal.startsWith("ERR") || yVal.contains("NaN") ||yVal.contains("Infinity")) {
                        if(yVals.size() > 0) {
                            LineDataSet set = new LineDataSet(yVals, "");
                            set.setDrawCircles(false);
                            set.setDrawValues(false);
                            set.setAxisDependency(YAxis.AxisDependency.LEFT);
                            switch (i) {
                                case 0: set.setColor(ContextCompat.getColor(MainActivity.mContext, R.color.graph_color0)); break;
                                case 1: set.setColor(ContextCompat.getColor(MainActivity.mContext, R.color.graph_color1)); break;
                                case 2: set.setColor(ContextCompat.getColor(MainActivity.mContext, R.color.graph_color2)); break;
                                case 3: set.setColor(ContextCompat.getColor(MainActivity.mContext, R.color.graph_color3)); break;
                            }
                            data.addDataSet(set);
                            yVals = new ArrayList<>();
                        }
                    } else yVals.add(new Entry(yValFloat, index));
                    index++;
                    if(i == 0) data.addXValue(Float.toString(xPos));
                    xPos = xPos + precision;
                }
                LineDataSet set = new LineDataSet(yVals, "");
                set.setDrawCircles(false);
                set.setDrawValues(false);
                set.setAxisDependency(YAxis.AxisDependency.LEFT);
                switch (i) {
                    case 0: set.setColor(ContextCompat.getColor(MainActivity.mContext, R.color.graph_color0)); break;
                    case 1: set.setColor(ContextCompat.getColor(MainActivity.mContext, R.color.graph_color1)); break;
                    case 2: set.setColor(ContextCompat.getColor(MainActivity.mContext, R.color.graph_color2)); break;
                    case 3: set.setColor(ContextCompat.getColor(MainActivity.mContext, R.color.graph_color3)); break;
                }
                data.addDataSet(set);
            }
            return data;
        }

        protected void onPostExecute(LineData data) {
            if(data.getDataSetCount() == 0) {
                mChart.setNoDataText(getString(R.string.error_noData));
            } else {
                mChart.setData(data);
            }
            mChart.invalidate();
        }

    }
}
