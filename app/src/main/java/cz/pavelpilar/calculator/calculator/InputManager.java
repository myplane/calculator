package cz.pavelpilar.calculator.calculator;

import android.graphics.Point;

import org.apache.commons.math3.fraction.Fraction;

import java.util.List;

import cz.pavelpilar.calculator.R;

public class InputManager {

    private static StringBuilder mInput;
    private static ButtonsFragment mButtonsFragment;
    private static DisplayFragment mDisplayFragment;

    public static void initialize(ButtonsFragment fragment, String s) {
        mInput = new StringBuilder(s);
        mButtonsFragment = fragment;
        mDisplayFragment = (DisplayFragment) mButtonsFragment.getParentFragment().getChildFragmentManager().findFragmentById(R.id.calculator_display_fragment);
        inputChanged();
    }

    public static void clear() {
        mInput = new StringBuilder("|");
        inputChanged();
    }

    public static String getCurrent() {
        return mInput.toString();
    }

    public static void setCurrent(String s) {
        mInput = new StringBuilder(s);
        inputChanged();
    }

    public static void backspace() {
        if (mInput.length() > 1) {
            String s = mInput.toString();
            int index = s.indexOf('|');
            if(index != 0) {
                if(s.charAt(index-1)!= '>'){
                    String s1 = s.substring(0, index - 1);
                    String s2 = s.substring(index, s.length());
                    mInput = new StringBuilder(s1 + s2);
                }else{
                    String tag = String.valueOf(s.charAt(index-4)) + String.valueOf(s.charAt(index-3)) + String.valueOf(s.charAt(index - 2));
                    switch (tag) {
                        case "pow":
                        {
                            String s1 = s.substring(0, index - 5) + "|";
                            for (int pow = 1; pow != 0; index++) {
                                if (s.charAt(index + 2) == 'p' && s.charAt(index + 3) == 'w' && s.charAt(index + 4) == 'n')
                                    --pow;
                                else if (s.charAt(index + 2) == 'p' && s.charAt(index + 3) == 'o' && s.charAt(index + 4) == 'w')
                                    pow++;
                            }
                            String s2 = s.substring(index + 5, s.length());
                            mInput = new StringBuilder(s1 + s2);
                            --mButtonsFragment.powers;
                            break;
                        }
                        case "abs":
                        {
                            String s1 = s.substring(0, index - 5) + "|";
                            for (int abs = 1; abs != 0; index++) {
                                if (s.charAt(index + 2) == 'a' && s.charAt(index + 3) == 'b' && s.charAt(index + 4) == 'n')
                                    --abs;
                                else if (s.charAt(index + 2) == 'a' && s.charAt(index + 3) == 'b' && s.charAt(index + 4) == 's')
                                    abs++;
                            }
                            String s2 = s.substring(index + 5, s.length());
                            mInput = new StringBuilder(s1 + s2);
                            break;
                        }
                        case "lgx":
                        {
                            String s2 = s.substring(index, s.length());
                            int strIndex = 0;
                            while (s2.charAt(strIndex + 2) != 'l' || s2.charAt(strIndex + 3) != 'g' || s2.charAt(strIndex + 4) != 'n')
                                strIndex++;
                            mInput = new StringBuilder(s.substring(0, index - 5) + "|" + s2.substring(strIndex + 6, s2.length()));
                            mButtonsFragment.changeMode(ButtonsFragment.Mode.DECIMAL);
                            break;
                        }
                        case "srt":
                        {
                            String s2 = s.substring(index, s.length());
                            int strIndex = 0;
                            while (s2.charAt(strIndex + 2) != 'r' || s2.charAt(strIndex + 3) != 't' || s2.charAt(strIndex + 4) != 'n') strIndex++;
                            mInput = new StringBuilder(s.substring(0, index - 5) + "|" + s2.substring(strIndex + 6, s2.length()));
                            break;
                        }
                        case "nrt":
                        {
                            String s2 = s.substring(index, s.length());
                            int strIndex = 0;
                            while (s2.charAt(strIndex + 2) != 'r' || s2.charAt(strIndex + 3) != 't' || s2.charAt(strIndex + 4) != 'n') strIndex++;
                            mInput = new StringBuilder(s.substring(0, index - 5) + "|" + s2.substring(strIndex + 6, s2.length()));
                            mButtonsFragment.changeMode(ButtonsFragment.Mode.DECIMAL);
                            break;
                        }
                        case "fra":
                        {
                            String s2 = s.substring(index, s.length());
                            int strIndex = 0;
                            while (s2.charAt(strIndex + 2) != 'f' || s2.charAt(strIndex + 3) != 'r' || s2.charAt(strIndex + 4) != 'n')
                                strIndex++;
                            mInput = new StringBuilder(s.substring(0, index - 5) + "|" + s2.substring(strIndex + 6, s2.length()));
                            --mButtonsFragment.fractions;
                            break;
                        }
                        case "rdr":
                        {
                            String s2 = s.substring(index, s.length());
                            int strIndex = 0;
                            while (s2.charAt(strIndex + 2) != 'r' || s2.charAt(strIndex + 3) != 'd' || s2.charAt(strIndex + 4) != 'n') strIndex++;
                            mInput = new StringBuilder(s.substring(0, index - 5) + "|" + s2.substring(strIndex + 6, s2.length()));
                            mButtonsFragment.changeMode(ButtonsFragment.Mode.DECIMAL);
                            break;
                        }
                        case "pcm":
                        case "pcp":
                        {
                            String s2 = s.substring(index, s.length());
                            int strIndex = 0;
                            while (s2.charAt(strIndex + 2) != 'p' || s2.charAt(strIndex + 3) != 'r' || s2.charAt(strIndex + 4) != 'c') strIndex++;
                            mInput = new StringBuilder(s.substring(0, index - 5) + "|" + s2.substring(strIndex + 6, s2.length()));
                            mButtonsFragment.changeMode(ButtonsFragment.Mode.DECIMAL);
                            break;
                        }
                        case "ncr":
                        {
                            String s2 = s.substring(index, s.length());
                            int strIndex = 0;
                            while (s2.charAt(strIndex + 2) != 'n' || s2.charAt(strIndex + 3) != 'c' || s2.charAt(strIndex + 4) != 'n')
                                strIndex++;
                            mInput = new StringBuilder(s.substring(0, index - 5) + "|" + s2.substring(strIndex + 6, s2.length()));
                            mButtonsFragment.changeMode(ButtonsFragment.Mode.DECIMAL);
                            break;
                        }
                        case "pcr":
                        {
                            String s2 = s.substring(index, s.length());
                            int strIndex = 0;
                            while (s2.charAt(strIndex + 2) != 'n' || s2.charAt(strIndex + 3) != 'p' || s2.charAt(strIndex + 4) != 'n') strIndex++;
                            mInput = new StringBuilder(s.substring(0, index - 5) + "|" + s2.substring(strIndex + 6, s2.length()));
                            mButtonsFragment.changeMode(ButtonsFragment.Mode.DECIMAL);
                            break;
                        }
                        case "hex":
                        {
                            String s2 = s.substring(index, s.length());
                            int strIndex = 0;
                            while(s2.charAt(strIndex+2) != 'h' || s2.charAt(strIndex+3) != 'x' || s2.charAt(strIndex+4) != 'n') strIndex++;
                            mInput = new StringBuilder(s.substring(0, index - 5) + "|" + s2.substring(strIndex+6, s2.length()));
                            mButtonsFragment.changeMode(ButtonsFragment.Mode.DECIMAL);
                            break;
                        }
                        case "oct":
                        {
                            String s2 = s.substring(index, s.length());
                            int strIndex = 0;
                            while(s2.charAt(strIndex+2) != 'o' || s2.charAt(strIndex+3) != 'c' || s2.charAt(strIndex+4) != 'n') strIndex++;
                            mInput = new StringBuilder(s.substring(0, index - 5) + "|" + s2.substring(strIndex+6, s2.length()));
                            mButtonsFragment.changeMode(ButtonsFragment.Mode.DECIMAL);
                            break;
                        }
                        case "bin":
                        {
                            String s2 = s.substring(index, s.length());
                            int strIndex = 0;
                            while(s2.charAt(strIndex+2) != 'b' || s2.charAt(strIndex+3) != 'n' || s2.charAt(strIndex+4) != 'n') strIndex++;
                            mInput = new StringBuilder(s.substring(0, index - 5) + "|" + s2.substring(strIndex+6, s2.length()));
                            mButtonsFragment.changeMode(ButtonsFragment.Mode.DECIMAL);
                            break;
                        }
                        case "sin":
                        case "cos":
                        case "tan":
                        case "log":
                        case "lon":
                        case "ans":
                        case "mod":
                        case "asn":
                        case "acs":
                        case "atn":
                        case "snh":
                        case "csh":
                        case "tnh":
                        case "ash":
                        case "ach":
                        case "ath":
                        case "rdh":
                        case "exp":
                        case "mem":
                        case "rnd":
                        case "par":
                        case "prn":
                        {
                            String s1 = s.substring(0, index - 5) + "|";
                            String s2 = s.substring(index, s.length());         //Maybe index+1
                            s2 = s2.replace("|", "");                           //and delete replace()
                            mInput = new StringBuilder(s1 + s2);
                            break;
                        }
                        default:
                            navLeft();
                            return;
                    }
                }
            }
            inputChanged();
        }
    }

    public static void navLeft() {
        String s = mInput.toString();
        int index = s.indexOf('|');
        if(index > 0) {
            if(s.charAt(index-1) != '>') {
                s = s.replace("|", "");
                String s1 = s.substring(0, index - 1);
                String s2 = s.substring(index - 1, s.length());
                mInput = new StringBuilder(s1 + "|" + s2);
            }else{
                String tag = String.valueOf(s.charAt(index - 4)) + String.valueOf(s.charAt(index-3)) + String.valueOf(s.charAt(index - 2));
                switch(tag){
                    case "hxn": mButtonsFragment.changeMode(ButtonsFragment.Mode.HEXADECIMAL); break;
                    case "bnn": mButtonsFragment.changeMode(ButtonsFragment.Mode.BINARY); break;

                    case "fra": --mButtonsFragment.fractions; break;
                    case "frn": mButtonsFragment.fractions++; break;

                    case "pow": --mButtonsFragment.powers; break;
                    case "pwn": mButtonsFragment.powers++; break;

                    case "nrt":
                    case "lgn":
                    case "rdr":
                    case "prc":
                    case "npn":
                    case "ncn": mButtonsFragment.changeMode(ButtonsFragment.Mode.DECIMAL_LOCKED); break;

                    case "hex":
                    case "oct":
                    case "bin":
                    case "lgx":
                    case "rdn":
                    case "pcm":
                    case "pcp":
                    case "npr":
                    case "ncr":
                    case "rtx": mButtonsFragment.changeMode(ButtonsFragment.Mode.DECIMAL);
                }
                s = s.replace("|", "");
                String s1 = s.substring(0, index - 5);
                String s2 = s.substring(index - 5, s.length());
                s1 = s1 + "|";
                mInput = new StringBuilder(s1 + s2);
            }
            inputChanged();
        }
    }

    public static void navRight() {
        String s = mInput.toString();
        int index = s.indexOf("|");
        if(index != s.length() - 1) {
            if(s.charAt(index+1) != '<'){
                s = s.replace("|", "");
                String s1 = s.substring(0, index+1);
                String s2 = s.substring(index+1, s.length());
                s2 = "|" + s2;
                mInput = new StringBuilder(s1 + s2);
            }else{
                String tag = String.valueOf(s.charAt(index+2)) + String.valueOf(s.charAt(index+3)) + String.valueOf(s.charAt(index+4));
                switch(tag){
                    case "hex": mButtonsFragment.changeMode(ButtonsFragment.Mode.HEXADECIMAL); break;
                    case "bin": mButtonsFragment.changeMode(ButtonsFragment.Mode.BINARY); break;

                    case "fra": mButtonsFragment.fractions++; break;
                    case "frn": --mButtonsFragment.fractions; break;

                    case "pow": mButtonsFragment.powers++; break;
                    case "pwn": --mButtonsFragment.powers; break;


                    case "hxn":
                    case "ocn":
                    case "bnn":
                    case "lgn":
                    case "rdn":
                    case "prc":
                    case "npn":
                    case "ncn":
                    case "rtx": mButtonsFragment.changeMode(ButtonsFragment.Mode.DECIMAL); break;

                    case "nrt":
                    case "lgx":
                    case "rdr":
                    case "pcm":
                    case "pcp":
                    case "npr":
                    case "ncr": mButtonsFragment.changeMode(ButtonsFragment.Mode.DECIMAL_LOCKED);
                }
                s = s.replace("|", "");
                String s1 = s.substring(0, index+5);
                String s2 = s.substring(index+5, s.length());
                s1 = s1 + "|";
                mInput = new StringBuilder(s1 + s2);
            }
            inputChanged();
        }
    }

    public static void navHome() {
        mInput = new StringBuilder("|" + mInput.toString().replace("|", ""));
        inputChanged();
    }

    public static void navEnd() {
        mInput = new StringBuilder(mInput.toString().replace("|", "") + "|");
        inputChanged();
    }

    public static void add(String s) {
        String original = mInput.toString();
        int index = original.indexOf('|');
        if (index != original.length() - 1) {
            String s1 = original.substring(0, index);
            String s2 = original.substring(index, original.length());
            s1 = s1.replace("|", "");
            s1 = s1 + s;
            if (s.contains("|")) s2 = s2.replace("|", "");
            mInput = new StringBuilder(s1 + s2);
        } else {
            mInput.setLength(mInput.length() - 1);
            mInput.append(s);
            if (!s.contains("|")) mInput.append("|");
        }
        inputChanged();
    }

    public static void toFraction(String s) {
        if(!s.equals("")) {
            try {
                Fraction fr = new Fraction(Double.valueOf(s));

                String numerator = String.valueOf(fr.getNumerator());
                String denominator = String.valueOf(fr.getDenominator());

                if (!numerator.equals("0") || !denominator.equals("1")) {
                    mInput = new StringBuilder("<fra>" + numerator + "<frx>" + denominator + "<frn>|");
                    inputChanged();
                }
            }catch (IllegalStateException ignored){}
        }
    }

    private static void inputChanged() {
        mDisplayFragment.show(mInput.toString());
    }

    public static String toCalc() {
        String s = mInput.toString();

        s = s.replace("<par>", "(").replace("<prn>", ")")
             .replace("<pow>", "^(").replace("<pwn>", ")")
             .replace("<abs>", "abs(").replace("<abn>", ")")
             .replace("<log>", "log").replace("<lon>", "ln")
             .replace("<lgx>", "lgx(").replace("<lgn>", ")X")
             .replace("<hex>", "(hex(").replace("<hxn>", "))")
             .replace("<bin>", "(bin(").replace("<bnn>", "))")
             .replace("<sin>", "sin").replace("<asn>", "asin")
             .replace("<cos>", "cos").replace("<acs>", "acos")
             .replace("<tan>", "tan").replace("<atn>", "atan")
             .replace("<snh>", "snh").replace("<ash>", "asnh")
             .replace("<csh>", "csh").replace("<ach>", "acsh")
             .replace("<tnh>", "tnh").replace("<ath>", "atnh")
             .replace("<fra>", "((")
             .replace("<frx>", ")÷(")
             .replace("<frn>", "))")
             .replace("<pcp>", "↑")
             .replace("<pcm>", "↓")
             .replace("<prc>", "")
             .replace("<rdr>", "rndr(")
             .replace("<rdx>", ")X(")
             .replace("<rdn>", ")")
             .replace("<srt>", "sqrt(")
             .replace("<srn>", ")")
             .replace("<nrt>", "ntrt(")
             .replace("<rtx>", ")X(")
             .replace("<rtn>", ")")
             .replace("<ncr>", "nCr").replace("<npr>", "nPr")
             .replace("<ncx>", "X").replace("<npx>", "X")
             .replace("<ncn>", "").replace("<npn>", "")
             .replace("<exp>", "E")
             .replace("<rnd>", "rand")
             .replace("<ans>", "ans")
             .replace("|", "")
             .replace("<mod>", "%")
             .replace("π", "(pi)")
             .replace("<mem>", "M");

        return s;
    }
}
