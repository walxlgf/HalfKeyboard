/**
 *
 */
package com.half.keyboard.fragment;

import android.content.*;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.half.keyboard.R;
import com.half.keyboard.service.Service;
import com.half.keyboard.vo.KeyData;
import com.half.keyboard.vo.KeyDatas;
import com.half.keyboard.vo.KeyValue;
import com.half.keyboard.widget.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KeyboardFragment extends Fragment implements OnTouchListener {
    private String TAG = "KeyboardFragment";
    public static final String KEY_ORIENTATION = "KEY_ORIENTATION";
    private final static int LEFT = 2;
    private final static int RIGHT = 1;

    private NormalKeyView key0;
    private NormalKeyView key1;
    private NormalKeyView key2;
    private NormalKeyView key3;
    private NormalKeyView key4;
    private NormalKeyView key5;
    private NormalKeyView key6;
    private NormalKeyView key7;
    private NormalKeyView key8;
    private NormalKeyView key9;
    private NormalKeyView key10;
    private NormalKeyView key11;
    private NormalKeyView key12;
    private NormalKeyView key13;
    private NormalKeyView key14;
    private SpaceKeyView keySpace;
    private NumberKeyView keyNumber;
    private SymbolKeyView keySymbol;
    private EnterKeyView keyEnter;
    private ShiftKeyView keyShift;
    private DeleteKeyView keyDelete;
    private BkspcKeyView keyBkSpc;

    private ImageView ivCtrlLock;
    private ImageView ivAltLock;
    private ImageView ivWinLock;
    private ImageView ivCapLock;
    private ImageView ivNumberLock;
    private Button btnChange;

    private FrameLayout flKeyboard;

    private View rootView;
    private Service mService;
    private int mOrientation;

    private LayoutInflater mInflater;


    /**
     * 各个触控点的最近的view
     * key为pointerId
     * value为这个pointer最近所在的view
     */
    private Map<Integer, BaseKeyView> mRecentTouchedMap = new HashMap<Integer, BaseKeyView>();

    /**
     * 各个触控点的ACTION_DOWN时的view
     * 用于一个手指滑过多个按键时的判断
     * key为pointerId
     * value为这个pointer在ACTION_DOWN时的view
     */
    private Map<Integer, BaseKeyView> mDownMap = new HashMap<Integer, BaseKeyView>();

    /**
     * 存放没有ACTION_DOWN,只有ACTION_MOVE的按键的map
     * 用于一个手指滑过多个按键时的判断
     * key为pointerId
     * value为这个pointer在down时的view
     */
    private Map<Integer, BaseKeyView> mNoDownList = new HashMap<Integer, BaseKeyView>();

    /**
     * 长按时普通按键异步任务map
     * 异步任务主要用于长按时不停输出字符.此map的主要功能是在用户在ActionUp或滑动离开此按建时,结束对应的异步任务
     */
//    private Map<NormalKeyView, NormalKeyAsyncTask> mNormalKeyAsyncTasks = new HashMap<NormalKeyView, KeyboardFragment.NormalKeyAsyncTask>();

    /**
     * 长按时普通按键线程map
     * 异步任务主要用于长按时不停输出字符.此map的主要功能是在用户在ActionUp或滑动离开此按建时,结束对应的异步任务
     */
    private Map<NormalKeyView, NormalKeyThread> mNormalKeyThreads = new HashMap<NormalKeyView, NormalKeyThread>();

    /**
     * BackSpace的异步任务
     * 异步任务主要用于长按时不停输出"backSpace"
     */
    private BkSpcKeyThread mBkSpcKeyAsyncTask;
    /**
     * Delete的异步任务
     * 异步任务主要用于长按时不停输出"delete"
     */
    private DeleteKeyThread mDeleteKeyAsyncTask;
    /**
     * Space的异步任务
     * 异步任务主要有2:
     * 1.当有其它的普通键按下时,保持isSpace为true,用于输出centerBottomRight的值
     * 2.当没其它普通键按下时,用于长按时不停输出"Space"
     */
    private SpaceKeyThread mSpaceKeyAsyncTask;

    /**
     * 是否按住Space
     * 1.为true时,当有其它的普通键按下时,输出按键centerBottomRight的值
     * 2.为false,说明space没有按下,输出按键centerTopRight的值
     */
    private boolean isSpacePressed;

    /**
     * 是否处于临时shif状态,模式下,临时Shift指示灯会亮,输入的下一字符会是大写字符,输入之后,临时Shift模式将会取消.
     */
    private boolean isTempShift;

    /**
     * 是否按下并保持着Shift键,
     * 按下并保持Shift键,之后按下一串字符键,会生成一连串大字字符.
     */
    private boolean isShift;

    /**
     * 是否按下了Shift按键
     * 不管按多久,只要一按下,此值就为true,一释放此值就为false,主要用于判断是否与Number或Space同时按下
     */
    private boolean isShiftDown;

    /**
     * 是否CapLock
     */
    private boolean isCapLock;
    /**
     * 是否ShiftLock
     */
    private boolean isShiftLock;

    /**
     * 处理shift按键
     * 如果按住不到指定的时间,如200毫秒就释放,即认为只是开关,设置临时shift模式
     * 如果按住时间超过了指定的时间,即认为是长按,此时可以按一连串的字母键输入大写字母
     * 同时按下Shift和Space是ShiftLock开关
     * 同时按下Shift和Number是CapLock的开关
     */
    private ShiftKeyThread mShiftAsyncTask;

    /**
     * 是否处于Symbol模式
     * 在Symbol模式,可以输入centerTopLeft的符号值,同时按下Space及字母键,输入centerBottomLeft的符号值
     */
    private boolean isSymbolLock;

    /**
     * 是否处于临时Symbol模式
     * 在Symbol模式,可以输入centerTopLeft的符号值,同时按下Space及字母键,输入centerBottomLeft的符号值
     */
    private boolean isTempSymbol;

    /**
     * 是否按下了Symbol按键
     * 不管按多久,只要一按下,此值就为true,一释放此值就为false,主要用于判断是否与Number或Space同时按下
     */
    private boolean isSymbolDown;

    /**
     * 处理Symbol按键
     * 按下并释放Symbol切换到Symbol模式,再次按下并释放Symbol释放Symbol模式
     * 在Symbol模式,可以输入centerTopLeft的符号值,同时按下Space及字母键,输入centerBottomLeft的符号值
     * 同时按下Symbol和Space输入逗号,不管是否处于Symbol模式,长按重复输入
     */
    private SymbolKeyThread mSymbolAsyncTask;

    /**
     * 是否处于KeypadNumber模式
     * 在KeypadNumber模式,输入bottomRight的数字值,相对于小键盘的数字键
     * 同时按下Space及字母键,输入F1到F12,
     */
    private boolean isKeypadNumberLock;

    /**
     * 是否处于KeyboardNumber模式
     * 在KeyboardNumber模式,输入bottomRight的数字值,相对于键盘的主键区的数字键
     * 同时按下Space及字母键,输入F1到F12等功能按键
     */
    private boolean isKeyboardNumberLock;

    /**
     * 是否按下了Number按键
     * 不管按多久,只要一按下,此值就为true,一释放此值就为false,主要用于判断是否与Space同时按下
     */
    private boolean isNumberDown;

    /**
     * 是否按住Number,按住时,当有其它的普通键按下时,输出按键bottomLeft的值
     */
    private boolean isNumberPressed;

    /**
     * 处理Number按键
     * 按下并释放Number切换到KeypadNumber模式,再次按下并释放Number释放KeypadNumber模式
     * 在KeypadNumber模式,输入bottomRight的数字值,相对于小键盘的数字键,同时按下Space及字母键,输入F1到F12.
     * 同时按下Number和Space键并释放切换到KeyboardNumber模式,再次同时Number和Space键并释放,取消KeyboardNumber模式
     * 在KeyboardNumber模式,输入bottomRight的数字值,相对于键盘的主键区的数字键,同时按下Space及字母键,输入F1到F12等功能按键
     * 同时按下Number键和字母键,输入bottomLeft的功能键如(delete Ctrl alt等)
     */
    private NumberKeyThread mNumberAsyncTask;

    /**
     * 是否按下了Enter按键
     * 不管按多久,只要一按下,此值就为true,一释放此值就为false,主要用于判断是否与Space同时按下
     */
    private boolean isEnterDown;

    /**
     * 处理Enter按键
     * 按下并释放Enter输入回车
     * 同时按下Number和Enter键输入句号
     */
    private EnterKeyThread mEnterAsyncTask;

    /**
     * 是否处于Alt,为true Alt灯会亮
     */
    private boolean isAlt;

    /**
     * 是否处于Ctrl,为true Ctrl灯会亮
     */
    private boolean isCtrl;

    /**
     * 是否处于Win,为true Win灯会亮
     */
    private boolean isWin;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(getActivity(), Service.class);
        getActivity().bindService(intent, conn, Context.BIND_AUTO_CREATE);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mInflater = inflater;
        rootView = inflater.inflate(R.layout.fragment_keys, null);
        btnChange = (Button) rootView.findViewById(R.id.btn_change);
        flKeyboard = (FrameLayout) rootView.findViewById(R.id.fl_key_board);
        btnChange.setOnClickListener(changeClickListener);
        initKeyboard();
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated:" + System.currentTimeMillis());
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart:" + System.currentTimeMillis());
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume:" + System.currentTimeMillis());
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause:" + System.currentTimeMillis());
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "onViewCreated:" + System.currentTimeMillis());
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop:" + System.currentTimeMillis());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unbindService(conn);
    }

    private void initKeyboard() {

        Log.d(TAG, "initKeyboard:s:" + System.currentTimeMillis());
        if (flKeyboard.getChildCount() > 0) {
            flKeyboard.removeAllViews();
            flKeyboard.setOnClickListener(null);
        }


        SharedPreferences sp = getActivity().getSharedPreferences(getActivity().getPackageName(), 0);
        mOrientation = sp.getInt(KEY_ORIENTATION, LEFT);

        if (mOrientation == LEFT) {
            btnChange.setText(R.string.switch_to_right);
            mInflater.inflate(R.layout.widget_keys_left, flKeyboard);
        } else {
            btnChange.setText(R.string.switch_to_left);
            mInflater.inflate(R.layout.widget_keys_right, flKeyboard);
        }
        key0 = (NormalKeyView) flKeyboard.findViewById(R.id.key_0);
        key1 = (NormalKeyView) flKeyboard.findViewById(R.id.key_1);
        key2 = (NormalKeyView) flKeyboard.findViewById(R.id.key_2);
        key3 = (NormalKeyView) flKeyboard.findViewById(R.id.key_3);
        key4 = (NormalKeyView) flKeyboard.findViewById(R.id.key_4);
        key5 = (NormalKeyView) flKeyboard.findViewById(R.id.key_5);
        key6 = (NormalKeyView) flKeyboard.findViewById(R.id.key_6);
        key7 = (NormalKeyView) flKeyboard.findViewById(R.id.key_7);
        key8 = (NormalKeyView) flKeyboard.findViewById(R.id.key_8);
        key9 = (NormalKeyView) flKeyboard.findViewById(R.id.key_9);
        key10 = (NormalKeyView) flKeyboard.findViewById(R.id.key_10);
        key11 = (NormalKeyView) flKeyboard.findViewById(R.id.key_11);
        key12 = (NormalKeyView) flKeyboard.findViewById(R.id.key_12);
        key13 = (NormalKeyView) flKeyboard.findViewById(R.id.key_13);
        key14 = (NormalKeyView) flKeyboard.findViewById(R.id.key_14);

        keySpace = (SpaceKeyView) flKeyboard.findViewById(R.id.key_space);
        keyNumber = (NumberKeyView) flKeyboard.findViewById(R.id.key_number);
        keySymbol = (SymbolKeyView) flKeyboard.findViewById(R.id.key_symbol);
        keyEnter = (EnterKeyView) flKeyboard.findViewById(R.id.key_enter);
        keyShift = (ShiftKeyView) flKeyboard.findViewById(R.id.key_shift);
        keyDelete = (DeleteKeyView) flKeyboard.findViewById(R.id.key_delete);
        keyBkSpc = (BkspcKeyView) flKeyboard.findViewById(R.id.key_bkspc);

        ivCtrlLock = (ImageView) flKeyboard.findViewById(R.id.iv_ctrl_lock);
        ivAltLock = (ImageView) flKeyboard.findViewById(R.id.iv_alt_lock);
        ivWinLock = (ImageView) flKeyboard.findViewById(R.id.iv_win_lock);
        ivNumberLock = (ImageView) flKeyboard.findViewById(R.id.iv_number_lock);
        ivCapLock = (ImageView) flKeyboard.findViewById(R.id.iv_cap_lock);


        flKeyboard.setOnTouchListener(this);
        Log.d(TAG, "initKeyboard:e:" + System.currentTimeMillis());
    }

    ServiceConnection conn = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Service.Binder binder = (Service.Binder) service;
            if (binder != null) {
                mService = binder.getService();
            }
        }
    };


    View.OnClickListener changeClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (mOrientation == LEFT) {
                mOrientation = RIGHT;
            } else {
                mOrientation = LEFT;
            }
            SharedPreferences sp = getActivity().getSharedPreferences(getActivity().getPackageName(), 0);
            SharedPreferences.Editor editor = sp.edit();
            editor.putInt(KEY_ORIENTATION, mOrientation);
            editor.commit();
            initKeyboard();
        }
    };

    @Override
    public boolean onTouch(final View v, final MotionEvent event) {
        //获取当前pointer的index
        int actionIndex = event.getActionIndex();

        //获取当前action
        int action = event.getActionMasked();
        if (action < 7 && action > 4) {
            action = action - 5;
        }
        if (action == MotionEvent.ACTION_MOVE) {
            for (int i = 0; i < event.getPointerCount(); i++) {
                dealEvent(i, event, v, action);
            }
        } else {
            dealEvent(actionIndex, event, v, action);
        }
        return true;
    }

    private void dealEvent(final int pointerIndex, final MotionEvent event, final View v, final int action) {
        //获取相于屏幕的绝对位置
        int rawX;
        int rawY;
        final int location[] = {0, 0};
        v.getLocationOnScreen(location);
        rawX = (int) event.getX(pointerIndex) + location[0];
        rawY = (int) event.getY(pointerIndex) + location[1];

        //获取
        int pointerId = event.getPointerId(pointerIndex);
        //获取当前pointer所在view
        BaseKeyView curTouchedView = getTouchedView(rawX, rawY);

        if (curTouchedView == null) {
            //如果当前view为空,且有按下view,说明已经move出边界,
            if (mDownMap.containsKey(pointerId)) {
                Log.d(TAG, "dealUp null mDownMap:" + getKeyName(mDownMap.get(pointerId)));
                dealUp(mDownMap.get(pointerId));
                // && mDownMap.get(pointerId).equals(mRecentTouchedMap.get(pointerId))
                if (mRecentTouchedMap.containsKey(pointerId) && mDownMap.get(pointerId).equals(mRecentTouchedMap.get(pointerId))) {
                    mRecentTouchedMap.remove(pointerId);
                }
                // && mDownMap.get(pointerId).equals(mNoDownList.get(pointerId))
                if (mNoDownList.containsKey(pointerId) && mDownMap.get(pointerId).equals(mNoDownList.get(pointerId))) {
                    Log.d(TAG, "dealUp null mDownMap mNoDownList:" + getKeyName(mNoDownList.get(pointerId)));
                    mNoDownList.remove(pointerId);
                }

                mDownMap.remove(pointerId);
            }
            //如果当前view为空,且有最近view,说明已经move出边界
            else if (mRecentTouchedMap.containsKey(pointerId)) {
                Log.d(TAG, "dealUp  null  mRecentTouchedMap:" + getKeyName(mRecentTouchedMap.get(pointerId)));
                dealUp(mRecentTouchedMap.get(pointerId));
                //&& mRecentTouchedMap.get(pointerId).equals(mNoDownList.get(pointerId))
                if (mNoDownList.containsKey(pointerId) && mRecentTouchedMap.get(pointerId).equals(mNoDownList.get(pointerId))) {
                    Log.d(TAG, "dealUp null mRecentTouchedMap mNoDownList:" + getKeyName(mNoDownList.get(pointerId)));
                    mNoDownList.remove(pointerId);
                }

                mRecentTouchedMap.remove(pointerId);
            } else if (mNoDownList.containsKey(pointerId)) {
                Log.d(TAG, "dealUp  null  mNoDownList:" + getKeyName(mNoDownList.get(pointerId)));
                dealUp(mNoDownList.get(pointerId));
                mNoDownList.remove(pointerId);
            } else {
                Log.d(TAG, "null else");
            }
        } else {
            switch (action) {
                case MotionEvent.ACTION_DOWN: {
                    //如果是down的action,记录下来
                    mDownMap.put(pointerId, curTouchedView);
                    dealDown(curTouchedView);
                    Log.d(TAG, "down:" + getKeyName(curTouchedView));
                    //mRecentTouchedMap.put(pointerId, curTouchedView);
                }
                break;
                case MotionEvent.ACTION_MOVE: {
                    //down
                    if (mDownMap.containsKey(pointerId)) {
                        //如果之前的记录的down的view中和当前的view不一致,说明是从其它位置move过来的,要把界面显示为按下的界面
                        if (!curTouchedView.equals(mDownMap.get(pointerId))) {
                            if (!mNoDownList.containsKey(pointerId) || !curTouchedView.equals(mNoDownList.get(pointerId))) {
                                Log.d(TAG, "down mDownMap containsKey:" + pointerId + getKeyName(curTouchedView));
                                dealDown(curTouchedView);
                                mNoDownList.put(pointerId, curTouchedView);
                            }
                        }
                    } else {
                        if (!mNoDownList.containsKey(pointerId) || !curTouchedView.equals(mNoDownList.get(pointerId))) {
                            Log.d(TAG, "down mDownMap !containsKey:" + pointerId + getKeyName(curTouchedView));
                            dealDown(curTouchedView);
                            mNoDownList.put(pointerId, curTouchedView);
                        }
                    }

                    //up
                    if (mRecentTouchedMap.containsKey(pointerId)) {
                        //如果之前记录的最近view和当前view不一样,说明已经move出边界
                        if (!curTouchedView.equals(mRecentTouchedMap.get(pointerId))) {
                            Log.d(TAG, "dealUp mRecentTouchedMap:" + getKeyName(mRecentTouchedMap.get(pointerId)));
                            dealUp(mRecentTouchedMap.get(pointerId));
                            if (mDownMap.containsKey(pointerId) && mRecentTouchedMap.get(pointerId).equals(mDownMap.get(pointerId))) {
                                mDownMap.remove(pointerId);
                            }

                            if (mNoDownList.containsKey(pointerId) && mRecentTouchedMap.get(pointerId).equals(mNoDownList.get(pointerId))) {
                                Log.d(TAG, "dealUp mRecentTouchedMap mNoDownList:" + getKeyName(mNoDownList.get(pointerId)));
                                mNoDownList.remove(pointerId);
                            }

                            mRecentTouchedMap.remove(pointerId);

                            mRecentTouchedMap.put(pointerId, curTouchedView);
                        }
                    } else {
                        mRecentTouchedMap.put(pointerId, curTouchedView);
                    }
                }
                break;
                case MotionEvent.ACTION_UP: {
                    //发是up的话,清空相关的
                    dealUp(curTouchedView);
                    Log.d(TAG, "dealUp:" + getKeyName(curTouchedView));
                    if (mDownMap.containsKey(pointerId) && curTouchedView.equals(mDownMap.get(pointerId))) {
                        mDownMap.remove(pointerId);
                    }
                    if (mRecentTouchedMap.containsKey(pointerId) && curTouchedView.equals(mRecentTouchedMap.get(pointerId))) {
                        mRecentTouchedMap.remove(pointerId);
                    }
                    if (mNoDownList.containsKey(pointerId) && curTouchedView.equals(mNoDownList.get(pointerId))) {
                        Log.d(TAG, "dealUp mNoDownList:" + getKeyName(mNoDownList.get(pointerId)));
                        mNoDownList.remove(pointerId);
                    }
                }
                break;

                default:
                    break;
            }
        }
    }

    private String getKeyName(BaseKeyView baseKeyView) {
        String name = "";
        if (baseKeyView instanceof NormalKeyView) {
            name = ((NormalKeyView) baseKeyView).getData().getPrimaryLetter().getName();
        } else {
            name = baseKeyView.getClass().getSimpleName();
        }
        return name;
    }

    /**
     * 获取当前MotionEvent下的按钮
     *
     * @param x
     * @param y
     * @return
     */
    private BaseKeyView getTouchedView(final int x, final int y) {
        BaseKeyView baseKeyView = null;
        final ArrayList<View> possibleViews = new ArrayList<View>();
        if (rootView instanceof ViewGroup) {
            possibleViews.add(rootView);
            for (int i = 0; i < possibleViews.size(); i++) {
                final View view = possibleViews.get(i);
                final int location[] = {0, 0};
                view.getLocationOnScreen(location);
                if (view.getHeight() + location[1] >= y && location[1] <= y && view.getWidth() + location[0] >= x && location[0] <= x) {
                    if (view instanceof BaseKeyView) {
                        baseKeyView = (BaseKeyView) view;
                        break;
                    } else if (view instanceof ViewGroup) {
                        possibleViews.addAll(getChildViews(view));
                    }
                }
            }
        }
        return baseKeyView;
    }

    /**
     * 获取子控件
     *
     * @param view
     * @return
     */
    private ArrayList<View> getChildViews(final View view) {
        final ArrayList<View> views = new ArrayList<View>();
        if (view instanceof ViewGroup) {
            final ViewGroup v = ((ViewGroup) view);
            if (v.getChildCount() > 0) {
                for (int i = 0; i < v.getChildCount(); i++) {
                    views.add(v.getChildAt(i));
                }
            }
        }
        return views;
    }

    /**
     * 按键的按下操作,包括从其它按键move过来.
     * @param keyView
     */
    private void dealDown(BaseKeyView keyView) {
        keyView.setPressed();
        if (keyView instanceof NormalKeyView) {
            NormalKeyView normalKeyView = ((NormalKeyView) keyView);
            if(mNormalKeyThreads.containsKey(keyView)){
                NormalKeyThread keyThread = mNormalKeyThreads.get(((NormalKeyView)keyView));
                keyThread.interrupt();
                mNormalKeyThreads.remove(keyView);
            }
            NormalKeyThread keyThread = new NormalKeyThread(normalKeyView);
            mNormalKeyThreads.put(normalKeyView, keyThread);
            keyThread.start();
        } else if (keyView instanceof BkspcKeyView) {
            if(mBkSpcKeyAsyncTask != null){
                mBkSpcKeyAsyncTask.interrupt();
            }
            mBkSpcKeyAsyncTask = new BkSpcKeyThread(keyView);
            mBkSpcKeyAsyncTask.start();
        } else if (keyView instanceof DeleteKeyView) {
            if(mDeleteKeyAsyncTask != null){
                mDeleteKeyAsyncTask.interrupt();
            }
            mDeleteKeyAsyncTask = new DeleteKeyThread(keyView);
            mDeleteKeyAsyncTask.start();
        } else if (keyView instanceof SpaceKeyView) {
            if(mSpaceKeyAsyncTask != null){
                mSpaceKeyAsyncTask.interrupt();
            }
            mSpaceKeyAsyncTask = new SpaceKeyThread(keyView);
            mSpaceKeyAsyncTask.start();
        } else if (keyView instanceof ShiftKeyView) {
            if(mShiftAsyncTask != null){
                mShiftAsyncTask.interrupt();
            }
            mShiftAsyncTask = new ShiftKeyThread(keyView);
            mShiftAsyncTask.start();
        } else if (keyView instanceof SymbolKeyView) {
            if(mSymbolAsyncTask != null){
                mSymbolAsyncTask.interrupt();
            }
            mSymbolAsyncTask = new SymbolKeyThread(keyView);
            mSymbolAsyncTask.start();
        } else if (keyView instanceof NumberKeyView) {
            if(mNumberAsyncTask != null){
                mNumberAsyncTask.interrupt();
            }
            mNumberAsyncTask = new NumberKeyThread(keyView);
            mNumberAsyncTask.start();
        } else if (keyView instanceof EnterKeyView) {
            if(mEnterAsyncTask != null){
                mEnterAsyncTask.interrupt();
            }
            mEnterAsyncTask = new EnterKeyThread(keyView);
            mEnterAsyncTask.start();
        }
    }

    /**
     * 按键up 或 已经move出当前按键
     * @param keyView
     */
    private void dealUp(BaseKeyView keyView) {
        keyView.setNormal();
        if (keyView instanceof NormalKeyView) {
            NormalKeyView normalKeyView = ((NormalKeyView) keyView);
            if (mNormalKeyThreads.containsKey(normalKeyView)) {
                NormalKeyThread normalKeyThread = mNormalKeyThreads.get(normalKeyView);
                normalKeyThread.interrupt();
                mNormalKeyThreads.remove(normalKeyView);
            }
        } else if (keyView instanceof BkspcKeyView) {
            if (mBkSpcKeyAsyncTask != null) {
                mBkSpcKeyAsyncTask.interrupt();
            }
        } else if (keyView instanceof DeleteKeyView) {
            if (mDeleteKeyAsyncTask != null) {
                mDeleteKeyAsyncTask.interrupt();
            }
        } else if (keyView instanceof SpaceKeyView) {
            if (mSpaceKeyAsyncTask != null) {
                mSpaceKeyAsyncTask.interrupt();
            }
        } else if (keyView instanceof ShiftKeyView) {
            if (mShiftAsyncTask != null) {
                mShiftAsyncTask.interrupt();
            }
        } else if (keyView instanceof SymbolKeyView) {
            if (mSymbolAsyncTask != null) {
                mSymbolAsyncTask.interrupt();
            }
        } else if (keyView instanceof NumberKeyView) {
            if (mNumberAsyncTask != null) {
                mNumberAsyncTask.interrupt();
            }
        } else if (keyView instanceof EnterKeyView) {
            if (mEnterAsyncTask != null) {
                mEnterAsyncTask.interrupt();
            }
        }


    }

    /**
     * 用于替代异步任务
     */
    abstract class KeyThread extends Thread {
        BaseKeyView baseKeyView;

        public KeyThread(BaseKeyView keyView) {
            super();
            setName(keyView.toString());
            baseKeyView = keyView;
        }

        @Override
        public void run() {
            super.run();
            while (!isInterrupted()) {
                try {
                    Thread.sleep(200);
                    Message msg = new Message();
                    msg.obj = baseKeyView;
                    msg.arg1 = 1;
                    keyHandler.sendMessage(msg);
                } catch (InterruptedException e) {

                    Message msg = new Message();
                    msg.obj = baseKeyView;
                    msg.arg1 = 2;
                    keyHandler.sendMessage(msg);
                    return;
                }
            }
        }

        protected abstract void progressUpdate(BaseKeyView keyView);

        protected abstract void interrupted1(BaseKeyView keyView);

        Handler keyHandler = new Handler(new Handler.Callback() {

            @Override
            public boolean handleMessage(Message msg) {
                if (msg.arg1 == 1) {
                    progressUpdate(baseKeyView);
                } else if (msg.arg1 == 2) {
                    interrupted1(baseKeyView);
                }
                return true;
            }
        });
    }


    /**
     * 处于普通模式时,根据是否同时按下并释放了Space键,输入小写PrimaryLetter还是小写SecondaryLetter.长按重复输入.
     * 处于普通模式并同时处于TempShift,ShiftLock,CapLock,Shift(按住Shift)键时,会输出大写字母.
     * 在SymbolLk时,根据是否同时按下并释放了Space键,确定输出PrimarySymbol还是SecondarySymbol.长按重复输入.
     * 在KpNumLk或KbNumLk时,根据是否同时按下并释放了Space键,确定输出PrimaryNumber还是SecondaryNumber.长按重复输入.
     * 在处于普通模式时,同时按下并释放Number键,处于Function模式,输入Function按键,长按不重复输入.
     * 在SymbolLk下,同时按下并释放Number键,输入Special按键,长按不重复输入
     */
    class NormalKeyThread extends KeyThread {
        public NormalKeyThread(BaseKeyView keyView) {
            super(keyView);
        }

        @Override
        public void run() {
            while (!isInterrupted()) {
                try {
                    Thread.sleep(200);
                    Message msg = new Message();
                    msg.obj = baseKeyView;
                    msg.arg1 = 1;
                    keyHandler.sendMessage(msg);
                } catch (InterruptedException e) {
                    if (!isSpacePressed ) {
                        Log.d(TAG, "NormalKeyThread :watting:" + getName());
                        try {
                            Thread.sleep(80);
                        } catch (InterruptedException e1) {
                        }
                    }
                    Message msg = new Message();
                    msg.obj = baseKeyView;
                    msg.arg1 = 2;
                    keyHandler.sendMessage(msg);
                    return;
                }
            }
        }

        @Override
        protected void progressUpdate(BaseKeyView keyView) {
            Log.d(TAG, "NormalKeyThread :progressUpdate:" + getName());
            NormalKeyView normalKeyView = (NormalKeyView) keyView;
            if (isAlt || isCtrl || isShiftLock || isWin)
                return;
            KeyValue value = null;
            if (isNumberPressed) {
                //在Symbol模式下,同时按下并释放Number键,输入Special按键,长按不重复输入
                if (isSymbolLock) {
                }
                //在处于普通模式时,同时按下并释放Number键,处于Function模式,输入Function按键,长按不重复输入.
                else {

                }
            } else {
                //在SymbolLk时,根据是否同时按下并释放了Space键,确定输出PrimarySymbol还是SecondarySymbol.长按重复输入.
                if (isSymbolLock) {
                    if (isSpacePressed) {
                        value = normalKeyView.getData().getSecondarySymbol();
                    } else {
                        value = normalKeyView.getData().getPrimarySymbol();
                    }
                }
                //在KpNumLk或KbNumLk时,根据是否同时按下并释放了Space键,确定输出PrimaryNumber还是SecondaryNumber.长按重复输入.
                else if (isKeyboardNumberLock || isKeypadNumberLock) {
                    if (isSpacePressed) {
                        value = normalKeyView.getData().getSecondaryNubmer();
                    } else {
                        value = normalKeyView.getData().getPrimaryNubmer();
                    }
                }
                //在处于普通模式时,根据是否同时按下并释放了Space键,输入小写PrimaryLetter还是小写SecondaryLetter.长按重复输入.
                else {
                    if (normalKeyView.equals(key10)) {

                    } else {
                        if (isSpacePressed) {
                            value = normalKeyView.getData().getSecondaryLetter();
                        } else {
                            value = normalKeyView.getData().getPrimaryLetter();
                        }
                        if (value != null) {
                            //处于普通模式并同时处于TempShift,ShiftLock,CapLock,Shift(按住Shift)键时,会输出大写字母.
                            if (isTempShift || isShift || isCapLock) {
                                value.setShifted(true);
                            } else {
                                value.setShifted(false);
                            }
                        }
                    }
                }
            }
            if (value != null) {
                if (mService != null) {
                    mService.sendKeyData(getKeyDatas(value));
                }
            }

            if (isTempShift) {
                isTempShift = false;
                refreshShiftKeyView();
            }
        }

        @Override
        protected void interrupted1(BaseKeyView keyView) {
            Log.d(TAG, "NormalKeyThread :interrupted1:" + getName());
            NormalKeyView normalKeyView = (NormalKeyView) keyView;
            if (normalKeyView == null)
                return;
            normalKeyView.setMoveTimes(0);
            KeyValue value = null;
            //判断是否发送按键到Host,一般情况都是要发送的.但有些是例外,如ctrl,alt,不需要发送key,而是要维护ctrl和alt的状态
            if (isNumberPressed) {
                //在Symbol模式下,同时按下并释放Number键,输入Special按键,长按不重复输入
                if (isSymbolLock) {
                    value = normalKeyView.getData().getSpecial();
                }
                //在处于普通模式时,同时按下并释放Number键,处于Function模式,输入Function按键,长按不重复输入.
                else {
                    //如果是Ctrl键,需保持Ctrl状态,Ctrl灯亮
                    if (normalKeyView.equals(key6)) {
                        isCtrl = !isCtrl;
                        if (isCtrl) {
                            ivCtrlLock.setImageResource(R.drawable.right_blink);
                        } else {
                            ivCtrlLock.setImageResource(R.drawable.right_normal);
                        }
                    }
                    //如果是Alt键,需保持Alt状态,Alt灯亮
                    else if (normalKeyView.equals(key7)) {
                        isAlt = !isAlt;
                        if (isAlt) {
                            ivAltLock.setImageResource(R.drawable.right_blink);
                        } else {
                            ivAltLock.setImageResource(R.drawable.right_normal);
                        }
                    } else {
                        value = normalKeyView.getData().getFunction();
                    }
                }
            } else {
                //在SymbolLk时,根据是否同时按下并释放了Space键,确定输出PrimarySymbol还是SecondarySymbol.长按重复输入.
                if (isSymbolLock || isTempSymbol) {
                    if (isSpacePressed) {
                        value = normalKeyView.getData().getSecondarySymbol();
                    } else {
                        value = normalKeyView.getData().getPrimarySymbol();
                    }
                }
                //在KpNumLk或KbNumLk时,根据是否同时按下并释放了Space键,确定输出PrimaryNumber还是SecondaryNumber.长按重复输入.
                else if (isKeyboardNumberLock || isKeypadNumberLock) {
                    if (isSpacePressed) {
                        value = normalKeyView.getData().getSecondaryNubmer();
                    } else {
                        value = normalKeyView.getData().getPrimaryNubmer();
                    }
                }
                //在处于普通模式时,根据是否同时按下并释放了Space键,输入小写PrimaryLetter还是小写SecondaryLetter.长按重复输入.
                else {
                    if (isSpacePressed) {
                        //如果是Win键,需保持Win状态,Win灯亮
                        if (normalKeyView.equals(key10)) {
                            isWin = !isWin;
                            if (isWin) {
                                ivWinLock.setImageResource(R.drawable.right_blink);
                            } else {
                                ivWinLock.setImageResource(R.drawable.right_normal);
                            }
                        } else {
                            value = normalKeyView.getData().getSecondaryLetter();
                        }
                    } else {
                        value = normalKeyView.getData().getPrimaryLetter();
                    }
                    if (value != null) {
                        //处于普通模式并同时处于TempShift,ShiftLock,CapLock,Shift(按住Shift)键时,会输出大写字母.
                        if (isTempShift || isShift || isCapLock || isShiftLock) {
                            value.setShifted(true);
                        } else {
                            value.setShifted(false);
                        }
                    }
                }
            }

            if (value != null) {

                if (mService != null) {
                    mService.sendKeyData(getKeyDatas(value));
                    					if (isShiftLock) {
                    						isShiftLock = false;
                    						refreshShiftKeyView();
                    					}
                    if (isCtrl) {
                        isCtrl = false;
                        ivCtrlLock.setImageResource(R.drawable.right_normal);
                    }
                    if (isAlt) {
                        isAlt = false;
                        ivAltLock.setImageResource(R.drawable.right_normal);
                    }
                    if (isWin) {
                        isWin = false;
                        ivWinLock.setImageResource(R.drawable.right_normal);
                    }
                    //					mService.sendKeyInfo(value);
                }
            }

            if (isTempShift) {
                isTempShift = false;
                refreshShiftKeyView();
            }
            if (isTempSymbol) {
                isTempSymbol = false;
                refreshSymbolKeyView();
            }
        }
    }

    private KeyDatas getKeyDatas(KeyValue value) {
        KeyDatas keyDatas = new KeyDatas();
        List<KeyData> list = new ArrayList<KeyData>();
        if (value.isShifted()) {
            //shift press
            list.add(new KeyData(16, KeyData.TYPE_KEY_PRESS));
            //key press
            list.add(new KeyData(value.getCode(), KeyData.TYPE_KEY_PRESS));
            //key release
            list.add(new KeyData(value.getCode(), KeyData.TYPE_KEY_RELEASE));
            //shift press
            list.add(new KeyData(16, KeyData.TYPE_KEY_RELEASE));
        } else if (isWin) {
            //win press
            list.add(new KeyData(524, KeyData.TYPE_KEY_PRESS));
            //key press
            list.add(new KeyData(value.getCode(), KeyData.TYPE_KEY_PRESS));
            //key release
            list.add(new KeyData(value.getCode(), KeyData.TYPE_KEY_RELEASE));
            //win release
            list.add(new KeyData(524, KeyData.TYPE_KEY_RELEASE));
        } else {
            if (isCtrl) {
                //ctrl press
                list.add(new KeyData(17, KeyData.TYPE_KEY_PRESS));
                if (isShiftLock ) {
                    //shift press
                    list.add(new KeyData(16, KeyData.TYPE_KEY_PRESS));
                    if (isAlt) {
                        //alt press
                        list.add(new KeyData(18, KeyData.TYPE_KEY_PRESS));
                        //key press
                        list.add(new KeyData(value.getCode(), KeyData.TYPE_KEY_PRESS));
                        //key release
                        list.add(new KeyData(value.getCode(), KeyData.TYPE_KEY_RELEASE));
                        //alt release
                        list.add(new KeyData(18, KeyData.TYPE_KEY_RELEASE));
                    } else {
                        //key press
                        list.add(new KeyData(value.getCode(), KeyData.TYPE_KEY_PRESS));
                        //key release
                        list.add(new KeyData(value.getCode(), KeyData.TYPE_KEY_RELEASE));
                    }
                    //shift press
                    list.add(new KeyData(16, KeyData.TYPE_KEY_RELEASE));
                } else {
                    if (isAlt) {
                        //alt press
                        list.add(new KeyData(18, KeyData.TYPE_KEY_PRESS));
                        //key press
                        list.add(new KeyData(value.getCode(), KeyData.TYPE_KEY_PRESS));
                        //key release
                        list.add(new KeyData(value.getCode(), KeyData.TYPE_KEY_RELEASE));
                        //alt release
                        list.add(new KeyData(18, KeyData.TYPE_KEY_RELEASE));
                    } else {
                        //key press
                        list.add(new KeyData(value.getCode(), KeyData.TYPE_KEY_PRESS));
                        //key release
                        list.add(new KeyData(value.getCode(), KeyData.TYPE_KEY_RELEASE));
                    }
                    //shift release
                    list.add(new KeyData(16, KeyData.TYPE_KEY_RELEASE));
                }

                //ctrl release
                list.add(new KeyData(17, KeyData.TYPE_KEY_RELEASE));
            } else {
                if (isShiftLock ) {
                    //shift press
                    list.add(new KeyData(16, KeyData.TYPE_KEY_PRESS));
                    if (isAlt) {
                        //alt press
                        list.add(new KeyData(18, KeyData.TYPE_KEY_PRESS));
                        //key press
                        list.add(new KeyData(value.getCode(), KeyData.TYPE_KEY_PRESS));
                        //key release
                        list.add(new KeyData(value.getCode(), KeyData.TYPE_KEY_RELEASE));
                        //alt release
                        list.add(new KeyData(18, KeyData.TYPE_KEY_RELEASE));
                    } else {
                        //key press
                        list.add(new KeyData(value.getCode(), KeyData.TYPE_KEY_PRESS));
                        //key release
                        list.add(new KeyData(value.getCode(), KeyData.TYPE_KEY_RELEASE));
                    }
                    //shift release
                    list.add(new KeyData(16, KeyData.TYPE_KEY_RELEASE));
                } else {
                    if (isAlt) {
                        //alt press
                        list.add(new KeyData(18, KeyData.TYPE_KEY_PRESS));
                        //key press
                        list.add(new KeyData(value.getCode(), KeyData.TYPE_KEY_PRESS));
                        //key release
                        list.add(new KeyData(value.getCode(), KeyData.TYPE_KEY_RELEASE));
                        //alt release
                        list.add(new KeyData(18, KeyData.TYPE_KEY_RELEASE));
                    } else {
                        //key press
                        list.add(new KeyData(value.getCode(), KeyData.TYPE_KEY_PRESS));
                        //key release
                        list.add(new KeyData(value.getCode(), KeyData.TYPE_KEY_RELEASE));
                    }
                }
            }
        }
        keyDatas.setKeyDatas(list);
        return keyDatas;
    }

    /**
     * BackSpace的线程
     * 线程主要用于长按时不停输出"backSpace"
     */
    class BkSpcKeyThread extends KeyThread {
        @Override
        protected void progressUpdate(BaseKeyView keyView) {
            Log.d(TAG, "BkSpcKeyThread :onProgressUpdate");
            KeyValue keyValue = new KeyValue();
            keyValue.setShifted(false);
            keyValue.setCode(8);
            if (mService != null) {
                mService.sendKeyData(getKeyDatas(keyValue));
            }
        }

        @Override
        protected void interrupted1(BaseKeyView keyView) {
            Log.d(TAG, "BkSpcKeyThread :onCancelled");
            KeyValue keyValue = new KeyValue();
            keyValue.setShifted(false);
            keyValue.setCode(8);
            if (mService != null) {
                mService.sendKeyData(getKeyDatas(keyValue));
            }
        }

        public BkSpcKeyThread(BaseKeyView keyView) {
            super(keyView);
        }
    }

    /**
     * Delete的线程
     * 异步任务主要用于长按时不停输出"backSpace"
     */
    class DeleteKeyThread extends KeyThread {
        @Override
        protected void progressUpdate(BaseKeyView keyView) {
            Log.d(TAG, "DeleteKeyThread :progressUpdate");
            KeyValue keyValue = new KeyValue();
            keyValue.setShifted(false);
            keyValue.setCode(127);
            if (mService != null) {
                mService.sendKeyData(getKeyDatas(keyValue));
            }
        }

        @Override
        protected void interrupted1(BaseKeyView keyView) {
            Log.d(TAG, "DeleteKeyThread :interrupted1");
            KeyValue keyValue = new KeyValue();
            keyValue.setShifted(false);
            keyValue.setCode(127);
            if (mService != null) {
                mService.sendKeyData(getKeyDatas(keyValue));
            }
        }

        public DeleteKeyThread(BaseKeyView keyView) {
            super(keyView);
        }
    }

    /**
     * Space的线程
     * 线程的任务主要有如下几点:
     * 当有其它的普通键按下时,保持isSpacePressed为true,用于输出centerBottomRight的值
     * 当没其它普通键按下时,用于长按时不停输出"Space"
     * 同时按下Shift和Space是ShiftLock开关
     * 同时按下Shift和Number是CapLock的开关
     */
    class SpaceKeyThread extends KeyThread {
        int count;

        public SpaceKeyThread(BaseKeyView keyView) {
            super(keyView);
        }

        @Override
        public void run() {
            //判断其它的普通键是否有按下
            //只
            if (!isSpacePressed) {
                if (mNormalKeyThreads.size() > 0 || isShiftDown || isSymbolDown || isNumberDown || isEnterDown) {
                    isSpacePressed = true;
                }
            }

            Log.d(TAG, "SpaceKeyThread :run:isSpacePressed:" + isSpacePressed);
            while (!isInterrupted()) {
                try {
                    Thread.sleep(50);
                    Message msg = new Message();
                    msg.obj = baseKeyView;
                    msg.arg1 = 1;
                    keyHandler.sendMessage(msg);
                    count++;
                } catch (InterruptedException e) {
                    Log.d(TAG, "SpaceKeyThread :run2:isSpacePressed:" + isSpacePressed);

                    //正常情况下,用户同时按下space和其它普通键是要入centerBottomRight的键值,
                    // 如果space先释放了,而普通键还没有释放,这样会输入centerTopRight的值,
                    // 如果其它的普通键有按下,而sapce已经被释放了,就稍等待一下,确保输入的是centerBottomRight

                    //判断其它的普通键是否有按下
                    if (!isSpacePressed) {
                        if (mNormalKeyThreads.size() > 0 || isShiftDown || isSymbolDown || isNumberDown || isEnterDown) {
                            isSpacePressed = true;
                        }
                    }

                    if (isSpacePressed) {
                        Log.d(TAG, "SpaceKeyThread :waitting");
                        try {
                            Thread.sleep(80);
                        } catch (InterruptedException e1) {
                        }
                    }

                    Message msg = new Message();
                    msg.obj = baseKeyView;
                    msg.arg1 = 2;
                    keyHandler.sendMessage(msg);
                    return;
                }
            }
        }

        @Override
        protected void progressUpdate(BaseKeyView keyView) {
            if (!isSpacePressed) {
                if (mNormalKeyThreads.size() > 0 || isShiftDown || isSymbolDown || isNumberDown || isEnterDown) {
                    isSpacePressed = true;
                }
            }
            Log.d(TAG, "SpaceKeyThread :progressUpdate:isSpacePressed:" + isSpacePressed);
            if (!isSpacePressed && count >= 16 && count % 4 == 0) {

                KeyValue keyValue = new KeyValue();
                keyValue.setShifted(false);
                keyValue.setCode(32);
                if (mService != null) {
                    mService.sendKeyData(getKeyDatas(keyValue));
                }
            }
        }

        @Override
        protected void interrupted1(BaseKeyView keyView) {
            Log.d(TAG, "SpaceKeyThread :interrupted1::");
            if (!isSpacePressed && count <= 4) {
                KeyValue keyValue = new KeyValue();
                keyValue.setShifted(false);
                keyValue.setCode(32);
                if (mService != null) {
                    mService.sendKeyData(getKeyDatas(keyValue));
                }
            }

            isSpacePressed = false;
        }
    }

    /**
     * 处理shift按键
     * 如果按住不到指定的时间,如200毫秒就释放,即认为只是开关,设置临时shift模式
     * 如果按住时间超过了指定的时间,即认为是长按,此时可以按一连串的字母键输入大写字母
     * 同时按下Shift和Space是ShiftLock开关,如果处于ShiftLock时,按Shift键无效,只能同时按下Shift和Space释放ShiftLock
     * 同时按下Shift和Number是CapLock的开关,如果处于CapLock时,按Shift键无效,只能同时按下Shift和Number释放CapLock
     */
    class ShiftKeyThread extends KeyThread {
        int count = 0;

        public ShiftKeyThread(BaseKeyView keyView) {
            super(keyView);
            isShiftDown = true;
        }

        @Override
        public void run() {
            while (!isInterrupted()) {
                Log.d(TAG, "ShiftKeyThread:isInterrupted()" + isInterrupted());
                try {
                    Thread.sleep(200);
                    if (++count == 3) {
                        Message msg = new Message();
                        msg.obj = baseKeyView;
                        msg.arg1 = 1;
                        keyHandler.sendMessage(msg);
                    }
                } catch (InterruptedException e) {

                    Message msg = new Message();
                    msg.obj = baseKeyView;
                    msg.arg1 = 2;
                    keyHandler.sendMessage(msg);
                    Log.d(TAG, "ShiftKeyThread:InterruptedException");
                    return;
                }
            }
        }

        @Override
        protected void progressUpdate(BaseKeyView keyView) {
            Log.d(TAG, "ShiftKeyThread :progressUpdate::" + "count:" + count);
            isTempShift = false;
            isShift = true;
            refreshShiftKeyView();
        }

        @Override
        protected void interrupted1(BaseKeyView keyView) {
            Log.d(TAG, "ShiftKeyThread :interrupted1::" + "count:" + count);
            isShiftDown = false;
            if (count <= 2) {
                //同时按下Shift和Space是ShiftLock开关,如果处于ShiftLock时,
                if (isSpacePressed) {
                    if (!isCapLock && !isTempShift) {
                        isShiftLock = !isShiftLock;
                    }
                }
                //同时按下Shift和Number是CapLock的开关,
                else if (isNumberPressed) {
                    if (!isShiftLock && !isTempShift) {
                        isCapLock = !isCapLock;
                    }
                }
                //如果按住不到指定的时间,如200毫秒就释放,即认为只是开关,设置临时shift模式
                else {
                    if (!isShiftLock && !isCapLock) {
                        if (isTempShift) {
                            //如果TempShift按下后,没有按任何键,再按下Shift键,发送ShiftR
                            KeyValue keyValue = new KeyValue();
                            keyValue.setShifted(false);
                            keyValue.setCode(16);
                            if (mService != null) {
                                mService.sendKeyData(getKeyDatas(keyValue));
                            }
                        }
                        isTempShift = !isTempShift;
                    }
                }
            }
            //如果按住时间超过了指定的时间,即认为是长按,此时可以按一连串的字母键输入大写字母
            else {
                isTempShift = false;
            }
            isShift = false;
            refreshShiftKeyView();
        }
    }

    /**
     * 根据状态更新ShiftKeyView
     */
    private void refreshShiftKeyView() {
        keyShift.setTempShift(isTempShift);
        keyShift.setShift(isShift);
        keyShift.setShiftLock(isShiftLock);
        keyShift.setCapLock(isCapLock);
        keyShift.refresh();

        if (isTempShift || isCapLock) {
            ivCapLock.setImageResource(R.drawable.right_blink);
        } else {
            ivCapLock.setImageResource(R.drawable.right_normal);
        }
    }

    /**
     * 处理Symbol按键
     * 按下并释放Symbol切换到Symbol模式,再次按下并释放Symbol释放Symbol模式
     * 在Symbol模式,可以输入centerTopLeft的符号值,同时按下Space及字母键,输入centerBottomLeft的符号值
     * 同时按下Symbol和Space输入逗号,不管是否处于Symbol模式,长按重复输入
     */
    class SymbolKeyThread extends KeyThread {
        public SymbolKeyThread(BaseKeyView keyView) {
            super(keyView);
            isSymbolDown = true;
        }

        @Override
        public void run() {
            while (!isInterrupted()) {
                try {
                    Thread.sleep(200);
                    Message msg = new Message();
                    msg.obj = baseKeyView;
                    msg.arg1 = 1;
                    keyHandler.sendMessage(msg);
                } catch (InterruptedException e) {
                    if (!isSpacePressed || !isNumberPressed) {
                        Log.d(TAG, "SymbolKeyThread :watting:");
                        try {
                            Thread.sleep(80);
                        } catch (InterruptedException e1) {
                        }
                    }
                    Message msg = new Message();
                    msg.obj = baseKeyView;
                    msg.arg1 = 2;
                    keyHandler.sendMessage(msg);
                    return;
                }
            }
        }

        @Override
        protected void progressUpdate(BaseKeyView keyView) {
            Log.d(TAG, "SymbolKeyThread :progressUpdate::");
            if (isSpacePressed) {
                KeyValue keyValue = new KeyValue();
                keyValue.setShifted(false);
                keyValue.setCode(44);
                if (mService != null) {
                    mService.sendKeyData(getKeyDatas(keyValue));
                }
            }
        }

        @Override
        protected void interrupted1(BaseKeyView keyView) {
            Log.d(TAG, "SymbolKeyThread :interrupted1::");
            isSymbolDown = false;
            if (isSpacePressed) {
                KeyValue keyValue = new KeyValue();
                keyValue.setShifted(false);
                keyValue.setCode(44);
                if (mService != null) {
                    mService.sendKeyData(getKeyDatas(keyValue));
                }
            } else if (isNumberPressed) {
                if (!isTempSymbol) {
                    isSymbolLock = !isSymbolLock;
                    refreshSymbolKeyView();
                }
            } else {
                if (!isSymbolLock) {
                    isTempSymbol = !isTempSymbol;
                    refreshSymbolKeyView();
                }
            }
        }
    }


    /**
     * 根据状态更新ShiftKeyView
     */
    private void refreshSymbolKeyView() {
        keySymbol.setSymbolLock(isSymbolLock);
        keySymbol.setTempSymbol(isTempSymbol);
        keySymbol.refresh();
    }

    /**
     * 处理Number按键
     * 按下并释放Number切换到KeypadNumber模式,再次按下并释放Number释放KeypadNumber模式
     * 在KeypadNumber模式,输入bottomRight的数字值,相对于小键盘的数字键,同时按下Space及字母键,输入F1到F12.
     * 同时按下Number和Space键并释放切换到KeyboardNumber模式,再次同时Number和Space键并释放,取消KeyboardNumber模式
     * 在KeyboardNumber模式,输入bottomRight的数字值,相对于键盘的主键区的数字键,同时按下Space及字母键,输入F1到F12等功能按键
     * 同时按下Number键和字母键,输入bottomLeft的功能键如(delete Ctrl alt等)
     */
    class NumberKeyThread extends KeyThread {
        public NumberKeyThread(BaseKeyView keyView) {
            super(keyView);
            isNumberDown = true;
        }

        @Override
        public void run() {

            //判断其它的普通键是否有按下
            //只
            if (!isNumberPressed) {
                if (mNormalKeyThreads.size() > 0 || isShiftDown || isSymbolDown) {
                    isNumberPressed = true;
                }
            }
            while (!isInterrupted()) {
                Log.d(TAG, "NumberKeyThread:isInterrupted()" + isInterrupted());
                try {
                    Thread.sleep(200);
                    Message msg = new Message();
                    msg.obj = baseKeyView;
                    msg.arg1 = 1;
                    keyHandler.sendMessage(msg);
                } catch (InterruptedException e) {
                    Log.d(TAG, "NumberKeyThread:InterruptedException");

                    //正常情况下,用户同时按下Number和其它普通键是要入bottomLeft的键值,
                    // 如果number先释放了,而普通键还没有释放,这样会输入centerTopRight的值,
                    // 如果其它的普通键有按下,而number已经被释放了,就稍等待一下,确保输入的是bottomLeft

                    //判断其它的普通键是否有按下
                    if (!isNumberPressed) {
                        if (mNormalKeyThreads.size() > 0 || isShiftDown || isSymbolDown) {
                            isNumberPressed = true;
                        }
                    }
                    if (isNumberPressed) {
                        Log.d(TAG, "NumberKeyThread :waitting");
                        try {
                            Thread.sleep(80);
                        } catch (InterruptedException e1) {
                        }
                    }

                    Message msg = new Message();
                    msg.obj = baseKeyView;
                    msg.arg1 = 2;
                    keyHandler.sendMessage(msg);
                    return;
                }
            }
        }

        @Override
        protected void progressUpdate(BaseKeyView keyView) {
            if (!isNumberPressed) {
                if (mNormalKeyThreads.size() > 0 || isShiftDown || isSymbolDown) {
                    isNumberPressed = true;
                }
            }
            Log.d(TAG, "NumberKeyThread :progressUpdate:isNumberPressed:" + isNumberPressed);
        }

        @Override
        protected void interrupted1(BaseKeyView keyView) {

            Log.d(TAG, "NumberKeyThread :interrupted1:isNumberPressed:" + isNumberPressed);
            isNumberDown = false;
            if (!isNumberPressed) {
                if (isSpacePressed) {
                    if (!isKeypadNumberLock) {
                        isKeyboardNumberLock = !isKeyboardNumberLock;
                    }
                } else {
                    if (!isKeyboardNumberLock) {
                        isKeypadNumberLock = !isKeypadNumberLock;
                    }
                }
            }
            isNumberPressed = false;
            refreshNumberKeyView();
        }
    }


    /**
     * 根据状态更新ShiftKeyView
     */
    private void refreshNumberKeyView() {
        keyNumber.setKeyboardNumberLock(isKeyboardNumberLock);
        keyNumber.setKeypadNumberLock(isKeypadNumberLock);
        if (isKeypadNumberLock) {
            ivNumberLock.setImageResource(R.drawable.right_blink);
        } else {
            ivNumberLock.setImageResource(R.drawable.right_normal);
        }
        keyNumber.refresh();
    }

    /**
     * 处理Enter按键
     * 按下并释放Enter输入回车
     * 同时按下Number和Enter键输入句号
     */
    class EnterKeyThread extends KeyThread {
        public EnterKeyThread(BaseKeyView keyView) {
            super(keyView);
            isEnterDown = true;
        }

        @Override
        public void run() {
            while (!isInterrupted()) {
                try {
                    Thread.sleep(200);
                    Message msg = new Message();
                    msg.obj = baseKeyView;
                    msg.arg1 = 1;
                    keyHandler.sendMessage(msg);
                } catch (InterruptedException e) {
                    if (!isSpacePressed || !isNumberPressed) {
                        Log.d(TAG, "EnterKeyThread :watting:");
                        try {
                            Thread.sleep(80);
                        } catch (InterruptedException e1) {
                        }
                    }
                    Message msg = new Message();
                    msg.obj = baseKeyView;
                    msg.arg1 = 2;
                    keyHandler.sendMessage(msg);
                    return;
                }
            }
        }

        @Override
        protected void progressUpdate(BaseKeyView keyView) {
            Log.d(TAG, "EnterKeyThread :progressUpdate::");
            KeyValue keyValue = new KeyValue();
            if (isSpacePressed) {
                keyValue.setShifted(false);
                keyValue.setCode(46);
            } else {
                keyValue.setShifted(false);
                keyValue.setCode(10);
            }
            if (mService != null) {
                mService.sendKeyData(getKeyDatas(keyValue));
            }
        }

        @Override
        protected void interrupted1(BaseKeyView keyView) {
            Log.d(TAG, "EnterKeyThread :interrupted1::");
            isEnterDown = false;

            KeyValue keyValue = new KeyValue();
            if (isSpacePressed) {
                keyValue.setShifted(false);
                keyValue.setCode(46);
            } else {
                keyValue.setShifted(false);
                keyValue.setCode(10);
            }
            if (mService != null) {
                mService.sendKeyData(getKeyDatas(keyValue));
            }
        }
    }
}
