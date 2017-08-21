package com.taraspelypets.awsomecalendar;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.FrameLayout;

/**
 * Created by Taras on 11.08.2017.
 */

public class ExpendableView extends FrameLayout {

    private boolean isCreated;

    private int maxHeight;
    private int minHeight;
    private int currentHeight = 0;



    private GestureDetector mDetector = new GestureDetector(getContext(), new GestureListener());


    public ExpendableView(@NonNull Context context) {
        super(context);
    }

    public ExpendableView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ExpendableView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init(){
        if(isCreated) return;
        isCreated = true;

        maxHeight = getHeight();
        minHeight = 100;
        currentHeight = maxHeight;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        init();
        super.onDraw(canvas);
    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mDetector.onTouchEvent(event);

        return true;
    }

    private class GestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {

            currentHeight -= (int)distanceY;
            if(currentHeight > maxHeight){
                currentHeight = maxHeight;

            } else if(currentHeight < minHeight){
                currentHeight = minHeight;
            }

            getLayoutParams().height = currentHeight;
            requestLayout();

            return super.onScroll(e1, e2, distanceX, distanceY);
        }
    }

    public int getMaxHeight() {
        return maxHeight;
    }

    public void setMaxHeight(int maxHeight) {
        this.maxHeight = maxHeight;
        if(currentHeight > maxHeight){
            currentHeight = maxHeight;
        }
        requestLayout();

    }

    public int getMinHeight() {
        return minHeight;
    }

    public void setMinHeight(int minHeight) {
        this.minHeight = minHeight;
        if(currentHeight < minHeight){
            currentHeight = minHeight;
        }
        requestLayout();
    }

    public void expend(){

    }
}
