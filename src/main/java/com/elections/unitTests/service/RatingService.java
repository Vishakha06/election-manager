package com.elections.unitTests.service;

import com.elections.apimodel.response.RatingResponse;
import com.elections.dbmodel.Citizen;
import com.elections.dbmodel.Contender;
import com.elections.dbmodel.Idea;
import com.elections.dbmodel.Rating;
import com.elections.apimodel.request.RatingRequest;
import com.elections.repository.RatingRepository;
import com.elections.unitTests.service.exception.ResourceNotFoundException;
import com.elections.unitTests.service.exception.ValidationException;
import com.elections.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class RatingService {

    @Autowired
    RatingRepository ratingRepository;

    @Autowired
    CitizenService citizenService;

    @Autowired
    IdeaService ideaService;

    @Autowired
    ContenderService contenderService;

    @Transactional
    public RatingResponse addRating(RatingRequest ratingRequest)throws ValidationException {

        Citizen citizen = citizenService.get(ratingRequest.getCitizenId()); // Takes care of citizen not found scenario

        Idea idea = ideaService.get(ratingRequest.getIdeaId())
                .orElseThrow(() -> new ResourceNotFoundException("Idea", "id", ratingRequest.getIdeaId()));
        if(ratingRequest.getRating() > Constants.MAX_RATING || ratingRequest.getRating() < Constants.MIN_RATING){
            throw new ValidationException("Check your rating, should be in range "+Constants.MIN_RATING+"-"+Constants.MAX_RATING);
        }

        Optional<Rating> ratingInDB = ratingRepository.findByCitizenAndIdea(citizen, idea);

        if(ratingInDB.isPresent() == true)
            throw new ValidationException("you already rated the idea");

        Rating ratingToPersist = new Rating(citizen, idea, ratingRequest.getRating());

        ratingRepository.save(ratingToPersist);

        if(ratingRequest.getRating() > Constants.RATING_TO_BECOME_FOLLOWER)
            contenderService.addFollower(idea.getContender().getId(), citizen);

        return new RatingResponse(citizen.getId(), idea.getId(), ratingRequest.getRating());

    }

    @Transactional
    public void deleteRating(RatingRequest ratingRequest) {

        Citizen citizen = citizenService.get(ratingRequest.getCitizenId()); // Takes care of citizen not found scenario

        Idea idea = ideaService.get(ratingRequest.getIdeaId())
                .orElseThrow(() -> new ResourceNotFoundException("Idea", "id", ratingRequest.getIdeaId()));

        Rating rating = ratingRepository.findByCitizenAndIdea(citizen, idea)
                .orElseThrow(() -> new ResourceNotFoundException("Rating", "idea", ratingRequest.getIdeaId()));

        ratingRepository.deleteById(rating.getId());

        if(rating.getRating() > Constants.RATING_TO_BECOME_FOLLOWER) {
            Contender contender = idea.getContender();
            List<Idea> ideas = contender.getManifesto();
            int ideasWithVotesMoreThanFive = ratingRepository.findCountOfIdeasWithVotesMoreThanFive(ideas, citizen.getId());
            // Check if this citizen has rated another idea of the same contestant more than 5, if yes skip the deleteFollower
            if(ideasWithVotesMoreThanFive == 0) {
                contenderService.deleteFollower(contender.getId(), rating.getCitizen());
            }
        }
    }


    public double getAverageRatingForIdea(Idea idea){
        int numOfRatings = ratingRepository.findNumberOfVotesForIdea(idea.getId());
        double avg = 0.0;
        if(numOfRatings > 0){
            avg = ratingRepository.findSumOfRatingsForIdea(idea.getId())/numOfRatings;
        }
        return avg;
    }

    public boolean isEligibleForElections(Contender contender){
        List<Idea> ideas = contender.getManifesto();
        if(ideas == null || ideas.size() == 0){
            return false;
        }
        Map<Integer, Integer> ideasMap = ratingRepository.findIdeasWithVotesBelowTheLimit(ideas);
        for (Map.Entry ideaEntry : ideasMap.entrySet()) {
            int votes = ((int)ideaEntry.getValue());
            if(votes > Constants.MAX_LIMIT_FOR_VOTES_UNQUALIFIED_RATING){
                return false;
            }
        }
        return true;
    }

}
