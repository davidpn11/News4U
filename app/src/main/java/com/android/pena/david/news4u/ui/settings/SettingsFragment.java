package com.android.pena.david.news4u.ui.settings;



import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;

import com.android.pena.david.news4u.R;

/**
 * Created by david on 24/07/17.
 */

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.pref_visualizer);
    }
}
