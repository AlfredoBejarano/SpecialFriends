package com.alfredobejarano.superfriends.home.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.widget.ImageView;

import com.alfredobejarano.superfriends.R;
import com.alfredobejarano.superfriends.common.view.BaseActivity;
import com.alfredobejarano.superfriends.databinding.ActivityHomeBinding;
import com.alfredobejarano.superfriends.home.viewmodel.HomeViewModel;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

/**
 * This activity displays a pair pof RecyclerView with a user
 * favorite friends and all his friends.
 *
 * @author Alfredo Bejarano
 */
public class HomeActivity extends BaseActivity {
    private HomeViewModel homeViewModel;
    private ImageView profilePicture;

    /**
     * Defines the id for this activity layout.
     *
     * @return The integer id value for this activity layout.
     */
    @Override
    protected Integer getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        ActivityHomeBinding binding = ActivityHomeBinding.inflate(getLayoutInflater());
        binding.setUser(homeViewModel);
        binding.setLifecycleOwner(this);
        setContentView(binding.getRoot());
        initializeProfilePictureView();
    }

    /**
     * Observes changes in the user profile picture and renders it in the image view.
     */
    private void initializeProfilePictureView() {
        profilePicture = findViewById(R.id.home_toolbar_picture);
        final Context context = this;
        final CircularCallback circularCallback = new CircularCallback(profilePicture);
        homeViewModel.userInformation.picture.observe(this, new Observer<Uri>() {
            @Override
            public void onChanged(@Nullable Uri picture) {
                Picasso.with(context).load(picture).into(profilePicture, circularCallback);
            }
        });
    }
}
