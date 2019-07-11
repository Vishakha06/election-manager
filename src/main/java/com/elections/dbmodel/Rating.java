package com.elections.dbmodel;

import com.elections.util.Constants;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Entity
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name="idea_id")
    private Idea idea;

    @ManyToOne
    @JoinColumn(name="citizen_id")
    private Citizen citizen;

    @Min(value = Constants.MIN_RATING, message = "Rating can not be negative")
    @Max(value = Constants.MAX_RATING, message = "Rating has be less than or equal to 10")
    private int rating;

    public Rating(){

    }

    public Idea getIdea() {
        return idea;
    }

    public void setIdea(Idea idea) {
        this.idea = idea;
    }

    public Citizen getCitizen() {
        return citizen;
    }

    public void setCitizen(Citizen citizen) {
        this.citizen = citizen;
    }

    public Rating(int id, Citizen citizen, Idea idea, int rating) {
        this.id = id;
        this.idea = idea;
        this.citizen = citizen;
        this.rating = rating;
    }

    public Rating(Citizen citizen, Idea idea, int rating) {
        this.idea = idea;
        this.citizen = citizen;
        this.rating = rating;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Idea getIdeaId() {
        return idea;
    }

    public void setIdeaId(Idea idea) {
        this.idea = idea;
    }

    public Citizen getCitizenId() {
        return citizen;
    }

    public void setCitizenId(Citizen citizen) {
        this.citizen = citizen;
    }
}
