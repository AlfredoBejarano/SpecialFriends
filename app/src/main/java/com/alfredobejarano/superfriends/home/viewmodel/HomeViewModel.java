package com.alfredobejarano.superfriends.home.viewmodel;

import android.app.Application;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.alfredobejarano.superfriends.common.viewmodel.BaseViewModel;
import com.alfredobejarano.superfriends.home.model.UserInformation;
import com.facebook.Profile;

/**
 * Simple {@link BaseViewModel} class that handles the retrieval of
 * a user information and friends. It also stores this friends in the
 * local storage.
 */
public class HomeViewModel extends BaseViewModel {
    public UserInformation userInformation = new UserInformation();

    /**
     * {@inheritDoc}
     */
    public HomeViewModel(@NonNull Application application) {
        super(application);
        fetchUserProfile();
    }

    /**
     * Fetchs the user information from the active session profile.
     */
    private void fetchUserProfile() {
        new Thread((new Runnable() {
            @Override
            public void run() {
                Profile user = Profile.getCurrentProfile();
                if (user != null) {
                    userInformation.name.postValue(user.getName());
                    userInformation.picture.postValue(user.getProfilePictureUri(200, 200));
                }
            }
        })).start();
    }
}
