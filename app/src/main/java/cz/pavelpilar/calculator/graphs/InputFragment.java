package cz.pavelpilar.calculator.graphs;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.OnClick;
import cz.pavelpilar.calculator.R;

public class InputFragment extends Fragment {

    private MainFragment mMainFragment;
    private boolean shift, hyp;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        mMainFragment = (MainFragment) getParentFragment();
        InputManager.initialize(this, new String[] {"|", "|", "|"}, 0);
        shift = hyp = false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        View v = inflater.inflate(R.layout.graphs_input, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    public void show(String[] strings, int currentDisplay) { mMainFragment.show(strings, currentDisplay); }

    @OnClick(R.id.buttonUp) public void buttonUp() {
        InputManager.up();
    }
    @OnClick(R.id.buttonDown) public void buttonDown() {
        InputManager.down();
    }
    @OnClick(R.id.buttonShow) public void buttonShow() {
        mMainFragment.showGraph(InputManager.toCalc());
    }

    @OnClick(R.id.buttonHyp) public void buttonHyp() { hyp = !hyp; }
    @OnClick(R.id.buttonShift) public void buttonShift() { shift = !shift; }
    @OnClick(R.id.buttonNavLeft) public void buttonNavLeft() { InputManager.navLeft(); }
    @OnClick(R.id.buttonNavRight) public void buttonNavRight() { InputManager.navRight(); }
    @OnClick(R.id.buttonClear) public void buttonClear() { InputManager.clear(); }
    @OnClick(R.id.buttonBackspace) public void buttonBackspace() { InputManager.backspace(); }

    @OnClick(R.id.buttonDot) public void buttonDot() { InputManager.add("."); }
    @OnClick(R.id.button0) public void button0() { InputManager.add("0"); }
    @OnClick(R.id.button1) public void button1() { InputManager.add("1"); }
    @OnClick(R.id.button2) public void button2() { InputManager.add("2"); }
    @OnClick(R.id.button3) public void button3() { InputManager.add("3"); }
    @OnClick(R.id.button4) public void button4() { InputManager.add("4"); }
    @OnClick(R.id.button5) public void button5() { InputManager.add("5"); }
    @OnClick(R.id.button6) public void button6() { InputManager.add("6"); }
    @OnClick(R.id.button7) public void button7() { InputManager.add("7"); }
    @OnClick(R.id.button8) public void button8() { InputManager.add("8"); }
    @OnClick(R.id.button9) public void button9() { InputManager.add("9"); }
    @OnClick(R.id.buttonAdd) public void buttonAdd() { InputManager.add("+"); }
    @OnClick(R.id.buttonSubtract) public void buttonSubtract() { InputManager.add("-"); }
    @OnClick(R.id.buttonMultiply) public void buttonMultiply() { InputManager.add("×"); }
    @OnClick(R.id.buttonDivide) public void buttonDivide() { InputManager.add("÷"); }
    @OnClick(R.id.buttonStartParentheses) public void buttonStartParentheses() { InputManager.add("("); }
    @OnClick(R.id.buttonEndParentheses) public void buttonEndParentheses() { InputManager.add(")"); }
    @OnClick(R.id.buttonSin) public void buttonSin() {
        if (hyp) {
            if(shift) InputManager.add("<ash>|<end>");
            else InputManager.add("<snh>|<end>");
        } else {
            if (shift) InputManager.add("<asn>|<end>");
            else InputManager.add("<sin>|<end>");
        }
        disableModifiers();
    }
    @OnClick(R.id.buttonCos) public void buttonCos() {
        if (hyp) {
            if (shift) InputManager.add("<ach>|<end>");
            else InputManager.add("<csh>|<end>");
        } else {
            if (shift) InputManager.add("<acs>|<end>");
            else InputManager.add("<cos>|<end>");
        }
        disableModifiers();
    }
    @OnClick(R.id.buttonTan) public void buttonTan() {
        if(hyp) {
            if (shift) InputManager.add("<ath>|<end>");
            else InputManager.add("<tnh>|<end>");
        } else {
            if (shift) InputManager.add("<atn>|<end>");
            else InputManager.add("<tan>|<end>");
        }
        disableModifiers();
    }
    @OnClick(R.id.buttonX) public void buttonX() { InputManager.add("<xxx>"); }
    @OnClick(R.id.buttonLog) public void buttonLog() {
        if(shift) InputManager.add("<lon>|<end>");
        else InputManager.add("<log>|<end>");
        disableModifiers();
    }
    @OnClick(R.id.buttonPower) public void buttonPower() { InputManager.add("<pow>|<pwn>"); }
    @OnClick(R.id.buttonRoot) public void buttonRoot() { InputManager.add("<srt>|<end>"); }

    private void disableModifiers() {
        shift = hyp = false;
    }

}
