package com.example.hackapplab;

public class uploadd {
    String user,email, category,desc;
    Integer likes;
    Boolean Verified;

    long timestamp;

    public uploadd(){}

    public uploadd(String user, String email, String category, String desc, Integer likes, Boolean verified, long timestamp) {
        this.user = user;
        this.email = email;
        this.category = category;
        this.desc = desc;
        this.likes = likes;
        Verified = verified;
        this.timestamp = timestamp;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public Boolean getVerified() {
        return Verified;
    }

    public void setVerified(Boolean verified) {
        Verified = verified;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
