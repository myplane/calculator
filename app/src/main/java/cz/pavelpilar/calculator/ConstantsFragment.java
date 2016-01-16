package cz.pavelpilar.calculator;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ConstantsFragment extends DialogFragment {

    private AlertDialog mDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        if(!getResources().getBoolean(R.bool.tablet)) return null;  //Opens a dialog on phones, view has to be inflated in onCreateDialog()

        View v = inflater.inflate(R.layout.constants, container, false);
        setButtons(v);
        return v;
    }

    @Override
    public @NonNull Dialog onCreateDialog(Bundle bundle) {
        View v = getActivity().getLayoutInflater().inflate(R.layout.constants, null);
        setButtons(v);
        mDialog = new AlertDialog.Builder(getActivity())
                .setTitle(R.string.constant_title)
                .setView(v)
                .setNegativeButton(R.string.close, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mDialog.dismiss();
                    }
                })
                .create();
        return mDialog;
    }

    private void setButtons(View v) {
        TextView light = (TextView)v.findViewById(cz.pavelpilar.calculator.R.id.constant_light);
        light.setText("c " + getResources().getString(cz.pavelpilar.calculator.R.string.constant_light) + " 299792458");
        light.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               addConstant("299792458");
            }
        });

        TextView gravity = (TextView)v.findViewById(cz.pavelpilar.calculator.R.id.constant_gravity);
        gravity.setText("G " + getResources().getString(cz.pavelpilar.calculator.R.string.constant_gravity) + " 6.674E-11");
        gravity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addConstant("6.674E-11");
            }
        });

        TextView planck = (TextView)v.findViewById(cz.pavelpilar.calculator.R.id.constant_planck);
        planck.setText("h " + getResources().getString(cz.pavelpilar.calculator.R.string.constant_planck) + " 6.626E-34");
        planck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addConstant("6.626E-34");
            }
        });

        TextView charge = (TextView)v.findViewById(cz.pavelpilar.calculator.R.id.constant_elementaryCharge);
        charge.setText("e " + getResources().getString(cz.pavelpilar.calculator.R.string.constant_elementaryCharge) + " 1.602E-19");
        charge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addConstant("1.602E-19");
            }
        });

        TextView permittivity = (TextView)v.findViewById(cz.pavelpilar.calculator.R.id.constant_vacPermittivity);
        permittivity.setText(Html.fromHtml("ε<sub><small>0</small></sub> " + getResources().getString(cz.pavelpilar.calculator.R.string.constant_permittivity) + " 8.854E-12"));
        permittivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addConstant("8.854E-12");
            }
        });

        TextView permeability = (TextView)v.findViewById(cz.pavelpilar.calculator.R.id.constant_vacPermeability);
        permeability.setText(Html.fromHtml("μ<sub><small>0</small></sub> " + getResources().getString(cz.pavelpilar.calculator.R.string.constant_permeability) + " 1.256E-6"));
        permeability.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addConstant("1.256E-6");
            }
        });

        TextView earth = (TextView)v.findViewById(cz.pavelpilar.calculator.R.id.constant_gravityEarth);
        earth.setText("g " + getResources().getString(cz.pavelpilar.calculator.R.string.constant_gravityEarth) + " 9.80665");
        earth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addConstant("9.80665");
            }
        });

        TextView avogadro = (TextView)v.findViewById(cz.pavelpilar.calculator.R.id.constant_avogadro);
        avogadro.setText(Html.fromHtml("N<sub><small>A</small></sub> " + getResources().getString(cz.pavelpilar.calculator.R.string.constant_avogadro) + " 6.022E23"));
        avogadro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addConstant("6.022E23");
            }
        });

        TextView boltzmann = (TextView)v.findViewById(cz.pavelpilar.calculator.R.id.constant_boltzmann);
        boltzmann.setText("k " + getResources().getString(cz.pavelpilar.calculator.R.string.constant_boltzmann) + " 1.380E-23");
        boltzmann.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addConstant("1.380E-23");
            }
        });

        TextView faraday = (TextView)v.findViewById(cz.pavelpilar.calculator.R.id.constant_faraday);
        faraday.setText("F " + getResources().getString(cz.pavelpilar.calculator.R.string.constant_faraday) + " 96485.332");
        faraday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addConstant("96485.332");
            }
        });

        TextView rydberg = (TextView)v.findViewById(cz.pavelpilar.calculator.R.id.constant_rydberg);
        rydberg.setText(Html.fromHtml("R<sub><small>∞</small></sub> " + getResources().getString(cz.pavelpilar.calculator.R.string.constant_rydberg) + " 1.093E7"));
        rydberg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addConstant("1.093E7");
            }
        });
        
        TextView stefanBoltzmann = (TextView)v.findViewById(cz.pavelpilar.calculator.R.id.constant_stefanBoltzmann);
        stefanBoltzmann.setText("σ " + getResources().getString(cz.pavelpilar.calculator.R.string.constant_stefanBoltzmann) + " 5.670E-8");
        stefanBoltzmann.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addConstant("5.670E-8");
            }
        });

        TextView electron = (TextView)v.findViewById(cz.pavelpilar.calculator.R.id.constant_massElectron);
        electron.setText(Html.fromHtml("m<sub><small>e</small></sub> " + getResources().getString(cz.pavelpilar.calculator.R.string.constant_electron) + " 9.109E-31"));
        electron.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addConstant("9.109E-31");
            }
        });

        TextView proton = (TextView)v.findViewById(cz.pavelpilar.calculator.R.id.constant_massProton);
        proton.setText(Html.fromHtml("m<sub><small>p</small></sub> " + getResources().getString(cz.pavelpilar.calculator.R.string.constant_proton) + " 1.672E-27"));
        proton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addConstant("1.672E-27");
            }
        });

        TextView neutron = (TextView)v.findViewById(cz.pavelpilar.calculator.R.id.constant_massNeutron);
        neutron.setText(Html.fromHtml("m<sub><small>u</small></sub> " + getResources().getString(cz.pavelpilar.calculator.R.string.constant_neutron) + " 1.674E-27"));
        neutron.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addConstant("1.674E-27");
            }
        });

        TextView alpha = (TextView)v.findViewById(cz.pavelpilar.calculator.R.id.constant_massAlpha);
        alpha.setText(Html.fromHtml("m<sub><small>α</small></sub> " + getResources().getString(cz.pavelpilar.calculator.R.string.constant_alpha) + " 1.664E-27"));
        alpha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addConstant("1.664E-27");
            }
        });

        TextView deuteron = (TextView)v.findViewById(cz.pavelpilar.calculator.R.id.constant_massDeuteron);
        deuteron.setText(Html.fromHtml("m<sub><small>d</small></sub> " + getResources().getString(cz.pavelpilar.calculator.R.string.constant_deuteron) + " 3.343E-27"));
        deuteron.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addConstant("3.343E-27");
            }
        });

        TextView helion = (TextView)v.findViewById(cz.pavelpilar.calculator.R.id.constant_massHelion);
        helion.setText(Html.fromHtml("m<sub><small>h</small></sub> " + getResources().getString(cz.pavelpilar.calculator.R.string.constant_helion) + " 5.006E-27"));
        helion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addConstant("5.006E-27");
            }
        });

        TextView muon = (TextView)v.findViewById(cz.pavelpilar.calculator.R.id.constant_massMuon);
        muon.setText(Html.fromHtml("m<sub><small>μ</small></sub> " + getResources().getString(cz.pavelpilar.calculator.R.string.constant_muon) + " 1.883E-28"));
        muon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addConstant("1.883E-28");
            }
        });

        TextView tau = (TextView)v.findViewById(cz.pavelpilar.calculator.R.id.constant_massTau);
        tau.setText(Html.fromHtml("m<sub><small>τ</small></sub> " + getResources().getString(cz.pavelpilar.calculator.R.string.constant_tau) + " 3.167E-27"));
        tau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addConstant("3.167E-27");
            }
        });

        TextView triton = (TextView)v.findViewById(cz.pavelpilar.calculator.R.id.constant_massTriton);
        triton.setText(Html.fromHtml("m<sub><small>t</small></sub> " + getResources().getString(cz.pavelpilar.calculator.R.string.constant_triton) + " 5.007E-27"));
        triton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addConstant("5.007E-27");
            }
        });

        TextView klitzing = (TextView)v.findViewById(cz.pavelpilar.calculator.R.id.constant_vonKlitzing);
        klitzing.setText(Html.fromHtml("R<sub><small>K</small></sub> " + getResources().getString(cz.pavelpilar.calculator.R.string.constant_vonKlitzing) + " 25812.807"));
        klitzing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addConstant("25812.807");
            }
        });

        TextView bohr = (TextView)v.findViewById(cz.pavelpilar.calculator.R.id.constant_bohrMagneton);
        bohr.setText(Html.fromHtml("μ<sub><small>B</small></sub> " + getResources().getString(cz.pavelpilar.calculator.R.string.constant_bohrMagneton) + " 9.274E-24"));
        bohr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addConstant("9.274E-24");
            }
        });

        TextView fineStructure = (TextView)v.findViewById(cz.pavelpilar.calculator.R.id.constant_fineStructure);
        fineStructure.setText("α " + getResources().getString(cz.pavelpilar.calculator.R.string.constant_fineStructure) + " 7.297E-3");
        fineStructure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addConstant("7.297E-3");
            }
        });
    }

    private void addConstant(String constant) {
        switch (MainActivity.mPreferences.getString("lastFragment", "")) {
            case "calculator":
                cz.pavelpilar.calculator.calculator.InputManager.add(constant);
                break;
            case "graphs":
                cz.pavelpilar.calculator.graphs.InputManager.add(constant);
        }
        if(mDialog != null) mDialog.dismiss();
    }

}
