package cz.pavelpilar.calculator.graphs;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.Bind;
import butterknife.ButterKnife;
import cz.pavelpilar.calculator.R;

public class DisplayFragment extends Fragment {

    @Bind(R.id.graphs_display0) Display mDisplay0;
    @Bind(R.id.graphs_display1) Display mDisplay1;
    @Bind(R.id.graphs_display2) Display mDisplay2;

    private String[] lastInputs;
    private int lastInFocus;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        View v =  inflater.inflate(R.layout.graphs_display, container, false);
        ButterKnife.bind(this, v);

        mDisplay0.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View v) {
                                            InputManager.setDisplay(0);
                                         }
                                     });
        mDisplay1.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            InputManager.setDisplay(1);
                                        }
                                    });
        mDisplay2.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            InputManager.setDisplay(2);
                                        }
                                    });
        lastInputs = new String[]{null, null, null};
        lastInFocus = 4;
        return v;
    }

    public void show(String[] strings, int currentDisplay) {
        if(!strings[0].equals(lastInputs[0]) || lastInFocus != currentDisplay) mDisplay0.show(strings[0], currentDisplay == 0);
        if(!strings[1].equals(lastInputs[1]) || lastInFocus != currentDisplay) mDisplay1.show(strings[1], currentDisplay == 1);
        if(!strings[2].equals(lastInputs[2]) || lastInFocus != currentDisplay) mDisplay2.show(strings[2], currentDisplay == 2);
        lastInFocus = currentDisplay;
    }

}
