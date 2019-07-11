package com.elections.unitTests.service;

import com.elections.dbmodel.Citizen;
import com.elections.repository.CitizenRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Iterator;

@RunWith(MockitoJUnitRunner.class)
public class ServiceTests {

    @Mock
    CitizenRepository citizenRepository;

    @InjectMocks
    CitizenService citizenService;

    @Test
    public void testCreateCitizen() {
        Citizen citizen = new Citizen("Alex");

        Mockito.when(citizenRepository.save(citizen)).thenReturn(citizen);

        Citizen citizenResult = citizenService.create(citizen);

        Assertions.assertThat(citizenResult.getName()).isEqualTo(citizen.getName());

    }


}
