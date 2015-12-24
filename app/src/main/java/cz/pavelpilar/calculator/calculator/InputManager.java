package cz.pavelpilar.calculator.calculator;

import android.util.Log;

public class InputManager {

    private static StringBuilder mInput;

    public static void initialize() {
        mInput = new StringBuilder("|");
    }

    public static void clear() {
        mInput = new StringBuilder("|");
    }

    public static void add(String s) {
        Log.d("INPUT", "add() called");
        if(!s.contains("<")){

        } else {

        }
    }
}
