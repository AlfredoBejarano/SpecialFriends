package com.alfredobejarano.superfriends.common.repository;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.alfredobejarano.superfriends.welcome.model.UserToken;

import java.util.List;

/**
 * Dao interface that allows easy access to the local database for facebook token operations.
 */
@Dao
public interface UserTokenDao {
    /**
     * Retrieves all the stored facebook tokens.
     *
     * @return List of all the stored facebook token.
     */
    @Query("SELECT * FROM UserToken")
    List<UserToken> getUserTokens();

    /**
     * Stores a Facebook token in the database.
     *
     * @param userToken The token to be stored.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void storeUserToken(UserToken userToken);

    /**
     * Removes all the user tokens from the local database.
     */
    @Query("DELETE FROM UserToken")
    void deleteUserTokens();
}
