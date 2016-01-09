package cz.pavelpilar.calculator.graphs;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;

import cz.pavelpilar.calculator.R;

public class GraphActivity extends AppCompatActivity {

    private LineChart mChart;
    private String[] mData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.graphs_graph);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.toolbarDark));
        mChart = (LineChart) findViewById(R.id.graphs_graph);
        mData = getIntent().getStringArrayExtra("DATA");

        setupGraph();
        getGraph();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) finish();
        return super.onOptionsItemSelected(menuItem);
    }

    private void setupGraph() {
        mChart.getXAxis().setLabelsToSkip(63);
        mChart.getXAxis().setAvoidFirstLastClipping(false);
        mChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        mChart.getAxisRight().setEnabled(false);
        mChart.getAxisLeft().setSpaceBottom(20);
        mChart.getAxisLeft().setSpaceTop(20);
        mChart.getAxisLeft().setLabelCount(11, false);
        mChart.getLegend().setEnabled(false);
        mChart.setDescription("");
        mChart.setGridBackgroundColor(Color.parseColor("#546e7a"));
        //mChart.setScaleYEnabled(false);
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
            for(int i = 0; i < 3; i++){
                if(mData[i].equals("")) continue;
                Log.d("GRAPHING", mData[i]);
                ArrayList<Entry> yVals = new ArrayList<>();
                float xPos = -5;
                int index = 0;
                while (xPos <= 5) {
                    String yVal = Calculator.calculate(mData[i].replace("<xxx>", "(" + String.valueOf(xPos) + ")"));
                    float yValFloat = (yVal.startsWith("ERR")) ? 0 : Float.parseFloat(yVal);
                    yVals.add(new Entry(yValFloat, index));
                    index++;
                    if(i == 0) data.addXValue(Float.toString(xPos));
                    xPos = xPos + 0.015625f;
                }
                LineDataSet set = new LineDataSet(yVals, String.valueOf(i));
                set.setDrawCircles(false);
                set.setDrawValues(false);
                set.setAxisDependency(YAxis.AxisDependency.LEFT);
                switch (i) {
                    case 0: set.setColor(Color.BLUE); break;
                    case 1: set.setColor(Color.YELLOW); break;
                    case 2: set.setColor(Color.RED); break;
                }
                data.addDataSet(set);
            }
            return data;
        }

        protected void onPostExecute(LineData data) {
            if(data.getDataSetCount() == 0) {
                mChart.setNoDataText("No data to display");
            } else {
                mChart.setData(data);
                mChart.invalidate();
            }
        }

    }
}
