package com.elections.apimodel.response;

import javax.validation.constraints.NotNull;

public class RatingResponse {
    @NotNull
    private int citizenId;

    @NotNull
    private int ideaId;

    private int rating;

    public RatingResponse(int citizenId, int ideaId, int rating){
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
