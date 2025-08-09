package com.app.nasamarsrover.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Entity class representing a NASA image of the day.
 * This class is used to store NASA image data in the Room database.
 */
@Entity(tableName = "nasa_images")
public class NasaImage {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String title;
    private String date;
    private String url;
    private String hdUrl;
    private String explanation;
    private String copyright;
    private String mediaType;

    /**
     * Default constructor for NasaImage
     */
    public NasaImage() {
    }

    /**
     * Constructor for NasaImage with all fields
     * 
     * @param title The title of the image
     * @param date The date of the image
     * @param url The URL of the image
     * @param hdUrl The HD URL of the image
     * @param explanation The explanation of the image
     * @param copyright The copyright information
     * @param mediaType The media type (image or video)
     */
    public NasaImage(String title, String date, String url, String hdUrl, String explanation, String copyright, String mediaType) {
        this.title = title;
        this.date = date;
        this.url = url;
        this.hdUrl = hdUrl;
        this.explanation = explanation;
        this.copyright = copyright;
        this.mediaType = mediaType;
    }

    /**
     * Gets the ID of the image
     * 
     * @return The ID of the image
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the ID of the image
     * 
     * @param id The ID to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Gets the title of the image
     * 
     * @return The title of the image
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the image
     * 
     * @param title The title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the date of the image
     * 
     * @return The date of the image
     */
    public String getDate() {
        return date;
    }

    /**
     * Sets the date of the image
     * 
     * @param date The date to set
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Gets the URL of the image
     * 
     * @return The URL of the image
     */
    public String getUrl() {
        return url;
    }

    /**
     * Sets the URL of the image
     * 
     * @param url The URL to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Gets the HD URL of the image
     * 
     * @return The HD URL of the image
     */
    public String getHdUrl() {
        return hdUrl;
    }

    /**
     * Sets the HD URL of the image
     * 
     * @param hdUrl The HD URL to set
     */
    public void setHdUrl(String hdUrl) {
        this.hdUrl = hdUrl;
    }

    /**
     * Gets the explanation of the image
     * 
     * @return The explanation of the image
     */
    public String getExplanation() {
        return explanation;
    }

    /**
     * Sets the explanation of the image
     * 
     * @param explanation The explanation to set
     */
    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    /**
     * Gets the copyright information
     * 
     * @return The copyright information
     */
    public String getCopyright() {
        return copyright;
    }

    /**
     * Sets the copyright information
     * 
     * @param copyright The copyright information to set
     */
    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    /**
     * Gets the media type
     * 
     * @return The media type
     */
    public String getMediaType() {
        return mediaType;
    }

    /**
     * Sets the media type
     * 
     * @param mediaType The media type to set
     */
    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }
}
