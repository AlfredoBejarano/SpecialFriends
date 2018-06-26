package com.alfredobejarano.superfriends.common.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.alfredobejarano.superfriends.R;

/**
 * Defines a placeholder for all activities in the app.
 */
public abstract class BaseActivity extends AppCompatActivity {
    private ConstraintLayout mLoadingView;

    /**
     * Creates the activity, sets the content view as the layout found in
     * view_loadingxml and inflates the child of this class into the
     * container FrameLayout.
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        // Create the activity.
        super.onCreate(savedInstanceState);
        // Set the view_loading.xmlas the layout for this activity.
        setContentView(getLayoutId());
        mLoadingView = findViewById(R.id.loading_view);
    }

    /**
     * This method should return a Integer defining a @LayoutRes value.
     *
     * @return The layout ID defining the content of this class.
     */
    protected abstract Integer getLayoutId();

    /**
     * Displays or hides the loading view.
     *
     * @param displayView true for displaying the view or false to hide it.
     */
    protected void displayLoadingView(Boolean displayView) {
        if (mLoadingView != null) {
            mLoadingView.setVisibility(displayView ? View.VISIBLE : View.GONE);
        }
    }

    /**
     * Displays a Toast containing an error message.
     *
     * @param error The error message value.
     */
    @SuppressLint("ShowToast")
    protected void displayError(Object error) {
        // Initializes a Toast instance.
        Toast t;
        if (error instanceof Integer) {
            // Display the error as an string res value if the error object is an Integer.
            t = Toast.makeText(this, (Integer) error, Toast.LENGTH_SHORT);
        } else {
            // Display the string value of the error if it is not an Integer.
            t = Toast.makeText(this, String.valueOf(error), Toast.LENGTH_SHORT);
        }

        // Display the toast.
        t.show();
    }
}
