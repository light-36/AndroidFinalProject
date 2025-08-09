package com.app.nasamarsrover.util;

import android.content.Context;

import androidx.appcompat.app.AlertDialog;

import com.app.nasamarsrover.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

/**
 * Utility class for displaying help dialogs.
 * This class provides methods for showing help dialogs in different activities.
 */
public class HelpDialogUtils {

    /**
     * Show the help dialog for the MainActivity
     * 
     * @param context The context
     */
    public static void showMainActivityHelpDialog(Context context) {
        new MaterialAlertDialogBuilder(context)
                .setTitle(R.string.help_title)
                .setMessage(R.string.main_activity_help_message)
                .setPositiveButton(R.string.ok, (dialog, which) -> dialog.dismiss())
                .show();
    }

    /**
     * Show the help dialog for the SavedImagesActivity
     * 
     * @param context The context
     */
    public static void showSavedImagesActivityHelpDialog(Context context) {
        new MaterialAlertDialogBuilder(context)
                .setTitle(R.string.help_title)
                .setMessage(R.string.saved_images_activity_help_message)
                .setPositiveButton(R.string.ok, (dialog, which) -> dialog.dismiss())
                .show();
    }

    /**
     * Show the help dialog for the ImageDetailActivity
     * 
     * @param context The context
     */
    public static void showImageDetailActivityHelpDialog(Context context) {
        new MaterialAlertDialogBuilder(context)
                .setTitle(R.string.help_title)
                .setMessage(R.string.image_detail_activity_help_message)
                .setPositiveButton(R.string.ok, (dialog, which) -> dialog.dismiss())
                .show();
    }

    /**
     * Show the help dialog for the SettingsActivity
     * 
     * @param context The context
     */
    public static void showSettingsActivityHelpDialog(Context context) {
        new MaterialAlertDialogBuilder(context)
                .setTitle(R.string.help_title)
                .setMessage(R.string.settings_activity_help_message)
                .setPositiveButton(R.string.ok, (dialog, which) -> dialog.dismiss())
                .show();
    }

    /**
     * Show the help dialog for the AboutActivity
     * 
     * @param context The context
     */
    public static void showAboutActivityHelpDialog(Context context) {
        new MaterialAlertDialogBuilder(context)
                .setTitle(R.string.help_title)
                .setMessage(R.string.about_activity_help_message)
                .setPositiveButton(R.string.ok, (dialog, which) -> dialog.dismiss())
                .show();
    }

    /**
     * Show a custom help dialog
     * 
     * @param context The context
     * @param titleResId The resource ID for the dialog title
     * @param messageResId The resource ID for the dialog message
     */
    public static void showCustomHelpDialog(Context context, int titleResId, int messageResId) {
        new MaterialAlertDialogBuilder(context)
                .setTitle(titleResId)
                .setMessage(messageResId)
                .setPositiveButton(R.string.ok, (dialog, which) -> dialog.dismiss())
                .show();
    }
}
