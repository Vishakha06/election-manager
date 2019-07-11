package com.elections.service;

import com.elections.apimodel.response.ContenderResponse;
import com.elections.dbmodel.Citizen;
import com.elections.dbmodel.Contender;
import com.elections.repository.CitizenRepository;
import com.elections.repository.ContenderRepository;
import com.elections.service.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CitizenService {

    @Autowired
    CitizenRepository citizenRepository;

    @Autowired
    ContenderService contenderService;

    public Citizen create(Citizen citizen){
        return citizenRepository.save(citizen);
    }

    public Citizen update(Citizen citizen) {
        return citizenRepository.save(citizen);
    }

    public Iterable<Citizen> findAll() {
        return citizenRepository.findAll();
    }

    public Citizen get(int id){
        return citizenRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Citizen", "id", id));
    }

    public void delete(int id){
        citizenRepository.deleteById(id);
    }

    public ContenderResponse contest(int id) {
        Citizen citizen = citizenRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Citizen", "id", id));

        Contender contender = contenderService.create(new Contender(citizen));
        return new ContenderResponse(contender.getId(), contender.getCitizen().getName(), contender.getManifesto());
    }

}
