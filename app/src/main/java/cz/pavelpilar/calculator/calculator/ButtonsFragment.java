package cz.pavelpilar.calculator.calculator;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cz.pavelpilar.calculator.R;

public class ButtonsFragment extends Fragment {
    private static String TAG = "ButtonsFragment";

    @Bind(R.id.buttonDot) Button mButtonDot;
    @Bind(R.id.button0) Button mButton0;
    @Bind(R.id.button1) Button mButton1;
    @Bind(R.id.button2) Button mButton2;
    @Bind(R.id.button3) Button mButton3;
    @Bind(R.id.button4) Button mButton4;
    @Bind(R.id.button5) Button mButton5;
    @Bind(R.id.button6) Button mButton6;
    @Bind(R.id.button7) Button mButton7;
    @Bind(R.id.button8) Button mButton8;
    @Bind(R.id.button9) Button mButton9;
    @Bind(R.id.buttonEquals) Button mButtonEquals;

    @Bind(R.id.buttonFraction) Button mButtonFraction;
    @Bind(R.id.buttonFactorial) Button mButtonFactorial;
    @Bind(R.id.buttonModulo) Button mButtonModulo;
    @Bind(R.id.buttonRoot) Button mButtonRoot;
    @Bind(R.id.buttonPower) Button mButtonPower;
    @Bind(R.id.buttonPercent) Button mButtonPercent;

    @Bind(R.id.buttonHyp) Button mButtonHyp;
    @Bind(R.id.buttonSin) Button mButtonSin;
    @Bind(R.id.buttonCos) Button mButtonCos;
    @Bind(R.id.buttonTan) Button mButtonTan;
    @Bind(R.id.buttonMemory) Button mButtonMemory;
    @Bind(R.id.buttonRandom) Button mButtonRandom;

    @Bind(R.id.buttonLog) Button mButtonLog;
    @Bind(R.id.buttonLn) Button mButtonLn;
    @Bind(R.id.buttonHex) Button mButtonHex;
    @Bind(R.id.buttonOct) Button mButtonOct;
    @Bind(R.id.buttonBin) Button mButtonBin;
    @Bind(R.id.buttonE) Button mButtonE;

    @Bind(R.id.buttonShift) Button mButtonShift;
    @Bind(R.id.buttonShift2) Button mButtonShift2;
    @Bind(R.id.buttonNavLeft) Button mButtonNavLeft;
    @Bind(R.id.buttonNavRight) Button mButtonNavRight;
    @Bind(R.id.buttonClear) Button mButtonClear;
    @Bind(R.id.buttonBackspace) ImageButton mButtonBackspace;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        InputManager.initialize();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        View v = inflater.inflate(R.layout.calculator_buttons, container, false);

        ButterKnife.bind(this, v);
        setLayoutText(v);

        return v;
    }

    private void setLayoutText(View v) {
        TextView tv = (TextView) v.findViewById(R.id.textRoot);
        tv.setText(Html.fromHtml("<small><sup>x</sup></small>√ "));
        tv = (TextView) v.findViewById(R.id.textPower);
        tv.setText(Html.fromHtml("x<small><sup>y "));
        tv = (TextView) v.findViewById(R.id.textSin);
        tv.setText(Html.fromHtml("sin<small><sup>-1 "));
        tv = (TextView) v.findViewById(R.id.textCos);
        tv.setText(Html.fromHtml("cos<small><sup>-1 "));
        tv = (TextView) v.findViewById(R.id.textTan);
        tv.setText(Html.fromHtml("tan<small><sup>-1 "));
        tv = (TextView) v.findViewById(R.id.textLog);
        tv.setText(Html.fromHtml("log<small><small>x</small></small>y "));

        mButtonPower.setText(Html.fromHtml("x<small><sup>2</sup></small>"));
    }

    @OnClick(R.id.button0) void button0() { InputManager.add("0"); }
    @OnClick(R.id.button1) void button1() { InputManager.add("1"); }
    @OnClick(R.id.button2) void button2() { InputManager.add("2"); }
    @OnClick(R.id.button3) void button3() { InputManager.add("3"); }
    @OnClick(R.id.button4) void button4() { InputManager.add("4"); }
    @OnClick(R.id.button5) void button5() { InputManager.add("5"); }
    @OnClick(R.id.button6) void button6() { InputManager.add("6"); }
    @OnClick(R.id.button7) void button7() { InputManager.add("7"); }
    @OnClick(R.id.button8) void button8() { InputManager.add("8"); }
    @OnClick(R.id.button9) void button9() { InputManager.add("9"); }
    @OnClick(R.id.buttonDot) void buttonDot() { InputManager.add("."); }
    @OnClick(R.id.buttonPlus) void buttonPlus() { InputManager.add("+"); }
    @OnClick(R.id.buttonMinus) void buttonMinus() { InputManager.add("-"); }
    @OnClick(R.id.buttonMultiply) void buttonMultiply() { InputManager.add("×"); }
    @OnClick(R.id.buttonDivide) void buttonDivide() { InputManager.add("÷"); }
    @OnClick(R.id.buttonStartParentheses) void buttonStartParentheses() { InputManager.add("<par>"); }
    @OnClick(R.id.buttonEndParentheses) void buttonEndParentheses() { InputManager.add("<prn>"); }

    @OnClick(R.id.buttonFraction) void buttonFraction() { InputManager.add("<fra>|<frx><frn>"); }
    @OnClick(R.id.buttonFactorial) void buttonFactorial() { InputManager.add("!"); }
    @OnClick(R.id.buttonModulo) void buttonModulo() { InputManager.add("<mod>"); }
    @OnClick(R.id.buttonRoot) void buttonRoot() { InputManager.add("<srt><rtn>"); }
    @OnClick(R.id.buttonPower) void buttonPower() { InputManager.add("<pow><pwn>"); }
    @OnClick(R.id.buttonPercent) void buttonPercent() { InputManager.add("<pcp><prc>"); }
}
