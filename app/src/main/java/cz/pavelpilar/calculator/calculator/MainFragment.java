package cz.pavelpilar.calculator.calculator;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.DecimalFormat;

import cz.pavelpilar.calculator.MainActivity;
import cz.pavelpilar.calculator.R;
import cz.pavelpilar.calculator.calculator.history.HistoryFragment;
import cz.pavelpilar.calculator.calculator.history.WriteToDBTask;

public class MainFragment extends android.support.v4.app.Fragment {

    private DisplayFragment mDisplayFragment;
    private ButtonsFragment mButtonsFragment;

    private DecimalFormat mScientificFormat;
    private DecimalFormat mDecimalFormat;

    public double mMemory;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if(MainActivity.mPreferences != null) mMemory = Double.longBitsToDouble(MainActivity.mPreferences.getLong("calculator_memory", 0));
        Calculator.initialize(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        MainActivity.mPreferences.edit()
                                 .putLong("calculator_memory", Double.doubleToRawLongBits(mMemory))
                                 .apply();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        View v = inflater.inflate(R.layout.calculator_main, container, false);
        mDisplayFragment = (DisplayFragment) getChildFragmentManager().findFragmentById(R.id.calculator_display_fragment);
        mButtonsFragment = (ButtonsFragment) getChildFragmentManager().findFragmentById(R.id.calculator_buttons_fragment);
        return v;
    }

    @Override
    public void onResume() {    //Refresh status when leaving settings activity
        super.onResume();
        statusChanged();
        Calculator.setDegrees(MainActivity.mPreferences.getString("preferences_radDeg", "Radians").equals("Degrees"));

        int places = MainActivity.mPreferences.getInt("preferences_scientific_places", 4);
        StringBuilder pattern = new StringBuilder("0");
        if(places > 0) pattern.append('.');
        for(int i = 0; i < places; i++) pattern.append('#');
        mScientificFormat = new DecimalFormat(pattern.toString() + "E0");

        places = MainActivity.mPreferences.getInt("preferences_decimal_places", 5);
        pattern = new StringBuilder("#########");
        if(places > 0) pattern.append('.');
        for(int i = 0; i < places; i++) pattern.append('#');
        mDecimalFormat = new DecimalFormat(pattern.toString());
    }

    public void clearResult() {
        mDisplayFragment.clearResult();
    }

    public void clearMemory() {
        mMemory = 0;
    }

    public void addToMemory() {
        try {mMemory = mMemory + Double.valueOf(mDisplayFragment.getResult()); }
        catch (NumberFormatException ignored) {}
    }

    public void setResult(String result) {
        mDisplayFragment.setResult(result);
    }

    public void setResult(String result, String input) {
        if(input.equals("")) return;
        if(result.charAt(0) >= 'A' && result.charAt(0) <= 'z') {
            mDisplayFragment.setResult(result);
            return;
        }

        double value = Double.valueOf(result);
        if((value > 0 && value < 0.000001) || value > 1000000000 || value < -1000000000 || (value < 0 && value > - 0.000001)) {
            String s = mScientificFormat.format(value);
            mDisplayFragment.setResult(s);
            new WriteToDBTask().execute(input, s);
            if(MainActivity.isTablet) {
                HistoryFragment fragment = (HistoryFragment) getChildFragmentManager().findFragmentById(R.id.calculator_history_fragment);
                fragment.addItem(input + " = " + s);
            }
        }
        else {
            String s = mDecimalFormat.format(value);
            mDisplayFragment.setResult(s);
            new WriteToDBTask().execute(input, s);
            if(MainActivity.isTablet) {
                HistoryFragment fragment = (HistoryFragment) getChildFragmentManager().findFragmentById(R.id.calculator_history_fragment);
                fragment.addItem(input + " = " + s);
            }
        }
    }

    public String getResult() {
        return mDisplayFragment.getResult();
    }

    public String formatMemory() {
        String s = Double.toString(mMemory);
        if(s.contains("E")){
            if(s.indexOf("E") > 5 +2)
                    if(!s.startsWith("-")) s = s.substring(0,5 +2) + s.substring(s.indexOf("E"), s.length());
                    else s = s.substring(0, 5 +3) + s.substring(s.indexOf("E"), s.length());
            String s1 = s.substring(0, s.indexOf("E"));
            while(s1.endsWith("0")) s1 = s1.substring(0, s1.length()-1);
            if(s1.endsWith(".")) s1 = s1.substring(0, s1.length()-1);
            s = s1 + s.substring(s.indexOf("E"), s.length());
        }
        else if(s.contains(".")){
            if(s.indexOf(".")+5 < s.length()) s = s.substring(0, s.indexOf(".")+5);
            while(s.endsWith("0") && s.contains(".")) s = s.substring(0, s.length()-1);
            if(s.endsWith(".")) s = s.substring(0, s.length()-1);
        }
        return s;
    }

    public void statusChanged() {
        StringBuilder status = new StringBuilder();

        if(MainActivity.mPreferences.getString("preferences_radDeg", "Radians").equals("Degrees")) status.append("DEG ");
        else status.append("RAD ");

        switch(mButtonsFragment.getStatus()) {
            case 1: status.append(""); break;
            case 2: status.append("2nd fn "); break;
            case 3: status.append("3rd fn "); break;
            case 11: status.append("HYP "); break;
            case 12: status.append("2nd fn HYP "); break;
            case 13: status.append("3rd fn HYP "); break;
        }

        if(mMemory != 0)
            status.append("MEM: ").append(formatMemory());

        mDisplayFragment.setStatus(status.toString());
    }
}
