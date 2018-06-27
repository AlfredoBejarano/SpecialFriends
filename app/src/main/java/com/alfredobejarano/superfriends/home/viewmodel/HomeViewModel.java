package com.alfredobejarano.superfriends.home.viewmodel;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;

import com.alfredobejarano.superfriends.common.ViewModelState;
import com.alfredobejarano.superfriends.common.viewmodel.BaseViewModel;
import com.alfredobejarano.superfriends.home.model.UserInformation;
import com.alfredobejarano.superfriends.welcome.view.WelcomeActivity;
import com.facebook.Profile;
import com.facebook.login.LoginManager;

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
     * Fetches the user information from the active session profile.
     */
    private void fetchUserProfile() {
        // Report that the ViewModel is not going to be able to perform operations.
        state.setValue(ViewModelState.STATE_BUSY);
        // Perform this operation in a worker thread, as it is going to be recursive. Preventing the UI thread from freezing.
        new Thread((new Runnable() {
            @Override
            public void run() {
                // Retrieve the user facebook session.
                Profile user = Profile.getCurrentProfile();
                // Check if the session profile is not null.
                if (user != null) {
                    // Assign the user name as the LiveData value.
                    userInformation.name.postValue(user.getName());
                    // Assign the user picture as the LiveData value.
                    userInformation.picture.postValue(user.getProfilePictureUri(200, 200));
                    // Notify that this ViewModel is going to be able to perform more operations.
                    state.postValue(ViewModelState.STATE_READY);
                } else {
                    // In case that the user is null, call this method again.
                    fetchUserProfile();
                }
            }
        })).start();
    }

    /**
     * Closes the active facebook session and deletes all the records from the local database.
     */
    public void closeSession() {
        // Check if the ViewModel is ready to perform an operation.
        if (state.getValue() == ViewModelState.STATE_READY) {
            // Report that the ViewModel is not going to be able to perform operations.
            state.setValue(ViewModelState.STATE_BUSY);
            // Execute database operations in a worker thread.
            new Thread(new Runnable() {
                @Override
                public void run() {
                    // Retrieve the ViewModel context.
                    final Context context = getApplication().getApplicationContext();
                    // Check if the database is usable.
                    if (isDatabaseUsable()) {
                        // Delete all the friends records.
                        friendDao.deleteAllFriends();
                        // Delete all the user tokens records.
                        tokenDao.deleteAllUserTokens();
                        // Log the active facebook session out.
                        LoginManager.getInstance().logOut();
                        // Report that the ViewModel is ready to perform more operations.
                        state.postValue(ViewModelState.STATE_READY);
                        // Call the UI thread.
                        new Handler(context.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                // Create an Intent for going to the WelcomeActivity.
                                Intent welcomeIntent = new Intent(context, WelcomeActivity.class);
                                // Adds this flags to clear the history stack.
                                welcomeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                // Start the welcome intent.
                                context.startActivity(welcomeIntent);
                            }
                        });
                    } else {
                        // If the database is not accessible, try it again.
                        closeSession();
                    }
                }
            }).start();
        }
    }
}
