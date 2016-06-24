package me.hsky.androidshop.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;

import me.hsky.androidshop.R;

/**
 * Created by user on 2016/6/24.
 */
public class ClearEditText extends EditText implements
        View.OnFocusChangeListener, TextWatcher {
    /**
     * É¾³ý°´Å¥µÄÒýÓÃ
     */
    private Drawable mClearDrawable;

    public ClearEditText(Context context) {
        this(context, null);
    }

    public ClearEditText(Context context, AttributeSet attrs) {
        //ÕâÀï¹¹Ôì·½·¨Ò²ºÜÖØÒª£¬²»¼ÓÕâ¸öºÜ¶àÊôÐÔ²»ÄÜÔÙXMLÀïÃæ¶¨Òå
        this(context, attrs, android.R.attr.editTextStyle);
    }

    public ClearEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }


    private void init() {
        //»ñÈ¡EditTextµÄDrawableRight,¼ÙÈçÃ»ÓÐÉèÖÃÎÒÃÇ¾ÍÊ¹ÓÃÄ¬ÈÏµÄÍ¼Æ¬
        mClearDrawable = getCompoundDrawables()[2];
        if (mClearDrawable == null) {
            mClearDrawable = getResources()
                    .getDrawable(R.drawable.emotionstore_progresscancelbtn);
        }
        mClearDrawable.setBounds(0, 0, mClearDrawable.getIntrinsicWidth(), mClearDrawable.getIntrinsicHeight());
        setClearIconVisible(false);
        setOnFocusChangeListener(this);
        addTextChangedListener(this);
    }


    /**
     * ÒòÎªÎÒÃÇ²»ÄÜÖ±½Ó¸øEditTextÉèÖÃµã»÷ÊÂ¼þ£¬ËùÒÔÎÒÃÇÓÃ¼Ç×¡ÎÒÃÇ°´ÏÂµÄÎ»ÖÃÀ´Ä£Äâµã»÷ÊÂ¼þ
     * µ±ÎÒÃÇ°´ÏÂµÄÎ»ÖÃ ÔÚ  EditTextµÄ¿í¶È - Í¼±êµ½¿Ø¼þÓÒ±ßµÄ¼ä¾à - Í¼±êµÄ¿í¶È  ºÍ
     * EditTextµÄ¿í¶È - Í¼±êµ½¿Ø¼þÓÒ±ßµÄ¼ä¾àÖ®¼äÎÒÃÇ¾ÍËãµã»÷ÁËÍ¼±ê£¬ÊúÖ±·½ÏòÃ»ÓÐ¿¼ÂÇ
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (getCompoundDrawables()[2] != null) {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                boolean touchable = event.getX() > (getWidth()
                        - getPaddingRight() - mClearDrawable.getIntrinsicWidth())
                        && (event.getX() < ((getWidth() - getPaddingRight())));
                if (touchable) {
                    this.setText("");
                }
            }
        }

        return super.onTouchEvent(event);
    }

    /**
     * µ±ClearEditText½¹µã·¢Éú±ä»¯µÄÊ±ºò£¬ÅÐ¶ÏÀïÃæ×Ö·û´®³¤¶ÈÉèÖÃÇå³ýÍ¼±êµÄÏÔÊ¾ÓëÒþ²Ø
     */
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            setClearIconVisible(getText().length() > 0);
        } else {
            setClearIconVisible(false);
        }
    }


    /**
     * ÉèÖÃÇå³ýÍ¼±êµÄÏÔÊ¾ÓëÒþ²Ø£¬µ÷ÓÃsetCompoundDrawablesÎªEditText»æÖÆÉÏÈ¥
     * @param visible
     */
    protected void setClearIconVisible(boolean visible) {
        Drawable right = visible ? mClearDrawable : null;
        setCompoundDrawables(getCompoundDrawables()[0],
                getCompoundDrawables()[1], right, getCompoundDrawables()[3]);
    }


    /**
     * µ±ÊäÈë¿òÀïÃæÄÚÈÝ·¢Éú±ä»¯µÄÊ±ºò»Øµ÷µÄ·½·¨
     */
    @Override
    public void onTextChanged(CharSequence s, int start, int count,
                              int after) {
        setClearIconVisible(s.length() > 0);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count,
                                  int after) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }


    /**
     * ÉèÖÃ»Î¶¯¶¯»­
     */
    public void setShakeAnimation(){
        this.setAnimation(shakeAnimation(5));
    }


    /**
     * »Î¶¯¶¯»­
     * @param counts 1ÃëÖÓ»Î¶¯¶àÉÙÏÂ
     * @return
     */
    public static Animation shakeAnimation(int counts){
        Animation translateAnimation = new TranslateAnimation(0, 10, 0, 0);
        translateAnimation.setInterpolator(new CycleInterpolator(counts));
        translateAnimation.setDuration(1000);
        return translateAnimation;
    }
}
