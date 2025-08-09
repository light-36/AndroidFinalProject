package com.app.nasamarsrover.ui;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.app.nasamarsrover.R;
import com.app.nasamarsrover.databinding.ActivitySettingsBinding;
import com.app.nasamarsrover.util.HelpDialogUtils;
import com.app.nasamarsrover.util.SharedPreferencesManager;

import java.util.Locale;

/**
 * Activity for configuring app settings.
 * This activity allows users to change app preferences such as API key, theme, and language.
 */
public class SettingsActivity extends AppCompatActivity {

    private ActivitySettingsBinding binding;
    private SharedPreferencesManager preferencesManager;
    private String versionName = "1.0"; // Default version

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Get app version
        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            versionName = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        // Set up toolbar
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setTitle(getString(R.string.nav_settings));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setSubtitle("v" + versionName);

        // Initialize preferences manager
        preferencesManager = SharedPreferencesManager.getInstance(this);

        // Set up API key input
        binding.editApiKey.setText(preferencesManager.getApiKey());

        // Set up theme switch
        binding.switchDarkMode.setChecked(preferencesManager.isDarkModeEnabled());
        binding.switchDarkMode.setOnCheckedChangeListener((buttonView, isChecked) -> {
            preferencesManager.setDarkModeEnabled(isChecked);
            AppCompatDelegate.setDefaultNightMode(
                    isChecked ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
        });

        // Set up language radio buttons
        if (preferencesManager.getLanguage().equals("fr")) {
            binding.radioFrench.setChecked(true);
        } else {
            binding.radioEnglish.setChecked(true);
        }

        binding.radioGroupLanguage.setOnCheckedChangeListener((group, checkedId) -> {
            String language = checkedId == R.id.radio_french ? "fr" : "en";
            if (!language.equals(preferencesManager.getLanguage())) {
                preferencesManager.setLanguage(language);
                Toast.makeText(this, R.string.language_change_restart, Toast.LENGTH_LONG).show();
            }
        });

        // Set up save button
        binding.buttonSave.setOnClickListener(v -> saveSettings());
    }

    /**
     * Save the settings
     */
    private void saveSettings() {
        String apiKey = binding.editApiKey.getText().toString().trim();
        preferencesManager.saveApiKey(apiKey);
        Toast.makeText(this, R.string.settings_saved, Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        } else if (id == R.id.action_help) {
            HelpDialogUtils.showSettingsActivityHelpDialog(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
