package com.zhuinden.flow_alpha_project;

import android.os.Bundle;

/**
 * Created by Zhuinden on 2016.03.02..
 */
public interface Bundleable {
    Bundle toBundle();
    void fromBundle(Bundle bundle);
}
