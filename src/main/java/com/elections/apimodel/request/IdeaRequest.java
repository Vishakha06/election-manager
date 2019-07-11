package com.elections.apimodel.request;

import org.springframework.context.annotation.Scope;

import javax.validation.constraints.NotNull;

@Scope("request")
public class IdeaRequest {

    @NotNull
    private String description;

    public IdeaRequest(String description){
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
