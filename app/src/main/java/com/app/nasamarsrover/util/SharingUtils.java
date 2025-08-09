package com.app.nasamarsrover.util;

import android.content.Context;
import android.content.Intent;

import com.app.nasamarsrover.R;
import com.app.nasamarsrover.api.NasaResponse;
import com.app.nasamarsrover.data.NasaImage;

import java.util.Locale;

/**
 * Utility class for sharing NASA image information.
 * This class provides methods for sharing NASA image details with other apps.
 */
public class SharingUtils {

    /**
     * Share NASA image information with other apps
     * 
     * @param context The context
     * @param nasaResponse The NASA response containing image information
     */
    public static void shareNasaImage(Context context, NasaResponse nasaResponse) {
        if (context == null || nasaResponse == null) {
            return;
        }
        
        String shareTitle = context.getString(R.string.share_title);
        String shareText = buildShareText(context, nasaResponse);
        
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, shareTitle);
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);
        
        context.startActivity(Intent.createChooser(shareIntent, context.getString(R.string.share_via)));
    }
    
    /**
     * Share saved NASA image information with other apps
     * 
     * @param context The context
     * @param nasaImage The saved NASA image
     */
    public static void shareSavedImage(Context context, NasaImage nasaImage) {
        if (context == null || nasaImage == null) {
            return;
        }
        
        String shareTitle = context.getString(R.string.share_title);
        String shareText = buildShareText(context, nasaImage);
        
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, shareTitle);
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);
        
        context.startActivity(Intent.createChooser(shareIntent, context.getString(R.string.share_via)));
    }
    
    /**
     * Build the share text for a NASA response
     * 
     * @param context The context
     * @param nasaResponse The NASA response
     * @return The formatted share text
     */
    private static String buildShareText(Context context, NasaResponse nasaResponse) {
        Locale locale = context.getResources().getConfiguration().getLocales().get(0);
        String formattedDate = DateUtils.formatDateForDisplay(nasaResponse.getDate(), locale);
        
        StringBuilder shareText = new StringBuilder();
        shareText.append(nasaResponse.getTitle()).append("\n\n");
        shareText.append(context.getString(R.string.date_label)).append(" ").append(formattedDate).append("\n\n");
        
        if (nasaResponse.getCopyright() != null && !nasaResponse.getCopyright().isEmpty()) {
            shareText.append(context.getString(R.string.copyright_label)).append(" ").append(nasaResponse.getCopyright()).append("\n\n");
        }
        
        shareText.append(nasaResponse.getExplanation()).append("\n\n");
        shareText.append(context.getString(R.string.image_url_label)).append(" ").append(nasaResponse.getUrl()).append("\n\n");
        
        if (nasaResponse.getHdUrl() != null && !nasaResponse.getHdUrl().isEmpty()) {
            shareText.append(context.getString(R.string.hd_image_url_label)).append(" ").append(nasaResponse.getHdUrl()).append("\n\n");
        }
        
        shareText.append(context.getString(R.string.shared_via_app));
        
        return shareText.toString();
    }
    
    /**
     * Build the share text for a saved NASA image
     * 
     * @param context The context
     * @param nasaImage The saved NASA image
     * @return The formatted share text
     */
    private static String buildShareText(Context context, NasaImage nasaImage) {
        Locale locale = context.getResources().getConfiguration().getLocales().get(0);
        String formattedDate = DateUtils.formatDateForDisplay(nasaImage.getDate(), locale);
        
        StringBuilder shareText = new StringBuilder();
        shareText.append(nasaImage.getTitle()).append("\n\n");
        shareText.append(context.getString(R.string.date_label)).append(" ").append(formattedDate).append("\n\n");
        
        if (nasaImage.getCopyright() != null && !nasaImage.getCopyright().isEmpty()) {
            shareText.append(context.getString(R.string.copyright_label)).append(" ").append(nasaImage.getCopyright()).append("\n\n");
        }
        
        shareText.append(nasaImage.getExplanation()).append("\n\n");
        shareText.append(context.getString(R.string.image_url_label)).append(" ").append(nasaImage.getUrl()).append("\n\n");
        
        if (nasaImage.getHdUrl() != null && !nasaImage.getHdUrl().isEmpty()) {
            shareText.append(context.getString(R.string.hd_image_url_label)).append(" ").append(nasaImage.getHdUrl()).append("\n\n");
        }
        
        shareText.append(context.getString(R.string.shared_via_app));
        
        return shareText.toString();
    }
}
