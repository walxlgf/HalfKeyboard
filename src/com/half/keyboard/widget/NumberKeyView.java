/**
 *
 */
package com.half.keyboard.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;

import com.half.keyboard.R;

/**
 * @author Thinkpad
 */
public class NumberKeyView extends BaseKeyView {
    private final static String TAG = "NumberKeyView";
    private Context mContext;
    private ImageView ivKeypadNumberLock;
    private ImageView ivKeyboardNumberLock;

    private boolean isKeypadNumberLock;
    private boolean isKeyboardNumberLock;

    public void setKeypadNumberLock(boolean keypadNumberLock) {
        isKeypadNumberLock = keypadNumberLock;
    }

    public void setKeyboardNumberLock(boolean keyboardNumberLock) {
        isKeyboardNumberLock = keyboardNumberLock;
    }

    public void refresh() {
        ivKeypadNumberLock.setImageResource(R.drawable.right_normal);
        ivKeyboardNumberLock.setImageResource(R.drawable.right_normal);
        if (isKeypadNumberLock) {
            ivKeypadNumberLock.setImageResource(R.drawable.right_blink);
        }
        if (isKeyboardNumberLock) {
            ivKeyboardNumberLock.setImageResource(R.drawable.right_blink);
        }

    }

    public NumberKeyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    public NumberKeyView(Context context) {
        super(context);
        mContext = context;
        init();
    }

    private void init() {
        if (mOrientation == LEFT) {
            LayoutInflater.from(mContext).inflate(R.layout.widget_number_key_left, this);
        } else {
            LayoutInflater.from(mContext).inflate(R.layout.widget_number_key_rihgt, this);
        }
        ivKeypadNumberLock = (ImageView) findViewById(R.id.iv_keypad_number_lock);
        ivKeyboardNumberLock = (ImageView) findViewById(R.id.iv_keyboard_number_lock);
    }


}
