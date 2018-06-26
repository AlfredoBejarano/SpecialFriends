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
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import butterknife.BindView;

public class WelcomeActivity extends BaseActivity {
    @BindView(R.id.welcome_fb_login_button)
    LoginButton mFacebookLoginButton;
    private WelcomeViewModel viewModel;
    private CallbackManager mLoginCallbackManager = CallbackManager.Factory.create();
    private FacebookCallback<LoginResult> mLoginResult = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            viewModel.storeFacebookToken(loginResult.getAccessToken().getToken());
        }

        @Override
        public void onCancel() {
            displayError(R.string.facebook_login_cancelled);
        }

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
                displayLoadingView(viewModelState == ViewModelState.STATE_BUSY);
            }
        });
        // Create a CallbackManager for the facebook login manager.
        mLoginCallbackManager = CallbackManager.Factory.create();
        // Assign this login manager and a facebook callback to the login manager.
        LoginManager.getInstance().registerCallback(mLoginCallbackManager, mLoginResult);
    }

    /**
     * Performs a click to the hidden Facebook Login Button.
     *
     * @param view The view calling this method.
     */
    public void performFacebookLogin(View view) {
        mFacebookLoginButton.performClick();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mLoginCallbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
}
