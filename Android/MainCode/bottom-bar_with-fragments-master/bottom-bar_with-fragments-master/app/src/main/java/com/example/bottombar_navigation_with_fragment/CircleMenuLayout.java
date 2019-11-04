package com.example.bottombar_navigation_with_fragment;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;


public class CircleMenuLayout extends ViewGroup {
    private int mRadius;

    private static final float RADIO_DEFAULT_CHILD_DIMENSION = 1 / 4f;

    private float RADIO_DEFAULT_CENTERITEM_DIMENSION = 1 / 3f;

    private static final float RADIO_PADDING_LAYOUT = 0 / 12f;

    private static final int FLINGABLE_VALUE = 300;

    private static final int NOCLICK_VALUE = 3;

    private int mFlingableValue = FLINGABLE_VALUE;

    private float mPadding;

    private double mStartAngle = 90;

    private String[] mItemTexts;

    private int[] mItemImgs;

    private int mMenuItemCount;

    private float mTmpAngle;

    private long mDownTime;

    private boolean isFling;

    private int mMenuItemLayoutId = R.layout.circle_menu_item;

    private int nowSelPostion = 0;

    public CircleMenuLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        setPadding(0, 0, 0, 0);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int resWidth = 0;
        int resHeight = 0;

        int width = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);

        int height = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        if (widthMode != MeasureSpec.EXACTLY
                || heightMode != MeasureSpec.EXACTLY) {

            resWidth = (int) MainActivity.getInstance().getStaticwidth();
            resWidth = resWidth == 0 ? getDefaultWidth() : resWidth;

            resHeight = resWidth / 2;

            resHeight = resHeight == 0 ? getDefaultWidth() : resHeight;
        } else {

            resWidth = resHeight = Math.min(width, height);
        }


        // 수정///////////////////////////////////////////////////

        setMeasuredDimension(resWidth, resHeight);

        mRadius = Math.max(getMeasuredWidth(), getMeasuredHeight());

        final int count = getChildCount();

        int childSize = (int) (mRadius * RADIO_DEFAULT_CHILD_DIMENSION);

        int childMode = MeasureSpec.EXACTLY;

        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);

            if (child.getVisibility() == GONE) {
                continue;
            }

            int makeMeasureSpec = -1;

            if (child.getId() == R.id.id_circle_menu_item_center) {
                makeMeasureSpec = MeasureSpec.makeMeasureSpec(
                        (int) (mRadius * RADIO_DEFAULT_CENTERITEM_DIMENSION),
                        childMode);
            } else {
                makeMeasureSpec = MeasureSpec.makeMeasureSpec(childSize,
                        childMode);
            }
            child.measure(makeMeasureSpec, makeMeasureSpec);
        }

        mPadding = RADIO_PADDING_LAYOUT * mRadius;

    }

    public interface OnMenuItemClickListener {
        void itemClick(View view, int pos);

        void itemCenterClick(View view);

        void itemSelChange(int pos);
    }

    private OnMenuItemClickListener mOnMenuItemClickListener;

    public void setOnMenuItemClickListener(
            OnMenuItemClickListener mOnMenuItemClickListener) {
        this.mOnMenuItemClickListener = mOnMenuItemClickListener;
    }

    public void setPostion(int postion) {
        if(postion < MainActivity.getInstance().getMenuArray().length)
            mStartAngle =90- (180 / (MainActivity.getInstance().getMenuArray().length ) * postion);
        else
            mStartAngle =90- (180 / (MainActivity.getInstance().getMenuArray().length ) * (postion-MainActivity.getInstance().getMenuArray().length));
        mTmpAngle = 0;
        requestLayout();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int layoutRadius = mRadius;

        final int childCount = getChildCount();

        int left, top;

        int cWidth = (int) (layoutRadius * RADIO_DEFAULT_CHILD_DIMENSION);

        float angleDelay = 360 / (MainActivity.getInstance().getMenuArray().length * 2);

        for (int i = 1; i < childCount; i++) {
            final View child = getChildAt(i);

            if (child.getVisibility() == GONE) {
                continue;
            }

            mStartAngle %= 360;

            float tmp = layoutRadius / 2f - cWidth / 2 - mPadding;

            left = layoutRadius
                    / 2
                    + (int) Math.round(tmp
                    * Math.cos(Math.toRadians(mStartAngle)) - 1 / 2f
                    * cWidth);

            top = layoutRadius
                    / 2
                    + (int) Math.round(tmp
                    * Math.sin(Math.toRadians(mStartAngle)) - 1 / 2f
                    * cWidth);

            // top = item 모형의 top 부분
            child.layout(left , top , left + cWidth, top + cWidth);

            double x = tmp - left;
            double y = tmp - top;
            float jiaodu = 0;
            if (x * y > 0) {
                jiaodu = (float) Math.abs(Math.atan(x / y) * 180 / Math.PI) * -1;
            } else {
                jiaodu = (float) Math.abs(Math.atan(x / y) * 180 / Math.PI);
            }
            if (Math.abs(jiaodu) < (360 / (MainActivity.getInstance().getMenuArray().length * 4))-1){
                child.findViewById(R.id.id_circle_menu_item_text).setBackgroundResource(R.drawable.menuitem);
                if(nowSelPostion != i-1 && nowSelPostion !=i-1-MainActivity.getInstance().getMenuArray().length){
                    nowSelPostion = i-1;
                    if(nowSelPostion >= MainActivity.getInstance().getMenuArray().length)
                        nowSelPostion -= MainActivity.getInstance().getMenuArray().length;
                    mOnMenuItemClickListener.itemSelChange(nowSelPostion);
                }
            }
            else
                child.findViewById(R.id.id_circle_menu_item_text).setBackgroundResource(R.drawable.menuitem);
            // jiaodu 아이템 회전률
            child.setRotation(jiaodu);
            mStartAngle += angleDelay;
        }

        View cView = findViewById(R.id.id_circle_menu_item_center);
        if (cView != null) {
            cView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (mOnMenuItemClickListener != null) {
                        mOnMenuItemClickListener.itemCenterClick(v);
                    }
                }
            });

            int cl = layoutRadius / 2 - cView.getMeasuredWidth() / 2;
            int cr = cl + cView.getMeasuredWidth();
            cView.layout(cl, cl, cr, cr);
        }

    }

    private float mLastX;
    private float mLastY;

    private AutoFlingRunnable mFlingRunnable;

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();


        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                mLastX = x;
                mLastY = y;
                mDownTime = System.currentTimeMillis();
                mTmpAngle = 0;

                if (isFling) {
                    removeCallbacks(mFlingRunnable);
                    isFling = false;
                    return true;
                }

                break;
            case MotionEvent.ACTION_MOVE:

                float start = getAngle(mLastX, mLastY);

                float end = getAngle(x, y);

                if (getQuadrant(x, y) == 1 || getQuadrant(x, y) == 4) {
                    mStartAngle += end - start;
                    mTmpAngle += end - start;
                } else

                {
                    mStartAngle += start - end;
                    mTmpAngle += start - end;
                }

                requestLayout();

                mLastX = x;
                mLastY = y;

                break;
            case MotionEvent.ACTION_UP:

                float anglePerSecond = mTmpAngle * 1000
                        / (System.currentTimeMillis() - mDownTime);

                if (Math.abs(anglePerSecond) > mFlingableValue && !isFling) {
                    post(mFlingRunnable = new AutoFlingRunnable(anglePerSecond));

                    return true;
                }
                if (Math.abs(mTmpAngle) > NOCLICK_VALUE) {
                    return true;
                }

                break;
        }
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }

    private float getAngle(float xTouch, float yTouch) {
        double x = xTouch - (mRadius / 2d);
        double y = yTouch - (mRadius / 2d);
        return (float) (Math.asin(y / Math.hypot(x, y)) * 180 / Math.PI);
    }

    private int getQuadrant(float x, float y) {
        int tmpX = (int) (x - mRadius / 2);
        int tmpY = (int) (y - mRadius / 2);
        if (tmpX >= 0) {
            return tmpY >= 0 ? 4 : 1;
        } else {
            return tmpY >= 0 ? 3 : 2;
        }

    }

    public void setMenuItemIconsAndTexts(String[] texts, int[] drawables) {
        mItemTexts = new String[texts.length * 2];
        mItemImgs = new int[drawables.length * 2];
        for (int i = 0; i < texts.length; i++) {
            mItemTexts[i] = texts[i];
            mItemImgs[i] = drawables[i];
        }
        for (int i = texts.length; i < texts.length * 2; i++) {
            mItemTexts[i] = texts[i - texts.length];
            mItemImgs[i] = drawables[i - texts.length];
        }

        mMenuItemCount = mItemTexts.length;

        addMenuItems();

    }

    public void setMenuItemLayoutId(int mMenuItemLayoutId) {
        this.mMenuItemLayoutId = mMenuItemLayoutId;
    }

    private void addMenuItems() {
        LayoutInflater mInflater = LayoutInflater.from(getContext());
        for (int i = 0; i < mMenuItemCount; i++) {
            final int j = i;
            View view = mInflater.inflate(mMenuItemLayoutId, this, false);
            TextView tv =  view
                    .findViewById(R.id.id_circle_menu_item_text);
            if (tv != null) {
                tv.setVisibility(View.VISIBLE);
                tv.setText(mItemTexts[i]);
            }

            ImageView imageView = view.findViewById(R.id.id_circle_menu_item_img);
            if(imageView != null) {
                imageView.setImageResource(mItemImgs[i]);
            }

            final int finalI = i;
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnMenuItemClickListener.itemClick(view , finalI);
                    setPostion(finalI);
                }
            });
            addView(view);
        }
    }

    public void setFlingableValue(int mFlingableValue) {
        this.mFlingableValue = mFlingableValue;
    }

    public void setPadding(float mPadding) {
        this.mPadding = mPadding;
    }

    private int getDefaultWidth() {
        WindowManager wm = (WindowManager) getContext().getSystemService(
                Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return Math.min(outMetrics.widthPixels, outMetrics.heightPixels);
    }

    private class AutoFlingRunnable implements Runnable {

        private float angelPerSecond;

        public AutoFlingRunnable(float velocity) {
            this.angelPerSecond = velocity;
        }

        public void run() {

            if ((int) Math.abs(angelPerSecond) < 20) {
                isFling = false;
                return;
            }
            isFling = true;

            mStartAngle += (angelPerSecond / 30);

            angelPerSecond /= 1.0666F;
            postDelayed(this, 30);

            requestLayout();
        }
    }

}
