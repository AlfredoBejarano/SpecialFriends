package com.alfredobejarano.superfriends.profile.model;

import android.arch.lifecycle.MutableLiveData;

public class ProfileInformation {
    public MutableLiveData<String> name = new MutableLiveData<>();
    public MutableLiveData<String> photo = new MutableLiveData<>();
    public MutableLiveData<String> birthday = new MutableLiveData<>();
    public MutableLiveData<Boolean> favorite = new MutableLiveData<>();
    public MutableLiveData<String> description = new MutableLiveData<>();
}
