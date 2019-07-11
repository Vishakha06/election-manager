package com.elections.apimodel.response;

import com.elections.dbmodel.Idea;

import javax.validation.constraints.NotNull;
import java.util.List;

public class ContenderResponse {
    @NotNull
    private int id;

    private String name;

    @NotNull
    private List<Idea> manifesto;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ContenderResponse(int id, String name, List<Idea> manifesto){
        this.id = id;
        this.manifesto = manifesto;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Idea> getManifesto() {
        return manifesto;
    }

    public void setManifesto(List<Idea> manifesto) {
        this.manifesto = manifesto;
    }
}
