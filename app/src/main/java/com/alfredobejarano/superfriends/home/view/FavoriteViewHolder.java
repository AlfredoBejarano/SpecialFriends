package com.alfredobejarano.superfriends.home.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alfredobejarano.superfriends.R;
import com.alfredobejarano.superfriends.SuperFriendsApplication;
import com.alfredobejarano.superfriends.common.model.Friend;
import com.alfredobejarano.superfriends.profile.view.ProfileActivity;

/**
 * Defines a visual representation of a Friend.
 */
public class FavoriteViewHolder extends RecyclerView.ViewHolder {
    private TextView name = itemView.findViewById(R.id.item_favorite_friend_name);
    private ImageView photo = itemView.findViewById(R.id.item_friend_picture);

    public FavoriteViewHolder(View itemView) {
        super(itemView);
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

        // Go to the profile activity when the friend is not a title.
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.getContext().startActivity(ProfileActivity.provideIntent(v.getContext(), friend.getId()));
            }
        });
    }
}
