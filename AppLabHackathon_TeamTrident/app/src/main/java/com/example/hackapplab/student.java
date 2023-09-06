package com.example.hackapplab;

public class student {
    public String reg,name,branch,sem,email,cat;
    boolean verified;

    public student(){}

    public student(String reg, String name, String branch, String sem, String email, String cat, boolean verified) {
        this.reg = reg;
        this.name = name;
        this.branch = branch;
        this.sem = sem;
        this.email = email;
        this.cat = cat;
        this.verified = verified;
    }

    public String getReg() {
        return reg;
    }

    public void setReg(String reg) {
        this.reg = reg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getSem() {
        return sem;
    }

    public void setSem(String sem) {
        this.sem = sem;
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
