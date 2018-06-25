package com.example.dim.licence.utils;

import android.os.Bundle;

public interface EntityBundleInterface<T> {

    Commons commons = new Commons();

    Bundle entityToBundle();
    void entityFromBundle(Bundle bundle);
}
