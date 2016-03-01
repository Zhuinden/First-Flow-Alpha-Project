package com.zhuinden.flow_alpha_project;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.Map;

import flow.ClassKey;
import flow.Direction;
import flow.Dispatcher;
import flow.Flow;
import flow.KeyChanger;
import flow.KeyDispatcher;
import flow.KeyParceler;
import flow.State;
import flow.Traversal;
import flow.TraversalCallback;

public class MainActivity
        extends AppCompatActivity {

    static class MainKeyChanger extends KeyChanger {
        private static final String TAG = "MainKeyChanger";

        private MainActivity mainActivity;

        MainKeyChanger(MainActivity mainActivity) {
            this.mainActivity = mainActivity;
        }

        @Override
        public void changeKey(State outgoingState, State incomingState, Direction direction, Map<Object, Context> incomingContexts, TraversalCallback callback) {
            Log.i(TAG, "Change Key: [" + outgoingState + "] - [" + incomingState + "]");
            if(incomingState.getKey() instanceof WelcomeKey) {
                WelcomeKey welcomeKey = incomingState.getKey(); //new key
                mainActivity.root.removeAllViews();
                Context internalContext = incomingContexts.get(welcomeKey);
                LayoutInflater layoutInflater = (LayoutInflater) internalContext.getSystemService(LAYOUT_INFLATER_SERVICE);
                ViewGroup viewGroup = (ViewGroup) layoutInflater.inflate(R.layout.path_welcome, mainActivity.root, false);
                viewGroup.findViewById(R.id.welcome_button).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Flow.get(v).set(new OtherKey());
                    }
                });
                mainActivity.root.addView(viewGroup); //I was lazy to make a custom view here
            } else if(incomingState.getKey() instanceof OtherKey) {
                OtherKey otherKey = incomingState.getKey(); //new key
                mainActivity.root.removeAllViews();
                Context internalContext = incomingContexts.get(otherKey);
                LayoutInflater layoutInflater = (LayoutInflater) internalContext.getSystemService(LAYOUT_INFLATER_SERVICE);
                layoutInflater.inflate(R.layout.path_other, mainActivity.root, true);
            }
            callback.onTraversalCompleted();
        }
    }


    private static final String TAG = "MainActivity";

    RelativeLayout root;

    public static class WelcomeKey
            extends ClassKey //equals, hashcode based on class of key
            implements Parcelable {
        public WelcomeKey() {
        }

        protected WelcomeKey(Parcel in) {
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {

        }

        public static final Creator<WelcomeKey> CREATOR = new Creator<WelcomeKey>() {
            @Override
            public WelcomeKey createFromParcel(Parcel in) {
                return new WelcomeKey(in);
            }

            @Override
            public WelcomeKey[] newArray(int size) {
                return new WelcomeKey[size];
            }
        };

    }

    public static class OtherKey
            extends ClassKey //equals, hashcode based on class of key
            implements Parcelable {
        public OtherKey() {
        }

        protected OtherKey(Parcel in) {
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {

        }

        public static final Creator<OtherKey> CREATOR = new Creator<OtherKey>() {
            @Override
            public OtherKey createFromParcel(Parcel in) {
                return new OtherKey(in);
            }

            @Override
            public OtherKey[] newArray(int size) {
                return new OtherKey[size];
            }
        };

    }

    @Override
    protected void attachBaseContext(Context baseContext) {
        Log.i(TAG, "Attaching base context");
        baseContext = Flow.configure(baseContext, this).keyParceler(new KeyParceler() {
            @Override
            public Parcelable toParcelable(Object key) {
                return (Parcelable) key;
            }

            @Override
            public Object toKey(Parcelable parcelable) {
                return parcelable;
            }
        }).defaultKey(new WelcomeKey()).dispatcher(KeyDispatcher.configure(this, new MainKeyChanger(this)).build()).install();
        super.attachBaseContext(baseContext);
    }

    @Override
    public void onBackPressed() {
        Object key = Flow.getKey(root.getChildAt(0).getContext());
        Log.i(TAG, "Key is " + key);
        if(key instanceof OtherKey) {
            Flow.get(this).goBack();
        } else {
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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.i(TAG, "Calling onSaveInstanceState()");
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.i(TAG, "Calling onRestoreInstanceState()");
        super.onRestoreInstanceState(savedInstanceState);
    }
}
