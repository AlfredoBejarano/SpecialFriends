package com.alfredobejarano.superfriends.profile.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.alfredobejarano.superfriends.R;
import com.alfredobejarano.superfriends.common.viewmodel.ViewModelState;
import com.alfredobejarano.superfriends.common.view.BaseActivity;
import com.alfredobejarano.superfriends.databinding.ActivityProfileBinding;
import com.alfredobejarano.superfriends.profile.viewmodel.ProfileViewModel;

/**
 * This activity displays the information of a selected Friend.
 * Here the user can also edit that friend information.
 */
public class ProfileActivity extends BaseActivity {
    private ProfileViewModel mViewModel;
    private Menu mMenu;
    public static final String FRIEND_ID = "friend_id";

    /**
     * Creates an Intent for easy navigation to this activity.
     * @param ctx The context calling this activity.
     * @param friendId The Id of the friend to load.
     * @return The Intent for navigating to this view.
     */
    public static Intent provideIntent(Context ctx, Integer friendId) {
        return new Intent(ctx, ProfileActivity.class)
                .putExtra(FRIEND_ID, friendId);
    }

    /**
     * Creates the activity and binds a ViewModel data to it.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Creates / retrieves the ViewModel assigned to this activity.
        mViewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);
        // Creates the DataBinding for this activity.
        ActivityProfileBinding binding = ActivityProfileBinding.inflate(getLayoutInflater());
        // Defines to the binding from which ViewModel it is going to read data.
        binding.setProfile(mViewModel);
        // Sets the DataBinging life cycle owner.
        binding.setLifecycleOwner(this);
        // Sets the content view for this activity.
        setContentView(binding.getRoot());
        // Sets the Toolbar for this activity.
        setSupportActionBar((Toolbar) findViewById(R.id.profile_toolbar));
        // Setup the toolbar.
        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle("");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        // Observers changes on the ViewModel for displaying / hiding the loading view.
        mViewModel.state.observe(this, new Observer<ViewModelState>() {
            @Override
            public void onChanged(@Nullable ViewModelState viewModelState) {
                displayLoadingView(viewModelState == ViewModelState.STATE_BUSY);
            }
        });
        // Observers changes on the ViewModel for displaying an error message.
        mViewModel.errorMessage.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer message) {
                displayError(message);
            }
        });
        // Observers changes on the ViewModel for changing the current menu drawable.
        mViewModel.favoriteMenu.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean status) {
                // Change the favorite value of the friend.
                if(mViewModel.friend != null) {
                    mViewModel.friend.setFavorite(status);
                }
                // Check if the menu is not null.
                if (mMenu != null && status != null) {
                    // Get the favorite item and change the icon depending on the value.
                    mMenu.getItem(0).setIcon(status ? R.drawable.ic_star_menu : R.drawable.ic_star_border_menu);
                }
            }
        });
        // Fetches the friend from the database.
        fetchFriendInformation();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Integer getLayoutId() {
        return R.layout.activity_profile;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Give access to this activity menu instance.
        mMenu = menu;
        // Inflate the menu layout.
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        // Set the correct drawable for the menu option.
        if(mViewModel.friend != null) {
            mMenu.getItem(0).setIcon(mViewModel.friend.isFavorite() ? R.drawable.ic_star_menu : R.drawable.ic_star_border_menu);
        }
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Check if the favorite option has been clicked.
        if (item.getItemId() == R.id.menu_favorite) {
            // Get the status the favorite menu.
            Boolean isFavorite = mViewModel.favoriteMenu.getValue();
            // Check if the status is not null.
            if (isFavorite != null) {
                // Assign the new favorite menu status.
                mViewModel.favoriteMenu.setValue(!isFavorite);
            }
        } else {
            onBackPressed();
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if(mViewModel.friend != null) {
            // Get the editable widgets.
            EditText notes = findViewById(R.id.profile_notes_input);
            EditText birthday = findViewById(R.id.profile_birthday_input);
            EditText phoneNumber = findViewById(R.id.profile_phone_number_input);
            // Assign the value of those widgets to the friend located in the ViewModel.
            mViewModel.friend.setNote(String.valueOf(notes.getText()));
            mViewModel.friend.setBirthday(String.valueOf(birthday.getText()));
            mViewModel.friend.setPhoneNumber(String.valueOf(phoneNumber.getText()));
            // Store the friend's new values.
            mViewModel.storeFriend();
        } else {
            super.onBackPressed();
        }
    }

    /**
     * Retrieves the friend ID from the extras and fetches it from the local database.
     */
    private void fetchFriendInformation() {
        // Get a null-safe Intent.
        Intent extrasIntent = (getIntent() != null ? getIntent() : new Intent());
        // Get the friend Id extra from said intent.
        Integer friendId = extrasIntent.getIntExtra(FRIEND_ID, 0 );
        // Retrieve the friend from the database.
        mViewModel.getFriendProfile(friendId);
    }
}
