package com.epam.belote;

public class Team {
    private int points;

    public void increasePoints(int points) {
        this.points += points;
    }

    public int getPoints(){
        return  this.points;
    }
}
