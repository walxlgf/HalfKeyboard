/**
 *
 */
package com.half.keyboard.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.LayoutInflater;

import com.half.keyboard.R;

/**
 * @author Thinkpad
 */
public class EnterKeyView extends BaseKeyView {
    private final static String TAG = "NormalKey";
    GestureDetector mDetector;
    Context mContext;
    AttributeSet mAttrs;

    public EnterKeyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mAttrs = attrs;
        init();

    }

    public EnterKeyView(Context context) {
        super(context);
        mContext = context;
        init();
    }

    private void init() {
        if (mOrientation == LEFT) {
            LayoutInflater.from(mContext).inflate(R.layout.widget_enter_key_left, this);
        } else {
            LayoutInflater.from(mContext).inflate(R.layout.widget_enter_key_right, this);
        }
    }

}
