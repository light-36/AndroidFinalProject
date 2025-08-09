package com.app.nasamarsrover;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.app.nasamarsrover.data.model.NasaImage;
import com.app.nasamarsrover.data.repository.NasaRepository;
import com.app.nasamarsrover.databinding.ActivityMainBinding;
import com.app.nasamarsrover.ui.AboutActivity;
import com.app.nasamarsrover.ui.SavedImagesActivity;
import com.app.nasamarsrover.ui.SettingsActivity;
import com.app.nasamarsrover.util.HelpDialogUtil;
import com.app.nasamarsrover.util.SharedPreferencesManager;
import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Main activity for the NASA Image of the Day application.
 * This activity allows users to search for NASA's Astronomy Picture of the Day by date.
 */
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ActivityMainBinding binding;
    private NasaRepository nasaRepository;
    private SharedPreferencesManager preferencesManager;
    private NasaImage currentImage;
    private final Calendar calendar = Calendar.getInstance();
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize repository and preferences
        nasaRepository = new NasaRepository(this);
        preferencesManager = SharedPreferencesManager.getInstance(this);

        // Set up toolbar
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setTitle(getString(R.string.app_name));
        
        // Get version name from package info instead of BuildConfig
        try {
            String versionName = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
            getSupportActionBar().setSubtitle("v" + versionName);
        } catch (Exception e) {
            getSupportActionBar().setSubtitle("");
        }

        // Set up navigation drawer
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, binding.drawerLayout, binding.toolbar,
                R.string.nav_home, R.string.nav_home);
        binding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        binding.navView.setNavigationItemSelectedListener(this);

        // Set up date picker
        binding.editDate.setOnClickListener(v -> showDatePickerDialog());

        // Set up search button
        binding.buttonSearch.setOnClickListener(v -> {
            String date = binding.editDate.getText().toString();
            if (!date.isEmpty()) {
                searchImageByDate(date);
            } else {
                Snackbar.make(binding.getRoot(), R.string.select_date, Snackbar.LENGTH_SHORT).show();
            }
        });

        // Set up save button
        binding.buttonSave.setOnClickListener(v -> saveCurrentImage());

        // Set up view HD button
        binding.buttonViewHd.setOnClickListener(v -> {
            if (currentImage != null && currentImage.getHdUrl() != null) {
                openWebUrl(currentImage.getHdUrl());
            }
        });

        // Check if there's a last viewed date
        String lastViewedDate = preferencesManager.getLastViewedDate();
        if (lastViewedDate != null) {
            binding.editDate.setText(lastViewedDate);
            searchImageByDate(lastViewedDate);
        } else {
            // Set today's date as default
            binding.editDate.setText(dateFormat.format(calendar.getTime()));
        }
    }

    /**
     * Show the date picker dialog
     */
    private void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, year, month, dayOfMonth) -> {
                    calendar.set(Calendar.YEAR, year);
                    calendar.set(Calendar.MONTH, month);
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    String selectedDate = dateFormat.format(calendar.getTime());
                    binding.editDate.setText(selectedDate);
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    /**
     * Search for NASA image by date
     * 
     * @param date The date in YYYY-MM-DD format
     */
    private void searchImageByDate(String date) {
        showLoading(true);
        hideImageViews();

        // Save the date as last viewed
        preferencesManager.saveLastViewedDate(date);

        // Fetch image from API
        nasaRepository.fetchImageOfTheDay(date, new NasaRepository.FetchImageCallback() {
            @Override
            public void onImageFetched(NasaImage nasaImage) {
                showLoading(false);
                if (nasaImage != null) {
                    displayImage(nasaImage);
                    currentImage = nasaImage;
                } else {
                    showError(getString(R.string.error_loading_image));
                }
            }

            @Override
            public void onError(String errorMessage) {
                showLoading(false);
                showError(errorMessage);
            }
        });
    }

    /**
     * Display the NASA image
     * 
     * @param nasaImage The NASA image to display
     */
    private void displayImage(NasaImage nasaImage) {
        binding.cardImage.setVisibility(View.VISIBLE);
        binding.buttonSave.setVisibility(View.VISIBLE);
        binding.buttonViewHd.setVisibility(View.VISIBLE);

        // Load image using Glide
        Glide.with(this)
                .load(nasaImage.getUrl())
                .placeholder(R.drawable.placeholder_image)
                .error(R.drawable.error_image)
                .into(binding.imageNasa);

        // Set text views
        binding.textTitle.setText(nasaImage.getTitle());
        binding.textDate.setText(nasaImage.getDate());

        // Check if image is already saved
        new CheckImageExistsTask().execute(nasaImage.getDate());
    }

    /**
     * Save the current image to the database
     */
    private void saveCurrentImage() {
        if (currentImage != null) {
            nasaRepository.saveImage(currentImage, new NasaRepository.DatabaseOperationCallback() {
                @Override
                public void onSuccess() {
                    Toast.makeText(MainActivity.this, R.string.image_saved, Toast.LENGTH_SHORT).show();
                    binding.buttonSave.setEnabled(false);
                }

                @Override
                public void onError(String errorMessage) {
                    Snackbar.make(binding.getRoot(), errorMessage, Snackbar.LENGTH_SHORT).show();
                }
            });
        }
    }

    /**
     * Show or hide the loading indicator
     * 
     * @param isLoading True to show loading, false to hide
     */
    private void showLoading(boolean isLoading) {
        binding.progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
    }

    /**
     * Hide image views
     */
    private void hideImageViews() {
        binding.cardImage.setVisibility(View.GONE);
        binding.buttonSave.setVisibility(View.GONE);
        binding.buttonViewHd.setVisibility(View.GONE);
    }

    /**
     * Show an error message
     * 
     * @param message The error message
     */
    private void showError(String message) {
        Snackbar.make(binding.getRoot(), message, Snackbar.LENGTH_LONG).show();
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
        if (item.getItemId() == R.id.action_help) {
            HelpDialogUtil.showMainActivityHelp(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Already on home screen, do nothing
        } else if (id == R.id.nav_saved_images) {
            startActivity(new Intent(this, SavedImagesActivity.class));
        } else if (id == R.id.nav_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
        } else if (id == R.id.nav_about) {
            startActivity(new Intent(this, AboutActivity.class));
        }

        binding.drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /**
     * AsyncTask to check if an image exists in the database
     */
    private class CheckImageExistsTask extends AsyncTask<String, Void, Boolean> {
        @Override
        protected Boolean doInBackground(String... dates) {
            return nasaRepository.imageExists(dates[0]);
        }

        @Override
        protected void onPostExecute(Boolean exists) {
            binding.buttonSave.setEnabled(!exists);
            if (exists) {
                Snackbar.make(binding.getRoot(), R.string.image_already_saved, Snackbar.LENGTH_SHORT).show();
            }
        }
    }
}