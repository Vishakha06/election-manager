package com.elections.repository;

import com.elections.dbmodel.Contender;
import com.elections.dbmodel.Idea;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContenderRepository extends CrudRepository<Contender, Integer> {
}
