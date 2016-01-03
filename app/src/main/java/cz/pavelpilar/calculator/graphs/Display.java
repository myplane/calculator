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

    public void show(String s) {
        mSource = s;
        invalidate();
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.BLACK);
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
                }
            }
        }
    }
}
