package com.alfredobejarano.superfriends.profile.view;

import android.app.Application;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.alfredobejarano.superfriends.common.ViewModelState;
import com.alfredobejarano.superfriends.common.model.Friend;
import com.alfredobejarano.superfriends.common.viewmodel.BaseViewModel;
import com.alfredobejarano.superfriends.home.view.HomeActivity;
import com.alfredobejarano.superfriends.profile.model.ProfileInformation;

public class ProfileViewModel extends BaseViewModel {
    private Friend friend;
    ProfileInformation profileInformation;

    /**
     * {@inheritDoc}
     */
    public ProfileViewModel(@NonNull Application application) {
        super(application);
    }

    /**
     * Retrieves a friend profile from the database.
     * @param friendId The id for the friend to retreive.
     */
    public void getFriendProfile(final Integer friendId) {
        if (state.getValue() == ViewModelState.STATE_READY) {
            state.setValue(ViewModelState.STATE_BUSY);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    friend = friendDao.getFriendById(friendId);
                    if (friend != null) {
                        profileInformation.name.postValue(friend.getName());
                        profileInformation.photo.postValue(friend.getPicture());
                        profileInformation.description.postValue(friend.getNote());
                        profileInformation.birthday.postValue(friend.getBirthday());
                    }
                }
            }).start();
        }
    }

    /**
     * Stores the modified values of the found friend.
     */
    public void storeFriend() {
        if(friend != null) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    friendDao.addFriend(friend);
                    Intent homeIntent = new Intent(getApplication().getApplicationContext(), HomeActivity.class);
                    homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    getApplication().getApplicationContext().startActivity(homeIntent);
                }
            });
        }
    }
}
