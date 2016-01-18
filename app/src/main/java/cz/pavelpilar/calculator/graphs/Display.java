package cz.pavelpilar.calculator.graphs;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;

import java.util.Vector;

import cz.pavelpilar.calculator.MainActivity;

public class Display extends View {

    private String mSource;
    private boolean mFocus;
    private Paint mPaint;
    private DisplayMetrics mDisplayMetrics;

    public Display(Context context, AttributeSet as) {
        super(context, as);

        mDisplayMetrics = MainActivity.mContext.getResources().getDisplayMetrics();

        mPaint = new Paint();
        mPaint.setColor(Color.WHITE);
        mPaint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 18, mDisplayMetrics));
        mPaint.setAntiAlias(true);
    }

    public void show(String s, boolean inFocus) {
        mSource = s;
        mFocus = inFocus;
        invalidate();
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(mSource != null) {
            if(mFocus) canvas.drawColor(Color.DKGRAY);
            else {
                canvas.drawColor(Color.BLACK);
                mSource = mSource.replace("|", "");
            }
            int positionX = 10;
            int positionY = this.getHeight()/2;
            canvas.drawText("y = ", positionX, positionY, mPaint);
            positionX = positionX + (int)  TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 28, mDisplayMetrics);
            int[]fraction = new int[3];

            int pos = 0;
            while(pos != mSource.length()) {
                if(mSource.charAt(pos) != '<') {
                    if(mSource.charAt(pos) == '|') mPaint.setColor(Color.GRAY);
                    canvas.drawText(Character.toString(mSource.charAt(pos)), positionX, positionY, mPaint);
                    mPaint.setColor(Color.WHITE);
                    switch (mSource.charAt(pos)) {
                        case '|':
                        case '.': positionX = positionX + (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, mDisplayMetrics); break;
                        case '-':
                        case '1': positionX = positionX + (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, mDisplayMetrics); break;
                        case '2':
                        case '3':
                        case '4':
                        case '5':
                        case '6':
                        case '7':
                        case '8':
                        case '9':
                        case '0': positionX = positionX + (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 9, mDisplayMetrics); break;
                        case 'π':
                        case '÷': positionX = positionX + (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, mDisplayMetrics); break;
                        default:
                            positionX = positionX + (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 9, mDisplayMetrics);
                    }
                    pos++;
                } else {
                    String tag = mSource.substring(pos + 1, pos + 4);
                    switch (tag){
                        case "xxx":
                            canvas.drawText("x", positionX, positionY, mPaint);
                            positionX = positionX + (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, mDisplayMetrics);
                            break;
                        case "sin":
                            canvas.drawText("sin(", positionX, positionY, mPaint);
                            positionX = positionX + (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, mDisplayMetrics);
                            break;
                        case "cos":
                            canvas.drawText("cos(", positionX, positionY, mPaint);
                            positionX = positionX + (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 36, mDisplayMetrics);
                            break;
                        case "tan":
                            canvas.drawText("tan(", positionX, positionY, mPaint);
                            positionX = positionX + (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 32, mDisplayMetrics);
                            break;
                        case "asn":
                            canvas.drawText("asin(", positionX, positionY, mPaint);
                            positionX = positionX + (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, mDisplayMetrics);
                            break;
                        case "acs":
                            canvas.drawText("acos(", positionX, positionY, mPaint);
                            positionX = positionX + (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 45, mDisplayMetrics);
                            break;
                        case "atn":
                            canvas.drawText("atan(", positionX, positionY, mPaint);
                            positionX = positionX + (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 42, mDisplayMetrics);
                            break;
                        case "snh":
                            canvas.drawText("sinh(", positionX, positionY, mPaint);
                            positionX = positionX + (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 41, mDisplayMetrics);
                            break;
                        case "csh":
                            canvas.drawText("cosh(", positionX, positionY, mPaint);
                            positionX = positionX + (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 46, mDisplayMetrics);
                            break;
                        case "tnh":
                            canvas.drawText("tanh(", positionX, positionY, mPaint);
                            positionX = positionX + (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 43, mDisplayMetrics);
                            break;
                        case "ash":
                            canvas.drawText("asinh(", positionX, positionY, mPaint);
                            positionX = positionX + (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 51, mDisplayMetrics);
                            break;
                        case "ach":
                            canvas.drawText("acosh(", positionX, positionY, mPaint);
                            positionX = positionX + (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 56, mDisplayMetrics);
                            break;
                        case "ath":
                            canvas.drawText("atanh(", positionX, positionY, mPaint);
                            positionX = positionX + (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 53, mDisplayMetrics);
                            break;
                        case "log":
                            canvas.drawText("log(", positionX, positionY, mPaint);
                            positionX = positionX + (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 32, mDisplayMetrics);
                            break;
                        case "lon":
                            canvas.drawText("ln(", positionX, positionY, mPaint);
                            positionX = positionX + (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, mDisplayMetrics);
                            break;
                        case "srt":
                            canvas.drawText("√(", positionX, positionY, mPaint);
                            positionX = positionX + (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15, mDisplayMetrics);
                            break;
                        case "crt":
                            canvas.drawText("∛(", positionX, positionY, mPaint);
                            positionX = positionX + (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15, mDisplayMetrics);
                            break;
                        case "end":
                            canvas.drawText(")", positionX, positionY, mPaint);
                            positionX = positionX + (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 7, mDisplayMetrics);
                            break;
                        case "pow":
                            positionY = positionY - (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 7, mDisplayMetrics);
                            break;
                        case "pwn":
                            positionY = positionY + (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 7, mDisplayMetrics);
                            break;
                        case "abs":
                        case "abn":
                            canvas.drawText("|", positionX + TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1.5f, mDisplayMetrics), positionY, mPaint);
                            positionX = positionX + (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 7, mDisplayMetrics);
                            break;
                        case "fra":
                            positionY = positionY - (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 12, mDisplayMetrics);
                            int[] lengths = fractionLengths(pos);
                            fraction = new int[] {positionX, lengths[0], lengths[1], lengths[2]};
                            if(fraction[1] < fraction[2]) positionX = positionX + (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, mDisplayMetrics) + (fraction[2] - fraction[1])/2;
                            else positionX = positionX + (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, mDisplayMetrics);
                            break;
                        case "frx":
                            positionY = positionY + (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, mDisplayMetrics);
                            if(fraction[1] > fraction[2]) positionX = fraction[0] + (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, mDisplayMetrics) + (fraction[1] - fraction[2])/2;
                            else positionX = fraction[0] + (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, mDisplayMetrics);
                            if(fraction[3] == 1) positionY = positionY + (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, mDisplayMetrics);
                            break;
                        case "frn":
                            positionY = positionY - (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 12, mDisplayMetrics);
                            positionX = fraction[0] + Math.max(fraction[1], fraction[2]) + (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, mDisplayMetrics);
                            if(fraction[3] == 1) positionY = positionY - (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, mDisplayMetrics);
                            canvas.drawLine(fraction[0],
                                            positionY - (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5.5f, mDisplayMetrics),
                                            positionX,
                                            positionY - (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5.5f, mDisplayMetrics),
                                            mPaint);
                    }
                    pos = pos + 5;
                }
            }
        }
    }

    private int[] fractionLengths(int startPos) {
        String numerator = mSource.substring(startPos+5).substring(0, mSource.substring(startPos+5).indexOf("<frx>"));
        String denominator = mSource.substring(startPos).substring(mSource.substring(startPos).indexOf("<frx>")+5, mSource.substring(startPos).indexOf("<frn>"));
        int denominatorPower = (denominator.contains("<pow>")) ? 1 : 0;
        return new int[] {getLength(numerator), getLength(denominator), denominatorPower};
    }

    private int getLength(String s) {
        int total = 0;
        int position = 0;
        while(position < s.length()) {
            if(s.charAt(position) == '<') {
                switch (s.substring(position +1, position+4)) {
                    case "xxx": total = total + (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, mDisplayMetrics); break;
                    case "sin": total = total + (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, mDisplayMetrics); break;
                    case "cos": total = total + (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 36, mDisplayMetrics); break;
                    case "tan": total = total + (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 32, mDisplayMetrics); break;
                    case "asn": total = total + (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 45, mDisplayMetrics); break;
                    case "acs": total = total + (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 61, mDisplayMetrics); break;
                    case "atn": total = total + (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 47, mDisplayMetrics); break;
                    case "snh": total = total + (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 41, mDisplayMetrics); break;
                    case "csh": total = total + (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 46, mDisplayMetrics); break;
                    case "tnh": total = total + (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 43, mDisplayMetrics); break;
                    case "ash": total = total + (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 56, mDisplayMetrics); break;
                    case "ach": total = total + (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 61, mDisplayMetrics); break;
                    case "ath": total = total + (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 58, mDisplayMetrics); break;
                    case "log": total = total + (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 32, mDisplayMetrics); break;
                    case "lon": total = total + (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, mDisplayMetrics); break;
                    case "crt":
                    case "srt": total = total + (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15, mDisplayMetrics); break;
                    case "end": total = total + (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 7, mDisplayMetrics); break;
                    case "abs":
                    case "abn":total = total + (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 7, mDisplayMetrics); break;
                }
                position = position + 5;
            }
            else {
                switch(s.charAt(position)) {
                    case '|':
                    case '.': total = total + (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, mDisplayMetrics); break;
                    case '-':
                    case '1': total = total + (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, mDisplayMetrics); break;
                    case '2':
                    case '3':
                    case '4':
                    case '5':
                    case '6':
                    case '7':
                    case '8':
                    case '9':
                    case '0': total = total + (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 9, mDisplayMetrics); break;
                    case 'π':
                    case '÷': total = total + (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, mDisplayMetrics); break;
                    default:
                        total = total + (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 9, mDisplayMetrics);
                }
                position++;
            }
        }
        return total;
    }
}
