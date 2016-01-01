package cz.pavelpilar.calculator.calculator;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cz.pavelpilar.calculator.R;

public class DisplayFragment extends Fragment {

    private Display mDisplay;
    private TextView mStatus;
    private TextView mResult;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        View v = inflater.inflate(R.layout.calculator_display, container, false);

        mDisplay = (Display) v.findViewById(R.id.calculator_display);
        mStatus = (TextView) v.findViewById(R.id.calculator_display_status);
        mResult = (TextView) v.findViewById(R.id.calculator_display_result);

        return v;
    }

    public void show(String s) {
        mDisplay.show(s);
    }

    public void setResult(String result) {
        mResult.setText(result);
    }

    public void clearResult() {
        mResult.setText("");
    }

    public void setStatus(String status) {
        mStatus.setText(status);
    }
}
