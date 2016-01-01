package cz.pavelpilar.calculator.calculator;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cz.pavelpilar.calculator.MainActivity;
import cz.pavelpilar.calculator.R;

public class MainFragment extends android.support.v4.app.Fragment {

    private DisplayFragment mDisplayFragment;
    private ButtonsFragment mButtonsFragment;

    public double mMemory;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        mMemory = Double.longBitsToDouble(MainActivity.mPreferences.getLong("calculator_memory", 0));
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
        Calculator.setDegrees(MainActivity.mPreferences.getString("pref_radDeg", "Radians").equals("Degrees"));
    }

    public void clearResult() {
        mDisplayFragment.clearResult();
    }

    public void clearMemory() {
        mMemory = 0;
    }

    public void addToMemory() {
        mMemory = mMemory + Double.valueOf(mDisplayFragment.getResult());
    }

    public void setResult(String result) {
        int eNotationDigits = 5;
        int decimalPlaces = 4;
        if(result.contains("E")){
            if(result.indexOf("E") > eNotationDigits +2)
                if(eNotationDigits != 0)
                    if(!result.startsWith("-")) result = result.substring(0, eNotationDigits +2) + result.substring(result.indexOf("E"), result.length());
                    else result = result.substring(0, eNotationDigits +3) + result.substring(result.indexOf("E"), result.length());
                else {
                    if(!result.startsWith("-")) result = result.substring(0, 1) + result.substring(result.indexOf("E"), result.length());
                    else result = result.substring(0,  2) + result.substring(result.indexOf("E"), result.length());
                }

            String s1 = result.substring(0, result.indexOf("E"));
            while(s1.endsWith("0")) s1 = s1.substring(0, s1.length()-1);
            if(s1.endsWith(".")) s1 = s1.substring(0, s1.length()-1);
            result = s1 + result.substring(result.indexOf("E"), result.length());
        }
        else if(result.contains(".")){
            if(result.indexOf(".")+decimalPlaces+1 < result.length()) result = result.substring(0, result.indexOf(".")+decimalPlaces+1);
            while(result.endsWith("0") && result.contains(".")) result = result.substring(0, result.length()-1);
            if(result.endsWith(".")) result = result.substring(0, result.length()-1);
        }
        mDisplayFragment.setResult(result);
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

        if(MainActivity.mPreferences.getString("pref_radDeg", "Radians").equals("Degrees")) status.append("DEG ");
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
