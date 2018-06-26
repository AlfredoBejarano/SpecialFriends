package com.alfredobejarano.superfriends.welcome.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Defines the token for a user Facebook session.
 * @author Alfredo Bejarano
 */
@Entity
public class UserToken {
    @PrimaryKey(autoGenerate = true)
    private Integer id;
    /**
     * This property defines the value for the user facebook token.
     */
    private String value;

        public UserToken(String value) {
            this.value = value;
        }

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

    /**
     * This method allows room to set an ID for this class.
     * @param id The id value for this class.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Gets the Id for the friend in the local database.
     * @return The id of the friend in the local database.
     */
    public Integer getId() {
        return id;
    }


}
