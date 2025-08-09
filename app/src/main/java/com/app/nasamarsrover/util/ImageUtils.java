package com.app.nasamarsrover.util;

import android.content.Context;
import android.widget.ImageView;

import com.app.nasamarsrover.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

/**
 * Utility class for image operations.
 * This class provides methods for loading and managing images.
 */
public class ImageUtils {

    /**
     * Load an image from a URL into an ImageView
     * 
     * @param context The context
     * @param imageUrl The image URL
     * @param imageView The ImageView to load the image into
     */
    public static void loadImage(Context context, String imageUrl, ImageView imageView) {
        if (context == null || imageUrl == null || imageUrl.isEmpty() || imageView == null) {
            return;
        }
        
        Glide.with(context)
                .load(imageUrl)
                .placeholder(R.drawable.placeholder_image)
                .error(R.drawable.error_image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }
    
    /**
     * Load an image from a URL into an ImageView with custom options
     * 
     * @param context The context
     * @param imageUrl The image URL
     * @param imageView The ImageView to load the image into
     * @param requestOptions Custom request options
     */
    public static void loadImageWithOptions(Context context, String imageUrl, ImageView imageView, RequestOptions requestOptions) {
        if (context == null || imageUrl == null || imageUrl.isEmpty() || imageView == null) {
            return;
        }
        
        Glide.with(context)
                .load(imageUrl)
                .apply(requestOptions)
                .placeholder(R.drawable.placeholder_image)
                .error(R.drawable.error_image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }
    
    /**
     * Check if a URL is an image URL
     * 
     * @param url The URL to check
     * @return True if the URL is an image URL, false otherwise
     */
    public static boolean isImageUrl(String url) {
        if (url == null || url.isEmpty()) {
            return false;
        }
        
        String lowerCaseUrl = url.toLowerCase();
        return lowerCaseUrl.endsWith(".jpg") || 
               lowerCaseUrl.endsWith(".jpeg") || 
               lowerCaseUrl.endsWith(".png") || 
               lowerCaseUrl.endsWith(".gif") || 
               lowerCaseUrl.endsWith(".bmp") || 
               lowerCaseUrl.endsWith(".webp");
    }
}
