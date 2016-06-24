package me.hsky.androidshop.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import me.hsky.androidshop.R;

/**
 * Created by user on 2016/6/24.
 */
public class SideBar extends View{
    // ´¥ÃþÊÂ¼þ
    private OnTouchingLetterChangedListener onTouchingLetterChangedListener;
    // 26¸ö×ÖÄ¸
    public static String[] b = { "A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z", "#" };
    private int choose = -1;// Ñ¡ÖÐ
    private Paint paint = new Paint();

    private TextView mTextDialog;

    public void setTextView(TextView mTextDialog) {
        this.mTextDialog = mTextDialog;
    }


    public SideBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public SideBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SideBar(Context context) {
        super(context);
    }

    /**
     * ÖØÐ´Õâ¸ö·½·¨
     */
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // »ñÈ¡½¹µã¸Ä±ä±³¾°ÑÕÉ«.
        int height = getHeight();// »ñÈ¡¶ÔÓ¦¸ß¶È
        int width = getWidth(); // »ñÈ¡¶ÔÓ¦¿í¶È
        int singleHeight = height / b.length;// »ñÈ¡Ã¿Ò»¸ö×ÖÄ¸µÄ¸ß¶È

        for (int i = 0; i < b.length; i++) {
            paint.setColor(Color.rgb(33, 65, 98));
            // paint.setColor(Color.WHITE);
            paint.setTypeface(Typeface.DEFAULT_BOLD);
            paint.setAntiAlias(true);
            paint.setTextSize(20);
            // Ñ¡ÖÐµÄ×´Ì¬
            if (i == choose) {
                paint.setColor(Color.parseColor("#3399ff"));
                paint.setFakeBoldText(true);
            }
            // x×ø±êµÈÓÚÖÐ¼ä-×Ö·û´®¿í¶ÈµÄÒ»°ë.
            float xPos = width / 2 - paint.measureText(b[i]) / 2;
            float yPos = singleHeight * i + singleHeight;
            canvas.drawText(b[i], xPos, yPos, paint);
            paint.reset();// ÖØÖÃ»­±Ê
        }

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        final int action = event.getAction();
        final float y = event.getY();// µã»÷y×ø±ê
        final int oldChoose = choose;
        final OnTouchingLetterChangedListener listener = onTouchingLetterChangedListener;
        final int c = (int) (y / getHeight() * b.length);// µã»÷y×ø±êËùÕ¼×Ü¸ß¶ÈµÄ±ÈÀý*bÊý×éµÄ³¤¶È¾ÍµÈÓÚµã»÷bÖÐµÄ¸öÊý.

        switch (action) {
            case MotionEvent.ACTION_UP:
                setBackgroundDrawable(new ColorDrawable(0x00000000));
                choose = -1;//
                invalidate();
                if (mTextDialog != null) {
                    mTextDialog.setVisibility(View.INVISIBLE);
                }
                break;

            default:
                setBackgroundResource(R.drawable.sidebar_background);
                if (oldChoose != c) {
                    if (c >= 0 && c < b.length) {
                        if (listener != null) {
                            listener.onTouchingLetterChanged(b[c]);
                        }
                        if (mTextDialog != null) {
                            mTextDialog.setText(b[c]);
                            mTextDialog.setVisibility(View.VISIBLE);
                        }

                        choose = c;
                        invalidate();
                    }
                }

                break;
        }
        return true;
    }

    /**
     * ÏòÍâ¹«¿ªµÄ·½·¨
     *
     * @param onTouchingLetterChangedListener
     */
    public void setOnTouchingLetterChangedListener(
            OnTouchingLetterChangedListener onTouchingLetterChangedListener) {
        this.onTouchingLetterChangedListener = onTouchingLetterChangedListener;
    }

    /**
     * ½Ó¿Ú
     *
     * @author coder
     *
     */
    public interface OnTouchingLetterChangedListener {
        public void onTouchingLetterChanged(String s);
    }
}
