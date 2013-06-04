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
public class SpaceKeyView extends BaseKeyView {
    private final static String TAG = "NormalKey";
    GestureDetector mDetector;
    Context mContext;
    AttributeSet mAttrs;

    public SpaceKeyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mAttrs = attrs;
        init();

    }

    public SpaceKeyView(Context context) {
        super(context);
        mContext = context;
        init();
    }

    private void init() {
        if (mOrientation == LEFT) {
            LayoutInflater.from(mContext).inflate(R.layout.widget_space_key_left, this);
        } else {
            LayoutInflater.from(mContext).inflate(R.layout.widget_space_key_right, this);
        }
    }
}
