package cz.pavelpilar.calculator.calculator;

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
                    case "ocn": mButtonsFragment.changeMode(ButtonsFragment.Mode.OCTAL); break;
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
                    case "oct": mButtonsFragment.changeMode(ButtonsFragment.Mode.OCTAL); break;
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

    private static void inputChanged() {
        mDisplayFragment.show(mInput.toString());
    }

    public static String[] getFraction(int index) {
        String s = mInput.toString();
        String numerator, denominator;
        int frxPos;
        if (!s.substring(index + 5, s.substring(index).indexOf("<frx>") + index).contains("<fra>")) {
            numerator = s.substring(index + 5, s.substring(index).indexOf("<frx>") + index);
            frxPos = s.substring(index).indexOf("<frx>") + index + 5;
        } else {
            int tempPos = index + 5;
            int hi = 1;
            while (hi != 0) {
                if (s.charAt(tempPos) == '<') {
                    String tag = s.substring(tempPos+1, tempPos+4);
                    if (tag.equals("fra")) hi++;
                    else if (tag.equals("frx")) --hi;
                    tempPos = tempPos + 5;
                } else tempPos++;
            }
            numerator = s.substring(index + 5, tempPos - 5);
            frxPos = tempPos;
        }
        if (!s.substring(frxPos, s.substring(frxPos).indexOf("<frn>") + frxPos).contains("<fra>")) {
                denominator = s.substring(frxPos, s.substring(frxPos).indexOf("<frn>") + frxPos);
        } else {
            int tempPos = frxPos;
            int hi = 1;
            while (hi != 0) {
                if(s.charAt(tempPos) == '<') {
                    String tag = s.substring(tempPos+1, tempPos+4);
                    if (tag.equals("fra")) ++hi;
                    else if (tag.equals("frn"))--hi;
                    tempPos = tempPos + 5;
                }else tempPos++;
            }
            denominator = s.substring(frxPos, tempPos - 5);
        }
        return new String[] {numerator, denominator};
    }

    public static String toCalc() {
        String s = mInput.toString();

        s = s.replace("<par>", "(").replace("<prn>", ")")
             .replace("<pow>", "^(").replace("<pwn>", ")")
             .replace("<abs>", "abs(").replace("<abn>", ")")
             .replace("<log>", "log").replace("<lon>", "ln");

        s = s.replace("<lgx>", "lgx(");
        s = s.replace("<lgn>", ")X");

        s = s.replace("<hex>", "(hex(");
        s = s.replace("<hxn>", "))");

        s = s.replace("<oct>", "(oct(");
        s = s.replace("<ocn>", "))");

        s = s.replace("<bin>", "(bin(");
        s = s.replace("<bnn>", "))");

        s = s.replace("<sin>", "sin");  s = s.replace("<asn>", "asin");
        s = s.replace("<cos>", "cos");  s = s.replace("<acs>", "acos");
        s = s.replace("<tan>", "tan");  s = s.replace("<atn>", "atan");

        s = s.replace("<snh>", "snh"); s = s.replace("<ash>", "asnh");
        s = s.replace("<csh>", "csh"); s = s.replace("<ach>", "acsh");
        s = s.replace("<tnh>", "tnh"); s = s.replace("<ath>", "atnh");

        s = s.replace("<fra>", "((");
        s = s.replace("<frx>", ")÷(");
        s = s.replace("<frn>", "))");

        s = s.replace("<pcp>", "↑");
        s = s.replace("<pcm>", "↓");
        s = s.replace("<prc>", "");

        s = s.replace("<rdr>", "rndr(");
        s = s.replace("<rdx>", ")X(");
        s = s.replace("<rdn>", ")");

        s = s.replace("<srt>", "sqrt(");
        s = s.replace("<srn>", ")");

        s = s.replace("<nrt>", "ntrt(");
        s = s.replace("<rtx>", ")X(");
        s = s.replace("<rtn>", ")");

        s = s.replace("<ncr>", "nCr");  s = s.replace("<npr>", "nPr");
        s = s.replace("<ncx>", "X");    s = s.replace("<npx>", "X");
        s = s.replace("<ncn>", "");     s = s.replace("<npn>", "");

        s = s.replace("<exp>", "E");
        s = s.replace("<rnd>", "rand");
        s = s.replace("<ans>", "ans");
        s = s.replace("|", "");
        s = s.replace("<mod>", "%");
        s = s.replace("π", "(pi)");
        s = s.replace("<mem>", "M");

        return s;
    }
}
