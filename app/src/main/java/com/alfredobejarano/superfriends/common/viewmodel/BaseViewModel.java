package com.alfredobejarano.superfriends.common.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.support.annotation.NonNull;

import com.alfredobejarano.superfriends.SuperFriendsApplication;
import com.alfredobejarano.superfriends.common.ViewModelState;
import com.alfredobejarano.superfriends.common.repository.FriendDao;
import com.alfredobejarano.superfriends.common.repository.SuperFriendsDatabase;
import com.alfredobejarano.superfriends.common.repository.UserTokenDao;

/**
 * Defines common actions and behaviours for this app ViewModel classes.
 * @author AlfredoBejarano
 */
public class BaseViewModel extends AndroidViewModel {
    /**
     * Property that defines if the ViewModel is busy or not.
     */
    public MutableLiveData<ViewModelState> state = new MutableLiveData<>();
    /**
     * Property that holds a reference to the local database.
     */
    protected SuperFriendsDatabase database = SuperFriendsApplication.superFriendsDatabase;
    /**
     * Property that holds a reference to the {@link UserTokenDao}, allowing faster access to the UserToken table.
     */
    protected UserTokenDao tokenDao = database.getUserTokenDao();
    /**
     * Property that holds a reference to the {@link FriendDao}, allowing faster access to the Friend table.
     */
    protected FriendDao friendDao = database.getFriendDao();


    /**
     * Constructor for this {@link AndroidViewModel} child.
     * @param application The application {@link android.content.Context} instance.
     */
    public BaseViewModel(@NonNull Application application) {
        super(application);
        // Notify that the ViewModel is ready to perform operations.
        state.setValue(ViewModelState.STATE_READY);
    }

    /**
     * Checks if the local database and all of its components are not null.
     * @return True if the database values are not null.
     */
    protected Boolean isDatabaseUsable() {
        return database != null && tokenDao != null && friendDao != null;
    }
}
