package com.alfredobejarano.superfriends.common;

import com.alfredobejarano.superfriends.common.model.Friend;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Utilities class that has global used methods.
 *
 * @author Alfredo Bejarano
 */
public class SuperFriendsUtils {
    private SuperFriendsUtils() {
        // Empty hidden constructor for an utility class.
    }

    /**
     * Sorts a list of friends alphabetically.
     *
     * @param friendsList The list of friends to be sorted.
     */
    public static void sortFriendsList(List<Friend> friendsList) {
        // Sort the list by a given comparator.
        Collections.sort(friendsList, new Comparator<Friend>() {
            @Override
            public int compare(Friend friend1, Friend friend2) {
                // Compare the name value.
                return friend1.getName().compareToIgnoreCase(friend2.getName());
            }
        });
    }
}
