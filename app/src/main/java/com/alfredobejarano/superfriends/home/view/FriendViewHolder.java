package com.alfredobejarano.superfriends.home.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alfredobejarano.superfriends.R;
import com.alfredobejarano.superfriends.SuperFriendsApplication;
import com.alfredobejarano.superfriends.common.model.Friend;
import com.alfredobejarano.superfriends.home.adapter.FavoriteFriendsAdapter;
import com.alfredobejarano.superfriends.profile.view.ProfileActivity;

/**
 * Defines a visual representation of a Friend.
 */
public class FriendViewHolder extends RecyclerView.ViewHolder {
    private FavoriteFriendsAdapter favAdapter;
    private TextView name = itemView.findViewById(R.id.item_friend_name);
    private ImageView photo = itemView.findViewById(R.id.item_friend_picture);
    private ImageView favorite = itemView.findViewById(R.id.item_friend_favorite);

    public FriendViewHolder(View itemView, FavoriteFriendsAdapter favAdapter) {
        super(itemView);
        this.favAdapter = favAdapter;
    }

    /**
     * Renders the details of a friend in the list.
     *
     * @param friend The friend to be rendered.
     */
    public void render(final Friend friend) {
        name.setText(friend.getName());
        /*if (photo != null) {
            Picasso.with(itemView.getContext()).load(friend.getPicture()).into(photo, new CircularCallback(photo));
        }*/
        if (favorite != null) {
            favorite.setImageResource(friend.isFavorite() ? R.drawable.ic_star_full : R.drawable.ic_star_border);
        }

        // Go to the profile activity when the friend is not a title.
        if (friend.getFaceBookId() != null) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    v.getContext().startActivity(ProfileActivity.provideIntent(v.getContext(), friend.getId()));
                }
            });
            // Changes the favorite status of a friend when the star is clicked.
            favorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Check the friend favorite status.
                    if (friend.isFavorite()) {
                        // Invert it.
                        friend.setFavorite(false);
                        // Change the star drawable depending on the new value.>
                        favorite.setImageResource(R.drawable.ic_star_border);
                    } else {
                        // Invert it.
                        friend.setFavorite(true);
                        // Change the star drawable depending on the new value.>
                        favorite.setImageResource(R.drawable.ic_star_full);
                    }
                    favAdapter.setFavoriteFriend(friend);
                    // Updates the new friend info in a worker thread.
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            // Retrieve the local database instance and store the value.
                            SuperFriendsApplication
                                    .superFriendsDatabase
                                    .getFriendDao()
                                    .addFriend(friend);
                        }
                    }).start();
                }
            });
        }
    }
}
