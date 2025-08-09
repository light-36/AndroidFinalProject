package com.app.nasamarsrover.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Utility class for date operations.
 * This class provides methods for formatting and validating dates.
 */
public class DateUtils {

    private static final String DATE_FORMAT = "yyyy-MM-dd";
    
    /**
     * Format a date to the NASA API format (YYYY-MM-DD)
     * 
     * @param year The year
     * @param month The month (0-11)
     * @param day The day of the month
     * @return The formatted date string
     */
    public static String formatDate(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT, Locale.US);
        return sdf.format(calendar.getTime());
    }
    
    /**
     * Format a date to a user-friendly format
     * 
     * @param dateString The date string in YYYY-MM-DD format
     * @param locale The locale to use for formatting
     * @return The formatted date string
     */
    public static String formatDateForDisplay(String dateString, Locale locale) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat(DATE_FORMAT, Locale.US);
            Date date = inputFormat.parse(dateString);
            
            SimpleDateFormat outputFormat = new SimpleDateFormat("MMMM d, yyyy", locale);
            return outputFormat.format(date);
        } catch (ParseException e) {
            return dateString;
        }
    }
    
    /**
     * Get today's date in the NASA API format (YYYY-MM-DD)
     * 
     * @return Today's date string
     */
    public static String getTodayDate() {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT, Locale.US);
        return sdf.format(new Date());
    }
    
    /**
     * Validate a date string
     * 
     * @param dateString The date string to validate
     * @return True if the date is valid, false otherwise
     */
    public static boolean isValidDate(String dateString) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT, Locale.US);
        sdf.setLenient(false);
        
        try {
            sdf.parse(dateString);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
    
    /**
     * Check if a date is in the future
     * 
     * @param dateString The date string in YYYY-MM-DD format
     * @return True if the date is in the future, false otherwise
     */
    public static boolean isFutureDate(String dateString) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT, Locale.US);
            Date date = sdf.parse(dateString);
            Date today = new Date();
            
            return date != null && date.after(today);
        } catch (ParseException e) {
            return false;
        }
    }
    
    /**
     * Check if a date is before the NASA APOD API start date (June 16, 1995)
     * 
     * @param dateString The date string in YYYY-MM-DD format
     * @return True if the date is before the API start date, false otherwise
     */
    public static boolean isBeforeApiStartDate(String dateString) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT, Locale.US);
            Date date = sdf.parse(dateString);
            Date startDate = sdf.parse("1995-06-16");
            
            return date != null && date.before(startDate);
        } catch (ParseException e) {
            return false;
        }
    }
}
