package com.alfredobejarano.superfriends.welcome.viewmodel;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import com.alfredobejarano.superfriends.SuperFriendsApplication;
import com.alfredobejarano.superfriends.common.ViewModelState;
import com.alfredobejarano.superfriends.common.repository.SuperFriendsDatabase;
import com.alfredobejarano.superfriends.common.viewmodel.BaseViewModel;
import com.alfredobejarano.superfriends.home.view.HomeActivity;
import com.alfredobejarano.superfriends.welcome.model.UserToken;

import java.util.List;

/**
 * ViewModel that handles actions for logging a user in his facebook account.
 * Retrieve the token from that log in and store it in the local database.
 *
 * @author Alfredo Bejarano
 */
public class WelcomeViewModel extends BaseViewModel {

    /**
     * {@inheritDoc}
     */
    public WelcomeViewModel(@NonNull Application application) {
        super(application);
        checkSessions();
    }

    /**
     * Receives a token value and stores it in the local database.
     *
     * @param token The token value retrieved from facebook.
     */
    public void storeFacebookToken(final String token) {
        // Check that the ViewModel is ready to perform an operation.
        if (state.getValue() == ViewModelState.STATE_READY) {
            // Indicate that the ViewModel is busy.
            state.setValue(ViewModelState.STATE_BUSY);
            // Check if the database is not null.
            if (isDatabaseUsable()) {
                final Context context = getApplication().getApplicationContext();
                // Execute a new thread to perform a database operation in it.
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // Prepare a looper in the thread.
                        Looper.prepare();
                        // Store the token.
                        tokenDao.storeUserToken(new UserToken(token));
                        // Run code in the UI thread.
                        new Handler(context.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                context.startActivity(new Intent(context, HomeActivity.class));
                                state.setValue(ViewModelState.STATE_READY);
                            }
                        });
                    }
                }).start();
            }
        }
    }

    /**
     * Checks if the is just one facebook session to operate with.
     */
    private void checkSessions() {
        // Check that the ViewModel is ready to perform an operation.
        if (state.getValue() == ViewModelState.STATE_READY) {
            // Indicate that the ViewModel is busy.
            final Context context = getApplication().getApplicationContext();
            final SuperFriendsDatabase database = SuperFriendsApplication.superFriendsDatabase;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    // Checks if the database is ready to use.
                    if (isDatabaseUsable() != null) {
                        // Retrieve all the stored tokens.
                        List<UserToken> tokens = tokenDao.getUserTokens();
                        // Check if there is only one session. Multiple sessions are not allowed.
                        if (tokens != null && tokens.size() == 1) {
                            // Start the HomeActivity if just one token exists.
                            context.startActivity(new Intent(context, HomeActivity.class));
                        } else {
                            // Delete all the tokens.
                            tokenDao.deleteAllUserTokens();
                        }
                        // Notify that the ViewModel is ready to perform more operations.
                        state.postValue(ViewModelState.STATE_READY);
                    }
                }
            }).start();
        }
    }
}
