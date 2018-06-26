package com.alfredobejarano.superfriends.common.repository;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.alfredobejarano.superfriends.BuildConfig;
import com.alfredobejarano.superfriends.common.model.Friend;
import com.alfredobejarano.superfriends.welcome.model.UserToken;

@Database(entities = {Friend.class, UserToken.class}, version = 1)
public abstract class SuperFriendsDatabase extends RoomDatabase {
    public static final String DATABASE_NAME = "SuperFriendsDatabase" + BuildConfig.BUILD_TYPE;

    /**
     * Gets this database Dao for easy access to the friend values.
     *
     * @return A {@link FriendDao} instance to access the local database values.
     */
    abstract public FriendDao getFriendDao();

    /**
     * Gets this database Dao for easy access to the token values.
     *
     * @return A {@link UserTokenDao} instance to access the local database values.
     */
    abstract public UserTokenDao getUserTokenDao();
}
