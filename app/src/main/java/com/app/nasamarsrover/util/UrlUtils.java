package com.app.nasamarsrover.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Utility class for URL operations.
 * This class provides methods for opening URLs in browser.
 */
public class UrlUtils {

    /**
     * Open a URL in the browser
     * 
     * @param context The context
     * @param url The URL to open
     * @return True if the URL was opened successfully, false otherwise
     */
    public static boolean openUrlInBrowser(Context context, String url) {
        if (context == null || url == null || url.isEmpty()) {
            return false;
        }
        
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            context.startActivity(intent);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Check if a URL is valid
     * 
     * @param url The URL to check
     * @return True if the URL is valid, false otherwise
     */
    public static boolean isValidUrl(String url) {
        if (url == null || url.isEmpty()) {
            return false;
        }
        
        try {
            Uri uri = Uri.parse(url);
            return uri.getScheme() != null && (uri.getScheme().equals("http") || uri.getScheme().equals("https"));
        } catch (Exception e) {
            return false;
        }
    }
}
