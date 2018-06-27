package com.alfredobejarano.superfriends.home.viewmodel;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.util.Log;

import com.alfredobejarano.superfriends.common.ViewModelState;
import com.alfredobejarano.superfriends.common.model.Friend;
import com.alfredobejarano.superfriends.common.viewmodel.BaseViewModel;
import com.alfredobejarano.superfriends.home.model.UserInformation;
import com.alfredobejarano.superfriends.welcome.model.UserToken;
import com.alfredobejarano.superfriends.welcome.view.WelcomeActivity;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.Profile;
import com.facebook.login.LoginManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Simple {@link BaseViewModel} class that handles the retrieval of
 * a user information and friends. It also stores this friends in the
 * local storage.
 */
public class HomeViewModel extends BaseViewModel {
    public MutableLiveData<List<Friend>> favoriteFriends = new MutableLiveData<>();
    public MutableLiveData<List<Friend>> friends = new MutableLiveData<>();
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
        state.postValue(ViewModelState.STATE_BUSY);
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
     * Checks the state of the network and fetches friends from the local
     * storage database or from the Facebook profile.
     */
    public void fetchFriends() {
        if(friends.getValue() == null) {
            // Notify that the ViewModel is busy.
            state.postValue(ViewModelState.STATE_BUSY);
            // run a new worker thread for database and network operations.
            new Thread(new Runnable() {
                @Override
                public void run() {
                    // Fetch the local stored friends.
                    List<Friend> lFriends = friendDao.getFriends();
                    // Retrieve the friends from the facebook profile.
                    if(lFriends.isEmpty() && isNetworkAvailable()) {
                        fetchFriendsFromFacebook();
                    } else {
                        // Retrieve the friends from the local database
                        friends.postValue(lFriends);
                    }
                }
            }).start();
        }

    }

    /**
     * This method access to the {@link ConnectivityManager} and checks
     * if there is any active network. If so, it also checks such connection
     * has internet access.
     *
     * @return true if the active network exists and has internet access or false otherwise.
     */
    private Boolean isNetworkAvailable() {
        // Get the ConnectivityManager from the system.
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getApplication().getSystemService(Context.CONNECTIVITY_SERVICE);
        // Check if the connectivity manager is not null.
        if (connectivityManager != null) {
            // Retrieve the manager active connection
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            // Return if the active connection is not null and if it is connected.
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        } else {
            // Return false if no connectivity manager was provided.
            return false;
        }
    }

    // Performs a Graph API request to fetch all the user's friends.
    private void fetchFriendsFromFacebook() {
        // Notify that the ViewModel is busy performing an operation.
        state.postValue(ViewModelState.STATE_BUSY);
        // get the active facebook profile.
        Profile user = Profile.getCurrentProfile();
        // get the current profile token from the local database.
        UserToken userToken = tokenDao.getUserTokens().get(0);
        // get the current access token from facebook.
        AccessToken fbToken = AccessToken.getCurrentAccessToken();
        // Check if both the local stored token and the current token values are the same.
        if (userToken != null && fbToken.getToken().equals(userToken.getValue()) && user != null) {
            // Create the GraphRequest.
            new GraphRequest(fbToken, "/" + user.getId() + "/friends", null, HttpMethod.GET, new GraphRequest.Callback() {
                @Override
                public void onCompleted(GraphResponse response) {
                    try {
                        // Get the facebook friends from the Graph response.
                        JSONArray facebookFriends = (JSONArray) response.getJSONObject().get("data");
                        // Iterate through all the received friends.
                        for (int i = 0; i < facebookFriends.length(); i++) {
                            // Get the actual facebook friend individually.
                            JSONObject facebookFriend = (JSONObject) facebookFriends.get(i);
                            // Get the current friend facebook Id.
                            String fbId = facebookFriend.getString("id");
                            // Get the current friend name.
                            String name = facebookFriend.getString("name");
                            // Add the friend.
                            friendDao.addFriend(
                                    new Friend(name, "", "", "", false, fbId, "")
                            );
                        }
                        // Notify to the ViewModel that the friends list has been updated.
                        friends.postValue(friendDao.getFriends());
                        state.postValue(ViewModelState.STATE_READY);
                    } catch (JSONException e) {
                        // If a Json parsing error happened. Notify that the ViewModel is no longer busy.
                        state.postValue(ViewModelState.STATE_READY);
                    }
                }
            }).executeAsync(); // Execute the call asynchronously.
        } else {
            // Close the active session if the token values doesn't match.
            closeSession();
        }
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
