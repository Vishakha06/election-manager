package com.elections.system;

import com.elections.dbmodel.Citizen;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class SystemTests {

    @Test
    public void testCreateReadDeleteCitizen() {
        RestTemplate restTemplate = new RestTemplate();

        String url = "http://localhost:8080/v1/citizen";

        Citizen citizen = new Citizen("Alex");
        ResponseEntity<Citizen> entity = restTemplate.postForEntity(url, citizen, Citizen.class);

        Citizen[] citizens = restTemplate.getForObject(url, Citizen[].class);
        Assertions.assertThat(citizens).extracting(Citizen::getName).contains("Alex");

        restTemplate.delete(url+ "/"+entity.getBody().getId());
        Assertions.assertThat(restTemplate.getForObject(url, Citizen[].class)).extracting(Citizen::getId).doesNotContain(entity.getBody().getId());
    }


}
