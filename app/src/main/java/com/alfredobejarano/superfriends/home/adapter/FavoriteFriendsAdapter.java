package com.alfredobejarano.superfriends.home.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.alfredobejarano.superfriends.R;
import com.alfredobejarano.superfriends.common.SuperFriendsUtils;
import com.alfredobejarano.superfriends.common.model.Friend;
import com.alfredobejarano.superfriends.home.view.FavoriteViewHolder;

import java.util.List;

/**
 * Defines an adapter to represent a list of Friends in a RecyclerView.
 *
 * @author Alfredo Bejarano
 */
public class FavoriteFriendsAdapter extends RecyclerView.Adapter<FavoriteViewHolder> {
    private List<Friend> mFriends;

    public void setFriends(List<Friend> friends) {
        // Assigns the new friends to the list value.
        mFriends = friends;
        // Check if the list is not null and not empty.
        if (mFriends != null && !mFriends.isEmpty()) {
            // Sort the friends names alphabetically.
            SuperFriendsUtils.sortFriendsList(mFriends);
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
        return R.layout.item_favorite_friend;
    }

    /**
     * {@inheritDoc}
     */
    @NonNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FavoriteViewHolder(LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onBindViewHolder(@NonNull FavoriteViewHolder holder, int position) {
        holder.render(mFriends.get(position));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getItemCount() {
        return mFriends == null ? 0 : mFriends.size();
    }
}
