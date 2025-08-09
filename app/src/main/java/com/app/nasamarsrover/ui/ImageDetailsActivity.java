package com.app.nasamarsrover.ui;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.app.nasamarsrover.R;
import com.app.nasamarsrover.databinding.ActivityImageDetailsBinding;
import com.app.nasamarsrover.util.HelpDialogUtils;
import com.bumptech.glide.Glide;

/**
 * Activity for displaying detailed information about a NASA image.
 * This activity shows the full image and its explanation.
 */
public class ImageDetailsActivity extends AppCompatActivity {

    public static final String EXTRA_IMAGE_ID = "extra_image_id";
    public static final String EXTRA_IMAGE_TITLE = "extra_image_title";
    public static final String EXTRA_IMAGE_DATE = "extra_image_date";
    public static final String EXTRA_IMAGE_URL = "extra_image_url";
    public static final String EXTRA_IMAGE_HD_URL = "extra_image_hd_url";
    public static final String EXTRA_IMAGE_EXPLANATION = "extra_image_explanation";

    private ActivityImageDetailsBinding binding;
    private String hdUrl;
    private String versionName = "1.0"; // Default version

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityImageDetailsBinding.inflate(getLayoutInflater());
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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setSubtitle("v" + versionName);

        // Get data from intent
        Intent intent = getIntent();
        if (intent != null) {
            String title = intent.getStringExtra(EXTRA_IMAGE_TITLE);
            String date = intent.getStringExtra(EXTRA_IMAGE_DATE);
            String url = intent.getStringExtra(EXTRA_IMAGE_URL);
            hdUrl = intent.getStringExtra(EXTRA_IMAGE_HD_URL);
            String explanation = intent.getStringExtra(EXTRA_IMAGE_EXPLANATION);

            // Set title
            getSupportActionBar().setTitle(title);

            // Load image
            Glide.with(this)
                    .load(url)
                    .placeholder(R.drawable.placeholder_image)
                    .error(R.drawable.error_image)
                    .into(binding.imageNasa);

            // Set text views
            binding.textTitle.setText(title);
            binding.textDate.setText(date);
            binding.textExplanation.setText(explanation);

            // Set up view HD button
            binding.buttonViewHd.setOnClickListener(v -> {
                if (hdUrl != null) {
                    openWebUrl(hdUrl);
                }
            });

            // Set up share button
            binding.buttonShare.setOnClickListener(v -> shareImage(title, explanation, url));
        }
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

    /**
     * Share the image details
     * 
     * @param title The image title
     * @param explanation The image explanation
     * @param url The image URL
     */
    private void shareImage(String title, String explanation, String url) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.share_subject, title));
        shareIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_text, title, explanation, url));
        startActivity(Intent.createChooser(shareIntent, getString(R.string.share_via)));
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
            HelpDialogUtils.showImageDetailActivityHelpDialog(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
