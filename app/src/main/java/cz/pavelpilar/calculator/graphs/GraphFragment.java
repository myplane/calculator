package cz.pavelpilar.calculator.graphs;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;

import cz.pavelpilar.calculator.R;

public class GraphFragment extends Fragment {

    LineChart mChart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        View v = inflater.inflate(R.layout.graphs_graph, container, false);
        mChart = (LineChart) v.findViewById(R.id.graphs_graph);
        setup();
        return v;
    }

    private void setup() {
        mChart.getXAxis().setLabelsToSkip(39);
        mChart.getXAxis().setAvoidFirstLastClipping(true);
        mChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        mChart.getAxisRight().setEnabled(false);
        mChart.getAxisLeft().setSpaceBottom(20);
        mChart.getAxisLeft().setSpaceTop(20);
        mChart.getAxisLeft().setLabelCount(11, false);
        mChart.getLegend().setEnabled(false);
        mChart.setDescription("");
        mChart.setGridBackgroundColor(Color.parseColor("#546e7a"));
        mChart.setScaleYEnabled(false);
        mChart.setDoubleTapToZoomEnabled(false);
        mChart.setHighlightPerDragEnabled(false);
        mChart.setHighlightPerTapEnabled(false);
    }

    public void show(String s) {
        ArrayList<Entry> yVals = new ArrayList<>();
        ArrayList<String> xVals = new ArrayList<>();
        float xPos = -20;
        int index = 0;
        while(xPos <= 20) {
            yVals.add(new Entry((float) Math.pow(xPos, 2), index));
            index++;
            xVals.add(Float.toString(xPos));
            xPos = xPos + 0.125f;
        }
        LineDataSet set = new LineDataSet(yVals, "Graph");
        set.setDrawCircles(false);
        set.setDrawValues(false);
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        ArrayList<LineDataSet> sets = new ArrayList<>();
        sets.add(set);
        LineData data = new LineData(xVals, sets);
        mChart.setData(data);
        mChart.invalidate();
    }
    
}
