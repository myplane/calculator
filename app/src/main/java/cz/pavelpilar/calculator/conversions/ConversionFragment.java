package cz.pavelpilar.calculator.conversions;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cz.pavelpilar.calculator.R;

public class ConversionFragment extends DialogFragment {

    private int mTitle;

    private EditText mInput;
    private TextView mOutput;
    private Spinner mInputSpinner;
    private Spinner mOutputSpinner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        if(getShowsDialog()) return super.onCreateView(inflater, container, bundle);

        mTitle = getArguments().getInt("title");

        View v = inflater.inflate(R.layout.conversions_layout, container, false);
        bind(v);

        return v;
    }

    @Override
    public @NonNull Dialog onCreateDialog(Bundle bundle) {
        super.onCreateDialog(bundle);
        mTitle = getArguments().getInt("title");

        View v = getActivity().getLayoutInflater().inflate(R.layout.conversions_layout, null);
        bind(v);

        return new AlertDialog.Builder(getActivity())
                              .setTitle(getString(mTitle))
                              .setView(v)
                              .create();
    }

    private void bind(View v) {
        mInput = (EditText) v.findViewById(R.id.conversions_input);
        mInputSpinner = (Spinner) v.findViewById(R.id.conversions_input_spinner);
        mOutput = (TextView) v.findViewById(R.id.conversions_output);
        mOutputSpinner = (Spinner) v.findViewById(R.id.conversions_output_spinner);

        mInput.addTextChangedListener(new TextWatcher() {
           @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
           @Override public void afterTextChanged(Editable s) {}
           @Override
           public void onTextChanged(CharSequence s, int start, int before, int count) {
                convert();
           }
        });

        List<String> list;
        switch (mTitle) {
            case R.string.conversions_length: list = Arrays.asList(getResources().getStringArray(R.array.conversions_length)); break;
            case R.string.conversions_area: list = Arrays.asList(getResources().getStringArray(R.array.conversions_area)); break;
            case R.string.conversions_volume: list = Arrays.asList(getResources().getStringArray(R.array.conversions_volume)); break;
            case R.string.conversions_weight: list = Arrays.asList(getResources().getStringArray(R.array.conversions_weight)); break;
            case R.string.conversions_pressure: list = Arrays.asList(getResources().getStringArray(R.array.conversions_pressure)); break;
            case R.string.conversions_temperature: list = Arrays.asList(getResources().getStringArray(R.array.conversions_temperature)); break;
            case R.string.conversions_speed: list = Arrays.asList(getResources().getStringArray(R.array.conversions_speed)); break;
            default: list = new ArrayList<>();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mInputSpinner.setAdapter(adapter);
        mOutputSpinner.setAdapter(adapter);

        mInputSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                convert();
            }

            @Override public void onNothingSelected(AdapterView<?> parent) {}
        });

        mOutputSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                convert();
            }

            @Override public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void convert() {
        String s = mInput.getText().toString();
        if(!s.equals("")) {
            if(s.startsWith(".")) s = "0" + s;
            BigDecimal input = new BigDecimal(s);
            input = input.setScale(20, BigDecimal.ROUND_HALF_EVEN);

            String inputType = mInputSpinner.getSelectedItem().toString();
            String outputType = mOutputSpinner.getSelectedItem().toString();
            if(inputType.equals(outputType)) {
                mOutput.setText(s);
                return;
            }

            if(inputType.equals(getString(R.string.conversions_knots))) inputType = "knots";
            switch (inputType) {

                //Length, converts to meters
                case "mm": input = input.divide(Conversions.MM, BigDecimal.ROUND_HALF_EVEN); break;
                case "cm": input = input.divide(Conversions.CM, BigDecimal.ROUND_HALF_EVEN); break;
                case "dm": input = input.divide(Conversions.DM, BigDecimal.ROUND_HALF_EVEN); break;
                case "km": input = input.divide(Conversions.KM, BigDecimal.ROUND_HALF_EVEN); break;
                case "in": input = input.divide(Conversions.IN, BigDecimal.ROUND_HALF_EVEN); break;
                case "ft": input = input.divide(Conversions.FT, BigDecimal.ROUND_HALF_EVEN); break;
                case "yd": input = input.divide(Conversions.YD, BigDecimal.ROUND_HALF_EVEN); break;
                case "mi": input = input.divide(Conversions.MI, BigDecimal.ROUND_HALF_EVEN); break;

                //Area, converts to meters²
                case "mm²": input = input.divide(Conversions.MM2, BigDecimal.ROUND_HALF_EVEN); break;
                case "cm²": input = input.divide(Conversions.CM2, BigDecimal.ROUND_HALF_EVEN); break;
                case "dm²": input = input.divide(Conversions.DM2, BigDecimal.ROUND_HALF_EVEN); break;
                case "km²": input = input.divide(Conversions.KM2, BigDecimal.ROUND_HALF_EVEN); break;
                case "in²": input = input.divide(Conversions.IN2, BigDecimal.ROUND_HALF_EVEN); break;
                case "ft²": input = input.divide(Conversions.FT2, BigDecimal.ROUND_HALF_EVEN); break;
                case "yd²": input = input.divide(Conversions.YD2, BigDecimal.ROUND_HALF_EVEN); break;
                case "mi²": input = input.divide(Conversions.MI2, BigDecimal.ROUND_HALF_EVEN); break;

                //Volume, converts to meters³
                case "mm³": input = input.divide(Conversions.MM3, BigDecimal.ROUND_HALF_EVEN); break;
                case "cm³": input = input.divide(Conversions.CM3, BigDecimal.ROUND_HALF_EVEN); break;
                case "dm³": input = input.divide(Conversions.DM3, BigDecimal.ROUND_HALF_EVEN); break;
                case "km³": input = input.divide(Conversions.KM3, BigDecimal.ROUND_HALF_EVEN); break;
                case "in³": input = input.divide(Conversions.IN3, BigDecimal.ROUND_HALF_EVEN); break;
                case "ft³": input = input.divide(Conversions.FT3, BigDecimal.ROUND_HALF_EVEN); break;
                case "yd³": input = input.divide(Conversions.YD3, BigDecimal.ROUND_HALF_EVEN); break;
                case "mi³": input = input.divide(Conversions.MI3, BigDecimal.ROUND_HALF_EVEN); break;

                //Weight, converts to kg
                case "g": input = input.divide(Conversions.G, BigDecimal.ROUND_HALF_EVEN); break;
                case "t": input = input.divide(Conversions.T, BigDecimal.ROUND_HALF_EVEN); break;
                case "oz": input = input.divide(Conversions.OZ, BigDecimal.ROUND_HALF_EVEN); break;
                case "lb": input = input.divide(Conversions.LB, BigDecimal.ROUND_HALF_EVEN); break;

                //Pressure, converts to Pa
                case "psi": input = input.divide(Conversions.PSI, BigDecimal.ROUND_HALF_EVEN); break;
                case "bar": input = input.divide(Conversions.BAR, BigDecimal.ROUND_HALF_EVEN); break;
                case "atm": input = input.divide(Conversions.ATM, BigDecimal.ROUND_HALF_EVEN); break;

                //Temperature, converts to °C
                case "°F": input = input.subtract(new BigDecimal(32)).divide(new BigDecimal("1.8"), BigDecimal.ROUND_HALF_EVEN); break;
                case "K": input = input.subtract(new BigDecimal("273.15")); break;

                //Speed, converts to m/s
                case "km/h": input = input.divide(Conversions.KMH, BigDecimal.ROUND_HALF_EVEN); break;
                case "mi/h": input = input.divide(Conversions.MPH, BigDecimal.ROUND_HALF_EVEN); break;
                case "knots": input = input.divide(Conversions.KNOTS, BigDecimal.ROUND_HALF_EVEN); break;
                case "ft/s": input = input.divide(Conversions.FTPS, BigDecimal.ROUND_HALF_EVEN); break;
                case "Mach": input = input.divide(Conversions.MACH, BigDecimal.ROUND_HALF_EVEN); break;
            }

            BigDecimal output;
            if(outputType.equals(getString(R.string.conversions_knots))) outputType = "knots";
            switch (outputType) {

                //Length, converts from meters to selected
                case "mm": output = input.multiply(Conversions.MM); break;
                case "cm": output = input.multiply(Conversions.CM); break;
                case "dm": output = input.multiply(Conversions.DM); break;
                case "km": output = input.multiply(Conversions.KM); break;
                case "in": output = input.multiply(Conversions.IN); break;
                case "ft": output = input.multiply(Conversions.FT); break;
                case "yd": output = input.multiply(Conversions.YD); break;
                case "mi": output = input.multiply(Conversions.MI); break;

                //Area, converts from meters² to selected
                case "mm²": output = input.multiply(Conversions.MM2); break;
                case "cm²": output = input.multiply(Conversions.CM2); break;
                case "dm²": output = input.multiply(Conversions.DM2); break;
                case "km²": output = input.multiply(Conversions.KM2); break;
                case "in²": output = input.multiply(Conversions.IN2); break;
                case "ft²": output = input.multiply(Conversions.FT2); break;
                case "yd²": output = input.multiply(Conversions.YD2); break;
                case "mi²": output = input.multiply(Conversions.MI2); break;

                //Volume, converts from meters³ to selected
                case "mm³": output = input.multiply(Conversions.MM3); break;
                case "cm³": output = input.multiply(Conversions.CM3); break;
                case "dm³": output = input.multiply(Conversions.DM3); break;
                case "km³": output = input.multiply(Conversions.KM3); break;
                case "in³": output = input.multiply(Conversions.IN3); break;
                case "ft³": output = input.multiply(Conversions.FT3); break;
                case "yd³": output = input.multiply(Conversions.YD3); break;
                case "mi³": output = input.multiply(Conversions.MI3); break;

                //Weight, converts from kg to selected
                case "g": output = input.multiply(Conversions.G); break;
                case "t": output = input.multiply(Conversions.T); break;
                case "oz": output = input.multiply(Conversions.OZ); break;
                case "lb": output = input.multiply(Conversions.LB); break;

                //Pressure, converts from Pa to selected
                case "psi": output = input.multiply(Conversions.PSI); break;
                case "bar": output = input.multiply(Conversions.BAR); break;
                case "atm": output = input.multiply(Conversions.ATM); break;

                //Temperature, converts from °C to selected
                case "°F": output = input.multiply(new BigDecimal("1.8")).add(new BigDecimal(32)); break;
                case "K": output = input.add(new BigDecimal("273.15")); break;

                //Speed, converts from m/s to selected
                case "km/h": output = input.multiply(Conversions.KMH); break;
                case "mi/h": output = input.multiply(Conversions.MPH); break;
                case "knots": output = input.multiply(Conversions.KNOTS); break;
                case "ft/s": output = input.multiply(Conversions.FTPS); break;
                case "Mach": output = input.multiply(Conversions.MACH); break;

                default: output = input;
            }
            String converted = output.toPlainString();
            while((converted.endsWith("0") && converted.contains(".")) || converted.endsWith(".")) converted = converted.substring(0, converted.length() - 1);
            mOutput.setText(converted);
        }else mOutput.setText("0");
    }

    private static class Conversions {

        //Length
        public static final BigDecimal MM = new BigDecimal(1000);
        public static final BigDecimal CM = new BigDecimal(100);
        public static final BigDecimal DM = new BigDecimal(10);
        public static final BigDecimal KM = new BigDecimal("0.001");
        public static final BigDecimal IN = new BigDecimal("39.3701");
        public static final BigDecimal FT = new BigDecimal("3.28084");
        public static final BigDecimal YD = new BigDecimal("1.09361");
        public static final BigDecimal MI = new BigDecimal("0.000621371");

        //Area
        public static final BigDecimal MM2 = new BigDecimal(1000000);
        public static final BigDecimal CM2 = new BigDecimal(10000);
        public static final BigDecimal DM2 = new BigDecimal(100);
        public static final BigDecimal KM2 = new BigDecimal("0.000001");
        public static final BigDecimal IN2 = new BigDecimal("1550.00477401");
        public static final BigDecimal FT2 = new BigDecimal("10.7639111056");
        public static final BigDecimal YD2 = new BigDecimal("1.1959828321");
        public static final BigDecimal MI2 = new BigDecimal("3.8610192e-7");

        //Volume
        public static final BigDecimal MM3 = new BigDecimal(1000000000);
        public static final BigDecimal CM3 = new BigDecimal(1000000);
        public static final BigDecimal DM3 = new BigDecimal(1000);
        public static final BigDecimal KM3 = new BigDecimal("0.000000001");
        public static final BigDecimal IN3 = new BigDecimal("61023.8429533");
        public static final BigDecimal FT3 = new BigDecimal("35.3146701117");
        public static final BigDecimal YD3 = new BigDecimal("1.30793878501");
        public static final BigDecimal MI3 = new BigDecimal("2.3991254e-10");

        //Weight
        public static final BigDecimal G = new BigDecimal(1000);
        public static final BigDecimal T = new BigDecimal("0.001");
        public static final BigDecimal OZ = new BigDecimal("35.274");
        public static final BigDecimal LB = new BigDecimal("2.20462");

        //Pressure
        public static final BigDecimal PSI = new BigDecimal("0.000145038");
        public static final BigDecimal BAR = new BigDecimal("0.00001");
        public static final BigDecimal ATM = new BigDecimal("9.86923e-6");

        //Speed
        public static final BigDecimal KMH = new BigDecimal("3.6");
        public static final BigDecimal MPH = new BigDecimal("2.23694");
        public static final BigDecimal KNOTS = new BigDecimal("1.94384");
        public static final BigDecimal FTPS = new BigDecimal("3.28084");
        public static final BigDecimal MACH = new BigDecimal("0.00291545");
    }
}
