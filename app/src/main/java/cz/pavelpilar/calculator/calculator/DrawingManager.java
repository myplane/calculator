package cz.pavelpilar.calculator.calculator;


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;

import java.util.Vector;

import cz.pavelpilar.calculator.MainActivity;

public class DrawingManager {

    private static Canvas mCanvas;
    private static DisplayMetrics mDisplayMetrics;
    private static Paint mPaint;

    private static float mPositionX;
    private static float mPositionY;

    private static float multiplier = 1;
    private static float mTextHeight;

    private static Vector<Float[]> mFractions;
    private static Vector<Float[]> mRootsAndParentheses;
    private static Vector<Integer> mParentheses;
    private static int mParenthesesCount;

    public static void initialize() {
        if(mFractions == null) {    //Only initialize once
            mDisplayMetrics = MainActivity.mContext.getResources().getDisplayMetrics();

            mPaint = new Paint();
            mPaint.setAntiAlias(true);
            mPaint.setColor(Color.WHITE);
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setStrokeWidth(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, mDisplayMetrics));
            mTextHeight = 18;

            mFractions = new Vector<>();
        }
    }

    public static void setTextSize(String size) {
        switch (size) {
            case "32":
                mPaint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 32, mDisplayMetrics));
                multiplier = 1.3333333333f;
                mTextHeight = 18*multiplier;
                break;
            case "24":
                mPaint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, mDisplayMetrics));
                multiplier = 1;
                mTextHeight = 18*multiplier;
        }
    }

    public static void drawTo(Canvas canvas) {
        mCanvas = canvas;

        mPositionX = 5;
        mPositionY = (canvas.getHeight()/2)/mDisplayMetrics.density + 9;

        mRootsAndParentheses = new Vector<>();
        mParentheses = new Vector<>();
        mParentheses.add(0);
        mParenthesesCount = -1;
    }

    public static Point drawChar(char c) {
        Point point = new Point((int) mPositionX, (int) mPositionY);

        mCanvas.drawText(String.valueOf(c),
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, mPositionX, mDisplayMetrics),
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, mPositionY, mDisplayMetrics),
                mPaint);
        switch (c){
            case '.':
            case ',':
            case '!':
            case ']':
            case '|': mPositionX = mPositionX + 5*multiplier; break;
            case ')': mPositionX = mPositionX + 7*multiplier; break;
            case '(':
            case '-': mPositionX = mPositionX + 9*multiplier; break;
            case '1': mPositionX = mPositionX + 11*multiplier; break;
            case 'e':
            case '+':
            case '×':
            case '0':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
            case '9':
            case '÷': mPositionX = mPositionX + 13*multiplier; break;
            case 'E':
            case 'F':
            case 'π': mPositionX = mPositionX + 14*multiplier; break;
            case '=':
            case 'A':
            case 'B':
            case 'C':
            case 'D':
            case 'P': mPositionX = mPositionX + 15*multiplier; break;
            case '%': mPositionX = mPositionX + 17*multiplier; break;
            case 'M': mPositionX = mPositionX + 19*multiplier; break;
        }
        if(mRootsAndParentheses.size() > 0)
            mRootsAndParentheses.set(mRootsAndParentheses.size() - 1,
                                     new Float[] {mRootsAndParentheses.lastElement()[0],
                                                  Math.min(mRootsAndParentheses.lastElement()[1], mPositionY - mTextHeight),
                                                  Math.max(mRootsAndParentheses.lastElement()[2], mPositionY)} );
        return point;
    }

    public static void drawText(String s) {
        mCanvas.drawText(s,
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, mPositionX, mDisplayMetrics),
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, mPositionY, mDisplayMetrics),
                mPaint);
        switch (s){
            case "-1": mPositionX = mPositionX + 20*multiplier; break;
            case "log": mPositionX = mPositionX + 32*multiplier; break;
            case "ln": mPositionX = mPositionX + 20*multiplier; break;
            case "sin": mPositionX = mPositionX + 32*multiplier; break;
            case "cos": mPositionX = mPositionX + 38*multiplier; break;
            case "tan": mPositionX = mPositionX + 35*multiplier; break;
            case "sinh": mPositionX = mPositionX + 45*multiplier; break;
            case "cosh": mPositionX = mPositionX + 52*multiplier; break;
            case "tanh": mPositionX = mPositionX + 47*multiplier; break;
            case "MOD": mPositionX = mPositionX + 54*multiplier; break;
            case "RAND": mPositionX = mPositionX + 64*multiplier; break;
            case "RAND[": mPositionX = mPositionX + 70*multiplier; break;
        }
        if(mRootsAndParentheses.size() > 0)
            mRootsAndParentheses.set(mRootsAndParentheses.size() - 1,
                                     new Float[] {mRootsAndParentheses.lastElement()[0],
                                                  Math.min(mRootsAndParentheses.lastElement()[1], mPositionY - mTextHeight),
                                                  Math.max(mRootsAndParentheses.lastElement()[2], mPositionY)} );
    }

    public static void functionStart() {
        mParentheses.add(0);
    }

    public static void functionEnd() {
        while(mParentheses.lastElement() > 0){
            if (mRootsAndParentheses.size() > 1)
                mRootsAndParentheses.set(mRootsAndParentheses.size() - 2,
                                         new Float[]{mRootsAndParentheses.elementAt(mRootsAndParentheses.size() - 2)[0],
                                                     Math.min(mRootsAndParentheses.elementAt(mRootsAndParentheses.size() - 2)[1],
                                                              mRootsAndParentheses.elementAt(mRootsAndParentheses.size() - 1)[1]),
                                                     Math.max(mRootsAndParentheses.elementAt(mRootsAndParentheses.size() - 2)[2],
                                                              mRootsAndParentheses.elementAt(mRootsAndParentheses.size() - 1)[2])});
            mParentheses.set(mParentheses.size() - 1, mParentheses.lastElement() - 1);
            if(mRootsAndParentheses.size() > 0) mRootsAndParentheses.removeElementAt(mRootsAndParentheses.size() - 1);
        }
        mParentheses.removeElementAt(mParentheses.size() - 1);

    }

    private static void drawLine(float startX, float startY, float endX, float endY) {
        mCanvas.drawLine(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, startX, mDisplayMetrics),
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, startY, mDisplayMetrics),
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, endX, mDisplayMetrics),
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, endY, mDisplayMetrics), mPaint);
    }

    private static void drawRoot(float startX, float minY, float maxY) {
        drawLine(startX, mPositionY - 9, startX + 4.5f, mPositionY - 9);
        drawLine(startX + 4, mPositionY - 9, startX + 7, maxY);
        drawLine(startX + 7, maxY, startX + 10.5f, minY - 3.5f);
        drawLine(startX + 10, minY - 3, mPositionX + 2, minY - 3);
    }

    private static void drawRect(float startX, float minY, float maxY) {
        RectF rect = new RectF(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, startX - 1, mDisplayMetrics),
                               TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, minY - 1, mDisplayMetrics),
                               TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, startX + 3, mDisplayMetrics),
                               TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, maxY + 1, mDisplayMetrics));
        mPaint.setColor(Color.parseColor("#263238"));   //Match background color
        mCanvas.drawRect(rect, mPaint);
        mPaint.setColor(Color.WHITE);
    }

    private static void drawParentheses(float startX, float minY, float maxY, boolean end) {
        switch(mParenthesesCount) {
            case 0: break;
            case 1: mPaint.setColor(Color.parseColor("#FF9800")); break;
            case 2: mPaint.setColor(Color.parseColor("#03A9F4")); break;
            case 3: mPaint.setColor(Color.parseColor("#00BFA5")); break;
            default: mPaint.setColor(Color.parseColor("#9E9E9E"));
        }
        RectF rect = new RectF(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, startX, mDisplayMetrics),
                               TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, minY, mDisplayMetrics),
                               TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, startX + 8, mDisplayMetrics),
                               TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, maxY, mDisplayMetrics));
        mPaint.setStyle(Paint.Style.STROKE);
        if(end) mCanvas.drawArc(rect, 270, 180, false, mPaint);
        else mCanvas.drawArc(rect, 90, 180, false, mPaint);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.WHITE);
    }

    public static void superscriptStart() {
        multiplier = multiplier - 0.25f;
        mPositionY = mPositionY - 13*multiplier;
        mTextHeight = mTextHeight - 4.5f*multiplier;
        mPaint.setTextSize(mPaint.getTextSize() - TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 6*multiplier, mDisplayMetrics));
    }

    public static void superscriptEnd() {
        mPaint.setTextSize(mPaint.getTextSize() + TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 6*multiplier, mDisplayMetrics));
        mTextHeight = mTextHeight + 4.5f*multiplier;
        mPositionY = mPositionY + 13*multiplier;
        multiplier = multiplier + 0.25f;
    }

    public static void subscriptStart() {
        multiplier = multiplier - 0.5f;
        mPositionY = mPositionY + 15*multiplier;
        mPaint.setTextSize(mPaint.getTextSize() - TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 12, mDisplayMetrics));
    }

    public static void subscriptEnd() {
        mPositionY = mPositionY - 13*multiplier;
        mPaint.setTextSize(mPaint.getTextSize() + TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 12, mDisplayMetrics));
        multiplier = multiplier + 0.5f;
    }

    public static void parenthesesStart() {
        mParenthesesCount++;
        mRootsAndParentheses.add(new Float[]{mPositionX + 4, mPositionY - mTextHeight, mPositionY});
        mParentheses.set(mParentheses.size() - 1, mParentheses.lastElement() + 1);
        RectF rect = new RectF(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, mPositionX + 4, mDisplayMetrics),
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, mPositionY - mTextHeight - 2 * multiplier, mDisplayMetrics),
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, mPositionX + 8, mDisplayMetrics),
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, mPositionY + 4 * multiplier, mDisplayMetrics));
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        mCanvas.drawArc(rect, 90, 180, false, mPaint);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.WHITE);
        mPositionX = mPositionX + 8*multiplier;
    }

    public static void parenthesesEnd() {
        if(mRootsAndParentheses.size() > 0 && mParentheses.lastElement() > 0) {
            drawRect(mRootsAndParentheses.lastElement()[0], mRootsAndParentheses.lastElement()[1] - 2 * multiplier, mRootsAndParentheses.lastElement()[2] + 4 * multiplier);
            drawParentheses(mRootsAndParentheses.lastElement()[0],
                            mRootsAndParentheses.lastElement()[1] - 2 * multiplier,
                            mRootsAndParentheses.lastElement()[2] + 4 * multiplier, false);
            drawParentheses(mPositionX - 4, mRootsAndParentheses.lastElement()[1] - 2 * multiplier, mRootsAndParentheses.lastElement()[2] + 4 * multiplier, true);
            if (mRootsAndParentheses.size() > 1)
                mRootsAndParentheses.set(mRootsAndParentheses.size() - 2,
                        new Float[]{mRootsAndParentheses.elementAt(mRootsAndParentheses.size() - 2)[0],
                                    Math.min(mRootsAndParentheses.elementAt(mRootsAndParentheses.size() - 2)[1],
                                             mRootsAndParentheses.elementAt(mRootsAndParentheses.size() - 1)[1]),
                                    Math.max(mRootsAndParentheses.elementAt(mRootsAndParentheses.size() - 2)[2],
                                             mRootsAndParentheses.elementAt(mRootsAndParentheses.size() - 1)[2])});
            mRootsAndParentheses.removeElementAt(mRootsAndParentheses.size() - 1);
            mParentheses.set(mParentheses.size() - 1, mParentheses.lastElement() - 1);
            --mParenthesesCount;
        }else{
            RectF rect = new RectF(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, mPositionX, mDisplayMetrics),
                    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, mPositionY - mTextHeight - 2 * multiplier, mDisplayMetrics),
                    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, mPositionX + 4, mDisplayMetrics),
                    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, mPositionY + 4 * multiplier, mDisplayMetrics));
            mPaint.setColor(Color.RED);
            mPaint.setStyle(Paint.Style.STROKE);
            mCanvas.drawArc(rect, 270, 180, false, mPaint);
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setColor(Color.WHITE);
        }
        mPositionX = mPositionX + 8*multiplier;
    }

    public static void fractionStart(String[] fraction) {
        mPositionX = mPositionX + 12*multiplier;
        mFractions.add(mockDraw(fraction, mPositionX));
        if(mFractions.lastElement()[1] < mFractions.lastElement()[3]) mPositionX = mPositionX + ((mFractions.lastElement()[3] - mFractions.lastElement()[1]) / 2);
        mPositionY = mPositionY - 14*multiplier - mFractions.lastElement()[2];
    }

    public static void fractionLine() {
        mPositionX = mFractions.lastElement()[0];
        if(mFractions.lastElement()[1] > mFractions.lastElement()[3]) mPositionX = mPositionX + ((mFractions.lastElement()[1] - mFractions.lastElement()[3]) / 2);
        mPositionY = mPositionY + 28*multiplier + mFractions.lastElement()[2] + mFractions.lastElement()[4];
    }

    public static void fractionEnd() {
        mPositionY = mPositionY - 14*multiplier - mFractions.lastElement()[4];
        drawLine(mFractions.lastElement()[0] - 6*multiplier,
                 mPositionY - 8.5f*multiplier,
                 Math.max(mFractions.lastElement()[1], mFractions.lastElement()[3]) + 6*multiplier,
                 mPositionY - 8.5f*multiplier);
        mPositionX = Math.max(mFractions.lastElement()[1], mFractions.lastElement()[3]) + 12*multiplier;
        mFractions.removeElementAt(mFractions.size() - 1);
    }

    public static void rootStart() {
        mRootsAndParentheses.add(new Float[]{mPositionX, mPositionY - mTextHeight, mPositionY});
        mPositionX = mPositionX + 12;
    }

    public static void rootEnd() {
        drawRoot(mRootsAndParentheses.lastElement()[0], mRootsAndParentheses.lastElement()[1], mRootsAndParentheses.lastElement()[2]);
        if(mRootsAndParentheses.size() > 1)
            mRootsAndParentheses.set(mRootsAndParentheses.size() - 2,
                                     new Float[] {mRootsAndParentheses.elementAt(mRootsAndParentheses.size() - 2)[0],
                                                  Math.min(mRootsAndParentheses.elementAt(mRootsAndParentheses.size() - 2)[1],
                                                           mRootsAndParentheses.elementAt(mRootsAndParentheses.size() - 1)[1] - 4*multiplier),
                                                  Math.max(mRootsAndParentheses.elementAt(mRootsAndParentheses.size() - 2)[2],
                                                           mRootsAndParentheses.elementAt(mRootsAndParentheses.size() - 1)[2]  + 2*multiplier)});
        mRootsAndParentheses.removeElementAt(mRootsAndParentheses.size() - 1);
        mPositionX = mPositionX + 4*multiplier;
    }

    private static @NonNull Float[] mockDraw(String[] strings, float startPos) {    //Used to get heights and lengths, so they can be offset properly
        Float limits[] = {mPositionX, null, null, null, null};    //Fraction start, numerator length, numerator max Y, denominator length, denominator min Y
        for(int i = 0; i < 2; i++){
            int pos = 0;
            float positionX = startPos, positionY = mPositionY, minY = positionY - mTextHeight, maxY = positionY;
            while(pos != strings[i].length()){
                if(strings[i].charAt(pos) != '<'){
                    switch (strings[i].charAt(pos)){
                        case '.':
                        case ',':
                        case '!':
                        case ']':
                        case '|': positionX = positionX + 5*multiplier; break;
                        case ')': positionX = positionX + 7*multiplier; break;
                        case '(':
                        case '-': positionX = positionX + 9*multiplier; break;
                        case '1': positionX = positionX + 11*multiplier; break;
                        case 'e':
                        case '+':
                        case '×':
                        case '0':
                        case '2':
                        case '3':
                        case '4':
                        case '5':
                        case '6':
                        case '7':
                        case '8':
                        case '9':
                        case '÷': positionX = positionX + 13*multiplier; break;
                        case 'E':
                        case 'F':
                        case 'π': positionX = positionX + 14*multiplier; break;
                        case 'A':
                        case 'B':
                        case 'C':
                        case 'D':
                        case 'P': positionX = positionX + 15*multiplier; break;
                        case '%': positionX = positionX + 17*multiplier; break;
                        case 'M': positionX = positionX + 19*multiplier; break;
                    }
                    minY = Math.min(minY, positionY - mTextHeight);
                    maxY = Math.max(maxY, positionY);
                    pos++;
                } else {
                    String tag = strings[i].substring(pos + 1, pos + 4);
                    switch (tag){
                        case "exp": positionX = positionX + 14*multiplier; break;
                        case "log": positionX = positionX + 32*multiplier; break;
                        case "lon": positionX = positionX + 20*multiplier; break;
                        case "sin": positionX = positionX + 32*multiplier; break;
                        case "cos": positionX = positionX + 38*multiplier; break;
                        case "tan": positionX = positionX + 35*multiplier; break;
                        case "snh": positionX = positionX + 45*multiplier; break;
                        case "csh": positionX = positionX + 52*multiplier; break;
                        case "tnh": positionX = positionX + 47*multiplier; break;
                        case "mod": positionX = positionX + 54*multiplier; break;
                        case "rnd": positionX = positionX + 64*multiplier; break;
                        case "rdr": positionX = positionX + 70*multiplier; break;
                        case "rdx":
                        case "rdn": positionX = positionX + 5*multiplier; break;
                        case "pcp": positionX = positionX + 12*multiplier; break;
                        case "pcm": positionX = positionX + 9*multiplier; break;
                        case "prc": positionX = positionX + 13*multiplier; break;
                        case "mem": positionX = positionX + 19*multiplier; break;
                        case "par":
                        case "prn": positionX = positionX + 8*multiplier; break;
                        case "nrt":
                        case "srt":
                            positionX = positionX + 12*multiplier;
                            maxY = Math.max(maxY, positionY);
                            break;
                        case "rtn":
                            minY = minY - 4*multiplier;
                            positionX = positionX + 4;
                            break;
                        case "lgx":
                            positionX = positionX + 32*multiplier;
                            multiplier = multiplier - 0.5f;
                            maxY = Math.max(maxY, positionY + 3);
                            break;
                        case "lgn": multiplier = multiplier + 0.5f; break;
                        case "hex":
                        case "oct":
                        case "bin": positionX = positionX + 9*multiplier; break;
                        case "hxn":
                            positionX = positionX + 19*multiplier;
                            maxY = Math.max(maxY, positionY + 5);
                            break;
                        case "ocn":
                        case "bnn":
                            positionX = positionX + 13*multiplier;
                            maxY = Math.max(maxY, positionY + 5);
                            break;
                        case "asn":
                            positionX = positionX + 47*multiplier;
                            positionY = positionY - 13*multiplier;
                            minY = Math.min(minY, positionY - (mTextHeight - 4.5f*multiplier));
                            positionY = positionY + 13*multiplier;
                            break;
                        case "acs":
                            positionX = positionX + 53*multiplier;
                            positionY = positionY - 13*multiplier;
                            minY = Math.min(minY, positionY - (mTextHeight - 4.5f*multiplier));
                            positionY = positionY + 13*multiplier;
                            break;
                        case "atn":
                            positionX = positionX + 50*multiplier;
                            positionY = positionY - 13*multiplier;
                            minY = Math.min(minY, positionY - (mTextHeight - 4.5f*multiplier));
                            positionY = positionY + 13*multiplier;
                            break;
                        case "ash":
                            positionX = positionX + 60*multiplier;
                            positionY = positionY - 13*multiplier;
                            minY = Math.min(minY, positionY - (mTextHeight - 4.5f*multiplier));
                            positionY = positionY + 13*multiplier;
                            break;
                        case "ach":
                            positionX = positionX + 67*multiplier;
                            positionY = positionY - 13*multiplier;
                            minY = Math.min(minY, positionY - (mTextHeight - 4.5f*multiplier));
                            positionY = positionY + 13*multiplier;
                            break;
                        case "ath":
                            positionX = positionX + 62*multiplier;
                            positionY = positionY - 13*multiplier;
                            minY = Math.min(minY, positionY - (mTextHeight - 4.5f*multiplier));
                            positionY = positionY + 13*multiplier;
                            break;
                        case "pow":
                            multiplier = multiplier - 0.25f;
                            positionY = positionY - 13*multiplier;
                            mTextHeight = mTextHeight - 4.5f*multiplier;
                            minY = Math.min(minY, positionY - mTextHeight);
                            break;
                        case "pwn":
                            mTextHeight = mTextHeight + 4.5f*multiplier;
                            positionY = positionY + 13*multiplier;
                            multiplier = multiplier + 0.25f;
                            break;
                        case "fra":
                            positionX = positionX + 12*multiplier;
                            mFractions.add(mockDraw(mockFraction(strings[i], pos), positionX));
                            if(mFractions.lastElement()[1] < mFractions.lastElement()[3])
                                positionX = positionX + ((mFractions.lastElement()[3] - mFractions.lastElement()[1]) / 2);
                            positionY = positionY - 14*multiplier - mFractions.lastElement()[2];
                            break;
                        case "frx":
                            positionX = mFractions.lastElement()[0];
                            if(mFractions.lastElement()[1] > mFractions.lastElement()[3])
                                positionX = positionX + ((mFractions.lastElement()[1] - mFractions.lastElement()[3]) / 2);
                            positionY = positionY + 28*multiplier + mFractions.lastElement()[2] + mFractions.lastElement()[4];
                            break;
                        case "frn":
                            positionY = positionY - 14*multiplier - mFractions.lastElement()[4];
                            positionX = Math.max(mFractions.lastElement()[1], mFractions.lastElement()[3]) + 12*multiplier;
                            mFractions.removeElementAt(mFractions.size() - 1);
                    }
                    pos = pos + 5;
                }
            }
            if(i == 0){
                limits[1] = positionX;
                limits[2] = maxY - mPositionY;
            } else {
                limits[3] = positionX;
                limits[4] = Math.abs(minY - (mPositionY - mTextHeight));
                return limits;
            }
        }
        return limits;
    }

    private static String[] mockFraction(String s, int position) {
        String numerator, denominator;
        int frxPos;
        if (!s.substring(position + 5, s.substring(position).indexOf("<frx>") + position).contains("<fra>")) {
            numerator = s.substring(position + 5, s.substring(position).indexOf("<frx>") + position);
            frxPos = s.substring(position).indexOf("<frx>") + position + 5;
        } else {
            int tempPos = position + 5;
            int hi = 1;
            while (hi != 0) {
                if (s.charAt(tempPos) == '<') {
                    String tag = s.substring(tempPos+1, tempPos+4);
                    if (tag.equals("fra")) hi++;
                    else if (tag.equals("frx")) --hi;
                    tempPos = tempPos + 5;
                } else tempPos++;
            }
            numerator = s.substring(position + 5, tempPos - 5);
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
}
