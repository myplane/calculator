package cz.pavelpilar.calculator.calculator;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class Display extends View {

    private String mSource;

    public Display(Context context, AttributeSet as){
        super(context, as);
        DrawingManager.initialize();
    }

    public void show(String s) {
        mSource = s;
        invalidate();
    }

    public String getSource() {
        return mSource;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        DrawingManager.drawTo(canvas);
        draw();
    }

    private void draw() {
        if(mSource != null) {
            int pos = 0;
            List<Point> positions = new ArrayList<>();
            while(positions.size() < mSource.length()) positions.add(new Point(-1000, -1000));
            while (pos != mSource.length()) {
                if(mSource.charAt(pos) != '<'){
                    positions.set(pos, DrawingManager.drawChar(mSource.charAt(pos)));
                    pos++;
                } else {
                    String tag = mSource.substring(pos+1, pos+4);
                    switch(tag){
                        case "exp": positions.set(pos, DrawingManager.drawChar('E')); break;
                        case "log": positions.set(pos, DrawingManager.drawText("log")); break;
                        case "lon": positions.set(pos, DrawingManager.drawText("ln")); break;
                        case "sin": positions.set(pos, DrawingManager.drawText("sin")); break;
                        case "cos": positions.set(pos, DrawingManager.drawText("cos")); break;
                        case "tan": positions.set(pos, DrawingManager.drawText("tan")); break;
                        case "snh": positions.set(pos, DrawingManager.drawText("sinh")); break;
                        case "csh": positions.set(pos, DrawingManager.drawText("cosh")); break;
                        case "tnh": positions.set(pos, DrawingManager.drawText("tanh")); break;
                        case "mod": positions.set(pos, DrawingManager.drawText("MOD")); break;
                        case "rnd": positions.set(pos, DrawingManager.drawText("RAND")); break;
                        case "rdr":
                            positions.set(pos, DrawingManager.drawText("RAND["));
                            DrawingManager.functionStart();
                            break;
                        case "rdx":
                            DrawingManager.functionEnd();
                            positions.set(pos, DrawingManager.drawChar(','));
                            DrawingManager.functionStart();
                            break;
                        case "rdn":
                            positions.set(pos, DrawingManager.drawChar(']'));
                            DrawingManager.functionEnd();
                            break;
                        case "pcp":
                            positions.set(pos, DrawingManager.drawChar('+'));
                            DrawingManager.functionStart();
                            break;
                        case "pcm":
                            positions.set(pos, DrawingManager.drawChar('-'));
                            DrawingManager.functionStart();
                            break;
                        case "prc":
                            DrawingManager.functionEnd();
                            positions.set(pos, DrawingManager.drawChar('%'));
                            break;
                        case "mem": positions.set(pos, DrawingManager.drawChar('M')); break;
                        case "hex":
                        case "oct":
                        case "bin": positions.set(pos, DrawingManager.drawChar('(')); break;
                        case "hxn":
                            positions.set(pos, DrawingManager.drawChar(')'));
                            DrawingManager.subscriptStart();
                            DrawingManager.drawChar('1');
                            DrawingManager.drawChar('6');
                            DrawingManager.subscriptEnd();
                            break;
                        case "ocn":
                            positions.set(pos, DrawingManager.drawChar(')'));
                            DrawingManager.subscriptStart();
                            DrawingManager.drawChar('8');
                            DrawingManager.subscriptEnd();
                            break;
                        case "bnn":
                            positions.set(pos, DrawingManager.drawChar(')'));
                            DrawingManager.subscriptStart();
                            DrawingManager.drawChar('2');
                            DrawingManager.subscriptEnd();
                            break;
                        case "asn":
                            positions.set(pos, DrawingManager.drawText("sin"));
                            DrawingManager.superscriptStart();
                            DrawingManager.drawText("-1");
                            DrawingManager.superscriptEnd();
                            break;
                        case "acs":
                            positions.set(pos, DrawingManager.drawText("cos"));
                            DrawingManager.superscriptStart();
                            DrawingManager.drawText("-1");
                            DrawingManager.superscriptEnd();
                            break;
                        case "atn":
                            positions.set(pos, DrawingManager.drawText("tan"));
                            DrawingManager.superscriptStart();
                            DrawingManager.drawText("-1");
                            DrawingManager.superscriptEnd();
                            break;
                        case "ash":
                            positions.set(pos, DrawingManager.drawText("sinh"));
                            DrawingManager.superscriptStart();
                            DrawingManager.drawText("-1");
                            DrawingManager.superscriptEnd();
                            break;
                        case "ach":
                            positions.set(pos, DrawingManager.drawText("cosh"));
                            DrawingManager.superscriptStart();
                            DrawingManager.drawText("-1");
                            DrawingManager.superscriptEnd();
                            break;
                        case "ath":
                            positions.set(pos, DrawingManager.drawText("tanh"));
                            DrawingManager.superscriptStart();
                            DrawingManager.drawText("-1");
                            DrawingManager.superscriptEnd();
                            break;
                        case "pow":
                            DrawingManager.functionStart();
                            positions.set(pos, DrawingManager.superscriptStart());
                            break;
                        case "pwn":
                            positions.set(pos, DrawingManager.superscriptEnd());
                            DrawingManager.functionEnd();
                            break;
                        case "par": positions.set(pos, DrawingManager.parenthesesStart()); break;
                        case "prn": positions.set(pos, DrawingManager.parenthesesEnd()); break;
                        case "fra":
                            positions.set(pos, DrawingManager.fractionStart(getFraction(pos)));
                            DrawingManager.functionStart();
                            break;
                        case "frx":
                            DrawingManager.functionEnd();
                            positions.set(pos, DrawingManager.fractionLine());
                            DrawingManager.functionStart();
                            break;
                        case "frn":
                            DrawingManager.functionEnd();
                            positions.set(pos, DrawingManager.fractionEnd());
                            break;
                        case "nrt":
                            DrawingManager.superscriptStart();
                            DrawingManager.functionStart();
                            break;
                        case "rtx":
                            positions.set(pos, DrawingManager.superscriptEnd());
                            DrawingManager.functionEnd();   //Continues, not a bug
                        case "srt":
                            positions.set(pos, DrawingManager.rootStart());
                            DrawingManager.functionStart();
                            break;
                        case "rtn":
                            DrawingManager.functionEnd();
                            positions.set(pos, DrawingManager.rootEnd());
                            break;
                        case "lgx":
                            positions.set(pos, DrawingManager.drawText("log"));
                            DrawingManager.subscriptStart();
                            DrawingManager.functionStart();
                            break;
                        case "lgn":
                            DrawingManager.functionEnd();
                            positions.set(pos, DrawingManager.subscriptEnd());
                            break;
                        case "abs":
                        case "abn":
                            positions.set(pos, DrawingManager.drawChar('|'));
                            break;
                        case "npr":
                        case "ncr":
                            positions.set(pos, DrawingManager.parenthesesStart());
                            break;
                        case "npx":
                            positions.set(pos, DrawingManager.drawChar('P'));
                            break;
                        case "ncx":
                            positions.set(pos, DrawingManager.drawChar('C'));
                            break;
                        case "npn":
                        case "ncn":
                            positions.set(pos, DrawingManager.parenthesesEnd());
                            break;
                        default:
                            Log.d("ERROR", "Unhandled tag " + tag);
                    }
                    pos = pos + 5;
                }
            }
            positions.add(DrawingManager.end());
            InputManager.setPositions(positions);
        }
    }

    private String[] getFraction(int index) {
        String numerator, denominator;
        int frxPos;
        if (!mSource.substring(index + 5, mSource.substring(index).indexOf("<frx>") + index).contains("<fra>")) {
            numerator = mSource.substring(index + 5, mSource.substring(index).indexOf("<frx>") + index);
            frxPos = mSource.substring(index).indexOf("<frx>") + index + 5;
        } else {
            int tempPos = index + 5;
            int hi = 1;
            while (hi != 0) {
                if (mSource.charAt(tempPos) == '<') {
                    String tag = mSource.substring(tempPos+1, tempPos+4);
                    if (tag.equals("fra")) hi++;
                    else if (tag.equals("frx")) --hi;
                    tempPos = tempPos + 5;
                } else tempPos++;
            }
            numerator = mSource.substring(index + 5, tempPos - 5);
            frxPos = tempPos;
        }
        if (!mSource.substring(frxPos, mSource.substring(frxPos).indexOf("<frn>") + frxPos).contains("<fra>")) {
            denominator = mSource.substring(frxPos, mSource.substring(frxPos).indexOf("<frn>") + frxPos);
        } else {
            int tempPos = frxPos;
            int hi = 1;
            while (hi != 0) {
                if(mSource.charAt(tempPos) == '<') {
                    String tag = mSource.substring(tempPos+1, tempPos+4);
                    if (tag.equals("fra")) ++hi;
                    else if (tag.equals("frn"))--hi;
                    tempPos = tempPos + 5;
                }else tempPos++;
            }
            denominator = mSource.substring(frxPos, tempPos - 5);
        }
        return new String[] {numerator, denominator};
    }
}
