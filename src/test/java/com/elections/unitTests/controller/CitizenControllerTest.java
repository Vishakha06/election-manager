package com.elections.unitTests.controller;


import com.elections.apimodel.response.ContenderResponse;
import com.elections.dbmodel.Citizen;
import com.elections.unitTests.CitizenController;
import com.elections.unitTests.service.CitizenService;
import com.elections.unitTests.service.IdeaService;
import com.elections.unitTests.service.RatingService;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@WebMvcTest(CitizenController.class)
public class CitizenControllerTest {

    @MockBean
    CitizenService citizenService;

    @MockBean
    RatingService ratingService;

    @MockBean
    IdeaService ideaService;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void testReadCitizens() throws Exception {
        Citizen citizen = new Citizen("Alex");
        List<Citizen> citizens = Arrays.asList(citizen);

        Mockito.when(citizenService.findAll()).thenReturn(citizens);

        mockMvc.perform(get("/v1/citizen"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].name", Matchers.is("Alex")));
    }

    @Test
    public void testContestElection() throws Exception {
        Citizen citizen = new Citizen("Alex");

        ContenderResponse contender = new ContenderResponse(1, "Contender1", null);

        Mockito.when(citizenService.contest(1)).thenReturn(contender);

        mockMvc.perform(put("/v1/citizen/contest/"+"1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", Matchers.is("Contender1")));

    }

    private String getManifestoJSON(){
        return "";
        //return "{\"id\":\"" + id + "\", \"content\":\"test data\"}";
    }

}