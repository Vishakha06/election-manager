package com.elections.unitTests;

import com.elections.apimodel.request.IdeaRequest;
import com.elections.apimodel.response.ContenderResponse;
import com.elections.dbmodel.Contender;
import com.elections.dbmodel.Idea;
import com.elections.unitTests.service.ContenderService;
import com.elections.unitTests.service.IdeaService;
import com.elections.unitTests.service.exception.ErrorResponse;
import com.elections.unitTests.service.exception.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/contender")
public class ContenderController {

    @Autowired
    ContenderService contenderService;

    @Autowired
    IdeaService ideaService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ContenderController.class);

    @GetMapping("/{id}")
    public Optional<Contender> getContender(@PathVariable Integer id){
        return contenderService.getContender(id);
    }


    @GetMapping("")
    public ResponseEntity getAllContenders(){
        try {
            List<ContenderResponse> contenders = contenderService.getContenders();
            return new ResponseEntity<>(contenders, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Action=getAllContenders Status=Exception Message=", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    /*
    Post an idea
     */
    @PostMapping("/{id}")
    public ResponseEntity postIdea(@PathVariable int contenderId, @RequestBody IdeaRequest ideaRequest) {
        try {
            Idea idea = ideaService.addIdea(contenderId, ideaRequest);
            return new ResponseEntity<>(idea, HttpStatus.OK);
        } catch(ValidationException vs) {
            LOGGER.error("Action=postIdea Status=ValidationException Message=", vs);
            return new ResponseEntity<>(new ErrorResponse(vs.getErrorMessage()), HttpStatus.BAD_REQUEST);
        } catch(Exception e) {
            LOGGER.error("Action=postIdea Status=Exception Message=", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
    /*
        Only for testing
     */
    @DeleteMapping("/{id}")
    public void deleteContender(@PathVariable Integer id) {
        contenderService.deleteContender(id);
    }

}
