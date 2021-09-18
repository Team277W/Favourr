package com.example.favourr;

import android.location.Location;

import java.io.Serializable;
import java.util.Date;

public class Favourr implements Serializable {

    private String title, description, location;
    private Date deadline;
    double bountyPrice;

    public Favourr(String title, double bountyPrice, String description, Date deadline, String location) {
        this.title = title;
        this.bountyPrice = bountyPrice;
        this.description = description;
        this.deadline = deadline;
        this.location = location;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getBountyPrice() {
        return bountyPrice;
    }

    public void setBountyPrice(double bountyPrice) {
        this.bountyPrice = bountyPrice;
    }
}
