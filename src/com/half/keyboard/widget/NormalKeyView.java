/**
 *
 */
package com.half.keyboard.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.widget.TextView;
import com.half.keyboard.R;
import com.half.keyboard.utils.Config;
import com.half.keyboard.vo.NormalKeyData;

/**
 * @author Thinkpad
 */
public class NormalKeyView extends BaseKeyView {
    private final static String TAG = "NormalKeyView";
    GestureDetector mDetector;
    Context mContext;

    private TextView tvPrimarySymbol;
    private TextView tvSecondarySymbol;
    private TextView tvPrimaryLetter;
    private TextView tvSecondaryLetter;
    private TextView tvPrimaryNumber;
    private TextView tvSecondaryNumber;
    private TextView tvFunction;
    private VerticalTextView tvSpecial;

    private NormalKeyData mData;
    private int mPosition;

    private int mMoveTimes;

    public int getMoveTimes() {
        return mMoveTimes;
    }

    public void setMoveTimes(int moveTimes) {
        this.mMoveTimes = moveTimes;
    }

    public NormalKeyData getData() {
        return mData;
    }

    public NormalKeyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init(attrs);
    }

    public NormalKeyView(Context context) {
        super(context);
        mContext = context;
    }

    private void init(AttributeSet attrs) {
        Log.d(TAG,"mOrientation:" +mOrientation);
        if (mOrientation == LEFT) {
            LayoutInflater.from(mContext).inflate(R.layout.widget_normal_key_left, this);
        } else {
            LayoutInflater.from(mContext).inflate(R.layout.widget_normal_key_right, this);
        }

        tvPrimaryLetter = (TextView) findViewById(R.id.tv_primary_letter);
        tvSecondaryLetter = (TextView) findViewById(R.id.tv_secondary_letter);
        tvPrimarySymbol = (TextView) findViewById(R.id.tv_primary_symbol);
        tvSecondarySymbol = (TextView) findViewById(R.id.tv_secondary_symbol);
        tvPrimaryNumber = (TextView) findViewById(R.id.tv_primary_number);
        tvSecondaryNumber = (TextView) findViewById(R.id.tv_secondary_number);
        tvFunction = (TextView) findViewById(R.id.tv_function);
        tvSpecial = (VerticalTextView) findViewById(R.id.tv_special);

        if (attrs != null) {
            TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.NormalKeyView);
            mPosition = typedArray.getInt(R.styleable.NormalKeyView_position, 0);
            mData = Config.getInstance().getNormalKeyDataByPosition(mPosition);
            fillData();
            typedArray.recycle();
        }

    }

    private void fillData() {
        if (mData != null) {
            tvPrimaryLetter.setText(mData.getPrimaryLetter().getName());
            tvSecondaryLetter.setText(mData.getSecondaryLetter().getName());
            tvPrimaryNumber.setText(mData.getPrimaryNubmer().getName());
            tvSecondaryNumber.setText(mData.getSecondaryNubmer().getName());
            tvPrimarySymbol.setText(mData.getPrimarySymbol().getName());
            tvSecondarySymbol.setText(mData.getSecondarySymbol().getName());
            tvFunction.setText(mData.getFunction().getName());
            tvSpecial.setText(mData.getSpecial().getName());
        }
    }

    @Override
    public String toString() {
        return TAG + ":" + mPosition;
    }
}
