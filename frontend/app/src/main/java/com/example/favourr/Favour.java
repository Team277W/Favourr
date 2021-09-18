package com.example.favourr;

import android.location.Location;

public class Favour {

    private String title, description, deadline, location;
    double bountyPrice;

    public Favour(String title, double bountyPrice, String description, String deadline, String location) {
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

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
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
