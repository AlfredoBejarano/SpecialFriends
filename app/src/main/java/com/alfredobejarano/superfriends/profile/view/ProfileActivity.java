package com.alfredobejarano.superfriends.profile.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;

import com.alfredobejarano.superfriends.R;
import com.alfredobejarano.superfriends.common.ViewModelState;
import com.alfredobejarano.superfriends.common.view.BaseActivity;
import com.alfredobejarano.superfriends.databinding.ActivityProfileBinding;
import com.alfredobejarano.superfriends.profile.viewmodel.ProfileViewModel;

/**
 * This activity displays the information of a selected Friend.
 * Here the user can also edit that friend information.
 */
public class ProfileActivity extends BaseActivity {
    private ProfileViewModel mViewModel;

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
        // Observers changes on the ViewModel for displaying / hiding the loading view.
        mViewModel.state.observe(this, new Observer<ViewModelState>() {
            @Override
            public void onChanged(@Nullable ViewModelState viewModelState) {
                displayLoadingView(viewModelState == ViewModelState.STATE_BUSY);
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Integer getLayoutId() {
        return R.layout.activity_profile;
    }
}
