package com.codercrew.HackConnect.repository;

import com.codercrew.HackConnect.model.Issue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IssueRepository extends JpaRepository<Issue,Long> {

    List<Issue> findByProjectId(Long id);
}
