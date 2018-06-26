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

    /**
     * Retrieves the value of the User token.
     * @return The value of the token.
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets a value for the user facebook token.
     * @param value The new value for the token.
     */
    public void setValue(String value) {
        this.value = value;
    }
}
