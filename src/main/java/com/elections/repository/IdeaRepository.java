package com.elections.repository;

import com.elections.dbmodel.Contender;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.elections.dbmodel.Idea;

import java.util.List;

@Repository
public interface IdeaRepository extends CrudRepository<Idea, Integer> {
}
