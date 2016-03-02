package com.zhuinden.flow_alpha_project;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import flow.Flow;

/**
 * Created by Zhuinden on 2016.03.02..
 */
public class WelcomeView extends LinearLayout {
    private static final String TAG = "WelcomeView";

    public WelcomeView(Context context) {
        super(context);
        init(context);
    }

    public WelcomeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public WelcomeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(21)
    public WelcomeView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if(!isInEditMode()) {
            MainActivity.WelcomeKey welcomeKey = Flow.getKey(this);
            Log.i(TAG, "Obtained key: " + welcomeKey);
        }
        findViewById(R.id.welcome_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Flow.get(v).set(new MainActivity.OtherKey());
            }
        });
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
