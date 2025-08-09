package com.app.nasamarsrover.ui;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.nasamarsrover.R;
import com.app.nasamarsrover.data.model.NasaImage;
import com.app.nasamarsrover.databinding.ActivitySavedImagesBinding;
import com.app.nasamarsrover.ui.adapter.NasaImageAdapter;
import com.app.nasamarsrover.ui.viewmodel.SavedImagesViewModel;
import com.app.nasamarsrover.util.HelpDialogUtils;
import com.google.android.material.snackbar.Snackbar;

/**
 * Activity for displaying saved NASA images.
 * This activity shows a list of NASA images that have been saved by the user.
 */
public class SavedImagesActivity extends AppCompatActivity implements NasaImageAdapter.OnItemClickListener {

    private ActivitySavedImagesBinding binding;
    private SavedImagesViewModel viewModel;
    private NasaImageAdapter adapter;
    private String versionName = "1.0"; // Default version

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySavedImagesBinding.inflate(getLayoutInflater());
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
        getSupportActionBar().setTitle(getString(R.string.nav_saved_images));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setSubtitle("v" + versionName);

        // Set up RecyclerView
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setHasFixedSize(true);
        adapter = new NasaImageAdapter(this, this);
        binding.recyclerView.setAdapter(adapter);

        // Set up ViewModel
        viewModel = new ViewModelProvider(this).get(SavedImagesViewModel.class);
        viewModel.getAllImages().observe(this, nasaImages -> {
            adapter.setNasaImages(nasaImages);
            
            // Show empty view if there are no images
            if (nasaImages.isEmpty()) {
                binding.textEmpty.setVisibility(View.VISIBLE);
            } else {
                binding.textEmpty.setVisibility(View.GONE);
            }
        });

        // Set up swipe to delete
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                NasaImage nasaImage = adapter.getNasaImageAt(position);
                viewModel.delete(nasaImage);
                Snackbar.make(binding.getRoot(), R.string.image_deleted, Snackbar.LENGTH_LONG)
                        .setAction(R.string.undo, v -> viewModel.insert(nasaImage))
                        .show();
            }
        }).attachToRecyclerView(binding.recyclerView);
    }

    @Override
    public void onItemClick(NasaImage nasaImage) {
        Intent intent = new Intent(this, ImageDetailsActivity.class);
        intent.putExtra(ImageDetailsActivity.EXTRA_IMAGE_ID, nasaImage.getId());
        intent.putExtra(ImageDetailsActivity.EXTRA_IMAGE_TITLE, nasaImage.getTitle());
        intent.putExtra(ImageDetailsActivity.EXTRA_IMAGE_DATE, nasaImage.getDate());
        intent.putExtra(ImageDetailsActivity.EXTRA_IMAGE_URL, nasaImage.getUrl());
        intent.putExtra(ImageDetailsActivity.EXTRA_IMAGE_HD_URL, nasaImage.getHdUrl());
        intent.putExtra(ImageDetailsActivity.EXTRA_IMAGE_EXPLANATION, nasaImage.getExplanation());
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
            HelpDialogUtils.showSavedImagesActivityHelpDialog(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
