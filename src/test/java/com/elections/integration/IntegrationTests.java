package com.elections.integration;

import com.elections.apimodel.request.IdeaRequest;
import com.elections.apimodel.request.RatingRequest;
import com.elections.apimodel.response.ContenderResponse;
import com.elections.apimodel.response.RatingResponse;
import com.elections.dbmodel.Contender;
import com.elections.dbmodel.Idea;
import com.elections.dbmodel.Rating;
import com.elections.unitTests.CitizenController;
import com.elections.dbmodel.Citizen;
import com.elections.unitTests.ContenderController;
import com.elections.unitTests.ElectionController;
import org.assertj.core.api.Assertions;
import org.hibernate.Hibernate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import javax.xml.ws.Response;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IntegrationTests {

    @Autowired
    CitizenController citizenController;

    @Autowired
    ContenderController contenderController;

    @Autowired
    ElectionController electionController;

    @Test
    public void testCreateReadDeleteCitizen() {
        Citizen citizen = new Citizen("Alex");

        Citizen citizenResult = citizenController.createCitizen(citizen);

        Iterable<Citizen> citizens = citizenController.getCitizens();
        Assertions.assertThat(citizens).extracting(Citizen::getId).contains(citizenResult.getId());

        citizenController.deleteCitizen(citizenResult.getId());
        Assertions.assertThat(citizenController.getCitizens()).extracting(Citizen::getId).doesNotContain(citizenResult.getId());

    }

    @Transactional
    @Test
    public void testFollowerUpdateOnAddDeleteRatingHighRating() {
        Citizen citizen = new Citizen("Alex");

        Citizen citizenResult = citizenController.createCitizen(citizen);

        ContenderResponse contender = (ContenderResponse)citizenController.contest(citizenResult.getId()).getBody();

        Idea idea = (Idea) contenderController.postIdea(contender.getId(),new IdeaRequest("idea")).getBody();

        RatingResponse ratingResponse = (RatingResponse) citizenController.rateIdea(new RatingRequest(citizenResult.getId(),idea.getId(),10)).getBody();

        Contender contenderInDB = contenderController.getContender(contender.getId()).get();

        //Hibernate.initialize(contenderInDB.getFollowers());
        Assertions.assertThat(contenderInDB.getFollowers()).contains(citizen);

        citizenController.deleteRating(new RatingRequest(citizenResult.getId(),idea.getId(),10)).getBody();

        contenderInDB = contenderController.getContender(contender.getId()).get();

        Assertions.assertThat(contenderInDB.getFollowers()).doesNotContain(citizen);

    }

    /*
        Need to start from a clean DB
    */

    // Not working as expected due to Lazy loading :(

    @Transactional
    @Test
    public void testElectionResult() {

        //Add 3 citizens
        Citizen citizen1 = new Citizen("c1-integration");

        Citizen citizenResult1 = citizenController.createCitizen(citizen1);

        Citizen citizen2 = new Citizen("contender1-integration");

        Citizen citizenResult2 = citizenController.createCitizen(citizen2);

        Citizen citizen3 = new Citizen("contender2-integration");

        Citizen citizenResult3 = citizenController.createCitizen(citizen3);

        // make 2 and 3 contenders

        ContenderResponse contender1 = (ContenderResponse)citizenController.contest(citizenResult2.getId()).getBody();

        Idea idea1 = (Idea) contenderController.postIdea(contender1.getId(),new IdeaRequest("idea1")).getBody();

        ContenderResponse contender2 = (ContenderResponse)citizenController.contest(citizenResult3.getId()).getBody();

        Idea idea2 = (Idea) contenderController.postIdea(contender2.getId(),new IdeaRequest("idea2")).getBody();
//
//        Hibernate.initialize(contender1.getManifesto());
//        Hibernate.initialize(contender2.getManifesto());

        citizenController.rateIdea(new RatingRequest(citizenResult1.getId(),idea1.getId(),10));
        citizenController.rateIdea(new RatingRequest(citizenResult1.getId(),idea2.getId(),5));

        ContenderResponse winner = (ContenderResponse)electionController.getResult().getBody();

        Assertions.assertThat(winner.getId()).isEqualTo(contender1.getId());

    }



}
