package cz.pavelpilar.calculator.calculator;

import java.io.IOException;
import java.io.StringReader;
import java.util.Random;

import cz.pavelpilar.calculator.MainActivity;
import cz.pavelpilar.calculator.R;

public class Calculator {

    private static String[][] words;
    private static boolean errorSet;

    private static double number;
    private static String word;
    private static String error;
    private static token current;

    private static boolean DEG;
    private static MainFragment mMainFragment;

    public static void initialize(MainFragment fragment) {
        words = new String[][] {{"e", "E", "M", "X"},
                {"pi", "ln"},
                {"hex", "oct", "bin", "log", "abs", "lgx", "sin", "cos", "tan", "snh", "csh", "tnh", "nCr", "nPr"},
                {"rand", "rndr", "sqrt", "ntrt", "asin", "acos", "atan", "asnh", "acsh", "atnh"}};
        errorSet = false;
        DEG = false;
        mMainFragment = fragment;
    }

    public static void setDegrees(boolean set) {
        DEG = set;
    }

    public static String calculate(String input)
    {
        cin = new StringReader(input);
        double result = expr(true);
        String resultString = String.valueOf(result);
        if(!errorSet && resultString.contains(".9999999999999")) return String.valueOf(Math.round(result));
        else if (!errorSet) return resultString;
        else {
            errorSet = false;
            return error;
        }
    }

    private enum token{
        PLUS,   MINUS,  MUL,    DIV,
        LP,     RP,     POW,    MOD,
        FACT,   PERUP,  PERDOWN,
        WORD,   NUMBER, END
    }

    private static StringReader cin;

    private static String putBack(char ch)
    {
        try {
            StringBuilder sb = new StringBuilder(Character.toString(ch));
            ch = (char)cin.read();
            while ((int)ch != 65535) {
                sb.append(ch);
                ch = (char)cin.read();
            }
            return sb.toString();
        }
        catch(IOException e)
        {
            return "";
        }
    }

    private static token getToken() {
        try {
            char ch;
            int x = cin.read();
            if (x != -1) ch = (char) x;
            else return current = token.END;
            switch (ch) {
                case '+':
                    return current = token.PLUS;
                case '-':
                    return current = token.MINUS;
                case '×':
                    return current = token.MUL;
                case '÷':
                    return current = token.DIV;
                case '(':
                    return current = token.LP;
                case ')':
                    return current = token.RP;
                case '^':
                    return current = token.POW;
                case '%':
                    return current = token.MOD;
                case '!':
                    return current = token.FACT;
                case '↑':
                    return current = token.PERUP;
                case '↓':
                    return current = token.PERDOWN;

                case '0':
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                case '8':
                case '9':
                case '.': {
                    StringBuilder sb = new StringBuilder();
                    for (; Character.isDigit(ch) || ch == '.'; ) {
                        sb.append(ch);
                        ch = (char) cin.read();
                    }
                    number = Double.parseDouble(sb.toString());
                    cin = new StringReader(putBack(ch));
                    return current = token.NUMBER;
                }

                default:
                    if (Character.isLetter(ch)) {
                        StringBuilder sc = new StringBuilder();
                        for (; Character.isLetter(ch); ) {
                            sc.append(ch);
                            ch = (char) cin.read();
                            if (checkWord(sc.toString(), sc.toString().length() - 1)) break;
                        }
                        word = sc.toString();
                        cin = new StringReader(putBack(ch));
                        return current = token.WORD;
                    }
                    return current = token.END;
            }

        } catch (IOException i) {
            return current = token.END;
        }
    }

    private static double expr(boolean get)
    {
        double left = term(get);

        for(;;) switch(current)
        {
            case PERUP:
                left *= 1+(prim(true)/100);
                break;
            case PERDOWN:
                left *= 1-(prim(true)/100);
                break;
            case PLUS:
                left += term(true);
                break;
            case MINUS:
                left -= term(true);
                break;
            default:
                return left;
        }
    }

    private static double term(boolean get)
    {
        double left = func(get);

        for(;;) switch(current) {
            case MUL:
                left *= func(true);
                break;
            case DIV:
            {
                double divisor = func(true);
                if (divisor != 0) left /= divisor;
                else err(R.string.error_divBy0);
                break;
            }
            default:
                return left;
        }
    }

    private static double func(boolean get)
    {
        double left = prim(get);

        for (;;)
            switch (current) {
                case POW:
                    left = Math.pow(left, prim(true));
                    break;
                case MOD:
                    double x = prim(true);
                    double result = (left % x);
                    if(Math.abs(result - x) <= 0.000001) left = 0;  //Deals with floating point errors
                    else left = result;
                    break;
                case FACT:
                {
                    left = factorial(left);
                    getToken();
                    break;
                }
                case LP:
                    left *= prim(false);
                    break;
                case WORD:
                    left *= prim(false);
                default:
                    return left;
            }
    }

    private static double prim(boolean get)
    {
        if(get) getToken();
        switch(current)
        {
            case NUMBER:
            {
                double x = number;
                getToken();
                return x;
            }
            case WORD:
                try{
                    switch (word) {
                        case "hex":          //convert from hex to dec
                        {
                            getToken();
                            StringBuilder sc = new StringBuilder();
                            for (char ch = (char) cin.read(); Character.isLetter(ch) || Character.isDigit(ch) || ch == '.'; ) {
                                sc.append(ch);
                                ch = (char) cin.read();
                            }
                            getToken();
                            return hex2dec(sc.toString());
                        }
                        case "oct":    //convert from oct to dec
                        {
                            getToken();
                            StringBuilder sc = new StringBuilder();
                            for (char ch = (char) cin.read(); Character.isDigit(ch) || ch == '.'; ) {
                                sc.append(ch);
                                ch = (char) cin.read();
                            }
                            getToken();
                            if(sc.toString().isEmpty()) return 0;
                            return oct2dec(sc.toString());
                        }

                        case "bin": {
                            getToken();
                            StringBuilder sc = new StringBuilder();
                            for (char ch = (char) cin.read(); Character.isDigit(ch) || ch == '.'; ) {
                                sc.append(ch);
                                ch = (char) cin.read();
                            }
                            getToken();
                            if(sc.toString().isEmpty()) return 0;
                            return bin2dec(sc.toString());
                        }
                        case "e":
                            getToken();
                            return Math.E;
                        case "E":
                            return Math.pow(10, prim(true));
                        case "M":
                            getToken();
                            return mMainFragment.mMemory;
                        case "pi":
                            getToken();
                            return Math.PI;
                        case "log":
                            return Math.log10(prim(true));
                        case "ln":
                            return Math.log(prim(true));
                        case "lgx": {
                            double base = prim(true);
                            return (Math.log10(prim(true)) / Math.log10(base));
                        }
                        case "rand":
                            getToken();
                            return Math.random() * (Math.random() * 100);
                        case "rndr": {
                            double min = prim(true);
                            double max = prim(true);
                            if(min > max) err(R.string.error_minBiggerThanMax);
                            return min + (max - min) * new Random().nextDouble();
                        }
                        case "sqrt":
                            return Math.sqrt(prim(true));
                        case "ntrt": {
                            double n = prim(true);
                            return Math.pow(prim(true), 1 / n);
                        }
                        case "abs":
                            return Math.abs(prim(true));
                        case "sin": {
                            double x = prim(true);
                            if (DEG) return Math.sin(x * (Math.PI / 180));
                            else {
                                if (x % Math.PI == 0) return Math.round(Math.sin(x));
                                else return Math.sin(x);
                            }
                        }
                        case "cos": {
                            double x = prim(true);
                            if (DEG) return Math.cos(x * (Math.PI / 180));
                            else {
                                if(x % (Math.PI*1.5f) == 0) return 0;
                                else return Math.cos(x);
                            }
                        }
                        case "tan": {
                            double x = prim(true);
                            if (DEG) return Math.tan(x * (Math.PI / 180));
                            else {
                                if(x % Math.PI == 0 && x != 0) return 0;
                                else return Math.tan(x);
                            }
                        }
                        case "asin":
                            if (DEG) return Math.asin(prim(true)) * (180 / Math.PI);
                            else return Math.asin(prim(true));
                        case "acos":
                            if (DEG) return Math.acos(prim(true)) * (180 / Math.PI);
                            else return Math.acos(prim(true));
                        case "atan":
                            if (DEG) return Math.atan(prim(true)) * (180 / Math.PI);
                            else return Math.atan(prim(true));
                        case "snh": {
                            double x = prim(true);
                            return (Math.exp(x) - Math.exp(-x)) / 2;
                        }
                        case "csh": {
                            double x = prim(true);
                            return (Math.exp(x) + Math.exp(-x)) / 2;
                        }
                        case "tnh": {
                            double x = prim(true);
                            return (Math.exp(x) - Math.exp(-x)) / (Math.exp(x) + Math.exp(-x));
                        }
                        case "asnh": {
                            double z = prim(true);
                            return Math.log(z + Math.sqrt(Math.pow(z, 2) + 1));
                        }
                        case "acsh": {
                            double z = prim(true);
                            return Math.log(z + Math.sqrt(Math.pow(z, 2) - 1));
                        }
                        case "atnh": {
                            double z = prim(true);
                            return 0.5 * (Math.log((1 + z) / (1 - z)));
                        }
                        case "nCr":
                        {
                            double n = prim(true);
                            double r = prim(true);

                            if ((n % 1) != 0 || (r % 1) != 0)
                                err(R.string.error_nOrRNotIntegers);
                            if (n < r) err(R.string.error_nSmallerThanR);
                            if (r <= 0) err(R.string.error_rSmallerThan0);

                            return Math.round(factorial(n) / (factorial(r) * factorial(n - r)));
                        }
                        case "nPr":
                        {
                            double n = prim(true);
                            double r = prim(true);

                            if ((n % 1) != 0 || (r % 1) != 0)
                                err(R.string.error_nOrRNotIntegers);
                            if (n < r) err(R.string.error_nSmallerThanR);
                            if (r <= 0) err(R.string.error_rSmallerThan0);

                            return Math.round(factorial(n) / factorial(r));
                        }
                    }
                }catch(IOException e){
                    return 0;
                }
            case MINUS:
                return -prim(true);
            case PLUS:
                return prim(true);
            case LP:
            {
                double x = expr(true);
                if(current != token.RP){
                    return x;
                }
                getToken();
                return x;
            }
            case END:
                return 0;
            default:
                return 0;
        }
    }

    private static void err(int resID)
    {
        if(!errorSet) error = MainActivity.mContext.getResources().getString(resID);
        errorSet = true;
    }

    private static double factorial(double number)
    {
        if(number < 171) {
            if ((number % 1) == 0) {
                double x = (number >= 0) ? 1 : -1;
                for (int i = 1; i <= Math.abs(number); i++) x *= i;
                return x;
            } else err(R.string.error_factorialNotInteger);
        } else err(R.string.error_overflow);
        return 0;
    }

    public static double bin2dec(String bin)
    {
        try {
            if (bin.contains(".")) {
                String s1 = bin.substring(0, bin.indexOf("."));
                String s2 = bin.substring(bin.indexOf(".") + 1, bin.length());

                double x = Long.parseLong(s1, 2);
                for (int i = 0; i < s2.length(); i++) {
                    x += ((s2.charAt(i) - 48) * Math.pow(2, -i - 1));
                }
                return x;
            } else return Long.parseLong(bin, 2);
        }
        catch(NumberFormatException e){
            err(R.string.error_BinaryTooLong);
            return 0;
        }
    }

    public static String dec2bin(String dec) {
        if(dec.length() == 0 || dec.equals("") || !Character.isDigit(dec.charAt(0))) return "0";

        try{
            return Integer.toBinaryString(Integer.parseInt(dec))  + "<sub><small><small><small>2";
        } catch(NumberFormatException e) {
            return "Conversion not possible";
        }
    }

    public static String dec2oct(String dec) {
        if(dec.length() == 0 || dec.equals("") || !Character.isDigit(dec.charAt(0))) return "0";

        try{
            return Integer.toOctalString(Integer.parseInt(dec))  + "<sub><small><small><small>8";
        } catch(NumberFormatException e) {
            return "Conversion not possible";
        }
    }

    public static String dec2hex(String dec) {
        if(dec.length() == 0 || dec.equals("") || !Character.isDigit(dec.charAt(0))) return "0";

        try{
            return Integer.toHexString(Integer.parseInt(dec)).toUpperCase()  + "<sub><small><small><small>16";
        } catch(NumberFormatException e) {
            return "Conversion not possible";
        }
    }

    public static double hex2dec(String hex)
    {
        String digits = "0123456789ABCDEF";
        if(hex.contains(".")) {
            String s1 = hex.substring(0, hex.indexOf("."));
            String s2 = hex.substring(hex.indexOf(".") + 1, hex.length());
            double val = 0;
            for (int i = 0; i < s1.length(); i++) {
                char c = s1.charAt(i);
                int d = digits.indexOf(c);
                val = 16 * val + d;
            }
            for (int i = 0; i < s2.length(); i++) val += digits.indexOf(s2.charAt(i)) * Math.pow(16, -i-1);
            return val;
        }else{
            double val = 0;
            for (int i = 0; i < hex.length(); i++) {
                char c = hex.charAt(i);
                int d = digits.indexOf(c);
                val = 16 * val + d;
            } return val;
        }
    }

    public static double oct2dec(String oct)
    {
        try {
            if (oct.contains(".")) {
                String s2 = oct.substring(oct.indexOf(".") + 1, oct.length());
                double x = Long.parseLong(oct.substring(0, oct.indexOf(".")), 8);
                for (int i = 0; i < s2.length(); i++)
                    x += ((int) s2.charAt(i) - 48) * Math.pow(8, -i - 1);
                return x;
            } else return Long.parseLong(oct, 8);
        }
        catch (NumberFormatException e) {
            err(R.string.error_OctTooLong);
            return 0;
        }
    }

    private static boolean checkWord(String word, int stringLength)
    {
        for(int i = 0; i < words[stringLength].length; i++) if(word.equals(words[stringLength][i])) return true;
        return false;
    }

}
