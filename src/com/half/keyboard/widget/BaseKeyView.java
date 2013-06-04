/**
 *
 */
package com.half.keyboard.widget;

import android.content.res.TypedArray;
import com.half.keyboard.R;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

/**
 * @author Thinkpad
 */
public class BaseKeyView extends RelativeLayout {
    private final static String TAG = "BaseKeyView";
    final static int LEFT = 1;
    final static int RIGHT = 2;
    RelativeLayout rl;
    int mOrientation;

    public BaseKeyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.BaseKeyView);
        mOrientation = typedArray.getInt(R.styleable.BaseKeyView_orientation, 0);

        Log.d(TAG,"mOrientation:" +mOrientation);
        typedArray.recycle();
    }

    public BaseKeyView(Context context) {
        super(context);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    public void setNormal() {
        if (rl == null) {
            rl = (RelativeLayout) findViewById(R.id.rl);
        }
        rl.setBackgroundResource(R.drawable.key_bg_normal);
    }

    public void setPressed() {
        if (rl == null) {
            rl = (RelativeLayout) findViewById(R.id.rl);
        }
        rl.setBackgroundResource(R.drawable.key_bg_selected);
    }
}
