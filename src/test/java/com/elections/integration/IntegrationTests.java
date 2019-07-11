package com.elections.integration;

import com.elections.controller.CitizenController;
import com.elections.dbmodel.Citizen;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IntegrationTests {

    @Autowired
    CitizenController citizenController;

    @Test
    public void testCreateReadDelete() {
        Citizen citizen = new Citizen("Alex");

        Citizen citizenResult = citizenController.createCitizen(citizen);

        Iterable<Citizen> citizens = citizenController.getCitizens();
        Assertions.assertThat(citizens).extracting(Citizen::getId).contains(citizenResult.getId());

        citizenController.deleteCitizen(citizenResult.getId());
        Assertions.assertThat(citizenController.getCitizens()).extracting(Citizen::getId).doesNotContain(citizenResult.getId());

    }

}
