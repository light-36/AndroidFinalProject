package com.app.nasamarsrover.ui;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProvider;

import com.app.nasamarsrover.R;
import com.app.nasamarsrover.api.NasaResponse;
import com.app.nasamarsrover.ui.base.BaseActivity;
import com.app.nasamarsrover.ui.viewmodel.MainViewModel;
import com.app.nasamarsrover.util.DateUtils;
import com.app.nasamarsrover.util.HelpDialogUtils;
import com.app.nasamarsrover.util.ImageUtils;
import com.app.nasamarsrover.util.SharingUtils;
import com.app.nasamarsrover.util.UiFeedbackUtils;
import com.app.nasamarsrover.util.UrlUtils;
import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;
import java.util.Locale;

/**
 * MainActivity for the NASA Image of the Day app.
 * This activity allows users to search for NASA's Astronomy Picture of the Day by date.
 */
public class MainActivity extends BaseActivity {

    private MainViewModel viewModel;
    private EditText editDate;
    private Button buttonSearch;
    private Button buttonSave;
    private Button buttonViewHd;
    private ProgressBar progressBar;
    private CardView cardImage;
    private ImageView imageNasa;
    private TextView textTitle;
    private TextView textDate;
    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Initialize views
        initViews();
        
        // Initialize view model
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        
        // Set up observers
        setupObservers();
        
        // Set up click listeners
        setupClickListeners();
        
        // Initialize calendar
        calendar = Calendar.getInstance();
        
        // Set default date to today
        String todayDate = DateUtils.getTodayDate();
        editDate.setText(todayDate);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_main;
    }

    /**
     * Initialize views
     */
    private void initViews() {
        editDate = findViewById(R.id.edit_date);
        buttonSearch = findViewById(R.id.button_search);
        buttonSave = findViewById(R.id.button_save);
        buttonViewHd = findViewById(R.id.button_view_hd);
        progressBar = findViewById(R.id.progress_bar);
        cardImage = findViewById(R.id.card_image);
        imageNasa = findViewById(R.id.image_nasa);
        textTitle = findViewById(R.id.text_title);
        textDate = findViewById(R.id.text_date);
    }

    /**
     * Set up observers for LiveData
     */
    private void setupObservers() {
        // Observe NASA image data
        viewModel.getNasaImageLiveData().observe(this, this::updateUI);
        
        // Observe error messages
        viewModel.getErrorLiveData().observe(this, errorMessage -> {
            UiFeedbackUtils.showToast(this, errorMessage, Toast.LENGTH_SHORT);
            progressBar.setVisibility(View.GONE);
        });
        
        // Observe loading state
        viewModel.getLoadingLiveData().observe(this, isLoading -> {
            progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        });
        
        // Observe image saved state
        viewModel.getImageSavedLiveData().observe(this, isSaved -> {
            if (isSaved) {
                UiFeedbackUtils.showSnackbar(findViewById(R.id.main), 
                        R.string.image_saved, Snackbar.LENGTH_SHORT);
                buttonSave.setEnabled(false);
                viewModel.resetImageSavedState();
            }
        });
        
        // Observe image exists state
        viewModel.getImageExistsLiveData().observe(this, exists -> {
            buttonSave.setEnabled(!exists);
            if (exists) {
                buttonSave.setText(R.string.image_already_saved);
            } else {
                buttonSave.setText(R.string.save_image);
            }
        });
    }

    /**
     * Set up click listeners
     */
    private void setupClickListeners() {
        // Date picker
        editDate.setOnClickListener(v -> showDatePickerDialog());
        
        // Search button
        buttonSearch.setOnClickListener(v -> {
            String date = editDate.getText().toString().trim();
            if (!date.isEmpty()) {
                viewModel.getNasaImage(date);
            } else {
                UiFeedbackUtils.showToast(this, R.string.please_select_date, Toast.LENGTH_SHORT);
            }
        });
        
        // Save button
        buttonSave.setOnClickListener(v -> viewModel.saveCurrentImage());
        
        // View HD button
        buttonViewHd.setOnClickListener(v -> {
            NasaResponse nasaResponse = viewModel.getNasaImageLiveData().getValue();
            if (nasaResponse != null && nasaResponse.getHdUrl() != null) {
                boolean opened = UrlUtils.openUrlInBrowser(this, nasaResponse.getHdUrl());
                if (!opened) {
                    UiFeedbackUtils.showToast(this, R.string.error_opening_url, Toast.LENGTH_SHORT);
                }
            }
        });
    }

    /**
     * Show date picker dialog
     */
    private void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, year, month, dayOfMonth) -> {
                    calendar.set(Calendar.YEAR, year);
                    calendar.set(Calendar.MONTH, month);
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    
                    String selectedDate = DateUtils.formatDate(year, month, dayOfMonth);
                    editDate.setText(selectedDate);
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        
        // Set max date to today
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        
        // Set min date to June 16, 1995 (first APOD)
        Calendar minDate = Calendar.getInstance();
        minDate.set(1995, Calendar.JUNE, 16);
        datePickerDialog.getDatePicker().setMinDate(minDate.getTimeInMillis());
        
        datePickerDialog.show();
    }

    /**
     * Update UI with NASA image data
     * 
     * @param nasaResponse The NASA API response
     */
    private void updateUI(NasaResponse nasaResponse) {
        if (nasaResponse == null) {
            return;
        }
        
        // Show card and buttons
        cardImage.setVisibility(View.VISIBLE);
        buttonSave.setVisibility(View.VISIBLE);
        buttonViewHd.setVisibility(View.VISIBLE);
        
        // Set image
        if ("image".equals(nasaResponse.getMediaType())) {
            ImageUtils.loadImage(this, nasaResponse.getUrl(), imageNasa);
        } else {
            // For video content, show placeholder
            imageNasa.setImageResource(R.drawable.placeholder_image);
        }
        
        // Set title and date
        textTitle.setText(nasaResponse.getTitle());
        
        // Format date for display
        Locale locale = getResources().getConfiguration().getLocales().get(0);
        String formattedDate = DateUtils.formatDateForDisplay(nasaResponse.getDate(), locale);
        textDate.setText(formattedDate);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        
        // Add share menu item
        menu.add(Menu.NONE, R.id.action_share, Menu.NONE, R.string.share)
                .setIcon(android.R.drawable.ic_menu_share)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        
        if (itemId == R.id.action_share) {
            NasaResponse nasaResponse = viewModel.getNasaImageLiveData().getValue();
            if (nasaResponse != null) {
                SharingUtils.shareNasaImage(this, nasaResponse);
            } else {
                UiFeedbackUtils.showToast(this, R.string.no_image_to_share, Toast.LENGTH_SHORT);
            }
            return true;
        } else if (itemId == R.id.action_help) {
            HelpDialogUtils.showMainActivityHelpDialog(this);
            return true;
        }
        
        return super.onOptionsItemSelected(item);
    }
}
