package com.avalith.challenge.repository;

import com.avalith.challenge.domain.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Integer> {

    @Query(
            value = "SELECT COUNT(vote_employee_id) FROM VOTES",
            nativeQuery = true
    )
    public long count();

    @Query(
            value = "SELECT vote_employee_id, count(v.vote_employee_id) as votos FROM VOTES v WHERE month(date) between :month1 and :month2 group by(vote_employee_id) order by votos desc limit 1",
            nativeQuery = true
    )
    public List<Object[]> mostVotedEmployee(@Param("month1")Integer month1, @Param("month2")Integer month2);

    @Query(
            value = "select vote_employee_id, count(v.vote_employee_id) as votos from votes v inner join employees e " +
                    "                                                                           on e.id = v.vote_employee_id " +
                    "                                                                           inner join areas a on a.id = e.id " +
                    "where a.name = :areaName group by(vote_employee_id) order by votos desc limit 1",
            nativeQuery = true
    )
    public List<Object[]> mostVotedArea(@Param("areaName") String areaName);

}
