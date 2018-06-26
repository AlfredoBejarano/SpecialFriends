package com.alfredobejarano.superfriends.common.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;

import com.alfredobejarano.superfriends.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Defines a placeholder for all activities in the app.
 */
abstract class BaseActivity extends AppCompatActivity {
    @BindView(R.id.loading_view)
    ConstraintLayout mLoadingView;

    /**
     * Creates the activity, sets the content view as the layout found in
     * activity_base.xml and inflates the child of this class into the
     * container FrameLayout.
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        // Create the activity.
        super.onCreate(savedInstanceState);
        // Set the activity_base.xml as the layout for this activity.
        setContentView(R.layout.activity_base);
        // Inflate the content of this class child into the FrameLayout.
        getLayoutInflater().inflate(getLayoutId(), (FrameLayout) findViewById(R.id.content));
        // Bind the views annotated with @BindView.
        ButterKnife.bind(this);
    }

    /**
     * This method should return a Integer defining a @LayoutRes value.
     * @return The layout ID defining the content of this class.
     */
    abstract Integer getLayoutId();

    /**
     * Displays or hides the loading view.
     * @param displayView true for displaying the view or false to hide it.
     */
    protected void displayLoadingView(Boolean displayView) {
        if(mLoadingView != null) {
            mLoadingView.setVisibility(displayView ? View.VISIBLE : View.GONE);
        }
    }
}
