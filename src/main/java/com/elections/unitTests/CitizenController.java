package com.elections.unitTests;

import com.elections.apimodel.response.ContenderResponse;
import com.elections.apimodel.response.RatingResponse;
import com.elections.dbmodel.Idea;
import com.elections.apimodel.request.RatingRequest;
import com.elections.unitTests.service.CitizenService;
import com.elections.unitTests.service.IdeaService;
import com.elections.unitTests.service.RatingService;
import com.elections.unitTests.service.exception.ErrorResponse;
import com.elections.unitTests.service.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.elections.dbmodel.Citizen;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RequestMapping("/v1/citizen")
@RestController
public class CitizenController {

    @Autowired
    private CitizenService citizenService;

    @Autowired
    private RatingService ratingService;

    @Autowired
    private IdeaService ideaService;

    private static final Logger LOGGER = LoggerFactory.getLogger(CitizenController.class);

    /*
        Citizens want to nominate themselves as contestant
    */
    @PutMapping("/contest/{id}")
    public ResponseEntity contest(@PathVariable int id){
        try {
            ContenderResponse cs = citizenService.contest(id);
            return new ResponseEntity<>(cs, HttpStatus.OK);
        } catch (ResourceNotFoundException e){
            LOGGER.error("Action=contest Status=ResourceNotFoundException Message=", e);
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            LOGGER.error("Action=contest Status=Exception Message=", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    /*
        Rate an idea
     */
    @PostMapping("/rating")
    public ResponseEntity rateIdea(@RequestBody RatingRequest ratingRequest){
        try {
            RatingResponse rating = ratingService.addRating(ratingRequest);
            return new ResponseEntity<>(rating, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            LOGGER.error("Action=rateContender Status=ResourceNotFoundException Message=", e);
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            LOGGER.error("Action=rateContender Status=Exception Message=", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
    /*
        Delete a rating
     */
    @DeleteMapping("/rating")
    public ResponseEntity deleteRating(@RequestBody RatingRequest ratingRequest){
        try {
            ratingService.deleteRating(ratingRequest);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            LOGGER.error("Action=deleteRating Status=ResourceNotFoundException Message=", e);
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            LOGGER.error("Action=deleteRating Status=Exception Message=", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    /*
      Below APIs are needed only for testing
     */
    @PostMapping("")
    public Citizen createCitizen(@Valid @RequestBody Citizen citizen){
        return citizenService.create(citizen);
    }

    @GetMapping("")
    public Iterable<Citizen> getCitizens(){
        return citizenService.findAll();
    }

    @GetMapping("/{id}")
    public Citizen getCitizen(@PathVariable int id){
        return citizenService.get(id);
    }

    @DeleteMapping("/{id}")
    public void deleteCitizen(@PathVariable int id){
        citizenService.delete(id);
    }

    @PutMapping("")
    public Citizen updateCitizen(@Valid @RequestBody Citizen citizen){
        return citizenService.update(citizen);
    }

    @GetMapping("/ideas")
    public Iterable<Idea> getIdeas(){
        return ideaService.get();
    }
}
