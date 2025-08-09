package com.app.nasamarsrover.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

/**
 * Utility class for handling permissions.
 * This class provides methods for checking and requesting permissions.
 */
public class PermissionUtils {

    /**
     * Check if a permission is granted
     * 
     * @param context The context
     * @param permission The permission to check
     * @return True if the permission is granted, false otherwise
     */
    public static boolean isPermissionGranted(Context context, String permission) {
        if (context == null || permission == null || permission.isEmpty()) {
            return false;
        }
        
        return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
    }
    
    /**
     * Request a permission
     * 
     * @param activity The activity
     * @param permission The permission to request
     * @param requestCode The request code
     */
    public static void requestPermission(Activity activity, String permission, int requestCode) {
        if (activity == null || permission == null || permission.isEmpty()) {
            return;
        }
        
        ActivityCompat.requestPermissions(activity, new String[]{permission}, requestCode);
    }
    
    /**
     * Request multiple permissions
     * 
     * @param activity The activity
     * @param permissions The permissions to request
     * @param requestCode The request code
     */
    public static void requestPermissions(Activity activity, String[] permissions, int requestCode) {
        if (activity == null || permissions == null || permissions.length == 0) {
            return;
        }
        
        ActivityCompat.requestPermissions(activity, permissions, requestCode);
    }
    
    /**
     * Check if a permission should show rationale
     * 
     * @param activity The activity
     * @param permission The permission to check
     * @return True if rationale should be shown, false otherwise
     */
    public static boolean shouldShowRationale(Activity activity, String permission) {
        if (activity == null || permission == null || permission.isEmpty()) {
            return false;
        }
        
        return ActivityCompat.shouldShowRequestPermissionRationale(activity, permission);
    }
}
