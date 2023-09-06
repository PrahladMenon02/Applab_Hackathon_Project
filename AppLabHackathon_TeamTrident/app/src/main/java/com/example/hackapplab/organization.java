package com.example.hackapplab;

public class organization {
    public String name,email,cat;
    public boolean verified;

    public organization(String name, String email, String cat, boolean verified) {
        this.name = name;
        this.email = email;
        this.cat = cat;
        this.verified = verified;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }
}
