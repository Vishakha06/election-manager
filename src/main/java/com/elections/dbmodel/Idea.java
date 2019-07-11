package com.elections.dbmodel;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Idea {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotNull
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contender_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonBackReference
    private Contender contender;

    public Idea(){
    }

    public Idea(int id){
        this.id = id;
    }

    public Idea(String description){
        this.description = description;
    }

    public Idea(String description, Contender contender){
        this.description = description;
        this.contender = contender;
    }

    public Contender getContender() {
        return contender;
    }

    public void setContender(Contender contender) {
        this.contender = contender;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
