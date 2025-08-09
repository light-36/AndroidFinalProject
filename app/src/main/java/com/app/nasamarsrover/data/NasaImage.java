package com.app.nasamarsrover.data;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

/**
 * Entity class representing a NASA image.
 * This class is used for storing NASA images in the Room database.
 */
@Entity(tableName = "nasa_images")
public class NasaImage implements Serializable {

    @PrimaryKey
    @NonNull
    private String date;
    private String title;
    private String url;
    private String hdUrl;
    private String explanation;
    private String copyright;
    private String mediaType;
    private long timestamp;

    /**
     * Default constructor required by Room
     */
    public NasaImage() {
    }

    /**
     * Constructor for creating a new NASA image
     * 
     * @param date The date of the image (YYYY-MM-DD)
     * @param title The title of the image
     * @param url The URL of the image
     * @param hdUrl The HD URL of the image
     * @param explanation The explanation of the image
     * @param copyright The copyright information
     * @param mediaType The media type (image or video)
     */
    public NasaImage(@NonNull String date, String title, String url, String hdUrl, 
                    String explanation, String copyright, String mediaType) {
        this.date = date;
        this.title = title;
        this.url = url;
        this.hdUrl = hdUrl;
        this.explanation = explanation;
        this.copyright = copyright;
        this.mediaType = mediaType;
        this.timestamp = System.currentTimeMillis();
    }

    @NonNull
    public String getDate() {
        return date;
    }

    public void setDate(@NonNull String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHdUrl() {
        return hdUrl;
    }

    public void setHdUrl(String hdUrl) {
        this.hdUrl = hdUrl;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
