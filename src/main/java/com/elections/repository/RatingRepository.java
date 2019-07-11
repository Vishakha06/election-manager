package com.elections.repository;

import com.elections.dbmodel.Citizen;
import com.elections.dbmodel.Contender;
import com.elections.dbmodel.Idea;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.elections.dbmodel.Rating;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface RatingRepository extends CrudRepository<Rating, Integer> {

    Optional<Rating> findByCitizenAndIdea(Citizen citizen, Idea idea);

    @Query(value = "select sum(rating) from rating where idea_id =?1", nativeQuery = true)
    int findSumOfRatingsForIdea(int ideaId);

    @Query(value = "select count(*) from rating where idea_id =?1", nativeQuery = true)
    int findNumberOfVotesForIdea(int ideaId);

    @Query(value="select idea_id, count(*) from rating where idea_id in (:ideas) and rating<5 group by idea_id", nativeQuery = true)
    Map<Integer, Integer> findIdeasWithVotesBelowTheLimit(@Param("ideas")List<Idea> ideas);

    @Query(value="select count(*) from rating where idea_id in (:ideas) and rating>5 and citizen_id=(:citizen_id)", nativeQuery = true)
    int findCountOfIdeasWithVotesMoreThanFive(@Param("ideas")List<Idea> ideas, @Param("citizen_id") int citizen_id);
}
