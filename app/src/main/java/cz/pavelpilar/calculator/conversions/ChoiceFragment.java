package cz.pavelpilar.calculator.conversions;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.OnClick;
import cz.pavelpilar.calculator.R;

public class ChoiceFragment extends Fragment{

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        View v = inflater.inflate(R.layout.conversions_list, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @OnClick(R.id.conversions_length) public void conversionLength() { openConversions(R.string.conversions_length); }

    @OnClick(R.id.conversions_weight) public void conversionWeight() { openConversions(R.string.conversions_weight); }

    @OnClick(R.id.conversions_area) public void conversionArea() { openConversions(R.string.conversions_area); }

    @OnClick(R.id.conversions_volume) public void conversionVolume() { openConversions(R.string.conversions_volume); }

    @OnClick(R.id.conversions_pressure) public void conversionPressure() { openConversions(R.string.conversions_pressure); }

    @OnClick(R.id.conversions_temperature) public void conversionTemperature() { openConversions(R.string.conversions_temperature); }

    @OnClick(R.id.conversions_speed) public void conversionSpeed() { openConversions(R.string.conversions_speed); }

    private void openConversions(int resID) {
        Bundle bundle = new Bundle();
        bundle.putInt("title", resID);

        ConversionFragment fragment = new ConversionFragment();
        fragment.setArguments(bundle);
        if(!getResources().getBoolean(R.bool.smallTablet)) fragment.show(getActivity().getSupportFragmentManager().beginTransaction(), "conversions");
        else getParentFragment().getFragmentManager().beginTransaction().replace(R.id.conversions_container, fragment).commit();
    }
}
