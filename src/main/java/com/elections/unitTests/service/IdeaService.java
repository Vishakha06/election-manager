package com.elections.unitTests.service;

import com.elections.apimodel.request.IdeaRequest;
import com.elections.dbmodel.Citizen;
import com.elections.dbmodel.Contender;
import com.elections.dbmodel.Idea;
import com.elections.dbmodel.Rating;
import com.elections.repository.IdeaRepository;
import com.elections.unitTests.service.exception.ResourceNotFoundException;
import com.elections.unitTests.service.exception.ValidationException;
import com.elections.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class IdeaService {
    @Autowired
    IdeaRepository ideaRepository;

    @Autowired
    ContenderService contenderService;

    @Autowired
    MessagingService messagingService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ContenderService.class);


    public Iterable<Idea> get(){
        return ideaRepository.findAll();
    }

    @Transactional
    public Idea addIdea(int contenderId, IdeaRequest ideaRequest) throws ValidationException {

        Idea idea = new Idea(ideaRequest.getDescription());

        Contender contenderInDB = contenderService.getContender(contenderId)
                .orElseThrow(() -> new ResourceNotFoundException("Contender", "id", contenderId));
        List<Idea> ideas = contenderInDB.getManifesto();

        if(ideas!= null && ideas.size() >= Constants.MAX_IDEAS){
            throw new ValidationException("You have posted max"+ Constants.MAX_IDEAS+ " ideas");
        }

        idea.setContender(contenderInDB);

        Idea savedIdea = ideaRepository.save(idea);

        Set<Citizen> followers = contenderService.getFollowers(contenderInDB);

        // Send messages to the followers. if this service is down or has a bug, updateContender should not fail
        try {
            messagingService.sendMessage(followers, ideaRequest.getDescription());
        }catch (Exception e){
            LOGGER.error("Error in messaging service, idea "+ideaRequest.getDescription()+" not promoted to followers");
        }

        return savedIdea;
    }

    public void updateRating(Rating rating) {
    }

    public void deleteRating(Rating contenderId) {
    }

    public Optional<Idea> get(int id){
        return ideaRepository.findById(id);
    }
}
