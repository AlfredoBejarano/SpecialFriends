package com.alfredobejarano.superfriends.common.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Model class that defines the structure of the data
 * necessary to represent a friend in the app.
 *
 * @author Alfredo Bejarano
 */
@Entity
public class Friend {
    @PrimaryKey(autoGenerate = true)
    private Integer id;
    private String name;
    private String note;
    private String picture;
    private String birthday;
    private Boolean favorite;
    private String faceBookId;
    private String phoneNumber;

    /**
     * Constructor for Room to use.
     * @param name The name of the friend.
     * @param note The note text value for the friend.
     * @param picture The profile picture for the friend.
     * @param birthday The birthday value for the friend.
     * @param favorite The favorite value for the friend.
     * @param phoneNumber The phone number value for the friend.
     */
    public Friend(String name, String note, String picture, String birthday, Boolean favorite,String faceBookId, String phoneNumber) {
        this.name = name;
        this.note = note;
        this.picture = picture;
        this.birthday = birthday;
        this.favorite = favorite;
        this.faceBookId = faceBookId;
        this.phoneNumber = phoneNumber;
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
    public Boolean isFavorite() {
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

    /**
     * Gets the given Facebook id for a Friend.
     * @return The Facebook id of the friend.
     */
    public String getFaceBookId() {
        return faceBookId;
    }

    /**
     * Allows the user to change the facebook Id for a friend.
     * @param faceBookId The new value for the phone number.
     */
    public void setFaceBookId(String faceBookId) {
        this.faceBookId = faceBookId;
    }
}
