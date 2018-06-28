package com.alfredobejarano.superfriends.profile.viewmodel;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.alfredobejarano.superfriends.R;
import com.alfredobejarano.superfriends.common.viewmodel.ViewModelState;
import com.alfredobejarano.superfriends.common.model.Friend;
import com.alfredobejarano.superfriends.common.viewmodel.BaseViewModel;
import com.alfredobejarano.superfriends.home.view.HomeActivity;
import com.alfredobejarano.superfriends.profile.model.ProfileInformation;

public class ProfileViewModel extends BaseViewModel {
    public Friend friend;
    public MutableLiveData<Integer> errorMessage = new MutableLiveData<>();
    public MutableLiveData<Boolean> favoriteMenu = new MutableLiveData<>();
    public ProfileInformation profileInformation = new ProfileInformation();

    /**
     * {@inheritDoc}
     */
    public ProfileViewModel(@NonNull Application application) {
        super(application);
        favoriteMenu.setValue(false);
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
                        favoriteMenu.postValue(friend.isFavorite());
                        profileInformation.name.postValue(friend.getName());
                        profileInformation.photo.postValue(friend.getPicture());
                        profileInformation.description.postValue(friend.getNote());
                        profileInformation.favorite.postValue(friend.isFavorite());
                        profileInformation.birthday.postValue(friend.getBirthday());
                        profileInformation.phoneNumber.postValue(friend.getPhoneNumber());
                    } else {
                        errorMessage.postValue(R.string.no_user_found);
                    }
                    state.postValue(ViewModelState.STATE_READY);
                }
            }).start();
        }
    }

    /**
     * Stores the modified values of the found friend.
     */
    public void storeFriend() {
        if(friend != null) {
            // Notify that the ViewModel is busy.
            state.setValue(ViewModelState.STATE_BUSY);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    friendDao.addFriend(friend);
                    Intent homeIntent = new Intent(getApplication().getApplicationContext(), HomeActivity.class);
                    homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    getApplication().getApplicationContext().startActivity(homeIntent);
                }
            }).start();
        }
    }
}
