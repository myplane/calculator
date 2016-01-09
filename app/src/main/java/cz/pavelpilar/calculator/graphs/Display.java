package cz.pavelpilar.calculator.graphs;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;

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
        if(mFocus) canvas.drawColor(Color.DKGRAY);
        else {
            canvas.drawColor(Color.BLACK);
            mSource = mSource.replace("|", "");
        }
        if(mSource != null) {
            int positionX = 10;
            int positionY = canvas.getHeight()/2 + (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 7, mDisplayMetrics);
            canvas.drawText("y = ", positionX, positionY, mPaint);
            positionX = positionX + (int)  TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 28, mDisplayMetrics);

            int pos = 0;
            while(pos != mSource.length()) {
                if(mSource.charAt(pos) != '<') {
                    canvas.drawText(Character.toString(mSource.charAt(pos)), positionX, positionY, mPaint);
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
                            canvas.drawText("sin-1(", positionX, positionY, mPaint);
                            positionX = positionX + (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 45, mDisplayMetrics);
                            break;
                        case "acs":
                            canvas.drawText("cos-1(", positionX, positionY, mPaint);
                            positionX = positionX + (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, mDisplayMetrics);
                            break;
                        case "atn":
                            canvas.drawText("tan-1(", positionX, positionY, mPaint);
                            positionX = positionX + (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 47, mDisplayMetrics);
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
                            canvas.drawText("sinh-1(", positionX, positionY, mPaint);
                            positionX = positionX + (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 56, mDisplayMetrics);
                            break;
                        case "ach":
                            canvas.drawText("cosh-1(", positionX, positionY, mPaint);
                            positionX = positionX + (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 61, mDisplayMetrics);
                            break;
                        case "ath":
                            canvas.drawText("tanh-1(", positionX, positionY, mPaint);
                            positionX = positionX + (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 58, mDisplayMetrics);
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
                    }
                    pos = pos + 5;
                }
            }
        }
    }
}
