package com.alfredobejarano.superfriends.common.repository;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.alfredobejarano.superfriends.BuildConfig;
import com.alfredobejarano.superfriends.common.model.Friend;
import com.alfredobejarano.superfriends.welcome.model.UserToken;

import static com.alfredobejarano.superfriends.common.repository.SuperFriendsDatabase.DATABASE_VERSION;

@Database(entities = {Friend.class, UserToken.class}, version = DATABASE_VERSION)
public abstract class SuperFriendsDatabase extends RoomDatabase {
    /**
     * Defines the current database version.
     */
    static final Integer DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "SuperFriendsDatabase" + BuildConfig.BUILD_TYPE;

    /**
     * Gets this database Dao for easy access to the values.
     * @return A {@link FriendDao} instance to access the local database values.
     */
    abstract FriendDao getFriendDao();
}
