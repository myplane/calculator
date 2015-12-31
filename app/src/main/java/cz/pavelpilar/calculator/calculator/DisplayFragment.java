package cz.pavelpilar.calculator.calculator;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

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

    public void setResult(String s) {
        int eNotationDigits = 5;
        int decimalPlaces = 4;
        mResult.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 32);
        if(s.contains("E")){
            if(s.indexOf("E") > eNotationDigits +2)
                if(eNotationDigits != 0)
                    if(!s.startsWith("-")) s = s.substring(0, eNotationDigits +2) + s.substring(s.indexOf("E"), s.length());
                    else s = s.substring(0, eNotationDigits +3) + s.substring(s.indexOf("E"), s.length());
                else {
                    if(!s.startsWith("-")) s = s.substring(0, 1) + s.substring(s.indexOf("E"), s.length());
                    else s = s.substring(0,  2) + s.substring(s.indexOf("E"), s.length());
                }

            String s1 = s.substring(0, s.indexOf("E"));
            while(s1.endsWith("0")) s1 = s1.substring(0, s1.length()-1);
            if(s1.endsWith(".")) s1 = s1.substring(0, s1.length()-1);
            s = s1 + s.substring(s.indexOf("E"), s.length());
        }
        else if(s.contains(".")){
            if(s.indexOf(".")+decimalPlaces+1 < s.length()) s = s.substring(0, s.indexOf(".")+decimalPlaces+1);
            while(s.endsWith("0") && s.contains(".")) s = s.substring(0, s.length()-1);
            if(s.endsWith(".")) s = s.substring(0, s.length()-1);
        } else if (s.length() > 2){
            char c = s.charAt(0);           //If string starts with a letter
            if(c >= 'A' && c <= 'z') {
                if (s.startsWith("Factorial")) mResult.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 22);
            }
        }
        mResult.setText(s);
    }

    public void clearResult() {
        mResult.setText("");
    }
}
