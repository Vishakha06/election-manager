package com.elections.apimodel.request;

import org.springframework.context.annotation.Scope;

import javax.validation.constraints.NotNull;

@Scope("request")
public class RatingRequest {
    @NotNull
    private int citizenId;

    @NotNull
    private int ideaId;

    private int rating;

    public RatingRequest(int citizenId, int ideaId, int rating){
        this.citizenId = citizenId;
        this.ideaId = ideaId;
        this.rating = rating;
    }

    public int getCitizenId() {
        return citizenId;
    }

    public void setCitizenId(int citizenId) {
        this.citizenId = citizenId;
    }

    public int getIdeaId() {
        return ideaId;
    }

    public void setIdeaId(int ideaId) {
        this.ideaId = ideaId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}

