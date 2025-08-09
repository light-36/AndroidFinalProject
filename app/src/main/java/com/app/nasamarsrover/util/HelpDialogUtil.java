package com.app.nasamarsrover.util;

import android.content.Context;

import androidx.appcompat.app.AlertDialog;

import com.app.nasamarsrover.R;

/**
 * Utility class for displaying help dialogs.
 * This class provides methods for showing help dialogs in different activities.
 */
public class HelpDialogUtil {
    
    /**
     * Show a help dialog for the main activity
     * 
     * @param context The context
     */
    public static void showMainActivityHelp(Context context) {
        showHelpDialog(context, R.string.help, R.string.help_main);
    }
    
    /**
     * Show a help dialog for the saved images activity
     * 
     * @param context The context
     */
    public static void showSavedImagesHelp(Context context) {
        showHelpDialog(context, R.string.help, R.string.help_saved);
    }
    
    /**
     * Show a help dialog for the image details activity
     * 
     * @param context The context
     */
    public static void showImageDetailsHelp(Context context) {
        showHelpDialog(context, R.string.help, R.string.help_details);
    }
    
    /**
     * Show a help dialog for the settings activity
     * 
     * @param context The context
     */
    public static void showSettingsHelp(Context context) {
        showHelpDialog(context, R.string.help, R.string.help_settings);
    }
    
    /**
     * Show a help dialog with the specified title and message
     * 
     * @param context The context
     * @param titleResId The resource ID of the title
     * @param messageResId The resource ID of the message
     */
    private static void showHelpDialog(Context context, int titleResId, int messageResId) {
        new AlertDialog.Builder(context)
                .setTitle(titleResId)
                .setMessage(messageResId)
                .setPositiveButton(android.R.string.ok, null)
                .show();
    }
}
