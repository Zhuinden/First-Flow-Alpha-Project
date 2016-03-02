package com.zhuinden.flow_alpha_project;

import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by Zhuinden on 2016.03.02..
 */
public interface Bundleable {
    Bundle toBundle();

    void fromBundle(@Nullable Bundle bundle);
}
