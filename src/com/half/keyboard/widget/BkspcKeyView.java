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
public class BkspcKeyView extends BaseKeyView {
    private final static String TAG = "BkspcKeyView";
    GestureDetector mDetector;
    Context mContext;
    private int mMoveTimes;

    public int getMoveTimes() {
        return mMoveTimes;
    }

    public void setMoveTimes(int moveTimes) {
        this.mMoveTimes = moveTimes;
    }

    public BkspcKeyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();

    }

    public BkspcKeyView(Context context) {
        super(context);
        mContext = context;
        init();
    }

    private void init() {
        if (mOrientation == LEFT) {
            LayoutInflater.from(mContext).inflate(R.layout.widget_bkspc_key_left, this);
        } else {
            LayoutInflater.from(mContext).inflate(R.layout.widget_bkspc_key_right, this);
        }
    }
}
