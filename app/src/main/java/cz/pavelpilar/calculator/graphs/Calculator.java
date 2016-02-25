package cz.pavelpilar.calculator.graphs;

import java.io.IOException;
import java.io.StringReader;
import java.util.Random;

import cz.pavelpilar.calculator.MainActivity;
import cz.pavelpilar.calculator.R;

public class Calculator {

    private static String[][] words;

    private static boolean errorSet;
    private static String error;
    private static StringReader mExpression;

    private static float number;
    private static String word;

    public static String calculate(String input) {
        if(words == null) words = new String[][] {{"e", "E", "X", "π"},
                                                  {"ln"},
                                                  {"log", "abs", "lgx", "sin", "cos", "tan", "snh", "csh", "tnh"},
                                                  {"sqrt", "cbrt", "asin", "acos", "atan", "asnh", "acsh", "atnh"}};
        errorSet = false;
        mExpression = new StringReader(input);
        float result = expr(true);
        if(errorSet) return error;
        else return Float.toString(result);
    }

    private enum token{
        PLUS,   MINUS,  MUL,    DIV,
        LP,     RP,     POW,    MOD,
        FACT,   WORD,   NUMBER, END
    }

    private static String putBack(char ch)
    {
        try {
            StringBuilder sb = new StringBuilder(Character.toString(ch));
            ch = (char)mExpression.read();
            while ((int)ch != 65535) {
                sb.append(ch);
                ch = (char)mExpression.read();
            }
            return sb.toString();
        }
        catch(IOException e)
        {
            return "";
        }
    }

    private static token current;
    private static token getToken() {
        try {
            char ch;
            int x = mExpression.read();
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
                        ch = (char) mExpression.read();
                    }
                    number = Float.parseFloat(sb.toString());
                    mExpression = new StringReader(putBack(ch));
                    return current = token.NUMBER;
                }

                default:
                    if (Character.isLetter(ch)) {
                        StringBuilder sc = new StringBuilder();
                        for (; Character.isLetter(ch); ) {
                            sc.append(ch);
                            ch = (char) mExpression.read();
                            if (checkWord(sc.toString(), sc.toString().length() - 1)) break;
                        }
                        word = sc.toString();
                        mExpression = new StringReader(putBack(ch));
                        return current = token.WORD;
                    }
                    return current = token.END;
            }

        } catch (IOException i) {
            return current = token.END;
        }
    }

    private static float expr(boolean get)
    {
        float left = term(get);

        for(;;) switch(current)
        {
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

    private static float term(boolean get)
    {
        float left = func(get);

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

    private static float func(boolean get)
    {
        float left = prim(get);

        for (; ; )
            switch (current) {
                case POW:
                    left = (float) Math.pow(left, prim(true));
                    break;
                case MOD:
                    left = (left % prim(true));
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

    private static float prim(boolean get)
    {
        if(get) getToken();
        switch(current)
        {
            case NUMBER:
            {
                float x = number;
                getToken();
                return x;
            }
            case WORD:
                    switch (word) {
                        case "π":
                            getToken();
                            return (float) Math.PI;
                        case "e":
                            getToken();
                            return (float) Math.E;
                        case "E":
                            return (float) Math.pow(10, prim(true));
                        case "log":
                            return (float) Math.log10(prim(true));
                        case "ln":
                            return (float) Math.log(prim(true));
                        case "lgx": {
                            float base = prim(true);
                            return (float) (Math.log10(prim(true)) / Math.log10(base));
                        }
                        case "sqrt":
                            return (float) Math.sqrt(prim(true));
                        case "cbrt":
                            return (float) Math.cbrt(prim(true));
                        case "abs":
                            return Math.abs(prim(true));
                        case "sin":
                            return (float) Math.sin(prim(true));
                        case "cos":
                            return (float) Math.cos(prim(true));
                        case "tan":
                            return (float) Math.tan(prim(true));
                        case "asin":
                            return (float) Math.asin(prim(true));
                        case "acos":
                            return (float) Math.acos(prim(true));
                        case "atan":
                            return (float) Math.atan(prim(true));
                        case "snh": {
                            float x = prim(true);
                            return (float) (Math.exp(x) - Math.exp(-x)) / 2;
                        }
                        case "csh": {
                            float x = prim(true);
                            return (float) (Math.exp(x) + Math.exp(-x)) / 2;
                        }
                        case "tnh": {
                            float x = prim(true);
                            return (float) ((Math.exp(x) - Math.exp(-x)) / (Math.exp(x) + Math.exp(-x)));
                        }
                        case "asnh": {
                            float z = prim(true);
                            return (float) Math.log(z + Math.sqrt(Math.pow(z, 2) + 1));
                        }
                        case "acsh": {
                            float z = prim(true);
                            return (float) Math.log(z + Math.sqrt(Math.pow(z, 2) - 1));
                        }
                        case "atnh": {
                            float z = prim(true);
                            return 0.5f * (float) (Math.log((1 + z) / (1 - z)));
                        }
                    }
            case MINUS:
                return -prim(true);
            case PLUS:
                return prim(true);
            case LP:
            {
                float x = expr(true);
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
        if(!errorSet) error = "ERR: " + MainActivity.mContext.getResources().getString(resID);
        errorSet = true;
    }

    private static float factorial(double number)
    {
        if(number < 34) {
            if ((number % 1) == 0) {
                float x = (number >= 0) ? 1 : -1;
                for (int i = 1; i <= Math.abs(number); i++) x *= i;
                return x;
            } else err(R.string.error_factorialNotInteger);
        } else err(R.string.error_overflow);
        return 0;
    }

    private static boolean checkWord(String word, int stringLength)
    {
        for(int i = 0; i < words[stringLength].length; i++) if(word.equals(words[stringLength][i])) return true;
        return false;
    }

}
