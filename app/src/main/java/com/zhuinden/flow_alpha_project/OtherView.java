package com.zhuinden.flow_alpha_project;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.LinearLayout;

import flow.Flow;

/**
 * Created by Zhuinden on 2016.03.02..
 */
public class OtherView extends LinearLayout {
    private static final String TAG = "OtherView";

    public OtherView(Context context) {
        super(context);
    }

    public OtherView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public OtherView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(21)
    public OtherView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if(!isInEditMode()) {
            MainActivity.OtherKey otherKey = Flow.getKey(this);
            Log.i(TAG, "Obtained key: " + otherKey);
        }
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Log.i(TAG, "Saving view state");
        return super.onSaveInstanceState();
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        Log.i(TAG, "Restoring view state");
        super.onRestoreInstanceState(state);
    }
}
