package cz.pavelpilar.calculator.graphs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cz.pavelpilar.calculator.R;

public class MainFragment extends Fragment {

    private DisplayFragment mDisplayFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        View v = inflater.inflate(R.layout.graphs_main, container, false);
        return v;
    }

    public void show(String[] strings, int currentDisplay) {
        if(mDisplayFragment == null) mDisplayFragment = (DisplayFragment) getChildFragmentManager().findFragmentById(R.id.graphs_display_fragment);
        mDisplayFragment.show(strings, currentDisplay);
    }

    public void showGraph(String[] strings) {
        startActivity(new Intent(getActivity(), GraphActivity.class).putExtra("DATA", strings));
    }
}
