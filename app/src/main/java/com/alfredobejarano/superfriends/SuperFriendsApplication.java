package com.alfredobejarano.superfriends;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.alfredobejarano.superfriends.common.repository.SuperFriendsDatabase;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import static com.alfredobejarano.superfriends.common.repository.SuperFriendsDatabase.DATABASE_NAME;


public class SuperFriendsApplication extends Application {
    public static SuperFriendsDatabase superFriendsDatabase;

    @Override
    @SuppressWarnings("deprecation")
    public void onCreate() {
        super.onCreate();
        superFriendsDatabase = Room.databaseBuilder(getApplicationContext(), SuperFriendsDatabase.class, DATABASE_NAME).build();
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
    }
}
