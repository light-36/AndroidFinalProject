package com.app.nasamarsrover.util;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

import com.google.android.material.snackbar.Snackbar;

/**
 * Utility class for UI feedback operations.
 * This class provides methods for showing Toast and Snackbar messages.
 */
public class UiFeedbackUtils {

    /**
     * Show a Toast message
     * 
     * @param context The context
     * @param message The message to show
     * @param duration The duration (Toast.LENGTH_SHORT or Toast.LENGTH_LONG)
     */
    public static void showToast(Context context, String message, int duration) {
        if (context == null || message == null || message.isEmpty()) {
            return;
        }
        
        Toast.makeText(context, message, duration).show();
    }
    
    /**
     * Show a Toast message with a resource ID
     * 
     * @param context The context
     * @param messageResId The resource ID of the message to show
     * @param duration The duration (Toast.LENGTH_SHORT or Toast.LENGTH_LONG)
     */
    public static void showToast(Context context, @StringRes int messageResId, int duration) {
        if (context == null) {
            return;
        }
        
        Toast.makeText(context, messageResId, duration).show();
    }
    
    /**
     * Show a Snackbar message
     * 
     * @param view The view to attach the Snackbar to
     * @param message The message to show
     * @param duration The duration (Snackbar.LENGTH_SHORT, Snackbar.LENGTH_LONG, or Snackbar.LENGTH_INDEFINITE)
     * @return The Snackbar instance
     */
    public static Snackbar showSnackbar(View view, String message, int duration) {
        if (view == null || message == null || message.isEmpty()) {
            return null;
        }
        
        Snackbar snackbar = Snackbar.make(view, message, duration);
        snackbar.show();
        return snackbar;
    }
    
    /**
     * Show a Snackbar message with a resource ID
     * 
     * @param view The view to attach the Snackbar to
     * @param messageResId The resource ID of the message to show
     * @param duration The duration (Snackbar.LENGTH_SHORT, Snackbar.LENGTH_LONG, or Snackbar.LENGTH_INDEFINITE)
     * @return The Snackbar instance
     */
    public static Snackbar showSnackbar(View view, @StringRes int messageResId, int duration) {
        if (view == null) {
            return null;
        }
        
        Snackbar snackbar = Snackbar.make(view, messageResId, duration);
        snackbar.show();
        return snackbar;
    }
    
    /**
     * Show a Snackbar message with an action
     * 
     * @param view The view to attach the Snackbar to
     * @param message The message to show
     * @param duration The duration (Snackbar.LENGTH_SHORT, Snackbar.LENGTH_LONG, or Snackbar.LENGTH_INDEFINITE)
     * @param actionText The text for the action button
     * @param listener The click listener for the action button
     * @return The Snackbar instance
     */
    public static Snackbar showSnackbarWithAction(View view, String message, int duration, 
                                                 String actionText, View.OnClickListener listener) {
        if (view == null || message == null || message.isEmpty() || 
            actionText == null || actionText.isEmpty() || listener == null) {
            return null;
        }
        
        Snackbar snackbar = Snackbar.make(view, message, duration);
        snackbar.setAction(actionText, listener);
        snackbar.show();
        return snackbar;
    }
    
    /**
     * Show a Snackbar message with an action using resource IDs
     * 
     * @param view The view to attach the Snackbar to
     * @param messageResId The resource ID of the message to show
     * @param duration The duration (Snackbar.LENGTH_SHORT, Snackbar.LENGTH_LONG, or Snackbar.LENGTH_INDEFINITE)
     * @param actionTextResId The resource ID of the text for the action button
     * @param listener The click listener for the action button
     * @return The Snackbar instance
     */
    public static Snackbar showSnackbarWithAction(View view, @StringRes int messageResId, int duration, 
                                                 @StringRes int actionTextResId, View.OnClickListener listener) {
        if (view == null || listener == null) {
            return null;
        }
        
        Snackbar snackbar = Snackbar.make(view, messageResId, duration);
        snackbar.setAction(actionTextResId, listener);
        snackbar.show();
        return snackbar;
    }
}
