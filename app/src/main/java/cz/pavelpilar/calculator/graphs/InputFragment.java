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

    private Display mDisplay;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        InputManager.initialize(this, "|");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        View v = inflater.inflate(R.layout.graphs_input, container, false);
        mDisplay = (Display) v.findViewById(R.id.graphs_display);
        ButterKnife.bind(this, v);
        return v;
    }

    public void show(String s) {
        mDisplay.show(s);
    }

    @OnClick(R.id.buttonShow) public void buttonShow() {

    }

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

}
