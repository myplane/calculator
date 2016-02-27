package cz.pavelpilar.calculator.calculator;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cz.pavelpilar.calculator.MainActivity;
import cz.pavelpilar.calculator.R;

public class DisplayFragment extends Fragment {

    private Display mDisplay;
    private TextView mStatus;
    private TextView mResult;

    private int resultBase;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @Override
    public void onResume() {
        super.onResume();
        DrawingManager.setTextSize(MainActivity.mPreferences.getString("preferences_size", "24"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        View v = inflater.inflate(R.layout.calculator_display, container, false);

        mDisplay = (Display) v.findViewById(R.id.calculator_display);
        mStatus = (TextView) v.findViewById(R.id.calculator_display_status);
        mResult = (TextView) v.findViewById(R.id.calculator_display_result);

        mResult.setText(MainActivity.mPreferences.getString("calculator_result", ""));
        resultBase = MainActivity.mPreferences.getInt("calculator_result_base", 10);

        return v;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        MainActivity.mPreferences.edit()
                                 .putString("calculator_result", mResult.getText().toString())
                                 .putInt("calculator_result_base", resultBase)
                                 .apply();
    }

    public void show(String s) {
        mDisplay.show(s);
    }

    public void setResult(String result) {
        if(result.contains("<sub>"))
            resultBase = Integer.parseInt(result.substring(result.indexOf("<sub>") + 26));

        mResult.setText(Html.fromHtml(result));
        if(mResult.getText().length() > 17) mResult.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);
        else mResult.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 32);
    }

    public String getResult() {
        String result = mResult.getText().toString();
        switch (resultBase) {
            case 2: return String.valueOf(Integer.parseInt(result.substring(0, result.length() - 1), 2));
            case 8: return String.valueOf(Integer.parseInt(result.substring(0, result.length() - 1), 8));
            case 16: return String.valueOf(Integer.parseInt(result.substring(0, result.length() - 2), 16));
            default: return result;
        }
    }

    public void clearResult() {
        mResult.setText("");
        resultBase = 10;
    }

    public void setStatus(String status) {
        mStatus.setText(status);
    }
}
