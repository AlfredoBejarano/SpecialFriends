package com.alfredobejarano.superfriends.common.model;

/**
 * Model class that defines the structure of the data
 * necessary to represent a friend in the app.
 *
 * @author Alfredo Bejarano
 */
public class Friend {
    private String name;
    private String note;
    private String picture;
    private String birthday;
    private Boolean favorite;
    private String phoneNumber;

    /**
     * Gets the name of the friend.
     * @return The name of the friend.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the picture of the friend profile.
     * @return The URl of the friend picture.
     */
    public String getPicture() {
        return picture;
    }

    /**
     * Gets the note added by the user for the friend.
     * @return String value of the note´s text.
     */
    public String getNote() {
        return note;
    }

    /**
     * Allows the user to change the value of the friend note.
     * @param note The new text value for the note.
     */
    public void setNote(String note) {
        this.note = note;
    }

    /**
     * Gets the birthday for the friend.
     * @return The birthday of the friend as a String.
     */
    public String getBirthday() {
        return birthday;
    }

    /**
     * Allows the user to change the birthday value for the friend.
     * @param  birthday The new value for the friend birthday.
     */
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    /**
     * Gets if the user is a favorite friend or not.
     * @return true if the friend is a favorite
     */
    public Boolean getFavorite() {
        return favorite;
    }

    /**
     * Allows the user to change if the friend is going to be a favorite user or not.
     * @param favorite the new favorite value for the friend.
     */
    public void setFavorite(Boolean favorite) {
        this.favorite = favorite;
    }

    /**
     * Gets the given phone number for a Friend.
     * @return The phone number of the friend.
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Allows the user to change the phone number for a friend.
     * @param phoneNumber The new value for the phone number.
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
