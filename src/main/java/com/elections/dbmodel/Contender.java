package com.elections.dbmodel;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.validation.constraints.Size;

import java.util.List;
import java.util.Set;

@Entity
public class Contender{

    @Id
    private int contender_id;

    @OneToOne
    @JoinColumn
    @MapsId
    private Citizen citizen;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "contender", cascade = CascadeType.ALL)
    @Size(max=3, message = "Only 3 ideas can be submitted")
    @JsonManagedReference
    private List<Idea> manifesto;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<Citizen> followers;

    public Contender(int contender_id){
        this.setId(contender_id);
    }

    public Contender(Citizen citizen){
        this.setId(citizen.getId());
        this.setCitizen(citizen);
    }

    public Contender(Citizen citizen, List<Idea> manifesto){
        this.setId(citizen.getId());
        this.setManifesto(manifesto);
        this.setCitizen(citizen);
    }

    public Contender() {

    }

    public int getId() {
        return contender_id;
    }

    public void setId(int contender_id) {
        this.contender_id = contender_id;
    }

    public Citizen getCitizen() {
        return citizen;
    }

    public void setCitizen(Citizen citizen) {
        this.citizen = citizen;
    }

    public Set<Citizen> getFollowers() {
        return followers;
    }

    public void setFollowers(Set<Citizen> followers) {
        this.followers = followers;
    }

    public List<Idea> getManifesto() {
        return manifesto;
    }

    public void setManifesto(List<Idea> manifesto) {
        this.manifesto = manifesto;
    }

}
