package com.zhuinden.flow_alpha_project;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.Map;

import flow.Direction;
import flow.Flow;
import flow.ForceBundler;
import flow.KeyChanger;
import flow.KeyDispatcher;
import flow.KeyParceler;
import flow.State;
import flow.TraversalCallback;

public class MainActivity
        extends AppCompatActivity {
    static class MainKeyChanger
            extends KeyChanger {
        private static final String TAG = "MainKeyChanger";

        private MainActivity mainActivity;

        MainKeyChanger(MainActivity mainActivity) {
            this.mainActivity = mainActivity;
        }

        @Override
        public void changeKey(State outgoingState, State incomingState, Direction direction, Map<Object, Context> incomingContexts, TraversalCallback callback) {
            Log.i(TAG, "Change Key: [" + outgoingState + "] - [" + incomingState + "]");
            LayoutClassKey layoutClassKey = incomingState.getKey();
            if(outgoingState != null && mainActivity.root.getChildAt(0) != null) {
                Log.i(TAG, "Persisting outgoing state for " + mainActivity.root.getChildAt(0));
                View previousView = mainActivity.root.getChildAt(0);
                outgoingState.save(previousView);
                if(previousView instanceof Bundleable) {
                    Log.i(TAG, "Persisting state to bundle for " + mainActivity.root.getChildAt(0));
                    outgoingState.setBundle(((Bundleable) previousView).toBundle());
                }
            }
            mainActivity.root.removeAllViews();
            Context internalContext = incomingContexts.get(layoutClassKey);
            LayoutInflater layoutInflater = (LayoutInflater) internalContext.getSystemService(LAYOUT_INFLATER_SERVICE);
            layoutInflater.inflate(layoutClassKey.getLayout(), mainActivity.root, true);
            Log.i(TAG, "Restoring view state for " + mainActivity.root.getChildAt(0));
            incomingState.restore(mainActivity.root.getChildAt(0));
            if(mainActivity.root.getChildAt(0) instanceof Bundleable) {
                Log.i(TAG, "Restoring state from bundle for " + mainActivity.root.getChildAt(0));
                ((Bundleable) mainActivity.root.getChildAt(0)).fromBundle(incomingState.getBundle());
            }
            callback.onTraversalCompleted();
        }
    }


    private static final String TAG = "MainActivity";

    RelativeLayout root;

    @Override
    protected void attachBaseContext(Context baseContext) {
        Log.i(TAG, "Attaching base context");
        baseContext = Flow.configure(baseContext, this) //
                .keyParceler(new KeyParceler() { //
                    @Override
                    public Parcelable toParcelable(Object key) {
                        return (Parcelable) key;
                    }

                    @Override
                    public Object toKey(Parcelable parcelable) {
                        return parcelable;
                    }
                }).defaultKey(new WelcomeKey()) //
                .dispatcher(KeyDispatcher.configure(this, new MainKeyChanger(this)).build()) //
                .install(); //
        super.attachBaseContext(baseContext);
    }

    @Override
    public void onBackPressed() {
        if(!Flow.get(this).goBack()) {
            super.onBackPressed();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "Calling onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        root = (RelativeLayout) findViewById(R.id.main_root);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.i(TAG, "Calling onSaveInstanceState()");
        super.onSaveInstanceState(outState);
        ForceBundler.saveToBundle(this, root.getChildAt(0));
    }

    //

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.i(TAG, "Calling onRestoreInstanceState()");
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onStart() {
        Log.i(TAG, "Calling onStart()");
        super.onStart();
    }

    @Override
    protected void onResume() {
        Log.i(TAG, "Calling onResume()");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.i(TAG, "Calling onPause()");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.i(TAG, "Calling onStop()");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.i(TAG, "Calling onDestroy()");
        super.onDestroy();
    }
}
