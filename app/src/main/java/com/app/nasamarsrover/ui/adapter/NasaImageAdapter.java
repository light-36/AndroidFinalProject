package com.app.nasamarsrover.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.nasamarsrover.R;
import com.app.nasamarsrover.data.model.NasaImage;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * RecyclerView adapter for displaying NASA images in a list.
 */
public class NasaImageAdapter extends RecyclerView.Adapter<NasaImageAdapter.NasaImageViewHolder> {
    
    private final Context context;
    private List<NasaImage> nasaImages = new ArrayList<>();
    private final OnItemClickListener listener;
    
    /**
     * Constructor for NasaImageAdapter
     * 
     * @param context The context
     * @param listener The click listener for items
     */
    public NasaImageAdapter(Context context, OnItemClickListener listener) {
        this.context = context;
        this.listener = listener;
    }
    
    @NonNull
    @Override
    public NasaImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_nasa_image, parent, false);
        return new NasaImageViewHolder(itemView);
    }
    
    @Override
    public void onBindViewHolder(@NonNull NasaImageViewHolder holder, int position) {
        NasaImage currentImage = nasaImages.get(position);
        holder.titleTextView.setText(currentImage.getTitle());
        holder.dateTextView.setText(currentImage.getDate());
        
        // Load image using Glide
        Glide.with(context)
                .load(currentImage.getUrl())
                .placeholder(R.drawable.placeholder_image)
                .error(R.drawable.error_image)
                .into(holder.imageView);
        
        // Set click listener
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(currentImage);
            }
        });
    }
    
    @Override
    public int getItemCount() {
        return nasaImages.size();
    }
    
    /**
     * Set the NASA images to display
     * 
     * @param nasaImages The list of NASA images
     */
    public void setNasaImages(List<NasaImage> nasaImages) {
        this.nasaImages = nasaImages;
        notifyDataSetChanged();
    }
    
    /**
     * Get the NASA image at the specified position
     * 
     * @param position The position
     * @return The NASA image at the specified position
     */
    public NasaImage getNasaImageAt(int position) {
        return nasaImages.get(position);
    }
    
    /**
     * ViewHolder for NASA image items
     */
    static class NasaImageViewHolder extends RecyclerView.ViewHolder {
        private final TextView titleTextView;
        private final TextView dateTextView;
        private final ImageView imageView;
        
        NasaImageViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.text_title);
            dateTextView = itemView.findViewById(R.id.text_date);
            imageView = itemView.findViewById(R.id.image_thumbnail);
        }
    }
    
    /**
     * Interface for item click events
     */
    public interface OnItemClickListener {
        void onItemClick(NasaImage nasaImage);
    }
}
