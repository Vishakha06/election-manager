package com.elections.unitTests.service;

import com.elections.apimodel.response.ContenderResponse;
import com.elections.dbmodel.Citizen;
import com.elections.dbmodel.Contender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.elections.repository.ContenderRepository;
import com.elections.unitTests.service.exception.ResourceNotFoundException;


import java.util.*;

@Service
public class ContenderService {

    @Autowired
    ContenderRepository contenderRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(ContenderService.class);

    public Optional<Contender> getContender(int id) {
        return contenderRepository.findById(id);
    }

    public List<ContenderResponse> getContenders() {
        Iterable<Contender> iterable = contenderRepository.findAll();
        Iterator<Contender> iterator = iterable.iterator();
        List<ContenderResponse> contendersResponse = new ArrayList<>();
        while(iterator.hasNext()){
            Contender contender = iterator.next();
            contendersResponse.add(new ContenderResponse(contender.getId(), contender.getCitizen().getName(), contender.getManifesto()));
        }
        return contendersResponse;
    }
    public Contender create(Contender contender) {
        return contenderRepository.save(contender);
    }
    /*
     Get all followers, returns followers of followers (if follower is a contender)
     */
    public Set<Citizen> getFollowers(Contender contender){

        Set<Citizen> contendersAlreadyScanned = new HashSet<Citizen>();
        Set<Citizen> followers = contender.getFollowers();
        if(followers != null && followers.size() != 0) {
            for(Citizen citizen: followers){
                if(!contendersAlreadyScanned.contains(citizen) && contenderRepository.existsById(citizen.getId())){ // Is follower a contender
                    contendersAlreadyScanned.add(citizen);
                    Contender contenderInFollowerList = contenderRepository.findById(citizen.getId()).get();
                    followers.addAll(getFollowers(contenderInFollowerList));
                }
            }
        }
        return followers;
    }

    public Contender addFollower(int id, Citizen citizen) {
        Contender contenderToUpdate = contenderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contender", "id", id));
        Set<Citizen> currentFollowers = contenderToUpdate.getFollowers();

        if(currentFollowers != null && currentFollowers.size()> 0){
            currentFollowers.add(citizen);
        } else {
            currentFollowers = new HashSet<>();
            currentFollowers.add(citizen);
        }

        contenderToUpdate.setFollowers(currentFollowers);
        return contenderRepository.save(contenderToUpdate);
    }

    public Contender deleteFollower(int id, Citizen citizen) {
        Contender contenderToUpdate = contenderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contender", "id", id));
        Set<Citizen> currentFollowers = contenderToUpdate.getFollowers();

        if(currentFollowers != null && currentFollowers.size()> 0){
            currentFollowers.remove(citizen);
        } else {
            return contenderToUpdate;
        }

        contenderToUpdate.setFollowers(currentFollowers);
        return contenderRepository.save(contenderToUpdate);
    }

    public void deleteContender(Integer id) {
        contenderRepository.deleteById(id);
    }
}
