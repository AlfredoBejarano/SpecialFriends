package com.alfredobejarano.superfriends.home.view;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.widget.ImageView;

import com.alfredobejarano.superfriends.R;
import com.squareup.picasso.Callback;

public class CircularCallback implements Callback {
    private ImageView mImageView;

    public CircularCallback(ImageView imageView) {
        mImageView = imageView;
    }

    @Override
    public void onSuccess() {
        Bitmap imageBitmap = ((BitmapDrawable) mImageView.getDrawable()).getBitmap();
        RoundedBitmapDrawable imageDrawable = RoundedBitmapDrawableFactory.create(mImageView.getContext().getResources(), imageBitmap);
        imageDrawable.setCircular(true);
        imageDrawable.setCornerRadius(Math.max(imageBitmap.getWidth(), imageBitmap.getHeight()) / 2.0f);
        mImageView.setImageDrawable(imageDrawable);
    }

    @Override
    public void onError() {
        mImageView.setImageResource(R.mipmap.ic_launcher_round);
    }
}
