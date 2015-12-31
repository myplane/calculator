package cz.pavelpilar.calculator.calculator;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

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

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        DrawingManager.drawTo(canvas);
        draw();
    }

    private void draw() {
        if(mSource != null) {
            Log.d("DRAW", mSource);
            int pos = 0;
            while (pos != mSource.length()) {
                if(mSource.charAt(pos) != '<'){
                    DrawingManager.drawChar(mSource.charAt(pos));
                    pos++;
                } else {
                    String tag = mSource.substring(pos+1, pos+4);
                    switch(tag){
                        case "exp": DrawingManager.drawChar('E'); break;
                        case "log": DrawingManager.drawText("log"); break;
                        case "lon": DrawingManager.drawText("ln"); break;
                        case "sin": DrawingManager.drawText("sin"); break;
                        case "cos": DrawingManager.drawText("cos"); break;
                        case "tan": DrawingManager.drawText("tan"); break;
                        case "snh": DrawingManager.drawText("sinh"); break;
                        case "csh": DrawingManager.drawText("cosh"); break;
                        case "tnh": DrawingManager.drawText("tanh"); break;
                        case "mod": DrawingManager.drawText("MOD"); break;
                        case "rnd": DrawingManager.drawText("RAND"); break;
                        case "rdr":
                            DrawingManager.drawText("RAND[");
                            DrawingManager.functionStart();
                            break;
                        case "rdx":
                            DrawingManager.functionEnd();
                            DrawingManager.drawChar(',');
                            DrawingManager.functionStart();
                            break;
                        case "rdn":
                            DrawingManager.drawChar(']');
                            DrawingManager.functionEnd();
                            break;
                        case "pcp":
                            DrawingManager.drawChar('+');
                            DrawingManager.functionStart();
                            break;
                        case "pcm":
                            DrawingManager.drawChar('-');
                            DrawingManager.functionStart();
                            break;
                        case "prc":
                            DrawingManager.functionEnd();
                            DrawingManager.drawChar('%');
                            break;
                        case "mem": DrawingManager.drawChar('M'); break;
                        case "hex":
                        case "oct":
                        case "bin": DrawingManager.drawChar('('); break;
                        case "hxn":
                            DrawingManager.drawChar(')');
                            DrawingManager.subscriptStart();
                            DrawingManager.drawChar('1');
                            DrawingManager.drawChar('6');
                            DrawingManager.subscriptEnd();
                            break;
                        case "ocn":
                            DrawingManager.drawChar(')');
                            DrawingManager.subscriptStart();
                            DrawingManager.drawChar('8');
                            DrawingManager.subscriptEnd();
                            break;
                        case "bnn":
                            DrawingManager.drawChar(')');
                            DrawingManager.subscriptStart();
                            DrawingManager.drawChar('2');
                            DrawingManager.subscriptEnd();
                            break;
                        case "asn":
                            DrawingManager.drawText("sin");
                            DrawingManager.superscriptStart();
                            DrawingManager.drawText("-1");
                            DrawingManager.superscriptEnd();
                            break;
                        case "acs":
                            DrawingManager.drawText("cos");
                            DrawingManager.superscriptStart();
                            DrawingManager.drawText("-1");
                            DrawingManager.superscriptEnd();
                            break;
                        case "atn":
                            DrawingManager.drawText("tan");
                            DrawingManager.superscriptStart();
                            DrawingManager.drawText("-1");
                            DrawingManager.superscriptEnd();
                            break;
                        case "ash":
                            DrawingManager.drawText("sinh");
                            DrawingManager.superscriptStart();
                            DrawingManager.drawText("-1");
                            DrawingManager.superscriptEnd();
                            break;
                        case "ach":
                            DrawingManager.drawText("cosh");
                            DrawingManager.superscriptStart();
                            DrawingManager.drawText("-1");
                            DrawingManager.superscriptEnd();
                            break;
                        case "ath":
                            DrawingManager.drawText("tanh");
                            DrawingManager.superscriptStart();
                            DrawingManager.drawText("-1");
                            DrawingManager.superscriptEnd();
                            break;
                        case "pow":
                            DrawingManager.functionStart();
                            DrawingManager.superscriptStart();
                            break;
                        case "pwn":
                            DrawingManager.superscriptEnd();
                            DrawingManager.functionEnd();
                            break;
                        case "par": DrawingManager.parenthesesStart(); break;
                        case "prn": DrawingManager.parenthesesEnd(); break;
                        case "fra":
                            DrawingManager.fractionStart(pos);
                            DrawingManager.functionStart();
                            break;
                        case "frx":
                            DrawingManager.functionEnd();
                            DrawingManager.fractionLine();
                            DrawingManager.functionStart();
                            break;
                        case "frn":
                            DrawingManager.functionEnd();
                            DrawingManager.fractionEnd();
                            break;
                        case "nrt":
                            DrawingManager.superscriptStart();
                            DrawingManager.functionStart();
                            break;
                        case "rtx":
                            DrawingManager.superscriptEnd();
                            DrawingManager.functionEnd();   //Continues, not a bug
                        case "srt":
                            DrawingManager.rootStart();
                            DrawingManager.functionStart();
                            break;
                        case "rtn":
                            DrawingManager.functionEnd();
                            DrawingManager.rootEnd();
                            break;
                        case "lgx":
                            DrawingManager.drawText("log");
                            DrawingManager.subscriptStart();
                            DrawingManager.functionStart();
                            break;
                        case "lgn":
                            DrawingManager.functionEnd();
                            DrawingManager.subscriptEnd();
                            break;
                    }
                    pos = pos + 5;
                }
            }
        }
    }

}