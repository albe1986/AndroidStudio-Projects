package it.app.apolverari.calendarioturni;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.os.Bundle;
import android.preference.PreferenceScreen;
import android.view.MenuItem;
import android.widget.Toast;

public class SettingsActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preference_layout);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
