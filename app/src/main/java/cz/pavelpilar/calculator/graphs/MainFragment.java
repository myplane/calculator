package cz.pavelpilar.calculator.graphs;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cz.pavelpilar.calculator.R;

public class MainFragment extends Fragment {

    private GraphFragment mGraphFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        return inflater.inflate(R.layout.graphs_main, container, false);
    }

    public void showGraph(String s) {
        if(mGraphFragment == null){
            mGraphFragment = (GraphFragment) getChildFragmentManager().findFragmentById(R.id.graphs_graph_fragment);
        }

        mGraphFragment.show(s);
    }

}
