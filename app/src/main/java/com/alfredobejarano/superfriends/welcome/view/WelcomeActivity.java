package com.alfredobejarano.superfriends.welcome.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.alfredobejarano.superfriends.R;
import com.alfredobejarano.superfriends.common.ViewModelState;
import com.alfredobejarano.superfriends.common.view.BaseActivity;
import com.alfredobejarano.superfriends.welcome.viewmodel.WelcomeViewModel;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.util.Arrays;

/**
 * Defines the welcome activity, it contains a button for a user to
 * perform a Facebook login.
 *
 * @author Alfredo Bejarano
 */
public class WelcomeActivity extends BaseActivity {
    /**
     * Property that defines the facebook login button found in the layout.
     */
    LoginButton mFacebookLoginButton;
    /**
     * Property that defines the ViewModel for this view.
     */
    private WelcomeViewModel viewModel;
    /**
     * Property that defines the necessary permissions from Facebook for the app to work.
     */
    private String[] permissions = {"user_friends", "public_profile"};
    /**
     * Property that initializes a CallbackManager for the login button.
     */
    private CallbackManager mLoginCallbackManager = CallbackManager.Factory.create();
    /**
     * Property that defines a FacebookCallback instance that will receive a {@link LoginResult}.
     * The LoginResult instance contains the user token.
     */
    private FacebookCallback<LoginResult> mLoginResult = new FacebookCallback<LoginResult>() {
        /**
         * If the result was retrieved successfully, store it in the local storage database.
         * @param loginResult The result retrieved from facebook.
         */
        @Override
        public void onSuccess(LoginResult loginResult) {
            viewModel.storeFacebookToken(loginResult.getAccessToken().getToken());
        }

        /**
         * This method is called when a user cancels the facebook login.
         */
        @Override
        public void onCancel() {
            displayError(R.string.facebook_login_cancelled);
        }

        /**
         * Displays the localized message of an error if something
         * went wrong in the Facebook login process.
         * @param error The exception thrown by the login process.
         */
        @Override
        public void onError(FacebookException error) {
            displayError(error.getLocalizedMessage());
        }
    };

    /**
     * Returns the layout id for this activity.
     *
     * @return Integer value of the id for this layout.
     */
    @Override
    protected Integer getLayoutId() {
        return R.layout.activity_welcome;
    }

    /**
     * Creates this activity, retrieves this View ViewModel and assigns
     * the necessary callbacks to the facebook login button.
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Create / retrieve the this activity ViewModel.
        viewModel = ViewModelProviders.of(this).get(WelcomeViewModel.class);
        // Observe the changes in the ViewModel state.
        viewModel.state.observe(this, new Observer<ViewModelState>() {
            @Override
            public void onChanged(@Nullable ViewModelState viewModelState) {
                // Display or hide the loading view depending on the ViewModel state,
                displayLoadingView(viewModelState == ViewModelState.STATE_BUSY);
            }
        });
        // Create a CallbackManager for the facebook login manager.
        mLoginCallbackManager = CallbackManager.Factory.create();
        // Fetch the facebook login button.
        mFacebookLoginButton = findViewById(R.id.welcome_fb_login_button);
        // Set the read permissions necessary for the app to work.
        mFacebookLoginButton.setReadPermissions(Arrays.asList(permissions));
        // Register the button callback.
        mFacebookLoginButton.registerCallback(mLoginCallbackManager, mLoginResult);
    }

    /**
     * Performs a click to the hidden Facebook Login Button.
     *
     * @param view The view calling this method.
     */
    public void performFacebookLogin(View view) {
        mFacebookLoginButton.performClick();
    }

    /**
     * Reports the result of the FacebookLogin to the CallbackManager.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mLoginCallbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
}
