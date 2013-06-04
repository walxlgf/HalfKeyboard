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
 *
 */
public class SymbolKeyView extends BaseKeyView {
	private final static String TAG = "SymbolKeyView";
	private Context mContext;
    private ImageView ivSymbolLock;
    private ImageView ivTempSymbol;
    private boolean isSymbolLock;
    private boolean isTempSymbol;

    public void setTempSymbol(boolean tempSymbol) {
        isTempSymbol = tempSymbol;
    }

    public void setSymbolLock(boolean symbolLock) {
        isSymbolLock = symbolLock;
    }

    public void refresh(){
        ivSymbolLock.setImageResource(R.drawable.right_normal);
        ivTempSymbol.setImageResource(R.drawable.right_normal);
        if(isSymbolLock){
            ivSymbolLock.setImageResource(R.drawable.right_blink);
        }
        if(isTempSymbol){
            ivTempSymbol.setImageResource(R.drawable.right_blink);
        }
    }

    public SymbolKeyView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		init();
	}

	public SymbolKeyView(Context context) {
		super(context);
		mContext = context;
		init();
	}

	private void init() {
        if(mOrientation == LEFT){
            LayoutInflater.from(mContext).inflate(R.layout.widget_symbol_key_left, this);
        }else{
            LayoutInflater.from(mContext).inflate(R.layout.widget_symbol_key_right, this);
        }
        ivSymbolLock = (ImageView)findViewById(R.id.iv_symbol_lock);
        ivTempSymbol = (ImageView)findViewById(R.id.iv_temp_symbol);
	}
}
