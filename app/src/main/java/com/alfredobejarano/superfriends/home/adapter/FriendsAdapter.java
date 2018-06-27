package com.alfredobejarano.superfriends.home.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.alfredobejarano.superfriends.R;
import com.alfredobejarano.superfriends.common.model.Friend;
import com.alfredobejarano.superfriends.home.view.FriendViewHolder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Defines an adapter to represent a list of Friends in a RecyclerView.
 *
 * @author Alfredo Bejarano
 */
public class FriendsAdapter extends RecyclerView.Adapter<FriendViewHolder> {
    private List<Friend> mFriends;
    private List<Friend> allFriends;

    public void setFriends(List<Friend> friends) {
        // Assigns the new friends to the list value.
        mFriends = friends;
        // Check if the list is not null and not empty.
        if (mFriends != null && !mFriends.isEmpty()) {
            // Add the friends title badges.
            addFriendsNameTitles();
            // Sort the friends names alphabetically.
            sortFriendsList(mFriends);
        }
        // Notify that the data has changed.
        notifyDataSetChanged();
    }

    /**
     * Render a Friend, depending if it is a Friend or a header.
     *
     * @param position The position to be rendered.
     * @return Layout id for the ViewHolder to render.
     */
    @Override
    public int getItemViewType(int position) {
        Friend friend = mFriends.get(position);
        if (friend.getFaceBookId() == null) {
            // Return the friend title badge layout if the facebook Id is null.
            return R.layout.item_friend_title;
        } else {
            // Return the friend item if the friend item facebook id is not null.
            return R.layout.item_friend;
        }
    }

    /**
     * {@inheritDoc}
     */
    @NonNull
    @Override
    public FriendViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FriendViewHolder(LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onBindViewHolder(@NonNull FriendViewHolder holder, int position) {
        holder.render(mFriends.get(position));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getItemCount() {
        return mFriends == null ? 0 : mFriends.size();
    }

    /**
     * Sorts a list of friends alphabetically.
     *
     * @param friendsList The list of friends to be sorted.
     */
    private void sortFriendsList(List<Friend> friendsList) {
        // Sort the list by a given comparator.
        Collections.sort(friendsList, new Comparator<Friend>() {
            @Override
            public int compare(Friend friend1, Friend friend2) {
                // Compare the name value.
                return friend1.getName().compareToIgnoreCase(friend2.getName());
            }
        });
    }

    /**
     * Adds the title elements to the list of friends.
     */
    private void addFriendsNameTitles() {
        if (mFriends.size() == 1) {
            mFriends.add(0, new Friend(
                    String.valueOf(mFriends.get(0).getName().charAt(0)),
                    "", "", "", false, null, ""
            ));
        } else {
            // Create a sparse array, that will hold the positions when a letter changes.
            SparseArray<Character> positions = new SparseArray<>();

            // Prevent adding a title at a position where there is one already.
            if(mFriends.get(0).getFaceBookId() != null) {
                mFriends.add(0, new Friend(
                        String.valueOf(mFriends.get(0).getName().charAt(0)),
                        "", "", "", false, null, ""
                ));
            }

            // Iterate through all the friends list.
            for (int i = 0; i < mFriends.size() - 1; i++) {
                // Prevent adding a title at a position where there is one already.
                if(mFriends.get(i +  1).getFaceBookId() != null) {
                    // Get the friend at the current position.
                    String friend1Name = mFriends.get(i).getName();
                    // Get the friend at the next position.
                    String friend2Name = mFriends.get(i + 1).getName();
                    // Check if there is a change in the first letter.
                    if (friend1Name.charAt(0) != friend2Name.charAt(0)) {
                        // If there is a change in the first letter, report the change position and the new letter.
                        positions.put(i + 1, friend2Name.charAt(0));
                    }
                }
            }

            // Iterate through the list of positions.
            for (int i = 0; i < positions.size(); i++) {
                // Add the Friend title.
                mFriends.add(positions.keyAt(i), new Friend(
                        positions.valueAt(i).toString(),
                        "", "", "", false, null, ""
                ));
            }
        }
    }

    /**
     * Searches for friends in the Friend list.
     */
    public void searchForFriends(String query) {
        // Store a version of the friends list when it is complete.
        if (allFriends == null) {
            // Store this backup only if it has not been stored before.
            allFriends = mFriends;
        }
        // Check if the query value is valid.
        if (query == null || query.length() == 0) {
            // Reset the friends list.
            mFriends = allFriends;
            // Notify that the friends list has changed.
            notifyDataSetChanged();
        } else {
            // Create a new List for the found friends.
            ArrayList<Friend> foundFriends = new ArrayList<>();
            // Iterate through the friends backup list.
            for (Friend f : allFriends) {
                // Get the current friend name.
                String name = f.getName().toLowerCase();
                // Check if there is any matches.
                if (name.contains(query) && name.length() > 1) {
                    // Add the found friend to the list.
                    foundFriends.add(f);
                }
            }
            // Set the new friends list, adding titles and sorting it.
            setFriends(foundFriends);
        }
    }
}
