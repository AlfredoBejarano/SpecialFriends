package com.alfredobejarano.superfriends.home.model;

import android.arch.lifecycle.MutableLiveData;
import android.net.Uri;

/**
 * Defines which information is going to display in the view for the user.
 *
 * @author Alfredo Bejarano
 */
public class UserInformation {
    /**
     * This property defines the user name retrieved from his facebook profile.
     */
    public MutableLiveData<String> name = new MutableLiveData<>();
    /**
     * This property defines the user picture retrieved from his facebook profile.
     */
    public MutableLiveData<Uri> picture = new MutableLiveData<>();
}
