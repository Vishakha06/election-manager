package com.elections.service;

import com.elections.dbmodel.Citizen;
import com.elections.repository.CitizenRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ServiceTests {

//    @Autowired
//    CitizenService citizenService;


    @Autowired
    CitizenRepository citizenRepository;

    @Test
    public void testCreateReadDelete() {
        Citizen citizen = new Citizen("Alex");

//        Citizen citizenResult = citizenRepository.save(citizen);
//
//        Iterable<Citizen> citizens = citizenRepository.findAll();
//        Assertions.assertThat(citizens).extracting(Citizen::getName).containsOnly("Alex");
//
//        citizenRepository.deleteAll();
//        Assertions.assertThat(citizenRepository.findAll()).isEmpty();

//        Citizen citizenResult = citizenService.create(citizen);
//
//        Iterable<Citizen> citizens = citizenService.findAll();
//        Assertions.assertThat(citizens).extracting(Citizen::getName).containsOnly("Alex");
//
//        citizenService.delete(citizenResult.getId());
//        Assertions.assertThat(citizenService.findAll()).isEmpty();

    }


}
