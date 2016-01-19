package cz.pavelpilar.calculator.graphs;

import android.util.Log;

import java.util.Set;

public class InputManager {

    private static String[] mInput;
    private static InputFragment mFragment;
    private static int mCurrent;
    private static boolean[] mPowers;

    public static void initialize(InputFragment fragment, String[] input, int current) {
        mFragment = fragment;
        mInput = input;
        mCurrent = current;
        mPowers = new boolean[] {false, false, false , false};
        inputChanged();
    }

    public static void clear() {
        mInput = new String[] {"|", "|", "|", "|"};
        mPowers = new boolean[] {false, false, false , false};
        inputChanged();
    }

    public static void setDisplay(int displayNumber) {
        mCurrent = displayNumber;
        inputChanged();
    }

    public static void up(){
        if(mCurrent == 0) mCurrent = 3;
        else --mCurrent;
        inputChanged();
    }

    public static void down() {
        if(mCurrent == 3) mCurrent = 0;
        else mCurrent++;
        inputChanged();
    }

    public static void add(String s) {
        int index = mInput[mCurrent].indexOf('|');
        if (index != mInput[mCurrent].length() - 1) {
            String s1 = mInput[mCurrent].substring(0, index);
            String s2 = mInput[mCurrent].substring(index, mInput[mCurrent].length());
            s1 = s1.replace("|", "");
            s1 = s1 + s;
            if (s.contains("|")) s2 = s2.replace("|", "");
            mInput[mCurrent] = s1 + s2;
        } else {
            if(!s.contains("|"))
                mInput[mCurrent] = mInput[mCurrent].replace("|", "") + s + "|";
            else {
                mInput[mCurrent] = mInput[mCurrent].replace("|", "") + s;
            }
        }
        inputChanged();
    }

    public static void addPow() {
        if(!mPowers[mCurrent]) {
            mPowers[mCurrent] = true;

            int index = mInput[mCurrent].indexOf('|');
            if (index != mInput[mCurrent].length() - 1) {
                String s1 = mInput[mCurrent].substring(0, index);
                String s2 = mInput[mCurrent].substring(index+1, mInput[mCurrent].length());
                s1 = s1 + "<pow>|<pwn>";
                mInput[mCurrent] = s1 + s2;
            } else {
                mInput[mCurrent] = mInput[mCurrent].replace("|", "") + "<pow>|<pwn>";
            }
            inputChanged();
        }
    }

    public static void backspace() {
        int index = mInput[mCurrent].indexOf("|");
        if(index != 0) {
            if (mInput[mCurrent].charAt(index - 1) != '>') {
                String s1 = mInput[mCurrent].substring(0, index - 1);
                String s2 = mInput[mCurrent].substring(index, mInput[mCurrent].length());
                mInput[mCurrent] = s1 + s2;
            } else {
                switch (String.valueOf(mInput[mCurrent].charAt(index - 4)) + String.valueOf(mInput[mCurrent].charAt(index - 3)) + String.valueOf(mInput[mCurrent].charAt(index - 2))) {
                    case "sin":
                    case "cos":
                    case "tan":
                    case "asn":
                    case "acs":
                    case "atn":
                    case "snh":
                    case "csh":
                    case "tnh":
                    case "ash":
                    case "ach":
                    case "ath":
                    case "log":
                    case "lon":
                    case "srt":
                    case "crt":
                        for (int number = 1; number != 0; index++) {
                            switch (String.valueOf(mInput[mCurrent].charAt(index + 2)) + mInput[mCurrent].charAt(index + 3) + mInput[mCurrent].charAt(index + 4)) {
                                case "sin":
                                case "cos":
                                case "tan":
                                case "asn":
                                case "acs":
                                case "atn":
                                case "snh":
                                case "csh":
                                case "tnh":
                                case "ash":
                                case "ach":
                                case "ath":
                                case "log":
                                case "lon":
                                case "srt":
                                case "crt": number++; break;

                                case "end": --number;
                            }
                        }
                        mInput[mCurrent] = mInput[mCurrent].substring(0, mInput[mCurrent].indexOf("|") - 5) +  "|" + mInput[mCurrent].substring(index + 5, mInput[mCurrent].length());
                        break;
                    case "pow":
                        mInput[mCurrent] = mInput[mCurrent].substring(0, index - 5) + "|" + mInput[mCurrent].substring(mInput[mCurrent].substring(index).indexOf("<pwn>") + index + 5);
                        mPowers[mCurrent] = false;
                        break;
                    case "abs":
                        mInput[mCurrent] = mInput[mCurrent].substring(0, index - 5) + "|" + mInput[mCurrent].substring(mInput[mCurrent].substring(index).indexOf("<abn>") + index + 5);
                        break;
                    case "xxx":
                        mInput[mCurrent] = mInput[mCurrent].substring(0, index - 5) + mInput[mCurrent].substring(index);
                        break;
                    case "fra":
                        mInput[mCurrent] = mInput[mCurrent].substring(0, index - 5) + "|" + mInput[mCurrent].substring(mInput[mCurrent].substring(index).indexOf("<frn>") + index + 5);
                    default:
                        navLeft();
                }
            }
            inputChanged();
        }
    }

    public static void navLeft() {
        int index = mInput[mCurrent].indexOf('|');
        if(index > 0) {
            if (mInput[mCurrent].charAt(index - 1) != '>') {
                mInput[mCurrent] = mInput[mCurrent].replace("|", "");
                String s1 = mInput[mCurrent].substring(0, index - 1);
                String s2 = mInput[mCurrent].substring(index - 1, mInput[mCurrent].length());
                mInput[mCurrent] = s1 + "|" + s2;
            } else {
                if(mInput[mCurrent].substring(index - 4, index - 1).equals("pow")) mPowers[mCurrent] = false;
                if(mInput[mCurrent].substring(index - 4, index - 1).equals("pwn")) mPowers[mCurrent] = true;

                mInput[mCurrent] = mInput[mCurrent].replace("|", "");
                String s1 = mInput[mCurrent].substring(0, index - 5);
                String s2 = mInput[mCurrent].substring(index - 5, mInput[mCurrent].length());
                s1 = s1 + "|";
                mInput[mCurrent] = s1 + s2;
            }
        }
        inputChanged();
    }

    public static void navRight() {
        int index = mInput[mCurrent].indexOf("|");
        if(index != mInput[mCurrent].length() - 1) {
            if (mInput[mCurrent].charAt(index + 1) != '<') {
                mInput[mCurrent] = mInput[mCurrent].replace("|", "");
                String s1 = mInput[mCurrent].substring(0, index + 1);
                String s2 = mInput[mCurrent].substring(index + 1, mInput[mCurrent].length());
                s2 = "|" + s2;
                mInput[mCurrent] = s1 + s2;
            } else {
                if(mInput[mCurrent].substring(index + 2, index + 5).equals("pow")) mPowers[mCurrent] = true;
                if(mInput[mCurrent].substring(index + 2, index + 5).equals("pwn")) mPowers[mCurrent] = false;

                mInput[mCurrent] = mInput[mCurrent].replace("|", "");
                String s1 = mInput[mCurrent].substring(0, index + 5);
                String s2 = mInput[mCurrent].substring(index + 5, mInput[mCurrent].length());
                s1 = s1 + "|";
                mInput[mCurrent] = s1 + s2;
            }
            inputChanged();
        }
    }

    private static void inputChanged() {
        mFragment.show(mInput, mCurrent);
    }

    public static String[] getInput() {
        String[] input = mInput.clone();
        for(int i = 0; i < input.length; i++)
            input[i] = input[i].replace("|", "") + "|";
        return input;
    }

    public static String[] toCalc() {
        String[] strings = new String[] {mInput[0], mInput[1], mInput[2], mInput[3]};
        for (int i = 0; i < strings.length; i++) {
            strings[i] = strings[i].replace("<sin>", "sin(").replace("<cos>", "cos(").replace("<tan>","tan(").replace("<end>", ")")
                                   .replace("<asn>", "asin(").replace("<acs>", "acos(").replace("<atn>", "atan(")
                                   .replace("<snh>", "snh(").replace("<csh>", "csh(").replace("<tnh>", "tnh(")
                                   .replace("<ash>", "asnh(").replace("<ach>", "acsh(").replace("<ath>", "atnh(")
                                   .replace("<pow>", "^(").replace("<pwn>", ")")
                                   .replace("<log>", "log(").replace("<lon>", "ln(")
                                   .replace("<srt>", "sqrt(").replace("<crt>", "cbrt(")
                                   .replace("<abs>", "abs(").replace("<abn>", ")")
                                   .replace("<fra>", "((").replace("<frx>", ")÷(").replace("<frn>", "))")
                                   .replace("|", "");
        }
        return strings;
    }

}
