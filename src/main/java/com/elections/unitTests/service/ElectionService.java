package com.elections.unitTests.service;

import com.elections.apimodel.response.ContenderResponse;
import com.elections.dbmodel.Contender;
import com.elections.dbmodel.Idea;
import com.elections.unitTests.service.exception.ElectionResultException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ElectionService {

    @Autowired
    RatingService ratingService;

    @Autowired
    ContenderService contenderService;

    public ContenderResponse getResult() throws Exception {
        List<ContenderResponse> contenders = contenderService.getContenders();

        if(contenders == null || contenders.size() == 0){
            throw new ElectionResultException("No contenders to contest for elections");
        }

        Contender winner = null; double maxRating = 0;
        for(ContenderResponse contenderResponse: contenders){
            Contender contender = contenderService.getContender(contenderResponse.getId()).get();
            // Skip the contender with idea rated less than 5 by 3 voters
            if(!ratingService.isEligibleForElections(contender))
                continue;
            double ideaAvgSum = 0.0;
            for(Idea idea: contender.getManifesto()){
                ideaAvgSum+= ratingService.getAverageRatingForIdea(idea);
            }

            double curContenderRating = 0.0;
            if (contender.getManifesto()!= null && contender.getManifesto().size() > 0) {
                curContenderRating = ideaAvgSum/contender.getManifesto().size();
            }

            // Add up all the ratings and divide by number of voters
            if(curContenderRating > maxRating){
                maxRating = curContenderRating;
                winner = contender;
            }
        }

        if(winner == null)
            throw new ElectionResultException("Results can not be calculated as of now");

        return new ContenderResponse(winner.getId(), winner.getCitizen().getName(), winner.getManifesto());
    }
}
