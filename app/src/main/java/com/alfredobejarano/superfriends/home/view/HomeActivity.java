package com.alfredobejarano.superfriends.home.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.alfredobejarano.superfriends.R;
import com.alfredobejarano.superfriends.common.ViewModelState;
import com.alfredobejarano.superfriends.common.model.Friend;
import com.alfredobejarano.superfriends.common.view.BaseActivity;
import com.alfredobejarano.superfriends.common.view.CircularCallback;
import com.alfredobejarano.superfriends.databinding.ActivityHomeBinding;
import com.alfredobejarano.superfriends.home.adapter.FriendsAdapter;
import com.alfredobejarano.superfriends.home.viewmodel.HomeViewModel;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * This activity displays a pair pof RecyclerView with a user
 * favorite friends and all his friends.
 *
 * @author Alfredo Bejarano
 */
public class HomeActivity extends BaseActivity {
    private HomeViewModel homeViewModel;
    private ImageView profilePicture;
    private RecyclerView friendsList, favoriteFriendsList;

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
        setSupportActionBar((Toolbar) findViewById(R.id.home_toolbar));
        initializeProfilePictureView();
        initializeLists();
        homeViewModel.state.observe(this, new Observer<ViewModelState>() {
            @Override
            public void onChanged(@Nullable ViewModelState viewModelState) {
                displayLoadingView(viewModelState == ViewModelState.STATE_BUSY);
            }
        });
        ((TextView) findViewById(R.id.home_search_view)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Empty unused method.
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Empty unused method.
            }

            @Override
            public void afterTextChanged(Editable s) {
                ((FriendsAdapter) friendsList.getAdapter()).searchForFriends(String.valueOf(s));
            }
        });
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

    /**
     * Assigns the layout managers for the RecyclerView lists.
     */
    private void initializeLists() {
        // Get the RecyclerView that will display all the available friends.
        friendsList = findViewById(R.id.home_friends);
        // Get the RecyclerView that will display the favorite friends.
        favoriteFriendsList = findViewById(R.id.home_favorite_friends);
        // Set the
        friendsList.setAdapter(new FriendsAdapter());
        homeViewModel.friends.observe(this, new Observer<List<Friend>>() {
            @Override
            public void onChanged(@Nullable List<Friend> friends) {
                ((FriendsAdapter) friendsList.getAdapter()).setFriends(friends);
            }
        });
        // Set a LinearLayoutManager for the friends list.
        friendsList.setLayoutManager(new LinearLayoutManager(this));
        // Create another LinearLayoutManager instance.
        LinearLayoutManager manager = new LinearLayoutManager(this);
        // Change its orientation to Horizontal.
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        // Set the horizontal LayoutManager to the Favorite friends RecyclerView.
        favoriteFriendsList.setLayoutManager(manager);
        homeViewModel.fetchFriends();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_log_out) {
            homeViewModel.closeSession();
        }
        return true;
    }
}
