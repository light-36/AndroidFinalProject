package com.app.nasamarsrover.api;

import com.google.gson.annotations.SerializedName;

/**
 * Model class for the NASA APOD API response.
 * This class maps the JSON response from the NASA API to a Java object.
 */
public class NasaResponse {

    @SerializedName("date")
    private String date;

    @SerializedName("explanation")
    private String explanation;

    @SerializedName("hdurl")
    private String hdUrl;

    @SerializedName("media_type")
    private String mediaType;

    @SerializedName("service_version")
    private String serviceVersion;

    @SerializedName("title")
    private String title;

    @SerializedName("url")
    private String url;

    @SerializedName("copyright")
    private String copyright;

    /**
     * Get the date of the image
     * 
     * @return The date in YYYY-MM-DD format
     */
    public String getDate() {
        return date;
    }

    /**
     * Get the explanation of the image
     * 
     * @return The explanation text
     */
    public String getExplanation() {
        return explanation;
    }

    /**
     * Get the HD URL of the image
     * 
     * @return The HD URL
     */
    public String getHdUrl() {
        return hdUrl;
    }

    /**
     * Get the media type of the response
     * 
     * @return The media type (image or video)
     */
    public String getMediaType() {
        return mediaType;
    }

    /**
     * Get the service version
     * 
     * @return The service version
     */
    public String getServiceVersion() {
        return serviceVersion;
    }

    /**
     * Get the title of the image
     * 
     * @return The title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Get the URL of the image
     * 
     * @return The URL
     */
    public String getUrl() {
        return url;
    }

    /**
     * Get the copyright information
     * 
     * @return The copyright information, or null if not provided
     */
    public String getCopyright() {
        return copyright;
    }
}
