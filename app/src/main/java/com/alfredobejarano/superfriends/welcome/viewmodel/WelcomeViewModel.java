package com.alfredobejarano.superfriends.welcome.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import com.alfredobejarano.superfriends.SuperFriendsApplication;
import com.alfredobejarano.superfriends.common.ViewModelState;
import com.alfredobejarano.superfriends.common.repository.SuperFriendsDatabase;
import com.alfredobejarano.superfriends.common.repository.UserTokenDao;
import com.alfredobejarano.superfriends.welcome.model.UserToken;
import com.alfredobejarano.superfriends.welcome.view.WelcomeActivity;

import java.util.List;

public class WelcomeViewModel extends AndroidViewModel {
    /**
     * Property that defines if the ViewModel is busy or not.
     */
    public MutableLiveData<ViewModelState> state = new MutableLiveData<>();

    /**
     * {@inheritDoc}
     */
    public WelcomeViewModel(@NonNull Application application) {
        super(application);
        state.setValue(ViewModelState.STATE_READY);
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
            // Retrieve the application database.
            SuperFriendsDatabase database = SuperFriendsApplication.superFriendsDatabase;
            // Check if the database is not null.
            if (database != null) {
                // Retrieve the token user dao.
                final UserTokenDao dao = database.getUserTokenDao();
                // Check if the dao is not null.
                if (dao != null) {
                    // Retrieve the application context.
                    final Context context = getApplication().getApplicationContext();
                    // Execute a new thread to perform a database operation in it.
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            // Prepare a looper in the thread.
                            Looper.prepare();
                            // Store the token.
                            dao.storeUserToken(new UserToken(token));
                            // Run code in the UI thread.
                            new Handler(context.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    context.startActivity(new Intent(context, WelcomeViewModel.class));
                                    state.setValue(ViewModelState.STATE_READY);
                                }
                            });
                        }
                    }).start();
                }
            }
        }
    }

    /**
     * Checks if the is just one facebook session to operate with.
     */
    public void checkSessions() {
        // Check that the ViewModel is ready to perform an operation.
        if (state.getValue() == ViewModelState.STATE_READY) {
            // Indicate that the ViewModel is busy.
            final Context context = getApplication().getApplicationContext();
            final SuperFriendsDatabase database = SuperFriendsApplication.superFriendsDatabase;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    if (database != null) {
                        UserTokenDao dao = database.getUserTokenDao();
                        if (dao != null) {
                            List<UserToken> tokens = dao.getUserTokens();
                            if (tokens != null && tokens.size() == 1) {
                                context.startActivity(new Intent(context, WelcomeActivity.class));
                                state.setValue(ViewModelState.STATE_READY);
                            }
                        }
                    }
                }
            }).start();
        }
    }
}
