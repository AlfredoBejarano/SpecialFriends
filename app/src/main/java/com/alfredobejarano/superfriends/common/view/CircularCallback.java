package com.alfredobejarano.superfriends.common.view;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.widget.ImageView;

import com.alfredobejarano.superfriends.R;
import com.squareup.picasso.Callback;

/**
 * This class defines a behaviour of drawing a retrieved image from Picasso as a circle.
 * @author Alfredo Bejarano
 */
public class CircularCallback implements Callback {
    /**
     * ImageView to be drawn the image into.
     */
    private ImageView mImageView;

    /**
     * Constructor that receives a reference to an {@link ImageView}.
     * @param imageView The ImageView to be used to drawn the retrieved picture.
     */
    public CircularCallback(ImageView imageView) {
        mImageView = imageView;
    }

    /**
     * This method is called when the image was retrieved successfully.
     */
    @Override
    public void onSuccess() {
        // Get the retrieved image from the ImageView.
        Bitmap imageBitmap = ((BitmapDrawable) mImageView.getDrawable()).getBitmap();
        // Convert the Bitmap into a RoundedBitmap.
        RoundedBitmapDrawable imageDrawable = RoundedBitmapDrawableFactory.create(mImageView.getContext().getResources(), imageBitmap);
        // Set the RoundedBitmap as a perfect circle.
        imageDrawable.setCircular(true);
        // Set the drawable corner radius as the half of the image width and image height.
        imageDrawable.setCornerRadius(Math.max(imageBitmap.getWidth(), imageBitmap.getHeight()) / 2.0f);
        // Render the drawable into the ImageView.
        mImageView.setImageDrawable(imageDrawable);
    }

    /**
     * Set a place holder drawable when the image was not retrieved correctly.
     */
    @Override
    public void onError() {
        // Draw the placeholder image.
        mImageView.setImageResource(R.mipmap.ic_launcher_round);
    }
}
