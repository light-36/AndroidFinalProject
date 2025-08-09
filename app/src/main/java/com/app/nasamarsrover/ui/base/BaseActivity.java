package com.app.nasamarsrover.ui.base;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.app.nasamarsrover.R;
import com.app.nasamarsrover.ui.AboutActivity;
import com.app.nasamarsrover.ui.MainActivity;
import com.app.nasamarsrover.ui.SavedImagesActivity;
import com.app.nasamarsrover.ui.SettingsActivity;
import com.app.nasamarsrover.util.HelpDialogUtils;
import com.google.android.material.navigation.NavigationView;

/**
 * Base activity class that all activities can extend.
 * This class provides common functionality for all activities.
 */
public abstract class BaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    protected DrawerLayout drawerLayout;
    protected NavigationView navigationView;
    protected Toolbar toolbar;
    protected ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResourceId());
        
        setupToolbar();
        setupNavigationDrawer();
    }

    /**
     * Get the layout resource ID for the activity
     * 
     * @return The layout resource ID
     */
    protected abstract int getLayoutResourceId();

    /**
     * Setup the toolbar
     */
    protected void setupToolbar() {
        toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }
        }
    }

    /**
     * Setup the navigation drawer
     */
    protected void setupNavigationDrawer() {
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        
        if (drawerLayout != null && navigationView != null) {
            toggle = new ActionBarDrawerToggle(
                    this, drawerLayout, toolbar, 
                    R.string.navigation_drawer_open, 
                    R.string.navigation_drawer_close);
            
            drawerLayout.addDrawerListener(toggle);
            toggle.syncState();
            
            navigationView.setNavigationItemSelectedListener(this);
            
            // Set the current activity as selected in the navigation drawer
            updateNavigationSelection();
        }
    }

    /**
     * Update the navigation drawer selection based on the current activity
     */
    protected void updateNavigationSelection() {
        if (navigationView != null) {
            if (this instanceof MainActivity) {
                navigationView.setCheckedItem(R.id.nav_home);
            } else if (this instanceof SavedImagesActivity) {
                navigationView.setCheckedItem(R.id.nav_saved_images);
            } else if (this instanceof SettingsActivity) {
                navigationView.setCheckedItem(R.id.nav_settings);
            } else if (this instanceof AboutActivity) {
                navigationView.setCheckedItem(R.id.nav_about);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (toggle != null && toggle.onOptionsItemSelected(item)) {
            return true;
        }
        
        int itemId = item.getItemId();
        if (itemId == R.id.action_help) {
            showHelpDialog();
            return true;
        } else if (itemId == android.R.id.home) {
            onBackPressed();
            return true;
        }
        
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        
        if (itemId == R.id.nav_home) {
            navigateToActivity(MainActivity.class);
        } else if (itemId == R.id.nav_saved_images) {
            navigateToActivity(SavedImagesActivity.class);
        } else if (itemId == R.id.nav_settings) {
            navigateToActivity(SettingsActivity.class);
        } else if (itemId == R.id.nav_about) {
            navigateToActivity(AboutActivity.class);
        }
        
        if (drawerLayout != null) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        
        return true;
    }

    /**
     * Navigate to an activity
     * 
     * @param activityClass The activity class to navigate to
     */
    protected void navigateToActivity(Class<?> activityClass) {
        // Don't navigate if we're already on this activity
        if (this.getClass().equals(activityClass)) {
            return;
        }
        
        Intent intent = new Intent(this, activityClass);
        startActivity(intent);
    }

    /**
     * Show the help dialog for the activity
     */
    protected void showHelpDialog() {
        if (this instanceof MainActivity) {
            HelpDialogUtils.showMainActivityHelpDialog(this);
        } else if (this instanceof SavedImagesActivity) {
            HelpDialogUtils.showSavedImagesActivityHelpDialog(this);
        } else if (this instanceof SettingsActivity) {
            HelpDialogUtils.showSettingsActivityHelpDialog(this);
        } else if (this instanceof AboutActivity) {
            HelpDialogUtils.showAboutActivityHelpDialog(this);
        }
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout != null && drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
