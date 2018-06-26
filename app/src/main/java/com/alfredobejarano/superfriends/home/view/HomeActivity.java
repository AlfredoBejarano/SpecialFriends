package com.alfredobejarano.superfriends.home.view;

import com.alfredobejarano.superfriends.R;
import com.alfredobejarano.superfriends.common.view.BaseActivity;

/**
 * This activity displays a pair pof RecyclerView with a user
 * favorite friends and all his friends.
 * @author  Alfredo Bejarano
 */
public class HomeActivity extends BaseActivity {
    /**
     * Defines the id for this activity layout.
     * @return The integer id value for this activity layout.
     */
    @Override
    protected Integer getLayoutId() {
        return R.layout.activity_home;
    }
}
