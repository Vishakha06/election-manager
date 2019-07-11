package com.elections.service;

import com.elections.dbmodel.Citizen;
import com.elections.dbmodel.Idea;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class MessagingService {
    public void sendMessage(Set<Citizen> citizens, String ideaDescription){
        for(Citizen c: citizens){
            System.out.println("To: "+c.getName()+"Here is my idea"+ideaDescription+". Vote for my idea~!~");
        }
    }
}
