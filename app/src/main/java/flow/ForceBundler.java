package flow;

import android.app.Activity;
import android.view.View;

import com.zhuinden.flow_alpha_project.Bundleable;

/**
 * Created by Zhuinden on 2016.03.02..
 */
public class ForceBundler {
    public static void saveToBundle(Activity activity, View... activeViews) {
        InternalLifecycleIntegration internalLifeCycleIntegration = InternalLifecycleIntegration.find(activity);
        if(internalLifeCycleIntegration != null) {
            KeyManager keyManager = internalLifeCycleIntegration.keyManager;
            for(View view : activeViews) {
                State state = keyManager.getState(Flow.getKey(view));
                state.save(view);
                if(view instanceof Bundleable) {
                    state.setBundle(((Bundleable) view).toBundle());
                }
            }
        }
    }
}
