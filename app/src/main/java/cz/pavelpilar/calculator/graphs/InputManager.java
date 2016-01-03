package cz.pavelpilar.calculator.graphs;

public class InputManager {

    private static String mInput;
    private static InputFragment mFragment;

    public static void initialize(InputFragment fragment, String input) {
        mFragment = fragment;
        mInput = input;
    }

    public static void clear() {
        mInput = "|";
        inputChanged();
    }

    public static void add(String s) {
        int index = mInput.indexOf('|');
        if (index != mInput.length() - 1) {
            String s1 = mInput.substring(0, index);
            String s2 = mInput.substring(index, mInput.length());
            s1 = s1.replace("|", "");
            s1 = s1 + s;
            if (s.contains("|")) s2 = s2.replace("|", "");
            mInput = s1 + s2;
        } else {
            if(!s.contains("|"))
                mInput = mInput.replace("|", "") + s + "|";
            else {
                mInput = mInput.replace("|", "") + s;
            }
        }
        inputChanged();
    }

    public static void backspace() {
        int index = mInput.indexOf("|");
        if(index != 0) {
            if (mInput.charAt(index - 1) != '>') {
                String s1 = mInput.substring(0, index - 1);
                String s2 = mInput.substring(index, mInput.length());
                mInput = s1 + s2;
            }
            inputChanged();
        }
    }

    public static void navLeft() {
        int index = mInput.indexOf('|');
        if(index > 0) {
            if (mInput.charAt(index - 1) != '>') {
                mInput = mInput.replace("|", "");
                String s1 = mInput.substring(0, index - 1);
                String s2 = mInput.substring(index - 1, mInput.length());
                mInput = s1 + "|" + s2;
            }
        }
        inputChanged();
    }

    public static void navRight() {
        int index = mInput.indexOf("|");
        if(index != mInput.length() - 1) {
            if (mInput.charAt(index + 1) != '<') {
                mInput = mInput.replace("|", "");
                String s1 = mInput.substring(0, index + 1);
                String s2 = mInput.substring(index + 1, mInput.length());
                s2 = "|" + s2;
                mInput = s1 + s2;
            }
            inputChanged();
        }
    }

    private static void inputChanged() {
        mFragment.show(mInput);
    }

}
