package com.kasumi.closet;

import android.app.Activity;
import android.os.Bundle;
import android.content.Context;
import android.provider.Settings;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceScreen;
import android.text.TextUtils;

import com.android.settings.dashboard.SummaryLoader;

import com.android.internal.logging.MetricsProto.MetricsEvent;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.Utils;

public class ClosetSettings extends SettingsPreferenceFragment implements
        Preference.OnPreferenceChangeListener {
    private static final String TAG = "ClosetSettings";

    @Override
    protected int getMetricsCategory() {
        return MetricsEvent.APPLICATION;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.closet);
    }

    public boolean onPreferenceChange(Preference preference, Object objValue) {

        return true;
    }

    private static class SummaryProvider implements SummaryLoader.SummaryProvider {

        private final Context mContext;
        private final SummaryLoader mSummaryLoader;

        public SummaryProvider(Context context, SummaryLoader summaryLoader) {
            mContext = context;
            mSummaryLoader = summaryLoader;
        }

        @Override
        public void setListening(boolean listening) {
           String mCustomSummary = Settings.System.getString(
                    mContext.getContentResolver(), Settings.System.KASUMI_SETTINGS_SUMMARY);
            if (listening) {		            
                if (TextUtils.isEmpty(mCustomSummary)) {
                    mSummaryLoader.setSummary(this, mContext.getString(R.string.closet_summary_title));
                } else {
                    mSummaryLoader.setSummary(this, mCustomSummary);
                }
            }
        }
    }

    public static final SummaryLoader.SummaryProviderFactory SUMMARY_PROVIDER_FACTORY
            = new SummaryLoader.SummaryProviderFactory() {
        @Override
        public SummaryLoader.SummaryProvider createSummaryProvider(Activity activity,
                                                                   SummaryLoader summaryLoader) {
            return new SummaryProvider(activity, summaryLoader);
        }
    };
}
