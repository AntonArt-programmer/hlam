package org.juicecode.telehlam.utils;

import androidx.fragment.app.Fragment;

public interface FragmentManagerSimplifier {
    void replaceFragment(Fragment fragment);
    void addFragment(Fragment fragment,String tag);
    void remove(String tag);
}
