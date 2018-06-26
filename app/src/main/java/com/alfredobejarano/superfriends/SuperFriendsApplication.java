package com.alfredobejarano.superfriends;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.alfredobejarano.superfriends.common.repository.SuperFriendsDatabase;

import static com.alfredobejarano.superfriends.common.repository.SuperFriendsDatabase.DATABASE_NAME;

public class SuperFriendsApplication extends Application {
    static SuperFriendsDatabase superFriendsDatabase;

    @Override
    public void onCreate() {
        super.onCreate();
        superFriendsDatabase = Room.databaseBuilder(getApplicationContext(), SuperFriendsDatabase.class, DATABASE_NAME).build();
    }
}
