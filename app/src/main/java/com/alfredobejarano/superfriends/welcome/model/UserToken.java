package com.alfredobejarano.superfriends.welcome.model;

import android.arch.persistence.room.Entity;

/**
 * Defines the token for a user Facebook session.
 * @author Alfredo Bejarano
 */
@Entity
public class UserToken {
    /**
     * This property defines the value for the user facebook token.
     */
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
