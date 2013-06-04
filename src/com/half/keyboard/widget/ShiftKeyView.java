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
public class ShiftKeyView extends BaseKeyView {
    private final static String TAG = "NormalKey";
    private ImageView ivTempShift;
    private ImageView ivCapLock;
    private ImageView ivShiftLock;


    private boolean isTempShift;
    private boolean isShift;
    private boolean isCapLock;
    private boolean isShiftLock;


    public void setTempShift(boolean tempShift) {
        isTempShift = tempShift;
    }

    public void setShift(boolean shift) {
        isShift = shift;
    }

    public void setShiftLock(boolean shiftLock) {
        isShiftLock = shiftLock;
    }

    public void setCapLock(boolean capLock) {
        isCapLock = capLock;
    }

    /**
     * 重新刷新界面
     */
    public void refresh() {
        ivTempShift.setImageResource(R.drawable.right_normal);
        ivCapLock.setImageResource(R.drawable.right_normal);
        ivShiftLock.setImageResource(R.drawable.right_normal);
        if (isTempShift) {
            ivTempShift.setImageResource(R.drawable.right_blink);
        }
        if (isShiftLock) {
            ivShiftLock.setImageResource(R.drawable.right_blink);
        }
        if (isCapLock) {
            ivCapLock.setImageResource(R.drawable.right_blink);
        }
    }

    public ShiftKeyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ShiftKeyView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        if (mOrientation == LEFT) {
            LayoutInflater.from(context).inflate(R.layout.widget_shift_key_left, this);
        } else {
            LayoutInflater.from(context).inflate(R.layout.widget_shift_key_right, this);
        }
        ivTempShift = (ImageView) findViewById(R.id.iv_temp_shift);
        ivCapLock = (ImageView) findViewById(R.id.iv_cap_lock);
        ivShiftLock = (ImageView) findViewById(R.id.iv_shift_lock);
    }
}
