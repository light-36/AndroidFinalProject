package com.app.nasamarsrover.ui;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import com.app.nasamarsrover.R;
import com.app.nasamarsrover.databinding.ActivityAboutBinding;
import com.app.nasamarsrover.util.HelpDialogUtils;

/**
 * Activity for displaying information about the app.
 * This activity shows app version, developer information, and links to NASA resources.
 */
public class AboutActivity extends AppCompatActivity {

    private ActivityAboutBinding binding;
    private String versionName = "1.0"; // Default version

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAboutBinding.inflate(getLayoutInflater());
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
        getSupportActionBar().setTitle(getString(R.string.nav_about));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setSubtitle("v" + versionName);

        // Set version text
        binding.textVersion.setText(getString(R.string.version, versionName));

        // Set up NASA website button
        binding.buttonNasaWebsite.setOnClickListener(v -> {
            openWebUrl("https://www.nasa.gov/");
        });

        // Set up APOD website button
        binding.buttonApodWebsite.setOnClickListener(v -> {
            openWebUrl("https://apod.nasa.gov/apod/");
        });

        // Set up NASA API button
        binding.buttonNasaApi.setOnClickListener(v -> {
            openWebUrl("https://api.nasa.gov/");
        });
    }

    /**
     * Open a web URL in the browser
     * 
     * @param url The URL to open
     */
    private void openWebUrl(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
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
            HelpDialogUtils.showAboutActivityHelpDialog(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
