package com.snoopy112.backbutton.ui.component;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.snoopy112.backbutton.R;


public class BackBtnView extends FrameLayout {

    private static final String TAG = "BackBtnView";

    private Context mContext;

    private GestureDetectorCompat mDetector;
    private ViewOnTouchListener mOnTouchListener;

    private TextView backButton;

    public BackBtnView(Context context) {
        super(context);
        this.mContext = context;
        initView();
    }

    public BackBtnView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initView();
    }

    public BackBtnView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mContext = context;
        initView();
    }


    @SuppressLint("ClickableViewAccessibility")
    private void initView() {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.button_layout, null);
        this.addView(view);

        mDetector = new GestureDetectorCompat(mContext, new SmallViewOnGestureListener());

        backButton = (TextView) view.findViewById(R.id.back_button);
        backButton.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                mDetector.onTouchEvent(event);
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    backButton.setPressed(true);
                    return true;
                }
//                if (event.getAction() == MotionEvent.ACTION_MOVE) {
//                    if (mOnTouchListener != null) {
//                        int halfWidth = view.getWidth() / 2;
//                        int halfHeight = view.getHeight() / 2;
//                        mOnTouchListener.onDrag(event.getRawX() + halfWidth,
//                                                event.getRawY() + halfHeight);
//                    }
//                    return true;
//                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    backButton.setPressed(false);
                    return true;
                }
                return false;
            }
        });
    }


    class SmallViewOnGestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            Log.d(TAG, "onSingleTap");
            if (mOnTouchListener != null) {
                mOnTouchListener.onBackTap();
                return true;
            } else {
                return false;
            }
        }

        @Override
        public boolean onDoubleTapEvent(MotionEvent e) {
            Log.d(TAG, "onDoubleTap");
            if (mOnTouchListener != null) {
                mOnTouchListener.onHomeTap();
                return true;
            } else {
                return false;
            }
        }

        @Override
        public void onLongPress(MotionEvent e) {
            Log.d(TAG, "onLongPress");
            if (mOnTouchListener != null) {
                mOnTouchListener.onRecentTap();
            }
        }
    }

    public void setmOnTouchListener(ViewOnTouchListener mOnTouchListener) {
        this.mOnTouchListener = mOnTouchListener;
    }

    public interface ViewOnTouchListener {
        void onBackTap();
        void onHomeTap();
        void onRecentTap();
        void onDrag(float x, float y);
    }
}