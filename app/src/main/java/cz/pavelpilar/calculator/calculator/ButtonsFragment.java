package cz.pavelpilar.calculator.calculator;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cz.pavelpilar.calculator.ConstantsFragment;
import cz.pavelpilar.calculator.MainActivity;
import cz.pavelpilar.calculator.R;

public class ButtonsFragment extends Fragment {

    //@Bind(R.id.buttonDot) Button mButtonDot;
    //@Bind(R.id.button0) Button mButton0;
    //@Bind(R.id.button1) Button mButton1;
    @Bind(R.id.button2) Button mButton2;
    @Bind(R.id.button3) Button mButton3;
    @Bind(R.id.button4) Button mButton4;
    @Bind(R.id.button5) Button mButton5;
    @Bind(R.id.button6) Button mButton6;
    @Bind(R.id.button7) Button mButton7;
    @Bind(R.id.button8) Button mButton8;
    @Bind(R.id.button9) Button mButton9;
    @Bind(R.id.buttonPlus) Button mButtonPlus;
    @Bind(R.id.buttonMinus) Button mButtonMinus;
    @Bind(R.id.buttonMultiply) Button mButtonMultiply;
    @Bind(R.id.buttonDivide) Button mButtonDivide;
    @Bind(R.id.buttonStartParentheses) Button mButtonStartParentheses;
    @Bind(R.id.buttonEndParentheses) Button mButtonEndParentheses;
    //@Bind(R.id.buttonEquals) Button mButtonEquals;

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
    @Bind(R.id.buttonHex) Button mButtonHex;
    @Bind(R.id.buttonOct) Button mButtonOct;
    @Bind(R.id.buttonBin) Button mButtonBin;
    @Bind(R.id.buttonE) Button mButtonE;

    @Bind(R.id.buttonShift) Button mButtonShift;
    @Bind(R.id.buttonShift2) Button mButtonShift2;
    //@Bind(R.id.buttonNavLeft) Button mButtonNavLeft;
    //@Bind(R.id.buttonNavRight) Button mButtonNavRight;
    //@Bind(R.id.buttonClear) Button mButtonClear;
    //@Bind(R.id.buttonBackspace) ImageButton mButtonBackspace;

    private boolean shift;
    private boolean shift2;
    private boolean hyp;

    public int fractions;
    public int powers;

    private MainFragment mMainFragment;

    int mode;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        mMainFragment = (MainFragment) getParentFragment();

        InputManager.initialize(this, MainActivity.mPreferences.getString("calculator_input", "|"));
        fractions = MainActivity.mPreferences.getInt("calculator_fractions", 0);
        powers = MainActivity.mPreferences.getInt("calculator_powers", 0);
        shift = MainActivity.mPreferences.getBoolean("calculator_shift", false);
        shift2 = MainActivity.mPreferences.getBoolean("calculator_shift2", false);
        hyp = MainActivity.mPreferences.getBoolean("calculator_hyp", false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        MainActivity.mPreferences.edit()
                                 .putString("calculator_input", InputManager.getCurrent())
                                 .putInt("calculator_fractions", fractions)
                                 .putInt("calculator_powers", powers)
                                 .putBoolean("calculator_shift", shift)
                                 .putBoolean("calculator_shift2", shift2)
                                 .putBoolean("calculator_hyp", hyp)
                                 .putInt("calculator_mode", mode)
                                 .apply();
        InputManager.clear();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        View v = inflater.inflate(R.layout.calculator_buttons, container, false);

        ButterKnife.bind(this, v);
        setLayoutText(v);
        changeMode(MainActivity.mPreferences.getInt("calculator_mode", Mode.DECIMAL));

        return v;
    }

    public int getStatus() {
        if(hyp) {
            if(shift2) return 13;
            else if (shift) return 12;
            else return 11;
        } else {
            if(shift2) return 3;
            else if (shift) return 2;
            else return 1;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
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
        tv = (TextView) v.findViewById(R.id.textLogx);
        tv.setText(Html.fromHtml(" log<small><small>x</small></small>y"));

        mButtonPower.setText(Html.fromHtml("x<small><sup>2</sup></small>"));
    }

    @OnClick(R.id.buttonEquals) void buttonEquals() { mMainFragment.setResult(Calculator.calculate(InputManager.toCalc()), InputManager.getCurrent().replace("|", "")); }
    @OnClick(R.id.buttonDot) void buttonDot() { InputManager.add("."); }
    @OnClick(R.id.button0) void button0() { InputManager.add("0"); }
    @OnClick(R.id.button1) void button1() { InputManager.add("1"); }
    @OnClick(R.id.button2) void button2() { InputManager.add("2"); }
    @OnClick(R.id.button3) void button3() { InputManager.add("3"); }
    @OnClick(R.id.button4) void button4() {
        if(mode == Mode.HEXADECIMAL) InputManager.add("6");
        else InputManager.add("4");
    }
    @OnClick(R.id.button5) void button5() {
        if(mode == Mode.HEXADECIMAL) InputManager.add("7");
        else InputManager.add("5");
    }
    @OnClick(R.id.button6) void button6() {
        if(mode == Mode.HEXADECIMAL) InputManager.add("8");
        else InputManager.add("6");
    }
    @OnClick(R.id.button7) void button7() {
        if(mode == Mode.HEXADECIMAL) InputManager.add("B");
        else InputManager.add("7");
    }
    @OnClick(R.id.button8) void button8() {
        if(mode == Mode.HEXADECIMAL) InputManager.add("C");
        else InputManager.add("8");
    }
    @OnClick(R.id.button9) void button9() {
        if(mode == Mode.HEXADECIMAL) InputManager.add("D");
        else InputManager.add("9");
    }
    @OnClick(R.id.buttonPlus) void buttonPlus() {
        if(mode == Mode.HEXADECIMAL) InputManager.add("4");
        else InputManager.add("+");
    }
    @OnClick(R.id.buttonMinus) void buttonMinus() {
        if(mode == Mode.HEXADECIMAL) InputManager.add("5");
        else InputManager.add("-");
    }
    @OnClick(R.id.buttonMultiply) void buttonMultiply() {
        if(mode == Mode.HEXADECIMAL) InputManager.add("9");
        else InputManager.add("×");
    }
    @OnClick(R.id.buttonDivide) void buttonDivide() {
        if(mode == Mode.HEXADECIMAL) InputManager.add("A");
        else InputManager.add("÷");
    }
    @OnClick(R.id.buttonStartParentheses) void buttonStartParentheses() {
        if(mode == Mode.HEXADECIMAL) InputManager.add("E");
        else InputManager.add("<par>");
    }
    @OnClick(R.id.buttonEndParentheses) void buttonEndParentheses() {
        if(mode == Mode.HEXADECIMAL) InputManager.add("F");
        else InputManager.add("<prn>");
    }

    @OnClick(R.id.buttonFraction) void buttonFraction() {
        if(fractions < 2){
            InputManager.add("<fra>|<frx><frn>");
            fractions++;
        }
    }
    @OnClick(R.id.buttonFactorial) void buttonFactorial() {
        if(shift) InputManager.add("<abs>|<abn>");
        else InputManager.add("!");
        disableModifiers();
    }
    @OnClick(R.id.buttonModulo) void buttonModulo() {
        if(shift) InputManager.add("π");
        else InputManager.add("<mod>");
        disableModifiers();
    }
    @OnClick(R.id.buttonRoot) void buttonRoot() {
        if(shift) {
            InputManager.add("<nrt>|<rtx><rtn>");
            changeMode(Mode.DECIMAL_LOCKED);
        }
        else InputManager.add("<srt>|<rtn>");
        disableModifiers();
    }
    @OnClick(R.id.buttonPower) void buttonPower() {
        if(powers < 2) {
            if (shift) {
                InputManager.add("<pow>|<pwn>");
                powers++;
            }
            else InputManager.add("<pow>2<pwn>");
            disableModifiers();
        }
    }
    @OnClick(R.id.buttonPercent) void buttonPercent() {
        if(shift) InputManager.add("<pcm>|<prc>");
        else InputManager.add("<pcp>|<prc>");
        changeMode(Mode.DECIMAL_LOCKED);
        disableModifiers();
    }

    @OnClick(R.id.buttonHyp) void buttonHyp() {
        hyp = !hyp;
        mMainFragment.statusChanged();
    }
    @OnClick(R.id.buttonSin) void buttonSin() {
        if(hyp){
            if(shift) InputManager.add("<ash>");
            else InputManager.add("<snh>");
        } else {
            if(shift) InputManager.add("<asn>");
            else InputManager.add("<sin>");
        }
        disableModifiers();
    }
    @OnClick(R.id.buttonCos) void buttonCos() {
        if(hyp){
            if(shift) InputManager.add("<ach>");
            else InputManager.add("<csh>");
        } else {
            if(shift) InputManager.add("<acs>");
            else InputManager.add("<cos>");
        }
        disableModifiers();
    }
    @OnClick(R.id.buttonTan) void buttonTan() {
        if(hyp){
            if(shift) InputManager.add("<ath>");
            else InputManager.add("<tnh>");
        } else {
            if(shift) InputManager.add("<atn>");
            else InputManager.add("<tan>");
        }
        disableModifiers();
    }
    @OnClick(R.id.buttonMemory) void buttonMemory() {
        if(shift2) mMainFragment.clearMemory();
        else if(shift) mMainFragment.addToMemory();
        else InputManager.add("<mem>");
        disableModifiers();
    }
    @OnClick(R.id.buttonRandom) void buttonRandom() {
        if(shift) {
            InputManager.add("<rdr>|<rdx><rdn>");
            changeMode(Mode.DECIMAL_LOCKED);
        }
        else InputManager.add("<rnd>");
        disableModifiers();
    }

    @OnClick(R.id.buttonConstants) void buttonConstants() {
        DialogFragment fragment = new ConstantsFragment();
        fragment.show(getActivity().getSupportFragmentManager().beginTransaction(), "constants");
    }

    @OnClick(R.id.buttonLog) void buttonLog() {
        if(shift) {
            InputManager.add("<lon>");
        } else if(shift2) {
            InputManager.add("<lgx>|<lgn>");
            changeMode(Mode.DECIMAL_LOCKED);
        }
        else InputManager.add("<log>");
        disableModifiers();
    }
    @OnClick(R.id.buttonHex) void buttonHex() {
        InputManager.add("<hex>|<hxn>");
        changeMode(Mode.HEXADECIMAL);
        disableModifiers();
    }
    @OnClick(R.id.buttonOct) void buttonOct() {
        InputManager.add("<oct>|<ocn>");
        changeMode(Mode.OCTAL);
        disableModifiers();
    }
    @OnClick(R.id.buttonBin) void buttonBin() {
        InputManager.add("<bin>|<bnn>");
        changeMode(Mode.BINARY);
        disableModifiers();
    }
    @OnClick(R.id.buttonE) void buttonE() {
        if(shift2) {
            InputManager.add("<npr>|<npx><npn>");
            changeMode(Mode.DECIMAL_LOCKED);
        }
        else if(shift) {
            InputManager.add("<ncr>|<ncx><ncn>");
            changeMode(Mode.DECIMAL_LOCKED);
        }
        else InputManager.add("<exp>");
        disableModifiers();
    }

    @OnClick(R.id.buttonShift) void buttonShift() {
        if(shift2) shift2 = false;
        shift = !shift;
        mMainFragment.statusChanged();
    }
    @OnClick(R.id.buttonShift2) void buttonShift2() {
        if(shift) shift = false;
        shift2 = !shift2;
        mMainFragment.statusChanged();
    }
    @OnClick(R.id.buttonNavLeft) void ButtonNavLeft() { InputManager.navLeft(); }
    @OnClick(R.id.buttonNavRight) void buttonNavRight() { InputManager.navRight(); }
    @OnClick(R.id.buttonClear) void buttonClear() {
        InputManager.clear();
        changeMode(Mode.DECIMAL);
        mMainFragment.clearResult();
        fractions = 0;
        powers = 0;
        disableModifiers();
    }
    @OnClick(R.id.buttonBackspace) void buttonBackspace() { InputManager.backspace(); }

    private void disableModifiers() {
        shift = false;
        shift2 = false;
        hyp = false;
        mMainFragment.statusChanged();
    }

    public void changeMode(int mode) {
        switch (mode){
            case Mode.DECIMAL: {
                setNumbers(Mode.DECIMAL);
                lockFunctions(false);
                this.mode = mode;
                break;
            }
            case Mode.BINARY: {
                setNumbers(Mode.BINARY);
                lockFunctions(true);
                this.mode = mode;
                break;
            }
            case Mode.OCTAL: {
                setNumbers(Mode.OCTAL);
                lockFunctions(true);
                this.mode = mode;
                break;
            }
            case Mode.HEXADECIMAL: {
                setNumbers(Mode.HEXADECIMAL);
                lockFunctions(true);
                this.mode = mode;
                break;
            }
            case Mode.DECIMAL_LOCKED: {
                lockFunctions(true);
                this.mode = mode;
                break;
            }
        }
    }

    private void setNumbers(int mode) {
        switch(mode){
            case Mode.DECIMAL:
            {
                mButtonPlus.setText("+");
                mButtonMinus.setText("-");
                mButton4.setText("4");
                mButton5.setText("5");
                mButton6.setText("6");
                mButtonMultiply.setText("×");
                mButtonDivide.setText("÷");
                mButton7.setText("7");
                mButton8.setText("8");
                mButton9.setText("9");
                mButtonStartParentheses.setText("(");
                mButtonEndParentheses.setText(")");

                mButton2.setClickable(true);
                mButton3.setClickable(true);
                mButton4.setClickable(true);
                mButton4.setClickable(true);
                mButton5.setClickable(true);
                mButton6.setClickable(true);
                mButton7.setClickable(true);
                mButton8.setClickable(true);
                mButton9.setClickable(true);
                mButtonPlus.setClickable(true);
                mButtonMinus.setClickable(true);
                mButtonMultiply.setClickable(true);
                mButtonDivide.setClickable(true);
                mButtonStartParentheses.setClickable(true);
                mButtonEndParentheses.setClickable(true);
                break;
            }
            case Mode.BINARY:
            {
                mButton2.setClickable(false);
                mButton3.setClickable(false);
                mButton4.setClickable(false);
                mButton4.setClickable(false);
                mButton5.setClickable(false);
                mButton6.setClickable(false);
                mButton7.setClickable(false);
                mButton8.setClickable(false);
                mButton9.setClickable(false);
                mButtonPlus.setClickable(false);
                mButtonMinus.setClickable(false);
                mButtonMultiply.setClickable(false);
                mButtonDivide.setClickable(false);
                mButtonStartParentheses.setClickable(false);
                mButtonEndParentheses.setClickable(false);
                break;
            }
            case Mode.OCTAL:
            {
                mButton8.setClickable(false);
                mButton9.setClickable(false);
                mButtonPlus.setClickable(false);
                mButtonMinus.setClickable(false);
                mButtonMultiply.setClickable(false);
                mButtonDivide.setClickable(false);
                mButtonStartParentheses.setClickable(false);
                mButtonEndParentheses.setClickable(false);
                break;
            }
            case Mode.HEXADECIMAL:
            {
                mButtonPlus.setText("4");
                mButtonMinus.setText("5");
                mButton4.setText("6");
                mButton5.setText("7");
                mButton6.setText("8");
                mButtonMultiply.setText("9");
                mButtonDivide.setText("A");
                mButton7.setText("B");
                mButton8.setText("C");
                mButton9.setText("D");
                mButtonStartParentheses.setText("E");
                mButtonEndParentheses.setText("F");
            }
        }
    }

    private void lockFunctions(boolean lock) {
        mButtonFraction.setClickable(!lock);
        mButtonFactorial.setClickable(!lock);
        mButtonModulo.setClickable(!lock);
        mButtonRoot.setClickable(!lock);
        mButtonPower.setClickable(!lock);
        mButtonPercent.setClickable(!lock);
        mButtonHyp.setClickable(!lock);
        mButtonSin.setClickable(!lock);
        mButtonCos.setClickable(!lock);
        mButtonTan.setClickable(!lock);
        mButtonMemory.setClickable(!lock);
        mButtonRandom.setClickable(!lock);
        mButtonLog.setClickable(!lock);
        mButtonHex.setClickable(!lock);
        mButtonOct.setClickable(!lock);
        mButtonBin.setClickable(!lock);
        mButtonE.setClickable(!lock);
        mButtonShift.setClickable(!lock);
        mButtonShift2.setClickable(!lock);
    }

    public class Mode {
        public static final int DECIMAL = 0;
        public static final int BINARY = 1;
        public static final int OCTAL = 2;
        public static final int HEXADECIMAL = 3;
        public static final int DECIMAL_LOCKED = 4;
    }
}
