package com.elections.controller;


import com.elections.apimodel.response.ContenderResponse;
import com.elections.service.ElectionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/v1/election")
public class ElectionController {

    @Autowired
    ElectionService electionService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ElectionController.class);

    @GetMapping("/result")
    public ResponseEntity getResult(){
        try{
            ContenderResponse contenderResponse = electionService.getResult();
            return new ResponseEntity<>(contenderResponse, HttpStatus.OK);
        } catch(Exception e){
            LOGGER.error("Action=getResult Status=Exception Message=", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
