package com.alfredobejarano.superfriends.common.repository;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.alfredobejarano.superfriends.common.model.Friend;

import java.util.List;

/**
 * Dao interface that allows easy access to the local database.
 *
 * @author Alfredo Bejarano
 */
@Dao
public interface FriendDao {
    /**
     * Retrieves all the friends from the local storage.
     *
     * @return A {@link List} of {@link Friend}s ordered alphabetically.
     */
    @Query("SELECT * FROM Friend ORDER BY name")
    List<Friend> getFriends();

    /**
     * Retrieves all the favorite friends from the local storage.
     *
     * @return A {@link List} of {@link Friend}s ordered alphabetically.
     */
    @Query("SELECT * FROM Friend WHERE favorite = 1 ORDER BY name")
    List<Friend> getFavoriteFriends();

    /**
     * Inserts a {@link Friend} in the local storage database.
     * It replaces a Friend that has been already added.
     *
     * @param friend The friend value to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addFriend(Friend friend);

    /**
     * Queries through the database and retrieves a friend record.
     * @param friendId The id to search from the database.
     * @return The friend found by the given id.
     */
    @Query("SELECT * FROM Friend WHERE id = :friendId")
    Friend getFriendById(Integer friendId);

    /**
     * Queries through the database and retrieves a friend record.
     * @param friendId The id to search from the database.
     * @return The friend found by the given id.
     */
    @Query("SELECT * FROM Friend WHERE faceBookId = :friendId")
    Friend getFriendByFBId(String friendId);

    /**
     * Removes all the Friend records.
     */
    @Query("DELETE FROM Friend")
    void deleteAllFriends();
}
