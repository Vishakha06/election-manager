package com.elections.application;


import com.elections.controller.CitizenController;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CitizenApplicationTests {

    @Autowired
    CitizenController citizenController;

    @Test
    public void contextLoads() {
        Assert.assertNotNull(citizenController);
    }


}